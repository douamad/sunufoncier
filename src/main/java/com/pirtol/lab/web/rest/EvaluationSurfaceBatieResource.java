package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.EvaluationSurfaceBatieRepository;
import com.pirtol.lab.service.EvaluationSurfaceBatieQueryService;
import com.pirtol.lab.service.EvaluationSurfaceBatieService;
import com.pirtol.lab.service.criteria.EvaluationSurfaceBatieCriteria;
import com.pirtol.lab.service.dto.EvaluationSurfaceBatieDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.EvaluationSurfaceBatie}.
 */
@RestController
@RequestMapping("/api")
public class EvaluationSurfaceBatieResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationSurfaceBatieResource.class);

    private static final String ENTITY_NAME = "evaluationSurfaceBatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvaluationSurfaceBatieService evaluationSurfaceBatieService;

    private final EvaluationSurfaceBatieRepository evaluationSurfaceBatieRepository;

    private final EvaluationSurfaceBatieQueryService evaluationSurfaceBatieQueryService;

    public EvaluationSurfaceBatieResource(
        EvaluationSurfaceBatieService evaluationSurfaceBatieService,
        EvaluationSurfaceBatieRepository evaluationSurfaceBatieRepository,
        EvaluationSurfaceBatieQueryService evaluationSurfaceBatieQueryService
    ) {
        this.evaluationSurfaceBatieService = evaluationSurfaceBatieService;
        this.evaluationSurfaceBatieRepository = evaluationSurfaceBatieRepository;
        this.evaluationSurfaceBatieQueryService = evaluationSurfaceBatieQueryService;
    }

    /**
     * {@code POST  /evaluation-surface-baties} : Create a new evaluationSurfaceBatie.
     *
     * @param evaluationSurfaceBatieDTO the evaluationSurfaceBatieDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evaluationSurfaceBatieDTO, or with status {@code 400 (Bad Request)} if the evaluationSurfaceBatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evaluation-surface-baties")
    public ResponseEntity<EvaluationSurfaceBatieDTO> createEvaluationSurfaceBatie(
        @RequestBody EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO
    ) throws URISyntaxException {
        log.debug("REST request to save EvaluationSurfaceBatie : {}", evaluationSurfaceBatieDTO);
        if (evaluationSurfaceBatieDTO.getId() != null) {
            throw new BadRequestAlertException("A new evaluationSurfaceBatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluationSurfaceBatieDTO result = evaluationSurfaceBatieService.save(evaluationSurfaceBatieDTO);
        return ResponseEntity
            .created(new URI("/api/evaluation-surface-baties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evaluation-surface-baties/:id} : Updates an existing evaluationSurfaceBatie.
     *
     * @param id the id of the evaluationSurfaceBatieDTO to save.
     * @param evaluationSurfaceBatieDTO the evaluationSurfaceBatieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluationSurfaceBatieDTO,
     * or with status {@code 400 (Bad Request)} if the evaluationSurfaceBatieDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evaluationSurfaceBatieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evaluation-surface-baties/{id}")
    public ResponseEntity<EvaluationSurfaceBatieDTO> updateEvaluationSurfaceBatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EvaluationSurfaceBatie : {}, {}", id, evaluationSurfaceBatieDTO);
        if (evaluationSurfaceBatieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evaluationSurfaceBatieDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evaluationSurfaceBatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EvaluationSurfaceBatieDTO result = evaluationSurfaceBatieService.save(evaluationSurfaceBatieDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, evaluationSurfaceBatieDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /evaluation-surface-baties/:id} : Partial updates given fields of an existing evaluationSurfaceBatie, field will ignore if it is null
     *
     * @param id the id of the evaluationSurfaceBatieDTO to save.
     * @param evaluationSurfaceBatieDTO the evaluationSurfaceBatieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluationSurfaceBatieDTO,
     * or with status {@code 400 (Bad Request)} if the evaluationSurfaceBatieDTO is not valid,
     * or with status {@code 404 (Not Found)} if the evaluationSurfaceBatieDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the evaluationSurfaceBatieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/evaluation-surface-baties/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EvaluationSurfaceBatieDTO> partialUpdateEvaluationSurfaceBatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EvaluationSurfaceBatie partially : {}, {}", id, evaluationSurfaceBatieDTO);
        if (evaluationSurfaceBatieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evaluationSurfaceBatieDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evaluationSurfaceBatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EvaluationSurfaceBatieDTO> result = evaluationSurfaceBatieService.partialUpdate(evaluationSurfaceBatieDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, evaluationSurfaceBatieDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /evaluation-surface-baties} : get all the evaluationSurfaceBaties.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evaluationSurfaceBaties in body.
     */
    @GetMapping("/evaluation-surface-baties")
    public ResponseEntity<List<EvaluationSurfaceBatieDTO>> getAllEvaluationSurfaceBaties(
        EvaluationSurfaceBatieCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get EvaluationSurfaceBaties by criteria: {}", criteria);
        Page<EvaluationSurfaceBatieDTO> page = evaluationSurfaceBatieQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /evaluation-surface-baties/count} : count all the evaluationSurfaceBaties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/evaluation-surface-baties/count")
    public ResponseEntity<Long> countEvaluationSurfaceBaties(EvaluationSurfaceBatieCriteria criteria) {
        log.debug("REST request to count EvaluationSurfaceBaties by criteria: {}", criteria);
        return ResponseEntity.ok().body(evaluationSurfaceBatieQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /evaluation-surface-baties/:id} : get the "id" evaluationSurfaceBatie.
     *
     * @param id the id of the evaluationSurfaceBatieDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evaluationSurfaceBatieDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evaluation-surface-baties/{id}")
    public ResponseEntity<EvaluationSurfaceBatieDTO> getEvaluationSurfaceBatie(@PathVariable Long id) {
        log.debug("REST request to get EvaluationSurfaceBatie : {}", id);
        Optional<EvaluationSurfaceBatieDTO> evaluationSurfaceBatieDTO = evaluationSurfaceBatieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evaluationSurfaceBatieDTO);
    }

    /**
     * {@code DELETE  /evaluation-surface-baties/:id} : delete the "id" evaluationSurfaceBatie.
     *
     * @param id the id of the evaluationSurfaceBatieDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evaluation-surface-baties/{id}")
    public ResponseEntity<Void> deleteEvaluationSurfaceBatie(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationSurfaceBatie : {}", id);
        evaluationSurfaceBatieService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
