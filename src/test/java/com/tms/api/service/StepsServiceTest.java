package com.tms.api.service;

import com.tms.api.data.repository.FeatureRepository;
import com.tms.api.data.repository.ScenarioRepository;
import com.tms.api.data.repository.StepRepository;
import com.tms.api.service.impl.ScenarioServiceImpl;
import com.tms.api.util.PropertyUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

//@ExtendWith(SpringExtension.class)
//@DataJpaTest
@ContextConfiguration(classes = PropertyUtil.class)
@TestPropertySource
@ExtendWith(MockitoExtension.class)
public class StepsServiceTest {

    @Autowired
    PropertyUtil propertyUtil;

    @Mock
    ScenarioRepository scenarioRepository;

    @Mock
    FeatureRepository featureRepository;

    @Mock
    StepRepository stepRepository;

    @InjectMocks
    ScenarioServiceImpl scenarioService;

    @Value("${spring.datasource.username}")
    private String username;


}
