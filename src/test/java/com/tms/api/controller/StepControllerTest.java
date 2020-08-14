package com.tms.api.controller;

import com.tms.api.model.feature.FeatureResponse;
import com.tms.api.service.StepService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(StepController.class)
public class StepControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StepService service;

    private FeatureResponse response;

    @BeforeTestMethod
    public void before() {
        response = FeatureResponse.builder().featureId("id")
                .featureName("Feature Name")
                .featureDescription("Description")
                .build();
    }

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        when(any()).thenReturn(response);
        this.mockMvc.perform(get("/features")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(content().string(containsString("Feature Name")))
                .andExpect(content().string(containsString("Description")));
    }
}