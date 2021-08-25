package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.CategorieBatie;
import com.pirtol.lab.domain.EvaluationSurfaceBatie;
import com.pirtol.lab.repository.CategorieBatieRepository;
import com.pirtol.lab.service.criteria.CategorieBatieCriteria;
import com.pirtol.lab.service.dto.CategorieBatieDTO;
import com.pirtol.lab.service.mapper.CategorieBatieMapper;
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
 * Integration tests for the {@link CategorieBatieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategorieBatieResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX_METRE_CARE = 1D;
    private static final Double UPDATED_PRIX_METRE_CARE = 2D;
    private static final Double SMALLER_PRIX_METRE_CARE = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/categorie-baties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategorieBatieRepository categorieBatieRepository;

    @Autowired
    private CategorieBatieMapper categorieBatieMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorieBatieMockMvc;

    private CategorieBatie categorieBatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieBatie createEntity(EntityManager em) {
        CategorieBatie categorieBatie = new CategorieBatie().libelle(DEFAULT_LIBELLE).prixMetreCare(DEFAULT_PRIX_METRE_CARE);
        return categorieBatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieBatie createUpdatedEntity(EntityManager em) {
        CategorieBatie categorieBatie = new CategorieBatie().libelle(UPDATED_LIBELLE).prixMetreCare(UPDATED_PRIX_METRE_CARE);
        return categorieBatie;
    }

    @BeforeEach
    public void initTest() {
        categorieBatie = createEntity(em);
    }

    @Test
    @Transactional
    void createCategorieBatie() throws Exception {
        int databaseSizeBeforeCreate = categorieBatieRepository.findAll().size();
        // Create the CategorieBatie
        CategorieBatieDTO categorieBatieDTO = categorieBatieMapper.toDto(categorieBatie);
        restCategorieBatieMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieBatieDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CategorieBatie in the database
        List<CategorieBatie> categorieBatieList = categorieBatieRepository.findAll();
        assertThat(categorieBatieList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieBatie testCategorieBatie = categorieBatieList.get(categorieBatieList.size() - 1);
        assertThat(testCategorieBatie.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testCategorieBatie.getPrixMetreCare()).isEqualTo(DEFAULT_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void createCategorieBatieWithExistingId() throws Exception {
        // Create the CategorieBatie with an existing ID
        categorieBatie.setId(1L);
        CategorieBatieDTO categorieBatieDTO = categorieBatieMapper.toDto(categorieBatie);

        int databaseSizeBeforeCreate = categorieBatieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieBatieMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieBatieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieBatie in the database
        List<CategorieBatie> categorieBatieList = categorieBatieRepository.findAll();
        assertThat(categorieBatieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategorieBaties() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get all the categorieBatieList
        restCategorieBatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieBatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].prixMetreCare").value(hasItem(DEFAULT_PRIX_METRE_CARE.doubleValue())));
    }

    @Test
    @Transactional
    void getCategorieBatie() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get the categorieBatie
        restCategorieBatieMockMvc
            .perform(get(ENTITY_API_URL_ID, categorieBatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorieBatie.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.prixMetreCare").value(DEFAULT_PRIX_METRE_CARE.doubleValue()));
    }

    @Test
    @Transactional
    void getCategorieBatiesByIdFiltering() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        Long id = categorieBatie.getId();

        defaultCategorieBatieShouldBeFound("id.equals=" + id);
        defaultCategorieBatieShouldNotBeFound("id.notEquals=" + id);

        defaultCategorieBatieShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategorieBatieShouldNotBeFound("id.greaterThan=" + id);

        defaultCategorieBatieShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategorieBatieShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCategorieBatiesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get all the categorieBatieList where libelle equals to DEFAULT_LIBELLE
        defaultCategorieBatieShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the categorieBatieList where libelle equals to UPDATED_LIBELLE
        defaultCategorieBatieShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieBatiesByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get all the categorieBatieList where libelle not equals to DEFAULT_LIBELLE
        defaultCategorieBatieShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the categorieBatieList where libelle not equals to UPDATED_LIBELLE
        defaultCategorieBatieShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieBatiesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get all the categorieBatieList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultCategorieBatieShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the categorieBatieList where libelle equals to UPDATED_LIBELLE
        defaultCategorieBatieShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieBatiesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get all the categorieBatieList where libelle is not null
        defaultCategorieBatieShouldBeFound("libelle.specified=true");

        // Get all the categorieBatieList where libelle is null
        defaultCategorieBatieShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    void getAllCategorieBatiesByLibelleContainsSomething() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get all the categorieBatieList where libelle contains DEFAULT_LIBELLE
        defaultCategorieBatieShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the categorieBatieList where libelle contains UPDATED_LIBELLE
        defaultCategorieBatieShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieBatiesByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get all the categorieBatieList where libelle does not contain DEFAULT_LIBELLE
        defaultCategorieBatieShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the categorieBatieList where libelle does not contain UPDATED_LIBELLE
        defaultCategorieBatieShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCategorieBatiesByPrixMetreCareIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get all the categorieBatieList where prixMetreCare equals to DEFAULT_PRIX_METRE_CARE
        defaultCategorieBatieShouldBeFound("prixMetreCare.equals=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieBatieList where prixMetreCare equals to UPDATED_PRIX_METRE_CARE
        defaultCategorieBatieShouldNotBeFound("prixMetreCare.equals=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieBatiesByPrixMetreCareIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get all the categorieBatieList where prixMetreCare not equals to DEFAULT_PRIX_METRE_CARE
        defaultCategorieBatieShouldNotBeFound("prixMetreCare.notEquals=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieBatieList where prixMetreCare not equals to UPDATED_PRIX_METRE_CARE
        defaultCategorieBatieShouldBeFound("prixMetreCare.notEquals=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieBatiesByPrixMetreCareIsInShouldWork() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get all the categorieBatieList where prixMetreCare in DEFAULT_PRIX_METRE_CARE or UPDATED_PRIX_METRE_CARE
        defaultCategorieBatieShouldBeFound("prixMetreCare.in=" + DEFAULT_PRIX_METRE_CARE + "," + UPDATED_PRIX_METRE_CARE);

        // Get all the categorieBatieList where prixMetreCare equals to UPDATED_PRIX_METRE_CARE
        defaultCategorieBatieShouldNotBeFound("prixMetreCare.in=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieBatiesByPrixMetreCareIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get all the categorieBatieList where prixMetreCare is not null
        defaultCategorieBatieShouldBeFound("prixMetreCare.specified=true");

        // Get all the categorieBatieList where prixMetreCare is null
        defaultCategorieBatieShouldNotBeFound("prixMetreCare.specified=false");
    }

    @Test
    @Transactional
    void getAllCategorieBatiesByPrixMetreCareIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get all the categorieBatieList where prixMetreCare is greater than or equal to DEFAULT_PRIX_METRE_CARE
        defaultCategorieBatieShouldBeFound("prixMetreCare.greaterThanOrEqual=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieBatieList where prixMetreCare is greater than or equal to UPDATED_PRIX_METRE_CARE
        defaultCategorieBatieShouldNotBeFound("prixMetreCare.greaterThanOrEqual=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieBatiesByPrixMetreCareIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get all the categorieBatieList where prixMetreCare is less than or equal to DEFAULT_PRIX_METRE_CARE
        defaultCategorieBatieShouldBeFound("prixMetreCare.lessThanOrEqual=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieBatieList where prixMetreCare is less than or equal to SMALLER_PRIX_METRE_CARE
        defaultCategorieBatieShouldNotBeFound("prixMetreCare.lessThanOrEqual=" + SMALLER_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieBatiesByPrixMetreCareIsLessThanSomething() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get all the categorieBatieList where prixMetreCare is less than DEFAULT_PRIX_METRE_CARE
        defaultCategorieBatieShouldNotBeFound("prixMetreCare.lessThan=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieBatieList where prixMetreCare is less than UPDATED_PRIX_METRE_CARE
        defaultCategorieBatieShouldBeFound("prixMetreCare.lessThan=" + UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieBatiesByPrixMetreCareIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        // Get all the categorieBatieList where prixMetreCare is greater than DEFAULT_PRIX_METRE_CARE
        defaultCategorieBatieShouldNotBeFound("prixMetreCare.greaterThan=" + DEFAULT_PRIX_METRE_CARE);

        // Get all the categorieBatieList where prixMetreCare is greater than SMALLER_PRIX_METRE_CARE
        defaultCategorieBatieShouldBeFound("prixMetreCare.greaterThan=" + SMALLER_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void getAllCategorieBatiesByEvaluationSurfaceBatieIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);
        EvaluationSurfaceBatie evaluationSurfaceBatie = EvaluationSurfaceBatieResourceIT.createEntity(em);
        em.persist(evaluationSurfaceBatie);
        em.flush();
        categorieBatie.addEvaluationSurfaceBatie(evaluationSurfaceBatie);
        categorieBatieRepository.saveAndFlush(categorieBatie);
        Long evaluationSurfaceBatieId = evaluationSurfaceBatie.getId();

        // Get all the categorieBatieList where evaluationSurfaceBatie equals to evaluationSurfaceBatieId
        defaultCategorieBatieShouldBeFound("evaluationSurfaceBatieId.equals=" + evaluationSurfaceBatieId);

        // Get all the categorieBatieList where evaluationSurfaceBatie equals to (evaluationSurfaceBatieId + 1)
        defaultCategorieBatieShouldNotBeFound("evaluationSurfaceBatieId.equals=" + (evaluationSurfaceBatieId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategorieBatieShouldBeFound(String filter) throws Exception {
        restCategorieBatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieBatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].prixMetreCare").value(hasItem(DEFAULT_PRIX_METRE_CARE.doubleValue())));

        // Check, that the count call also returns 1
        restCategorieBatieMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategorieBatieShouldNotBeFound(String filter) throws Exception {
        restCategorieBatieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategorieBatieMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCategorieBatie() throws Exception {
        // Get the categorieBatie
        restCategorieBatieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCategorieBatie() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        int databaseSizeBeforeUpdate = categorieBatieRepository.findAll().size();

        // Update the categorieBatie
        CategorieBatie updatedCategorieBatie = categorieBatieRepository.findById(categorieBatie.getId()).get();
        // Disconnect from session so that the updates on updatedCategorieBatie are not directly saved in db
        em.detach(updatedCategorieBatie);
        updatedCategorieBatie.libelle(UPDATED_LIBELLE).prixMetreCare(UPDATED_PRIX_METRE_CARE);
        CategorieBatieDTO categorieBatieDTO = categorieBatieMapper.toDto(updatedCategorieBatie);

        restCategorieBatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieBatieDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieBatieDTO))
            )
            .andExpect(status().isOk());

        // Validate the CategorieBatie in the database
        List<CategorieBatie> categorieBatieList = categorieBatieRepository.findAll();
        assertThat(categorieBatieList).hasSize(databaseSizeBeforeUpdate);
        CategorieBatie testCategorieBatie = categorieBatieList.get(categorieBatieList.size() - 1);
        assertThat(testCategorieBatie.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCategorieBatie.getPrixMetreCare()).isEqualTo(UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void putNonExistingCategorieBatie() throws Exception {
        int databaseSizeBeforeUpdate = categorieBatieRepository.findAll().size();
        categorieBatie.setId(count.incrementAndGet());

        // Create the CategorieBatie
        CategorieBatieDTO categorieBatieDTO = categorieBatieMapper.toDto(categorieBatie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieBatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieBatieDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieBatieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieBatie in the database
        List<CategorieBatie> categorieBatieList = categorieBatieRepository.findAll();
        assertThat(categorieBatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategorieBatie() throws Exception {
        int databaseSizeBeforeUpdate = categorieBatieRepository.findAll().size();
        categorieBatie.setId(count.incrementAndGet());

        // Create the CategorieBatie
        CategorieBatieDTO categorieBatieDTO = categorieBatieMapper.toDto(categorieBatie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieBatieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieBatieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieBatie in the database
        List<CategorieBatie> categorieBatieList = categorieBatieRepository.findAll();
        assertThat(categorieBatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategorieBatie() throws Exception {
        int databaseSizeBeforeUpdate = categorieBatieRepository.findAll().size();
        categorieBatie.setId(count.incrementAndGet());

        // Create the CategorieBatie
        CategorieBatieDTO categorieBatieDTO = categorieBatieMapper.toDto(categorieBatie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieBatieMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieBatieDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieBatie in the database
        List<CategorieBatie> categorieBatieList = categorieBatieRepository.findAll();
        assertThat(categorieBatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategorieBatieWithPatch() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        int databaseSizeBeforeUpdate = categorieBatieRepository.findAll().size();

        // Update the categorieBatie using partial update
        CategorieBatie partialUpdatedCategorieBatie = new CategorieBatie();
        partialUpdatedCategorieBatie.setId(categorieBatie.getId());

        partialUpdatedCategorieBatie.prixMetreCare(UPDATED_PRIX_METRE_CARE);

        restCategorieBatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieBatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieBatie))
            )
            .andExpect(status().isOk());

        // Validate the CategorieBatie in the database
        List<CategorieBatie> categorieBatieList = categorieBatieRepository.findAll();
        assertThat(categorieBatieList).hasSize(databaseSizeBeforeUpdate);
        CategorieBatie testCategorieBatie = categorieBatieList.get(categorieBatieList.size() - 1);
        assertThat(testCategorieBatie.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testCategorieBatie.getPrixMetreCare()).isEqualTo(UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void fullUpdateCategorieBatieWithPatch() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        int databaseSizeBeforeUpdate = categorieBatieRepository.findAll().size();

        // Update the categorieBatie using partial update
        CategorieBatie partialUpdatedCategorieBatie = new CategorieBatie();
        partialUpdatedCategorieBatie.setId(categorieBatie.getId());

        partialUpdatedCategorieBatie.libelle(UPDATED_LIBELLE).prixMetreCare(UPDATED_PRIX_METRE_CARE);

        restCategorieBatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieBatie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieBatie))
            )
            .andExpect(status().isOk());

        // Validate the CategorieBatie in the database
        List<CategorieBatie> categorieBatieList = categorieBatieRepository.findAll();
        assertThat(categorieBatieList).hasSize(databaseSizeBeforeUpdate);
        CategorieBatie testCategorieBatie = categorieBatieList.get(categorieBatieList.size() - 1);
        assertThat(testCategorieBatie.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCategorieBatie.getPrixMetreCare()).isEqualTo(UPDATED_PRIX_METRE_CARE);
    }

    @Test
    @Transactional
    void patchNonExistingCategorieBatie() throws Exception {
        int databaseSizeBeforeUpdate = categorieBatieRepository.findAll().size();
        categorieBatie.setId(count.incrementAndGet());

        // Create the CategorieBatie
        CategorieBatieDTO categorieBatieDTO = categorieBatieMapper.toDto(categorieBatie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieBatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categorieBatieDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieBatieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieBatie in the database
        List<CategorieBatie> categorieBatieList = categorieBatieRepository.findAll();
        assertThat(categorieBatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategorieBatie() throws Exception {
        int databaseSizeBeforeUpdate = categorieBatieRepository.findAll().size();
        categorieBatie.setId(count.incrementAndGet());

        // Create the CategorieBatie
        CategorieBatieDTO categorieBatieDTO = categorieBatieMapper.toDto(categorieBatie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieBatieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieBatieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieBatie in the database
        List<CategorieBatie> categorieBatieList = categorieBatieRepository.findAll();
        assertThat(categorieBatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategorieBatie() throws Exception {
        int databaseSizeBeforeUpdate = categorieBatieRepository.findAll().size();
        categorieBatie.setId(count.incrementAndGet());

        // Create the CategorieBatie
        CategorieBatieDTO categorieBatieDTO = categorieBatieMapper.toDto(categorieBatie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieBatieMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieBatieDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieBatie in the database
        List<CategorieBatie> categorieBatieList = categorieBatieRepository.findAll();
        assertThat(categorieBatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategorieBatie() throws Exception {
        // Initialize the database
        categorieBatieRepository.saveAndFlush(categorieBatie);

        int databaseSizeBeforeDelete = categorieBatieRepository.findAll().size();

        // Delete the categorieBatie
        restCategorieBatieMockMvc
            .perform(delete(ENTITY_API_URL_ID, categorieBatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategorieBatie> categorieBatieList = categorieBatieRepository.findAll();
        assertThat(categorieBatieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
