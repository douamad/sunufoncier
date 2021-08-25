package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.QuartierRepository;
import com.pirtol.lab.service.QuartierQueryService;
import com.pirtol.lab.service.QuartierService;
import com.pirtol.lab.service.criteria.QuartierCriteria;
import com.pirtol.lab.service.dto.QuartierDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.Quartier}.
 */
@RestController
@RequestMapping("/api")
public class QuartierResource {

    private final Logger log = LoggerFactory.getLogger(QuartierResource.class);

    private static final String ENTITY_NAME = "quartier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuartierService quartierService;

    private final QuartierRepository quartierRepository;

    private final QuartierQueryService quartierQueryService;

    public QuartierResource(
        QuartierService quartierService,
        QuartierRepository quartierRepository,
        QuartierQueryService quartierQueryService
    ) {
        this.quartierService = quartierService;
        this.quartierRepository = quartierRepository;
        this.quartierQueryService = quartierQueryService;
    }

    /**
     * {@code POST  /quartiers} : Create a new quartier.
     *
     * @param quartierDTO the quartierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quartierDTO, or with status {@code 400 (Bad Request)} if the quartier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quartiers")
    public ResponseEntity<QuartierDTO> createQuartier(@RequestBody QuartierDTO quartierDTO) throws URISyntaxException {
        log.debug("REST request to save Quartier : {}", quartierDTO);
        if (quartierDTO.getId() != null) {
            throw new BadRequestAlertException("A new quartier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuartierDTO result = quartierService.save(quartierDTO);
        return ResponseEntity
            .created(new URI("/api/quartiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quartiers/:id} : Updates an existing quartier.
     *
     * @param id the id of the quartierDTO to save.
     * @param quartierDTO the quartierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quartierDTO,
     * or with status {@code 400 (Bad Request)} if the quartierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quartierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quartiers/{id}")
    public ResponseEntity<QuartierDTO> updateQuartier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuartierDTO quartierDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Quartier : {}, {}", id, quartierDTO);
        if (quartierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quartierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quartierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QuartierDTO result = quartierService.save(quartierDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, quartierDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /quartiers/:id} : Partial updates given fields of an existing quartier, field will ignore if it is null
     *
     * @param id the id of the quartierDTO to save.
     * @param quartierDTO the quartierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quartierDTO,
     * or with status {@code 400 (Bad Request)} if the quartierDTO is not valid,
     * or with status {@code 404 (Not Found)} if the quartierDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the quartierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/quartiers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QuartierDTO> partialUpdateQuartier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuartierDTO quartierDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Quartier partially : {}, {}", id, quartierDTO);
        if (quartierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quartierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quartierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QuartierDTO> result = quartierService.partialUpdate(quartierDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, quartierDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /quartiers} : get all the quartiers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quartiers in body.
     */
    @GetMapping("/quartiers")
    public ResponseEntity<List<QuartierDTO>> getAllQuartiers(QuartierCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Quartiers by criteria: {}", criteria);
        Page<QuartierDTO> page = quartierQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quartiers/count} : count all the quartiers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/quartiers/count")
    public ResponseEntity<Long> countQuartiers(QuartierCriteria criteria) {
        log.debug("REST request to count Quartiers by criteria: {}", criteria);
        return ResponseEntity.ok().body(quartierQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /quartiers/:id} : get the "id" quartier.
     *
     * @param id the id of the quartierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quartierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quartiers/{id}")
    public ResponseEntity<QuartierDTO> getQuartier(@PathVariable Long id) {
        log.debug("REST request to get Quartier : {}", id);
        Optional<QuartierDTO> quartierDTO = quartierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quartierDTO);
    }

    /**
     * {@code DELETE  /quartiers/:id} : delete the "id" quartier.
     *
     * @param id the id of the quartierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quartiers/{id}")
    public ResponseEntity<Void> deleteQuartier(@PathVariable Long id) {
        log.debug("REST request to delete Quartier : {}", id);
        quartierService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
