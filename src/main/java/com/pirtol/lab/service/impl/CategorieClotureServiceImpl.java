package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.CategorieCloture;
import com.pirtol.lab.repository.CategorieClotureRepository;
import com.pirtol.lab.service.CategorieClotureService;
import com.pirtol.lab.service.dto.CategorieClotureDTO;
import com.pirtol.lab.service.mapper.CategorieClotureMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategorieCloture}.
 */
@Service
@Transactional
public class CategorieClotureServiceImpl implements CategorieClotureService {

    private final Logger log = LoggerFactory.getLogger(CategorieClotureServiceImpl.class);

    private final CategorieClotureRepository categorieClotureRepository;

    private final CategorieClotureMapper categorieClotureMapper;

    public CategorieClotureServiceImpl(
        CategorieClotureRepository categorieClotureRepository,
        CategorieClotureMapper categorieClotureMapper
    ) {
        this.categorieClotureRepository = categorieClotureRepository;
        this.categorieClotureMapper = categorieClotureMapper;
    }

    @Override
    public CategorieClotureDTO save(CategorieClotureDTO categorieClotureDTO) {
        log.debug("Request to save CategorieCloture : {}", categorieClotureDTO);
        CategorieCloture categorieCloture = categorieClotureMapper.toEntity(categorieClotureDTO);
        categorieCloture = categorieClotureRepository.save(categorieCloture);
        return categorieClotureMapper.toDto(categorieCloture);
    }

    @Override
    public Optional<CategorieClotureDTO> partialUpdate(CategorieClotureDTO categorieClotureDTO) {
        log.debug("Request to partially update CategorieCloture : {}", categorieClotureDTO);

        return categorieClotureRepository
            .findById(categorieClotureDTO.getId())
            .map(
                existingCategorieCloture -> {
                    categorieClotureMapper.partialUpdate(existingCategorieCloture, categorieClotureDTO);
                    return existingCategorieCloture;
                }
            )
            .map(categorieClotureRepository::save)
            .map(categorieClotureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategorieClotureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategorieClotures");
        return categorieClotureRepository.findAll(pageable).map(categorieClotureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategorieClotureDTO> findOne(Long id) {
        log.debug("Request to get CategorieCloture : {}", id);
        return categorieClotureRepository.findById(id).map(categorieClotureMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategorieCloture : {}", id);
        categorieClotureRepository.deleteById(id);
    }
}
