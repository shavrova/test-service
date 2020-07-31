package com.tms.api.data.repository;

import com.tms.api.data.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StepRepository extends JpaRepository<Step, Long> {

    Optional<Step> findByStepId(String featureId);

    Optional<Step> findByStepName(String featureName);
}
