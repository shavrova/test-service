package com.tms.api.controller;

import com.tms.api.data.dto.FeatureDto;
import com.tms.api.model.feature.CreateFeatureRequest;
import com.tms.api.model.feature.FeatureResponse;
import com.tms.api.model.feature.UpdateFeatureRequest;
import com.tms.api.service.FeatureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/features")
public class FeatureController {

    @Autowired
    private FeatureService service;

    @Autowired
    private ModelMapper mapper;


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FeatureResponse> createFeature(@Valid @RequestBody CreateFeatureRequest created) {
        FeatureDto dto = service.create(mapper.map(created, FeatureDto.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(dto, FeatureResponse.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeatureResponse> getFeature(@PathVariable("id") String id) {
        return ResponseEntity.ok(mapper.map(service.getById(id), FeatureResponse.class));
    }

    @PutMapping
    public ResponseEntity<FeatureResponse> updateFeature(@Valid @RequestBody UpdateFeatureRequest updated) {
        FeatureDto dto = service.update(mapper.map(updated, FeatureDto.class));
        return ResponseEntity.status(HttpStatus.OK).body(mapper.map(dto, FeatureResponse.class));
    }

    @GetMapping
    ResponseEntity<Page<FeatureResponse>> getFeatures(Pageable page) {
        Page<FeatureDto> dtos = service.findPage(page);
        return ResponseEntity.ok(dtos.map(dto -> mapper.map(dto, FeatureResponse.class)));
    }



    //TODO: add POST to update all features in single request (e.g update/add/remove all data)  /updateAll ?
    //TODO: and test it!

}
