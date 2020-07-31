package com.tms.api.service;

import com.tms.api.data.dto.ScenarioDto;

import java.util.List;

public interface ScenarioService extends CrudService<ScenarioDto, String> {

    ScenarioDto createScenarioByUser(ScenarioDto dto, String userId);

    List<ScenarioDto> getUserScenarios(String userId);

}
