package com.splitEasy.core.repository;

import com.splitEasy.core.entity.reference.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, String> {
}