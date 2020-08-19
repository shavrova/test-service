package com.tms.api.model.step;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStepRequest {

    @NotNull
    private String stepId;

    @Size(min = 2, max = 100, message = "Step name must be between 2 and 100 characters")
    @NotNull(message = "Step name is required")
    private String stepName;

    private String comment;

    private String methodName;
}
