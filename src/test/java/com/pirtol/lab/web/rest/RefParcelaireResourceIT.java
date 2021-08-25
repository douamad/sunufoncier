package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.Commune;
import com.pirtol.lab.domain.Dossier;
import com.pirtol.lab.domain.RefParcelaire;
import com.pirtol.lab.repository.RefParcelaireRepository;
import com.pirtol.lab.service.criteria.RefParcelaireCriteria;
import com.pirtol.lab.service.dto.RefParcelaireDTO;
import com.pirtol.lab.service.mapper.RefParcelaireMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RefParcelaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RefParcelaireResourceIT {

    private static final String DEFAULT_NUMERO_PARCELLE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PARCELLE = "BBBBBBBBBB";

    private static final String DEFAULT_NATURE_PARCELLE = "AAAAAAAAAA";
    private static final String UPDATED_NATURE_PARCELLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BATIE = false;
    private static final Boolean UPDATED_BATIE = true;

    private static final String ENTITY_API_URL = "/api/ref-parcelaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RefParcelaireRepository refParcelaireRepository;

    @Autowired
    private RefParcelaireMapper refParcelaireMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRefParcelaireMockMvc;

    private RefParcelaire refParcelaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RefParcelaire createEntity(EntityManager em) {
        RefParcelaire refParcelaire = new RefParcelaire()
            .numeroParcelle(DEFAULT_NUMERO_PARCELLE)
            .natureParcelle(DEFAULT_NATURE_PARCELLE)
            .batie(DEFAULT_BATIE);
        return refParcelaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RefParcelaire createUpdatedEntity(EntityManager em) {
        RefParcelaire refParcelaire = new RefParcelaire()
            .numeroParcelle(UPDATED_NUMERO_PARCELLE)
            .natureParcelle(UPDATED_NATURE_PARCELLE)
            .batie(UPDATED_BATIE);
        return refParcelaire;
    }

    @BeforeEach
    public void initTest() {
        refParcelaire = createEntity(em);
    }

    @Test
    @Transactional
    void createRefParcelaire() throws Exception {
        int databaseSizeBeforeCreate = refParcelaireRepository.findAll().size();
        // Create the RefParcelaire
        RefParcelaireDTO refParcelaireDTO = refParcelaireMapper.toDto(refParcelaire);
        restRefParcelaireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refParcelaireDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RefParcelaire in the database
        List<RefParcelaire> refParcelaireList = refParcelaireRepository.findAll();
        assertThat(refParcelaireList).hasSize(databaseSizeBeforeCreate + 1);
        RefParcelaire testRefParcelaire = refParcelaireList.get(refParcelaireList.size() - 1);
        assertThat(testRefParcelaire.getNumeroParcelle()).isEqualTo(DEFAULT_NUMERO_PARCELLE);
        assertThat(testRefParcelaire.getNatureParcelle()).isEqualTo(DEFAULT_NATURE_PARCELLE);
        assertThat(testRefParcelaire.getBatie()).isEqualTo(DEFAULT_BATIE);
    }

    @Test
    @Transactional
    void createRefParcelaireWithExistingId() throws Exception {
        // Create the RefParcelaire with an existing ID
        refParcelaire.setId(1L);
        RefParcelaireDTO refParcelaireDTO = refParcelaireMapper.toDto(refParcelaire);

        int databaseSizeBeforeCreate = refParcelaireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefParcelaireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refParcelaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RefParcelaire in the database
        List<RefParcelaire> refParcelaireList = refParcelaireRepository.findAll();
        assertThat(refParcelaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRefParcelaires() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList
        restRefParcelaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refParcelaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroParcelle").value(hasItem(DEFAULT_NUMERO_PARCELLE)))
            .andExpect(jsonPath("$.[*].natureParcelle").value(hasItem(DEFAULT_NATURE_PARCELLE)))
            .andExpect(jsonPath("$.[*].batie").value(hasItem(DEFAULT_BATIE.booleanValue())));
    }

    @Test
    @Transactional
    void getRefParcelaire() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get the refParcelaire
        restRefParcelaireMockMvc
            .perform(get(ENTITY_API_URL_ID, refParcelaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(refParcelaire.getId().intValue()))
            .andExpect(jsonPath("$.numeroParcelle").value(DEFAULT_NUMERO_PARCELLE))
            .andExpect(jsonPath("$.natureParcelle").value(DEFAULT_NATURE_PARCELLE))
            .andExpect(jsonPath("$.batie").value(DEFAULT_BATIE.booleanValue()));
    }

    @Test
    @Transactional
    void getRefParcelairesByIdFiltering() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        Long id = refParcelaire.getId();

        defaultRefParcelaireShouldBeFound("id.equals=" + id);
        defaultRefParcelaireShouldNotBeFound("id.notEquals=" + id);

        defaultRefParcelaireShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRefParcelaireShouldNotBeFound("id.greaterThan=" + id);

        defaultRefParcelaireShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRefParcelaireShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRefParcelairesByNumeroParcelleIsEqualToSomething() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where numeroParcelle equals to DEFAULT_NUMERO_PARCELLE
        defaultRefParcelaireShouldBeFound("numeroParcelle.equals=" + DEFAULT_NUMERO_PARCELLE);

        // Get all the refParcelaireList where numeroParcelle equals to UPDATED_NUMERO_PARCELLE
        defaultRefParcelaireShouldNotBeFound("numeroParcelle.equals=" + UPDATED_NUMERO_PARCELLE);
    }

    @Test
    @Transactional
    void getAllRefParcelairesByNumeroParcelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where numeroParcelle not equals to DEFAULT_NUMERO_PARCELLE
        defaultRefParcelaireShouldNotBeFound("numeroParcelle.notEquals=" + DEFAULT_NUMERO_PARCELLE);

        // Get all the refParcelaireList where numeroParcelle not equals to UPDATED_NUMERO_PARCELLE
        defaultRefParcelaireShouldBeFound("numeroParcelle.notEquals=" + UPDATED_NUMERO_PARCELLE);
    }

    @Test
    @Transactional
    void getAllRefParcelairesByNumeroParcelleIsInShouldWork() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where numeroParcelle in DEFAULT_NUMERO_PARCELLE or UPDATED_NUMERO_PARCELLE
        defaultRefParcelaireShouldBeFound("numeroParcelle.in=" + DEFAULT_NUMERO_PARCELLE + "," + UPDATED_NUMERO_PARCELLE);

        // Get all the refParcelaireList where numeroParcelle equals to UPDATED_NUMERO_PARCELLE
        defaultRefParcelaireShouldNotBeFound("numeroParcelle.in=" + UPDATED_NUMERO_PARCELLE);
    }

    @Test
    @Transactional
    void getAllRefParcelairesByNumeroParcelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where numeroParcelle is not null
        defaultRefParcelaireShouldBeFound("numeroParcelle.specified=true");

        // Get all the refParcelaireList where numeroParcelle is null
        defaultRefParcelaireShouldNotBeFound("numeroParcelle.specified=false");
    }

    @Test
    @Transactional
    void getAllRefParcelairesByNumeroParcelleContainsSomething() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where numeroParcelle contains DEFAULT_NUMERO_PARCELLE
        defaultRefParcelaireShouldBeFound("numeroParcelle.contains=" + DEFAULT_NUMERO_PARCELLE);

        // Get all the refParcelaireList where numeroParcelle contains UPDATED_NUMERO_PARCELLE
        defaultRefParcelaireShouldNotBeFound("numeroParcelle.contains=" + UPDATED_NUMERO_PARCELLE);
    }

    @Test
    @Transactional
    void getAllRefParcelairesByNumeroParcelleNotContainsSomething() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where numeroParcelle does not contain DEFAULT_NUMERO_PARCELLE
        defaultRefParcelaireShouldNotBeFound("numeroParcelle.doesNotContain=" + DEFAULT_NUMERO_PARCELLE);

        // Get all the refParcelaireList where numeroParcelle does not contain UPDATED_NUMERO_PARCELLE
        defaultRefParcelaireShouldBeFound("numeroParcelle.doesNotContain=" + UPDATED_NUMERO_PARCELLE);
    }

    @Test
    @Transactional
    void getAllRefParcelairesByNatureParcelleIsEqualToSomething() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where natureParcelle equals to DEFAULT_NATURE_PARCELLE
        defaultRefParcelaireShouldBeFound("natureParcelle.equals=" + DEFAULT_NATURE_PARCELLE);

        // Get all the refParcelaireList where natureParcelle equals to UPDATED_NATURE_PARCELLE
        defaultRefParcelaireShouldNotBeFound("natureParcelle.equals=" + UPDATED_NATURE_PARCELLE);
    }

    @Test
    @Transactional
    void getAllRefParcelairesByNatureParcelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where natureParcelle not equals to DEFAULT_NATURE_PARCELLE
        defaultRefParcelaireShouldNotBeFound("natureParcelle.notEquals=" + DEFAULT_NATURE_PARCELLE);

        // Get all the refParcelaireList where natureParcelle not equals to UPDATED_NATURE_PARCELLE
        defaultRefParcelaireShouldBeFound("natureParcelle.notEquals=" + UPDATED_NATURE_PARCELLE);
    }

    @Test
    @Transactional
    void getAllRefParcelairesByNatureParcelleIsInShouldWork() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where natureParcelle in DEFAULT_NATURE_PARCELLE or UPDATED_NATURE_PARCELLE
        defaultRefParcelaireShouldBeFound("natureParcelle.in=" + DEFAULT_NATURE_PARCELLE + "," + UPDATED_NATURE_PARCELLE);

        // Get all the refParcelaireList where natureParcelle equals to UPDATED_NATURE_PARCELLE
        defaultRefParcelaireShouldNotBeFound("natureParcelle.in=" + UPDATED_NATURE_PARCELLE);
    }

    @Test
    @Transactional
    void getAllRefParcelairesByNatureParcelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where natureParcelle is not null
        defaultRefParcelaireShouldBeFound("natureParcelle.specified=true");

        // Get all the refParcelaireList where natureParcelle is null
        defaultRefParcelaireShouldNotBeFound("natureParcelle.specified=false");
    }

    @Test
    @Transactional
    void getAllRefParcelairesByNatureParcelleContainsSomething() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where natureParcelle contains DEFAULT_NATURE_PARCELLE
        defaultRefParcelaireShouldBeFound("natureParcelle.contains=" + DEFAULT_NATURE_PARCELLE);

        // Get all the refParcelaireList where natureParcelle contains UPDATED_NATURE_PARCELLE
        defaultRefParcelaireShouldNotBeFound("natureParcelle.contains=" + UPDATED_NATURE_PARCELLE);
    }

    @Test
    @Transactional
    void getAllRefParcelairesByNatureParcelleNotContainsSomething() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where natureParcelle does not contain DEFAULT_NATURE_PARCELLE
        defaultRefParcelaireShouldNotBeFound("natureParcelle.doesNotContain=" + DEFAULT_NATURE_PARCELLE);

        // Get all the refParcelaireList where natureParcelle does not contain UPDATED_NATURE_PARCELLE
        defaultRefParcelaireShouldBeFound("natureParcelle.doesNotContain=" + UPDATED_NATURE_PARCELLE);
    }

    @Test
    @Transactional
    void getAllRefParcelairesByBatieIsEqualToSomething() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where batie equals to DEFAULT_BATIE
        defaultRefParcelaireShouldBeFound("batie.equals=" + DEFAULT_BATIE);

        // Get all the refParcelaireList where batie equals to UPDATED_BATIE
        defaultRefParcelaireShouldNotBeFound("batie.equals=" + UPDATED_BATIE);
    }

    @Test
    @Transactional
    void getAllRefParcelairesByBatieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where batie not equals to DEFAULT_BATIE
        defaultRefParcelaireShouldNotBeFound("batie.notEquals=" + DEFAULT_BATIE);

        // Get all the refParcelaireList where batie not equals to UPDATED_BATIE
        defaultRefParcelaireShouldBeFound("batie.notEquals=" + UPDATED_BATIE);
    }

    @Test
    @Transactional
    void getAllRefParcelairesByBatieIsInShouldWork() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where batie in DEFAULT_BATIE or UPDATED_BATIE
        defaultRefParcelaireShouldBeFound("batie.in=" + DEFAULT_BATIE + "," + UPDATED_BATIE);

        // Get all the refParcelaireList where batie equals to UPDATED_BATIE
        defaultRefParcelaireShouldNotBeFound("batie.in=" + UPDATED_BATIE);
    }

    @Test
    @Transactional
    void getAllRefParcelairesByBatieIsNullOrNotNull() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        // Get all the refParcelaireList where batie is not null
        defaultRefParcelaireShouldBeFound("batie.specified=true");

        // Get all the refParcelaireList where batie is null
        defaultRefParcelaireShouldNotBeFound("batie.specified=false");
    }

    @Test
    @Transactional
    void getAllRefParcelairesByDossierIsEqualToSomething() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);
        Dossier dossier = DossierResourceIT.createEntity(em);
        em.persist(dossier);
        em.flush();
        refParcelaire.addDossier(dossier);
        refParcelaireRepository.saveAndFlush(refParcelaire);
        Long dossierId = dossier.getId();

        // Get all the refParcelaireList where dossier equals to dossierId
        defaultRefParcelaireShouldBeFound("dossierId.equals=" + dossierId);

        // Get all the refParcelaireList where dossier equals to (dossierId + 1)
        defaultRefParcelaireShouldNotBeFound("dossierId.equals=" + (dossierId + 1));
    }

    @Test
    @Transactional
    void getAllRefParcelairesByCommuneIsEqualToSomething() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);
        Commune commune = CommuneResourceIT.createEntity(em);
        em.persist(commune);
        em.flush();
        refParcelaire.setCommune(commune);
        refParcelaireRepository.saveAndFlush(refParcelaire);
        Long communeId = commune.getId();

        // Get all the refParcelaireList where commune equals to communeId
        defaultRefParcelaireShouldBeFound("communeId.equals=" + communeId);

        // Get all the refParcelaireList where commune equals to (communeId + 1)
        defaultRefParcelaireShouldNotBeFound("communeId.equals=" + (communeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRefParcelaireShouldBeFound(String filter) throws Exception {
        restRefParcelaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refParcelaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroParcelle").value(hasItem(DEFAULT_NUMERO_PARCELLE)))
            .andExpect(jsonPath("$.[*].natureParcelle").value(hasItem(DEFAULT_NATURE_PARCELLE)))
            .andExpect(jsonPath("$.[*].batie").value(hasItem(DEFAULT_BATIE.booleanValue())));

        // Check, that the count call also returns 1
        restRefParcelaireMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRefParcelaireShouldNotBeFound(String filter) throws Exception {
        restRefParcelaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRefParcelaireMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRefParcelaire() throws Exception {
        // Get the refParcelaire
        restRefParcelaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRefParcelaire() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        int databaseSizeBeforeUpdate = refParcelaireRepository.findAll().size();

        // Update the refParcelaire
        RefParcelaire updatedRefParcelaire = refParcelaireRepository.findById(refParcelaire.getId()).get();
        // Disconnect from session so that the updates on updatedRefParcelaire are not directly saved in db
        em.detach(updatedRefParcelaire);
        updatedRefParcelaire.numeroParcelle(UPDATED_NUMERO_PARCELLE).natureParcelle(UPDATED_NATURE_PARCELLE).batie(UPDATED_BATIE);
        RefParcelaireDTO refParcelaireDTO = refParcelaireMapper.toDto(updatedRefParcelaire);

        restRefParcelaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, refParcelaireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refParcelaireDTO))
            )
            .andExpect(status().isOk());

        // Validate the RefParcelaire in the database
        List<RefParcelaire> refParcelaireList = refParcelaireRepository.findAll();
        assertThat(refParcelaireList).hasSize(databaseSizeBeforeUpdate);
        RefParcelaire testRefParcelaire = refParcelaireList.get(refParcelaireList.size() - 1);
        assertThat(testRefParcelaire.getNumeroParcelle()).isEqualTo(UPDATED_NUMERO_PARCELLE);
        assertThat(testRefParcelaire.getNatureParcelle()).isEqualTo(UPDATED_NATURE_PARCELLE);
        assertThat(testRefParcelaire.getBatie()).isEqualTo(UPDATED_BATIE);
    }

    @Test
    @Transactional
    void putNonExistingRefParcelaire() throws Exception {
        int databaseSizeBeforeUpdate = refParcelaireRepository.findAll().size();
        refParcelaire.setId(count.incrementAndGet());

        // Create the RefParcelaire
        RefParcelaireDTO refParcelaireDTO = refParcelaireMapper.toDto(refParcelaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefParcelaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, refParcelaireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refParcelaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RefParcelaire in the database
        List<RefParcelaire> refParcelaireList = refParcelaireRepository.findAll();
        assertThat(refParcelaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRefParcelaire() throws Exception {
        int databaseSizeBeforeUpdate = refParcelaireRepository.findAll().size();
        refParcelaire.setId(count.incrementAndGet());

        // Create the RefParcelaire
        RefParcelaireDTO refParcelaireDTO = refParcelaireMapper.toDto(refParcelaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefParcelaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refParcelaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RefParcelaire in the database
        List<RefParcelaire> refParcelaireList = refParcelaireRepository.findAll();
        assertThat(refParcelaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRefParcelaire() throws Exception {
        int databaseSizeBeforeUpdate = refParcelaireRepository.findAll().size();
        refParcelaire.setId(count.incrementAndGet());

        // Create the RefParcelaire
        RefParcelaireDTO refParcelaireDTO = refParcelaireMapper.toDto(refParcelaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefParcelaireMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refParcelaireDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RefParcelaire in the database
        List<RefParcelaire> refParcelaireList = refParcelaireRepository.findAll();
        assertThat(refParcelaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRefParcelaireWithPatch() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        int databaseSizeBeforeUpdate = refParcelaireRepository.findAll().size();

        // Update the refParcelaire using partial update
        RefParcelaire partialUpdatedRefParcelaire = new RefParcelaire();
        partialUpdatedRefParcelaire.setId(refParcelaire.getId());

        partialUpdatedRefParcelaire.natureParcelle(UPDATED_NATURE_PARCELLE).batie(UPDATED_BATIE);

        restRefParcelaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRefParcelaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRefParcelaire))
            )
            .andExpect(status().isOk());

        // Validate the RefParcelaire in the database
        List<RefParcelaire> refParcelaireList = refParcelaireRepository.findAll();
        assertThat(refParcelaireList).hasSize(databaseSizeBeforeUpdate);
        RefParcelaire testRefParcelaire = refParcelaireList.get(refParcelaireList.size() - 1);
        assertThat(testRefParcelaire.getNumeroParcelle()).isEqualTo(DEFAULT_NUMERO_PARCELLE);
        assertThat(testRefParcelaire.getNatureParcelle()).isEqualTo(UPDATED_NATURE_PARCELLE);
        assertThat(testRefParcelaire.getBatie()).isEqualTo(UPDATED_BATIE);
    }

    @Test
    @Transactional
    void fullUpdateRefParcelaireWithPatch() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        int databaseSizeBeforeUpdate = refParcelaireRepository.findAll().size();

        // Update the refParcelaire using partial update
        RefParcelaire partialUpdatedRefParcelaire = new RefParcelaire();
        partialUpdatedRefParcelaire.setId(refParcelaire.getId());

        partialUpdatedRefParcelaire.numeroParcelle(UPDATED_NUMERO_PARCELLE).natureParcelle(UPDATED_NATURE_PARCELLE).batie(UPDATED_BATIE);

        restRefParcelaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRefParcelaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRefParcelaire))
            )
            .andExpect(status().isOk());

        // Validate the RefParcelaire in the database
        List<RefParcelaire> refParcelaireList = refParcelaireRepository.findAll();
        assertThat(refParcelaireList).hasSize(databaseSizeBeforeUpdate);
        RefParcelaire testRefParcelaire = refParcelaireList.get(refParcelaireList.size() - 1);
        assertThat(testRefParcelaire.getNumeroParcelle()).isEqualTo(UPDATED_NUMERO_PARCELLE);
        assertThat(testRefParcelaire.getNatureParcelle()).isEqualTo(UPDATED_NATURE_PARCELLE);
        assertThat(testRefParcelaire.getBatie()).isEqualTo(UPDATED_BATIE);
    }

    @Test
    @Transactional
    void patchNonExistingRefParcelaire() throws Exception {
        int databaseSizeBeforeUpdate = refParcelaireRepository.findAll().size();
        refParcelaire.setId(count.incrementAndGet());

        // Create the RefParcelaire
        RefParcelaireDTO refParcelaireDTO = refParcelaireMapper.toDto(refParcelaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefParcelaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, refParcelaireDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refParcelaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RefParcelaire in the database
        List<RefParcelaire> refParcelaireList = refParcelaireRepository.findAll();
        assertThat(refParcelaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRefParcelaire() throws Exception {
        int databaseSizeBeforeUpdate = refParcelaireRepository.findAll().size();
        refParcelaire.setId(count.incrementAndGet());

        // Create the RefParcelaire
        RefParcelaireDTO refParcelaireDTO = refParcelaireMapper.toDto(refParcelaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefParcelaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refParcelaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RefParcelaire in the database
        List<RefParcelaire> refParcelaireList = refParcelaireRepository.findAll();
        assertThat(refParcelaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRefParcelaire() throws Exception {
        int databaseSizeBeforeUpdate = refParcelaireRepository.findAll().size();
        refParcelaire.setId(count.incrementAndGet());

        // Create the RefParcelaire
        RefParcelaireDTO refParcelaireDTO = refParcelaireMapper.toDto(refParcelaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefParcelaireMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refParcelaireDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RefParcelaire in the database
        List<RefParcelaire> refParcelaireList = refParcelaireRepository.findAll();
        assertThat(refParcelaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRefParcelaire() throws Exception {
        // Initialize the database
        refParcelaireRepository.saveAndFlush(refParcelaire);

        int databaseSizeBeforeDelete = refParcelaireRepository.findAll().size();

        // Delete the refParcelaire
        restRefParcelaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, refParcelaire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RefParcelaire> refParcelaireList = refParcelaireRepository.findAll();
        assertThat(refParcelaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
