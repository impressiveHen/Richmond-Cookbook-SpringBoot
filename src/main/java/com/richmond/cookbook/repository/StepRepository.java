package com.richmond.cookbook.repository;

import com.richmond.cookbook.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Step, Integer> {
}
