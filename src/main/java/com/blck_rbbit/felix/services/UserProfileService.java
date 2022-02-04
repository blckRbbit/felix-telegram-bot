package com.blck_rbbit.felix.services;

import com.blck_rbbit.felix.dao.UserProfileDao;
import com.blck_rbbit.felix.dao.specifications.UserProfileSpecification;
import com.blck_rbbit.felix.entities.User;
import com.blck_rbbit.felix.entities.UserProfile;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileDao userProfileDao;
    private final UserService userService;
    private User user;
    
    public Page<UserProfile> find(Integer page, Long userId, String lastName, String institution) {
        Specification<UserProfile> specification = Specification.where(null);
        if (lastName != null) {
            specification = specification.and(UserProfileSpecification.lastNameLike(lastName));
        }
        if (institution != null) {
            specification = specification.and(UserProfileSpecification.institutionLike(institution));
        }
        if (userId != null) {
            UserProfileSpecification userProfileSpecification = new UserProfileSpecification(userService);
            specification = specification.and(userProfileSpecification.findProfileByUserId(userId));
        }
        
        return userProfileDao.findAll(specification, PageRequest.of(page-1, 5));
    }
    
    public void saveUserProfileToDataBase(
            String firstName, String lastName, String institution,
            String position, Long phoneNumber, String email
    ) {
        UserProfile userProfile;
        if (user.getDetails() == null) {
            userProfile = new UserProfile();
            userProfile.setUser(user);
            userProfile.setFirstName(firstName);
            userProfile.setLastName(lastName);
            userProfile.setInstitution(institution);
            userProfile.setPosition(position);
            userProfile.setPhoneNumber(phoneNumber);
            userProfile.setEmail(email);
        } else {
            userProfile = user.getDetails();
        }
        user.setDetails(userProfile);
        userProfileDao.save(userProfile);
        userService.saveUser(user);
    }
    
    public List<UserProfile> findAll() {
        return userProfileDao.findAll();
    }
    
    public Page<UserProfile> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<UserProfile> users = findAll();
        List<UserProfile> list;
        if (users.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, users.size());
            list = users.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), users.size());
    }
}
