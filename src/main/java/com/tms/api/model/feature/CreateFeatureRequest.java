package com.tms.api.model.feature;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateFeatureRequest {

    @Size(min = 2, max = 100, message = "Feature name must be between 2 and 100 characters")
    @NotNull(message = "Feature name is required")
    private String featureName;

    private String featureDescription;

    private String className;
    //Impossible to create feature with scenarios
    //private List<UpdateScenarioRequest> scenarios;
}
