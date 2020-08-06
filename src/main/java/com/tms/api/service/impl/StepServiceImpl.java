package com.tms.api.service.impl;

import com.tms.api.data.dto.StepDto;
import com.tms.api.data.entity.Step;
import com.tms.api.data.repository.StepRepository;
import com.tms.api.exception.AlreadyExistsException;
import com.tms.api.exception.ResourceNotFoundException;
import com.tms.api.service.StepService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
public class StepServiceImpl implements StepService {

    @Autowired
    StepRepository repository;

    @Autowired
    private ModelMapper mapper;


    @PostConstruct
    public void init() {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public StepDto create(StepDto dto) {
        if (repository.findByStepName(dto.getStepName()).isEmpty()) {
            dto.setStepId(UUID.randomUUID().toString().replace("-", ""));
            Step step = mapper.map(dto, Step.class);
            repository.save(step);
            return mapper.map(step, StepDto.class);
        }
        throw new AlreadyExistsException("Step name already exists. Please use another name.");
    }

    @Override
    public StepDto update(StepDto dto) {
        return null;
    }

    @Override
    public StepDto getById(String id) {
        Step step = repository.findByStepId(id).orElseThrow(() -> new ResourceNotFoundException("Step id doesn't exists."));
        return mapper.map(step, StepDto.class);
    }

    @Override
    public Page<StepDto> findPage(Pageable page) {
        Page<Step> steps = repository.findAll(page);
        return steps.map(f -> mapper.map(f, StepDto.class));
    }

    @Override
    public void deleteById(String id) {

    }
}
