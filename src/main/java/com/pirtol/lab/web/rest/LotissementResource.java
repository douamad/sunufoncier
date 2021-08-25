package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.LotissementRepository;
import com.pirtol.lab.service.LotissementQueryService;
import com.pirtol.lab.service.LotissementService;
import com.pirtol.lab.service.criteria.LotissementCriteria;
import com.pirtol.lab.service.dto.LotissementDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.Lotissement}.
 */
@RestController
@RequestMapping("/api")
public class LotissementResource {

    private final Logger log = LoggerFactory.getLogger(LotissementResource.class);

    private static final String ENTITY_NAME = "lotissement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LotissementService lotissementService;

    private final LotissementRepository lotissementRepository;

    private final LotissementQueryService lotissementQueryService;

    public LotissementResource(
        LotissementService lotissementService,
        LotissementRepository lotissementRepository,
        LotissementQueryService lotissementQueryService
    ) {
        this.lotissementService = lotissementService;
        this.lotissementRepository = lotissementRepository;
        this.lotissementQueryService = lotissementQueryService;
    }

    /**
     * {@code POST  /lotissements} : Create a new lotissement.
     *
     * @param lotissementDTO the lotissementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lotissementDTO, or with status {@code 400 (Bad Request)} if the lotissement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lotissements")
    public ResponseEntity<LotissementDTO> createLotissement(@RequestBody LotissementDTO lotissementDTO) throws URISyntaxException {
        log.debug("REST request to save Lotissement : {}", lotissementDTO);
        if (lotissementDTO.getId() != null) {
            throw new BadRequestAlertException("A new lotissement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LotissementDTO result = lotissementService.save(lotissementDTO);
        return ResponseEntity
            .created(new URI("/api/lotissements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lotissements/:id} : Updates an existing lotissement.
     *
     * @param id the id of the lotissementDTO to save.
     * @param lotissementDTO the lotissementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lotissementDTO,
     * or with status {@code 400 (Bad Request)} if the lotissementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lotissementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lotissements/{id}")
    public ResponseEntity<LotissementDTO> updateLotissement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LotissementDTO lotissementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Lotissement : {}, {}", id, lotissementDTO);
        if (lotissementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lotissementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lotissementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LotissementDTO result = lotissementService.save(lotissementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lotissementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lotissements/:id} : Partial updates given fields of an existing lotissement, field will ignore if it is null
     *
     * @param id the id of the lotissementDTO to save.
     * @param lotissementDTO the lotissementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lotissementDTO,
     * or with status {@code 400 (Bad Request)} if the lotissementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the lotissementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the lotissementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lotissements/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<LotissementDTO> partialUpdateLotissement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LotissementDTO lotissementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Lotissement partially : {}, {}", id, lotissementDTO);
        if (lotissementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lotissementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lotissementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LotissementDTO> result = lotissementService.partialUpdate(lotissementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lotissementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /lotissements} : get all the lotissements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lotissements in body.
     */
    @GetMapping("/lotissements")
    public ResponseEntity<List<LotissementDTO>> getAllLotissements(LotissementCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Lotissements by criteria: {}", criteria);
        Page<LotissementDTO> page = lotissementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lotissements/count} : count all the lotissements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/lotissements/count")
    public ResponseEntity<Long> countLotissements(LotissementCriteria criteria) {
        log.debug("REST request to count Lotissements by criteria: {}", criteria);
        return ResponseEntity.ok().body(lotissementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /lotissements/:id} : get the "id" lotissement.
     *
     * @param id the id of the lotissementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lotissementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lotissements/{id}")
    public ResponseEntity<LotissementDTO> getLotissement(@PathVariable Long id) {
        log.debug("REST request to get Lotissement : {}", id);
        Optional<LotissementDTO> lotissementDTO = lotissementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lotissementDTO);
    }

    /**
     * {@code DELETE  /lotissements/:id} : delete the "id" lotissement.
     *
     * @param id the id of the lotissementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lotissements/{id}")
    public ResponseEntity<Void> deleteLotissement(@PathVariable Long id) {
        log.debug("REST request to delete Lotissement : {}", id);
        lotissementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
