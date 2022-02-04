package com.blck_rbbit.felix.dao;

import com.blck_rbbit.felix.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByTelegramIdAndName(Long telegramId, String name);
    User findByTelegramId(Long telegramId);
    Optional<User> findByName(String name);
    
}
