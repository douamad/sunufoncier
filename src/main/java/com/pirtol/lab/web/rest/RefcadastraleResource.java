package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.RefcadastraleRepository;
import com.pirtol.lab.service.RefcadastraleQueryService;
import com.pirtol.lab.service.RefcadastraleService;
import com.pirtol.lab.service.criteria.RefcadastraleCriteria;
import com.pirtol.lab.service.dto.RefcadastraleDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.Refcadastrale}.
 */
@RestController
@RequestMapping("/api")
public class RefcadastraleResource {

    private final Logger log = LoggerFactory.getLogger(RefcadastraleResource.class);

    private static final String ENTITY_NAME = "refcadastrale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RefcadastraleService refcadastraleService;

    private final RefcadastraleRepository refcadastraleRepository;

    private final RefcadastraleQueryService refcadastraleQueryService;

    public RefcadastraleResource(
        RefcadastraleService refcadastraleService,
        RefcadastraleRepository refcadastraleRepository,
        RefcadastraleQueryService refcadastraleQueryService
    ) {
        this.refcadastraleService = refcadastraleService;
        this.refcadastraleRepository = refcadastraleRepository;
        this.refcadastraleQueryService = refcadastraleQueryService;
    }

    /**
     * {@code POST  /refcadastrales} : Create a new refcadastrale.
     *
     * @param refcadastraleDTO the refcadastraleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new refcadastraleDTO, or with status {@code 400 (Bad Request)} if the refcadastrale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/refcadastrales")
    public ResponseEntity<RefcadastraleDTO> createRefcadastrale(@RequestBody RefcadastraleDTO refcadastraleDTO) throws URISyntaxException {
        log.debug("REST request to save Refcadastrale : {}", refcadastraleDTO);
        if (refcadastraleDTO.getId() != null) {
            throw new BadRequestAlertException("A new refcadastrale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RefcadastraleDTO result = refcadastraleService.save(refcadastraleDTO);
        return ResponseEntity
            .created(new URI("/api/refcadastrales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /refcadastrales/:id} : Updates an existing refcadastrale.
     *
     * @param id the id of the refcadastraleDTO to save.
     * @param refcadastraleDTO the refcadastraleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refcadastraleDTO,
     * or with status {@code 400 (Bad Request)} if the refcadastraleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the refcadastraleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/refcadastrales/{id}")
    public ResponseEntity<RefcadastraleDTO> updateRefcadastrale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RefcadastraleDTO refcadastraleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Refcadastrale : {}, {}", id, refcadastraleDTO);
        if (refcadastraleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, refcadastraleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!refcadastraleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RefcadastraleDTO result = refcadastraleService.save(refcadastraleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, refcadastraleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /refcadastrales/:id} : Partial updates given fields of an existing refcadastrale, field will ignore if it is null
     *
     * @param id the id of the refcadastraleDTO to save.
     * @param refcadastraleDTO the refcadastraleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refcadastraleDTO,
     * or with status {@code 400 (Bad Request)} if the refcadastraleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the refcadastraleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the refcadastraleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/refcadastrales/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RefcadastraleDTO> partialUpdateRefcadastrale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RefcadastraleDTO refcadastraleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Refcadastrale partially : {}, {}", id, refcadastraleDTO);
        if (refcadastraleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, refcadastraleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!refcadastraleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RefcadastraleDTO> result = refcadastraleService.partialUpdate(refcadastraleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, refcadastraleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /refcadastrales} : get all the refcadastrales.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refcadastrales in body.
     */
    @GetMapping("/refcadastrales")
    public ResponseEntity<List<RefcadastraleDTO>> getAllRefcadastrales(RefcadastraleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Refcadastrales by criteria: {}", criteria);
        Page<RefcadastraleDTO> page = refcadastraleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /refcadastrales/count} : count all the refcadastrales.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/refcadastrales/count")
    public ResponseEntity<Long> countRefcadastrales(RefcadastraleCriteria criteria) {
        log.debug("REST request to count Refcadastrales by criteria: {}", criteria);
        return ResponseEntity.ok().body(refcadastraleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /refcadastrales/:id} : get the "id" refcadastrale.
     *
     * @param id the id of the refcadastraleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the refcadastraleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/refcadastrales/{id}")
    public ResponseEntity<RefcadastraleDTO> getRefcadastrale(@PathVariable Long id) {
        log.debug("REST request to get Refcadastrale : {}", id);
        Optional<RefcadastraleDTO> refcadastraleDTO = refcadastraleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(refcadastraleDTO);
    }

    /**
     * {@code DELETE  /refcadastrales/:id} : delete the "id" refcadastrale.
     *
     * @param id the id of the refcadastraleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/refcadastrales/{id}")
    public ResponseEntity<Void> deleteRefcadastrale(@PathVariable Long id) {
        log.debug("REST request to delete Refcadastrale : {}", id);
        refcadastraleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
