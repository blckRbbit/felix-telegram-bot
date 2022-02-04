package com.blck_rbbit.felix.services;

import com.blck_rbbit.felix.dao.AdminDao;
import com.blck_rbbit.felix.entities.Admin;
import com.blck_rbbit.felix.entities.User;
import com.blck_rbbit.felix.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final Util util = new Util();
    private final AdminDao adminDao;
    private final UserService userService;
    
    public boolean isAuth(String login, String password) {
        return passwordEncoder.matches(password, passwordEncoder.encode(password)) && isFind(login);
    }
    
    public boolean isFind(String login) {
        return adminDao.findByLogin(login).isPresent();
    }
    
    public Admin findByLogin(String login) {
        return adminDao.findByLogin(login).orElseThrow(RuntimeException::new);
    }
    
    boolean adminIsExists(String login) {
        return adminDao.findByLogin(login).isPresent();
    }
    
    public void sendPassword(Long chatId, String login) {
        String password = util.generatePassword();
        Admin admin = findByLogin(login);
        admin.setPassword(passwordEncoder.encode(password));
        adminDao.save(admin);
        util.createContent(util.createPostData(chatId, password));
    }
    
    @Transactional
    public void deleteAdmin(String telegramName) {
        adminDao.deleteByLogin(telegramName);
    }
    
    public void sendMessageForAllAdmins(String message) {
        List<Admin> admins = adminDao.findAll();
        for (Admin admin : admins) {
            Long chatId = userService.findByName(admin.getLogin()).getTelegramId();
            util.createContent(util.createPostData(chatId, message));
        }
    }
    
    @Transactional
    public boolean adminIsCreated(String login) {
        boolean result = false;
        if (userService.userExists(login) && !adminIsExists(login)) {
            User user = userService.findByName(login);
            Admin admin = new Admin();
            String password = util.generatePassword();
            Long chatId = user.getTelegramId();
            util.createContent(util.createPostData(chatId, "Вам дали админку, пароль: " + password));
            admin.setLogin(login);
            admin.setPassword(passwordEncoder.encode(password));
            adminDao.saveAndFlush(admin);
            result = true;
        }
        return result;
    }
}
