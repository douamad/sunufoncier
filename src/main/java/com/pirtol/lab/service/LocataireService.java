package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.LocataireDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.Locataire}.
 */
public interface LocataireService {
    /**
     * Save a locataire.
     *
     * @param locataireDTO the entity to save.
     * @return the persisted entity.
     */
    LocataireDTO save(LocataireDTO locataireDTO);

    List<LocataireDTO> saveBulk(List<LocataireDTO> locataireDTO);

    /**
     * Partially updates a locataire.
     *
     * @param locataireDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LocataireDTO> partialUpdate(LocataireDTO locataireDTO);

    /**
     * Get all the locataires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LocataireDTO> findAll(Pageable pageable);

    /**
     * Get the "id" locataire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LocataireDTO> findOne(Long id);

    /**
     * Delete the "id" locataire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
