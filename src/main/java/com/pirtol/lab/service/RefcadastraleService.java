package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.RefcadastraleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.Refcadastrale}.
 */
public interface RefcadastraleService {
    /**
     * Save a refcadastrale.
     *
     * @param refcadastraleDTO the entity to save.
     * @return the persisted entity.
     */
    RefcadastraleDTO save(RefcadastraleDTO refcadastraleDTO);

    /**
     * Partially updates a refcadastrale.
     *
     * @param refcadastraleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RefcadastraleDTO> partialUpdate(RefcadastraleDTO refcadastraleDTO);

    /**
     * Get all the refcadastrales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RefcadastraleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" refcadastrale.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RefcadastraleDTO> findOne(Long id);

    /**
     * Delete the "id" refcadastrale.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
