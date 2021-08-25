package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.EvaluationCloture;
import com.pirtol.lab.repository.EvaluationClotureRepository;
import com.pirtol.lab.service.EvaluationClotureService;
import com.pirtol.lab.service.dto.EvaluationClotureDTO;
import com.pirtol.lab.service.mapper.EvaluationClotureMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EvaluationCloture}.
 */
@Service
@Transactional
public class EvaluationClotureServiceImpl implements EvaluationClotureService {

    private final Logger log = LoggerFactory.getLogger(EvaluationClotureServiceImpl.class);

    private final EvaluationClotureRepository evaluationClotureRepository;

    private final EvaluationClotureMapper evaluationClotureMapper;

    public EvaluationClotureServiceImpl(
        EvaluationClotureRepository evaluationClotureRepository,
        EvaluationClotureMapper evaluationClotureMapper
    ) {
        this.evaluationClotureRepository = evaluationClotureRepository;
        this.evaluationClotureMapper = evaluationClotureMapper;
    }

    @Override
    public EvaluationClotureDTO save(EvaluationClotureDTO evaluationClotureDTO) {
        log.debug("Request to save EvaluationCloture : {}", evaluationClotureDTO);
        EvaluationCloture evaluationCloture = evaluationClotureMapper.toEntity(evaluationClotureDTO);
        evaluationCloture = evaluationClotureRepository.save(evaluationCloture);
        return evaluationClotureMapper.toDto(evaluationCloture);
    }

    @Override
    public Optional<EvaluationClotureDTO> partialUpdate(EvaluationClotureDTO evaluationClotureDTO) {
        log.debug("Request to partially update EvaluationCloture : {}", evaluationClotureDTO);

        return evaluationClotureRepository
            .findById(evaluationClotureDTO.getId())
            .map(
                existingEvaluationCloture -> {
                    evaluationClotureMapper.partialUpdate(existingEvaluationCloture, evaluationClotureDTO);
                    return existingEvaluationCloture;
                }
            )
            .map(evaluationClotureRepository::save)
            .map(evaluationClotureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationClotureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EvaluationClotures");
        return evaluationClotureRepository.findAll(pageable).map(evaluationClotureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluationClotureDTO> findOne(Long id) {
        log.debug("Request to get EvaluationCloture : {}", id);
        return evaluationClotureRepository.findById(id).map(evaluationClotureMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EvaluationCloture : {}", id);
        evaluationClotureRepository.deleteById(id);
    }
}
