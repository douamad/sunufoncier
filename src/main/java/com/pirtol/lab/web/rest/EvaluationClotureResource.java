package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.EvaluationClotureRepository;
import com.pirtol.lab.service.EvaluationClotureQueryService;
import com.pirtol.lab.service.EvaluationClotureService;
import com.pirtol.lab.service.criteria.EvaluationClotureCriteria;
import com.pirtol.lab.service.dto.EvaluationClotureDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.EvaluationCloture}.
 */
@RestController
@RequestMapping("/api")
public class EvaluationClotureResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationClotureResource.class);

    private static final String ENTITY_NAME = "evaluationCloture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvaluationClotureService evaluationClotureService;

    private final EvaluationClotureRepository evaluationClotureRepository;

    private final EvaluationClotureQueryService evaluationClotureQueryService;

    public EvaluationClotureResource(
        EvaluationClotureService evaluationClotureService,
        EvaluationClotureRepository evaluationClotureRepository,
        EvaluationClotureQueryService evaluationClotureQueryService
    ) {
        this.evaluationClotureService = evaluationClotureService;
        this.evaluationClotureRepository = evaluationClotureRepository;
        this.evaluationClotureQueryService = evaluationClotureQueryService;
    }

    /**
     * {@code POST  /evaluation-clotures} : Create a new evaluationCloture.
     *
     * @param evaluationClotureDTO the evaluationClotureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evaluationClotureDTO, or with status {@code 400 (Bad Request)} if the evaluationCloture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evaluation-clotures")
    public ResponseEntity<EvaluationClotureDTO> createEvaluationCloture(@RequestBody EvaluationClotureDTO evaluationClotureDTO)
        throws URISyntaxException {
        log.debug("REST request to save EvaluationCloture : {}", evaluationClotureDTO);
        if (evaluationClotureDTO.getId() != null) {
            throw new BadRequestAlertException("A new evaluationCloture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluationClotureDTO result = evaluationClotureService.save(evaluationClotureDTO);
        return ResponseEntity
            .created(new URI("/api/evaluation-clotures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/evaluation-clotures/bulk")
    public ResponseEntity<List<EvaluationClotureDTO>> createEvaluationClotureList(
        @RequestBody List<EvaluationClotureDTO> evaluationClotureDTOList
    ) throws URISyntaxException {
        log.debug("REST request to save EvaluationCloture : {}", evaluationClotureDTOList);
        if (evaluationClotureDTOList.isEmpty()) {
            throw new BadRequestAlertException("La list est vide", ENTITY_NAME, "idexists");
        }
        List<EvaluationClotureDTO> resultList = evaluationClotureService.saveBulk(evaluationClotureDTOList);
        return ResponseEntity
            .created(new URI("/api/evaluation-clotures/"))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, resultList.toString()))
            .body(resultList);
    }

    /**
     * {@code PUT  /evaluation-clotures/:id} : Updates an existing evaluationCloture.
     *
     * @param id the id of the evaluationClotureDTO to save.
     * @param evaluationClotureDTO the evaluationClotureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluationClotureDTO,
     * or with status {@code 400 (Bad Request)} if the evaluationClotureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evaluationClotureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evaluation-clotures/{id}")
    public ResponseEntity<EvaluationClotureDTO> updateEvaluationCloture(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EvaluationClotureDTO evaluationClotureDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EvaluationCloture : {}, {}", id, evaluationClotureDTO);
        if (evaluationClotureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evaluationClotureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evaluationClotureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EvaluationClotureDTO result = evaluationClotureService.save(evaluationClotureDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, evaluationClotureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /evaluation-clotures/:id} : Partial updates given fields of an existing evaluationCloture, field will ignore if it is null
     *
     * @param id the id of the evaluationClotureDTO to save.
     * @param evaluationClotureDTO the evaluationClotureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluationClotureDTO,
     * or with status {@code 400 (Bad Request)} if the evaluationClotureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the evaluationClotureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the evaluationClotureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/evaluation-clotures/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EvaluationClotureDTO> partialUpdateEvaluationCloture(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EvaluationClotureDTO evaluationClotureDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EvaluationCloture partially : {}, {}", id, evaluationClotureDTO);
        if (evaluationClotureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evaluationClotureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evaluationClotureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EvaluationClotureDTO> result = evaluationClotureService.partialUpdate(evaluationClotureDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, evaluationClotureDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /evaluation-clotures} : get all the evaluationClotures.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evaluationClotures in body.
     */
    @GetMapping("/evaluation-clotures")
    public ResponseEntity<List<EvaluationClotureDTO>> getAllEvaluationClotures(EvaluationClotureCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EvaluationClotures by criteria: {}", criteria);
        Page<EvaluationClotureDTO> page = evaluationClotureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/evaluation-clotures/list")
    public ResponseEntity<List<EvaluationClotureDTO>> getListAllEvaluationClotures(EvaluationClotureCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EvaluationClotures by criteria: {}", criteria);
        List<EvaluationClotureDTO> page = evaluationClotureQueryService.findByCriteria(criteria);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /evaluation-clotures/count} : count all the evaluationClotures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/evaluation-clotures/count")
    public ResponseEntity<Long> countEvaluationClotures(EvaluationClotureCriteria criteria) {
        log.debug("REST request to count EvaluationClotures by criteria: {}", criteria);
        return ResponseEntity.ok().body(evaluationClotureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /evaluation-clotures/:id} : get the "id" evaluationCloture.
     *
     * @param id the id of the evaluationClotureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evaluationClotureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evaluation-clotures/{id}")
    public ResponseEntity<EvaluationClotureDTO> getEvaluationCloture(@PathVariable Long id) {
        log.debug("REST request to get EvaluationCloture : {}", id);
        Optional<EvaluationClotureDTO> evaluationClotureDTO = evaluationClotureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evaluationClotureDTO);
    }

    /**
     * {@code DELETE  /evaluation-clotures/:id} : delete the "id" evaluationCloture.
     *
     * @param id the id of the evaluationClotureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evaluation-clotures/{id}")
    public ResponseEntity<Void> deleteEvaluationCloture(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationCloture : {}", id);
        evaluationClotureService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
