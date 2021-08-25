package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.UsageDossier;
import com.pirtol.lab.repository.UsageDossierRepository;
import com.pirtol.lab.service.UsageDossierService;
import com.pirtol.lab.service.dto.UsageDossierDTO;
import com.pirtol.lab.service.mapper.UsageDossierMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UsageDossier}.
 */
@Service
@Transactional
public class UsageDossierServiceImpl implements UsageDossierService {

    private final Logger log = LoggerFactory.getLogger(UsageDossierServiceImpl.class);

    private final UsageDossierRepository usageDossierRepository;

    private final UsageDossierMapper usageDossierMapper;

    public UsageDossierServiceImpl(UsageDossierRepository usageDossierRepository, UsageDossierMapper usageDossierMapper) {
        this.usageDossierRepository = usageDossierRepository;
        this.usageDossierMapper = usageDossierMapper;
    }

    @Override
    public UsageDossierDTO save(UsageDossierDTO usageDossierDTO) {
        log.debug("Request to save UsageDossier : {}", usageDossierDTO);
        UsageDossier usageDossier = usageDossierMapper.toEntity(usageDossierDTO);
        usageDossier = usageDossierRepository.save(usageDossier);
        return usageDossierMapper.toDto(usageDossier);
    }

    @Override
    public Optional<UsageDossierDTO> partialUpdate(UsageDossierDTO usageDossierDTO) {
        log.debug("Request to partially update UsageDossier : {}", usageDossierDTO);

        return usageDossierRepository
            .findById(usageDossierDTO.getId())
            .map(
                existingUsageDossier -> {
                    usageDossierMapper.partialUpdate(existingUsageDossier, usageDossierDTO);
                    return existingUsageDossier;
                }
            )
            .map(usageDossierRepository::save)
            .map(usageDossierMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsageDossierDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UsageDossiers");
        return usageDossierRepository.findAll(pageable).map(usageDossierMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsageDossierDTO> findOne(Long id) {
        log.debug("Request to get UsageDossier : {}", id);
        return usageDossierRepository.findById(id).map(usageDossierMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UsageDossier : {}", id);
        usageDossierRepository.deleteById(id);
    }
}
