package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.CategorieBatie;
import com.pirtol.lab.domain.Dossier;
import com.pirtol.lab.domain.EvaluationSurfaceBatie;
import com.pirtol.lab.repository.EvaluationSurfaceBatieRepository;
import com.pirtol.lab.service.criteria.EvaluationSurfaceBatieCriteria;
import com.pirtol.lab.service.dto.EvaluationSurfaceBatieDTO;
import com.pirtol.lab.service.mapper.EvaluationSurfaceBatieMapper;
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
 * Integration tests for the {@link EvaluationSurfaceBatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EvaluationSurfaceBatieResourceIT {

    private static final Double DEFAULT_SUPERFICIE_TOTALE = 1D;
    private static final Double UPDATED_SUPERFICIE_TOTALE = 2D;
    private static final Double SMALLER_SUPERFICIE_TOTALE = 1D - 1D;

    private static final Double DEFAULT_SUPERFICIE_BATIE = 1D;
    private static final Double UPDATED_SUPERFICIE_BATIE = 2D;
    private static final Double SMALLER_SUPERFICIE_BATIE = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/evaluation-surface-baties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EvaluationSurfaceBatieRepository evaluationSurfaceBatieRepository;

    @Autowired
    private EvaluationSurfaceBatieMapper evaluationSurfaceBatieMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEvaluationSurfaceBatieMockMvc;

    private EvaluationSurfaceBatie evaluationSurfaceBatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluationSurfaceBatie createEntity(EntityManager em) {
        EvaluationSurfaceBatie evaluationSurfaceBatie = new EvaluationSurfaceBatie()
            .superficieTotale(DEFAULT_SUPERFICIE_TOTALE)
            .superficieBatie(DEFAULT_SUPERFICIE_BATIE);
        return evaluationSurfaceBatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluationSurfaceBatie createUpdatedEntity(EntityManager em) {
        EvaluationSurfaceBatie evaluationSurfaceBatie = new EvaluationSurfaceBatie()
            .superficieTotale(UPDATED_SUPERFICIE_TOTALE)
            .superficieBatie(UPDATED_SUPERFICIE_BATIE);
        return evaluationSurfaceBatie;
    }

    @BeforeEach
    public void initTest() {
        evaluationSurfaceBatie = createEntity(em);
    }

    @Test
    @Transactional
    void createEvaluationSurfaceBatie() throws Exception {
        int databaseSizeBeforeCreate = evaluationSurfaceBatieRepository.findAll().size();
        // Create the EvaluationSurfaceBatie
        EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO = evaluationSurfaceBatieMapper.toDto(evaluationSurfaceBatie);
        restEvaluationSurfaceBatieMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationSurfaceBatieDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EvaluationSurfaceBatie in the database
        List<EvaluationSurfaceBatie> evaluationSurfaceBatieList = evaluationSurfaceBatieRepository.findAll();
        assertThat(evaluationSurfaceBatieList).hasSize(databaseSizeBeforeCreate + 1);
        EvaluationSurfaceBatie testEvaluationSurfaceBatie = evaluationSurfaceBatieList.get(evaluationSurfaceBatieList.size() - 1);
        assertThat(testEvaluationSurfaceBatie.getSuperficieTotale()).isEqualTo(DEFAULT_SUPERFICIE_TOTALE);
        assertThat(testEvaluationSurfaceBatie.getSuperficieBatie()).isEqualTo(DEFAULT_SUPERFICIE_BATIE);
    }

    @Test
    @Transactional
    void createEvaluationSurfaceBatieWithExistingId() throws Exception {
        // Create the EvaluationSurfaceBatie with an existing ID
        evaluationSurfaceBatie.setId(1L);
        EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO = evaluationSurfaceBatieMapper.toDto(evaluationSurfaceBatie);

        int databaseSizeBeforeCreate = evaluationSurfaceBatieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluationSurfaceBatieMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationSurfaceBatieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationSurfaceBatie in the database
        List<EvaluationSurfaceBatie> evaluationSurfaceBatieList = evaluationSurfaceBatieRepository.findAll();
        assertThat(evaluationSurfaceBatieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBaties() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList
        restEvaluationSurfaceBatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationSurfaceBatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].superficieTotale").value(hasItem(DEFAULT_SUPERFICIE_TOTALE.doubleValue())))
            .andExpect(jsonPath("$.[*].superficieBatie").value(hasItem(DEFAULT_SUPERFICIE_BATIE.doubleValue())));
    }

    @Test
    @Transactional
    void getEvaluationSurfaceBatie() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get the evaluationSurfaceBatie
        restEvaluationSurfaceBatieMockMvc
            .perform(get(ENTITY_API_URL_ID, evaluationSurfaceBatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evaluationSurfaceBatie.getId().intValue()))
            .andExpect(jsonPath("$.superficieTotale").value(DEFAULT_SUPERFICIE_TOTALE.doubleValue()))
            .andExpect(jsonPath("$.superficieBatie").value(DEFAULT_SUPERFICIE_BATIE.doubleValue()));
    }

    @Test
    @Transactional
    void getEvaluationSurfaceBatiesByIdFiltering() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        Long id = evaluationSurfaceBatie.getId();

        defaultEvaluationSurfaceBatieShouldBeFound("id.equals=" + id);
        defaultEvaluationSurfaceBatieShouldNotBeFound("id.notEquals=" + id);

        defaultEvaluationSurfaceBatieShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEvaluationSurfaceBatieShouldNotBeFound("id.greaterThan=" + id);

        defaultEvaluationSurfaceBatieShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEvaluationSurfaceBatieShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieTotaleIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieTotale equals to DEFAULT_SUPERFICIE_TOTALE
        defaultEvaluationSurfaceBatieShouldBeFound("superficieTotale.equals=" + DEFAULT_SUPERFICIE_TOTALE);

        // Get all the evaluationSurfaceBatieList where superficieTotale equals to UPDATED_SUPERFICIE_TOTALE
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieTotale.equals=" + UPDATED_SUPERFICIE_TOTALE);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieTotaleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieTotale not equals to DEFAULT_SUPERFICIE_TOTALE
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieTotale.notEquals=" + DEFAULT_SUPERFICIE_TOTALE);

        // Get all the evaluationSurfaceBatieList where superficieTotale not equals to UPDATED_SUPERFICIE_TOTALE
        defaultEvaluationSurfaceBatieShouldBeFound("superficieTotale.notEquals=" + UPDATED_SUPERFICIE_TOTALE);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieTotaleIsInShouldWork() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieTotale in DEFAULT_SUPERFICIE_TOTALE or UPDATED_SUPERFICIE_TOTALE
        defaultEvaluationSurfaceBatieShouldBeFound("superficieTotale.in=" + DEFAULT_SUPERFICIE_TOTALE + "," + UPDATED_SUPERFICIE_TOTALE);

        // Get all the evaluationSurfaceBatieList where superficieTotale equals to UPDATED_SUPERFICIE_TOTALE
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieTotale.in=" + UPDATED_SUPERFICIE_TOTALE);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieTotaleIsNullOrNotNull() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieTotale is not null
        defaultEvaluationSurfaceBatieShouldBeFound("superficieTotale.specified=true");

        // Get all the evaluationSurfaceBatieList where superficieTotale is null
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieTotale.specified=false");
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieTotaleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieTotale is greater than or equal to DEFAULT_SUPERFICIE_TOTALE
        defaultEvaluationSurfaceBatieShouldBeFound("superficieTotale.greaterThanOrEqual=" + DEFAULT_SUPERFICIE_TOTALE);

        // Get all the evaluationSurfaceBatieList where superficieTotale is greater than or equal to UPDATED_SUPERFICIE_TOTALE
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieTotale.greaterThanOrEqual=" + UPDATED_SUPERFICIE_TOTALE);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieTotaleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieTotale is less than or equal to DEFAULT_SUPERFICIE_TOTALE
        defaultEvaluationSurfaceBatieShouldBeFound("superficieTotale.lessThanOrEqual=" + DEFAULT_SUPERFICIE_TOTALE);

        // Get all the evaluationSurfaceBatieList where superficieTotale is less than or equal to SMALLER_SUPERFICIE_TOTALE
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieTotale.lessThanOrEqual=" + SMALLER_SUPERFICIE_TOTALE);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieTotaleIsLessThanSomething() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieTotale is less than DEFAULT_SUPERFICIE_TOTALE
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieTotale.lessThan=" + DEFAULT_SUPERFICIE_TOTALE);

        // Get all the evaluationSurfaceBatieList where superficieTotale is less than UPDATED_SUPERFICIE_TOTALE
        defaultEvaluationSurfaceBatieShouldBeFound("superficieTotale.lessThan=" + UPDATED_SUPERFICIE_TOTALE);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieTotaleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieTotale is greater than DEFAULT_SUPERFICIE_TOTALE
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieTotale.greaterThan=" + DEFAULT_SUPERFICIE_TOTALE);

        // Get all the evaluationSurfaceBatieList where superficieTotale is greater than SMALLER_SUPERFICIE_TOTALE
        defaultEvaluationSurfaceBatieShouldBeFound("superficieTotale.greaterThan=" + SMALLER_SUPERFICIE_TOTALE);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieBatieIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieBatie equals to DEFAULT_SUPERFICIE_BATIE
        defaultEvaluationSurfaceBatieShouldBeFound("superficieBatie.equals=" + DEFAULT_SUPERFICIE_BATIE);

        // Get all the evaluationSurfaceBatieList where superficieBatie equals to UPDATED_SUPERFICIE_BATIE
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieBatie.equals=" + UPDATED_SUPERFICIE_BATIE);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieBatieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieBatie not equals to DEFAULT_SUPERFICIE_BATIE
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieBatie.notEquals=" + DEFAULT_SUPERFICIE_BATIE);

        // Get all the evaluationSurfaceBatieList where superficieBatie not equals to UPDATED_SUPERFICIE_BATIE
        defaultEvaluationSurfaceBatieShouldBeFound("superficieBatie.notEquals=" + UPDATED_SUPERFICIE_BATIE);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieBatieIsInShouldWork() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieBatie in DEFAULT_SUPERFICIE_BATIE or UPDATED_SUPERFICIE_BATIE
        defaultEvaluationSurfaceBatieShouldBeFound("superficieBatie.in=" + DEFAULT_SUPERFICIE_BATIE + "," + UPDATED_SUPERFICIE_BATIE);

        // Get all the evaluationSurfaceBatieList where superficieBatie equals to UPDATED_SUPERFICIE_BATIE
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieBatie.in=" + UPDATED_SUPERFICIE_BATIE);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieBatieIsNullOrNotNull() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieBatie is not null
        defaultEvaluationSurfaceBatieShouldBeFound("superficieBatie.specified=true");

        // Get all the evaluationSurfaceBatieList where superficieBatie is null
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieBatie.specified=false");
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieBatieIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieBatie is greater than or equal to DEFAULT_SUPERFICIE_BATIE
        defaultEvaluationSurfaceBatieShouldBeFound("superficieBatie.greaterThanOrEqual=" + DEFAULT_SUPERFICIE_BATIE);

        // Get all the evaluationSurfaceBatieList where superficieBatie is greater than or equal to UPDATED_SUPERFICIE_BATIE
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieBatie.greaterThanOrEqual=" + UPDATED_SUPERFICIE_BATIE);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieBatieIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieBatie is less than or equal to DEFAULT_SUPERFICIE_BATIE
        defaultEvaluationSurfaceBatieShouldBeFound("superficieBatie.lessThanOrEqual=" + DEFAULT_SUPERFICIE_BATIE);

        // Get all the evaluationSurfaceBatieList where superficieBatie is less than or equal to SMALLER_SUPERFICIE_BATIE
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieBatie.lessThanOrEqual=" + SMALLER_SUPERFICIE_BATIE);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieBatieIsLessThanSomething() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieBatie is less than DEFAULT_SUPERFICIE_BATIE
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieBatie.lessThan=" + DEFAULT_SUPERFICIE_BATIE);

        // Get all the evaluationSurfaceBatieList where superficieBatie is less than UPDATED_SUPERFICIE_BATIE
        defaultEvaluationSurfaceBatieShouldBeFound("superficieBatie.lessThan=" + UPDATED_SUPERFICIE_BATIE);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesBySuperficieBatieIsGreaterThanSomething() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        // Get all the evaluationSurfaceBatieList where superficieBatie is greater than DEFAULT_SUPERFICIE_BATIE
        defaultEvaluationSurfaceBatieShouldNotBeFound("superficieBatie.greaterThan=" + DEFAULT_SUPERFICIE_BATIE);

        // Get all the evaluationSurfaceBatieList where superficieBatie is greater than SMALLER_SUPERFICIE_BATIE
        defaultEvaluationSurfaceBatieShouldBeFound("superficieBatie.greaterThan=" + SMALLER_SUPERFICIE_BATIE);
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesByCategorieBatieIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);
        CategorieBatie categorieBatie = CategorieBatieResourceIT.createEntity(em);
        em.persist(categorieBatie);
        em.flush();
        evaluationSurfaceBatie.setCategorieBatie(categorieBatie);
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);
        Long categorieBatieId = categorieBatie.getId();

        // Get all the evaluationSurfaceBatieList where categorieBatie equals to categorieBatieId
        defaultEvaluationSurfaceBatieShouldBeFound("categorieBatieId.equals=" + categorieBatieId);

        // Get all the evaluationSurfaceBatieList where categorieBatie equals to (categorieBatieId + 1)
        defaultEvaluationSurfaceBatieShouldNotBeFound("categorieBatieId.equals=" + (categorieBatieId + 1));
    }

    @Test
    @Transactional
    void getAllEvaluationSurfaceBatiesByDossierIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);
        Dossier dossier = DossierResourceIT.createEntity(em);
        em.persist(dossier);
        em.flush();
        evaluationSurfaceBatie.setDossier(dossier);
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);
        Long dossierId = dossier.getId();

        // Get all the evaluationSurfaceBatieList where dossier equals to dossierId
        defaultEvaluationSurfaceBatieShouldBeFound("dossierId.equals=" + dossierId);

        // Get all the evaluationSurfaceBatieList where dossier equals to (dossierId + 1)
        defaultEvaluationSurfaceBatieShouldNotBeFound("dossierId.equals=" + (dossierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEvaluationSurfaceBatieShouldBeFound(String filter) throws Exception {
        restEvaluationSurfaceBatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationSurfaceBatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].superficieTotale").value(hasItem(DEFAULT_SUPERFICIE_TOTALE.doubleValue())))
            .andExpect(jsonPath("$.[*].superficieBatie").value(hasItem(DEFAULT_SUPERFICIE_BATIE.doubleValue())));

        // Check, that the count call also returns 1
        restEvaluationSurfaceBatieMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEvaluationSurfaceBatieShouldNotBeFound(String filter) throws Exception {
        restEvaluationSurfaceBatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEvaluationSurfaceBatieMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEvaluationSurfaceBatie() throws Exception {
        // Get the evaluationSurfaceBatie
        restEvaluationSurfaceBatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEvaluationSurfaceBatie() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        int databaseSizeBeforeUpdate = evaluationSurfaceBatieRepository.findAll().size();

        // Update the evaluationSurfaceBatie
        EvaluationSurfaceBatie updatedEvaluationSurfaceBatie = evaluationSurfaceBatieRepository
            .findById(evaluationSurfaceBatie.getId())
            .get();
        // Disconnect from session so that the updates on updatedEvaluationSurfaceBatie are not directly saved in db
        em.detach(updatedEvaluationSurfaceBatie);
        updatedEvaluationSurfaceBatie.superficieTotale(UPDATED_SUPERFICIE_TOTALE).superficieBatie(UPDATED_SUPERFICIE_BATIE);
        EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO = evaluationSurfaceBatieMapper.toDto(updatedEvaluationSurfaceBatie);

        restEvaluationSurfaceBatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluationSurfaceBatieDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationSurfaceBatieDTO))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationSurfaceBatie in the database
        List<EvaluationSurfaceBatie> evaluationSurfaceBatieList = evaluationSurfaceBatieRepository.findAll();
        assertThat(evaluationSurfaceBatieList).hasSize(databaseSizeBeforeUpdate);
        EvaluationSurfaceBatie testEvaluationSurfaceBatie = evaluationSurfaceBatieList.get(evaluationSurfaceBatieList.size() - 1);
        assertThat(testEvaluationSurfaceBatie.getSuperficieTotale()).isEqualTo(UPDATED_SUPERFICIE_TOTALE);
        assertThat(testEvaluationSurfaceBatie.getSuperficieBatie()).isEqualTo(UPDATED_SUPERFICIE_BATIE);
    }

    @Test
    @Transactional
    void putNonExistingEvaluationSurfaceBatie() throws Exception {
        int databaseSizeBeforeUpdate = evaluationSurfaceBatieRepository.findAll().size();
        evaluationSurfaceBatie.setId(count.incrementAndGet());

        // Create the EvaluationSurfaceBatie
        EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO = evaluationSurfaceBatieMapper.toDto(evaluationSurfaceBatie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationSurfaceBatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluationSurfaceBatieDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationSurfaceBatieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationSurfaceBatie in the database
        List<EvaluationSurfaceBatie> evaluationSurfaceBatieList = evaluationSurfaceBatieRepository.findAll();
        assertThat(evaluationSurfaceBatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvaluationSurfaceBatie() throws Exception {
        int databaseSizeBeforeUpdate = evaluationSurfaceBatieRepository.findAll().size();
        evaluationSurfaceBatie.setId(count.incrementAndGet());

        // Create the EvaluationSurfaceBatie
        EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO = evaluationSurfaceBatieMapper.toDto(evaluationSurfaceBatie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationSurfaceBatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationSurfaceBatieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationSurfaceBatie in the database
        List<EvaluationSurfaceBatie> evaluationSurfaceBatieList = evaluationSurfaceBatieRepository.findAll();
        assertThat(evaluationSurfaceBatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvaluationSurfaceBatie() throws Exception {
        int databaseSizeBeforeUpdate = evaluationSurfaceBatieRepository.findAll().size();
        evaluationSurfaceBatie.setId(count.incrementAndGet());

        // Create the EvaluationSurfaceBatie
        EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO = evaluationSurfaceBatieMapper.toDto(evaluationSurfaceBatie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationSurfaceBatieMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationSurfaceBatieDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvaluationSurfaceBatie in the database
        List<EvaluationSurfaceBatie> evaluationSurfaceBatieList = evaluationSurfaceBatieRepository.findAll();
        assertThat(evaluationSurfaceBatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEvaluationSurfaceBatieWithPatch() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        int databaseSizeBeforeUpdate = evaluationSurfaceBatieRepository.findAll().size();

        // Update the evaluationSurfaceBatie using partial update
        EvaluationSurfaceBatie partialUpdatedEvaluationSurfaceBatie = new EvaluationSurfaceBatie();
        partialUpdatedEvaluationSurfaceBatie.setId(evaluationSurfaceBatie.getId());

        restEvaluationSurfaceBatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluationSurfaceBatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluationSurfaceBatie))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationSurfaceBatie in the database
        List<EvaluationSurfaceBatie> evaluationSurfaceBatieList = evaluationSurfaceBatieRepository.findAll();
        assertThat(evaluationSurfaceBatieList).hasSize(databaseSizeBeforeUpdate);
        EvaluationSurfaceBatie testEvaluationSurfaceBatie = evaluationSurfaceBatieList.get(evaluationSurfaceBatieList.size() - 1);
        assertThat(testEvaluationSurfaceBatie.getSuperficieTotale()).isEqualTo(DEFAULT_SUPERFICIE_TOTALE);
        assertThat(testEvaluationSurfaceBatie.getSuperficieBatie()).isEqualTo(DEFAULT_SUPERFICIE_BATIE);
    }

    @Test
    @Transactional
    void fullUpdateEvaluationSurfaceBatieWithPatch() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        int databaseSizeBeforeUpdate = evaluationSurfaceBatieRepository.findAll().size();

        // Update the evaluationSurfaceBatie using partial update
        EvaluationSurfaceBatie partialUpdatedEvaluationSurfaceBatie = new EvaluationSurfaceBatie();
        partialUpdatedEvaluationSurfaceBatie.setId(evaluationSurfaceBatie.getId());

        partialUpdatedEvaluationSurfaceBatie.superficieTotale(UPDATED_SUPERFICIE_TOTALE).superficieBatie(UPDATED_SUPERFICIE_BATIE);

        restEvaluationSurfaceBatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluationSurfaceBatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluationSurfaceBatie))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationSurfaceBatie in the database
        List<EvaluationSurfaceBatie> evaluationSurfaceBatieList = evaluationSurfaceBatieRepository.findAll();
        assertThat(evaluationSurfaceBatieList).hasSize(databaseSizeBeforeUpdate);
        EvaluationSurfaceBatie testEvaluationSurfaceBatie = evaluationSurfaceBatieList.get(evaluationSurfaceBatieList.size() - 1);
        assertThat(testEvaluationSurfaceBatie.getSuperficieTotale()).isEqualTo(UPDATED_SUPERFICIE_TOTALE);
        assertThat(testEvaluationSurfaceBatie.getSuperficieBatie()).isEqualTo(UPDATED_SUPERFICIE_BATIE);
    }

    @Test
    @Transactional
    void patchNonExistingEvaluationSurfaceBatie() throws Exception {
        int databaseSizeBeforeUpdate = evaluationSurfaceBatieRepository.findAll().size();
        evaluationSurfaceBatie.setId(count.incrementAndGet());

        // Create the EvaluationSurfaceBatie
        EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO = evaluationSurfaceBatieMapper.toDto(evaluationSurfaceBatie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationSurfaceBatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, evaluationSurfaceBatieDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationSurfaceBatieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationSurfaceBatie in the database
        List<EvaluationSurfaceBatie> evaluationSurfaceBatieList = evaluationSurfaceBatieRepository.findAll();
        assertThat(evaluationSurfaceBatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvaluationSurfaceBatie() throws Exception {
        int databaseSizeBeforeUpdate = evaluationSurfaceBatieRepository.findAll().size();
        evaluationSurfaceBatie.setId(count.incrementAndGet());

        // Create the EvaluationSurfaceBatie
        EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO = evaluationSurfaceBatieMapper.toDto(evaluationSurfaceBatie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationSurfaceBatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationSurfaceBatieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationSurfaceBatie in the database
        List<EvaluationSurfaceBatie> evaluationSurfaceBatieList = evaluationSurfaceBatieRepository.findAll();
        assertThat(evaluationSurfaceBatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvaluationSurfaceBatie() throws Exception {
        int databaseSizeBeforeUpdate = evaluationSurfaceBatieRepository.findAll().size();
        evaluationSurfaceBatie.setId(count.incrementAndGet());

        // Create the EvaluationSurfaceBatie
        EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO = evaluationSurfaceBatieMapper.toDto(evaluationSurfaceBatie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationSurfaceBatieMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationSurfaceBatieDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvaluationSurfaceBatie in the database
        List<EvaluationSurfaceBatie> evaluationSurfaceBatieList = evaluationSurfaceBatieRepository.findAll();
        assertThat(evaluationSurfaceBatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvaluationSurfaceBatie() throws Exception {
        // Initialize the database
        evaluationSurfaceBatieRepository.saveAndFlush(evaluationSurfaceBatie);

        int databaseSizeBeforeDelete = evaluationSurfaceBatieRepository.findAll().size();

        // Delete the evaluationSurfaceBatie
        restEvaluationSurfaceBatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, evaluationSurfaceBatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EvaluationSurfaceBatie> evaluationSurfaceBatieList = evaluationSurfaceBatieRepository.findAll();
        assertThat(evaluationSurfaceBatieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
