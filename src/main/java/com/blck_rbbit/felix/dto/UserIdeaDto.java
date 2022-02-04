package com.blck_rbbit.felix.dto;

import com.blck_rbbit.felix.entities.User;
import com.blck_rbbit.felix.entities.UserIdea;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserIdeaDto {
    private Long id;
    private User user;
    private String additionally;
    private String ideaTitle;
    private String kindOfIdea;
    private String idea;
    private Integer estimatedCosts;
    private Integer estimatedEconomicEffect;
    
    
    public UserIdeaDto(UserIdea userIdea) {
       this.id = userIdea.getId();
       this.user = userIdea.getUser();
       this.additionally = userIdea.getAdditionally();
       this.ideaTitle = userIdea.getIdeaTitle();
       this.ideaTitle = userIdea.getKindOfIdea();
       this.kindOfIdea = userIdea.getKindOfIdea();
       this.idea = userIdea.getIdea();
       this.estimatedCosts = userIdea.getEstimatedCosts();
       this.estimatedEconomicEffect = userIdea.getEstimatedEconomicEffect();
    }
    
}
