package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.ProprietaireDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.Proprietaire}.
 */
public interface ProprietaireService {
    /**
     * Save a proprietaire.
     *
     * @param proprietaireDTO the entity to save.
     * @return the persisted entity.
     */
    ProprietaireDTO save(ProprietaireDTO proprietaireDTO);

    /**
     * Partially updates a proprietaire.
     *
     * @param proprietaireDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProprietaireDTO> partialUpdate(ProprietaireDTO proprietaireDTO);

    /**
     * Get all the proprietaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProprietaireDTO> findAll(Pageable pageable);

    /**
     * Get the "id" proprietaire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProprietaireDTO> findOne(Long id);

    /**
     * Delete the "id" proprietaire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
