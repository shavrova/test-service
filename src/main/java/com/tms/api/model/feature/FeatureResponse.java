package com.tms.api.model.feature;

import com.tms.api.model.scenario.ScenarioResponse;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class FeatureResponse {

    private String featureId;

    private String featureName;

    private String featureDescription;

    private String className;

    private List<ScenarioResponse> scenarios;
}
