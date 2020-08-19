package com.tms.api.data.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
