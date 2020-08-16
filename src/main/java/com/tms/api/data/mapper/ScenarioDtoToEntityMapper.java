package com.tms.api.data.mapper;

import com.tms.api.data.dto.ScenarioDto;
import com.tms.api.data.dto.StepDto;
import com.tms.api.data.entity.Feature;
import com.tms.api.data.entity.Scenario;
import com.tms.api.data.entity.Step;
import com.tms.api.data.repository.FeatureRepository;
import com.tms.api.data.repository.ScenarioRepository;
import com.tms.api.data.repository.StepRepository;
import com.tms.api.exception.ResourceNotFoundException;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScenarioDtoToEntityMapper extends PropertyMap<ScenarioDto, Scenario> {
    private FeatureRepository featureRepository;
    private StepRepository stepRepository;
    private ScenarioRepository scenarioRepository;

    @Autowired
    public ScenarioDtoToEntityMapper(FeatureRepository featureRepository, StepRepository stepRepository, ScenarioRepository scenarioRepository) {
        this.featureRepository = featureRepository;
        this.stepRepository = stepRepository;
        this.scenarioRepository = scenarioRepository;
    }

    private Converter<String, Feature> featureIdToEntity = context ->
            featureRepository.findByFeatureId(context.getSource())
                    .orElseThrow(() -> new ResourceNotFoundException("Feature id doesn't exist."));

    private Converter<List<StepDto>, List<Step>> stepsDtosToEntities = context -> {
        if (context.getSource() == null || context.getSource().isEmpty())
            return new ArrayList<>();
        return context.getSource().stream().map(stepDto ->
                stepRepository.findByStepId(stepDto.getStepId()).orElseThrow(() ->
                        new ResourceNotFoundException("Step id doesn't exist.")))
                .collect(Collectors.toList());
    };

    @Override
    protected void configure() {
        using(featureIdToEntity).map(source.getFeatureId()).setFeature(null);
        using(stepsDtosToEntities).map(source.getSteps()).setSteps(null);
    }

}
