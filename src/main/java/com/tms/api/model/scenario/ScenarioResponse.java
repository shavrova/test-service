package com.tms.api.model.scenario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tms.api.data.dto.StepDto;
import com.tms.api.data.entity.Step;
import com.tms.api.model.step.StepResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class ScenarioResponse {

    private String scenarioId;

    private String scenarioName;

    private String scenarioDescription;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date createdAt;

    private String userId;

    private String featureId;

    private List<StepResponse> steps;


}
