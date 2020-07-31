package com.tms.api.model.step;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class CreateStepRequest {

    @Size(min = 2, max = 50, message = "Step name must be between 2 and 50 characters")
    @NotBlank(message = "Step name cannot be empty")
    private String stepName;

    private String comment;

    private String methodName;
}
