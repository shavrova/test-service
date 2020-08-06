package com.tms.api.controller;

import com.tms.api.data.dto.StepDto;
import com.tms.api.model.step.CreateStepRequest;
import com.tms.api.model.step.StepResponse;
import com.tms.api.service.StepService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
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

    @GetMapping
    ResponseEntity<Page<StepResponse>> getSteps(Pageable page) {
        Page<StepDto> dtos = service.findPage(page);
        return ResponseEntity.ok(dtos.map(dto -> mapper.map(dto, StepResponse.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StepResponse> getStep(@PathVariable("id") String id) {
        return ResponseEntity.ok(mapper.map(service.getById(id), StepResponse.class));
    }
}
