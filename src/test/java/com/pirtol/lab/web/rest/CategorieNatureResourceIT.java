package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.CategorieNature;
import com.pirtol.lab.domain.EvaluationBatiments;
import com.pirtol.lab.repository.CategorieNatureRepository;
import com.pirtol.lab.service.criteria.CategorieNatureCriteria;
import com.pirtol.lab.service.dto.CategorieNatureDTO;
import com.pirtol.lab.service.mapper.CategorieNatureMapper;
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
 * Integration tests for the {@link CategorieNatureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategorieNatureResourceIT {

    private static final String DEFAULT_NATURE = "AAAAAAAAAA";
    private static final String UPDATED_NATURE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX_METRE_CARE = 1D;
    private static final Double UPDATED_PRIX_METRE_CARE = 2D;
    private static final Double SMALLER_PRIX_METRE_CARE = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/categorie-natures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategorieNatureRepository categorieNatureRepository;

    @Autowired
    private CategorieNatureMapper categorieNatureMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorieNatureMockMvc;

    private CategorieNature categorieNature;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieNature createEntity(EntityManager em) {
        CategorieNature categorieNature = new CategorieNature()
            .nature(DEFAULT_NATURE)
            .libelle(DEFAULT_LIBELLE)
            .prixMetreCare(DEFAULT_PRIX_METRE_CARE);
        return categorieNature;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieNature createUpdatedEntity(EntityManager em) {
        CategorieNature categorieNature = new CategorieNature()
            .nature(UPDATED_NATURE)
            .libelle(UPDATED_LIBELLE)
            .prixMetreCare(UPDATED_PRIX_METRE_CARE);
        return categorieNature;
    }

    @BeforeEach
    public void initTest() {
        categorieNature = createEntity(em);
    }

    @Test
    @Transactional
    void createCategorieNature() throws Exception {
        int databaseSizeBeforeCreate = categorieNatureRepository.findAll().size();
        // Create the CategorieNature
        CategorieNatureDTO categorieNatureDTO = categorieNatureMapper.toDto(categorieNature);
        restCategorieNatureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieNatureDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CategorieNature in the database
        List<CategorieNature> categorieNatureList = categorieNatureRepository.findAll();
        assertThat(categorieNatureList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieNature testCategorieNature = categorieNatureList.get(categorieNatureList.size() - 1);
        assertThat(testCategorieNature.getNature()).isEqualTo(DEFAULT_NATURE);
        assertThat(testCategorieNature.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testCategorieNature.getPrixMetreCare()).isEqualTo(DEFAULT_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void createCategorieNatureWithExistingId() throws Exception {
        // Create the CategorieNature with an existing ID
        categorieNature.setId(1L);
        CategorieNatureDTO categorieNatureDTO = categorieNatureMapper.toDto(categorieNature);

        int databaseSizeBeforeCreate = categorieNatureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieNatureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieNatureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieNature in the database
        List<CategorieNature> categorieNatureList = categorieNatureRepository.findAll();
        assertThat(categorieNatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategorieNatures() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList
        restCategorieNatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieNature.getId().intValue())))
            .andExpect(jsonPath("$.[*].nature").value(hasItem(DEFAULT_NATURE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].prixMetreCare").value(hasItem(DEFAULT_PRIX_METRE_CARE.doubleValue())));
    }

    @Test
    @Transactional
    void getCategorieNature() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get the categorieNature
        restCategorieNatureMockMvc
            .perform(get(ENTITY_API_URL_ID, categorieNature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorieNature.getId().intValue()))
            .andExpect(jsonPath("$.nature").value(DEFAULT_NATURE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.prixMetreCare").value(DEFAULT_PRIX_METRE_CARE.doubleValue()));
    }

    @Test
    @Transactional
    void getCategorieNaturesByIdFiltering() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        Long id = categorieNature.getId();

        defaultCategorieNatureShouldBeFound("id.equals=" + id);
        defaultCategorieNatureShouldNotBeFound("id.notEquals=" + id);

        defaultCategorieNatureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategorieNatureShouldNotBeFound("id.greaterThan=" + id);

        defaultCategorieNatureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategorieNatureShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByNatureIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where nature equals to DEFAULT_NATURE
        defaultCategorieNatureShouldBeFound("nature.equals=" + DEFAULT_NATURE);

        // Get all the categorieNatureList where nature equals to UPDATED_NATURE
        defaultCategorieNatureShouldNotBeFound("nature.equals=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByNatureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where nature not equals to DEFAULT_NATURE
        defaultCategorieNatureShouldNotBeFound("nature.notEquals=" + DEFAULT_NATURE);

        // Get all the categorieNatureList where nature not equals to UPDATED_NATURE
        defaultCategorieNatureShouldBeFound("nature.notEquals=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByNatureIsInShouldWork() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where nature in DEFAULT_NATURE or UPDATED_NATURE
        defaultCategorieNatureShouldBeFound("nature.in=" + DEFAULT_NATURE + "," + UPDATED_NATURE);

        // Get all the categorieNatureList where nature equals to UPDATED_NATURE
        defaultCategorieNatureShouldNotBeFound("nature.in=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByNatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where nature is not null
        defaultCategorieNatureShouldBeFound("nature.specified=true");

        // Get all the categorieNatureList where nature is null
        defaultCategorieNatureShouldNotBeFound("nature.specified=false");
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByNatureContainsSomething() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where nature contains DEFAULT_NATURE
        defaultCategorieNatureShouldBeFound("nature.contains=" + DEFAULT_NATURE);

        // Get all the categorieNatureList where nature contains UPDATED_NATURE
        defaultCategorieNatureShouldNotBeFound("nature.contains=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByNatureNotContainsSomething() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where nature does not contain DEFAULT_NATURE
        defaultCategorieNatureShouldNotBeFound("nature.doesNotContain=" + DEFAULT_NATURE);

        // Get all the categorieNatureList where nature does not contain UPDATED_NATURE
        defaultCategorieNatureShouldBeFound("nature.doesNotContain=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where libelle equals to DEFAULT_LIBELLE
        defaultCategorieNatureShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the categorieNatureList where libelle equals to UPDATED_LIBELLE
        defaultCategorieNatureShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where libelle not equals to DEFAULT_LIBELLE
        defaultCategorieNatureShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the categorieNatureList where libelle not equals to UPDATED_LIBELLE
        defaultCategorieNatureShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultCategorieNatureShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the categorieNatureList where libelle equals to UPDATED_LIBELLE
        defaultCategorieNatureShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where libelle is not null
        defaultCategorieNatureShouldBeFound("libelle.specified=true");

        // Get all the categorieNatureList where libelle is null
        defaultCategorieNatureShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByLibelleContainsSomething() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where libelle contains DEFAULT_LIBELLE
        defaultCategorieNatureShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the categorieNatureList where libelle contains UPDATED_LIBELLE
        defaultCategorieNatureShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where libelle does not contain DEFAULT_LIBELLE
        defaultCategorieNatureShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the categorieNatureList where libelle does not contain UPDATED_LIBELLE
        defaultCategorieNatureShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByPrixMetreCareIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where prixMetreCare equals to DEFAULT_PRIX_METRE_CARE
        defaultCategorieNatureShouldBeFound("prixMetreCare.equals=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieNatureList where prixMetreCare equals to UPDATED_PRIX_METRE_CARE
        defaultCategorieNatureShouldNotBeFound("prixMetreCare.equals=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByPrixMetreCareIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where prixMetreCare not equals to DEFAULT_PRIX_METRE_CARE
        defaultCategorieNatureShouldNotBeFound("prixMetreCare.notEquals=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieNatureList where prixMetreCare not equals to UPDATED_PRIX_METRE_CARE
        defaultCategorieNatureShouldBeFound("prixMetreCare.notEquals=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByPrixMetreCareIsInShouldWork() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where prixMetreCare in DEFAULT_PRIX_METRE_CARE or UPDATED_PRIX_METRE_CARE
        defaultCategorieNatureShouldBeFound("prixMetreCare.in=" + DEFAULT_PRIX_METRE_CARE + "," + UPDATED_PRIX_METRE_CARE);

        // Get all the categorieNatureList where prixMetreCare equals to UPDATED_PRIX_METRE_CARE
        defaultCategorieNatureShouldNotBeFound("prixMetreCare.in=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByPrixMetreCareIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where prixMetreCare is not null
        defaultCategorieNatureShouldBeFound("prixMetreCare.specified=true");

        // Get all the categorieNatureList where prixMetreCare is null
        defaultCategorieNatureShouldNotBeFound("prixMetreCare.specified=false");
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByPrixMetreCareIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where prixMetreCare is greater than or equal to DEFAULT_PRIX_METRE_CARE
        defaultCategorieNatureShouldBeFound("prixMetreCare.greaterThanOrEqual=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieNatureList where prixMetreCare is greater than or equal to UPDATED_PRIX_METRE_CARE
        defaultCategorieNatureShouldNotBeFound("prixMetreCare.greaterThanOrEqual=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByPrixMetreCareIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where prixMetreCare is less than or equal to DEFAULT_PRIX_METRE_CARE
        defaultCategorieNatureShouldBeFound("prixMetreCare.lessThanOrEqual=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieNatureList where prixMetreCare is less than or equal to SMALLER_PRIX_METRE_CARE
        defaultCategorieNatureShouldNotBeFound("prixMetreCare.lessThanOrEqual=" + SMALLER_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByPrixMetreCareIsLessThanSomething() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where prixMetreCare is less than DEFAULT_PRIX_METRE_CARE
        defaultCategorieNatureShouldNotBeFound("prixMetreCare.lessThan=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieNatureList where prixMetreCare is less than UPDATED_PRIX_METRE_CARE
        defaultCategorieNatureShouldBeFound("prixMetreCare.lessThan=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByPrixMetreCareIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        // Get all the categorieNatureList where prixMetreCare is greater than DEFAULT_PRIX_METRE_CARE
        defaultCategorieNatureShouldNotBeFound("prixMetreCare.greaterThan=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieNatureList where prixMetreCare is greater than SMALLER_PRIX_METRE_CARE
        defaultCategorieNatureShouldBeFound("prixMetreCare.greaterThan=" + SMALLER_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieNaturesByEvaluationBatimentsIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);
        EvaluationBatiments evaluationBatiments = EvaluationBatimentsResourceIT.createEntity(em);
        em.persist(evaluationBatiments);
        em.flush();
        categorieNature.addEvaluationBatiments(evaluationBatiments);
        categorieNatureRepository.saveAndFlush(categorieNature);
        Long evaluationBatimentsId = evaluationBatiments.getId();

        // Get all the categorieNatureList where evaluationBatiments equals to evaluationBatimentsId
        defaultCategorieNatureShouldBeFound("evaluationBatimentsId.equals=" + evaluationBatimentsId);

        // Get all the categorieNatureList where evaluationBatiments equals to (evaluationBatimentsId + 1)
        defaultCategorieNatureShouldNotBeFound("evaluationBatimentsId.equals=" + (evaluationBatimentsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategorieNatureShouldBeFound(String filter) throws Exception {
        restCategorieNatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieNature.getId().intValue())))
            .andExpect(jsonPath("$.[*].nature").value(hasItem(DEFAULT_NATURE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].prixMetreCare").value(hasItem(DEFAULT_PRIX_METRE_CARE.doubleValue())));

        // Check, that the count call also returns 1
        restCategorieNatureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategorieNatureShouldNotBeFound(String filter) throws Exception {
        restCategorieNatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategorieNatureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCategorieNature() throws Exception {
        // Get the categorieNature
        restCategorieNatureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCategorieNature() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        int databaseSizeBeforeUpdate = categorieNatureRepository.findAll().size();

        // Update the categorieNature
        CategorieNature updatedCategorieNature = categorieNatureRepository.findById(categorieNature.getId()).get();
        // Disconnect from session so that the updates on updatedCategorieNature are not directly saved in db
        em.detach(updatedCategorieNature);
        updatedCategorieNature.nature(UPDATED_NATURE).libelle(UPDATED_LIBELLE).prixMetreCare(UPDATED_PRIX_METRE_CARE);
        CategorieNatureDTO categorieNatureDTO = categorieNatureMapper.toDto(updatedCategorieNature);

        restCategorieNatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieNatureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieNatureDTO))
            )
            .andExpect(status().isOk());

        // Validate the CategorieNature in the database
        List<CategorieNature> categorieNatureList = categorieNatureRepository.findAll();
        assertThat(categorieNatureList).hasSize(databaseSizeBeforeUpdate);
        CategorieNature testCategorieNature = categorieNatureList.get(categorieNatureList.size() - 1);
        assertThat(testCategorieNature.getNature()).isEqualTo(UPDATED_NATURE);
        assertThat(testCategorieNature.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCategorieNature.getPrixMetreCare()).isEqualTo(UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void putNonExistingCategorieNature() throws Exception {
        int databaseSizeBeforeUpdate = categorieNatureRepository.findAll().size();
        categorieNature.setId(count.incrementAndGet());

        // Create the CategorieNature
        CategorieNatureDTO categorieNatureDTO = categorieNatureMapper.toDto(categorieNature);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieNatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieNatureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieNatureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieNature in the database
        List<CategorieNature> categorieNatureList = categorieNatureRepository.findAll();
        assertThat(categorieNatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategorieNature() throws Exception {
        int databaseSizeBeforeUpdate = categorieNatureRepository.findAll().size();
        categorieNature.setId(count.incrementAndGet());

        // Create the CategorieNature
        CategorieNatureDTO categorieNatureDTO = categorieNatureMapper.toDto(categorieNature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieNatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieNatureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieNature in the database
        List<CategorieNature> categorieNatureList = categorieNatureRepository.findAll();
        assertThat(categorieNatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategorieNature() throws Exception {
        int databaseSizeBeforeUpdate = categorieNatureRepository.findAll().size();
        categorieNature.setId(count.incrementAndGet());

        // Create the CategorieNature
        CategorieNatureDTO categorieNatureDTO = categorieNatureMapper.toDto(categorieNature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieNatureMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieNatureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieNature in the database
        List<CategorieNature> categorieNatureList = categorieNatureRepository.findAll();
        assertThat(categorieNatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategorieNatureWithPatch() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        int databaseSizeBeforeUpdate = categorieNatureRepository.findAll().size();

        // Update the categorieNature using partial update
        CategorieNature partialUpdatedCategorieNature = new CategorieNature();
        partialUpdatedCategorieNature.setId(categorieNature.getId());

        restCategorieNatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieNature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieNature))
            )
            .andExpect(status().isOk());

        // Validate the CategorieNature in the database
        List<CategorieNature> categorieNatureList = categorieNatureRepository.findAll();
        assertThat(categorieNatureList).hasSize(databaseSizeBeforeUpdate);
        CategorieNature testCategorieNature = categorieNatureList.get(categorieNatureList.size() - 1);
        assertThat(testCategorieNature.getNature()).isEqualTo(DEFAULT_NATURE);
        assertThat(testCategorieNature.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testCategorieNature.getPrixMetreCare()).isEqualTo(DEFAULT_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void fullUpdateCategorieNatureWithPatch() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        int databaseSizeBeforeUpdate = categorieNatureRepository.findAll().size();

        // Update the categorieNature using partial update
        CategorieNature partialUpdatedCategorieNature = new CategorieNature();
        partialUpdatedCategorieNature.setId(categorieNature.getId());

        partialUpdatedCategorieNature.nature(UPDATED_NATURE).libelle(UPDATED_LIBELLE).prixMetreCare(UPDATED_PRIX_METRE_CARE);

        restCategorieNatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieNature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieNature))
            )
            .andExpect(status().isOk());

        // Validate the CategorieNature in the database
        List<CategorieNature> categorieNatureList = categorieNatureRepository.findAll();
        assertThat(categorieNatureList).hasSize(databaseSizeBeforeUpdate);
        CategorieNature testCategorieNature = categorieNatureList.get(categorieNatureList.size() - 1);
        assertThat(testCategorieNature.getNature()).isEqualTo(UPDATED_NATURE);
        assertThat(testCategorieNature.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCategorieNature.getPrixMetreCare()).isEqualTo(UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void patchNonExistingCategorieNature() throws Exception {
        int databaseSizeBeforeUpdate = categorieNatureRepository.findAll().size();
        categorieNature.setId(count.incrementAndGet());

        // Create the CategorieNature
        CategorieNatureDTO categorieNatureDTO = categorieNatureMapper.toDto(categorieNature);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieNatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categorieNatureDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieNatureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieNature in the database
        List<CategorieNature> categorieNatureList = categorieNatureRepository.findAll();
        assertThat(categorieNatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategorieNature() throws Exception {
        int databaseSizeBeforeUpdate = categorieNatureRepository.findAll().size();
        categorieNature.setId(count.incrementAndGet());

        // Create the CategorieNature
        CategorieNatureDTO categorieNatureDTO = categorieNatureMapper.toDto(categorieNature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieNatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieNatureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieNature in the database
        List<CategorieNature> categorieNatureList = categorieNatureRepository.findAll();
        assertThat(categorieNatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategorieNature() throws Exception {
        int databaseSizeBeforeUpdate = categorieNatureRepository.findAll().size();
        categorieNature.setId(count.incrementAndGet());

        // Create the CategorieNature
        CategorieNatureDTO categorieNatureDTO = categorieNatureMapper.toDto(categorieNature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieNatureMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieNatureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieNature in the database
        List<CategorieNature> categorieNatureList = categorieNatureRepository.findAll();
        assertThat(categorieNatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategorieNature() throws Exception {
        // Initialize the database
        categorieNatureRepository.saveAndFlush(categorieNature);

        int databaseSizeBeforeDelete = categorieNatureRepository.findAll().size();

        // Delete the categorieNature
        restCategorieNatureMockMvc
            .perform(delete(ENTITY_API_URL_ID, categorieNature.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategorieNature> categorieNatureList = categorieNatureRepository.findAll();
        assertThat(categorieNatureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
