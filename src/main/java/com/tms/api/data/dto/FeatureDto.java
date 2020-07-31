package com.tms.api.data.dto;

import com.tms.api.data.entity.Scenario;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class FeatureDto implements Serializable {

    private String featureId;

    private String featureName;

    private String featureDescription;

    private String className;

    private List<ScenarioDto> scenarios;
}
