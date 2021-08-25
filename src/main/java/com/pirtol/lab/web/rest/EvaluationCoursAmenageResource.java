package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.EvaluationCoursAmenageRepository;
import com.pirtol.lab.service.EvaluationCoursAmenageQueryService;
import com.pirtol.lab.service.EvaluationCoursAmenageService;
import com.pirtol.lab.service.criteria.EvaluationCoursAmenageCriteria;
import com.pirtol.lab.service.dto.EvaluationCoursAmenageDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.EvaluationCoursAmenage}.
 */
@RestController
@RequestMapping("/api")
public class EvaluationCoursAmenageResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationCoursAmenageResource.class);

    private static final String ENTITY_NAME = "evaluationCoursAmenage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvaluationCoursAmenageService evaluationCoursAmenageService;

    private final EvaluationCoursAmenageRepository evaluationCoursAmenageRepository;

    private final EvaluationCoursAmenageQueryService evaluationCoursAmenageQueryService;

    public EvaluationCoursAmenageResource(
        EvaluationCoursAmenageService evaluationCoursAmenageService,
        EvaluationCoursAmenageRepository evaluationCoursAmenageRepository,
        EvaluationCoursAmenageQueryService evaluationCoursAmenageQueryService
    ) {
        this.evaluationCoursAmenageService = evaluationCoursAmenageService;
        this.evaluationCoursAmenageRepository = evaluationCoursAmenageRepository;
        this.evaluationCoursAmenageQueryService = evaluationCoursAmenageQueryService;
    }

    /**
     * {@code POST  /evaluation-cours-amenages} : Create a new evaluationCoursAmenage.
     *
     * @param evaluationCoursAmenageDTO the evaluationCoursAmenageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evaluationCoursAmenageDTO, or with status {@code 400 (Bad Request)} if the evaluationCoursAmenage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evaluation-cours-amenages")
    public ResponseEntity<EvaluationCoursAmenageDTO> createEvaluationCoursAmenage(
        @RequestBody EvaluationCoursAmenageDTO evaluationCoursAmenageDTO
    ) throws URISyntaxException {
        log.debug("REST request to save EvaluationCoursAmenage : {}", evaluationCoursAmenageDTO);
        if (evaluationCoursAmenageDTO.getId() != null) {
            throw new BadRequestAlertException("A new evaluationCoursAmenage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluationCoursAmenageDTO result = evaluationCoursAmenageService.save(evaluationCoursAmenageDTO);
        return ResponseEntity
            .created(new URI("/api/evaluation-cours-amenages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evaluation-cours-amenages/:id} : Updates an existing evaluationCoursAmenage.
     *
     * @param id the id of the evaluationCoursAmenageDTO to save.
     * @param evaluationCoursAmenageDTO the evaluationCoursAmenageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluationCoursAmenageDTO,
     * or with status {@code 400 (Bad Request)} if the evaluationCoursAmenageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evaluationCoursAmenageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evaluation-cours-amenages/{id}")
    public ResponseEntity<EvaluationCoursAmenageDTO> updateEvaluationCoursAmenage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EvaluationCoursAmenageDTO evaluationCoursAmenageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EvaluationCoursAmenage : {}, {}", id, evaluationCoursAmenageDTO);
        if (evaluationCoursAmenageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evaluationCoursAmenageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evaluationCoursAmenageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EvaluationCoursAmenageDTO result = evaluationCoursAmenageService.save(evaluationCoursAmenageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, evaluationCoursAmenageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /evaluation-cours-amenages/:id} : Partial updates given fields of an existing evaluationCoursAmenage, field will ignore if it is null
     *
     * @param id the id of the evaluationCoursAmenageDTO to save.
     * @param evaluationCoursAmenageDTO the evaluationCoursAmenageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluationCoursAmenageDTO,
     * or with status {@code 400 (Bad Request)} if the evaluationCoursAmenageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the evaluationCoursAmenageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the evaluationCoursAmenageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/evaluation-cours-amenages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EvaluationCoursAmenageDTO> partialUpdateEvaluationCoursAmenage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EvaluationCoursAmenageDTO evaluationCoursAmenageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EvaluationCoursAmenage partially : {}, {}", id, evaluationCoursAmenageDTO);
        if (evaluationCoursAmenageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evaluationCoursAmenageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evaluationCoursAmenageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EvaluationCoursAmenageDTO> result = evaluationCoursAmenageService.partialUpdate(evaluationCoursAmenageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, evaluationCoursAmenageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /evaluation-cours-amenages} : get all the evaluationCoursAmenages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evaluationCoursAmenages in body.
     */
    @GetMapping("/evaluation-cours-amenages")
    public ResponseEntity<List<EvaluationCoursAmenageDTO>> getAllEvaluationCoursAmenages(
        EvaluationCoursAmenageCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get EvaluationCoursAmenages by criteria: {}", criteria);
        Page<EvaluationCoursAmenageDTO> page = evaluationCoursAmenageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /evaluation-cours-amenages/count} : count all the evaluationCoursAmenages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/evaluation-cours-amenages/count")
    public ResponseEntity<Long> countEvaluationCoursAmenages(EvaluationCoursAmenageCriteria criteria) {
        log.debug("REST request to count EvaluationCoursAmenages by criteria: {}", criteria);
        return ResponseEntity.ok().body(evaluationCoursAmenageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /evaluation-cours-amenages/:id} : get the "id" evaluationCoursAmenage.
     *
     * @param id the id of the evaluationCoursAmenageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evaluationCoursAmenageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evaluation-cours-amenages/{id}")
    public ResponseEntity<EvaluationCoursAmenageDTO> getEvaluationCoursAmenage(@PathVariable Long id) {
        log.debug("REST request to get EvaluationCoursAmenage : {}", id);
        Optional<EvaluationCoursAmenageDTO> evaluationCoursAmenageDTO = evaluationCoursAmenageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evaluationCoursAmenageDTO);
    }

    /**
     * {@code DELETE  /evaluation-cours-amenages/:id} : delete the "id" evaluationCoursAmenage.
     *
     * @param id the id of the evaluationCoursAmenageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evaluation-cours-amenages/{id}")
    public ResponseEntity<Void> deleteEvaluationCoursAmenage(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationCoursAmenage : {}", id);
        evaluationCoursAmenageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
