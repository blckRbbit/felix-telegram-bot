package com.blck_rbbit.felix.controllers;

import com.blck_rbbit.felix.services.AdminService;
import com.blck_rbbit.felix.services.UserIdeaService;
import com.blck_rbbit.felix.services.UserService;
import com.blck_rbbit.felix.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AdminService adminService;
    private final UserService userService;
    @GetMapping("/auth")
    public String getAuthForm() {
        return "felix_auth";
    }
    
    @GetMapping("/fail_auth")
    public String getFailAuthForm() {
        return "felix_fail_auth";
    }
    
    @PostMapping("/auth")
    public String auth(@RequestParam String login, @RequestParam String password) {
        String result;
        if (adminService.isAuth(login, password)) {
            result = "redirect:/admin";
            log.debug("Успешная авторизация аминистратора {} ", login);
        } else {
            result = "redirect:/fail_auth";
            log.error("Неверный логин или пароль");
        }
        return result;
    }

    @GetMapping("/restore_password")
    public String getRestorePasswordPage() { return "felix_forgotten";}
    
    @PostMapping("/restore_password")
    //todo шифр пароля
    public String sendPasswordToTelegramAccount(@RequestParam String login) {
        String result;
        if (adminService.isFind(login)) {
            Long chatId = userService.findByName(login).getTelegramId();
            adminService.sendPassword(chatId, login);
            result = "redirect:/auth";
            log.debug("Пользователю {} отправлен пароль", login);
        } else {
            result = "redirect:/fail_auth";
            log.error("Пользователь {} не зарегистрирован, как админ", login);
        }
        return result;
    }
}
