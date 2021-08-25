package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.CategorieCloture;
import com.pirtol.lab.domain.EvaluationCloture;
import com.pirtol.lab.repository.CategorieClotureRepository;
import com.pirtol.lab.service.criteria.CategorieClotureCriteria;
import com.pirtol.lab.service.dto.CategorieClotureDTO;
import com.pirtol.lab.service.mapper.CategorieClotureMapper;
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
 * Integration tests for the {@link CategorieClotureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategorieClotureResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX_METRE_CARE = 1D;
    private static final Double UPDATED_PRIX_METRE_CARE = 2D;
    private static final Double SMALLER_PRIX_METRE_CARE = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/categorie-clotures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategorieClotureRepository categorieClotureRepository;

    @Autowired
    private CategorieClotureMapper categorieClotureMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorieClotureMockMvc;

    private CategorieCloture categorieCloture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieCloture createEntity(EntityManager em) {
        CategorieCloture categorieCloture = new CategorieCloture().libelle(DEFAULT_LIBELLE).prixMetreCare(DEFAULT_PRIX_METRE_CARE);
        return categorieCloture;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieCloture createUpdatedEntity(EntityManager em) {
        CategorieCloture categorieCloture = new CategorieCloture().libelle(UPDATED_LIBELLE).prixMetreCare(UPDATED_PRIX_METRE_CARE);
        return categorieCloture;
    }

    @BeforeEach
    public void initTest() {
        categorieCloture = createEntity(em);
    }

    @Test
    @Transactional
    void createCategorieCloture() throws Exception {
        int databaseSizeBeforeCreate = categorieClotureRepository.findAll().size();
        // Create the CategorieCloture
        CategorieClotureDTO categorieClotureDTO = categorieClotureMapper.toDto(categorieCloture);
        restCategorieClotureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieClotureDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CategorieCloture in the database
        List<CategorieCloture> categorieClotureList = categorieClotureRepository.findAll();
        assertThat(categorieClotureList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieCloture testCategorieCloture = categorieClotureList.get(categorieClotureList.size() - 1);
        assertThat(testCategorieCloture.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testCategorieCloture.getPrixMetreCare()).isEqualTo(DEFAULT_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void createCategorieClotureWithExistingId() throws Exception {
        // Create the CategorieCloture with an existing ID
        categorieCloture.setId(1L);
        CategorieClotureDTO categorieClotureDTO = categorieClotureMapper.toDto(categorieCloture);

        int databaseSizeBeforeCreate = categorieClotureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieClotureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieClotureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieCloture in the database
        List<CategorieCloture> categorieClotureList = categorieClotureRepository.findAll();
        assertThat(categorieClotureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategorieClotures() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get all the categorieClotureList
        restCategorieClotureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieCloture.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].prixMetreCare").value(hasItem(DEFAULT_PRIX_METRE_CARE.doubleValue())));
    }

    @Test
    @Transactional
    void getCategorieCloture() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get the categorieCloture
        restCategorieClotureMockMvc
            .perform(get(ENTITY_API_URL_ID, categorieCloture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorieCloture.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.prixMetreCare").value(DEFAULT_PRIX_METRE_CARE.doubleValue()));
    }

    @Test
    @Transactional
    void getCategorieCloturesByIdFiltering() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        Long id = categorieCloture.getId();

        defaultCategorieClotureShouldBeFound("id.equals=" + id);
        defaultCategorieClotureShouldNotBeFound("id.notEquals=" + id);

        defaultCategorieClotureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategorieClotureShouldNotBeFound("id.greaterThan=" + id);

        defaultCategorieClotureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategorieClotureShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCategorieCloturesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get all the categorieClotureList where libelle equals to DEFAULT_LIBELLE
        defaultCategorieClotureShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the categorieClotureList where libelle equals to UPDATED_LIBELLE
        defaultCategorieClotureShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieCloturesByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get all the categorieClotureList where libelle not equals to DEFAULT_LIBELLE
        defaultCategorieClotureShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the categorieClotureList where libelle not equals to UPDATED_LIBELLE
        defaultCategorieClotureShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieCloturesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get all the categorieClotureList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultCategorieClotureShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the categorieClotureList where libelle equals to UPDATED_LIBELLE
        defaultCategorieClotureShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieCloturesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get all the categorieClotureList where libelle is not null
        defaultCategorieClotureShouldBeFound("libelle.specified=true");

        // Get all the categorieClotureList where libelle is null
        defaultCategorieClotureShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    void getAllCategorieCloturesByLibelleContainsSomething() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get all the categorieClotureList where libelle contains DEFAULT_LIBELLE
        defaultCategorieClotureShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the categorieClotureList where libelle contains UPDATED_LIBELLE
        defaultCategorieClotureShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieCloturesByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get all the categorieClotureList where libelle does not contain DEFAULT_LIBELLE
        defaultCategorieClotureShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the categorieClotureList where libelle does not contain UPDATED_LIBELLE
        defaultCategorieClotureShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieCloturesByPrixMetreCareIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get all the categorieClotureList where prixMetreCare equals to DEFAULT_PRIX_METRE_CARE
        defaultCategorieClotureShouldBeFound("prixMetreCare.equals=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieClotureList where prixMetreCare equals to UPDATED_PRIX_METRE_CARE
        defaultCategorieClotureShouldNotBeFound("prixMetreCare.equals=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieCloturesByPrixMetreCareIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get all the categorieClotureList where prixMetreCare not equals to DEFAULT_PRIX_METRE_CARE
        defaultCategorieClotureShouldNotBeFound("prixMetreCare.notEquals=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieClotureList where prixMetreCare not equals to UPDATED_PRIX_METRE_CARE
        defaultCategorieClotureShouldBeFound("prixMetreCare.notEquals=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieCloturesByPrixMetreCareIsInShouldWork() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get all the categorieClotureList where prixMetreCare in DEFAULT_PRIX_METRE_CARE or UPDATED_PRIX_METRE_CARE
        defaultCategorieClotureShouldBeFound("prixMetreCare.in=" + DEFAULT_PRIX_METRE_CARE + "," + UPDATED_PRIX_METRE_CARE);

        // Get all the categorieClotureList where prixMetreCare equals to UPDATED_PRIX_METRE_CARE
        defaultCategorieClotureShouldNotBeFound("prixMetreCare.in=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieCloturesByPrixMetreCareIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get all the categorieClotureList where prixMetreCare is not null
        defaultCategorieClotureShouldBeFound("prixMetreCare.specified=true");

        // Get all the categorieClotureList where prixMetreCare is null
        defaultCategorieClotureShouldNotBeFound("prixMetreCare.specified=false");
    }

    @Test
    @Transactional
    void getAllCategorieCloturesByPrixMetreCareIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get all the categorieClotureList where prixMetreCare is greater than or equal to DEFAULT_PRIX_METRE_CARE
        defaultCategorieClotureShouldBeFound("prixMetreCare.greaterThanOrEqual=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieClotureList where prixMetreCare is greater than or equal to UPDATED_PRIX_METRE_CARE
        defaultCategorieClotureShouldNotBeFound("prixMetreCare.greaterThanOrEqual=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieCloturesByPrixMetreCareIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get all the categorieClotureList where prixMetreCare is less than or equal to DEFAULT_PRIX_METRE_CARE
        defaultCategorieClotureShouldBeFound("prixMetreCare.lessThanOrEqual=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieClotureList where prixMetreCare is less than or equal to SMALLER_PRIX_METRE_CARE
        defaultCategorieClotureShouldNotBeFound("prixMetreCare.lessThanOrEqual=" + SMALLER_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieCloturesByPrixMetreCareIsLessThanSomething() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get all the categorieClotureList where prixMetreCare is less than DEFAULT_PRIX_METRE_CARE
        defaultCategorieClotureShouldNotBeFound("prixMetreCare.lessThan=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieClotureList where prixMetreCare is less than UPDATED_PRIX_METRE_CARE
        defaultCategorieClotureShouldBeFound("prixMetreCare.lessThan=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieCloturesByPrixMetreCareIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        // Get all the categorieClotureList where prixMetreCare is greater than DEFAULT_PRIX_METRE_CARE
        defaultCategorieClotureShouldNotBeFound("prixMetreCare.greaterThan=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieClotureList where prixMetreCare is greater than SMALLER_PRIX_METRE_CARE
        defaultCategorieClotureShouldBeFound("prixMetreCare.greaterThan=" + SMALLER_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieCloturesByEvaluationClotureIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);
        EvaluationCloture evaluationCloture = EvaluationClotureResourceIT.createEntity(em);
        em.persist(evaluationCloture);
        em.flush();
        categorieCloture.addEvaluationCloture(evaluationCloture);
        categorieClotureRepository.saveAndFlush(categorieCloture);
        Long evaluationClotureId = evaluationCloture.getId();

        // Get all the categorieClotureList where evaluationCloture equals to evaluationClotureId
        defaultCategorieClotureShouldBeFound("evaluationClotureId.equals=" + evaluationClotureId);

        // Get all the categorieClotureList where evaluationCloture equals to (evaluationClotureId + 1)
        defaultCategorieClotureShouldNotBeFound("evaluationClotureId.equals=" + (evaluationClotureId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategorieClotureShouldBeFound(String filter) throws Exception {
        restCategorieClotureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieCloture.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].prixMetreCare").value(hasItem(DEFAULT_PRIX_METRE_CARE.doubleValue())));

        // Check, that the count call also returns 1
        restCategorieClotureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategorieClotureShouldNotBeFound(String filter) throws Exception {
        restCategorieClotureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategorieClotureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCategorieCloture() throws Exception {
        // Get the categorieCloture
        restCategorieClotureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCategorieCloture() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        int databaseSizeBeforeUpdate = categorieClotureRepository.findAll().size();

        // Update the categorieCloture
        CategorieCloture updatedCategorieCloture = categorieClotureRepository.findById(categorieCloture.getId()).get();
        // Disconnect from session so that the updates on updatedCategorieCloture are not directly saved in db
        em.detach(updatedCategorieCloture);
        updatedCategorieCloture.libelle(UPDATED_LIBELLE).prixMetreCare(UPDATED_PRIX_METRE_CARE);
        CategorieClotureDTO categorieClotureDTO = categorieClotureMapper.toDto(updatedCategorieCloture);

        restCategorieClotureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieClotureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieClotureDTO))
            )
            .andExpect(status().isOk());

        // Validate the CategorieCloture in the database
        List<CategorieCloture> categorieClotureList = categorieClotureRepository.findAll();
        assertThat(categorieClotureList).hasSize(databaseSizeBeforeUpdate);
        CategorieCloture testCategorieCloture = categorieClotureList.get(categorieClotureList.size() - 1);
        assertThat(testCategorieCloture.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCategorieCloture.getPrixMetreCare()).isEqualTo(UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void putNonExistingCategorieCloture() throws Exception {
        int databaseSizeBeforeUpdate = categorieClotureRepository.findAll().size();
        categorieCloture.setId(count.incrementAndGet());

        // Create the CategorieCloture
        CategorieClotureDTO categorieClotureDTO = categorieClotureMapper.toDto(categorieCloture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieClotureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieClotureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieClotureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieCloture in the database
        List<CategorieCloture> categorieClotureList = categorieClotureRepository.findAll();
        assertThat(categorieClotureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategorieCloture() throws Exception {
        int databaseSizeBeforeUpdate = categorieClotureRepository.findAll().size();
        categorieCloture.setId(count.incrementAndGet());

        // Create the CategorieCloture
        CategorieClotureDTO categorieClotureDTO = categorieClotureMapper.toDto(categorieCloture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieClotureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieClotureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieCloture in the database
        List<CategorieCloture> categorieClotureList = categorieClotureRepository.findAll();
        assertThat(categorieClotureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategorieCloture() throws Exception {
        int databaseSizeBeforeUpdate = categorieClotureRepository.findAll().size();
        categorieCloture.setId(count.incrementAndGet());

        // Create the CategorieCloture
        CategorieClotureDTO categorieClotureDTO = categorieClotureMapper.toDto(categorieCloture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieClotureMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieClotureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieCloture in the database
        List<CategorieCloture> categorieClotureList = categorieClotureRepository.findAll();
        assertThat(categorieClotureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategorieClotureWithPatch() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        int databaseSizeBeforeUpdate = categorieClotureRepository.findAll().size();

        // Update the categorieCloture using partial update
        CategorieCloture partialUpdatedCategorieCloture = new CategorieCloture();
        partialUpdatedCategorieCloture.setId(categorieCloture.getId());

        restCategorieClotureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieCloture.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieCloture))
            )
            .andExpect(status().isOk());

        // Validate the CategorieCloture in the database
        List<CategorieCloture> categorieClotureList = categorieClotureRepository.findAll();
        assertThat(categorieClotureList).hasSize(databaseSizeBeforeUpdate);
        CategorieCloture testCategorieCloture = categorieClotureList.get(categorieClotureList.size() - 1);
        assertThat(testCategorieCloture.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testCategorieCloture.getPrixMetreCare()).isEqualTo(DEFAULT_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void fullUpdateCategorieClotureWithPatch() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        int databaseSizeBeforeUpdate = categorieClotureRepository.findAll().size();

        // Update the categorieCloture using partial update
        CategorieCloture partialUpdatedCategorieCloture = new CategorieCloture();
        partialUpdatedCategorieCloture.setId(categorieCloture.getId());

        partialUpdatedCategorieCloture.libelle(UPDATED_LIBELLE).prixMetreCare(UPDATED_PRIX_METRE_CARE);

        restCategorieClotureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieCloture.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieCloture))
            )
            .andExpect(status().isOk());

        // Validate the CategorieCloture in the database
        List<CategorieCloture> categorieClotureList = categorieClotureRepository.findAll();
        assertThat(categorieClotureList).hasSize(databaseSizeBeforeUpdate);
        CategorieCloture testCategorieCloture = categorieClotureList.get(categorieClotureList.size() - 1);
        assertThat(testCategorieCloture.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCategorieCloture.getPrixMetreCare()).isEqualTo(UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void patchNonExistingCategorieCloture() throws Exception {
        int databaseSizeBeforeUpdate = categorieClotureRepository.findAll().size();
        categorieCloture.setId(count.incrementAndGet());

        // Create the CategorieCloture
        CategorieClotureDTO categorieClotureDTO = categorieClotureMapper.toDto(categorieCloture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieClotureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categorieClotureDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieClotureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieCloture in the database
        List<CategorieCloture> categorieClotureList = categorieClotureRepository.findAll();
        assertThat(categorieClotureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategorieCloture() throws Exception {
        int databaseSizeBeforeUpdate = categorieClotureRepository.findAll().size();
        categorieCloture.setId(count.incrementAndGet());

        // Create the CategorieCloture
        CategorieClotureDTO categorieClotureDTO = categorieClotureMapper.toDto(categorieCloture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieClotureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieClotureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieCloture in the database
        List<CategorieCloture> categorieClotureList = categorieClotureRepository.findAll();
        assertThat(categorieClotureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategorieCloture() throws Exception {
        int databaseSizeBeforeUpdate = categorieClotureRepository.findAll().size();
        categorieCloture.setId(count.incrementAndGet());

        // Create the CategorieCloture
        CategorieClotureDTO categorieClotureDTO = categorieClotureMapper.toDto(categorieCloture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieClotureMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieClotureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieCloture in the database
        List<CategorieCloture> categorieClotureList = categorieClotureRepository.findAll();
        assertThat(categorieClotureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategorieCloture() throws Exception {
        // Initialize the database
        categorieClotureRepository.saveAndFlush(categorieCloture);

        int databaseSizeBeforeDelete = categorieClotureRepository.findAll().size();

        // Delete the categorieCloture
        restCategorieClotureMockMvc
            .perform(delete(ENTITY_API_URL_ID, categorieCloture.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategorieCloture> categorieClotureList = categorieClotureRepository.findAll();
        assertThat(categorieClotureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
