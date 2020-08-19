package com.tms.api.controller;

import com.tms.api.data.dto.StepDto;
import com.tms.api.model.step.CreateStepRequest;
import com.tms.api.model.step.StepResponse;
import com.tms.api.model.step.UpdateStepRequest;
import com.tms.api.service.StepService;
import com.tms.api.util.Path;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(Path.STEPS)
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

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StepResponse> updateStep(@Valid @RequestBody UpdateStepRequest updated) {
        StepDto dto = service.update(mapper.map(updated, StepDto.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(dto, StepResponse.class));
    }

    @GetMapping
    ResponseEntity<Page<StepResponse>> getSteps(@PageableDefault(size = 40) Pageable page) {
        Page<StepDto> dtos = service.findPage(page);
        return ResponseEntity.ok(dtos.map(dto -> mapper.map(dto, StepResponse.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StepResponse> getStep(@PathVariable("id") String id) {
        return ResponseEntity.ok(mapper.map(service.getById(id), StepResponse.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStep(@PathVariable("id") String id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
