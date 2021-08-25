package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.CategorieCoursAmenageRepository;
import com.pirtol.lab.service.CategorieCoursAmenageQueryService;
import com.pirtol.lab.service.CategorieCoursAmenageService;
import com.pirtol.lab.service.criteria.CategorieCoursAmenageCriteria;
import com.pirtol.lab.service.dto.CategorieCoursAmenageDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.CategorieCoursAmenage}.
 */
@RestController
@RequestMapping("/api")
public class CategorieCoursAmenageResource {

    private final Logger log = LoggerFactory.getLogger(CategorieCoursAmenageResource.class);

    private static final String ENTITY_NAME = "categorieCoursAmenage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorieCoursAmenageService categorieCoursAmenageService;

    private final CategorieCoursAmenageRepository categorieCoursAmenageRepository;

    private final CategorieCoursAmenageQueryService categorieCoursAmenageQueryService;

    public CategorieCoursAmenageResource(
        CategorieCoursAmenageService categorieCoursAmenageService,
        CategorieCoursAmenageRepository categorieCoursAmenageRepository,
        CategorieCoursAmenageQueryService categorieCoursAmenageQueryService
    ) {
        this.categorieCoursAmenageService = categorieCoursAmenageService;
        this.categorieCoursAmenageRepository = categorieCoursAmenageRepository;
        this.categorieCoursAmenageQueryService = categorieCoursAmenageQueryService;
    }

    /**
     * {@code POST  /categorie-cours-amenages} : Create a new categorieCoursAmenage.
     *
     * @param categorieCoursAmenageDTO the categorieCoursAmenageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorieCoursAmenageDTO, or with status {@code 400 (Bad Request)} if the categorieCoursAmenage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorie-cours-amenages")
    public ResponseEntity<CategorieCoursAmenageDTO> createCategorieCoursAmenage(
        @RequestBody CategorieCoursAmenageDTO categorieCoursAmenageDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CategorieCoursAmenage : {}", categorieCoursAmenageDTO);
        if (categorieCoursAmenageDTO.getId() != null) {
            throw new BadRequestAlertException("A new categorieCoursAmenage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieCoursAmenageDTO result = categorieCoursAmenageService.save(categorieCoursAmenageDTO);
        return ResponseEntity
            .created(new URI("/api/categorie-cours-amenages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categorie-cours-amenages/:id} : Updates an existing categorieCoursAmenage.
     *
     * @param id the id of the categorieCoursAmenageDTO to save.
     * @param categorieCoursAmenageDTO the categorieCoursAmenageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieCoursAmenageDTO,
     * or with status {@code 400 (Bad Request)} if the categorieCoursAmenageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorieCoursAmenageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorie-cours-amenages/{id}")
    public ResponseEntity<CategorieCoursAmenageDTO> updateCategorieCoursAmenage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategorieCoursAmenageDTO categorieCoursAmenageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CategorieCoursAmenage : {}, {}", id, categorieCoursAmenageDTO);
        if (categorieCoursAmenageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieCoursAmenageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieCoursAmenageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategorieCoursAmenageDTO result = categorieCoursAmenageService.save(categorieCoursAmenageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categorieCoursAmenageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categorie-cours-amenages/:id} : Partial updates given fields of an existing categorieCoursAmenage, field will ignore if it is null
     *
     * @param id the id of the categorieCoursAmenageDTO to save.
     * @param categorieCoursAmenageDTO the categorieCoursAmenageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieCoursAmenageDTO,
     * or with status {@code 400 (Bad Request)} if the categorieCoursAmenageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categorieCoursAmenageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categorieCoursAmenageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categorie-cours-amenages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CategorieCoursAmenageDTO> partialUpdateCategorieCoursAmenage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategorieCoursAmenageDTO categorieCoursAmenageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategorieCoursAmenage partially : {}, {}", id, categorieCoursAmenageDTO);
        if (categorieCoursAmenageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieCoursAmenageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieCoursAmenageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategorieCoursAmenageDTO> result = categorieCoursAmenageService.partialUpdate(categorieCoursAmenageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categorieCoursAmenageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /categorie-cours-amenages} : get all the categorieCoursAmenages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorieCoursAmenages in body.
     */
    @GetMapping("/categorie-cours-amenages")
    public ResponseEntity<List<CategorieCoursAmenageDTO>> getAllCategorieCoursAmenages(
        CategorieCoursAmenageCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get CategorieCoursAmenages by criteria: {}", criteria);
        Page<CategorieCoursAmenageDTO> page = categorieCoursAmenageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categorie-cours-amenages/count} : count all the categorieCoursAmenages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/categorie-cours-amenages/count")
    public ResponseEntity<Long> countCategorieCoursAmenages(CategorieCoursAmenageCriteria criteria) {
        log.debug("REST request to count CategorieCoursAmenages by criteria: {}", criteria);
        return ResponseEntity.ok().body(categorieCoursAmenageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categorie-cours-amenages/:id} : get the "id" categorieCoursAmenage.
     *
     * @param id the id of the categorieCoursAmenageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorieCoursAmenageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorie-cours-amenages/{id}")
    public ResponseEntity<CategorieCoursAmenageDTO> getCategorieCoursAmenage(@PathVariable Long id) {
        log.debug("REST request to get CategorieCoursAmenage : {}", id);
        Optional<CategorieCoursAmenageDTO> categorieCoursAmenageDTO = categorieCoursAmenageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorieCoursAmenageDTO);
    }

    /**
     * {@code DELETE  /categorie-cours-amenages/:id} : delete the "id" categorieCoursAmenage.
     *
     * @param id the id of the categorieCoursAmenageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorie-cours-amenages/{id}")
    public ResponseEntity<Void> deleteCategorieCoursAmenage(@PathVariable Long id) {
        log.debug("REST request to delete CategorieCoursAmenage : {}", id);
        categorieCoursAmenageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
