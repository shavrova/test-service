package com.tms.api.model.scenario;

import com.tms.api.model.step.CreateStepRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
public class CreateScenarioRequest {

    @Size(min = 2, max = 50, message = "Scenario name must be between 2 and 50 characters")
    @NotBlank(message = "Scenario name cannot be empty")
    private String scenarioName;

    @Size(max = 100, message = "Scenario description should be less then 1000 characters")
    private String scenarioDescription;

    private String featureId;

    private List<CreateStepRequest> steps;

}
