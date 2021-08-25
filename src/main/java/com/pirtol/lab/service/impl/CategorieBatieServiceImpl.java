package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.CategorieBatie;
import com.pirtol.lab.repository.CategorieBatieRepository;
import com.pirtol.lab.service.CategorieBatieService;
import com.pirtol.lab.service.dto.CategorieBatieDTO;
import com.pirtol.lab.service.mapper.CategorieBatieMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategorieBatie}.
 */
@Service
@Transactional
public class CategorieBatieServiceImpl implements CategorieBatieService {

    private final Logger log = LoggerFactory.getLogger(CategorieBatieServiceImpl.class);

    private final CategorieBatieRepository categorieBatieRepository;

    private final CategorieBatieMapper categorieBatieMapper;

    public CategorieBatieServiceImpl(CategorieBatieRepository categorieBatieRepository, CategorieBatieMapper categorieBatieMapper) {
        this.categorieBatieRepository = categorieBatieRepository;
        this.categorieBatieMapper = categorieBatieMapper;
    }

    @Override
    public CategorieBatieDTO save(CategorieBatieDTO categorieBatieDTO) {
        log.debug("Request to save CategorieBatie : {}", categorieBatieDTO);
        CategorieBatie categorieBatie = categorieBatieMapper.toEntity(categorieBatieDTO);
        categorieBatie = categorieBatieRepository.save(categorieBatie);
        return categorieBatieMapper.toDto(categorieBatie);
    }

    @Override
    public Optional<CategorieBatieDTO> partialUpdate(CategorieBatieDTO categorieBatieDTO) {
        log.debug("Request to partially update CategorieBatie : {}", categorieBatieDTO);

        return categorieBatieRepository
            .findById(categorieBatieDTO.getId())
            .map(
                existingCategorieBatie -> {
                    categorieBatieMapper.partialUpdate(existingCategorieBatie, categorieBatieDTO);
                    return existingCategorieBatie;
                }
            )
            .map(categorieBatieRepository::save)
            .map(categorieBatieMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategorieBatieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategorieBaties");
        return categorieBatieRepository.findAll(pageable).map(categorieBatieMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategorieBatieDTO> findOne(Long id) {
        log.debug("Request to get CategorieBatie : {}", id);
        return categorieBatieRepository.findById(id).map(categorieBatieMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategorieBatie : {}", id);
        categorieBatieRepository.deleteById(id);
    }
}
