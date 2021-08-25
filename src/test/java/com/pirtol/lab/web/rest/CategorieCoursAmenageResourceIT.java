package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.CategorieCoursAmenage;
import com.pirtol.lab.domain.EvaluationCoursAmenage;
import com.pirtol.lab.repository.CategorieCoursAmenageRepository;
import com.pirtol.lab.service.criteria.CategorieCoursAmenageCriteria;
import com.pirtol.lab.service.dto.CategorieCoursAmenageDTO;
import com.pirtol.lab.service.mapper.CategorieCoursAmenageMapper;
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
 * Integration tests for the {@link CategorieCoursAmenageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategorieCoursAmenageResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX_METRE_CARE = 1D;
    private static final Double UPDATED_PRIX_METRE_CARE = 2D;
    private static final Double SMALLER_PRIX_METRE_CARE = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/categorie-cours-amenages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategorieCoursAmenageRepository categorieCoursAmenageRepository;

    @Autowired
    private CategorieCoursAmenageMapper categorieCoursAmenageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorieCoursAmenageMockMvc;

    private CategorieCoursAmenage categorieCoursAmenage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieCoursAmenage createEntity(EntityManager em) {
        CategorieCoursAmenage categorieCoursAmenage = new CategorieCoursAmenage()
            .libelle(DEFAULT_LIBELLE)
            .prixMetreCare(DEFAULT_PRIX_METRE_CARE);
        return categorieCoursAmenage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieCoursAmenage createUpdatedEntity(EntityManager em) {
        CategorieCoursAmenage categorieCoursAmenage = new CategorieCoursAmenage()
            .libelle(UPDATED_LIBELLE)
            .prixMetreCare(UPDATED_PRIX_METRE_CARE);
        return categorieCoursAmenage;
    }

    @BeforeEach
    public void initTest() {
        categorieCoursAmenage = createEntity(em);
    }

    @Test
    @Transactional
    void createCategorieCoursAmenage() throws Exception {
        int databaseSizeBeforeCreate = categorieCoursAmenageRepository.findAll().size();
        // Create the CategorieCoursAmenage
        CategorieCoursAmenageDTO categorieCoursAmenageDTO = categorieCoursAmenageMapper.toDto(categorieCoursAmenage);
        restCategorieCoursAmenageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieCoursAmenageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CategorieCoursAmenage in the database
        List<CategorieCoursAmenage> categorieCoursAmenageList = categorieCoursAmenageRepository.findAll();
        assertThat(categorieCoursAmenageList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieCoursAmenage testCategorieCoursAmenage = categorieCoursAmenageList.get(categorieCoursAmenageList.size() - 1);
        assertThat(testCategorieCoursAmenage.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testCategorieCoursAmenage.getPrixMetreCare()).isEqualTo(DEFAULT_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void createCategorieCoursAmenageWithExistingId() throws Exception {
        // Create the CategorieCoursAmenage with an existing ID
        categorieCoursAmenage.setId(1L);
        CategorieCoursAmenageDTO categorieCoursAmenageDTO = categorieCoursAmenageMapper.toDto(categorieCoursAmenage);

        int databaseSizeBeforeCreate = categorieCoursAmenageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieCoursAmenageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieCoursAmenageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieCoursAmenage in the database
        List<CategorieCoursAmenage> categorieCoursAmenageList = categorieCoursAmenageRepository.findAll();
        assertThat(categorieCoursAmenageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenages() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get all the categorieCoursAmenageList
        restCategorieCoursAmenageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieCoursAmenage.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].prixMetreCare").value(hasItem(DEFAULT_PRIX_METRE_CARE.doubleValue())));
    }

    @Test
    @Transactional
    void getCategorieCoursAmenage() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get the categorieCoursAmenage
        restCategorieCoursAmenageMockMvc
            .perform(get(ENTITY_API_URL_ID, categorieCoursAmenage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorieCoursAmenage.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.prixMetreCare").value(DEFAULT_PRIX_METRE_CARE.doubleValue()));
    }

    @Test
    @Transactional
    void getCategorieCoursAmenagesByIdFiltering() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        Long id = categorieCoursAmenage.getId();

        defaultCategorieCoursAmenageShouldBeFound("id.equals=" + id);
        defaultCategorieCoursAmenageShouldNotBeFound("id.notEquals=" + id);

        defaultCategorieCoursAmenageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategorieCoursAmenageShouldNotBeFound("id.greaterThan=" + id);

        defaultCategorieCoursAmenageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategorieCoursAmenageShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenagesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get all the categorieCoursAmenageList where libelle equals to DEFAULT_LIBELLE
        defaultCategorieCoursAmenageShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the categorieCoursAmenageList where libelle equals to UPDATED_LIBELLE
        defaultCategorieCoursAmenageShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenagesByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get all the categorieCoursAmenageList where libelle not equals to DEFAULT_LIBELLE
        defaultCategorieCoursAmenageShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the categorieCoursAmenageList where libelle not equals to UPDATED_LIBELLE
        defaultCategorieCoursAmenageShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenagesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get all the categorieCoursAmenageList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultCategorieCoursAmenageShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the categorieCoursAmenageList where libelle equals to UPDATED_LIBELLE
        defaultCategorieCoursAmenageShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenagesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get all the categorieCoursAmenageList where libelle is not null
        defaultCategorieCoursAmenageShouldBeFound("libelle.specified=true");

        // Get all the categorieCoursAmenageList where libelle is null
        defaultCategorieCoursAmenageShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenagesByLibelleContainsSomething() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get all the categorieCoursAmenageList where libelle contains DEFAULT_LIBELLE
        defaultCategorieCoursAmenageShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the categorieCoursAmenageList where libelle contains UPDATED_LIBELLE
        defaultCategorieCoursAmenageShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenagesByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get all the categorieCoursAmenageList where libelle does not contain DEFAULT_LIBELLE
        defaultCategorieCoursAmenageShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the categorieCoursAmenageList where libelle does not contain UPDATED_LIBELLE
        defaultCategorieCoursAmenageShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenagesByPrixMetreCareIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get all the categorieCoursAmenageList where prixMetreCare equals to DEFAULT_PRIX_METRE_CARE
        defaultCategorieCoursAmenageShouldBeFound("prixMetreCare.equals=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieCoursAmenageList where prixMetreCare equals to UPDATED_PRIX_METRE_CARE
        defaultCategorieCoursAmenageShouldNotBeFound("prixMetreCare.equals=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenagesByPrixMetreCareIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get all the categorieCoursAmenageList where prixMetreCare not equals to DEFAULT_PRIX_METRE_CARE
        defaultCategorieCoursAmenageShouldNotBeFound("prixMetreCare.notEquals=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieCoursAmenageList where prixMetreCare not equals to UPDATED_PRIX_METRE_CARE
        defaultCategorieCoursAmenageShouldBeFound("prixMetreCare.notEquals=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenagesByPrixMetreCareIsInShouldWork() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get all the categorieCoursAmenageList where prixMetreCare in DEFAULT_PRIX_METRE_CARE or UPDATED_PRIX_METRE_CARE
        defaultCategorieCoursAmenageShouldBeFound("prixMetreCare.in=" + DEFAULT_PRIX_METRE_CARE + "," + UPDATED_PRIX_METRE_CARE);

        // Get all the categorieCoursAmenageList where prixMetreCare equals to UPDATED_PRIX_METRE_CARE
        defaultCategorieCoursAmenageShouldNotBeFound("prixMetreCare.in=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenagesByPrixMetreCareIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get all the categorieCoursAmenageList where prixMetreCare is not null
        defaultCategorieCoursAmenageShouldBeFound("prixMetreCare.specified=true");

        // Get all the categorieCoursAmenageList where prixMetreCare is null
        defaultCategorieCoursAmenageShouldNotBeFound("prixMetreCare.specified=false");
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenagesByPrixMetreCareIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get all the categorieCoursAmenageList where prixMetreCare is greater than or equal to DEFAULT_PRIX_METRE_CARE
        defaultCategorieCoursAmenageShouldBeFound("prixMetreCare.greaterThanOrEqual=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieCoursAmenageList where prixMetreCare is greater than or equal to UPDATED_PRIX_METRE_CARE
        defaultCategorieCoursAmenageShouldNotBeFound("prixMetreCare.greaterThanOrEqual=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenagesByPrixMetreCareIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get all the categorieCoursAmenageList where prixMetreCare is less than or equal to DEFAULT_PRIX_METRE_CARE
        defaultCategorieCoursAmenageShouldBeFound("prixMetreCare.lessThanOrEqual=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieCoursAmenageList where prixMetreCare is less than or equal to SMALLER_PRIX_METRE_CARE
        defaultCategorieCoursAmenageShouldNotBeFound("prixMetreCare.lessThanOrEqual=" + SMALLER_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenagesByPrixMetreCareIsLessThanSomething() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get all the categorieCoursAmenageList where prixMetreCare is less than DEFAULT_PRIX_METRE_CARE
        defaultCategorieCoursAmenageShouldNotBeFound("prixMetreCare.lessThan=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieCoursAmenageList where prixMetreCare is less than UPDATED_PRIX_METRE_CARE
        defaultCategorieCoursAmenageShouldBeFound("prixMetreCare.lessThan=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenagesByPrixMetreCareIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        // Get all the categorieCoursAmenageList where prixMetreCare is greater than DEFAULT_PRIX_METRE_CARE
        defaultCategorieCoursAmenageShouldNotBeFound("prixMetreCare.greaterThan=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieCoursAmenageList where prixMetreCare is greater than SMALLER_PRIX_METRE_CARE
        defaultCategorieCoursAmenageShouldBeFound("prixMetreCare.greaterThan=" + SMALLER_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieCoursAmenagesByEvaluationCoursAmenageIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);
        EvaluationCoursAmenage evaluationCoursAmenage = EvaluationCoursAmenageResourceIT.createEntity(em);
        em.persist(evaluationCoursAmenage);
        em.flush();
        categorieCoursAmenage.addEvaluationCoursAmenage(evaluationCoursAmenage);
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);
        Long evaluationCoursAmenageId = evaluationCoursAmenage.getId();

        // Get all the categorieCoursAmenageList where evaluationCoursAmenage equals to evaluationCoursAmenageId
        defaultCategorieCoursAmenageShouldBeFound("evaluationCoursAmenageId.equals=" + evaluationCoursAmenageId);

        // Get all the categorieCoursAmenageList where evaluationCoursAmenage equals to (evaluationCoursAmenageId + 1)
        defaultCategorieCoursAmenageShouldNotBeFound("evaluationCoursAmenageId.equals=" + (evaluationCoursAmenageId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategorieCoursAmenageShouldBeFound(String filter) throws Exception {
        restCategorieCoursAmenageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieCoursAmenage.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].prixMetreCare").value(hasItem(DEFAULT_PRIX_METRE_CARE.doubleValue())));

        // Check, that the count call also returns 1
        restCategorieCoursAmenageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategorieCoursAmenageShouldNotBeFound(String filter) throws Exception {
        restCategorieCoursAmenageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategorieCoursAmenageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCategorieCoursAmenage() throws Exception {
        // Get the categorieCoursAmenage
        restCategorieCoursAmenageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCategorieCoursAmenage() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        int databaseSizeBeforeUpdate = categorieCoursAmenageRepository.findAll().size();

        // Update the categorieCoursAmenage
        CategorieCoursAmenage updatedCategorieCoursAmenage = categorieCoursAmenageRepository.findById(categorieCoursAmenage.getId()).get();
        // Disconnect from session so that the updates on updatedCategorieCoursAmenage are not directly saved in db
        em.detach(updatedCategorieCoursAmenage);
        updatedCategorieCoursAmenage.libelle(UPDATED_LIBELLE).prixMetreCare(UPDATED_PRIX_METRE_CARE);
        CategorieCoursAmenageDTO categorieCoursAmenageDTO = categorieCoursAmenageMapper.toDto(updatedCategorieCoursAmenage);

        restCategorieCoursAmenageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieCoursAmenageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieCoursAmenageDTO))
            )
            .andExpect(status().isOk());

        // Validate the CategorieCoursAmenage in the database
        List<CategorieCoursAmenage> categorieCoursAmenageList = categorieCoursAmenageRepository.findAll();
        assertThat(categorieCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
        CategorieCoursAmenage testCategorieCoursAmenage = categorieCoursAmenageList.get(categorieCoursAmenageList.size() - 1);
        assertThat(testCategorieCoursAmenage.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCategorieCoursAmenage.getPrixMetreCare()).isEqualTo(UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void putNonExistingCategorieCoursAmenage() throws Exception {
        int databaseSizeBeforeUpdate = categorieCoursAmenageRepository.findAll().size();
        categorieCoursAmenage.setId(count.incrementAndGet());

        // Create the CategorieCoursAmenage
        CategorieCoursAmenageDTO categorieCoursAmenageDTO = categorieCoursAmenageMapper.toDto(categorieCoursAmenage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieCoursAmenageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieCoursAmenageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieCoursAmenageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieCoursAmenage in the database
        List<CategorieCoursAmenage> categorieCoursAmenageList = categorieCoursAmenageRepository.findAll();
        assertThat(categorieCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategorieCoursAmenage() throws Exception {
        int databaseSizeBeforeUpdate = categorieCoursAmenageRepository.findAll().size();
        categorieCoursAmenage.setId(count.incrementAndGet());

        // Create the CategorieCoursAmenage
        CategorieCoursAmenageDTO categorieCoursAmenageDTO = categorieCoursAmenageMapper.toDto(categorieCoursAmenage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieCoursAmenageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieCoursAmenageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieCoursAmenage in the database
        List<CategorieCoursAmenage> categorieCoursAmenageList = categorieCoursAmenageRepository.findAll();
        assertThat(categorieCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategorieCoursAmenage() throws Exception {
        int databaseSizeBeforeUpdate = categorieCoursAmenageRepository.findAll().size();
        categorieCoursAmenage.setId(count.incrementAndGet());

        // Create the CategorieCoursAmenage
        CategorieCoursAmenageDTO categorieCoursAmenageDTO = categorieCoursAmenageMapper.toDto(categorieCoursAmenage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieCoursAmenageMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieCoursAmenageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieCoursAmenage in the database
        List<CategorieCoursAmenage> categorieCoursAmenageList = categorieCoursAmenageRepository.findAll();
        assertThat(categorieCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategorieCoursAmenageWithPatch() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        int databaseSizeBeforeUpdate = categorieCoursAmenageRepository.findAll().size();

        // Update the categorieCoursAmenage using partial update
        CategorieCoursAmenage partialUpdatedCategorieCoursAmenage = new CategorieCoursAmenage();
        partialUpdatedCategorieCoursAmenage.setId(categorieCoursAmenage.getId());

        partialUpdatedCategorieCoursAmenage.libelle(UPDATED_LIBELLE).prixMetreCare(UPDATED_PRIX_METRE_CARE);

        restCategorieCoursAmenageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieCoursAmenage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieCoursAmenage))
            )
            .andExpect(status().isOk());

        // Validate the CategorieCoursAmenage in the database
        List<CategorieCoursAmenage> categorieCoursAmenageList = categorieCoursAmenageRepository.findAll();
        assertThat(categorieCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
        CategorieCoursAmenage testCategorieCoursAmenage = categorieCoursAmenageList.get(categorieCoursAmenageList.size() - 1);
        assertThat(testCategorieCoursAmenage.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCategorieCoursAmenage.getPrixMetreCare()).isEqualTo(UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void fullUpdateCategorieCoursAmenageWithPatch() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        int databaseSizeBeforeUpdate = categorieCoursAmenageRepository.findAll().size();

        // Update the categorieCoursAmenage using partial update
        CategorieCoursAmenage partialUpdatedCategorieCoursAmenage = new CategorieCoursAmenage();
        partialUpdatedCategorieCoursAmenage.setId(categorieCoursAmenage.getId());

        partialUpdatedCategorieCoursAmenage.libelle(UPDATED_LIBELLE).prixMetreCare(UPDATED_PRIX_METRE_CARE);

        restCategorieCoursAmenageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieCoursAmenage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieCoursAmenage))
            )
            .andExpect(status().isOk());

        // Validate the CategorieCoursAmenage in the database
        List<CategorieCoursAmenage> categorieCoursAmenageList = categorieCoursAmenageRepository.findAll();
        assertThat(categorieCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
        CategorieCoursAmenage testCategorieCoursAmenage = categorieCoursAmenageList.get(categorieCoursAmenageList.size() - 1);
        assertThat(testCategorieCoursAmenage.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCategorieCoursAmenage.getPrixMetreCare()).isEqualTo(UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void patchNonExistingCategorieCoursAmenage() throws Exception {
        int databaseSizeBeforeUpdate = categorieCoursAmenageRepository.findAll().size();
        categorieCoursAmenage.setId(count.incrementAndGet());

        // Create the CategorieCoursAmenage
        CategorieCoursAmenageDTO categorieCoursAmenageDTO = categorieCoursAmenageMapper.toDto(categorieCoursAmenage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieCoursAmenageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categorieCoursAmenageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieCoursAmenageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieCoursAmenage in the database
        List<CategorieCoursAmenage> categorieCoursAmenageList = categorieCoursAmenageRepository.findAll();
        assertThat(categorieCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategorieCoursAmenage() throws Exception {
        int databaseSizeBeforeUpdate = categorieCoursAmenageRepository.findAll().size();
        categorieCoursAmenage.setId(count.incrementAndGet());

        // Create the CategorieCoursAmenage
        CategorieCoursAmenageDTO categorieCoursAmenageDTO = categorieCoursAmenageMapper.toDto(categorieCoursAmenage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieCoursAmenageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieCoursAmenageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieCoursAmenage in the database
        List<CategorieCoursAmenage> categorieCoursAmenageList = categorieCoursAmenageRepository.findAll();
        assertThat(categorieCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategorieCoursAmenage() throws Exception {
        int databaseSizeBeforeUpdate = categorieCoursAmenageRepository.findAll().size();
        categorieCoursAmenage.setId(count.incrementAndGet());

        // Create the CategorieCoursAmenage
        CategorieCoursAmenageDTO categorieCoursAmenageDTO = categorieCoursAmenageMapper.toDto(categorieCoursAmenage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieCoursAmenageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieCoursAmenageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieCoursAmenage in the database
        List<CategorieCoursAmenage> categorieCoursAmenageList = categorieCoursAmenageRepository.findAll();
        assertThat(categorieCoursAmenageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategorieCoursAmenage() throws Exception {
        // Initialize the database
        categorieCoursAmenageRepository.saveAndFlush(categorieCoursAmenage);

        int databaseSizeBeforeDelete = categorieCoursAmenageRepository.findAll().size();

        // Delete the categorieCoursAmenage
        restCategorieCoursAmenageMockMvc
            .perform(delete(ENTITY_API_URL_ID, categorieCoursAmenage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategorieCoursAmenage> categorieCoursAmenageList = categorieCoursAmenageRepository.findAll();
        assertThat(categorieCoursAmenageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
