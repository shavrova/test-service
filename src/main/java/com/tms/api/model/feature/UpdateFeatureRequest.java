package com.tms.api.model.feature;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class UpdateFeatureRequest {

    @NotNull
    private String featureId;

    @Size(min = 2, max = 50, message = "Scenario name must be between 2 and 50 characters")
    @NotEmpty(message = "Scenario name cannot be empty")
    private String featureName;

    private String featureDescription;

    private String className;

    //private List<Scenario> scenarios;
}

