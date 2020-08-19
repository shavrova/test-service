package com.tms.api.integration;

import com.tms.api.model.feature.CreateFeatureRequest;
import com.tms.api.model.feature.FeatureResponse;
import com.tms.api.model.feature.UpdateFeatureRequest;
import com.tms.api.model.scenario.ScenarioResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.tms.api.util.ObjectUtil.createFeatureRequest;
import static com.tms.api.util.ObjectUtil.updateFeatureRequest;
import static com.tms.api.util.ResourceUtil.asJsonString;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsMapContaining.hasKey;

@Slf4j
public class FeatureIntegrationTest extends BaseSetup {

    @Test
    public void whenPostFeature_thenAllFieldsPresentInResponse() {
        CreateFeatureRequest createFeatureRequest = createFeatureRequest();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(asJsonString(createFeatureRequest))
                .when()
                .post("/features")
                .then()
                .assertThat().statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("featureId"))
                .body("featureName", equalTo(createFeatureRequest.getFeatureName()))
                .body("featureDescription", equalTo(createFeatureRequest.getFeatureDescription()))
                .body("className", equalTo(createFeatureRequest.getClassName()))
                .body("$", hasKey("scenarios"));
    }

    @Test
    public void whenPutFeature_thenFieldsUpdated() {
        FeatureResponse featureResponse = createFeature();
        UpdateFeatureRequest updateFeatureRequest = updateFeatureRequest(featureResponse.getFeatureId());

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(asJsonString(updateFeatureRequest))
                .when()
                .put("/features")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value())
                .body("featureName", equalTo(updateFeatureRequest.getFeatureName()))
                .body("featureDescription", equalTo(updateFeatureRequest.getFeatureDescription()))
                .body("className", equalTo(updateFeatureRequest.getClassName()));
    }

    @Test
    /**
     * In this test feature will be posted with list of scenarios
     * Test will check that scenarios[] is empty in response
     */
    public void whenPostFeature_thenScenariosIsEmpty() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(asJsonString(createFeatureRequest()))
                .when()
                .post("/features")
                .then()
                .assertThat().statusCode(HttpStatus.CREATED.value())
                .body("scenarios", is(empty()));
    }

    @Test
    /**
     * Precondition: Create feature with scenario
     * When: update feature with empty list of scenarios
     * Then: Feature scenarios remain same
     */
    public void whenPostFeature_thenScenariosDoesNotUpdated() {
        FeatureResponse featureResponse = createFeatureWithScenario();
        UpdateFeatureRequest updateFeatureRequest = updateFeatureRequest(featureResponse.getFeatureId());

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(asJsonString(updateFeatureRequest))
                .when()
                .put("/features")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value())
                .body("scenarios[0]", hasKey("scenarioId"));
    }

    @Test
    /**
     * Precondition: create feature
     * When: get feature by id
     * Then: created feature is returned
     */
    public void whenGetFeatureById_thenFeatureReturned() {
        FeatureResponse featureResponse = createFeature();
        given()
                .get("/features/" + featureResponse.getFeatureId())
                .then()
                .assertThat().statusCode(HttpStatus.OK.value())
                .body("featureId", equalTo(featureResponse.getFeatureId()))
                .body("featureName", equalTo(featureResponse.getFeatureName()))
                .body("featureDescription", equalTo(featureResponse.getFeatureDescription()))
                .body("className", equalTo(featureResponse.getClassName()));
    }


    @Test
    /**
     * Precondition: create feature
     * And: create scenario with steps and reference to created feature
     * And: set to scenario feature id
     * When delete feature
     * Then: scenario(s) should be deleted
     * And: steps shouldn't be deleted
     */
    public void whenDeleteFeature_ThenFeatureScenariosDeleted_AndStepsRemains() {
        ScenarioResponse scenarioResponse = createScenarioWithStep();
        //Delete feature
        given()
                .delete("/features/" + scenarioResponse.getFeatureId())
                .then()
                .assertThat().statusCode(HttpStatus.NO_CONTENT.value());
        //Assert feature deleted
        get("/features/" + scenarioResponse.getFeatureId())
                .then()
                .assertThat().statusCode(HttpStatus.NOT_FOUND.value());
        //Scenario was deleted
        get("/features/scenarios/" + scenarioResponse.getScenarioId())
                .then()
                .assertThat().statusCode(HttpStatus.NOT_FOUND.value());
        //Step wasn't deleted
        get("/steps/" + scenarioResponse.getSteps().get(0).getStepId())
                .then()
                .assertThat().statusCode(HttpStatus.OK.value());
    }


}


