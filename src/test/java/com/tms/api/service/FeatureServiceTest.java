package com.tms.api.service;

import com.tms.api.data.dto.FeatureDto;
import com.tms.api.data.entity.Feature;
import com.tms.api.data.repository.FeatureRepository;
import com.tms.api.exception.AlreadyExistsException;
import com.tms.api.exception.ResourceNotFoundException;
import com.tms.api.service.impl.FeatureServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static com.tms.api.util.ObjectUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FeatureServiceTest {
    @Spy
    private ModelMapper mapper;

    @Mock
    private FeatureRepository featureRepository;

    @InjectMocks
    private FeatureServiceImpl featureService;

    private FeatureDto featureDto;

    private Feature feature;


    @BeforeEach
    public void beforeAll() {
        feature = createFeatureObject();
        featureDto = createFeatureDtoFromFeature(feature);
    }

    @Test
    public void whenSaveFeature_thenSuccess() {
        when(featureRepository.findByFeatureName(anyString())).thenReturn(Optional.empty());
        when(featureRepository.save(any(Feature.class))).thenReturn(feature);
        FeatureDto saved = featureService.create(featureDto);
        assertThat(saved).isNotNull();
        assertThat(featureDto).isEqualToIgnoringGivenFields(saved, "featureId");
    }

    @Test
    public void whenSaveFeature_andFeatureNameAlreadyExists_thenFail() {
        when(featureRepository.findByFeatureName(anyString())).thenReturn(Optional.of(feature));
        assertThatThrownBy(() -> featureService.create(featureDto))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining("Feature name already exists. Please use another name.");
    }


    @Test
    public void whenUpdateFeature_andFeatureNameAlreadyExists_thenError() {
        when(featureRepository.findByFeatureName(anyString())).thenReturn(Optional.of(feature));
        assertThatThrownBy(() -> featureService.update(featureDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Feature id doesn't exist.");
    }

    @Test
    public void whenUpdateFeature_thenSuccess() {
        FeatureDto featureDto = createFeatureDtoObject(); //creating another dto object with different values for update
        featureDto.setFeatureId(feature.getFeatureId()); //set feature id to dto
        when(featureRepository.findByFeatureId(anyString())).thenReturn(Optional.of(feature));//feature exists, can proceed with update
        when(featureRepository.save(any(Feature.class))).thenReturn(feature);//persisting to repository successful
        FeatureDto updated = featureService.update(featureDto);//dto returned from service
        assertThat(updated).isNotNull();
        assertThat(featureDto).isEqualToComparingFieldByField(updated);
    }


    @Test
    public void whenGetFeatureById_thenFeatureReturned(){
        when(featureRepository.findByFeatureId(any(String.class))).thenReturn(Optional.of(feature));
        FeatureDto retrieved = featureService.getById(featureDto.getFeatureId());
        assertThat(retrieved).isNotNull();
        assertThat(featureDto).isEqualToComparingFieldByField(retrieved);
    }


    private FeatureDto createFeatureDtoFromFeature(Feature feature) {
        log.info("Feature : " + feature);
        FeatureDto featureDto = mapper.map(feature, FeatureDto.class);
        log.info("Feature dto : " + featureDto);
        return featureDto;
    }




}
