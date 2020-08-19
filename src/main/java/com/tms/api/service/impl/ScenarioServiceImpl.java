package com.tms.api.service.impl;

import com.tms.api.data.dto.ScenarioDto;
import com.tms.api.data.entity.Scenario;
import com.tms.api.data.mapper.ScenarioDtoToEntityMapper;
import com.tms.api.data.mapper.ScenarioEntityToDtoMapper;
import com.tms.api.data.repository.FeatureRepository;
import com.tms.api.data.repository.ScenarioRepository;
import com.tms.api.data.repository.StepRepository;
import com.tms.api.exception.AlreadyExistsException;
import com.tms.api.exception.ResourceNotFoundException;
import com.tms.api.service.ScenarioService;
import com.tms.api.util.IdUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
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

    @Autowired
    private StepRepository stepRepository;

    @PostConstruct
    public void init() {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.addMappings(scenarioDtoToEntityMapper);
        mapper.addMappings(scenarioEntityToDtoMapper);
    }

    @Override
    public ScenarioDto createScenarioByUser(ScenarioDto dto, String userId) {
        dto.setUserId(userId);//set user id
        if (repository.findByScenarioName(dto.getScenarioName()).isPresent()) //throw exception if scenario name exists
            throw new AlreadyExistsException("Scenario name already exists. Please use another name.");
        dto.setScenarioId(IdUtil.uuid());//set scenario id
        Scenario scenario = mapper.map(dto, Scenario.class);//map dto to entity
        repository.save(scenario); //save scenario object
        return mapper.map(scenario, ScenarioDto.class);//map entity back to dto
    }

    @Override
    public ScenarioDto create(ScenarioDto dto) {
        throw new NotImplementedException("Scenario can only be created with user id. Use createScenarioByUser method");
    }

    @Override
    public ScenarioDto update(ScenarioDto dto) {
        //Find scenario by id and throw exception id doesn't exists
        Scenario s = repository.findByScenarioId(dto.getScenarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Scenario id doesn't exists."));
        if (!dto.getScenarioName().equals(s.getScenarioName()))//check if new name doesn't equal current, otherwise do nothing
            if (repository.findByScenarioName(dto.getScenarioName()).isPresent()) //Find scenario by name and trow exception if name already exist
                throw new AlreadyExistsException("Scenario name already exists. Please use another name.");
        Scenario scenario = mapper.map(dto, Scenario.class);
        scenario.setId(s.getId());
        scenario.setCreatedAt(s.getCreatedAt());
        scenario.setUserId(s.getUserId());
        repository.save(scenario);
        return mapper.map(scenario, ScenarioDto.class);
    }

    @Override
    public ScenarioDto getById(String id) {
        Scenario scenario = repository.findByScenarioId(id).orElseThrow(() -> new ResourceNotFoundException("Scenario id doesn't exists."));
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
                .orElseThrow(() -> new ResourceNotFoundException("Scenario id doesn't exists."));
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
