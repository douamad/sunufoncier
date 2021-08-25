package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.CategorieCoursAmenage;
import com.pirtol.lab.domain.Dossier;
import com.pirtol.lab.domain.EvaluationCoursAmenage;
import com.pirtol.lab.repository.EvaluationCoursAmenageRepository;
import com.pirtol.lab.service.criteria.EvaluationCoursAmenageCriteria;
import com.pirtol.lab.service.dto.EvaluationCoursAmenageDTO;
import com.pirtol.lab.service.mapper.EvaluationCoursAmenageMapper;
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
 * Integration tests for the {@link EvaluationCoursAmenageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EvaluationCoursAmenageResourceIT {

    private static final Double DEFAULT_SURFACE = 1D;
    private static final Double UPDATED_SURFACE = 2D;
    private static final Double SMALLER_SURFACE = 1D - 1D;

    private static final Double DEFAULT_COEFF = 1D;
    private static final Double UPDATED_COEFF = 2D;
    private static final Double SMALLER_COEFF = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/evaluation-cours-amenages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EvaluationCoursAmenageRepository evaluationCoursAmenageRepository;

    @Autowired
    private EvaluationCoursAmenageMapper evaluationCoursAmenageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEvaluationCoursAmenageMockMvc;

    private EvaluationCoursAmenage evaluationCoursAmenage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluationCoursAmenage createEntity(EntityManager em) {
        EvaluationCoursAmenage evaluationCoursAmenage = new EvaluationCoursAmenage().surface(DEFAULT_SURFACE).coeff(DEFAULT_COEFF);
        return evaluationCoursAmenage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluationCoursAmenage createUpdatedEntity(EntityManager em) {
        EvaluationCoursAmenage evaluationCoursAmenage = new EvaluationCoursAmenage().surface(UPDATED_SURFACE).coeff(UPDATED_COEFF);
        return evaluationCoursAmenage;
    }

    @BeforeEach
    public void initTest() {
        evaluationCoursAmenage = createEntity(em);
    }

    @Test
    @Transactional
    void createEvaluationCoursAmenage() throws Exception {
        int databaseSizeBeforeCreate = evaluationCoursAmenageRepository.findAll().size();
        // Create the EvaluationCoursAmenage
        EvaluationCoursAmenageDTO evaluationCoursAmenageDTO = evaluationCoursAmenageMapper.toDto(evaluationCoursAmenage);
        restEvaluationCoursAmenageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationCoursAmenageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EvaluationCoursAmenage in the database
        List<EvaluationCoursAmenage> evaluationCoursAmenageList = evaluationCoursAmenageRepository.findAll();
        assertThat(evaluationCoursAmenageList).hasSize(databaseSizeBeforeCreate + 1);
        EvaluationCoursAmenage testEvaluationCoursAmenage = evaluationCoursAmenageList.get(evaluationCoursAmenageList.size() - 1);
        assertThat(testEvaluationCoursAmenage.getSurface()).isEqualTo(DEFAULT_SURFACE);
        assertThat(testEvaluationCoursAmenage.getCoeff()).isEqualTo(DEFAULT_COEFF);
    }

    @Test
    @Transactional
    void createEvaluationCoursAmenageWithExistingId() throws Exception {
        // Create the EvaluationCoursAmenage with an existing ID
        evaluationCoursAmenage.setId(1L);
        EvaluationCoursAmenageDTO evaluationCoursAmenageDTO = evaluationCoursAmenageMapper.toDto(evaluationCoursAmenage);

        int databaseSizeBeforeCreate = evaluationCoursAmenageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluationCoursAmenageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationCoursAmenageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCoursAmenage in the database
        List<EvaluationCoursAmenage> evaluationCoursAmenageList = evaluationCoursAmenageRepository.findAll();
        assertThat(evaluationCoursAmenageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenages() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList
        restEvaluationCoursAmenageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationCoursAmenage.getId().intValue())))
            .andExpect(jsonPath("$.[*].surface").value(hasItem(DEFAULT_SURFACE.doubleValue())))
            .andExpect(jsonPath("$.[*].coeff").value(hasItem(DEFAULT_COEFF.doubleValue())));
    }

    @Test
    @Transactional
    void getEvaluationCoursAmenage() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get the evaluationCoursAmenage
        restEvaluationCoursAmenageMockMvc
            .perform(get(ENTITY_API_URL_ID, evaluationCoursAmenage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evaluationCoursAmenage.getId().intValue()))
            .andExpect(jsonPath("$.surface").value(DEFAULT_SURFACE.doubleValue()))
            .andExpect(jsonPath("$.coeff").value(DEFAULT_COEFF.doubleValue()));
    }

    @Test
    @Transactional
    void getEvaluationCoursAmenagesByIdFiltering() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        Long id = evaluationCoursAmenage.getId();

        defaultEvaluationCoursAmenageShouldBeFound("id.equals=" + id);
        defaultEvaluationCoursAmenageShouldNotBeFound("id.notEquals=" + id);

        defaultEvaluationCoursAmenageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEvaluationCoursAmenageShouldNotBeFound("id.greaterThan=" + id);

        defaultEvaluationCoursAmenageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEvaluationCoursAmenageShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesBySurfaceIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where surface equals to DEFAULT_SURFACE
        defaultEvaluationCoursAmenageShouldBeFound("surface.equals=" + DEFAULT_SURFACE);

        // Get all the evaluationCoursAmenageList where surface equals to UPDATED_SURFACE
        defaultEvaluationCoursAmenageShouldNotBeFound("surface.equals=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesBySurfaceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where surface not equals to DEFAULT_SURFACE
        defaultEvaluationCoursAmenageShouldNotBeFound("surface.notEquals=" + DEFAULT_SURFACE);

        // Get all the evaluationCoursAmenageList where surface not equals to UPDATED_SURFACE
        defaultEvaluationCoursAmenageShouldBeFound("surface.notEquals=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesBySurfaceIsInShouldWork() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where surface in DEFAULT_SURFACE or UPDATED_SURFACE
        defaultEvaluationCoursAmenageShouldBeFound("surface.in=" + DEFAULT_SURFACE + "," + UPDATED_SURFACE);

        // Get all the evaluationCoursAmenageList where surface equals to UPDATED_SURFACE
        defaultEvaluationCoursAmenageShouldNotBeFound("surface.in=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesBySurfaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where surface is not null
        defaultEvaluationCoursAmenageShouldBeFound("surface.specified=true");

        // Get all the evaluationCoursAmenageList where surface is null
        defaultEvaluationCoursAmenageShouldNotBeFound("surface.specified=false");
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesBySurfaceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where surface is greater than or equal to DEFAULT_SURFACE
        defaultEvaluationCoursAmenageShouldBeFound("surface.greaterThanOrEqual=" + DEFAULT_SURFACE);

        // Get all the evaluationCoursAmenageList where surface is greater than or equal to UPDATED_SURFACE
        defaultEvaluationCoursAmenageShouldNotBeFound("surface.greaterThanOrEqual=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesBySurfaceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where surface is less than or equal to DEFAULT_SURFACE
        defaultEvaluationCoursAmenageShouldBeFound("surface.lessThanOrEqual=" + DEFAULT_SURFACE);

        // Get all the evaluationCoursAmenageList where surface is less than or equal to SMALLER_SURFACE
        defaultEvaluationCoursAmenageShouldNotBeFound("surface.lessThanOrEqual=" + SMALLER_SURFACE);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesBySurfaceIsLessThanSomething() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where surface is less than DEFAULT_SURFACE
        defaultEvaluationCoursAmenageShouldNotBeFound("surface.lessThan=" + DEFAULT_SURFACE);

        // Get all the evaluationCoursAmenageList where surface is less than UPDATED_SURFACE
        defaultEvaluationCoursAmenageShouldBeFound("surface.lessThan=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesBySurfaceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where surface is greater than DEFAULT_SURFACE
        defaultEvaluationCoursAmenageShouldNotBeFound("surface.greaterThan=" + DEFAULT_SURFACE);

        // Get all the evaluationCoursAmenageList where surface is greater than SMALLER_SURFACE
        defaultEvaluationCoursAmenageShouldBeFound("surface.greaterThan=" + SMALLER_SURFACE);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesByCoeffIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where coeff equals to DEFAULT_COEFF
        defaultEvaluationCoursAmenageShouldBeFound("coeff.equals=" + DEFAULT_COEFF);

        // Get all the evaluationCoursAmenageList where coeff equals to UPDATED_COEFF
        defaultEvaluationCoursAmenageShouldNotBeFound("coeff.equals=" + UPDATED_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesByCoeffIsNotEqualToSomething() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where coeff not equals to DEFAULT_COEFF
        defaultEvaluationCoursAmenageShouldNotBeFound("coeff.notEquals=" + DEFAULT_COEFF);

        // Get all the evaluationCoursAmenageList where coeff not equals to UPDATED_COEFF
        defaultEvaluationCoursAmenageShouldBeFound("coeff.notEquals=" + UPDATED_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesByCoeffIsInShouldWork() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where coeff in DEFAULT_COEFF or UPDATED_COEFF
        defaultEvaluationCoursAmenageShouldBeFound("coeff.in=" + DEFAULT_COEFF + "," + UPDATED_COEFF);

        // Get all the evaluationCoursAmenageList where coeff equals to UPDATED_COEFF
        defaultEvaluationCoursAmenageShouldNotBeFound("coeff.in=" + UPDATED_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesByCoeffIsNullOrNotNull() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where coeff is not null
        defaultEvaluationCoursAmenageShouldBeFound("coeff.specified=true");

        // Get all the evaluationCoursAmenageList where coeff is null
        defaultEvaluationCoursAmenageShouldNotBeFound("coeff.specified=false");
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesByCoeffIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where coeff is greater than or equal to DEFAULT_COEFF
        defaultEvaluationCoursAmenageShouldBeFound("coeff.greaterThanOrEqual=" + DEFAULT_COEFF);

        // Get all the evaluationCoursAmenageList where coeff is greater than or equal to UPDATED_COEFF
        defaultEvaluationCoursAmenageShouldNotBeFound("coeff.greaterThanOrEqual=" + UPDATED_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesByCoeffIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where coeff is less than or equal to DEFAULT_COEFF
        defaultEvaluationCoursAmenageShouldBeFound("coeff.lessThanOrEqual=" + DEFAULT_COEFF);

        // Get all the evaluationCoursAmenageList where coeff is less than or equal to SMALLER_COEFF
        defaultEvaluationCoursAmenageShouldNotBeFound("coeff.lessThanOrEqual=" + SMALLER_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesByCoeffIsLessThanSomething() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where coeff is less than DEFAULT_COEFF
        defaultEvaluationCoursAmenageShouldNotBeFound("coeff.lessThan=" + DEFAULT_COEFF);

        // Get all the evaluationCoursAmenageList where coeff is less than UPDATED_COEFF
        defaultEvaluationCoursAmenageShouldBeFound("coeff.lessThan=" + UPDATED_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesByCoeffIsGreaterThanSomething() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        // Get all the evaluationCoursAmenageList where coeff is greater than DEFAULT_COEFF
        defaultEvaluationCoursAmenageShouldNotBeFound("coeff.greaterThan=" + DEFAULT_COEFF);

        // Get all the evaluationCoursAmenageList where coeff is greater than SMALLER_COEFF
        defaultEvaluationCoursAmenageShouldBeFound("coeff.greaterThan=" + SMALLER_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesByCategorieCoursAmenageIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);
        CategorieCoursAmenage categorieCoursAmenage = CategorieCoursAmenageResourceIT.createEntity(em);
        em.persist(categorieCoursAmenage);
        em.flush();
        evaluationCoursAmenage.setCategorieCoursAmenage(categorieCoursAmenage);
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);
        Long categorieCoursAmenageId = categorieCoursAmenage.getId();

        // Get all the evaluationCoursAmenageList where categorieCoursAmenage equals to categorieCoursAmenageId
        defaultEvaluationCoursAmenageShouldBeFound("categorieCoursAmenageId.equals=" + categorieCoursAmenageId);

        // Get all the evaluationCoursAmenageList where categorieCoursAmenage equals to (categorieCoursAmenageId + 1)
        defaultEvaluationCoursAmenageShouldNotBeFound("categorieCoursAmenageId.equals=" + (categorieCoursAmenageId + 1));
    }

    @Test
    @Transactional
    void getAllEvaluationCoursAmenagesByDossierIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);
        Dossier dossier = DossierResourceIT.createEntity(em);
        em.persist(dossier);
        em.flush();
        evaluationCoursAmenage.setDossier(dossier);
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);
        Long dossierId = dossier.getId();

        // Get all the evaluationCoursAmenageList where dossier equals to dossierId
        defaultEvaluationCoursAmenageShouldBeFound("dossierId.equals=" + dossierId);

        // Get all the evaluationCoursAmenageList where dossier equals to (dossierId + 1)
        defaultEvaluationCoursAmenageShouldNotBeFound("dossierId.equals=" + (dossierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEvaluationCoursAmenageShouldBeFound(String filter) throws Exception {
        restEvaluationCoursAmenageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationCoursAmenage.getId().intValue())))
            .andExpect(jsonPath("$.[*].surface").value(hasItem(DEFAULT_SURFACE.doubleValue())))
            .andExpect(jsonPath("$.[*].coeff").value(hasItem(DEFAULT_COEFF.doubleValue())));

        // Check, that the count call also returns 1
        restEvaluationCoursAmenageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEvaluationCoursAmenageShouldNotBeFound(String filter) throws Exception {
        restEvaluationCoursAmenageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEvaluationCoursAmenageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEvaluationCoursAmenage() throws Exception {
        // Get the evaluationCoursAmenage
        restEvaluationCoursAmenageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEvaluationCoursAmenage() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        int databaseSizeBeforeUpdate = evaluationCoursAmenageRepository.findAll().size();

        // Update the evaluationCoursAmenage
        EvaluationCoursAmenage updatedEvaluationCoursAmenage = evaluationCoursAmenageRepository
            .findById(evaluationCoursAmenage.getId())
            .get();
        // Disconnect from session so that the updates on updatedEvaluationCoursAmenage are not directly saved in db
        em.detach(updatedEvaluationCoursAmenage);
        updatedEvaluationCoursAmenage.surface(UPDATED_SURFACE).coeff(UPDATED_COEFF);
        EvaluationCoursAmenageDTO evaluationCoursAmenageDTO = evaluationCoursAmenageMapper.toDto(updatedEvaluationCoursAmenage);

        restEvaluationCoursAmenageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluationCoursAmenageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationCoursAmenageDTO))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationCoursAmenage in the database
        List<EvaluationCoursAmenage> evaluationCoursAmenageList = evaluationCoursAmenageRepository.findAll();
        assertThat(evaluationCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
        EvaluationCoursAmenage testEvaluationCoursAmenage = evaluationCoursAmenageList.get(evaluationCoursAmenageList.size() - 1);
        assertThat(testEvaluationCoursAmenage.getSurface()).isEqualTo(UPDATED_SURFACE);
        assertThat(testEvaluationCoursAmenage.getCoeff()).isEqualTo(UPDATED_COEFF);
    }

    @Test
    @Transactional
    void putNonExistingEvaluationCoursAmenage() throws Exception {
        int databaseSizeBeforeUpdate = evaluationCoursAmenageRepository.findAll().size();
        evaluationCoursAmenage.setId(count.incrementAndGet());

        // Create the EvaluationCoursAmenage
        EvaluationCoursAmenageDTO evaluationCoursAmenageDTO = evaluationCoursAmenageMapper.toDto(evaluationCoursAmenage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationCoursAmenageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluationCoursAmenageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationCoursAmenageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCoursAmenage in the database
        List<EvaluationCoursAmenage> evaluationCoursAmenageList = evaluationCoursAmenageRepository.findAll();
        assertThat(evaluationCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvaluationCoursAmenage() throws Exception {
        int databaseSizeBeforeUpdate = evaluationCoursAmenageRepository.findAll().size();
        evaluationCoursAmenage.setId(count.incrementAndGet());

        // Create the EvaluationCoursAmenage
        EvaluationCoursAmenageDTO evaluationCoursAmenageDTO = evaluationCoursAmenageMapper.toDto(evaluationCoursAmenage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationCoursAmenageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationCoursAmenageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCoursAmenage in the database
        List<EvaluationCoursAmenage> evaluationCoursAmenageList = evaluationCoursAmenageRepository.findAll();
        assertThat(evaluationCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvaluationCoursAmenage() throws Exception {
        int databaseSizeBeforeUpdate = evaluationCoursAmenageRepository.findAll().size();
        evaluationCoursAmenage.setId(count.incrementAndGet());

        // Create the EvaluationCoursAmenage
        EvaluationCoursAmenageDTO evaluationCoursAmenageDTO = evaluationCoursAmenageMapper.toDto(evaluationCoursAmenage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationCoursAmenageMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationCoursAmenageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvaluationCoursAmenage in the database
        List<EvaluationCoursAmenage> evaluationCoursAmenageList = evaluationCoursAmenageRepository.findAll();
        assertThat(evaluationCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEvaluationCoursAmenageWithPatch() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        int databaseSizeBeforeUpdate = evaluationCoursAmenageRepository.findAll().size();

        // Update the evaluationCoursAmenage using partial update
        EvaluationCoursAmenage partialUpdatedEvaluationCoursAmenage = new EvaluationCoursAmenage();
        partialUpdatedEvaluationCoursAmenage.setId(evaluationCoursAmenage.getId());

        partialUpdatedEvaluationCoursAmenage.coeff(UPDATED_COEFF);

        restEvaluationCoursAmenageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluationCoursAmenage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluationCoursAmenage))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationCoursAmenage in the database
        List<EvaluationCoursAmenage> evaluationCoursAmenageList = evaluationCoursAmenageRepository.findAll();
        assertThat(evaluationCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
        EvaluationCoursAmenage testEvaluationCoursAmenage = evaluationCoursAmenageList.get(evaluationCoursAmenageList.size() - 1);
        assertThat(testEvaluationCoursAmenage.getSurface()).isEqualTo(DEFAULT_SURFACE);
        assertThat(testEvaluationCoursAmenage.getCoeff()).isEqualTo(UPDATED_COEFF);
    }

    @Test
    @Transactional
    void fullUpdateEvaluationCoursAmenageWithPatch() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        int databaseSizeBeforeUpdate = evaluationCoursAmenageRepository.findAll().size();

        // Update the evaluationCoursAmenage using partial update
        EvaluationCoursAmenage partialUpdatedEvaluationCoursAmenage = new EvaluationCoursAmenage();
        partialUpdatedEvaluationCoursAmenage.setId(evaluationCoursAmenage.getId());

        partialUpdatedEvaluationCoursAmenage.surface(UPDATED_SURFACE).coeff(UPDATED_COEFF);

        restEvaluationCoursAmenageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluationCoursAmenage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluationCoursAmenage))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationCoursAmenage in the database
        List<EvaluationCoursAmenage> evaluationCoursAmenageList = evaluationCoursAmenageRepository.findAll();
        assertThat(evaluationCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
        EvaluationCoursAmenage testEvaluationCoursAmenage = evaluationCoursAmenageList.get(evaluationCoursAmenageList.size() - 1);
        assertThat(testEvaluationCoursAmenage.getSurface()).isEqualTo(UPDATED_SURFACE);
        assertThat(testEvaluationCoursAmenage.getCoeff()).isEqualTo(UPDATED_COEFF);
    }

    @Test
    @Transactional
    void patchNonExistingEvaluationCoursAmenage() throws Exception {
        int databaseSizeBeforeUpdate = evaluationCoursAmenageRepository.findAll().size();
        evaluationCoursAmenage.setId(count.incrementAndGet());

        // Create the EvaluationCoursAmenage
        EvaluationCoursAmenageDTO evaluationCoursAmenageDTO = evaluationCoursAmenageMapper.toDto(evaluationCoursAmenage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationCoursAmenageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, evaluationCoursAmenageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationCoursAmenageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCoursAmenage in the database
        List<EvaluationCoursAmenage> evaluationCoursAmenageList = evaluationCoursAmenageRepository.findAll();
        assertThat(evaluationCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvaluationCoursAmenage() throws Exception {
        int databaseSizeBeforeUpdate = evaluationCoursAmenageRepository.findAll().size();
        evaluationCoursAmenage.setId(count.incrementAndGet());

        // Create the EvaluationCoursAmenage
        EvaluationCoursAmenageDTO evaluationCoursAmenageDTO = evaluationCoursAmenageMapper.toDto(evaluationCoursAmenage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationCoursAmenageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationCoursAmenageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCoursAmenage in the database
        List<EvaluationCoursAmenage> evaluationCoursAmenageList = evaluationCoursAmenageRepository.findAll();
        assertThat(evaluationCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvaluationCoursAmenage() throws Exception {
        int databaseSizeBeforeUpdate = evaluationCoursAmenageRepository.findAll().size();
        evaluationCoursAmenage.setId(count.incrementAndGet());

        // Create the EvaluationCoursAmenage
        EvaluationCoursAmenageDTO evaluationCoursAmenageDTO = evaluationCoursAmenageMapper.toDto(evaluationCoursAmenage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationCoursAmenageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationCoursAmenageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvaluationCoursAmenage in the database
        List<EvaluationCoursAmenage> evaluationCoursAmenageList = evaluationCoursAmenageRepository.findAll();
        assertThat(evaluationCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvaluationCoursAmenage() throws Exception {
        // Initialize the database
        evaluationCoursAmenageRepository.saveAndFlush(evaluationCoursAmenage);

        int databaseSizeBeforeDelete = evaluationCoursAmenageRepository.findAll().size();

        // Delete the evaluationCoursAmenage
        restEvaluationCoursAmenageMockMvc
            .perform(delete(ENTITY_API_URL_ID, evaluationCoursAmenage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EvaluationCoursAmenage> evaluationCoursAmenageList = evaluationCoursAmenageRepository.findAll();
        assertThat(evaluationCoursAmenageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
