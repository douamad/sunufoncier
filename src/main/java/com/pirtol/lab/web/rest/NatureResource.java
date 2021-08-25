package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.NatureRepository;
import com.pirtol.lab.service.NatureQueryService;
import com.pirtol.lab.service.NatureService;
import com.pirtol.lab.service.criteria.NatureCriteria;
import com.pirtol.lab.service.dto.NatureDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.Nature}.
 */
@RestController
@RequestMapping("/api")
public class NatureResource {

    private final Logger log = LoggerFactory.getLogger(NatureResource.class);

    private static final String ENTITY_NAME = "nature";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NatureService natureService;

    private final NatureRepository natureRepository;

    private final NatureQueryService natureQueryService;

    public NatureResource(NatureService natureService, NatureRepository natureRepository, NatureQueryService natureQueryService) {
        this.natureService = natureService;
        this.natureRepository = natureRepository;
        this.natureQueryService = natureQueryService;
    }

    /**
     * {@code POST  /natures} : Create a new nature.
     *
     * @param natureDTO the natureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new natureDTO, or with status {@code 400 (Bad Request)} if the nature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/natures")
    public ResponseEntity<NatureDTO> createNature(@RequestBody NatureDTO natureDTO) throws URISyntaxException {
        log.debug("REST request to save Nature : {}", natureDTO);
        if (natureDTO.getId() != null) {
            throw new BadRequestAlertException("A new nature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NatureDTO result = natureService.save(natureDTO);
        return ResponseEntity
            .created(new URI("/api/natures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /natures/:id} : Updates an existing nature.
     *
     * @param id the id of the natureDTO to save.
     * @param natureDTO the natureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureDTO,
     * or with status {@code 400 (Bad Request)} if the natureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the natureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/natures/{id}")
    public ResponseEntity<NatureDTO> updateNature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureDTO natureDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Nature : {}, {}", id, natureDTO);
        if (natureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NatureDTO result = natureService.save(natureDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, natureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /natures/:id} : Partial updates given fields of an existing nature, field will ignore if it is null
     *
     * @param id the id of the natureDTO to save.
     * @param natureDTO the natureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureDTO,
     * or with status {@code 400 (Bad Request)} if the natureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the natureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the natureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/natures/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<NatureDTO> partialUpdateNature(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureDTO natureDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nature partially : {}, {}", id, natureDTO);
        if (natureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NatureDTO> result = natureService.partialUpdate(natureDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, natureDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /natures} : get all the natures.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of natures in body.
     */
    @GetMapping("/natures")
    public ResponseEntity<List<NatureDTO>> getAllNatures(NatureCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Natures by criteria: {}", criteria);
        Page<NatureDTO> page = natureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /natures/count} : count all the natures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/natures/count")
    public ResponseEntity<Long> countNatures(NatureCriteria criteria) {
        log.debug("REST request to count Natures by criteria: {}", criteria);
        return ResponseEntity.ok().body(natureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /natures/:id} : get the "id" nature.
     *
     * @param id the id of the natureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the natureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/natures/{id}")
    public ResponseEntity<NatureDTO> getNature(@PathVariable Long id) {
        log.debug("REST request to get Nature : {}", id);
        Optional<NatureDTO> natureDTO = natureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(natureDTO);
    }

    /**
     * {@code DELETE  /natures/:id} : delete the "id" nature.
     *
     * @param id the id of the natureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/natures/{id}")
    public ResponseEntity<Void> deleteNature(@PathVariable Long id) {
        log.debug("REST request to delete Nature : {}", id);
        natureService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
