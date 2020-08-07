package com.tms.api.model.scenario;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class UpdateScenarioRequest {

    @NotNull
    private String scenarioId;

    @Size(min = 2, max = 50, message = "Scenario name must be between 2 and 50 characters")
    @NotNull(message = "Scenario name is required")
    private String scenarioName;

    @Size(max = 500, message = "Scenario description should be less then 500 characters")
    private String scenarioDescription;

    private String featureId;

    //TODO
    //private List<StepR> steps;

}
