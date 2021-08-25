package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.EvaluationCoursAmenage;
import com.pirtol.lab.repository.EvaluationCoursAmenageRepository;
import com.pirtol.lab.service.EvaluationCoursAmenageService;
import com.pirtol.lab.service.dto.EvaluationCoursAmenageDTO;
import com.pirtol.lab.service.mapper.EvaluationCoursAmenageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EvaluationCoursAmenage}.
 */
@Service
@Transactional
public class EvaluationCoursAmenageServiceImpl implements EvaluationCoursAmenageService {

    private final Logger log = LoggerFactory.getLogger(EvaluationCoursAmenageServiceImpl.class);

    private final EvaluationCoursAmenageRepository evaluationCoursAmenageRepository;

    private final EvaluationCoursAmenageMapper evaluationCoursAmenageMapper;

    public EvaluationCoursAmenageServiceImpl(
        EvaluationCoursAmenageRepository evaluationCoursAmenageRepository,
        EvaluationCoursAmenageMapper evaluationCoursAmenageMapper
    ) {
        this.evaluationCoursAmenageRepository = evaluationCoursAmenageRepository;
        this.evaluationCoursAmenageMapper = evaluationCoursAmenageMapper;
    }

    @Override
    public EvaluationCoursAmenageDTO save(EvaluationCoursAmenageDTO evaluationCoursAmenageDTO) {
        log.debug("Request to save EvaluationCoursAmenage : {}", evaluationCoursAmenageDTO);
        EvaluationCoursAmenage evaluationCoursAmenage = evaluationCoursAmenageMapper.toEntity(evaluationCoursAmenageDTO);
        evaluationCoursAmenage = evaluationCoursAmenageRepository.save(evaluationCoursAmenage);
        return evaluationCoursAmenageMapper.toDto(evaluationCoursAmenage);
    }

    @Override
    public Optional<EvaluationCoursAmenageDTO> partialUpdate(EvaluationCoursAmenageDTO evaluationCoursAmenageDTO) {
        log.debug("Request to partially update EvaluationCoursAmenage : {}", evaluationCoursAmenageDTO);

        return evaluationCoursAmenageRepository
            .findById(evaluationCoursAmenageDTO.getId())
            .map(
                existingEvaluationCoursAmenage -> {
                    evaluationCoursAmenageMapper.partialUpdate(existingEvaluationCoursAmenage, evaluationCoursAmenageDTO);
                    return existingEvaluationCoursAmenage;
                }
            )
            .map(evaluationCoursAmenageRepository::save)
            .map(evaluationCoursAmenageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationCoursAmenageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EvaluationCoursAmenages");
        return evaluationCoursAmenageRepository.findAll(pageable).map(evaluationCoursAmenageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluationCoursAmenageDTO> findOne(Long id) {
        log.debug("Request to get EvaluationCoursAmenage : {}", id);
        return evaluationCoursAmenageRepository.findById(id).map(evaluationCoursAmenageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EvaluationCoursAmenage : {}", id);
        evaluationCoursAmenageRepository.deleteById(id);
    }
}
