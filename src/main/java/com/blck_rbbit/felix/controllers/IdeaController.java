package com.blck_rbbit.felix.controllers;

import com.blck_rbbit.felix.entities.User;
import com.blck_rbbit.felix.services.UserIdeaService;
import com.blck_rbbit.felix.services.UserProfileService;
import com.blck_rbbit.felix.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class IdeaController {
    
    private final UserService userService;
    private final UserProfileService userProfileService;
    private final UserIdeaService userIdeaService;
    private Long telegramId;

    @GetMapping("/idea/{user_id}")
    public String getFormFromSubmittingAnIdea(@PathVariable("user_id") Long id) {
        setTelegramId(id);
        return "felix";
    }

    @PostMapping("/idea/{user_id}")
    public String sendUserDataToDataBase(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String institution,
            @RequestParam String position,
            @RequestParam Long phoneNumber,
            @RequestParam String email,
            @RequestParam String ideaTitle,
            @RequestParam(value = "kindOfIdea") String kindOfIdea,
            @RequestParam Integer estimatedCosts,
            @RequestParam Integer estimatedEconomicEffect,
            @RequestParam String idea,
            @RequestParam String additionally
    ) {
        User user = userService.findUserByTelegramId(telegramId);
        userProfileService.setUser(user);
        userIdeaService.setUser(user);
        userProfileService.saveUserProfileToDataBase(
                firstName, lastName, institution, position, phoneNumber, email
        );
        userIdeaService.saveUserIdeaToDataBase(
                additionally, ideaTitle, kindOfIdea, idea, estimatedCosts, estimatedEconomicEffect
        );
        return "success";
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }
    
}
