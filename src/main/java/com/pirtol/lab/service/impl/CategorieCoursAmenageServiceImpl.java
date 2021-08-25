package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.CategorieCoursAmenage;
import com.pirtol.lab.repository.CategorieCoursAmenageRepository;
import com.pirtol.lab.service.CategorieCoursAmenageService;
import com.pirtol.lab.service.dto.CategorieCoursAmenageDTO;
import com.pirtol.lab.service.mapper.CategorieCoursAmenageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategorieCoursAmenage}.
 */
@Service
@Transactional
public class CategorieCoursAmenageServiceImpl implements CategorieCoursAmenageService {

    private final Logger log = LoggerFactory.getLogger(CategorieCoursAmenageServiceImpl.class);

    private final CategorieCoursAmenageRepository categorieCoursAmenageRepository;

    private final CategorieCoursAmenageMapper categorieCoursAmenageMapper;

    public CategorieCoursAmenageServiceImpl(
        CategorieCoursAmenageRepository categorieCoursAmenageRepository,
        CategorieCoursAmenageMapper categorieCoursAmenageMapper
    ) {
        this.categorieCoursAmenageRepository = categorieCoursAmenageRepository;
        this.categorieCoursAmenageMapper = categorieCoursAmenageMapper;
    }

    @Override
    public CategorieCoursAmenageDTO save(CategorieCoursAmenageDTO categorieCoursAmenageDTO) {
        log.debug("Request to save CategorieCoursAmenage : {}", categorieCoursAmenageDTO);
        CategorieCoursAmenage categorieCoursAmenage = categorieCoursAmenageMapper.toEntity(categorieCoursAmenageDTO);
        categorieCoursAmenage = categorieCoursAmenageRepository.save(categorieCoursAmenage);
        return categorieCoursAmenageMapper.toDto(categorieCoursAmenage);
    }

    @Override
    public Optional<CategorieCoursAmenageDTO> partialUpdate(CategorieCoursAmenageDTO categorieCoursAmenageDTO) {
        log.debug("Request to partially update CategorieCoursAmenage : {}", categorieCoursAmenageDTO);

        return categorieCoursAmenageRepository
            .findById(categorieCoursAmenageDTO.getId())
            .map(
                existingCategorieCoursAmenage -> {
                    categorieCoursAmenageMapper.partialUpdate(existingCategorieCoursAmenage, categorieCoursAmenageDTO);
                    return existingCategorieCoursAmenage;
                }
            )
            .map(categorieCoursAmenageRepository::save)
            .map(categorieCoursAmenageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategorieCoursAmenageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategorieCoursAmenages");
        return categorieCoursAmenageRepository.findAll(pageable).map(categorieCoursAmenageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategorieCoursAmenageDTO> findOne(Long id) {
        log.debug("Request to get CategorieCoursAmenage : {}", id);
        return categorieCoursAmenageRepository.findById(id).map(categorieCoursAmenageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategorieCoursAmenage : {}", id);
        categorieCoursAmenageRepository.deleteById(id);
    }
}
