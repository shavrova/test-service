package com.tms.api.model.scenario;

import com.tms.api.model.step.UpdateStepRequest;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateScenarioRequest {

    @Size(min = 2, max = 100, message = "Scenario name must be between 2 and 100 characters")
    @NotNull(message = "Scenario name is required")
    private String scenarioName;

    @Size(max = 500, message = "Scenario description should be less then 500 characters")
    private String scenarioDescription;

    private String featureId;

    private List<UpdateStepRequest> steps;

}
