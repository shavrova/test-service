package com.tms.api.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class StepDto implements Serializable {
    private String stepId;

    private String stepName;

    private String comment;

    private String methodName;

    //TODO
    // private List<ScenarioDto> scenarios;
}
