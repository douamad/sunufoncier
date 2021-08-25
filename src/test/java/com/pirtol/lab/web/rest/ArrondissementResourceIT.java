package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.Arrondissement;
import com.pirtol.lab.domain.Commune;
import com.pirtol.lab.domain.Departement;
import com.pirtol.lab.repository.ArrondissementRepository;
import com.pirtol.lab.service.criteria.ArrondissementCriteria;
import com.pirtol.lab.service.dto.ArrondissementDTO;
import com.pirtol.lab.service.mapper.ArrondissementMapper;
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
 * Integration tests for the {@link ArrondissementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArrondissementResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/arrondissements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArrondissementRepository arrondissementRepository;

    @Autowired
    private ArrondissementMapper arrondissementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArrondissementMockMvc;

    private Arrondissement arrondissement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arrondissement createEntity(EntityManager em) {
        Arrondissement arrondissement = new Arrondissement().code(DEFAULT_CODE).libelle(DEFAULT_LIBELLE);
        return arrondissement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arrondissement createUpdatedEntity(EntityManager em) {
        Arrondissement arrondissement = new Arrondissement().code(UPDATED_CODE).libelle(UPDATED_LIBELLE);
        return arrondissement;
    }

    @BeforeEach
    public void initTest() {
        arrondissement = createEntity(em);
    }

    @Test
    @Transactional
    void createArrondissement() throws Exception {
        int databaseSizeBeforeCreate = arrondissementRepository.findAll().size();
        // Create the Arrondissement
        ArrondissementDTO arrondissementDTO = arrondissementMapper.toDto(arrondissement);
        restArrondissementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arrondissementDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeCreate + 1);
        Arrondissement testArrondissement = arrondissementList.get(arrondissementList.size() - 1);
        assertThat(testArrondissement.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testArrondissement.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void createArrondissementWithExistingId() throws Exception {
        // Create the Arrondissement with an existing ID
        arrondissement.setId(1L);
        ArrondissementDTO arrondissementDTO = arrondissementMapper.toDto(arrondissement);

        int databaseSizeBeforeCreate = arrondissementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArrondissementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arrondissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArrondissements() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get all the arrondissementList
        restArrondissementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arrondissement.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getArrondissement() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get the arrondissement
        restArrondissementMockMvc
            .perform(get(ENTITY_API_URL_ID, arrondissement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(arrondissement.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getArrondissementsByIdFiltering() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        Long id = arrondissement.getId();

        defaultArrondissementShouldBeFound("id.equals=" + id);
        defaultArrondissementShouldNotBeFound("id.notEquals=" + id);

        defaultArrondissementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultArrondissementShouldNotBeFound("id.greaterThan=" + id);

        defaultArrondissementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultArrondissementShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllArrondissementsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get all the arrondissementList where code equals to DEFAULT_CODE
        defaultArrondissementShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the arrondissementList where code equals to UPDATED_CODE
        defaultArrondissementShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllArrondissementsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get all the arrondissementList where code not equals to DEFAULT_CODE
        defaultArrondissementShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the arrondissementList where code not equals to UPDATED_CODE
        defaultArrondissementShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllArrondissementsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get all the arrondissementList where code in DEFAULT_CODE or UPDATED_CODE
        defaultArrondissementShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the arrondissementList where code equals to UPDATED_CODE
        defaultArrondissementShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllArrondissementsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get all the arrondissementList where code is not null
        defaultArrondissementShouldBeFound("code.specified=true");

        // Get all the arrondissementList where code is null
        defaultArrondissementShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllArrondissementsByCodeContainsSomething() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get all the arrondissementList where code contains DEFAULT_CODE
        defaultArrondissementShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the arrondissementList where code contains UPDATED_CODE
        defaultArrondissementShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllArrondissementsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get all the arrondissementList where code does not contain DEFAULT_CODE
        defaultArrondissementShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the arrondissementList where code does not contain UPDATED_CODE
        defaultArrondissementShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllArrondissementsByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get all the arrondissementList where libelle equals to DEFAULT_LIBELLE
        defaultArrondissementShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the arrondissementList where libelle equals to UPDATED_LIBELLE
        defaultArrondissementShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllArrondissementsByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get all the arrondissementList where libelle not equals to DEFAULT_LIBELLE
        defaultArrondissementShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the arrondissementList where libelle not equals to UPDATED_LIBELLE
        defaultArrondissementShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllArrondissementsByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get all the arrondissementList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultArrondissementShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the arrondissementList where libelle equals to UPDATED_LIBELLE
        defaultArrondissementShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllArrondissementsByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get all the arrondissementList where libelle is not null
        defaultArrondissementShouldBeFound("libelle.specified=true");

        // Get all the arrondissementList where libelle is null
        defaultArrondissementShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    void getAllArrondissementsByLibelleContainsSomething() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get all the arrondissementList where libelle contains DEFAULT_LIBELLE
        defaultArrondissementShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the arrondissementList where libelle contains UPDATED_LIBELLE
        defaultArrondissementShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllArrondissementsByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        // Get all the arrondissementList where libelle does not contain DEFAULT_LIBELLE
        defaultArrondissementShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the arrondissementList where libelle does not contain UPDATED_LIBELLE
        defaultArrondissementShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllArrondissementsByCommuneIsEqualToSomething() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);
        Commune commune = CommuneResourceIT.createEntity(em);
        em.persist(commune);
        em.flush();
        arrondissement.addCommune(commune);
        arrondissementRepository.saveAndFlush(arrondissement);
        Long communeId = commune.getId();

        // Get all the arrondissementList where commune equals to communeId
        defaultArrondissementShouldBeFound("communeId.equals=" + communeId);

        // Get all the arrondissementList where commune equals to (communeId + 1)
        defaultArrondissementShouldNotBeFound("communeId.equals=" + (communeId + 1));
    }

    @Test
    @Transactional
    void getAllArrondissementsByDepartementIsEqualToSomething() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);
        Departement departement = DepartementResourceIT.createEntity(em);
        em.persist(departement);
        em.flush();
        arrondissement.setDepartement(departement);
        arrondissementRepository.saveAndFlush(arrondissement);
        Long departementId = departement.getId();

        // Get all the arrondissementList where departement equals to departementId
        defaultArrondissementShouldBeFound("departementId.equals=" + departementId);

        // Get all the arrondissementList where departement equals to (departementId + 1)
        defaultArrondissementShouldNotBeFound("departementId.equals=" + (departementId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultArrondissementShouldBeFound(String filter) throws Exception {
        restArrondissementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arrondissement.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restArrondissementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultArrondissementShouldNotBeFound(String filter) throws Exception {
        restArrondissementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restArrondissementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingArrondissement() throws Exception {
        // Get the arrondissement
        restArrondissementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewArrondissement() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();

        // Update the arrondissement
        Arrondissement updatedArrondissement = arrondissementRepository.findById(arrondissement.getId()).get();
        // Disconnect from session so that the updates on updatedArrondissement are not directly saved in db
        em.detach(updatedArrondissement);
        updatedArrondissement.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);
        ArrondissementDTO arrondissementDTO = arrondissementMapper.toDto(updatedArrondissement);

        restArrondissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, arrondissementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arrondissementDTO))
            )
            .andExpect(status().isOk());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);
        Arrondissement testArrondissement = arrondissementList.get(arrondissementList.size() - 1);
        assertThat(testArrondissement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testArrondissement.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void putNonExistingArrondissement() throws Exception {
        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();
        arrondissement.setId(count.incrementAndGet());

        // Create the Arrondissement
        ArrondissementDTO arrondissementDTO = arrondissementMapper.toDto(arrondissement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArrondissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, arrondissementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arrondissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArrondissement() throws Exception {
        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();
        arrondissement.setId(count.incrementAndGet());

        // Create the Arrondissement
        ArrondissementDTO arrondissementDTO = arrondissementMapper.toDto(arrondissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArrondissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arrondissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArrondissement() throws Exception {
        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();
        arrondissement.setId(count.incrementAndGet());

        // Create the Arrondissement
        ArrondissementDTO arrondissementDTO = arrondissementMapper.toDto(arrondissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArrondissementMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arrondissementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArrondissementWithPatch() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();

        // Update the arrondissement using partial update
        Arrondissement partialUpdatedArrondissement = new Arrondissement();
        partialUpdatedArrondissement.setId(arrondissement.getId());

        partialUpdatedArrondissement.libelle(UPDATED_LIBELLE);

        restArrondissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArrondissement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArrondissement))
            )
            .andExpect(status().isOk());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);
        Arrondissement testArrondissement = arrondissementList.get(arrondissementList.size() - 1);
        assertThat(testArrondissement.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testArrondissement.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void fullUpdateArrondissementWithPatch() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();

        // Update the arrondissement using partial update
        Arrondissement partialUpdatedArrondissement = new Arrondissement();
        partialUpdatedArrondissement.setId(arrondissement.getId());

        partialUpdatedArrondissement.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);

        restArrondissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArrondissement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArrondissement))
            )
            .andExpect(status().isOk());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);
        Arrondissement testArrondissement = arrondissementList.get(arrondissementList.size() - 1);
        assertThat(testArrondissement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testArrondissement.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void patchNonExistingArrondissement() throws Exception {
        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();
        arrondissement.setId(count.incrementAndGet());

        // Create the Arrondissement
        ArrondissementDTO arrondissementDTO = arrondissementMapper.toDto(arrondissement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArrondissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, arrondissementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arrondissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArrondissement() throws Exception {
        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();
        arrondissement.setId(count.incrementAndGet());

        // Create the Arrondissement
        ArrondissementDTO arrondissementDTO = arrondissementMapper.toDto(arrondissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArrondissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arrondissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArrondissement() throws Exception {
        int databaseSizeBeforeUpdate = arrondissementRepository.findAll().size();
        arrondissement.setId(count.incrementAndGet());

        // Create the Arrondissement
        ArrondissementDTO arrondissementDTO = arrondissementMapper.toDto(arrondissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArrondissementMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arrondissementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Arrondissement in the database
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArrondissement() throws Exception {
        // Initialize the database
        arrondissementRepository.saveAndFlush(arrondissement);

        int databaseSizeBeforeDelete = arrondissementRepository.findAll().size();

        // Delete the arrondissement
        restArrondissementMockMvc
            .perform(delete(ENTITY_API_URL_ID, arrondissement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Arrondissement> arrondissementList = arrondissementRepository.findAll();
        assertThat(arrondissementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
