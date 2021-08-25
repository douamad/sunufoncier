package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EvaluationBatimentsMapperTest {

    private EvaluationBatimentsMapper evaluationBatimentsMapper;

    @BeforeEach
    public void setUp() {
        evaluationBatimentsMapper = new EvaluationBatimentsMapperImpl();
    }
}
