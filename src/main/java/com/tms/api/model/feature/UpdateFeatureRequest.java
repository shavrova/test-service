package com.tms.api.model.feature;

import com.tms.api.data.dto.ScenarioDto;
import com.tms.api.model.scenario.UpdateScenarioRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
public class UpdateFeatureRequest {

    @NotNull
    private String featureId;

    @Size(min = 2, max = 100, message = "Scenario name must be between 2 and 100 characters")
    @NotEmpty(message = "Scenario name cannot be empty")
    private String featureName;

    private String featureDescription;

    private String className;

    private List<UpdateScenarioRequest> scenarios;
}

