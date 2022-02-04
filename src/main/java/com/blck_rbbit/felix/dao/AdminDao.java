package com.blck_rbbit.felix.dao;

import com.blck_rbbit.felix.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminDao extends JpaRepository<Admin, Long> {
    Optional<Admin> findByLoginAndPassword(String login, String password);
    Optional<Admin> findByLogin(String login);
    void deleteByLogin(String telegramName);
}
