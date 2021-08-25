package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.LocataireRepository;
import com.pirtol.lab.service.LocataireQueryService;
import com.pirtol.lab.service.LocataireService;
import com.pirtol.lab.service.criteria.LocataireCriteria;
import com.pirtol.lab.service.dto.LocataireDTO;
import com.pirtol.lab.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.pirtol.lab.domain.Locataire}.
 */
@RestController
@RequestMapping("/api")
public class LocataireResource {

    private final Logger log = LoggerFactory.getLogger(LocataireResource.class);

    private static final String ENTITY_NAME = "locataire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocataireService locataireService;

    private final LocataireRepository locataireRepository;

    private final LocataireQueryService locataireQueryService;

    public LocataireResource(
        LocataireService locataireService,
        LocataireRepository locataireRepository,
        LocataireQueryService locataireQueryService
    ) {
        this.locataireService = locataireService;
        this.locataireRepository = locataireRepository;
        this.locataireQueryService = locataireQueryService;
    }

    /**
     * {@code POST  /locataires} : Create a new locataire.
     *
     * @param locataireDTO the locataireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locataireDTO, or with status {@code 400 (Bad Request)} if the locataire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/locataires")
    public ResponseEntity<LocataireDTO> createLocataire(@RequestBody LocataireDTO locataireDTO) throws URISyntaxException {
        log.debug("REST request to save Locataire : {}", locataireDTO);
        if (locataireDTO.getId() != null) {
            throw new BadRequestAlertException("A new locataire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocataireDTO result = locataireService.save(locataireDTO);
        return ResponseEntity
            .created(new URI("/api/locataires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /locataires/:id} : Updates an existing locataire.
     *
     * @param id the id of the locataireDTO to save.
     * @param locataireDTO the locataireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locataireDTO,
     * or with status {@code 400 (Bad Request)} if the locataireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locataireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/locataires/{id}")
    public ResponseEntity<LocataireDTO> updateLocataire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LocataireDTO locataireDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Locataire : {}, {}", id, locataireDTO);
        if (locataireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locataireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locataireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LocataireDTO result = locataireService.save(locataireDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locataireDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /locataires/:id} : Partial updates given fields of an existing locataire, field will ignore if it is null
     *
     * @param id the id of the locataireDTO to save.
     * @param locataireDTO the locataireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locataireDTO,
     * or with status {@code 400 (Bad Request)} if the locataireDTO is not valid,
     * or with status {@code 404 (Not Found)} if the locataireDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the locataireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/locataires/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<LocataireDTO> partialUpdateLocataire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LocataireDTO locataireDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Locataire partially : {}, {}", id, locataireDTO);
        if (locataireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locataireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locataireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LocataireDTO> result = locataireService.partialUpdate(locataireDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locataireDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /locataires} : get all the locataires.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locataires in body.
     */
    @GetMapping("/locataires")
    public ResponseEntity<List<LocataireDTO>> getAllLocataires(LocataireCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Locataires by criteria: {}", criteria);
        Page<LocataireDTO> page = locataireQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /locataires/count} : count all the locataires.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/locataires/count")
    public ResponseEntity<Long> countLocataires(LocataireCriteria criteria) {
        log.debug("REST request to count Locataires by criteria: {}", criteria);
        return ResponseEntity.ok().body(locataireQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /locataires/:id} : get the "id" locataire.
     *
     * @param id the id of the locataireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locataireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/locataires/{id}")
    public ResponseEntity<LocataireDTO> getLocataire(@PathVariable Long id) {
        log.debug("REST request to get Locataire : {}", id);
        Optional<LocataireDTO> locataireDTO = locataireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locataireDTO);
    }

    /**
     * {@code DELETE  /locataires/:id} : delete the "id" locataire.
     *
     * @param id the id of the locataireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/locataires/{id}")
    public ResponseEntity<Void> deleteLocataire(@PathVariable Long id) {
        log.debug("REST request to delete Locataire : {}", id);
        locataireService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
