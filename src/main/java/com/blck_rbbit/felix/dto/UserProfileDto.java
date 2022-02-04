package com.blck_rbbit.felix.dto;

import com.blck_rbbit.felix.entities.User;
import com.blck_rbbit.felix.entities.UserProfile;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserProfileDto {
    private Long id;
    private User user;
    private String firstName;
    private String lastName;
    private String institution;
    private String position;
    private Long phoneNumber;
    private String email;
    
    public UserProfileDto(UserProfile userProfile) {
        this.id = userProfile.getId();
        this.user = userProfile.getUser();
        this.firstName = userProfile.getFirstName();
        this.lastName = userProfile.getLastName();
        this.institution = userProfile.getInstitution();
        this.position = userProfile.getPosition();
        this.phoneNumber = userProfile.getPhoneNumber();
        this.email = userProfile.getEmail();
    }
    
}
