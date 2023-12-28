package com.pirtol.lab.web.rest;

import com.pirtol.lab.domain.Refcadastrale;
import com.pirtol.lab.repository.RefcadastraleRepository;
import com.pirtol.lab.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.pirtol.lab.domain.Refcadastrale}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RefcadastraleResource {

    private final Logger log = LoggerFactory.getLogger(RefcadastraleResource.class);

    private static final String ENTITY_NAME = "refcadastrale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RefcadastraleRepository refcadastraleRepository;

    public RefcadastraleResource(RefcadastraleRepository refcadastraleRepository) {
        this.refcadastraleRepository = refcadastraleRepository;
    }

    /**
     * {@code POST  /refcadastrales} : Create a new refcadastrale.
     *
     * @param refcadastrale the refcadastrale to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new refcadastrale, or with status {@code 400 (Bad Request)} if the refcadastrale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/refcadastrales")
    public ResponseEntity<Refcadastrale> createRefcadastrale(@RequestBody Refcadastrale refcadastrale) throws URISyntaxException {
        log.debug("REST request to save Refcadastrale : {}", refcadastrale);
        if (refcadastrale.getId() != null) {
            throw new BadRequestAlertException("A new refcadastrale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Refcadastrale result = refcadastraleRepository.save(refcadastrale);
        return ResponseEntity
            .created(new URI("/api/refcadastrales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /refcadastrales/:id} : Updates an existing refcadastrale.
     *
     * @param id the id of the refcadastrale to save.
     * @param refcadastrale the refcadastrale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refcadastrale,
     * or with status {@code 400 (Bad Request)} if the refcadastrale is not valid,
     * or with status {@code 500 (Internal Server Error)} if the refcadastrale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/refcadastrales/{id}")
    public ResponseEntity<Refcadastrale> updateRefcadastrale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Refcadastrale refcadastrale
    ) throws URISyntaxException {
        log.debug("REST request to update Refcadastrale : {}, {}", id, refcadastrale);
        if (refcadastrale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, refcadastrale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!refcadastraleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Refcadastrale result = refcadastraleRepository.save(refcadastrale);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, refcadastrale.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /refcadastrales/:id} : Partial updates given fields of an existing refcadastrale, field will ignore if it is null
     *
     * @param id the id of the refcadastrale to save.
     * @param refcadastrale the refcadastrale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refcadastrale,
     * or with status {@code 400 (Bad Request)} if the refcadastrale is not valid,
     * or with status {@code 404 (Not Found)} if the refcadastrale is not found,
     * or with status {@code 500 (Internal Server Error)} if the refcadastrale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/refcadastrales/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Refcadastrale> partialUpdateRefcadastrale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Refcadastrale refcadastrale
    ) throws URISyntaxException {
        log.debug("REST request to partial update Refcadastrale partially : {}, {}", id, refcadastrale);
        if (refcadastrale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, refcadastrale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!refcadastraleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Refcadastrale> result = refcadastraleRepository
            .findById(refcadastrale.getId())
            .map(
                existingRefcadastrale -> {
                    if (refcadastrale.getCodeSection() != null) {
                        existingRefcadastrale.setCodeSection(refcadastrale.getCodeSection());
                    }
                    if (refcadastrale.getCodeParcelle() != null) {
                        existingRefcadastrale.setCodeParcelle(refcadastrale.getCodeParcelle());
                    }
                    if (refcadastrale.getNicad() != null) {
                        existingRefcadastrale.setNicad(refcadastrale.getNicad());
                    }
                    if (refcadastrale.getSuperfici() != null) {
                        existingRefcadastrale.setSuperfici(refcadastrale.getSuperfici());
                    }
                    if (refcadastrale.getTitreMere() != null) {
                        existingRefcadastrale.setTitreMere(refcadastrale.getTitreMere());
                    }
                    if (refcadastrale.getTitreCree() != null) {
                        existingRefcadastrale.setTitreCree(refcadastrale.getTitreCree());
                    }
                    if (refcadastrale.getNumeroRequisition() != null) {
                        existingRefcadastrale.setNumeroRequisition(refcadastrale.getNumeroRequisition());
                    }
                    if (refcadastrale.getDateBornage() != null) {
                        existingRefcadastrale.setDateBornage(refcadastrale.getDateBornage());
                    }
                    if (refcadastrale.getDependantDomaine() != null) {
                        existingRefcadastrale.setDependantDomaine(refcadastrale.getDependantDomaine());
                    }
                    if (refcadastrale.getNumeroDeliberation() != null) {
                        existingRefcadastrale.setNumeroDeliberation(refcadastrale.getNumeroDeliberation());
                    }
                    if (refcadastrale.getDateDeliberation() != null) {
                        existingRefcadastrale.setDateDeliberation(refcadastrale.getDateDeliberation());
                    }
                    if (refcadastrale.getNomGeometre() != null) {
                        existingRefcadastrale.setNomGeometre(refcadastrale.getNomGeometre());
                    }
                    if (refcadastrale.getIssueBornage() != null) {
                        existingRefcadastrale.setIssueBornage(refcadastrale.getIssueBornage());
                    }

                    return existingRefcadastrale;
                }
            )
            .map(refcadastraleRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, refcadastrale.getId().toString())
        );
    }

    /**
     * {@code GET  /refcadastrales} : get all the refcadastrales.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refcadastrales in body.
     */
    @GetMapping("/refcadastrales")
    public List<Refcadastrale> getAllRefcadastrales() {
        log.debug("REST request to get all Refcadastrales");
        return refcadastraleRepository.findAll();
    }

    /**
     * {@code GET  /refcadastrales/:id} : get the "id" refcadastrale.
     *
     * @param id the id of the refcadastrale to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the refcadastrale, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/refcadastrales/{id}")
    public ResponseEntity<Refcadastrale> getRefcadastrale(@PathVariable Long id) {
        log.debug("REST request to get Refcadastrale : {}", id);
        Optional<Refcadastrale> refcadastrale = refcadastraleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(refcadastrale);
    }

    /**
     * {@code DELETE  /refcadastrales/:id} : delete the "id" refcadastrale.
     *
     * @param id the id of the refcadastrale to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/refcadastrales/{id}")
    public ResponseEntity<Void> deleteRefcadastrale(@PathVariable Long id) {
        log.debug("REST request to delete Refcadastrale : {}", id);
        refcadastraleRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
