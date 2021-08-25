package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.Proprietaire;
import com.pirtol.lab.repository.ProprietaireRepository;
import com.pirtol.lab.service.ProprietaireService;
import com.pirtol.lab.service.dto.ProprietaireDTO;
import com.pirtol.lab.service.mapper.ProprietaireMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Proprietaire}.
 */
@Service
@Transactional
public class ProprietaireServiceImpl implements ProprietaireService {

    private final Logger log = LoggerFactory.getLogger(ProprietaireServiceImpl.class);

    private final ProprietaireRepository proprietaireRepository;

    private final ProprietaireMapper proprietaireMapper;

    public ProprietaireServiceImpl(ProprietaireRepository proprietaireRepository, ProprietaireMapper proprietaireMapper) {
        this.proprietaireRepository = proprietaireRepository;
        this.proprietaireMapper = proprietaireMapper;
    }

    @Override
    public ProprietaireDTO save(ProprietaireDTO proprietaireDTO) {
        log.debug("Request to save Proprietaire : {}", proprietaireDTO);
        Proprietaire proprietaire = proprietaireMapper.toEntity(proprietaireDTO);
        proprietaire = proprietaireRepository.save(proprietaire);
        return proprietaireMapper.toDto(proprietaire);
    }

    @Override
    public Optional<ProprietaireDTO> partialUpdate(ProprietaireDTO proprietaireDTO) {
        log.debug("Request to partially update Proprietaire : {}", proprietaireDTO);

        return proprietaireRepository
            .findById(proprietaireDTO.getId())
            .map(
                existingProprietaire -> {
                    proprietaireMapper.partialUpdate(existingProprietaire, proprietaireDTO);
                    return existingProprietaire;
                }
            )
            .map(proprietaireRepository::save)
            .map(proprietaireMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProprietaireDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Proprietaires");
        return proprietaireRepository.findAll(pageable).map(proprietaireMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProprietaireDTO> findOne(Long id) {
        log.debug("Request to get Proprietaire : {}", id);
        return proprietaireRepository.findById(id).map(proprietaireMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Proprietaire : {}", id);
        proprietaireRepository.deleteById(id);
    }
}
