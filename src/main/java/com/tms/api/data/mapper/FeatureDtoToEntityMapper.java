package com.tms.api.data.mapper;

import com.tms.api.data.dto.FeatureDto;
import com.tms.api.data.dto.ScenarioDto;
import com.tms.api.data.entity.Feature;
import com.tms.api.data.entity.Scenario;
import com.tms.api.data.repository.ScenarioRepository;
import com.tms.api.exception.ResourceNotFoundException;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class FeatureDtoToEntityMapper extends PropertyMap<FeatureDto, Feature> {

    private ScenarioRepository scenarioRepository;

    @Autowired
    public FeatureDtoToEntityMapper(ScenarioRepository scenarioRepository) {
        this.scenarioRepository = scenarioRepository;
    }

    private Converter<List<ScenarioDto>, List<Scenario>> scenarioDtosToScenarios = context ->
    {
        if (context.getSource() == null || context.getSource().isEmpty()) {
            return new ArrayList<>();
        }
        return context.getSource()
                .stream().map(dto -> scenarioRepository.findByScenarioId(dto.getScenarioId())
                        .orElseThrow(() -> new ResourceNotFoundException("Scenario id doesn't exist.")))
                .collect(Collectors.toList());
    };

    @Override
    protected void configure() {
        using(scenarioDtosToScenarios).map(source.getScenarios()).setScenarios(null);
    }
}
