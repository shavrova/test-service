package com.tms.api.model.feature;

import com.tms.api.data.dto.ScenarioDto;
import com.tms.api.data.entity.Scenario;
import com.tms.api.model.scenario.ScenarioResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
public class FeatureResponse {

    private String featureId;

    @NotBlank(message = "Feature name cannot be empty")
    private String featureName;

    private String featureDescription;

    private String className;

    //TODO maybe remove scenarios from response?
    private List<ScenarioResponse> scenarios;
}
