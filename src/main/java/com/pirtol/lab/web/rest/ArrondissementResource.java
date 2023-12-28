package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.ArrondissementRepository;
import com.pirtol.lab.service.ArrondissementQueryService;
import com.pirtol.lab.service.ArrondissementService;
import com.pirtol.lab.service.criteria.ArrondissementCriteria;
import com.pirtol.lab.service.criteria.DepartementCriteria;
import com.pirtol.lab.service.dto.ArrondissementDTO;
import com.pirtol.lab.service.dto.DepartementDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.Arrondissement}.
 */
@RestController
@RequestMapping("/api")
public class ArrondissementResource {

    private final Logger log = LoggerFactory.getLogger(ArrondissementResource.class);

    private static final String ENTITY_NAME = "arrondissement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArrondissementService arrondissementService;

    private final ArrondissementRepository arrondissementRepository;

    private final ArrondissementQueryService arrondissementQueryService;

    public ArrondissementResource(
        ArrondissementService arrondissementService,
        ArrondissementRepository arrondissementRepository,
        ArrondissementQueryService arrondissementQueryService
    ) {
        this.arrondissementService = arrondissementService;
        this.arrondissementRepository = arrondissementRepository;
        this.arrondissementQueryService = arrondissementQueryService;
    }

    /**
     * {@code POST  /arrondissements} : Create a new arrondissement.
     *
     * @param arrondissementDTO the arrondissementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arrondissementDTO, or with status {@code 400 (Bad Request)} if the arrondissement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arrondissements")
    public ResponseEntity<ArrondissementDTO> createArrondissement(@RequestBody ArrondissementDTO arrondissementDTO)
        throws URISyntaxException {
        log.debug("REST request to save Arrondissement : {}", arrondissementDTO);
        if (arrondissementDTO.getId() != null) {
            throw new BadRequestAlertException("A new arrondissement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArrondissementDTO result = arrondissementService.save(arrondissementDTO);
        return ResponseEntity
            .created(new URI("/api/arrondissements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arrondissements/:id} : Updates an existing arrondissement.
     *
     * @param id the id of the arrondissementDTO to save.
     * @param arrondissementDTO the arrondissementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arrondissementDTO,
     * or with status {@code 400 (Bad Request)} if the arrondissementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arrondissementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arrondissements/{id}")
    public ResponseEntity<ArrondissementDTO> updateArrondissement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArrondissementDTO arrondissementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Arrondissement : {}, {}", id, arrondissementDTO);
        if (arrondissementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arrondissementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arrondissementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArrondissementDTO result = arrondissementService.save(arrondissementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, arrondissementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /arrondissements/:id} : Partial updates given fields of an existing arrondissement, field will ignore if it is null
     *
     * @param id the id of the arrondissementDTO to save.
     * @param arrondissementDTO the arrondissementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arrondissementDTO,
     * or with status {@code 400 (Bad Request)} if the arrondissementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the arrondissementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the arrondissementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/arrondissements/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ArrondissementDTO> partialUpdateArrondissement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArrondissementDTO arrondissementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Arrondissement partially : {}, {}", id, arrondissementDTO);
        if (arrondissementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arrondissementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arrondissementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArrondissementDTO> result = arrondissementService.partialUpdate(arrondissementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, arrondissementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /arrondissements} : get all the arrondissements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arrondissements in body.
     */
    @GetMapping("/arrondissements")
    public ResponseEntity<List<ArrondissementDTO>> getAllArrondissements(ArrondissementCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Arrondissements by criteria: {}", criteria);
        Page<ArrondissementDTO> page = arrondissementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /arrondissements/count} : count all the arrondissements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/arrondissements/count")
    public ResponseEntity<Long> countArrondissements(ArrondissementCriteria criteria) {
        log.debug("REST request to count Arrondissements by criteria: {}", criteria);
        return ResponseEntity.ok().body(arrondissementQueryService.countByCriteria(criteria));
    }

    @GetMapping("/arrondissements/all")
    public ResponseEntity<List<ArrondissementDTO>> getListAllArrondissements(ArrondissementCriteria criteria) {
        log.debug("REST request to get Arrondissement by criteria: {}", criteria);
        List<ArrondissementDTO> list = arrondissementQueryService.findByCriteria(criteria);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest());
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /arrondissements/:id} : get the "id" arrondissement.
     *
     * @param id the id of the arrondissementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arrondissementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arrondissements/{id}")
    public ResponseEntity<ArrondissementDTO> getArrondissement(@PathVariable Long id) {
        log.debug("REST request to get Arrondissement : {}", id);
        Optional<ArrondissementDTO> arrondissementDTO = arrondissementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(arrondissementDTO);
    }

    /**
     * {@code DELETE  /arrondissements/:id} : delete the "id" arrondissement.
     *
     * @param id the id of the arrondissementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arrondissements/{id}")
    public ResponseEntity<Void> deleteArrondissement(@PathVariable Long id) {
        log.debug("REST request to delete Arrondissement : {}", id);
        arrondissementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
