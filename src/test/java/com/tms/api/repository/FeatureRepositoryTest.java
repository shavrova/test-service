package com.tms.api.repository;

import com.tms.api.data.entity.Feature;
import com.tms.api.data.entity.Scenario;
import com.tms.api.data.repository.FeatureRepository;
import com.tms.api.data.repository.ScenarioRepository;
import com.tms.api.model.scenario.ScenarioResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static com.tms.api.util.ObjectUtil.createFeatureObject;
import static com.tms.api.util.ObjectUtil.createFeatureObjectWithScenario;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@DataJpaTest
public class FeatureRepositoryTest {

    @Autowired
    FeatureRepository featureRepository;

    @Autowired
    ScenarioRepository scenarioRepository;

    @Test
    public void whenFeatureIsSaved_thenSuccess() {
        Feature feature = createFeatureObject();
        Feature saved = featureRepository.save(feature);
        assertThat(saved).isEqualTo(feature);
    }

    @Test
    public void whenFeatureIsSavedWithoutId_thenFail() {
        Feature feature = createFeatureObject();
        feature.setFeatureId(null);
        assertThatThrownBy(() -> featureRepository.save(feature))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void whenFeatureIsUpdated_thenSuccess() {
        Feature feature = createFeatureObject();
        feature.setId(1L);
        featureRepository.save(feature);

        Feature retrievedBeforeUpdate = featureRepository.findByFeatureId(feature.getFeatureId()).get();
        log.info("feature before update: " + retrievedBeforeUpdate);

        Feature updatedFeature = createFeatureObject();
        updatedFeature.setId(1L);
        updatedFeature.setFeatureId(feature.getFeatureId());

        featureRepository.save(updatedFeature);

        Feature retrievedAfterUpdate = featureRepository.findByFeatureId(feature.getFeatureId()).get();
        log.info("feature after update: " + retrievedAfterUpdate);
        assertThat(retrievedAfterUpdate).isEqualToIgnoringGivenFields(updatedFeature, "scenarios");
    }

    @Test
    public void whenFeatureIsSavedWithScenario_thenPersistedSuccessfully(){
        Feature feature = createFeatureObjectWithScenario();
        Feature saved = featureRepository.save(feature);
        assertThat(saved).isEqualToComparingFieldByField(feature);
    }

    @Test
    public void whenFindFeatureById_thenReturned() {
        Feature feature = createFeatureObject();
        featureRepository.save(feature);
        Feature retrieved = featureRepository.findByFeatureId(feature.getFeatureId()).get();
        assertThat(retrieved).isEqualTo(feature);
    }
}
