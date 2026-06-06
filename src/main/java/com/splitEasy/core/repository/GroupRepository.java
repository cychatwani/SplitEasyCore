package com.splitEasy.core.repository;

import com.splitEasy.core.entity.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, String> {
}