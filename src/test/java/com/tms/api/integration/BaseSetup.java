package com.tms.api.integration;

import com.tms.api.TestServiceApplication;
import com.tms.api.model.feature.CreateFeatureRequest;
import com.tms.api.model.feature.FeatureResponse;
import com.tms.api.model.feature.UpdateFeatureRequest;
import com.tms.api.model.scenario.CreateScenarioRequest;
import com.tms.api.model.scenario.ScenarioResponse;
import com.tms.api.model.step.CreateStepRequest;
import com.tms.api.model.step.StepResponse;
import com.tms.api.model.step.UpdateStepRequest;
import com.tms.api.util.IdUtil;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;

import static com.tms.api.util.ResourceUtil.asJsonString;
import static io.restassured.RestAssured.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Integration tests for feature endpoint
 *
 * @SpringBootTest creates application context
 * y default, SpringBootTest will not start the server.
 * But if we define the 'webEnvironment' property as
 * @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT OR WebEnvironment.DEFINED_PORT),
 * spring will load a WebServerApplicationContext providing a real web environment.
 */


@Slf4j
@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = TestServiceApplication.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseSetup {

    @LocalServerPort
    int serverPort;

    String FEATURES_URL;
    String SCENARIOS_URL;
    String STEPS_URL;

    @Autowired
    RestTemplateUtil restTemplateUtil;

    @BeforeAll
    public void initRestAssured() {
        port = serverPort;
        baseURI = DEFAULT_URI;
        basePath = "/tests-ws";
        FEATURES_URL = baseURI + ":" + port + basePath + "/features";
        SCENARIOS_URL = FEATURES_URL + "/scenarios";
        STEPS_URL = baseURI + ":" + port + basePath + "/steps";
        log.info("Constructing features URL:  FEATURES_URL = " + FEATURES_URL);
        filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }


    public StepResponse createStep() {
        CreateStepRequest createStepRequest = CreateStepRequest.builder()
                .stepName(IdUtil.uuid())
                .comment(IdUtil.uuid())
                .methodName(IdUtil.uuid())
                .build();
        log.info("CreateStepRequest : " + createStepRequest);
        StepResponse stepResponse = restTemplateUtil.create(STEPS_URL, asJsonString(createStepRequest), StepResponse.class);
        log.info("StepResponse : " + stepResponse);
        return stepResponse;
    }

    public ScenarioResponse createScenario(String featureId) {
        CreateScenarioRequest createScenarioRequest = CreateScenarioRequest.builder()
                .scenarioName(IdUtil.uuid())
                .scenarioDescription(IdUtil.uuid())
                .featureId(featureId)
                .build();
        log.info("CreateScenarioRequest : " + createScenarioRequest);
        ScenarioResponse scenarioResponse = restTemplateUtil.create(SCENARIOS_URL, asJsonString(createScenarioRequest), ScenarioResponse.class);
        log.info("ScenarioResponse : " + scenarioResponse);
        return scenarioResponse;
    }

    public ScenarioResponse createScenario() {
        FeatureResponse featureResponse = createFeature();
        CreateScenarioRequest createScenarioRequest = CreateScenarioRequest.builder()
                .scenarioName(IdUtil.uuid())
                .scenarioDescription(IdUtil.uuid())
                .featureId(featureResponse.getFeatureId())
                .build();
        log.info("CreateScenarioRequest : " + createScenarioRequest);
        ScenarioResponse scenarioResponse = restTemplateUtil.create(SCENARIOS_URL, asJsonString(createScenarioRequest), ScenarioResponse.class);
        log.info("ScenarioResponse : " + scenarioResponse);
        return scenarioResponse;
    }

    public FeatureResponse createFeature() {
        CreateFeatureRequest createFeatureRequest = CreateFeatureRequest.builder()
                .featureName(IdUtil.uuid())
                .featureDescription(IdUtil.uuid())
                .className(IdUtil.uuid())
                .build();
        log.info("CreateFeatureRequest : " + createFeatureRequest);
        FeatureResponse featureResponse = restTemplateUtil.create(FEATURES_URL, asJsonString(createFeatureRequest), FeatureResponse.class);
        log.info("FeatureResponse : " + featureResponse);
        return featureResponse;
    }

    public FeatureResponse createFeatureWithScenario() {
        FeatureResponse featureResponse = createFeature();
        createScenario(featureResponse.getFeatureId());
        return featureResponse;
    }

    public ScenarioResponse createScenarioWithStep() {
        UpdateStepRequest updateStepRequest = UpdateStepRequest.builder()
                .stepId(createStep().getStepId())
                .stepName(IdUtil.uuid())
                .comment(IdUtil.uuid())
                .methodName(IdUtil.uuid())
                .build();
        log.info("UpdateStepRequest : " + updateStepRequest);
        CreateScenarioRequest createScenarioRequest = CreateScenarioRequest.builder()
                .scenarioName(IdUtil.uuid())
                .scenarioDescription(IdUtil.uuid())
                .featureId(createFeature().getFeatureId())
                .steps(Collections.singletonList(updateStepRequest))
                .build();
        log.info("CreateScenarioRequest : " + createScenarioRequest);
        ScenarioResponse scenarioResponse = restTemplateUtil.create(SCENARIOS_URL, asJsonString(createScenarioRequest), ScenarioResponse.class);
        log.info("ScenarioResponse : " + scenarioResponse);
        return scenarioResponse;
    }
}
