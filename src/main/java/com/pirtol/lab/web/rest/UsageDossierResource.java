package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.UsageDossierRepository;
import com.pirtol.lab.service.UsageDossierQueryService;
import com.pirtol.lab.service.UsageDossierService;
import com.pirtol.lab.service.criteria.UsageDossierCriteria;
import com.pirtol.lab.service.dto.UsageDossierDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.UsageDossier}.
 */
@RestController
@RequestMapping("/api")
public class UsageDossierResource {

    private final Logger log = LoggerFactory.getLogger(UsageDossierResource.class);

    private static final String ENTITY_NAME = "usageDossier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsageDossierService usageDossierService;

    private final UsageDossierRepository usageDossierRepository;

    private final UsageDossierQueryService usageDossierQueryService;

    public UsageDossierResource(
        UsageDossierService usageDossierService,
        UsageDossierRepository usageDossierRepository,
        UsageDossierQueryService usageDossierQueryService
    ) {
        this.usageDossierService = usageDossierService;
        this.usageDossierRepository = usageDossierRepository;
        this.usageDossierQueryService = usageDossierQueryService;
    }

    /**
     * {@code POST  /usage-dossiers} : Create a new usageDossier.
     *
     * @param usageDossierDTO the usageDossierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usageDossierDTO, or with status {@code 400 (Bad Request)} if the usageDossier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usage-dossiers")
    public ResponseEntity<UsageDossierDTO> createUsageDossier(@RequestBody UsageDossierDTO usageDossierDTO) throws URISyntaxException {
        log.debug("REST request to save UsageDossier : {}", usageDossierDTO);
        if (usageDossierDTO.getId() != null) {
            throw new BadRequestAlertException("A new usageDossier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsageDossierDTO result = usageDossierService.save(usageDossierDTO);
        return ResponseEntity
            .created(new URI("/api/usage-dossiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usage-dossiers/:id} : Updates an existing usageDossier.
     *
     * @param id the id of the usageDossierDTO to save.
     * @param usageDossierDTO the usageDossierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usageDossierDTO,
     * or with status {@code 400 (Bad Request)} if the usageDossierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usageDossierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usage-dossiers/{id}")
    public ResponseEntity<UsageDossierDTO> updateUsageDossier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UsageDossierDTO usageDossierDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UsageDossier : {}, {}", id, usageDossierDTO);
        if (usageDossierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usageDossierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usageDossierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UsageDossierDTO result = usageDossierService.save(usageDossierDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, usageDossierDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /usage-dossiers/:id} : Partial updates given fields of an existing usageDossier, field will ignore if it is null
     *
     * @param id the id of the usageDossierDTO to save.
     * @param usageDossierDTO the usageDossierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usageDossierDTO,
     * or with status {@code 400 (Bad Request)} if the usageDossierDTO is not valid,
     * or with status {@code 404 (Not Found)} if the usageDossierDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the usageDossierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/usage-dossiers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<UsageDossierDTO> partialUpdateUsageDossier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UsageDossierDTO usageDossierDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UsageDossier partially : {}, {}", id, usageDossierDTO);
        if (usageDossierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usageDossierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usageDossierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UsageDossierDTO> result = usageDossierService.partialUpdate(usageDossierDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, usageDossierDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /usage-dossiers} : get all the usageDossiers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usageDossiers in body.
     */
    @GetMapping("/usage-dossiers")
    public ResponseEntity<List<UsageDossierDTO>> getAllUsageDossiers(UsageDossierCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UsageDossiers by criteria: {}", criteria);
        Page<UsageDossierDTO> page = usageDossierQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /usage-dossiers/count} : count all the usageDossiers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/usage-dossiers/count")
    public ResponseEntity<Long> countUsageDossiers(UsageDossierCriteria criteria) {
        log.debug("REST request to count UsageDossiers by criteria: {}", criteria);
        return ResponseEntity.ok().body(usageDossierQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /usage-dossiers/:id} : get the "id" usageDossier.
     *
     * @param id the id of the usageDossierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usageDossierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usage-dossiers/{id}")
    public ResponseEntity<UsageDossierDTO> getUsageDossier(@PathVariable Long id) {
        log.debug("REST request to get UsageDossier : {}", id);
        Optional<UsageDossierDTO> usageDossierDTO = usageDossierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usageDossierDTO);
    }

    /**
     * {@code DELETE  /usage-dossiers/:id} : delete the "id" usageDossier.
     *
     * @param id the id of the usageDossierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usage-dossiers/{id}")
    public ResponseEntity<Void> deleteUsageDossier(@PathVariable Long id) {
        log.debug("REST request to delete UsageDossier : {}", id);
        usageDossierService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
