package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.Nature;
import com.pirtol.lab.repository.NatureRepository;
import com.pirtol.lab.service.NatureService;
import com.pirtol.lab.service.dto.NatureDTO;
import com.pirtol.lab.service.mapper.NatureMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Nature}.
 */
@Service
@Transactional
public class NatureServiceImpl implements NatureService {

    private final Logger log = LoggerFactory.getLogger(NatureServiceImpl.class);

    private final NatureRepository natureRepository;

    private final NatureMapper natureMapper;

    public NatureServiceImpl(NatureRepository natureRepository, NatureMapper natureMapper) {
        this.natureRepository = natureRepository;
        this.natureMapper = natureMapper;
    }

    @Override
    public NatureDTO save(NatureDTO natureDTO) {
        log.debug("Request to save Nature : {}", natureDTO);
        Nature nature = natureMapper.toEntity(natureDTO);
        nature = natureRepository.save(nature);
        return natureMapper.toDto(nature);
    }

    @Override
    public Optional<NatureDTO> partialUpdate(NatureDTO natureDTO) {
        log.debug("Request to partially update Nature : {}", natureDTO);

        return natureRepository
            .findById(natureDTO.getId())
            .map(
                existingNature -> {
                    natureMapper.partialUpdate(existingNature, natureDTO);
                    return existingNature;
                }
            )
            .map(natureRepository::save)
            .map(natureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NatureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Natures");
        return natureRepository.findAll(pageable).map(natureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NatureDTO> findOne(Long id) {
        log.debug("Request to get Nature : {}", id);
        return natureRepository.findById(id).map(natureMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nature : {}", id);
        natureRepository.deleteById(id);
    }
}
