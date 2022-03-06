package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.CategorieBatieRepository;
import com.pirtol.lab.service.CategorieBatieQueryService;
import com.pirtol.lab.service.CategorieBatieService;
import com.pirtol.lab.service.criteria.CategorieBatieCriteria;
import com.pirtol.lab.service.dto.CategorieBatieDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.CategorieBatie}.
 */
@RestController
@RequestMapping("/api")
public class CategorieBatieResource {

    private final Logger log = LoggerFactory.getLogger(CategorieBatieResource.class);

    private static final String ENTITY_NAME = "categorieBatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorieBatieService categorieBatieService;

    private final CategorieBatieRepository categorieBatieRepository;

    private final CategorieBatieQueryService categorieBatieQueryService;

    public CategorieBatieResource(
        CategorieBatieService categorieBatieService,
        CategorieBatieRepository categorieBatieRepository,
        CategorieBatieQueryService categorieBatieQueryService
    ) {
        this.categorieBatieService = categorieBatieService;
        this.categorieBatieRepository = categorieBatieRepository;
        this.categorieBatieQueryService = categorieBatieQueryService;
    }

    /**
     * {@code POST  /categorie-baties} : Create a new categorieBatie.
     *
     * @param categorieBatieDTO the categorieBatieDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorieBatieDTO, or with status {@code 400 (Bad Request)} if the categorieBatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorie-baties")
    public ResponseEntity<CategorieBatieDTO> createCategorieBatie(@RequestBody CategorieBatieDTO categorieBatieDTO)
        throws URISyntaxException {
        log.debug("REST request to save CategorieBatie : {}", categorieBatieDTO);
        if (categorieBatieDTO.getId() != null) {
            throw new BadRequestAlertException("A new categorieBatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieBatieDTO result = categorieBatieService.save(categorieBatieDTO);
        return ResponseEntity
            .created(new URI("/api/categorie-baties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categorie-baties/:id} : Updates an existing categorieBatie.
     *
     * @param id the id of the categorieBatieDTO to save.
     * @param categorieBatieDTO the categorieBatieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieBatieDTO,
     * or with status {@code 400 (Bad Request)} if the categorieBatieDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorieBatieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorie-baties/{id}")
    public ResponseEntity<CategorieBatieDTO> updateCategorieBatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategorieBatieDTO categorieBatieDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CategorieBatie : {}, {}", id, categorieBatieDTO);
        if (categorieBatieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieBatieDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieBatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategorieBatieDTO result = categorieBatieService.save(categorieBatieDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categorieBatieDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categorie-baties/:id} : Partial updates given fields of an existing categorieBatie, field will ignore if it is null
     *
     * @param id the id of the categorieBatieDTO to save.
     * @param categorieBatieDTO the categorieBatieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieBatieDTO,
     * or with status {@code 400 (Bad Request)} if the categorieBatieDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categorieBatieDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categorieBatieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categorie-baties/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CategorieBatieDTO> partialUpdateCategorieBatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategorieBatieDTO categorieBatieDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategorieBatie partially : {}, {}", id, categorieBatieDTO);
        if (categorieBatieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieBatieDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieBatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategorieBatieDTO> result = categorieBatieService.partialUpdate(categorieBatieDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categorieBatieDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /categorie-baties} : get all the categorieBaties.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorieBaties in body.
     */
    @GetMapping("/categorie-baties")
    public ResponseEntity<List<CategorieBatieDTO>> getAllCategorieBaties(CategorieBatieCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CategorieBaties by criteria: {}", criteria);
        Page<CategorieBatieDTO> page = categorieBatieQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/categorie-baties/list")
    public ResponseEntity<List<CategorieBatieDTO>> getListAllCategorieBaties(CategorieBatieCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CategorieBaties by criteria: {}", criteria);
        List<CategorieBatieDTO> page = categorieBatieQueryService.findByCriteria(criteria);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /categorie-baties/count} : count all the categorieBaties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/categorie-baties/count")
    public ResponseEntity<Long> countCategorieBaties(CategorieBatieCriteria criteria) {
        log.debug("REST request to count CategorieBaties by criteria: {}", criteria);
        return ResponseEntity.ok().body(categorieBatieQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categorie-baties/:id} : get the "id" categorieBatie.
     *
     * @param id the id of the categorieBatieDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorieBatieDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorie-baties/{id}")
    public ResponseEntity<CategorieBatieDTO> getCategorieBatie(@PathVariable Long id) {
        log.debug("REST request to get CategorieBatie : {}", id);
        Optional<CategorieBatieDTO> categorieBatieDTO = categorieBatieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorieBatieDTO);
    }

    /**
     * {@code DELETE  /categorie-baties/:id} : delete the "id" categorieBatie.
     *
     * @param id the id of the categorieBatieDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorie-baties/{id}")
    public ResponseEntity<Void> deleteCategorieBatie(@PathVariable Long id) {
        log.debug("REST request to delete CategorieBatie : {}", id);
        categorieBatieService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
