package com.blck_rbbit.felix.dao.specifications;

import com.blck_rbbit.felix.entities.UserIdea;
import org.springframework.data.jpa.domain.Specification;

public class IdeaSpecification {
    
    public static Specification<UserIdea> costLesserOrEqualsThan(Integer cost) {
        return (Specification<UserIdea>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .lessThanOrEqualTo(root.get("estimatedCosts"), cost);
    }
    
    public static Specification<UserIdea> titleLike(String titlePart) {
        return (Specification<UserIdea>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get("kindOfIdea"), String.format("%%%s%%", titlePart));
    }
    
    public static Specification<UserIdea> findByIdeaId(Long id) {
        return (Specification<UserIdea>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("id"), id);
    }
    
    public static Specification<UserIdea> findByUserId(Long id) {
        return (Specification<UserIdea>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("user"), id);
    }
}
