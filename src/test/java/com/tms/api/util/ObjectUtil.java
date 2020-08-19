package com.tms.api.util;

import com.tms.api.data.dto.FeatureDto;
import com.tms.api.data.entity.Feature;
import com.tms.api.data.entity.Scenario;
import com.tms.api.data.entity.Step;
import com.tms.api.model.feature.CreateFeatureRequest;
import com.tms.api.model.feature.UpdateFeatureRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class ObjectUtil {

    public static CreateFeatureRequest createFeatureRequest() {
        return CreateFeatureRequest.builder()
                .featureName(IdUtil.uuid())
                .featureDescription(IdUtil.uuid())
                .className(IdUtil.uuid())
                .build();
    }

    public static UpdateFeatureRequest updateFeatureRequest(String featureId) {
        return UpdateFeatureRequest.builder()
                .featureId(featureId)
                .featureName(IdUtil.uuid())
                .featureDescription(IdUtil.uuid())
                .className(IdUtil.uuid())
                .scenarios(Collections.emptyList())
                .build();
    }

    public static UpdateFeatureRequest updateFeatureRequest() {
        return UpdateFeatureRequest.builder()
                .featureId(IdUtil.uuid())
                .featureName(IdUtil.uuid())
                .featureDescription(IdUtil.uuid())
                .className(IdUtil.uuid())
                .scenarios(Collections.emptyList())
                .build();
    }

    public static Feature createFeatureObject() {
        return Feature.builder()
                .featureId(IdUtil.uuid())
                .featureName(IdUtil.uuid())
                .featureDescription(IdUtil.uuid())
                .className(IdUtil.uuid())
                .scenarios(Collections.emptyList())
                .build();
    }

    public static Feature createFeatureObjectWithScenario() {
        return Feature.builder()
                .featureId(IdUtil.uuid())
                .featureName(IdUtil.uuid())
                .featureDescription(IdUtil.uuid())
                .className(IdUtil.uuid())
                .scenarios(Collections.singletonList(createScenarioObject()))
                .build();
    }

    public static Scenario createScenarioObject(){
        return Scenario.builder()
                .scenarioId(IdUtil.uuid())
                .scenarioName(IdUtil.uuid())
                .scenarioDescription(IdUtil.uuid())
                .createdAt(new Date())
                .userId(IdUtil.uuid())
                .feature(createFeatureObject())
                .steps(Collections.emptyList())
                .build();
    }

    public static Scenario createScenarioObjectWithStep(){
        return Scenario.builder()
                .scenarioId(IdUtil.uuid())
                .scenarioName(IdUtil.uuid())
                .scenarioDescription(IdUtil.uuid())
                .createdAt(new Date())
                .userId(IdUtil.uuid())
                .feature(createFeatureObject())
                .steps(Collections.singletonList(createStepObject()))
                .build();
    }

    public static Step createStepObject(){
        return Step.builder()
                .stepId(IdUtil.uuid())
                .stepName(IdUtil.uuid())
                .comment(IdUtil.uuid())
                .methodName(IdUtil.uuid())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }



    public static FeatureDto createFeatureDtoObject() {
        return FeatureDto.builder()
                .featureId(IdUtil.uuid())
                .featureName(IdUtil.uuid())
                .featureDescription(IdUtil.uuid())
                .className(IdUtil.uuid())
                .scenarios(Collections.emptyList())
                .build();
    }

}
