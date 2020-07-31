package com.tms.api.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tms.api.data.entity.Scenario;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class StepDto implements Serializable {
    private String stepId;

    private String stepName;

    private String comment;

    private String methodName;

    private List<Scenario> scenarios;
}
