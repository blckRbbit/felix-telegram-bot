package com.blck_rbbit.felix.services;

import com.blck_rbbit.felix.dao.UserIdeaDao;
import com.blck_rbbit.felix.dao.specifications.IdeaSpecification;
import com.blck_rbbit.felix.entities.User;
import com.blck_rbbit.felix.entities.UserIdea;
import com.blck_rbbit.felix.utils.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserIdeaService {
    private final UserIdeaDao userIdeaDao;
    private final AdminService adminService;
    private final Util util = new Util();
    private User user;
    
    public UserIdeaService(UserIdeaDao userIdeaDao, AdminService adminService) {
        this.userIdeaDao = userIdeaDao;
        this.adminService = adminService;
    }
    
    public Page<UserIdea> find(Integer cost, Integer page, Long ideaId, Long userId, String partName) {
        Specification<UserIdea> specification = Specification.where(null);
        if (cost != null) {
            specification = specification.and(IdeaSpecification.costLesserOrEqualsThan(cost));
        }
        if (ideaId != null) {
            specification = specification.and(IdeaSpecification.findByIdeaId(ideaId));
        }
        if (userId != null) {
            specification = specification.and(IdeaSpecification.findByUserId(userId));
        }
        if (partName != null) {
            specification = specification.and(IdeaSpecification.titleLike(partName));
        }

        return userIdeaDao.findAll(specification, PageRequest.of(page-1, 5));
    }
    
    public void saveUserIdeaToDataBase(
            String additionally, String ideaTitle, String kindOfIdea,
            String idea_description, Integer estimatedCosts, Integer estimatedEconomicEffect
    ) {
        UserIdea userIdea = new UserIdea();
        userIdea.setUser(user);
        userIdea.setAdditionally(additionally);
        userIdea.setIdeaTitle(ideaTitle);
        userIdea.setKindOfIdea(kindOfIdea);
        userIdea.setIdea(idea_description);
        userIdea.setEstimatedCosts(estimatedCosts);
        userIdea.setEstimatedEconomicEffect(estimatedEconomicEffect);
        userIdeaDao.save(userIdea);
        String messageForAdmins = String.format("Пользователь %s оставил новую идею! Тип идеи: %s", user.getName(), kindOfIdea);
        String messageForUser = String.format("Ваша идея принята в обработку. Номер заявки %s", userIdea.getId());
        Long chatId = user.getTelegramId();
        util.createContent(util.createPostData(chatId, messageForUser));
        adminService.sendMessageForAllAdmins(messageForAdmins);
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public UserIdea findIdeaById(Long id) {
        return userIdeaDao.findById(id).orElseThrow(() -> new RuntimeException("Idea not found. Id: " + id));
    }
    
    @Transactional
    public void removeIdea(Long id) {
        UserIdea idea = findIdeaById(id);
        Long chatId = idea.getUser().getTelegramId();
        String message = String.format(
                "Ваша идея с названем %s, тип: %s, удалена с рассмотрения",
                idea.getIdeaTitle(), idea.getKindOfIdea()
        );
        util.createContent(util.createPostData(chatId, message));
        userIdeaDao.delete(idea);
    }
}
