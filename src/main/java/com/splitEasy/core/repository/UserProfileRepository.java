package com.splitEasy.core.repository;

import com.splitEasy.core.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    boolean existsByUserId(Long userId);
}