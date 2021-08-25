package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.RefParcelaireDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.RefParcelaire}.
 */
public interface RefParcelaireService {
    /**
     * Save a refParcelaire.
     *
     * @param refParcelaireDTO the entity to save.
     * @return the persisted entity.
     */
    RefParcelaireDTO save(RefParcelaireDTO refParcelaireDTO);

    /**
     * Partially updates a refParcelaire.
     *
     * @param refParcelaireDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RefParcelaireDTO> partialUpdate(RefParcelaireDTO refParcelaireDTO);

    /**
     * Get all the refParcelaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RefParcelaireDTO> findAll(Pageable pageable);

    /**
     * Get the "id" refParcelaire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RefParcelaireDTO> findOne(Long id);

    /**
     * Delete the "id" refParcelaire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
