package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.CategorieNature;
import com.pirtol.lab.repository.CategorieNatureRepository;
import com.pirtol.lab.service.CategorieNatureService;
import com.pirtol.lab.service.dto.CategorieNatureDTO;
import com.pirtol.lab.service.mapper.CategorieNatureMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategorieNature}.
 */
@Service
@Transactional
public class CategorieNatureServiceImpl implements CategorieNatureService {

    private final Logger log = LoggerFactory.getLogger(CategorieNatureServiceImpl.class);

    private final CategorieNatureRepository categorieNatureRepository;

    private final CategorieNatureMapper categorieNatureMapper;

    public CategorieNatureServiceImpl(CategorieNatureRepository categorieNatureRepository, CategorieNatureMapper categorieNatureMapper) {
        this.categorieNatureRepository = categorieNatureRepository;
        this.categorieNatureMapper = categorieNatureMapper;
    }

    @Override
    public CategorieNatureDTO save(CategorieNatureDTO categorieNatureDTO) {
        log.debug("Request to save CategorieNature : {}", categorieNatureDTO);
        CategorieNature categorieNature = categorieNatureMapper.toEntity(categorieNatureDTO);
        categorieNature = categorieNatureRepository.save(categorieNature);
        return categorieNatureMapper.toDto(categorieNature);
    }

    @Override
    public Optional<CategorieNatureDTO> partialUpdate(CategorieNatureDTO categorieNatureDTO) {
        log.debug("Request to partially update CategorieNature : {}", categorieNatureDTO);

        return categorieNatureRepository
            .findById(categorieNatureDTO.getId())
            .map(
                existingCategorieNature -> {
                    categorieNatureMapper.partialUpdate(existingCategorieNature, categorieNatureDTO);
                    return existingCategorieNature;
                }
            )
            .map(categorieNatureRepository::save)
            .map(categorieNatureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategorieNatureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategorieNatures");
        return categorieNatureRepository.findAll(pageable).map(categorieNatureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategorieNatureDTO> findOne(Long id) {
        log.debug("Request to get CategorieNature : {}", id);
        return categorieNatureRepository.findById(id).map(categorieNatureMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategorieNature : {}", id);
        categorieNatureRepository.deleteById(id);
    }
}
