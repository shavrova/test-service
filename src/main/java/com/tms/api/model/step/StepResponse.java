package com.tms.api.model.step;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StepResponse {

    private String stepId;

    private String stepName;

    private String comment;

    private String methodName;
}
