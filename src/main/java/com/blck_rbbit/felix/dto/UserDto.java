package com.blck_rbbit.felix.dto;

import com.blck_rbbit.felix.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private Long telegramId;
    
    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.telegramId = user.getTelegramId();
    }
    
}
