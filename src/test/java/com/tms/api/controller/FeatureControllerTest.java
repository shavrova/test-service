package com.tms.api.controller;

import com.tms.api.data.dto.FeatureDto;
import com.tms.api.model.feature.CreateFeatureRequest;
import com.tms.api.model.feature.UpdateFeatureRequest;
import com.tms.api.service.FeatureService;
import com.tms.api.util.IdUtil;
import com.tms.api.util.Path;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.tms.api.util.ObjectUtil.*;
import static com.tms.api.util.ResourceUtil.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(FeatureController.class)
public class FeatureControllerTest {

    @Spy
    private ModelMapper mapper;

    @MockBean
    private FeatureService featureService;

    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    public void whenValidCreateFeatureRequestIsSend_thenFeatureResponseIsReturned() {
        CreateFeatureRequest createFeatureRequest = createFeatureRequest();
        FeatureDto featureDto = createFeatureDtoFromRequest(createFeatureRequest);

        when(featureService.create(any(FeatureDto.class))).thenReturn(featureDto);
        mockMvc.perform(post(Path.FEATURES)
                .content(asJsonString(createFeatureRequest()))
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.featureId").isString())
                .andExpect(jsonPath("$.featureName").value(createFeatureRequest.getFeatureName()))
                .andExpect(jsonPath("$.featureDescription").value(createFeatureRequest.getFeatureDescription()))
                .andExpect(jsonPath("$.className").value(createFeatureRequest.getClassName()))
                .andExpect(jsonPath("$.scenarios").exists());
    }

    @SneakyThrows
    @Test
    public void whenRequestWithoutFeatureNameIsSend_thenError() {
        CreateFeatureRequest createFeatureRequest = createFeatureRequest();
        createFeatureRequest.setFeatureName(null);
        FeatureDto featureDto = createFeatureDtoFromRequest(createFeatureRequest);
        ;

        when(featureService.create(any(FeatureDto.class))).thenReturn(featureDto);
        mockMvc.perform(post(Path.FEATURES)
                .content(asJsonString(createFeatureRequest))
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @SneakyThrows
    @Test
    public void whenValidUpdateFeatureRequestIsSend_thenSuccess() {
        UpdateFeatureRequest updateFeatureRequest = updateFeatureRequest();
        FeatureDto featureDto = createFeatureDtoFromRequest(updateFeatureRequest);

        when(featureService.create(any(FeatureDto.class))).thenReturn(featureDto);
        mockMvc.perform(post(Path.FEATURES)
                .content(asJsonString(updateFeatureRequest))
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.featureId").value(updateFeatureRequest.getFeatureId()))
                .andExpect(jsonPath("$.featureName").value(updateFeatureRequest.getFeatureName()))
                .andExpect(jsonPath("$.featureDescription").value(updateFeatureRequest.getFeatureDescription()))
                .andExpect(jsonPath("$.className").value(updateFeatureRequest.getClassName()))
                .andExpect(jsonPath("$.scenarios").exists());
    }

    @SneakyThrows
    @Test
    public void whenGetFeatureRequestIsSent_ThenFeatureReturned() {
        FeatureDto featureDto = createFeatureDtoObject();
        when(featureService.getById(any(String.class))).thenReturn(featureDto);
        mockMvc.perform(get(Path.FEATURES + "/" + featureDto.getFeatureId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.featureId").value(featureDto.getFeatureId()))
                .andExpect(jsonPath("$.featureName").value(featureDto.getFeatureName()))
                .andExpect(jsonPath("$.featureDescription").value(featureDto.getFeatureDescription()))
                .andExpect(jsonPath("$.className").value(featureDto.getClassName()))
                .andExpect(jsonPath("$.scenarios").exists());
    }

    @SneakyThrows
    @Test
    public void whenDeleteFeatureRequestIsSent_thenDeleted() {
        mockMvc.perform(delete(Path.FEATURES + "/someId"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private FeatureDto createFeatureDtoFromRequest(CreateFeatureRequest createFeatureRequest) {
        FeatureDto featureDto = mapper.map(createFeatureRequest, FeatureDto.class);
        featureDto.setFeatureId(IdUtil.uuid());
        featureDto.setScenarios(Collections.emptyList());
        return featureDto;
    }

    private FeatureDto createFeatureDtoFromRequest(UpdateFeatureRequest updateFeatureRequest) {
        FeatureDto featureDto = mapper.map(updateFeatureRequest, FeatureDto.class);
        featureDto.setScenarios(Collections.emptyList());
        return featureDto;
    }

}
