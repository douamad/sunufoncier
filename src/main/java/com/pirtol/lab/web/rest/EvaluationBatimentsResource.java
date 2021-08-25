package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.EvaluationBatimentsRepository;
import com.pirtol.lab.service.EvaluationBatimentsQueryService;
import com.pirtol.lab.service.EvaluationBatimentsService;
import com.pirtol.lab.service.criteria.EvaluationBatimentsCriteria;
import com.pirtol.lab.service.dto.EvaluationBatimentsDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.EvaluationBatiments}.
 */
@RestController
@RequestMapping("/api")
public class EvaluationBatimentsResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationBatimentsResource.class);

    private static final String ENTITY_NAME = "evaluationBatiments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvaluationBatimentsService evaluationBatimentsService;

    private final EvaluationBatimentsRepository evaluationBatimentsRepository;

    private final EvaluationBatimentsQueryService evaluationBatimentsQueryService;

    public EvaluationBatimentsResource(
        EvaluationBatimentsService evaluationBatimentsService,
        EvaluationBatimentsRepository evaluationBatimentsRepository,
        EvaluationBatimentsQueryService evaluationBatimentsQueryService
    ) {
        this.evaluationBatimentsService = evaluationBatimentsService;
        this.evaluationBatimentsRepository = evaluationBatimentsRepository;
        this.evaluationBatimentsQueryService = evaluationBatimentsQueryService;
    }

    /**
     * {@code POST  /evaluation-batiments} : Create a new evaluationBatiments.
     *
     * @param evaluationBatimentsDTO the evaluationBatimentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evaluationBatimentsDTO, or with status {@code 400 (Bad Request)} if the evaluationBatiments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evaluation-batiments")
    public ResponseEntity<EvaluationBatimentsDTO> createEvaluationBatiments(@RequestBody EvaluationBatimentsDTO evaluationBatimentsDTO)
        throws URISyntaxException {
        log.debug("REST request to save EvaluationBatiments : {}", evaluationBatimentsDTO);
        if (evaluationBatimentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new evaluationBatiments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluationBatimentsDTO result = evaluationBatimentsService.save(evaluationBatimentsDTO);
        return ResponseEntity
            .created(new URI("/api/evaluation-batiments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evaluation-batiments/:id} : Updates an existing evaluationBatiments.
     *
     * @param id the id of the evaluationBatimentsDTO to save.
     * @param evaluationBatimentsDTO the evaluationBatimentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluationBatimentsDTO,
     * or with status {@code 400 (Bad Request)} if the evaluationBatimentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evaluationBatimentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evaluation-batiments/{id}")
    public ResponseEntity<EvaluationBatimentsDTO> updateEvaluationBatiments(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EvaluationBatimentsDTO evaluationBatimentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EvaluationBatiments : {}, {}", id, evaluationBatimentsDTO);
        if (evaluationBatimentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evaluationBatimentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evaluationBatimentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EvaluationBatimentsDTO result = evaluationBatimentsService.save(evaluationBatimentsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, evaluationBatimentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /evaluation-batiments/:id} : Partial updates given fields of an existing evaluationBatiments, field will ignore if it is null
     *
     * @param id the id of the evaluationBatimentsDTO to save.
     * @param evaluationBatimentsDTO the evaluationBatimentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluationBatimentsDTO,
     * or with status {@code 400 (Bad Request)} if the evaluationBatimentsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the evaluationBatimentsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the evaluationBatimentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/evaluation-batiments/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EvaluationBatimentsDTO> partialUpdateEvaluationBatiments(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EvaluationBatimentsDTO evaluationBatimentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EvaluationBatiments partially : {}, {}", id, evaluationBatimentsDTO);
        if (evaluationBatimentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evaluationBatimentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evaluationBatimentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EvaluationBatimentsDTO> result = evaluationBatimentsService.partialUpdate(evaluationBatimentsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, evaluationBatimentsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /evaluation-batiments} : get all the evaluationBatiments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evaluationBatiments in body.
     */
    @GetMapping("/evaluation-batiments")
    public ResponseEntity<List<EvaluationBatimentsDTO>> getAllEvaluationBatiments(EvaluationBatimentsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EvaluationBatiments by criteria: {}", criteria);
        Page<EvaluationBatimentsDTO> page = evaluationBatimentsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /evaluation-batiments/count} : count all the evaluationBatiments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/evaluation-batiments/count")
    public ResponseEntity<Long> countEvaluationBatiments(EvaluationBatimentsCriteria criteria) {
        log.debug("REST request to count EvaluationBatiments by criteria: {}", criteria);
        return ResponseEntity.ok().body(evaluationBatimentsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /evaluation-batiments/:id} : get the "id" evaluationBatiments.
     *
     * @param id the id of the evaluationBatimentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evaluationBatimentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evaluation-batiments/{id}")
    public ResponseEntity<EvaluationBatimentsDTO> getEvaluationBatiments(@PathVariable Long id) {
        log.debug("REST request to get EvaluationBatiments : {}", id);
        Optional<EvaluationBatimentsDTO> evaluationBatimentsDTO = evaluationBatimentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evaluationBatimentsDTO);
    }

    /**
     * {@code DELETE  /evaluation-batiments/:id} : delete the "id" evaluationBatiments.
     *
     * @param id the id of the evaluationBatimentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evaluation-batiments/{id}")
    public ResponseEntity<Void> deleteEvaluationBatiments(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationBatiments : {}", id);
        evaluationBatimentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
