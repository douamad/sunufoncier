package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EvaluationCoursAmenageMapperTest {

    private EvaluationCoursAmenageMapper evaluationCoursAmenageMapper;

    @BeforeEach
    public void setUp() {
        evaluationCoursAmenageMapper = new EvaluationCoursAmenageMapperImpl();
    }
}
