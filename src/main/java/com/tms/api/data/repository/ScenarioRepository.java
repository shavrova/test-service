package com.tms.api.data.repository;

import com.tms.api.data.entity.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScenarioRepository extends JpaRepository<Scenario, Long> {

    //TODO: find scenarios by partial description

    Optional<Scenario> findByScenarioId(String scenarioId);

    Optional<Scenario> findByScenarioName(String scenarioName);

    Optional<Scenario> findByScenarioNameNot(String scenarioName);

    Streamable<Scenario> findByScenarioNameContaining(String key);

    List<Scenario> findByUserId(String userId);

}
