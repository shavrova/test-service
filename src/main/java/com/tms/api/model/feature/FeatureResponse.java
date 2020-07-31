package com.tms.api.model.feature;

import com.tms.api.data.dto.ScenarioDto;
import com.tms.api.data.entity.Scenario;
import com.tms.api.model.scenario.ScenarioResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
public class FeatureResponse {

    private String featureId;

    private String featureName;
    @Size(max = 500, message = "Feature description should be less then 500 characters")
    private String featureDescription;

    private String className;

    //TODO maybe remove scenarios from response?
    private List<ScenarioResponse> scenarios;
}
