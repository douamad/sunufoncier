package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.Arrondissement;
import com.pirtol.lab.repository.ArrondissementRepository;
import com.pirtol.lab.service.ArrondissementService;
import com.pirtol.lab.service.dto.ArrondissementDTO;
import com.pirtol.lab.service.mapper.ArrondissementMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Arrondissement}.
 */
@Service
@Transactional
public class ArrondissementServiceImpl implements ArrondissementService {

    private final Logger log = LoggerFactory.getLogger(ArrondissementServiceImpl.class);

    private final ArrondissementRepository arrondissementRepository;

    private final ArrondissementMapper arrondissementMapper;

    public ArrondissementServiceImpl(ArrondissementRepository arrondissementRepository, ArrondissementMapper arrondissementMapper) {
        this.arrondissementRepository = arrondissementRepository;
        this.arrondissementMapper = arrondissementMapper;
    }

    @Override
    public ArrondissementDTO save(ArrondissementDTO arrondissementDTO) {
        log.debug("Request to save Arrondissement : {}", arrondissementDTO);
        Arrondissement arrondissement = arrondissementMapper.toEntity(arrondissementDTO);
        arrondissement = arrondissementRepository.save(arrondissement);
        return arrondissementMapper.toDto(arrondissement);
    }

    @Override
    public Optional<ArrondissementDTO> partialUpdate(ArrondissementDTO arrondissementDTO) {
        log.debug("Request to partially update Arrondissement : {}", arrondissementDTO);

        return arrondissementRepository
            .findById(arrondissementDTO.getId())
            .map(
                existingArrondissement -> {
                    arrondissementMapper.partialUpdate(existingArrondissement, arrondissementDTO);
                    return existingArrondissement;
                }
            )
            .map(arrondissementRepository::save)
            .map(arrondissementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArrondissementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Arrondissements");
        return arrondissementRepository.findAll(pageable).map(arrondissementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArrondissementDTO> findOne(Long id) {
        log.debug("Request to get Arrondissement : {}", id);
        return arrondissementRepository.findById(id).map(arrondissementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Arrondissement : {}", id);
        arrondissementRepository.deleteById(id);
    }
}
