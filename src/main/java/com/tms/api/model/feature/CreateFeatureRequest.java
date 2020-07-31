package com.tms.api.model.feature;

import com.tms.api.data.entity.Scenario;
import com.tms.api.model.scenario.CreateScenarioRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
public class CreateFeatureRequest {

    @Size(min = 2, max = 50, message = "Scenario name must be between 2 and 50 characters")
    @NotEmpty(message = "Scenario name cannot be empty")
    private String featureName;

    private String featureDescription;

    private String className;

    private List<CreateScenarioRequest> scenarios;
}
