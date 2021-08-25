package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.CategorieNature;
import com.pirtol.lab.domain.Dossier;
import com.pirtol.lab.domain.EvaluationBatiments;
import com.pirtol.lab.repository.EvaluationBatimentsRepository;
import com.pirtol.lab.service.criteria.EvaluationBatimentsCriteria;
import com.pirtol.lab.service.dto.EvaluationBatimentsDTO;
import com.pirtol.lab.service.mapper.EvaluationBatimentsMapper;
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
 * Integration tests for the {@link EvaluationBatimentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EvaluationBatimentsResourceIT {

    private static final Integer DEFAULT_NIVEAU = 1;
    private static final Integer UPDATED_NIVEAU = 2;
    private static final Integer SMALLER_NIVEAU = 1 - 1;

    private static final Double DEFAULT_SURFACE = 1D;
    private static final Double UPDATED_SURFACE = 2D;
    private static final Double SMALLER_SURFACE = 1D - 1D;

    private static final Double DEFAULT_COEFF = 1D;
    private static final Double UPDATED_COEFF = 2D;
    private static final Double SMALLER_COEFF = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/evaluation-batiments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EvaluationBatimentsRepository evaluationBatimentsRepository;

    @Autowired
    private EvaluationBatimentsMapper evaluationBatimentsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEvaluationBatimentsMockMvc;

    private EvaluationBatiments evaluationBatiments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluationBatiments createEntity(EntityManager em) {
        EvaluationBatiments evaluationBatiments = new EvaluationBatiments()
            .niveau(DEFAULT_NIVEAU)
            .surface(DEFAULT_SURFACE)
            .coeff(DEFAULT_COEFF);
        return evaluationBatiments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluationBatiments createUpdatedEntity(EntityManager em) {
        EvaluationBatiments evaluationBatiments = new EvaluationBatiments()
            .niveau(UPDATED_NIVEAU)
            .surface(UPDATED_SURFACE)
            .coeff(UPDATED_COEFF);
        return evaluationBatiments;
    }

    @BeforeEach
    public void initTest() {
        evaluationBatiments = createEntity(em);
    }

    @Test
    @Transactional
    void createEvaluationBatiments() throws Exception {
        int databaseSizeBeforeCreate = evaluationBatimentsRepository.findAll().size();
        // Create the EvaluationBatiments
        EvaluationBatimentsDTO evaluationBatimentsDTO = evaluationBatimentsMapper.toDto(evaluationBatiments);
        restEvaluationBatimentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationBatimentsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EvaluationBatiments in the database
        List<EvaluationBatiments> evaluationBatimentsList = evaluationBatimentsRepository.findAll();
        assertThat(evaluationBatimentsList).hasSize(databaseSizeBeforeCreate + 1);
        EvaluationBatiments testEvaluationBatiments = evaluationBatimentsList.get(evaluationBatimentsList.size() - 1);
        assertThat(testEvaluationBatiments.getNiveau()).isEqualTo(DEFAULT_NIVEAU);
        assertThat(testEvaluationBatiments.getSurface()).isEqualTo(DEFAULT_SURFACE);
        assertThat(testEvaluationBatiments.getCoeff()).isEqualTo(DEFAULT_COEFF);
    }

    @Test
    @Transactional
    void createEvaluationBatimentsWithExistingId() throws Exception {
        // Create the EvaluationBatiments with an existing ID
        evaluationBatiments.setId(1L);
        EvaluationBatimentsDTO evaluationBatimentsDTO = evaluationBatimentsMapper.toDto(evaluationBatiments);

        int databaseSizeBeforeCreate = evaluationBatimentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluationBatimentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationBatimentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationBatiments in the database
        List<EvaluationBatiments> evaluationBatimentsList = evaluationBatimentsRepository.findAll();
        assertThat(evaluationBatimentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEvaluationBatiments() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList
        restEvaluationBatimentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationBatiments.getId().intValue())))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU)))
            .andExpect(jsonPath("$.[*].surface").value(hasItem(DEFAULT_SURFACE.doubleValue())))
            .andExpect(jsonPath("$.[*].coeff").value(hasItem(DEFAULT_COEFF.doubleValue())));
    }

    @Test
    @Transactional
    void getEvaluationBatiments() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get the evaluationBatiments
        restEvaluationBatimentsMockMvc
            .perform(get(ENTITY_API_URL_ID, evaluationBatiments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evaluationBatiments.getId().intValue()))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU))
            .andExpect(jsonPath("$.surface").value(DEFAULT_SURFACE.doubleValue()))
            .andExpect(jsonPath("$.coeff").value(DEFAULT_COEFF.doubleValue()));
    }

    @Test
    @Transactional
    void getEvaluationBatimentsByIdFiltering() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        Long id = evaluationBatiments.getId();

        defaultEvaluationBatimentsShouldBeFound("id.equals=" + id);
        defaultEvaluationBatimentsShouldNotBeFound("id.notEquals=" + id);

        defaultEvaluationBatimentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEvaluationBatimentsShouldNotBeFound("id.greaterThan=" + id);

        defaultEvaluationBatimentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEvaluationBatimentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByNiveauIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where niveau equals to DEFAULT_NIVEAU
        defaultEvaluationBatimentsShouldBeFound("niveau.equals=" + DEFAULT_NIVEAU);

        // Get all the evaluationBatimentsList where niveau equals to UPDATED_NIVEAU
        defaultEvaluationBatimentsShouldNotBeFound("niveau.equals=" + UPDATED_NIVEAU);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByNiveauIsNotEqualToSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where niveau not equals to DEFAULT_NIVEAU
        defaultEvaluationBatimentsShouldNotBeFound("niveau.notEquals=" + DEFAULT_NIVEAU);

        // Get all the evaluationBatimentsList where niveau not equals to UPDATED_NIVEAU
        defaultEvaluationBatimentsShouldBeFound("niveau.notEquals=" + UPDATED_NIVEAU);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByNiveauIsInShouldWork() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where niveau in DEFAULT_NIVEAU or UPDATED_NIVEAU
        defaultEvaluationBatimentsShouldBeFound("niveau.in=" + DEFAULT_NIVEAU + "," + UPDATED_NIVEAU);

        // Get all the evaluationBatimentsList where niveau equals to UPDATED_NIVEAU
        defaultEvaluationBatimentsShouldNotBeFound("niveau.in=" + UPDATED_NIVEAU);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByNiveauIsNullOrNotNull() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where niveau is not null
        defaultEvaluationBatimentsShouldBeFound("niveau.specified=true");

        // Get all the evaluationBatimentsList where niveau is null
        defaultEvaluationBatimentsShouldNotBeFound("niveau.specified=false");
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByNiveauIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where niveau is greater than or equal to DEFAULT_NIVEAU
        defaultEvaluationBatimentsShouldBeFound("niveau.greaterThanOrEqual=" + DEFAULT_NIVEAU);

        // Get all the evaluationBatimentsList where niveau is greater than or equal to UPDATED_NIVEAU
        defaultEvaluationBatimentsShouldNotBeFound("niveau.greaterThanOrEqual=" + UPDATED_NIVEAU);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByNiveauIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where niveau is less than or equal to DEFAULT_NIVEAU
        defaultEvaluationBatimentsShouldBeFound("niveau.lessThanOrEqual=" + DEFAULT_NIVEAU);

        // Get all the evaluationBatimentsList where niveau is less than or equal to SMALLER_NIVEAU
        defaultEvaluationBatimentsShouldNotBeFound("niveau.lessThanOrEqual=" + SMALLER_NIVEAU);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByNiveauIsLessThanSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where niveau is less than DEFAULT_NIVEAU
        defaultEvaluationBatimentsShouldNotBeFound("niveau.lessThan=" + DEFAULT_NIVEAU);

        // Get all the evaluationBatimentsList where niveau is less than UPDATED_NIVEAU
        defaultEvaluationBatimentsShouldBeFound("niveau.lessThan=" + UPDATED_NIVEAU);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByNiveauIsGreaterThanSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where niveau is greater than DEFAULT_NIVEAU
        defaultEvaluationBatimentsShouldNotBeFound("niveau.greaterThan=" + DEFAULT_NIVEAU);

        // Get all the evaluationBatimentsList where niveau is greater than SMALLER_NIVEAU
        defaultEvaluationBatimentsShouldBeFound("niveau.greaterThan=" + SMALLER_NIVEAU);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsBySurfaceIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where surface equals to DEFAULT_SURFACE
        defaultEvaluationBatimentsShouldBeFound("surface.equals=" + DEFAULT_SURFACE);

        // Get all the evaluationBatimentsList where surface equals to UPDATED_SURFACE
        defaultEvaluationBatimentsShouldNotBeFound("surface.equals=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsBySurfaceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where surface not equals to DEFAULT_SURFACE
        defaultEvaluationBatimentsShouldNotBeFound("surface.notEquals=" + DEFAULT_SURFACE);

        // Get all the evaluationBatimentsList where surface not equals to UPDATED_SURFACE
        defaultEvaluationBatimentsShouldBeFound("surface.notEquals=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsBySurfaceIsInShouldWork() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where surface in DEFAULT_SURFACE or UPDATED_SURFACE
        defaultEvaluationBatimentsShouldBeFound("surface.in=" + DEFAULT_SURFACE + "," + UPDATED_SURFACE);

        // Get all the evaluationBatimentsList where surface equals to UPDATED_SURFACE
        defaultEvaluationBatimentsShouldNotBeFound("surface.in=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsBySurfaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where surface is not null
        defaultEvaluationBatimentsShouldBeFound("surface.specified=true");

        // Get all the evaluationBatimentsList where surface is null
        defaultEvaluationBatimentsShouldNotBeFound("surface.specified=false");
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsBySurfaceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where surface is greater than or equal to DEFAULT_SURFACE
        defaultEvaluationBatimentsShouldBeFound("surface.greaterThanOrEqual=" + DEFAULT_SURFACE);

        // Get all the evaluationBatimentsList where surface is greater than or equal to UPDATED_SURFACE
        defaultEvaluationBatimentsShouldNotBeFound("surface.greaterThanOrEqual=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsBySurfaceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where surface is less than or equal to DEFAULT_SURFACE
        defaultEvaluationBatimentsShouldBeFound("surface.lessThanOrEqual=" + DEFAULT_SURFACE);

        // Get all the evaluationBatimentsList where surface is less than or equal to SMALLER_SURFACE
        defaultEvaluationBatimentsShouldNotBeFound("surface.lessThanOrEqual=" + SMALLER_SURFACE);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsBySurfaceIsLessThanSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where surface is less than DEFAULT_SURFACE
        defaultEvaluationBatimentsShouldNotBeFound("surface.lessThan=" + DEFAULT_SURFACE);

        // Get all the evaluationBatimentsList where surface is less than UPDATED_SURFACE
        defaultEvaluationBatimentsShouldBeFound("surface.lessThan=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsBySurfaceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where surface is greater than DEFAULT_SURFACE
        defaultEvaluationBatimentsShouldNotBeFound("surface.greaterThan=" + DEFAULT_SURFACE);

        // Get all the evaluationBatimentsList where surface is greater than SMALLER_SURFACE
        defaultEvaluationBatimentsShouldBeFound("surface.greaterThan=" + SMALLER_SURFACE);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByCoeffIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where coeff equals to DEFAULT_COEFF
        defaultEvaluationBatimentsShouldBeFound("coeff.equals=" + DEFAULT_COEFF);

        // Get all the evaluationBatimentsList where coeff equals to UPDATED_COEFF
        defaultEvaluationBatimentsShouldNotBeFound("coeff.equals=" + UPDATED_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByCoeffIsNotEqualToSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where coeff not equals to DEFAULT_COEFF
        defaultEvaluationBatimentsShouldNotBeFound("coeff.notEquals=" + DEFAULT_COEFF);

        // Get all the evaluationBatimentsList where coeff not equals to UPDATED_COEFF
        defaultEvaluationBatimentsShouldBeFound("coeff.notEquals=" + UPDATED_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByCoeffIsInShouldWork() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where coeff in DEFAULT_COEFF or UPDATED_COEFF
        defaultEvaluationBatimentsShouldBeFound("coeff.in=" + DEFAULT_COEFF + "," + UPDATED_COEFF);

        // Get all the evaluationBatimentsList where coeff equals to UPDATED_COEFF
        defaultEvaluationBatimentsShouldNotBeFound("coeff.in=" + UPDATED_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByCoeffIsNullOrNotNull() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where coeff is not null
        defaultEvaluationBatimentsShouldBeFound("coeff.specified=true");

        // Get all the evaluationBatimentsList where coeff is null
        defaultEvaluationBatimentsShouldNotBeFound("coeff.specified=false");
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByCoeffIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where coeff is greater than or equal to DEFAULT_COEFF
        defaultEvaluationBatimentsShouldBeFound("coeff.greaterThanOrEqual=" + DEFAULT_COEFF);

        // Get all the evaluationBatimentsList where coeff is greater than or equal to UPDATED_COEFF
        defaultEvaluationBatimentsShouldNotBeFound("coeff.greaterThanOrEqual=" + UPDATED_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByCoeffIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where coeff is less than or equal to DEFAULT_COEFF
        defaultEvaluationBatimentsShouldBeFound("coeff.lessThanOrEqual=" + DEFAULT_COEFF);

        // Get all the evaluationBatimentsList where coeff is less than or equal to SMALLER_COEFF
        defaultEvaluationBatimentsShouldNotBeFound("coeff.lessThanOrEqual=" + SMALLER_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByCoeffIsLessThanSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where coeff is less than DEFAULT_COEFF
        defaultEvaluationBatimentsShouldNotBeFound("coeff.lessThan=" + DEFAULT_COEFF);

        // Get all the evaluationBatimentsList where coeff is less than UPDATED_COEFF
        defaultEvaluationBatimentsShouldBeFound("coeff.lessThan=" + UPDATED_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByCoeffIsGreaterThanSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        // Get all the evaluationBatimentsList where coeff is greater than DEFAULT_COEFF
        defaultEvaluationBatimentsShouldNotBeFound("coeff.greaterThan=" + DEFAULT_COEFF);

        // Get all the evaluationBatimentsList where coeff is greater than SMALLER_COEFF
        defaultEvaluationBatimentsShouldBeFound("coeff.greaterThan=" + SMALLER_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByCategorieNatureIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);
        CategorieNature categorieNature = CategorieNatureResourceIT.createEntity(em);
        em.persist(categorieNature);
        em.flush();
        evaluationBatiments.setCategorieNature(categorieNature);
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);
        Long categorieNatureId = categorieNature.getId();

        // Get all the evaluationBatimentsList where categorieNature equals to categorieNatureId
        defaultEvaluationBatimentsShouldBeFound("categorieNatureId.equals=" + categorieNatureId);

        // Get all the evaluationBatimentsList where categorieNature equals to (categorieNatureId + 1)
        defaultEvaluationBatimentsShouldNotBeFound("categorieNatureId.equals=" + (categorieNatureId + 1));
    }

    @Test
    @Transactional
    void getAllEvaluationBatimentsByDossierIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);
        Dossier dossier = DossierResourceIT.createEntity(em);
        em.persist(dossier);
        em.flush();
        evaluationBatiments.setDossier(dossier);
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);
        Long dossierId = dossier.getId();

        // Get all the evaluationBatimentsList where dossier equals to dossierId
        defaultEvaluationBatimentsShouldBeFound("dossierId.equals=" + dossierId);

        // Get all the evaluationBatimentsList where dossier equals to (dossierId + 1)
        defaultEvaluationBatimentsShouldNotBeFound("dossierId.equals=" + (dossierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEvaluationBatimentsShouldBeFound(String filter) throws Exception {
        restEvaluationBatimentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationBatiments.getId().intValue())))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU)))
            .andExpect(jsonPath("$.[*].surface").value(hasItem(DEFAULT_SURFACE.doubleValue())))
            .andExpect(jsonPath("$.[*].coeff").value(hasItem(DEFAULT_COEFF.doubleValue())));

        // Check, that the count call also returns 1
        restEvaluationBatimentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEvaluationBatimentsShouldNotBeFound(String filter) throws Exception {
        restEvaluationBatimentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEvaluationBatimentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEvaluationBatiments() throws Exception {
        // Get the evaluationBatiments
        restEvaluationBatimentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEvaluationBatiments() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        int databaseSizeBeforeUpdate = evaluationBatimentsRepository.findAll().size();

        // Update the evaluationBatiments
        EvaluationBatiments updatedEvaluationBatiments = evaluationBatimentsRepository.findById(evaluationBatiments.getId()).get();
        // Disconnect from session so that the updates on updatedEvaluationBatiments are not directly saved in db
        em.detach(updatedEvaluationBatiments);
        updatedEvaluationBatiments.niveau(UPDATED_NIVEAU).surface(UPDATED_SURFACE).coeff(UPDATED_COEFF);
        EvaluationBatimentsDTO evaluationBatimentsDTO = evaluationBatimentsMapper.toDto(updatedEvaluationBatiments);

        restEvaluationBatimentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluationBatimentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationBatimentsDTO))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationBatiments in the database
        List<EvaluationBatiments> evaluationBatimentsList = evaluationBatimentsRepository.findAll();
        assertThat(evaluationBatimentsList).hasSize(databaseSizeBeforeUpdate);
        EvaluationBatiments testEvaluationBatiments = evaluationBatimentsList.get(evaluationBatimentsList.size() - 1);
        assertThat(testEvaluationBatiments.getNiveau()).isEqualTo(UPDATED_NIVEAU);
        assertThat(testEvaluationBatiments.getSurface()).isEqualTo(UPDATED_SURFACE);
        assertThat(testEvaluationBatiments.getCoeff()).isEqualTo(UPDATED_COEFF);
    }

    @Test
    @Transactional
    void putNonExistingEvaluationBatiments() throws Exception {
        int databaseSizeBeforeUpdate = evaluationBatimentsRepository.findAll().size();
        evaluationBatiments.setId(count.incrementAndGet());

        // Create the EvaluationBatiments
        EvaluationBatimentsDTO evaluationBatimentsDTO = evaluationBatimentsMapper.toDto(evaluationBatiments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationBatimentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluationBatimentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationBatimentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationBatiments in the database
        List<EvaluationBatiments> evaluationBatimentsList = evaluationBatimentsRepository.findAll();
        assertThat(evaluationBatimentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvaluationBatiments() throws Exception {
        int databaseSizeBeforeUpdate = evaluationBatimentsRepository.findAll().size();
        evaluationBatiments.setId(count.incrementAndGet());

        // Create the EvaluationBatiments
        EvaluationBatimentsDTO evaluationBatimentsDTO = evaluationBatimentsMapper.toDto(evaluationBatiments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationBatimentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationBatimentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationBatiments in the database
        List<EvaluationBatiments> evaluationBatimentsList = evaluationBatimentsRepository.findAll();
        assertThat(evaluationBatimentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvaluationBatiments() throws Exception {
        int databaseSizeBeforeUpdate = evaluationBatimentsRepository.findAll().size();
        evaluationBatiments.setId(count.incrementAndGet());

        // Create the EvaluationBatiments
        EvaluationBatimentsDTO evaluationBatimentsDTO = evaluationBatimentsMapper.toDto(evaluationBatiments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationBatimentsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationBatimentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvaluationBatiments in the database
        List<EvaluationBatiments> evaluationBatimentsList = evaluationBatimentsRepository.findAll();
        assertThat(evaluationBatimentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEvaluationBatimentsWithPatch() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        int databaseSizeBeforeUpdate = evaluationBatimentsRepository.findAll().size();

        // Update the evaluationBatiments using partial update
        EvaluationBatiments partialUpdatedEvaluationBatiments = new EvaluationBatiments();
        partialUpdatedEvaluationBatiments.setId(evaluationBatiments.getId());

        partialUpdatedEvaluationBatiments.niveau(UPDATED_NIVEAU).surface(UPDATED_SURFACE);

        restEvaluationBatimentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluationBatiments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluationBatiments))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationBatiments in the database
        List<EvaluationBatiments> evaluationBatimentsList = evaluationBatimentsRepository.findAll();
        assertThat(evaluationBatimentsList).hasSize(databaseSizeBeforeUpdate);
        EvaluationBatiments testEvaluationBatiments = evaluationBatimentsList.get(evaluationBatimentsList.size() - 1);
        assertThat(testEvaluationBatiments.getNiveau()).isEqualTo(UPDATED_NIVEAU);
        assertThat(testEvaluationBatiments.getSurface()).isEqualTo(UPDATED_SURFACE);
        assertThat(testEvaluationBatiments.getCoeff()).isEqualTo(DEFAULT_COEFF);
    }

    @Test
    @Transactional
    void fullUpdateEvaluationBatimentsWithPatch() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        int databaseSizeBeforeUpdate = evaluationBatimentsRepository.findAll().size();

        // Update the evaluationBatiments using partial update
        EvaluationBatiments partialUpdatedEvaluationBatiments = new EvaluationBatiments();
        partialUpdatedEvaluationBatiments.setId(evaluationBatiments.getId());

        partialUpdatedEvaluationBatiments.niveau(UPDATED_NIVEAU).surface(UPDATED_SURFACE).coeff(UPDATED_COEFF);

        restEvaluationBatimentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluationBatiments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluationBatiments))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationBatiments in the database
        List<EvaluationBatiments> evaluationBatimentsList = evaluationBatimentsRepository.findAll();
        assertThat(evaluationBatimentsList).hasSize(databaseSizeBeforeUpdate);
        EvaluationBatiments testEvaluationBatiments = evaluationBatimentsList.get(evaluationBatimentsList.size() - 1);
        assertThat(testEvaluationBatiments.getNiveau()).isEqualTo(UPDATED_NIVEAU);
        assertThat(testEvaluationBatiments.getSurface()).isEqualTo(UPDATED_SURFACE);
        assertThat(testEvaluationBatiments.getCoeff()).isEqualTo(UPDATED_COEFF);
    }

    @Test
    @Transactional
    void patchNonExistingEvaluationBatiments() throws Exception {
        int databaseSizeBeforeUpdate = evaluationBatimentsRepository.findAll().size();
        evaluationBatiments.setId(count.incrementAndGet());

        // Create the EvaluationBatiments
        EvaluationBatimentsDTO evaluationBatimentsDTO = evaluationBatimentsMapper.toDto(evaluationBatiments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationBatimentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, evaluationBatimentsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationBatimentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationBatiments in the database
        List<EvaluationBatiments> evaluationBatimentsList = evaluationBatimentsRepository.findAll();
        assertThat(evaluationBatimentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvaluationBatiments() throws Exception {
        int databaseSizeBeforeUpdate = evaluationBatimentsRepository.findAll().size();
        evaluationBatiments.setId(count.incrementAndGet());

        // Create the EvaluationBatiments
        EvaluationBatimentsDTO evaluationBatimentsDTO = evaluationBatimentsMapper.toDto(evaluationBatiments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationBatimentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationBatimentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationBatiments in the database
        List<EvaluationBatiments> evaluationBatimentsList = evaluationBatimentsRepository.findAll();
        assertThat(evaluationBatimentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvaluationBatiments() throws Exception {
        int databaseSizeBeforeUpdate = evaluationBatimentsRepository.findAll().size();
        evaluationBatiments.setId(count.incrementAndGet());

        // Create the EvaluationBatiments
        EvaluationBatimentsDTO evaluationBatimentsDTO = evaluationBatimentsMapper.toDto(evaluationBatiments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationBatimentsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationBatimentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvaluationBatiments in the database
        List<EvaluationBatiments> evaluationBatimentsList = evaluationBatimentsRepository.findAll();
        assertThat(evaluationBatimentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvaluationBatiments() throws Exception {
        // Initialize the database
        evaluationBatimentsRepository.saveAndFlush(evaluationBatiments);

        int databaseSizeBeforeDelete = evaluationBatimentsRepository.findAll().size();

        // Delete the evaluationBatiments
        restEvaluationBatimentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, evaluationBatiments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EvaluationBatiments> evaluationBatimentsList = evaluationBatimentsRepository.findAll();
        assertThat(evaluationBatimentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
