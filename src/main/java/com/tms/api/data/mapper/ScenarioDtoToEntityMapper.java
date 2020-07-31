package com.tms.api.data.mapper;

import com.tms.api.data.dto.ScenarioDto;
import com.tms.api.data.entity.Feature;
import com.tms.api.data.entity.Scenario;
import com.tms.api.data.repository.FeatureRepository;
import com.tms.api.exception.ResourceNotFoundException;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScenarioDtoToEntityMapper extends PropertyMap<ScenarioDto, Scenario> {
    private FeatureRepository featureRepository;

    @Autowired
    public ScenarioDtoToEntityMapper(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    private Converter<String, Feature> featureIdToEntity = context ->
            featureRepository.findByFeatureId(context.getSource())
                    .orElseThrow(() -> new ResourceNotFoundException("No feature with id " + context.getSource()));


    @Override
    protected void configure() {
        using(featureIdToEntity).map(source.getFeatureId()).setFeature(null);
    }

}
