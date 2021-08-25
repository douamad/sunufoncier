package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.EvaluationSurfaceBatie;
import com.pirtol.lab.repository.EvaluationSurfaceBatieRepository;
import com.pirtol.lab.service.EvaluationSurfaceBatieService;
import com.pirtol.lab.service.dto.EvaluationSurfaceBatieDTO;
import com.pirtol.lab.service.mapper.EvaluationSurfaceBatieMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EvaluationSurfaceBatie}.
 */
@Service
@Transactional
public class EvaluationSurfaceBatieServiceImpl implements EvaluationSurfaceBatieService {

    private final Logger log = LoggerFactory.getLogger(EvaluationSurfaceBatieServiceImpl.class);

    private final EvaluationSurfaceBatieRepository evaluationSurfaceBatieRepository;

    private final EvaluationSurfaceBatieMapper evaluationSurfaceBatieMapper;

    public EvaluationSurfaceBatieServiceImpl(
        EvaluationSurfaceBatieRepository evaluationSurfaceBatieRepository,
        EvaluationSurfaceBatieMapper evaluationSurfaceBatieMapper
    ) {
        this.evaluationSurfaceBatieRepository = evaluationSurfaceBatieRepository;
        this.evaluationSurfaceBatieMapper = evaluationSurfaceBatieMapper;
    }

    @Override
    public EvaluationSurfaceBatieDTO save(EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO) {
        log.debug("Request to save EvaluationSurfaceBatie : {}", evaluationSurfaceBatieDTO);
        EvaluationSurfaceBatie evaluationSurfaceBatie = evaluationSurfaceBatieMapper.toEntity(evaluationSurfaceBatieDTO);
        evaluationSurfaceBatie = evaluationSurfaceBatieRepository.save(evaluationSurfaceBatie);
        return evaluationSurfaceBatieMapper.toDto(evaluationSurfaceBatie);
    }

    @Override
    public Optional<EvaluationSurfaceBatieDTO> partialUpdate(EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO) {
        log.debug("Request to partially update EvaluationSurfaceBatie : {}", evaluationSurfaceBatieDTO);

        return evaluationSurfaceBatieRepository
            .findById(evaluationSurfaceBatieDTO.getId())
            .map(
                existingEvaluationSurfaceBatie -> {
                    evaluationSurfaceBatieMapper.partialUpdate(existingEvaluationSurfaceBatie, evaluationSurfaceBatieDTO);
                    return existingEvaluationSurfaceBatie;
                }
            )
            .map(evaluationSurfaceBatieRepository::save)
            .map(evaluationSurfaceBatieMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationSurfaceBatieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EvaluationSurfaceBaties");
        return evaluationSurfaceBatieRepository.findAll(pageable).map(evaluationSurfaceBatieMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluationSurfaceBatieDTO> findOne(Long id) {
        log.debug("Request to get EvaluationSurfaceBatie : {}", id);
        return evaluationSurfaceBatieRepository.findById(id).map(evaluationSurfaceBatieMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EvaluationSurfaceBatie : {}", id);
        evaluationSurfaceBatieRepository.deleteById(id);
    }
}
