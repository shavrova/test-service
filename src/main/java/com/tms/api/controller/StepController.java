package com.tms.api.controller;

import com.tms.api.data.dto.FeatureDto;
import com.tms.api.data.dto.StepDto;
import com.tms.api.model.feature.CreateFeatureRequest;
import com.tms.api.model.feature.FeatureResponse;
import com.tms.api.model.step.CreateStepRequest;
import com.tms.api.model.step.StepResponse;
import com.tms.api.service.StepService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/steps")
public class StepController {

    @Autowired
    private StepService service;
    @Autowired
    private ModelMapper mapper;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StepResponse> createStep(@Valid @RequestBody CreateStepRequest created) {
        StepDto dto = service.create(mapper.map(created, StepDto.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(dto, StepResponse.class));
    }
}
