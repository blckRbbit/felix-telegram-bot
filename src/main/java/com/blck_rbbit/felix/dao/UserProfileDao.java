package com.blck_rbbit.felix.dao;

import com.blck_rbbit.felix.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserProfileDao extends JpaRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {

}
