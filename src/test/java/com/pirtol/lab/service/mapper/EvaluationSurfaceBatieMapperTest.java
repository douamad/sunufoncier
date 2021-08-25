package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EvaluationSurfaceBatieMapperTest {

    private EvaluationSurfaceBatieMapper evaluationSurfaceBatieMapper;

    @BeforeEach
    public void setUp() {
        evaluationSurfaceBatieMapper = new EvaluationSurfaceBatieMapperImpl();
    }
}
