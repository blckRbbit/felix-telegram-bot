package com.blck_rbbit.felix.dto;

import com.blck_rbbit.felix.entities.Admin;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminDto {
    private Long id;
    private String login;
    
    public AdminDto(Admin admin) {
        this.id = admin.getId();
        this.login = admin.getLogin();
    }
    
}
