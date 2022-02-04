package com.blck_rbbit.felix.services;

import com.blck_rbbit.felix.dao.UserDao;
import com.blck_rbbit.felix.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    
    public void saveUser(User user) {
        userDao.save(user);
    }
    
    public void saveUserToDataBase(Long telegramId, String userName) {
        User user = userDao.findByTelegramIdAndName(telegramId, userName);
        if (user == null) {
            userDao.save(new User(telegramId, userName));
        }
    }
    
    public User findUserByTelegramId(Long telegramId) {
        return userDao.findByTelegramId(telegramId);
    }
    
    public User findByName(String name) {
        return userDao.findByName(name).orElseThrow(RuntimeException::new);
    }
    
    public User findById(Long id) {
        return userDao.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
    }
    
    public boolean userExists(String login) {
        boolean result = false;
        List<User> users = userDao.findAll();
        for (User user:users) {
            if (user.getName().equals(login)) {
                result = true;
                break;
            }
            break;
        }
        return result;
    }
}
