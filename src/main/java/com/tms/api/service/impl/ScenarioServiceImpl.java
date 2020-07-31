package com.tms.api.service.impl;

import com.tms.api.data.dto.ScenarioDto;
import com.tms.api.data.entity.Scenario;
import com.tms.api.data.mapper.ScenarioDtoToEntityMapper;
import com.tms.api.data.mapper.ScenarioEntityToDtoMapper;
import com.tms.api.data.repository.FeatureRepository;
import com.tms.api.data.repository.ScenarioRepository;
import com.tms.api.exception.ResourceNotFoundException;
import com.tms.api.exception.NotUniqueEntryException;
import com.tms.api.service.ScenarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScenarioServiceImpl implements ScenarioService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ScenarioRepository repository;
    @Autowired
    private ScenarioDtoToEntityMapper scenarioDtoToEntityMapper;
    @Autowired
    private ScenarioEntityToDtoMapper scenarioEntityToDtoMapper;
    @Autowired
    private FeatureRepository featureRepository;

    @PostConstruct
    public void init() {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.addMappings(scenarioDtoToEntityMapper);
        mapper.addMappings(scenarioEntityToDtoMapper);
    }

    @Override
    public ScenarioDto create(ScenarioDto dto) {
        if (repository.findByScenarioName(dto.getScenarioName()).isEmpty()) {
            dto.setScenarioId(UUID.randomUUID().toString().replace("-", ""));
            Scenario scenario = mapper.map(dto, Scenario.class);
            repository.save(scenario);
            return mapper.map(scenario, ScenarioDto.class);
        }
        throw new NotUniqueEntryException(String.format("Scenario name \'%s\' already exists. Please use another name.", dto.getScenarioName()));

    }

    @Override
    public ScenarioDto createScenarioByUser(ScenarioDto dto, String userId) {
        dto.setUserId(userId);
        if (repository.findByScenarioName(dto.getScenarioName()).isEmpty()) {
            dto.setScenarioId(UUID.randomUUID().toString().replace("-", ""));
            Scenario scenario = mapper.map(dto, Scenario.class);
            repository.save(scenario);
            System.out.println("inside createScenarioByUser");
            System.out.println("Scenario object id " + scenario.getFeature().getFeatureId());
            return mapper.map(scenario, ScenarioDto.class);
        }
        throw new NotUniqueEntryException(String.format("Scenario name \'%s\' already exists. Please use another name.", dto.getScenarioName()));
    }

    @Override
    public ScenarioDto update(ScenarioDto dto) {
        return null;
    }

    @Override
    public ScenarioDto getById(String id) {
        Scenario scenario = repository.findByScenarioId(id).orElseThrow(() -> new ResourceNotFoundException("Can't find scenario with id " + id));
        return mapper.map(scenario, ScenarioDto.class);
    }

    @Override
    public Page<ScenarioDto> findPage(Pageable page) {
        Page<Scenario> scenarios = repository.findAll(page);
        return scenarios.map(scenario -> mapper.map(scenario, ScenarioDto.class));
    }

    @Override
    public void deleteById(String id) {
        Scenario scenario = repository
                .findByScenarioId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find scenario with id " + id));
        repository.delete(scenario);
    }

    @Override
    public List<ScenarioDto> getUserScenarios(String userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(scenario -> mapper.map(scenario, ScenarioDto.class))
                .collect(Collectors.toList());
    }


}
