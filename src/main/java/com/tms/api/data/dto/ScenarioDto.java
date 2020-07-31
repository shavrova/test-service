package com.tms.api.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tms.api.data.entity.Feature;
import com.tms.api.data.entity.Step;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScenarioDto implements Serializable {
    private String scenarioId;
    private String scenarioName;
    private String scenarioDescription;
    private Date createdAt;
    private String userId;
    private String featureId;
    private List<StepDto> steps;
}
