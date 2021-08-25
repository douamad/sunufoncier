package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.RepresentantRepository;
import com.pirtol.lab.service.RepresentantService;
import com.pirtol.lab.service.dto.RepresentantDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.Representant}.
 */
@RestController
@RequestMapping("/api")
public class RepresentantResource {

    private final Logger log = LoggerFactory.getLogger(RepresentantResource.class);

    private static final String ENTITY_NAME = "representant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RepresentantService representantService;

    private final RepresentantRepository representantRepository;

    public RepresentantResource(RepresentantService representantService, RepresentantRepository representantRepository) {
        this.representantService = representantService;
        this.representantRepository = representantRepository;
    }

    /**
     * {@code POST  /representants} : Create a new representant.
     *
     * @param representantDTO the representantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new representantDTO, or with status {@code 400 (Bad Request)} if the representant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/representants")
    public ResponseEntity<RepresentantDTO> createRepresentant(@RequestBody RepresentantDTO representantDTO) throws URISyntaxException {
        log.debug("REST request to save Representant : {}", representantDTO);
        if (representantDTO.getId() != null) {
            throw new BadRequestAlertException("A new representant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RepresentantDTO result = representantService.save(representantDTO);
        return ResponseEntity
            .created(new URI("/api/representants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /representants/:id} : Updates an existing representant.
     *
     * @param id the id of the representantDTO to save.
     * @param representantDTO the representantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated representantDTO,
     * or with status {@code 400 (Bad Request)} if the representantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the representantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/representants/{id}")
    public ResponseEntity<RepresentantDTO> updateRepresentant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RepresentantDTO representantDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Representant : {}, {}", id, representantDTO);
        if (representantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, representantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!representantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RepresentantDTO result = representantService.save(representantDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, representantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /representants/:id} : Partial updates given fields of an existing representant, field will ignore if it is null
     *
     * @param id the id of the representantDTO to save.
     * @param representantDTO the representantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated representantDTO,
     * or with status {@code 400 (Bad Request)} if the representantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the representantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the representantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/representants/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RepresentantDTO> partialUpdateRepresentant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RepresentantDTO representantDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Representant partially : {}, {}", id, representantDTO);
        if (representantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, representantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!representantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RepresentantDTO> result = representantService.partialUpdate(representantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, representantDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /representants} : get all the representants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of representants in body.
     */
    @GetMapping("/representants")
    public ResponseEntity<List<RepresentantDTO>> getAllRepresentants(Pageable pageable) {
        log.debug("REST request to get a page of Representants");
        Page<RepresentantDTO> page = representantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /representants/:id} : get the "id" representant.
     *
     * @param id the id of the representantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the representantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/representants/{id}")
    public ResponseEntity<RepresentantDTO> getRepresentant(@PathVariable Long id) {
        log.debug("REST request to get Representant : {}", id);
        Optional<RepresentantDTO> representantDTO = representantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(representantDTO);
    }

    /**
     * {@code DELETE  /representants/:id} : delete the "id" representant.
     *
     * @param id the id of the representantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/representants/{id}")
    public ResponseEntity<Void> deleteRepresentant(@PathVariable Long id) {
        log.debug("REST request to delete Representant : {}", id);
        representantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
