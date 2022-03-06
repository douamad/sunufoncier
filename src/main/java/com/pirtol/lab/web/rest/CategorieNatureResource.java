package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.CategorieNatureRepository;
import com.pirtol.lab.service.CategorieNatureQueryService;
import com.pirtol.lab.service.CategorieNatureService;
import com.pirtol.lab.service.criteria.CategorieNatureCriteria;
import com.pirtol.lab.service.dto.CategorieNatureDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.CategorieNature}.
 */
@RestController
@RequestMapping("/api")
public class CategorieNatureResource {

    private final Logger log = LoggerFactory.getLogger(CategorieNatureResource.class);

    private static final String ENTITY_NAME = "categorieNature";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorieNatureService categorieNatureService;

    private final CategorieNatureRepository categorieNatureRepository;

    private final CategorieNatureQueryService categorieNatureQueryService;

    public CategorieNatureResource(
        CategorieNatureService categorieNatureService,
        CategorieNatureRepository categorieNatureRepository,
        CategorieNatureQueryService categorieNatureQueryService
    ) {
        this.categorieNatureService = categorieNatureService;
        this.categorieNatureRepository = categorieNatureRepository;
        this.categorieNatureQueryService = categorieNatureQueryService;
    }

    /**
     * {@code POST  /categorie-natures} : Create a new categorieNature.
     *
     * @param categorieNatureDTO the categorieNatureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorieNatureDTO, or with status {@code 400 (Bad Request)} if the categorieNature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorie-natures")
    public ResponseEntity<CategorieNatureDTO> createCategorieNature(@RequestBody CategorieNatureDTO categorieNatureDTO)
        throws URISyntaxException {
        log.debug("REST request to save CategorieNature : {}", categorieNatureDTO);
        if (categorieNatureDTO.getId() != null) {
            throw new BadRequestAlertException("A new categorieNature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieNatureDTO result = categorieNatureService.save(categorieNatureDTO);
        return ResponseEntity
            .created(new URI("/api/categorie-natures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categorie-natures/:id} : Updates an existing categorieNature.
     *
     * @param id the id of the categorieNatureDTO to save.
     * @param categorieNatureDTO the categorieNatureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieNatureDTO,
     * or with status {@code 400 (Bad Request)} if the categorieNatureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorieNatureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorie-natures/{id}")
    public ResponseEntity<CategorieNatureDTO> updateCategorieNature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategorieNatureDTO categorieNatureDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CategorieNature : {}, {}", id, categorieNatureDTO);
        if (categorieNatureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieNatureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieNatureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategorieNatureDTO result = categorieNatureService.save(categorieNatureDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categorieNatureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categorie-natures/:id} : Partial updates given fields of an existing categorieNature, field will ignore if it is null
     *
     * @param id the id of the categorieNatureDTO to save.
     * @param categorieNatureDTO the categorieNatureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieNatureDTO,
     * or with status {@code 400 (Bad Request)} if the categorieNatureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categorieNatureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categorieNatureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categorie-natures/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CategorieNatureDTO> partialUpdateCategorieNature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategorieNatureDTO categorieNatureDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategorieNature partially : {}, {}", id, categorieNatureDTO);
        if (categorieNatureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieNatureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieNatureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategorieNatureDTO> result = categorieNatureService.partialUpdate(categorieNatureDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categorieNatureDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /categorie-natures} : get all the categorieNatures.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorieNatures in body.
     */
    @GetMapping("/categorie-natures")
    public ResponseEntity<List<CategorieNatureDTO>> getAllCategorieNatures(CategorieNatureCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CategorieNatures by criteria: {}", criteria);
        Page<CategorieNatureDTO> page = categorieNatureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/categorie-natures/list")
    public ResponseEntity<List<CategorieNatureDTO>> getListAllCategorieNatures(CategorieNatureCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CategorieNatures by criteria: {}", criteria);
        List<CategorieNatureDTO> page = categorieNatureQueryService.findByCriteria(criteria);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /categorie-natures/count} : count all the categorieNatures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/categorie-natures/count")
    public ResponseEntity<Long> countCategorieNatures(CategorieNatureCriteria criteria) {
        log.debug("REST request to count CategorieNatures by criteria: {}", criteria);
        return ResponseEntity.ok().body(categorieNatureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categorie-natures/:id} : get the "id" categorieNature.
     *
     * @param id the id of the categorieNatureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorieNatureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorie-natures/{id}")
    public ResponseEntity<CategorieNatureDTO> getCategorieNature(@PathVariable Long id) {
        log.debug("REST request to get CategorieNature : {}", id);
        Optional<CategorieNatureDTO> categorieNatureDTO = categorieNatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorieNatureDTO);
    }

    /**
     * {@code DELETE  /categorie-natures/:id} : delete the "id" categorieNature.
     *
     * @param id the id of the categorieNatureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorie-natures/{id}")
    public ResponseEntity<Void> deleteCategorieNature(@PathVariable Long id) {
        log.debug("REST request to delete CategorieNature : {}", id);
        categorieNatureService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
