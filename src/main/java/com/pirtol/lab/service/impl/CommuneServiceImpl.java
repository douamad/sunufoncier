package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.Commune;
import com.pirtol.lab.repository.CommuneRepository;
import com.pirtol.lab.service.CommuneService;
import com.pirtol.lab.service.dto.CommuneDTO;
import com.pirtol.lab.service.mapper.CommuneMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Commune}.
 */
@Service
@Transactional
public class CommuneServiceImpl implements CommuneService {

    private final Logger log = LoggerFactory.getLogger(CommuneServiceImpl.class);

    private final CommuneRepository communeRepository;

    private final CommuneMapper communeMapper;

    public CommuneServiceImpl(CommuneRepository communeRepository, CommuneMapper communeMapper) {
        this.communeRepository = communeRepository;
        this.communeMapper = communeMapper;
    }

    @Override
    public CommuneDTO save(CommuneDTO communeDTO) {
        log.debug("Request to save Commune : {}", communeDTO);
        Commune commune = communeMapper.toEntity(communeDTO);
        commune = communeRepository.save(commune);
        return communeMapper.toDto(commune);
    }

    @Override
    public Optional<CommuneDTO> partialUpdate(CommuneDTO communeDTO) {
        log.debug("Request to partially update Commune : {}", communeDTO);

        return communeRepository
            .findById(communeDTO.getId())
            .map(
                existingCommune -> {
                    communeMapper.partialUpdate(existingCommune, communeDTO);
                    return existingCommune;
                }
            )
            .map(communeRepository::save)
            .map(communeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommuneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Communes");
        return communeRepository.findAll(pageable).map(communeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommuneDTO> findOne(Long id) {
        log.debug("Request to get Commune : {}", id);
        return communeRepository.findById(id).map(communeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Commune : {}", id);
        communeRepository.deleteById(id);
    }
}
