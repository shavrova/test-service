package com.tms.api.service.impl;


import com.tms.api.data.dto.FeatureDto;
import com.tms.api.data.entity.Feature;
import com.tms.api.data.repository.FeatureRepository;
import com.tms.api.exception.ItemNotFoundException;
import com.tms.api.exception.NotUniqueEntryException;
import com.tms.api.service.FeatureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    FeatureRepository repository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public FeatureDto create(FeatureDto dto) {
        if (repository.findByFeatureName(dto.getFeatureName()).isEmpty()) {
            dto.setFeatureId(UUID.randomUUID().toString().replace("-", ""));
            Feature feature = mapper.map(dto, Feature.class);
            repository.save(feature);
            FeatureDto created = mapper.map(feature, FeatureDto.class);
            created.setScenarios(Collections.emptyList());
            return created;
        }
        throw new NotUniqueEntryException(String.format("Feature name \'%s\' already exists. Please use another name.", dto.getFeatureName()));
    }

    @Override
    public FeatureDto update(FeatureDto dto) {
        //Feature feature = repository.findByFeatureId(dto.getFeatureId()).orElseThrow(() ->new ItemNotFoundException("Feature with this id doesn't exist"));
        return null;
    }

    @Override
    public FeatureDto getById(String id) {
        Feature feature = repository.findByFeatureId(id).orElseThrow(() -> new ItemNotFoundException("Can't find feature with id " + id));
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
                .orElseThrow(() -> new ItemNotFoundException("Can't find feature with id " + id));
        repository.delete(feature);
    }
}
