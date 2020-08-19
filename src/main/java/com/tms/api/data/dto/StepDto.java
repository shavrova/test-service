package com.tms.api.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
public class StepDto implements Serializable {
    private String stepId;

    private String stepName;

    private String comment;

    private String methodName;

    private Date CreatedAt;

    private Date UpdatedAt;

}
