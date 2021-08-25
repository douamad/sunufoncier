package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.Dossier;
import com.pirtol.lab.repository.DossierRepository;
import com.pirtol.lab.service.DossierService;
import com.pirtol.lab.service.dto.DossierDTO;
import com.pirtol.lab.service.mapper.DossierMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Dossier}.
 */
@Service
@Transactional
public class DossierServiceImpl implements DossierService {

    private final Logger log = LoggerFactory.getLogger(DossierServiceImpl.class);

    private final DossierRepository dossierRepository;

    private final DossierMapper dossierMapper;

    public DossierServiceImpl(DossierRepository dossierRepository, DossierMapper dossierMapper) {
        this.dossierRepository = dossierRepository;
        this.dossierMapper = dossierMapper;
    }

    @Override
    public DossierDTO save(DossierDTO dossierDTO) {
        log.debug("Request to save Dossier : {}", dossierDTO);
        Dossier dossier = dossierMapper.toEntity(dossierDTO);
        dossier = dossierRepository.save(dossier);
        return dossierMapper.toDto(dossier);
    }

    @Override
    public Optional<DossierDTO> partialUpdate(DossierDTO dossierDTO) {
        log.debug("Request to partially update Dossier : {}", dossierDTO);

        return dossierRepository
            .findById(dossierDTO.getId())
            .map(
                existingDossier -> {
                    dossierMapper.partialUpdate(existingDossier, dossierDTO);
                    return existingDossier;
                }
            )
            .map(dossierRepository::save)
            .map(dossierMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DossierDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dossiers");
        return dossierRepository.findAll(pageable).map(dossierMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DossierDTO> findOne(Long id) {
        log.debug("Request to get Dossier : {}", id);
        return dossierRepository.findById(id).map(dossierMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dossier : {}", id);
        dossierRepository.deleteById(id);
    }
}
