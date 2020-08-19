package com.tms.api.service.impl;


import com.tms.api.data.dto.FeatureDto;
import com.tms.api.data.entity.Feature;
import com.tms.api.data.repository.FeatureRepository;
import com.tms.api.exception.AlreadyExistsException;
import com.tms.api.exception.ResourceNotFoundException;
import com.tms.api.service.FeatureService;
import com.tms.api.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Slf4j
@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    FeatureRepository repository;

    @Autowired
    private ModelMapper mapper;

    @PostConstruct
    public void init() {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public FeatureDto create(FeatureDto dto) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        if (repository.findByFeatureName(dto.getFeatureName()).isPresent())
            throw new AlreadyExistsException("Feature name already exists. Please use another name.");
        dto.setFeatureId(IdUtil.uuid());
        Feature feature = mapper.map(dto, Feature.class);
        Feature saved = repository.save(feature);
        FeatureDto created = mapper.map(saved, FeatureDto.class);
        created.setScenarios(Collections.emptyList());//feature can't be created with scenarios
        return created;
    }

    @Override
    public FeatureDto update(FeatureDto dto) {
        Feature feature = repository.findByFeatureId(dto.getFeatureId())
                .orElseThrow(() -> new ResourceNotFoundException("Feature id doesn't exist."));
        if (!dto.getFeatureName().equals(feature.getFeatureName()))
            if (repository.findByFeatureName(dto.getFeatureName()).isPresent())
                throw new AlreadyExistsException("Feature name already exist.");
        feature.setFeatureName(dto.getFeatureName());
        feature.setFeatureDescription(dto.getFeatureDescription());
        feature.setClassName(dto.getClassName());
        repository.save(feature);
        return mapper.map(feature, FeatureDto.class);
    }

    @Override
    public FeatureDto getById(String id) {
        Feature feature = repository.findByFeatureId(id).orElseThrow(() -> new ResourceNotFoundException("Feature id doesn't exist."));
        return mapper.map(feature, FeatureDto.class);
    }

    @Override
    public Page<FeatureDto> findPage(Pageable page) {
        Page<Feature> features = repository.findAll(page);
        return features.map(f -> mapper.map(f, FeatureDto.class));
    }

    @Override
    public void deleteById(String id) {
        Feature feature = repository
                .findByFeatureId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feature id doesn't exist."));
        repository.delete(feature);
    }
}
