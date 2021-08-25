package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EvaluationClotureMapperTest {

    private EvaluationClotureMapper evaluationClotureMapper;

    @BeforeEach
    public void setUp() {
        evaluationClotureMapper = new EvaluationClotureMapperImpl();
    }
}
