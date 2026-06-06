package com.splitEasy.core.repository;

import com.splitEasy.core.entity.reference.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
}