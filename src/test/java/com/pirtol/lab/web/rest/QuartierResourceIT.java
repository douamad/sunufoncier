package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.Commune;
import com.pirtol.lab.domain.Lotissement;
import com.pirtol.lab.domain.Quartier;
import com.pirtol.lab.repository.QuartierRepository;
import com.pirtol.lab.service.criteria.QuartierCriteria;
import com.pirtol.lab.service.dto.QuartierDTO;
import com.pirtol.lab.service.mapper.QuartierMapper;
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
 * Integration tests for the {@link QuartierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuartierResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/quartiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuartierRepository quartierRepository;

    @Autowired
    private QuartierMapper quartierMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuartierMockMvc;

    private Quartier quartier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quartier createEntity(EntityManager em) {
        Quartier quartier = new Quartier().code(DEFAULT_CODE).libelle(DEFAULT_LIBELLE);
        return quartier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quartier createUpdatedEntity(EntityManager em) {
        Quartier quartier = new Quartier().code(UPDATED_CODE).libelle(UPDATED_LIBELLE);
        return quartier;
    }

    @BeforeEach
    public void initTest() {
        quartier = createEntity(em);
    }

    @Test
    @Transactional
    void createQuartier() throws Exception {
        int databaseSizeBeforeCreate = quartierRepository.findAll().size();
        // Create the Quartier
        QuartierDTO quartierDTO = quartierMapper.toDto(quartier);
        restQuartierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quartierDTO)))
            .andExpect(status().isCreated());

        // Validate the Quartier in the database
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeCreate + 1);
        Quartier testQuartier = quartierList.get(quartierList.size() - 1);
        assertThat(testQuartier.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testQuartier.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void createQuartierWithExistingId() throws Exception {
        // Create the Quartier with an existing ID
        quartier.setId(1L);
        QuartierDTO quartierDTO = quartierMapper.toDto(quartier);

        int databaseSizeBeforeCreate = quartierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuartierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quartierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Quartier in the database
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQuartiers() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get all the quartierList
        restQuartierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quartier.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getQuartier() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get the quartier
        restQuartierMockMvc
            .perform(get(ENTITY_API_URL_ID, quartier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quartier.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getQuartiersByIdFiltering() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        Long id = quartier.getId();

        defaultQuartierShouldBeFound("id.equals=" + id);
        defaultQuartierShouldNotBeFound("id.notEquals=" + id);

        defaultQuartierShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuartierShouldNotBeFound("id.greaterThan=" + id);

        defaultQuartierShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuartierShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllQuartiersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get all the quartierList where code equals to DEFAULT_CODE
        defaultQuartierShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the quartierList where code equals to UPDATED_CODE
        defaultQuartierShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllQuartiersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get all the quartierList where code not equals to DEFAULT_CODE
        defaultQuartierShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the quartierList where code not equals to UPDATED_CODE
        defaultQuartierShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllQuartiersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get all the quartierList where code in DEFAULT_CODE or UPDATED_CODE
        defaultQuartierShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the quartierList where code equals to UPDATED_CODE
        defaultQuartierShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllQuartiersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get all the quartierList where code is not null
        defaultQuartierShouldBeFound("code.specified=true");

        // Get all the quartierList where code is null
        defaultQuartierShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllQuartiersByCodeContainsSomething() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get all the quartierList where code contains DEFAULT_CODE
        defaultQuartierShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the quartierList where code contains UPDATED_CODE
        defaultQuartierShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllQuartiersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get all the quartierList where code does not contain DEFAULT_CODE
        defaultQuartierShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the quartierList where code does not contain UPDATED_CODE
        defaultQuartierShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllQuartiersByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get all the quartierList where libelle equals to DEFAULT_LIBELLE
        defaultQuartierShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the quartierList where libelle equals to UPDATED_LIBELLE
        defaultQuartierShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllQuartiersByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get all the quartierList where libelle not equals to DEFAULT_LIBELLE
        defaultQuartierShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the quartierList where libelle not equals to UPDATED_LIBELLE
        defaultQuartierShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllQuartiersByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get all the quartierList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultQuartierShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the quartierList where libelle equals to UPDATED_LIBELLE
        defaultQuartierShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllQuartiersByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get all the quartierList where libelle is not null
        defaultQuartierShouldBeFound("libelle.specified=true");

        // Get all the quartierList where libelle is null
        defaultQuartierShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    void getAllQuartiersByLibelleContainsSomething() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get all the quartierList where libelle contains DEFAULT_LIBELLE
        defaultQuartierShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the quartierList where libelle contains UPDATED_LIBELLE
        defaultQuartierShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllQuartiersByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        // Get all the quartierList where libelle does not contain DEFAULT_LIBELLE
        defaultQuartierShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the quartierList where libelle does not contain UPDATED_LIBELLE
        defaultQuartierShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllQuartiersByLotissementIsEqualToSomething() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);
        Lotissement lotissement = LotissementResourceIT.createEntity(em);
        em.persist(lotissement);
        em.flush();
        quartier.addLotissement(lotissement);
        quartierRepository.saveAndFlush(quartier);
        Long lotissementId = lotissement.getId();

        // Get all the quartierList where lotissement equals to lotissementId
        defaultQuartierShouldBeFound("lotissementId.equals=" + lotissementId);

        // Get all the quartierList where lotissement equals to (lotissementId + 1)
        defaultQuartierShouldNotBeFound("lotissementId.equals=" + (lotissementId + 1));
    }

    @Test
    @Transactional
    void getAllQuartiersByCommununeIsEqualToSomething() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);
        Commune communune = CommuneResourceIT.createEntity(em);
        em.persist(communune);
        em.flush();
        quartier.setCommunune(communune);
        quartierRepository.saveAndFlush(quartier);
        Long commununeId = communune.getId();

        // Get all the quartierList where communune equals to commununeId
        defaultQuartierShouldBeFound("commununeId.equals=" + commununeId);

        // Get all the quartierList where communune equals to (commununeId + 1)
        defaultQuartierShouldNotBeFound("commununeId.equals=" + (commununeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuartierShouldBeFound(String filter) throws Exception {
        restQuartierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quartier.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restQuartierMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuartierShouldNotBeFound(String filter) throws Exception {
        restQuartierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuartierMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingQuartier() throws Exception {
        // Get the quartier
        restQuartierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQuartier() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        int databaseSizeBeforeUpdate = quartierRepository.findAll().size();

        // Update the quartier
        Quartier updatedQuartier = quartierRepository.findById(quartier.getId()).get();
        // Disconnect from session so that the updates on updatedQuartier are not directly saved in db
        em.detach(updatedQuartier);
        updatedQuartier.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);
        QuartierDTO quartierDTO = quartierMapper.toDto(updatedQuartier);

        restQuartierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quartierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quartierDTO))
            )
            .andExpect(status().isOk());

        // Validate the Quartier in the database
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeUpdate);
        Quartier testQuartier = quartierList.get(quartierList.size() - 1);
        assertThat(testQuartier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testQuartier.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void putNonExistingQuartier() throws Exception {
        int databaseSizeBeforeUpdate = quartierRepository.findAll().size();
        quartier.setId(count.incrementAndGet());

        // Create the Quartier
        QuartierDTO quartierDTO = quartierMapper.toDto(quartier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuartierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quartierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quartierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quartier in the database
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuartier() throws Exception {
        int databaseSizeBeforeUpdate = quartierRepository.findAll().size();
        quartier.setId(count.incrementAndGet());

        // Create the Quartier
        QuartierDTO quartierDTO = quartierMapper.toDto(quartier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuartierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quartierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quartier in the database
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuartier() throws Exception {
        int databaseSizeBeforeUpdate = quartierRepository.findAll().size();
        quartier.setId(count.incrementAndGet());

        // Create the Quartier
        QuartierDTO quartierDTO = quartierMapper.toDto(quartier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuartierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quartierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quartier in the database
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuartierWithPatch() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        int databaseSizeBeforeUpdate = quartierRepository.findAll().size();

        // Update the quartier using partial update
        Quartier partialUpdatedQuartier = new Quartier();
        partialUpdatedQuartier.setId(quartier.getId());

        partialUpdatedQuartier.code(UPDATED_CODE);

        restQuartierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuartier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuartier))
            )
            .andExpect(status().isOk());

        // Validate the Quartier in the database
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeUpdate);
        Quartier testQuartier = quartierList.get(quartierList.size() - 1);
        assertThat(testQuartier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testQuartier.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void fullUpdateQuartierWithPatch() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        int databaseSizeBeforeUpdate = quartierRepository.findAll().size();

        // Update the quartier using partial update
        Quartier partialUpdatedQuartier = new Quartier();
        partialUpdatedQuartier.setId(quartier.getId());

        partialUpdatedQuartier.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);

        restQuartierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuartier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuartier))
            )
            .andExpect(status().isOk());

        // Validate the Quartier in the database
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeUpdate);
        Quartier testQuartier = quartierList.get(quartierList.size() - 1);
        assertThat(testQuartier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testQuartier.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void patchNonExistingQuartier() throws Exception {
        int databaseSizeBeforeUpdate = quartierRepository.findAll().size();
        quartier.setId(count.incrementAndGet());

        // Create the Quartier
        QuartierDTO quartierDTO = quartierMapper.toDto(quartier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuartierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quartierDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quartierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quartier in the database
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuartier() throws Exception {
        int databaseSizeBeforeUpdate = quartierRepository.findAll().size();
        quartier.setId(count.incrementAndGet());

        // Create the Quartier
        QuartierDTO quartierDTO = quartierMapper.toDto(quartier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuartierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quartierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quartier in the database
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuartier() throws Exception {
        int databaseSizeBeforeUpdate = quartierRepository.findAll().size();
        quartier.setId(count.incrementAndGet());

        // Create the Quartier
        QuartierDTO quartierDTO = quartierMapper.toDto(quartier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuartierMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(quartierDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quartier in the database
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuartier() throws Exception {
        // Initialize the database
        quartierRepository.saveAndFlush(quartier);

        int databaseSizeBeforeDelete = quartierRepository.findAll().size();

        // Delete the quartier
        restQuartierMockMvc
            .perform(delete(ENTITY_API_URL_ID, quartier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Quartier> quartierList = quartierRepository.findAll();
        assertThat(quartierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
