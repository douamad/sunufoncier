package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.CategorieCloture;
import com.pirtol.lab.domain.Dossier;
import com.pirtol.lab.domain.EvaluationCloture;
import com.pirtol.lab.repository.EvaluationClotureRepository;
import com.pirtol.lab.service.criteria.EvaluationClotureCriteria;
import com.pirtol.lab.service.dto.EvaluationClotureDTO;
import com.pirtol.lab.service.mapper.EvaluationClotureMapper;
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
 * Integration tests for the {@link EvaluationClotureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EvaluationClotureResourceIT {

    private static final Double DEFAULT_LINEAIRE = 1D;
    private static final Double UPDATED_LINEAIRE = 2D;
    private static final Double SMALLER_LINEAIRE = 1D - 1D;

    private static final Double DEFAULT_COEFF = 1D;
    private static final Double UPDATED_COEFF = 2D;
    private static final Double SMALLER_COEFF = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/evaluation-clotures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EvaluationClotureRepository evaluationClotureRepository;

    @Autowired
    private EvaluationClotureMapper evaluationClotureMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEvaluationClotureMockMvc;

    private EvaluationCloture evaluationCloture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluationCloture createEntity(EntityManager em) {
        EvaluationCloture evaluationCloture = new EvaluationCloture().lineaire(DEFAULT_LINEAIRE).coeff(DEFAULT_COEFF);
        return evaluationCloture;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluationCloture createUpdatedEntity(EntityManager em) {
        EvaluationCloture evaluationCloture = new EvaluationCloture().lineaire(UPDATED_LINEAIRE).coeff(UPDATED_COEFF);
        return evaluationCloture;
    }

    @BeforeEach
    public void initTest() {
        evaluationCloture = createEntity(em);
    }

    @Test
    @Transactional
    void createEvaluationCloture() throws Exception {
        int databaseSizeBeforeCreate = evaluationClotureRepository.findAll().size();
        // Create the EvaluationCloture
        EvaluationClotureDTO evaluationClotureDTO = evaluationClotureMapper.toDto(evaluationCloture);
        restEvaluationClotureMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationClotureDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EvaluationCloture in the database
        List<EvaluationCloture> evaluationClotureList = evaluationClotureRepository.findAll();
        assertThat(evaluationClotureList).hasSize(databaseSizeBeforeCreate + 1);
        EvaluationCloture testEvaluationCloture = evaluationClotureList.get(evaluationClotureList.size() - 1);
        assertThat(testEvaluationCloture.getLineaire()).isEqualTo(DEFAULT_LINEAIRE);
        assertThat(testEvaluationCloture.getCoeff()).isEqualTo(DEFAULT_COEFF);
    }

    @Test
    @Transactional
    void createEvaluationClotureWithExistingId() throws Exception {
        // Create the EvaluationCloture with an existing ID
        evaluationCloture.setId(1L);
        EvaluationClotureDTO evaluationClotureDTO = evaluationClotureMapper.toDto(evaluationCloture);

        int databaseSizeBeforeCreate = evaluationClotureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluationClotureMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationClotureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCloture in the database
        List<EvaluationCloture> evaluationClotureList = evaluationClotureRepository.findAll();
        assertThat(evaluationClotureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEvaluationClotures() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList
        restEvaluationClotureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationCloture.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineaire").value(hasItem(DEFAULT_LINEAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].coeff").value(hasItem(DEFAULT_COEFF.doubleValue())));
    }

    @Test
    @Transactional
    void getEvaluationCloture() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get the evaluationCloture
        restEvaluationClotureMockMvc
            .perform(get(ENTITY_API_URL_ID, evaluationCloture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evaluationCloture.getId().intValue()))
            .andExpect(jsonPath("$.lineaire").value(DEFAULT_LINEAIRE.doubleValue()))
            .andExpect(jsonPath("$.coeff").value(DEFAULT_COEFF.doubleValue()));
    }

    @Test
    @Transactional
    void getEvaluationCloturesByIdFiltering() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        Long id = evaluationCloture.getId();

        defaultEvaluationClotureShouldBeFound("id.equals=" + id);
        defaultEvaluationClotureShouldNotBeFound("id.notEquals=" + id);

        defaultEvaluationClotureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEvaluationClotureShouldNotBeFound("id.greaterThan=" + id);

        defaultEvaluationClotureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEvaluationClotureShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByLineaireIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where lineaire equals to DEFAULT_LINEAIRE
        defaultEvaluationClotureShouldBeFound("lineaire.equals=" + DEFAULT_LINEAIRE);

        // Get all the evaluationClotureList where lineaire equals to UPDATED_LINEAIRE
        defaultEvaluationClotureShouldNotBeFound("lineaire.equals=" + UPDATED_LINEAIRE);
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByLineaireIsNotEqualToSomething() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where lineaire not equals to DEFAULT_LINEAIRE
        defaultEvaluationClotureShouldNotBeFound("lineaire.notEquals=" + DEFAULT_LINEAIRE);

        // Get all the evaluationClotureList where lineaire not equals to UPDATED_LINEAIRE
        defaultEvaluationClotureShouldBeFound("lineaire.notEquals=" + UPDATED_LINEAIRE);
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByLineaireIsInShouldWork() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where lineaire in DEFAULT_LINEAIRE or UPDATED_LINEAIRE
        defaultEvaluationClotureShouldBeFound("lineaire.in=" + DEFAULT_LINEAIRE + "," + UPDATED_LINEAIRE);

        // Get all the evaluationClotureList where lineaire equals to UPDATED_LINEAIRE
        defaultEvaluationClotureShouldNotBeFound("lineaire.in=" + UPDATED_LINEAIRE);
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByLineaireIsNullOrNotNull() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where lineaire is not null
        defaultEvaluationClotureShouldBeFound("lineaire.specified=true");

        // Get all the evaluationClotureList where lineaire is null
        defaultEvaluationClotureShouldNotBeFound("lineaire.specified=false");
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByLineaireIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where lineaire is greater than or equal to DEFAULT_LINEAIRE
        defaultEvaluationClotureShouldBeFound("lineaire.greaterThanOrEqual=" + DEFAULT_LINEAIRE);

        // Get all the evaluationClotureList where lineaire is greater than or equal to UPDATED_LINEAIRE
        defaultEvaluationClotureShouldNotBeFound("lineaire.greaterThanOrEqual=" + UPDATED_LINEAIRE);
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByLineaireIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where lineaire is less than or equal to DEFAULT_LINEAIRE
        defaultEvaluationClotureShouldBeFound("lineaire.lessThanOrEqual=" + DEFAULT_LINEAIRE);

        // Get all the evaluationClotureList where lineaire is less than or equal to SMALLER_LINEAIRE
        defaultEvaluationClotureShouldNotBeFound("lineaire.lessThanOrEqual=" + SMALLER_LINEAIRE);
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByLineaireIsLessThanSomething() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where lineaire is less than DEFAULT_LINEAIRE
        defaultEvaluationClotureShouldNotBeFound("lineaire.lessThan=" + DEFAULT_LINEAIRE);

        // Get all the evaluationClotureList where lineaire is less than UPDATED_LINEAIRE
        defaultEvaluationClotureShouldBeFound("lineaire.lessThan=" + UPDATED_LINEAIRE);
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByLineaireIsGreaterThanSomething() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where lineaire is greater than DEFAULT_LINEAIRE
        defaultEvaluationClotureShouldNotBeFound("lineaire.greaterThan=" + DEFAULT_LINEAIRE);

        // Get all the evaluationClotureList where lineaire is greater than SMALLER_LINEAIRE
        defaultEvaluationClotureShouldBeFound("lineaire.greaterThan=" + SMALLER_LINEAIRE);
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByCoeffIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where coeff equals to DEFAULT_COEFF
        defaultEvaluationClotureShouldBeFound("coeff.equals=" + DEFAULT_COEFF);

        // Get all the evaluationClotureList where coeff equals to UPDATED_COEFF
        defaultEvaluationClotureShouldNotBeFound("coeff.equals=" + UPDATED_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByCoeffIsNotEqualToSomething() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where coeff not equals to DEFAULT_COEFF
        defaultEvaluationClotureShouldNotBeFound("coeff.notEquals=" + DEFAULT_COEFF);

        // Get all the evaluationClotureList where coeff not equals to UPDATED_COEFF
        defaultEvaluationClotureShouldBeFound("coeff.notEquals=" + UPDATED_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByCoeffIsInShouldWork() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where coeff in DEFAULT_COEFF or UPDATED_COEFF
        defaultEvaluationClotureShouldBeFound("coeff.in=" + DEFAULT_COEFF + "," + UPDATED_COEFF);

        // Get all the evaluationClotureList where coeff equals to UPDATED_COEFF
        defaultEvaluationClotureShouldNotBeFound("coeff.in=" + UPDATED_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByCoeffIsNullOrNotNull() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where coeff is not null
        defaultEvaluationClotureShouldBeFound("coeff.specified=true");

        // Get all the evaluationClotureList where coeff is null
        defaultEvaluationClotureShouldNotBeFound("coeff.specified=false");
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByCoeffIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where coeff is greater than or equal to DEFAULT_COEFF
        defaultEvaluationClotureShouldBeFound("coeff.greaterThanOrEqual=" + DEFAULT_COEFF);

        // Get all the evaluationClotureList where coeff is greater than or equal to UPDATED_COEFF
        defaultEvaluationClotureShouldNotBeFound("coeff.greaterThanOrEqual=" + UPDATED_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByCoeffIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where coeff is less than or equal to DEFAULT_COEFF
        defaultEvaluationClotureShouldBeFound("coeff.lessThanOrEqual=" + DEFAULT_COEFF);

        // Get all the evaluationClotureList where coeff is less than or equal to SMALLER_COEFF
        defaultEvaluationClotureShouldNotBeFound("coeff.lessThanOrEqual=" + SMALLER_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByCoeffIsLessThanSomething() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where coeff is less than DEFAULT_COEFF
        defaultEvaluationClotureShouldNotBeFound("coeff.lessThan=" + DEFAULT_COEFF);

        // Get all the evaluationClotureList where coeff is less than UPDATED_COEFF
        defaultEvaluationClotureShouldBeFound("coeff.lessThan=" + UPDATED_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByCoeffIsGreaterThanSomething() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        // Get all the evaluationClotureList where coeff is greater than DEFAULT_COEFF
        defaultEvaluationClotureShouldNotBeFound("coeff.greaterThan=" + DEFAULT_COEFF);

        // Get all the evaluationClotureList where coeff is greater than SMALLER_COEFF
        defaultEvaluationClotureShouldBeFound("coeff.greaterThan=" + SMALLER_COEFF);
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByCategoriteClotureIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);
        CategorieCloture categoriteCloture = CategorieClotureResourceIT.createEntity(em);
        em.persist(categoriteCloture);
        em.flush();
        evaluationCloture.setCategoriteCloture(categoriteCloture);
        evaluationClotureRepository.saveAndFlush(evaluationCloture);
        Long categoriteClotureId = categoriteCloture.getId();

        // Get all the evaluationClotureList where categoriteCloture equals to categoriteClotureId
        defaultEvaluationClotureShouldBeFound("categoriteClotureId.equals=" + categoriteClotureId);

        // Get all the evaluationClotureList where categoriteCloture equals to (categoriteClotureId + 1)
        defaultEvaluationClotureShouldNotBeFound("categoriteClotureId.equals=" + (categoriteClotureId + 1));
    }

    @Test
    @Transactional
    void getAllEvaluationCloturesByDossierIsEqualToSomething() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);
        Dossier dossier = DossierResourceIT.createEntity(em);
        em.persist(dossier);
        em.flush();
        evaluationCloture.setDossier(dossier);
        evaluationClotureRepository.saveAndFlush(evaluationCloture);
        Long dossierId = dossier.getId();

        // Get all the evaluationClotureList where dossier equals to dossierId
        defaultEvaluationClotureShouldBeFound("dossierId.equals=" + dossierId);

        // Get all the evaluationClotureList where dossier equals to (dossierId + 1)
        defaultEvaluationClotureShouldNotBeFound("dossierId.equals=" + (dossierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEvaluationClotureShouldBeFound(String filter) throws Exception {
        restEvaluationClotureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationCloture.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineaire").value(hasItem(DEFAULT_LINEAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].coeff").value(hasItem(DEFAULT_COEFF.doubleValue())));

        // Check, that the count call also returns 1
        restEvaluationClotureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEvaluationClotureShouldNotBeFound(String filter) throws Exception {
        restEvaluationClotureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEvaluationClotureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEvaluationCloture() throws Exception {
        // Get the evaluationCloture
        restEvaluationClotureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEvaluationCloture() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        int databaseSizeBeforeUpdate = evaluationClotureRepository.findAll().size();

        // Update the evaluationCloture
        EvaluationCloture updatedEvaluationCloture = evaluationClotureRepository.findById(evaluationCloture.getId()).get();
        // Disconnect from session so that the updates on updatedEvaluationCloture are not directly saved in db
        em.detach(updatedEvaluationCloture);
        updatedEvaluationCloture.lineaire(UPDATED_LINEAIRE).coeff(UPDATED_COEFF);
        EvaluationClotureDTO evaluationClotureDTO = evaluationClotureMapper.toDto(updatedEvaluationCloture);

        restEvaluationClotureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluationClotureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationClotureDTO))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationCloture in the database
        List<EvaluationCloture> evaluationClotureList = evaluationClotureRepository.findAll();
        assertThat(evaluationClotureList).hasSize(databaseSizeBeforeUpdate);
        EvaluationCloture testEvaluationCloture = evaluationClotureList.get(evaluationClotureList.size() - 1);
        assertThat(testEvaluationCloture.getLineaire()).isEqualTo(UPDATED_LINEAIRE);
        assertThat(testEvaluationCloture.getCoeff()).isEqualTo(UPDATED_COEFF);
    }

    @Test
    @Transactional
    void putNonExistingEvaluationCloture() throws Exception {
        int databaseSizeBeforeUpdate = evaluationClotureRepository.findAll().size();
        evaluationCloture.setId(count.incrementAndGet());

        // Create the EvaluationCloture
        EvaluationClotureDTO evaluationClotureDTO = evaluationClotureMapper.toDto(evaluationCloture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationClotureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluationClotureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationClotureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCloture in the database
        List<EvaluationCloture> evaluationClotureList = evaluationClotureRepository.findAll();
        assertThat(evaluationClotureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvaluationCloture() throws Exception {
        int databaseSizeBeforeUpdate = evaluationClotureRepository.findAll().size();
        evaluationCloture.setId(count.incrementAndGet());

        // Create the EvaluationCloture
        EvaluationClotureDTO evaluationClotureDTO = evaluationClotureMapper.toDto(evaluationCloture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationClotureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluationClotureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCloture in the database
        List<EvaluationCloture> evaluationClotureList = evaluationClotureRepository.findAll();
        assertThat(evaluationClotureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvaluationCloture() throws Exception {
        int databaseSizeBeforeUpdate = evaluationClotureRepository.findAll().size();
        evaluationCloture.setId(count.incrementAndGet());

        // Create the EvaluationCloture
        EvaluationClotureDTO evaluationClotureDTO = evaluationClotureMapper.toDto(evaluationCloture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationClotureMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluationClotureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvaluationCloture in the database
        List<EvaluationCloture> evaluationClotureList = evaluationClotureRepository.findAll();
        assertThat(evaluationClotureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEvaluationClotureWithPatch() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        int databaseSizeBeforeUpdate = evaluationClotureRepository.findAll().size();

        // Update the evaluationCloture using partial update
        EvaluationCloture partialUpdatedEvaluationCloture = new EvaluationCloture();
        partialUpdatedEvaluationCloture.setId(evaluationCloture.getId());

        partialUpdatedEvaluationCloture.lineaire(UPDATED_LINEAIRE).coeff(UPDATED_COEFF);

        restEvaluationClotureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluationCloture.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluationCloture))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationCloture in the database
        List<EvaluationCloture> evaluationClotureList = evaluationClotureRepository.findAll();
        assertThat(evaluationClotureList).hasSize(databaseSizeBeforeUpdate);
        EvaluationCloture testEvaluationCloture = evaluationClotureList.get(evaluationClotureList.size() - 1);
        assertThat(testEvaluationCloture.getLineaire()).isEqualTo(UPDATED_LINEAIRE);
        assertThat(testEvaluationCloture.getCoeff()).isEqualTo(UPDATED_COEFF);
    }

    @Test
    @Transactional
    void fullUpdateEvaluationClotureWithPatch() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        int databaseSizeBeforeUpdate = evaluationClotureRepository.findAll().size();

        // Update the evaluationCloture using partial update
        EvaluationCloture partialUpdatedEvaluationCloture = new EvaluationCloture();
        partialUpdatedEvaluationCloture.setId(evaluationCloture.getId());

        partialUpdatedEvaluationCloture.lineaire(UPDATED_LINEAIRE).coeff(UPDATED_COEFF);

        restEvaluationClotureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluationCloture.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluationCloture))
            )
            .andExpect(status().isOk());

        // Validate the EvaluationCloture in the database
        List<EvaluationCloture> evaluationClotureList = evaluationClotureRepository.findAll();
        assertThat(evaluationClotureList).hasSize(databaseSizeBeforeUpdate);
        EvaluationCloture testEvaluationCloture = evaluationClotureList.get(evaluationClotureList.size() - 1);
        assertThat(testEvaluationCloture.getLineaire()).isEqualTo(UPDATED_LINEAIRE);
        assertThat(testEvaluationCloture.getCoeff()).isEqualTo(UPDATED_COEFF);
    }

    @Test
    @Transactional
    void patchNonExistingEvaluationCloture() throws Exception {
        int databaseSizeBeforeUpdate = evaluationClotureRepository.findAll().size();
        evaluationCloture.setId(count.incrementAndGet());

        // Create the EvaluationCloture
        EvaluationClotureDTO evaluationClotureDTO = evaluationClotureMapper.toDto(evaluationCloture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluationClotureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, evaluationClotureDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationClotureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCloture in the database
        List<EvaluationCloture> evaluationClotureList = evaluationClotureRepository.findAll();
        assertThat(evaluationClotureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvaluationCloture() throws Exception {
        int databaseSizeBeforeUpdate = evaluationClotureRepository.findAll().size();
        evaluationCloture.setId(count.incrementAndGet());

        // Create the EvaluationCloture
        EvaluationClotureDTO evaluationClotureDTO = evaluationClotureMapper.toDto(evaluationCloture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationClotureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationClotureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluationCloture in the database
        List<EvaluationCloture> evaluationClotureList = evaluationClotureRepository.findAll();
        assertThat(evaluationClotureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvaluationCloture() throws Exception {
        int databaseSizeBeforeUpdate = evaluationClotureRepository.findAll().size();
        evaluationCloture.setId(count.incrementAndGet());

        // Create the EvaluationCloture
        EvaluationClotureDTO evaluationClotureDTO = evaluationClotureMapper.toDto(evaluationCloture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluationClotureMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluationClotureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvaluationCloture in the database
        List<EvaluationCloture> evaluationClotureList = evaluationClotureRepository.findAll();
        assertThat(evaluationClotureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvaluationCloture() throws Exception {
        // Initialize the database
        evaluationClotureRepository.saveAndFlush(evaluationCloture);

        int databaseSizeBeforeDelete = evaluationClotureRepository.findAll().size();

        // Delete the evaluationCloture
        restEvaluationClotureMockMvc
            .perform(delete(ENTITY_API_URL_ID, evaluationCloture.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EvaluationCloture> evaluationClotureList = evaluationClotureRepository.findAll();
        assertThat(evaluationClotureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
