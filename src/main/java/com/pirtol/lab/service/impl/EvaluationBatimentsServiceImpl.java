package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.EvaluationBatiments;
import com.pirtol.lab.repository.EvaluationBatimentsRepository;
import com.pirtol.lab.service.EvaluationBatimentsService;
import com.pirtol.lab.service.dto.EvaluationBatimentsDTO;
import com.pirtol.lab.service.mapper.EvaluationBatimentsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EvaluationBatiments}.
 */
@Service
@Transactional
public class EvaluationBatimentsServiceImpl implements EvaluationBatimentsService {

    private final Logger log = LoggerFactory.getLogger(EvaluationBatimentsServiceImpl.class);

    private final EvaluationBatimentsRepository evaluationBatimentsRepository;

    private final EvaluationBatimentsMapper evaluationBatimentsMapper;

    public EvaluationBatimentsServiceImpl(
        EvaluationBatimentsRepository evaluationBatimentsRepository,
        EvaluationBatimentsMapper evaluationBatimentsMapper
    ) {
        this.evaluationBatimentsRepository = evaluationBatimentsRepository;
        this.evaluationBatimentsMapper = evaluationBatimentsMapper;
    }

    @Override
    public EvaluationBatimentsDTO save(EvaluationBatimentsDTO evaluationBatimentsDTO) {
        log.debug("Request to save EvaluationBatiments : {}", evaluationBatimentsDTO);
        EvaluationBatiments evaluationBatiments = evaluationBatimentsMapper.toEntity(evaluationBatimentsDTO);
        evaluationBatiments = evaluationBatimentsRepository.save(evaluationBatiments);
        return evaluationBatimentsMapper.toDto(evaluationBatiments);
    }

    @Override
    public Optional<EvaluationBatimentsDTO> partialUpdate(EvaluationBatimentsDTO evaluationBatimentsDTO) {
        log.debug("Request to partially update EvaluationBatiments : {}", evaluationBatimentsDTO);

        return evaluationBatimentsRepository
            .findById(evaluationBatimentsDTO.getId())
            .map(
                existingEvaluationBatiments -> {
                    evaluationBatimentsMapper.partialUpdate(existingEvaluationBatiments, evaluationBatimentsDTO);
                    return existingEvaluationBatiments;
                }
            )
            .map(evaluationBatimentsRepository::save)
            .map(evaluationBatimentsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationBatimentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EvaluationBatiments");
        return evaluationBatimentsRepository.findAll(pageable).map(evaluationBatimentsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluationBatimentsDTO> findOne(Long id) {
        log.debug("Request to get EvaluationBatiments : {}", id);
        return evaluationBatimentsRepository.findById(id).map(evaluationBatimentsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EvaluationBatiments : {}", id);
        evaluationBatimentsRepository.deleteById(id);
    }
}
