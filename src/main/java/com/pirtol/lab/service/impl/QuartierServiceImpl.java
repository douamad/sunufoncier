package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.Quartier;
import com.pirtol.lab.repository.QuartierRepository;
import com.pirtol.lab.service.QuartierService;
import com.pirtol.lab.service.dto.QuartierDTO;
import com.pirtol.lab.service.mapper.QuartierMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Quartier}.
 */
@Service
@Transactional
public class QuartierServiceImpl implements QuartierService {

    private final Logger log = LoggerFactory.getLogger(QuartierServiceImpl.class);

    private final QuartierRepository quartierRepository;

    private final QuartierMapper quartierMapper;

    public QuartierServiceImpl(QuartierRepository quartierRepository, QuartierMapper quartierMapper) {
        this.quartierRepository = quartierRepository;
        this.quartierMapper = quartierMapper;
    }

    @Override
    public QuartierDTO save(QuartierDTO quartierDTO) {
        log.debug("Request to save Quartier : {}", quartierDTO);
        Quartier quartier = quartierMapper.toEntity(quartierDTO);
        quartier = quartierRepository.save(quartier);
        return quartierMapper.toDto(quartier);
    }

    @Override
    public Optional<QuartierDTO> partialUpdate(QuartierDTO quartierDTO) {
        log.debug("Request to partially update Quartier : {}", quartierDTO);

        return quartierRepository
            .findById(quartierDTO.getId())
            .map(
                existingQuartier -> {
                    quartierMapper.partialUpdate(existingQuartier, quartierDTO);
                    return existingQuartier;
                }
            )
            .map(quartierRepository::save)
            .map(quartierMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuartierDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Quartiers");
        return quartierRepository.findAll(pageable).map(quartierMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuartierDTO> findOne(Long id) {
        log.debug("Request to get Quartier : {}", id);
        return quartierRepository.findById(id).map(quartierMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Quartier : {}", id);
        quartierRepository.deleteById(id);
    }
}
