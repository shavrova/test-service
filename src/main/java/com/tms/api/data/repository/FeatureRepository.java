package com.tms.api.data.repository;

import com.tms.api.data.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

    Optional<Feature> findByFeatureId(String featureId);

    Optional<Feature> findByFeatureName(String featureName);
}
