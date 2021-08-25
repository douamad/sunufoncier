package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.Dossier;
import com.pirtol.lab.domain.UsageDossier;
import com.pirtol.lab.repository.UsageDossierRepository;
import com.pirtol.lab.service.criteria.UsageDossierCriteria;
import com.pirtol.lab.service.dto.UsageDossierDTO;
import com.pirtol.lab.service.mapper.UsageDossierMapper;
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
 * Integration tests for the {@link UsageDossierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UsageDossierResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/usage-dossiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UsageDossierRepository usageDossierRepository;

    @Autowired
    private UsageDossierMapper usageDossierMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsageDossierMockMvc;

    private UsageDossier usageDossier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsageDossier createEntity(EntityManager em) {
        UsageDossier usageDossier = new UsageDossier().code(DEFAULT_CODE).libelle(DEFAULT_LIBELLE);
        return usageDossier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsageDossier createUpdatedEntity(EntityManager em) {
        UsageDossier usageDossier = new UsageDossier().code(UPDATED_CODE).libelle(UPDATED_LIBELLE);
        return usageDossier;
    }

    @BeforeEach
    public void initTest() {
        usageDossier = createEntity(em);
    }

    @Test
    @Transactional
    void createUsageDossier() throws Exception {
        int databaseSizeBeforeCreate = usageDossierRepository.findAll().size();
        // Create the UsageDossier
        UsageDossierDTO usageDossierDTO = usageDossierMapper.toDto(usageDossier);
        restUsageDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usageDossierDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UsageDossier in the database
        List<UsageDossier> usageDossierList = usageDossierRepository.findAll();
        assertThat(usageDossierList).hasSize(databaseSizeBeforeCreate + 1);
        UsageDossier testUsageDossier = usageDossierList.get(usageDossierList.size() - 1);
        assertThat(testUsageDossier.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testUsageDossier.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void createUsageDossierWithExistingId() throws Exception {
        // Create the UsageDossier with an existing ID
        usageDossier.setId(1L);
        UsageDossierDTO usageDossierDTO = usageDossierMapper.toDto(usageDossier);

        int databaseSizeBeforeCreate = usageDossierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsageDossierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usageDossierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsageDossier in the database
        List<UsageDossier> usageDossierList = usageDossierRepository.findAll();
        assertThat(usageDossierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUsageDossiers() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        // Get all the usageDossierList
        restUsageDossierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usageDossier.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getUsageDossier() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        // Get the usageDossier
        restUsageDossierMockMvc
            .perform(get(ENTITY_API_URL_ID, usageDossier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usageDossier.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getUsageDossiersByIdFiltering() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        Long id = usageDossier.getId();

        defaultUsageDossierShouldBeFound("id.equals=" + id);
        defaultUsageDossierShouldNotBeFound("id.notEquals=" + id);

        defaultUsageDossierShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUsageDossierShouldNotBeFound("id.greaterThan=" + id);

        defaultUsageDossierShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUsageDossierShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUsageDossiersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        // Get all the usageDossierList where code equals to DEFAULT_CODE
        defaultUsageDossierShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the usageDossierList where code equals to UPDATED_CODE
        defaultUsageDossierShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllUsageDossiersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        // Get all the usageDossierList where code not equals to DEFAULT_CODE
        defaultUsageDossierShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the usageDossierList where code not equals to UPDATED_CODE
        defaultUsageDossierShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllUsageDossiersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        // Get all the usageDossierList where code in DEFAULT_CODE or UPDATED_CODE
        defaultUsageDossierShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the usageDossierList where code equals to UPDATED_CODE
        defaultUsageDossierShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllUsageDossiersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        // Get all the usageDossierList where code is not null
        defaultUsageDossierShouldBeFound("code.specified=true");

        // Get all the usageDossierList where code is null
        defaultUsageDossierShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllUsageDossiersByCodeContainsSomething() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        // Get all the usageDossierList where code contains DEFAULT_CODE
        defaultUsageDossierShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the usageDossierList where code contains UPDATED_CODE
        defaultUsageDossierShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllUsageDossiersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        // Get all the usageDossierList where code does not contain DEFAULT_CODE
        defaultUsageDossierShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the usageDossierList where code does not contain UPDATED_CODE
        defaultUsageDossierShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllUsageDossiersByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        // Get all the usageDossierList where libelle equals to DEFAULT_LIBELLE
        defaultUsageDossierShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the usageDossierList where libelle equals to UPDATED_LIBELLE
        defaultUsageDossierShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllUsageDossiersByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        // Get all the usageDossierList where libelle not equals to DEFAULT_LIBELLE
        defaultUsageDossierShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the usageDossierList where libelle not equals to UPDATED_LIBELLE
        defaultUsageDossierShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllUsageDossiersByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        // Get all the usageDossierList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultUsageDossierShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the usageDossierList where libelle equals to UPDATED_LIBELLE
        defaultUsageDossierShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllUsageDossiersByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        // Get all the usageDossierList where libelle is not null
        defaultUsageDossierShouldBeFound("libelle.specified=true");

        // Get all the usageDossierList where libelle is null
        defaultUsageDossierShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    void getAllUsageDossiersByLibelleContainsSomething() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        // Get all the usageDossierList where libelle contains DEFAULT_LIBELLE
        defaultUsageDossierShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the usageDossierList where libelle contains UPDATED_LIBELLE
        defaultUsageDossierShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllUsageDossiersByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        // Get all the usageDossierList where libelle does not contain DEFAULT_LIBELLE
        defaultUsageDossierShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the usageDossierList where libelle does not contain UPDATED_LIBELLE
        defaultUsageDossierShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllUsageDossiersByDossierIsEqualToSomething() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);
        Dossier dossier = DossierResourceIT.createEntity(em);
        em.persist(dossier);
        em.flush();
        usageDossier.addDossier(dossier);
        usageDossierRepository.saveAndFlush(usageDossier);
        Long dossierId = dossier.getId();

        // Get all the usageDossierList where dossier equals to dossierId
        defaultUsageDossierShouldBeFound("dossierId.equals=" + dossierId);

        // Get all the usageDossierList where dossier equals to (dossierId + 1)
        defaultUsageDossierShouldNotBeFound("dossierId.equals=" + (dossierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUsageDossierShouldBeFound(String filter) throws Exception {
        restUsageDossierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usageDossier.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restUsageDossierMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUsageDossierShouldNotBeFound(String filter) throws Exception {
        restUsageDossierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsageDossierMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUsageDossier() throws Exception {
        // Get the usageDossier
        restUsageDossierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUsageDossier() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        int databaseSizeBeforeUpdate = usageDossierRepository.findAll().size();

        // Update the usageDossier
        UsageDossier updatedUsageDossier = usageDossierRepository.findById(usageDossier.getId()).get();
        // Disconnect from session so that the updates on updatedUsageDossier are not directly saved in db
        em.detach(updatedUsageDossier);
        updatedUsageDossier.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);
        UsageDossierDTO usageDossierDTO = usageDossierMapper.toDto(updatedUsageDossier);

        restUsageDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usageDossierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usageDossierDTO))
            )
            .andExpect(status().isOk());

        // Validate the UsageDossier in the database
        List<UsageDossier> usageDossierList = usageDossierRepository.findAll();
        assertThat(usageDossierList).hasSize(databaseSizeBeforeUpdate);
        UsageDossier testUsageDossier = usageDossierList.get(usageDossierList.size() - 1);
        assertThat(testUsageDossier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUsageDossier.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void putNonExistingUsageDossier() throws Exception {
        int databaseSizeBeforeUpdate = usageDossierRepository.findAll().size();
        usageDossier.setId(count.incrementAndGet());

        // Create the UsageDossier
        UsageDossierDTO usageDossierDTO = usageDossierMapper.toDto(usageDossier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsageDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usageDossierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usageDossierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsageDossier in the database
        List<UsageDossier> usageDossierList = usageDossierRepository.findAll();
        assertThat(usageDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUsageDossier() throws Exception {
        int databaseSizeBeforeUpdate = usageDossierRepository.findAll().size();
        usageDossier.setId(count.incrementAndGet());

        // Create the UsageDossier
        UsageDossierDTO usageDossierDTO = usageDossierMapper.toDto(usageDossier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsageDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usageDossierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsageDossier in the database
        List<UsageDossier> usageDossierList = usageDossierRepository.findAll();
        assertThat(usageDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUsageDossier() throws Exception {
        int databaseSizeBeforeUpdate = usageDossierRepository.findAll().size();
        usageDossier.setId(count.incrementAndGet());

        // Create the UsageDossier
        UsageDossierDTO usageDossierDTO = usageDossierMapper.toDto(usageDossier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsageDossierMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usageDossierDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsageDossier in the database
        List<UsageDossier> usageDossierList = usageDossierRepository.findAll();
        assertThat(usageDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUsageDossierWithPatch() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        int databaseSizeBeforeUpdate = usageDossierRepository.findAll().size();

        // Update the usageDossier using partial update
        UsageDossier partialUpdatedUsageDossier = new UsageDossier();
        partialUpdatedUsageDossier.setId(usageDossier.getId());

        partialUpdatedUsageDossier.code(UPDATED_CODE);

        restUsageDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsageDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsageDossier))
            )
            .andExpect(status().isOk());

        // Validate the UsageDossier in the database
        List<UsageDossier> usageDossierList = usageDossierRepository.findAll();
        assertThat(usageDossierList).hasSize(databaseSizeBeforeUpdate);
        UsageDossier testUsageDossier = usageDossierList.get(usageDossierList.size() - 1);
        assertThat(testUsageDossier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUsageDossier.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void fullUpdateUsageDossierWithPatch() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        int databaseSizeBeforeUpdate = usageDossierRepository.findAll().size();

        // Update the usageDossier using partial update
        UsageDossier partialUpdatedUsageDossier = new UsageDossier();
        partialUpdatedUsageDossier.setId(usageDossier.getId());

        partialUpdatedUsageDossier.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);

        restUsageDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsageDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsageDossier))
            )
            .andExpect(status().isOk());

        // Validate the UsageDossier in the database
        List<UsageDossier> usageDossierList = usageDossierRepository.findAll();
        assertThat(usageDossierList).hasSize(databaseSizeBeforeUpdate);
        UsageDossier testUsageDossier = usageDossierList.get(usageDossierList.size() - 1);
        assertThat(testUsageDossier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUsageDossier.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void patchNonExistingUsageDossier() throws Exception {
        int databaseSizeBeforeUpdate = usageDossierRepository.findAll().size();
        usageDossier.setId(count.incrementAndGet());

        // Create the UsageDossier
        UsageDossierDTO usageDossierDTO = usageDossierMapper.toDto(usageDossier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsageDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usageDossierDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usageDossierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsageDossier in the database
        List<UsageDossier> usageDossierList = usageDossierRepository.findAll();
        assertThat(usageDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUsageDossier() throws Exception {
        int databaseSizeBeforeUpdate = usageDossierRepository.findAll().size();
        usageDossier.setId(count.incrementAndGet());

        // Create the UsageDossier
        UsageDossierDTO usageDossierDTO = usageDossierMapper.toDto(usageDossier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsageDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usageDossierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsageDossier in the database
        List<UsageDossier> usageDossierList = usageDossierRepository.findAll();
        assertThat(usageDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUsageDossier() throws Exception {
        int databaseSizeBeforeUpdate = usageDossierRepository.findAll().size();
        usageDossier.setId(count.incrementAndGet());

        // Create the UsageDossier
        UsageDossierDTO usageDossierDTO = usageDossierMapper.toDto(usageDossier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsageDossierMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usageDossierDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsageDossier in the database
        List<UsageDossier> usageDossierList = usageDossierRepository.findAll();
        assertThat(usageDossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUsageDossier() throws Exception {
        // Initialize the database
        usageDossierRepository.saveAndFlush(usageDossier);

        int databaseSizeBeforeDelete = usageDossierRepository.findAll().size();

        // Delete the usageDossier
        restUsageDossierMockMvc
            .perform(delete(ENTITY_API_URL_ID, usageDossier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsageDossier> usageDossierList = usageDossierRepository.findAll();
        assertThat(usageDossierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
