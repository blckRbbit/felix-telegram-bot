package com.blck_rbbit.felix.dao.specifications;

import com.blck_rbbit.felix.entities.User;
import com.blck_rbbit.felix.entities.UserProfile;
import com.blck_rbbit.felix.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfileSpecification {
    private final UserService userService;
    
    public Specification<UserProfile> findProfileByUserId(Long id) {
        Long profileId = userService.findById(id).getDetails().getId();
        return (Specification<UserProfile>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("user"), profileId);
    }
    
    public static Specification<UserProfile> findByProfileId(Long id) {
        return (Specification<UserProfile>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("user"), id);
    }
    public static Specification<UserProfile> lastNameLike(String lastName) {
        return (Specification<UserProfile>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get("lastName"), String.format("%%%s%%", lastName));
    }
    
    public static Specification<UserProfile> institutionLike(String institution) {
        return (Specification<UserProfile>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get("institution"), String.format("%%%s%%", institution));
    }
}
