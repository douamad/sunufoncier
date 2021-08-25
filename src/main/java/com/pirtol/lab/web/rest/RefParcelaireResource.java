package com.pirtol.lab.web.rest;

import com.pirtol.lab.repository.RefParcelaireRepository;
import com.pirtol.lab.service.RefParcelaireQueryService;
import com.pirtol.lab.service.RefParcelaireService;
import com.pirtol.lab.service.criteria.RefParcelaireCriteria;
import com.pirtol.lab.service.dto.RefParcelaireDTO;
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
 * REST controller for managing {@link com.pirtol.lab.domain.RefParcelaire}.
 */
@RestController
@RequestMapping("/api")
public class RefParcelaireResource {

    private final Logger log = LoggerFactory.getLogger(RefParcelaireResource.class);

    private static final String ENTITY_NAME = "refParcelaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RefParcelaireService refParcelaireService;

    private final RefParcelaireRepository refParcelaireRepository;

    private final RefParcelaireQueryService refParcelaireQueryService;

    public RefParcelaireResource(
        RefParcelaireService refParcelaireService,
        RefParcelaireRepository refParcelaireRepository,
        RefParcelaireQueryService refParcelaireQueryService
    ) {
        this.refParcelaireService = refParcelaireService;
        this.refParcelaireRepository = refParcelaireRepository;
        this.refParcelaireQueryService = refParcelaireQueryService;
    }

    /**
     * {@code POST  /ref-parcelaires} : Create a new refParcelaire.
     *
     * @param refParcelaireDTO the refParcelaireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new refParcelaireDTO, or with status {@code 400 (Bad Request)} if the refParcelaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ref-parcelaires")
    public ResponseEntity<RefParcelaireDTO> createRefParcelaire(@RequestBody RefParcelaireDTO refParcelaireDTO) throws URISyntaxException {
        log.debug("REST request to save RefParcelaire : {}", refParcelaireDTO);
        if (refParcelaireDTO.getId() != null) {
            throw new BadRequestAlertException("A new refParcelaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RefParcelaireDTO result = refParcelaireService.save(refParcelaireDTO);
        return ResponseEntity
            .created(new URI("/api/ref-parcelaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ref-parcelaires/:id} : Updates an existing refParcelaire.
     *
     * @param id the id of the refParcelaireDTO to save.
     * @param refParcelaireDTO the refParcelaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refParcelaireDTO,
     * or with status {@code 400 (Bad Request)} if the refParcelaireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the refParcelaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ref-parcelaires/{id}")
    public ResponseEntity<RefParcelaireDTO> updateRefParcelaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RefParcelaireDTO refParcelaireDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RefParcelaire : {}, {}", id, refParcelaireDTO);
        if (refParcelaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, refParcelaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!refParcelaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RefParcelaireDTO result = refParcelaireService.save(refParcelaireDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, refParcelaireDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ref-parcelaires/:id} : Partial updates given fields of an existing refParcelaire, field will ignore if it is null
     *
     * @param id the id of the refParcelaireDTO to save.
     * @param refParcelaireDTO the refParcelaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refParcelaireDTO,
     * or with status {@code 400 (Bad Request)} if the refParcelaireDTO is not valid,
     * or with status {@code 404 (Not Found)} if the refParcelaireDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the refParcelaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ref-parcelaires/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RefParcelaireDTO> partialUpdateRefParcelaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RefParcelaireDTO refParcelaireDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RefParcelaire partially : {}, {}", id, refParcelaireDTO);
        if (refParcelaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, refParcelaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!refParcelaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RefParcelaireDTO> result = refParcelaireService.partialUpdate(refParcelaireDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, refParcelaireDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ref-parcelaires} : get all the refParcelaires.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refParcelaires in body.
     */
    @GetMapping("/ref-parcelaires")
    public ResponseEntity<List<RefParcelaireDTO>> getAllRefParcelaires(RefParcelaireCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RefParcelaires by criteria: {}", criteria);
        Page<RefParcelaireDTO> page = refParcelaireQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ref-parcelaires/count} : count all the refParcelaires.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ref-parcelaires/count")
    public ResponseEntity<Long> countRefParcelaires(RefParcelaireCriteria criteria) {
        log.debug("REST request to count RefParcelaires by criteria: {}", criteria);
        return ResponseEntity.ok().body(refParcelaireQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ref-parcelaires/:id} : get the "id" refParcelaire.
     *
     * @param id the id of the refParcelaireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the refParcelaireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ref-parcelaires/{id}")
    public ResponseEntity<RefParcelaireDTO> getRefParcelaire(@PathVariable Long id) {
        log.debug("REST request to get RefParcelaire : {}", id);
        Optional<RefParcelaireDTO> refParcelaireDTO = refParcelaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(refParcelaireDTO);
    }

    /**
     * {@code DELETE  /ref-parcelaires/:id} : delete the "id" refParcelaire.
     *
     * @param id the id of the refParcelaireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ref-parcelaires/{id}")
    public ResponseEntity<Void> deleteRefParcelaire(@PathVariable Long id) {
        log.debug("REST request to delete RefParcelaire : {}", id);
        refParcelaireService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
