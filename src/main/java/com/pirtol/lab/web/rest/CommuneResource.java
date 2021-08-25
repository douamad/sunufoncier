package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.CommuneRepository;
import com.pirtol.lab.service.CommuneQueryService;
import com.pirtol.lab.service.CommuneService;
import com.pirtol.lab.service.criteria.CommuneCriteria;
import com.pirtol.lab.service.dto.CommuneDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.Commune}.
 */
@RestController
@RequestMapping("/api")
public class CommuneResource {

    private final Logger log = LoggerFactory.getLogger(CommuneResource.class);

    private static final String ENTITY_NAME = "commune";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommuneService communeService;

    private final CommuneRepository communeRepository;

    private final CommuneQueryService communeQueryService;

    public CommuneResource(CommuneService communeService, CommuneRepository communeRepository, CommuneQueryService communeQueryService) {
        this.communeService = communeService;
        this.communeRepository = communeRepository;
        this.communeQueryService = communeQueryService;
    }

    /**
     * {@code POST  /communes} : Create a new commune.
     *
     * @param communeDTO the communeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new communeDTO, or with status {@code 400 (Bad Request)} if the commune has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/communes")
    public ResponseEntity<CommuneDTO> createCommune(@RequestBody CommuneDTO communeDTO) throws URISyntaxException {
        log.debug("REST request to save Commune : {}", communeDTO);
        if (communeDTO.getId() != null) {
            throw new BadRequestAlertException("A new commune cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommuneDTO result = communeService.save(communeDTO);
        return ResponseEntity
            .created(new URI("/api/communes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /communes/:id} : Updates an existing commune.
     *
     * @param id the id of the communeDTO to save.
     * @param communeDTO the communeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated communeDTO,
     * or with status {@code 400 (Bad Request)} if the communeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the communeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/communes/{id}")
    public ResponseEntity<CommuneDTO> updateCommune(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommuneDTO communeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Commune : {}, {}", id, communeDTO);
        if (communeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, communeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!communeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommuneDTO result = communeService.save(communeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, communeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /communes/:id} : Partial updates given fields of an existing commune, field will ignore if it is null
     *
     * @param id the id of the communeDTO to save.
     * @param communeDTO the communeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated communeDTO,
     * or with status {@code 400 (Bad Request)} if the communeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the communeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the communeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/communes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CommuneDTO> partialUpdateCommune(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommuneDTO communeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Commune partially : {}, {}", id, communeDTO);
        if (communeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, communeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!communeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommuneDTO> result = communeService.partialUpdate(communeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, communeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /communes} : get all the communes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of communes in body.
     */
    @GetMapping("/communes")
    public ResponseEntity<List<CommuneDTO>> getAllCommunes(CommuneCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Communes by criteria: {}", criteria);
        Page<CommuneDTO> page = communeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /communes/count} : count all the communes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/communes/count")
    public ResponseEntity<Long> countCommunes(CommuneCriteria criteria) {
        log.debug("REST request to count Communes by criteria: {}", criteria);
        return ResponseEntity.ok().body(communeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /communes/:id} : get the "id" commune.
     *
     * @param id the id of the communeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the communeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/communes/{id}")
    public ResponseEntity<CommuneDTO> getCommune(@PathVariable Long id) {
        log.debug("REST request to get Commune : {}", id);
        Optional<CommuneDTO> communeDTO = communeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(communeDTO);
    }

    /**
     * {@code DELETE  /communes/:id} : delete the "id" commune.
     *
     * @param id the id of the communeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/communes/{id}")
    public ResponseEntity<Void> deleteCommune(@PathVariable Long id) {
        log.debug("REST request to delete Commune : {}", id);
        communeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
