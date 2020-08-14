package com.tms.api.model.feature;

import com.tms.api.model.scenario.CreateScenarioRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
public class CreateFeatureRequest {

    @Size(min = 2, max = 100, message = "Feature name must be between 2 and 100 characters")
    @NotNull(message = "Feature name is required")
    private String featureName;

    private String featureDescription;

    private String className;

    private List<CreateScenarioRequest> scenarios;
}
