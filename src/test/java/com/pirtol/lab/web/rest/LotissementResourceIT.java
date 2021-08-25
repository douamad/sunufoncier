package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.Dossier;
import com.pirtol.lab.domain.Lotissement;
import com.pirtol.lab.domain.Quartier;
import com.pirtol.lab.repository.LotissementRepository;
import com.pirtol.lab.service.criteria.LotissementCriteria;
import com.pirtol.lab.service.dto.LotissementDTO;
import com.pirtol.lab.service.mapper.LotissementMapper;
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
 * Integration tests for the {@link LotissementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LotissementResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lotissements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LotissementRepository lotissementRepository;

    @Autowired
    private LotissementMapper lotissementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLotissementMockMvc;

    private Lotissement lotissement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lotissement createEntity(EntityManager em) {
        Lotissement lotissement = new Lotissement().code(DEFAULT_CODE).libelle(DEFAULT_LIBELLE);
        return lotissement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lotissement createUpdatedEntity(EntityManager em) {
        Lotissement lotissement = new Lotissement().code(UPDATED_CODE).libelle(UPDATED_LIBELLE);
        return lotissement;
    }

    @BeforeEach
    public void initTest() {
        lotissement = createEntity(em);
    }

    @Test
    @Transactional
    void createLotissement() throws Exception {
        int databaseSizeBeforeCreate = lotissementRepository.findAll().size();
        // Create the Lotissement
        LotissementDTO lotissementDTO = lotissementMapper.toDto(lotissement);
        restLotissementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lotissementDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Lotissement in the database
        List<Lotissement> lotissementList = lotissementRepository.findAll();
        assertThat(lotissementList).hasSize(databaseSizeBeforeCreate + 1);
        Lotissement testLotissement = lotissementList.get(lotissementList.size() - 1);
        assertThat(testLotissement.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLotissement.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void createLotissementWithExistingId() throws Exception {
        // Create the Lotissement with an existing ID
        lotissement.setId(1L);
        LotissementDTO lotissementDTO = lotissementMapper.toDto(lotissement);

        int databaseSizeBeforeCreate = lotissementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLotissementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lotissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lotissement in the database
        List<Lotissement> lotissementList = lotissementRepository.findAll();
        assertThat(lotissementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLotissements() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        // Get all the lotissementList
        restLotissementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lotissement.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getLotissement() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        // Get the lotissement
        restLotissementMockMvc
            .perform(get(ENTITY_API_URL_ID, lotissement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lotissement.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getLotissementsByIdFiltering() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        Long id = lotissement.getId();

        defaultLotissementShouldBeFound("id.equals=" + id);
        defaultLotissementShouldNotBeFound("id.notEquals=" + id);

        defaultLotissementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLotissementShouldNotBeFound("id.greaterThan=" + id);

        defaultLotissementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLotissementShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLotissementsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        // Get all the lotissementList where code equals to DEFAULT_CODE
        defaultLotissementShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the lotissementList where code equals to UPDATED_CODE
        defaultLotissementShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllLotissementsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        // Get all the lotissementList where code not equals to DEFAULT_CODE
        defaultLotissementShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the lotissementList where code not equals to UPDATED_CODE
        defaultLotissementShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllLotissementsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        // Get all the lotissementList where code in DEFAULT_CODE or UPDATED_CODE
        defaultLotissementShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the lotissementList where code equals to UPDATED_CODE
        defaultLotissementShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllLotissementsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        // Get all the lotissementList where code is not null
        defaultLotissementShouldBeFound("code.specified=true");

        // Get all the lotissementList where code is null
        defaultLotissementShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllLotissementsByCodeContainsSomething() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        // Get all the lotissementList where code contains DEFAULT_CODE
        defaultLotissementShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the lotissementList where code contains UPDATED_CODE
        defaultLotissementShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllLotissementsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        // Get all the lotissementList where code does not contain DEFAULT_CODE
        defaultLotissementShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the lotissementList where code does not contain UPDATED_CODE
        defaultLotissementShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllLotissementsByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        // Get all the lotissementList where libelle equals to DEFAULT_LIBELLE
        defaultLotissementShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the lotissementList where libelle equals to UPDATED_LIBELLE
        defaultLotissementShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllLotissementsByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        // Get all the lotissementList where libelle not equals to DEFAULT_LIBELLE
        defaultLotissementShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the lotissementList where libelle not equals to UPDATED_LIBELLE
        defaultLotissementShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllLotissementsByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        // Get all the lotissementList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultLotissementShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the lotissementList where libelle equals to UPDATED_LIBELLE
        defaultLotissementShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllLotissementsByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        // Get all the lotissementList where libelle is not null
        defaultLotissementShouldBeFound("libelle.specified=true");

        // Get all the lotissementList where libelle is null
        defaultLotissementShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    void getAllLotissementsByLibelleContainsSomething() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        // Get all the lotissementList where libelle contains DEFAULT_LIBELLE
        defaultLotissementShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the lotissementList where libelle contains UPDATED_LIBELLE
        defaultLotissementShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllLotissementsByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        // Get all the lotissementList where libelle does not contain DEFAULT_LIBELLE
        defaultLotissementShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the lotissementList where libelle does not contain UPDATED_LIBELLE
        defaultLotissementShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllLotissementsByLotissementIsEqualToSomething() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);
        Dossier lotissement = DossierResourceIT.createEntity(em);
        em.persist(lotissement);
        em.flush();
        lotissement.addLotissement(lotissement);
        lotissementRepository.saveAndFlush(lotissement);
        Long lotissementId = lotissement.getId();

        // Get all the lotissementList where lotissement equals to lotissementId
        defaultLotissementShouldBeFound("lotissementId.equals=" + lotissementId);

        // Get all the lotissementList where lotissement equals to (lotissementId + 1)
        defaultLotissementShouldNotBeFound("lotissementId.equals=" + (lotissementId + 1));
    }

    @Test
    @Transactional
    void getAllLotissementsByQuartierIsEqualToSomething() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);
        Quartier quartier = QuartierResourceIT.createEntity(em);
        em.persist(quartier);
        em.flush();
        lotissement.setQuartier(quartier);
        lotissementRepository.saveAndFlush(lotissement);
        Long quartierId = quartier.getId();

        // Get all the lotissementList where quartier equals to quartierId
        defaultLotissementShouldBeFound("quartierId.equals=" + quartierId);

        // Get all the lotissementList where quartier equals to (quartierId + 1)
        defaultLotissementShouldNotBeFound("quartierId.equals=" + (quartierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLotissementShouldBeFound(String filter) throws Exception {
        restLotissementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lotissement.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restLotissementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLotissementShouldNotBeFound(String filter) throws Exception {
        restLotissementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLotissementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLotissement() throws Exception {
        // Get the lotissement
        restLotissementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLotissement() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        int databaseSizeBeforeUpdate = lotissementRepository.findAll().size();

        // Update the lotissement
        Lotissement updatedLotissement = lotissementRepository.findById(lotissement.getId()).get();
        // Disconnect from session so that the updates on updatedLotissement are not directly saved in db
        em.detach(updatedLotissement);
        updatedLotissement.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);
        LotissementDTO lotissementDTO = lotissementMapper.toDto(updatedLotissement);

        restLotissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lotissementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lotissementDTO))
            )
            .andExpect(status().isOk());

        // Validate the Lotissement in the database
        List<Lotissement> lotissementList = lotissementRepository.findAll();
        assertThat(lotissementList).hasSize(databaseSizeBeforeUpdate);
        Lotissement testLotissement = lotissementList.get(lotissementList.size() - 1);
        assertThat(testLotissement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLotissement.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void putNonExistingLotissement() throws Exception {
        int databaseSizeBeforeUpdate = lotissementRepository.findAll().size();
        lotissement.setId(count.incrementAndGet());

        // Create the Lotissement
        LotissementDTO lotissementDTO = lotissementMapper.toDto(lotissement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLotissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lotissementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lotissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lotissement in the database
        List<Lotissement> lotissementList = lotissementRepository.findAll();
        assertThat(lotissementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLotissement() throws Exception {
        int databaseSizeBeforeUpdate = lotissementRepository.findAll().size();
        lotissement.setId(count.incrementAndGet());

        // Create the Lotissement
        LotissementDTO lotissementDTO = lotissementMapper.toDto(lotissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLotissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lotissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lotissement in the database
        List<Lotissement> lotissementList = lotissementRepository.findAll();
        assertThat(lotissementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLotissement() throws Exception {
        int databaseSizeBeforeUpdate = lotissementRepository.findAll().size();
        lotissement.setId(count.incrementAndGet());

        // Create the Lotissement
        LotissementDTO lotissementDTO = lotissementMapper.toDto(lotissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLotissementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lotissementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lotissement in the database
        List<Lotissement> lotissementList = lotissementRepository.findAll();
        assertThat(lotissementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLotissementWithPatch() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        int databaseSizeBeforeUpdate = lotissementRepository.findAll().size();

        // Update the lotissement using partial update
        Lotissement partialUpdatedLotissement = new Lotissement();
        partialUpdatedLotissement.setId(lotissement.getId());

        partialUpdatedLotissement.libelle(UPDATED_LIBELLE);

        restLotissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLotissement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLotissement))
            )
            .andExpect(status().isOk());

        // Validate the Lotissement in the database
        List<Lotissement> lotissementList = lotissementRepository.findAll();
        assertThat(lotissementList).hasSize(databaseSizeBeforeUpdate);
        Lotissement testLotissement = lotissementList.get(lotissementList.size() - 1);
        assertThat(testLotissement.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLotissement.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void fullUpdateLotissementWithPatch() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        int databaseSizeBeforeUpdate = lotissementRepository.findAll().size();

        // Update the lotissement using partial update
        Lotissement partialUpdatedLotissement = new Lotissement();
        partialUpdatedLotissement.setId(lotissement.getId());

        partialUpdatedLotissement.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);

        restLotissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLotissement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLotissement))
            )
            .andExpect(status().isOk());

        // Validate the Lotissement in the database
        List<Lotissement> lotissementList = lotissementRepository.findAll();
        assertThat(lotissementList).hasSize(databaseSizeBeforeUpdate);
        Lotissement testLotissement = lotissementList.get(lotissementList.size() - 1);
        assertThat(testLotissement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLotissement.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void patchNonExistingLotissement() throws Exception {
        int databaseSizeBeforeUpdate = lotissementRepository.findAll().size();
        lotissement.setId(count.incrementAndGet());

        // Create the Lotissement
        LotissementDTO lotissementDTO = lotissementMapper.toDto(lotissement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLotissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lotissementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lotissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lotissement in the database
        List<Lotissement> lotissementList = lotissementRepository.findAll();
        assertThat(lotissementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLotissement() throws Exception {
        int databaseSizeBeforeUpdate = lotissementRepository.findAll().size();
        lotissement.setId(count.incrementAndGet());

        // Create the Lotissement
        LotissementDTO lotissementDTO = lotissementMapper.toDto(lotissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLotissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lotissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lotissement in the database
        List<Lotissement> lotissementList = lotissementRepository.findAll();
        assertThat(lotissementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLotissement() throws Exception {
        int databaseSizeBeforeUpdate = lotissementRepository.findAll().size();
        lotissement.setId(count.incrementAndGet());

        // Create the Lotissement
        LotissementDTO lotissementDTO = lotissementMapper.toDto(lotissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLotissementMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lotissementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lotissement in the database
        List<Lotissement> lotissementList = lotissementRepository.findAll();
        assertThat(lotissementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLotissement() throws Exception {
        // Initialize the database
        lotissementRepository.saveAndFlush(lotissement);

        int databaseSizeBeforeDelete = lotissementRepository.findAll().size();

        // Delete the lotissement
        restLotissementMockMvc
            .perform(delete(ENTITY_API_URL_ID, lotissement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lotissement> lotissementList = lotissementRepository.findAll();
        assertThat(lotissementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
