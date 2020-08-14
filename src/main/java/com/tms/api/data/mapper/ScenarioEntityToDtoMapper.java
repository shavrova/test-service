package com.tms.api.data.mapper;

import com.tms.api.data.dto.ScenarioDto;
import com.tms.api.data.entity.Feature;
import com.tms.api.data.entity.Scenario;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class ScenarioEntityToDtoMapper extends PropertyMap<Scenario, ScenarioDto> {

    private Converter<Feature, String> getFeatureId = context -> context.getSource().getFeatureId();


    @Override
    protected void configure() {
        using(getFeatureId).map(source.getFeature()).setFeatureId(null);
    }
}



