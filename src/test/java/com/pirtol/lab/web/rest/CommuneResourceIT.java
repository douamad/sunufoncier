package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.Arrondissement;
import com.pirtol.lab.domain.Commune;
import com.pirtol.lab.domain.Quartier;
import com.pirtol.lab.domain.RefParcelaire;
import com.pirtol.lab.repository.CommuneRepository;
import com.pirtol.lab.service.criteria.CommuneCriteria;
import com.pirtol.lab.service.dto.CommuneDTO;
import com.pirtol.lab.service.mapper.CommuneMapper;
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
 * Integration tests for the {@link CommuneResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommuneResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/communes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommuneRepository communeRepository;

    @Autowired
    private CommuneMapper communeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommuneMockMvc;

    private Commune commune;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commune createEntity(EntityManager em) {
        Commune commune = new Commune().code(DEFAULT_CODE).libelle(DEFAULT_LIBELLE);
        return commune;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commune createUpdatedEntity(EntityManager em) {
        Commune commune = new Commune().code(UPDATED_CODE).libelle(UPDATED_LIBELLE);
        return commune;
    }

    @BeforeEach
    public void initTest() {
        commune = createEntity(em);
    }

    @Test
    @Transactional
    void createCommune() throws Exception {
        int databaseSizeBeforeCreate = communeRepository.findAll().size();
        // Create the Commune
        CommuneDTO communeDTO = communeMapper.toDto(commune);
        restCommuneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(communeDTO)))
            .andExpect(status().isCreated());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeCreate + 1);
        Commune testCommune = communeList.get(communeList.size() - 1);
        assertThat(testCommune.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCommune.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void createCommuneWithExistingId() throws Exception {
        // Create the Commune with an existing ID
        commune.setId(1L);
        CommuneDTO communeDTO = communeMapper.toDto(commune);

        int databaseSizeBeforeCreate = communeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommuneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(communeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCommunes() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList
        restCommuneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commune.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get the commune
        restCommuneMockMvc
            .perform(get(ENTITY_API_URL_ID, commune.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commune.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getCommunesByIdFiltering() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        Long id = commune.getId();

        defaultCommuneShouldBeFound("id.equals=" + id);
        defaultCommuneShouldNotBeFound("id.notEquals=" + id);

        defaultCommuneShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommuneShouldNotBeFound("id.greaterThan=" + id);

        defaultCommuneShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommuneShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCommunesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList where code equals to DEFAULT_CODE
        defaultCommuneShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the communeList where code equals to UPDATED_CODE
        defaultCommuneShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCommunesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList where code not equals to DEFAULT_CODE
        defaultCommuneShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the communeList where code not equals to UPDATED_CODE
        defaultCommuneShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCommunesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCommuneShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the communeList where code equals to UPDATED_CODE
        defaultCommuneShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCommunesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList where code is not null
        defaultCommuneShouldBeFound("code.specified=true");

        // Get all the communeList where code is null
        defaultCommuneShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllCommunesByCodeContainsSomething() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList where code contains DEFAULT_CODE
        defaultCommuneShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the communeList where code contains UPDATED_CODE
        defaultCommuneShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCommunesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList where code does not contain DEFAULT_CODE
        defaultCommuneShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the communeList where code does not contain UPDATED_CODE
        defaultCommuneShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCommunesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList where libelle equals to DEFAULT_LIBELLE
        defaultCommuneShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the communeList where libelle equals to UPDATED_LIBELLE
        defaultCommuneShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCommunesByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList where libelle not equals to DEFAULT_LIBELLE
        defaultCommuneShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the communeList where libelle not equals to UPDATED_LIBELLE
        defaultCommuneShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCommunesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultCommuneShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the communeList where libelle equals to UPDATED_LIBELLE
        defaultCommuneShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCommunesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList where libelle is not null
        defaultCommuneShouldBeFound("libelle.specified=true");

        // Get all the communeList where libelle is null
        defaultCommuneShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    void getAllCommunesByLibelleContainsSomething() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList where libelle contains DEFAULT_LIBELLE
        defaultCommuneShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the communeList where libelle contains UPDATED_LIBELLE
        defaultCommuneShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCommunesByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList where libelle does not contain DEFAULT_LIBELLE
        defaultCommuneShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the communeList where libelle does not contain UPDATED_LIBELLE
        defaultCommuneShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllCommunesByQuartierIsEqualToSomething() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);
        Quartier quartier = QuartierResourceIT.createEntity(em);
        em.persist(quartier);
        em.flush();
        commune.addQuartier(quartier);
        communeRepository.saveAndFlush(commune);
        Long quartierId = quartier.getId();

        // Get all the communeList where quartier equals to quartierId
        defaultCommuneShouldBeFound("quartierId.equals=" + quartierId);

        // Get all the communeList where quartier equals to (quartierId + 1)
        defaultCommuneShouldNotBeFound("quartierId.equals=" + (quartierId + 1));
    }

    @Test
    @Transactional
    void getAllCommunesByArrondissementIsEqualToSomething() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);
        Arrondissement arrondissement = ArrondissementResourceIT.createEntity(em);
        em.persist(arrondissement);
        em.flush();
        commune.setArrondissement(arrondissement);
        communeRepository.saveAndFlush(commune);
        Long arrondissementId = arrondissement.getId();

        // Get all the communeList where arrondissement equals to arrondissementId
        defaultCommuneShouldBeFound("arrondissementId.equals=" + arrondissementId);

        // Get all the communeList where arrondissement equals to (arrondissementId + 1)
        defaultCommuneShouldNotBeFound("arrondissementId.equals=" + (arrondissementId + 1));
    }

    @Test
    @Transactional
    void getAllCommunesByRefParcelaireIsEqualToSomething() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);
        RefParcelaire refParcelaire = RefParcelaireResourceIT.createEntity(em);
        em.persist(refParcelaire);
        em.flush();
        commune.addRefParcelaire(refParcelaire);
        communeRepository.saveAndFlush(commune);
        Long refParcelaireId = refParcelaire.getId();

        // Get all the communeList where refParcelaire equals to refParcelaireId
        defaultCommuneShouldBeFound("refParcelaireId.equals=" + refParcelaireId);

        // Get all the communeList where refParcelaire equals to (refParcelaireId + 1)
        defaultCommuneShouldNotBeFound("refParcelaireId.equals=" + (refParcelaireId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommuneShouldBeFound(String filter) throws Exception {
        restCommuneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commune.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restCommuneMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommuneShouldNotBeFound(String filter) throws Exception {
        restCommuneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommuneMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCommune() throws Exception {
        // Get the commune
        restCommuneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        int databaseSizeBeforeUpdate = communeRepository.findAll().size();

        // Update the commune
        Commune updatedCommune = communeRepository.findById(commune.getId()).get();
        // Disconnect from session so that the updates on updatedCommune are not directly saved in db
        em.detach(updatedCommune);
        updatedCommune.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);
        CommuneDTO communeDTO = communeMapper.toDto(updatedCommune);

        restCommuneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, communeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
        Commune testCommune = communeList.get(communeList.size() - 1);
        assertThat(testCommune.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCommune.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void putNonExistingCommune() throws Exception {
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();
        commune.setId(count.incrementAndGet());

        // Create the Commune
        CommuneDTO communeDTO = communeMapper.toDto(commune);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommuneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, communeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommune() throws Exception {
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();
        commune.setId(count.incrementAndGet());

        // Create the Commune
        CommuneDTO communeDTO = communeMapper.toDto(commune);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommuneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommune() throws Exception {
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();
        commune.setId(count.incrementAndGet());

        // Create the Commune
        CommuneDTO communeDTO = communeMapper.toDto(commune);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommuneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(communeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommuneWithPatch() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        int databaseSizeBeforeUpdate = communeRepository.findAll().size();

        // Update the commune using partial update
        Commune partialUpdatedCommune = new Commune();
        partialUpdatedCommune.setId(commune.getId());

        restCommuneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommune.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommune))
            )
            .andExpect(status().isOk());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
        Commune testCommune = communeList.get(communeList.size() - 1);
        assertThat(testCommune.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCommune.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void fullUpdateCommuneWithPatch() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        int databaseSizeBeforeUpdate = communeRepository.findAll().size();

        // Update the commune using partial update
        Commune partialUpdatedCommune = new Commune();
        partialUpdatedCommune.setId(commune.getId());

        partialUpdatedCommune.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);

        restCommuneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommune.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommune))
            )
            .andExpect(status().isOk());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
        Commune testCommune = communeList.get(communeList.size() - 1);
        assertThat(testCommune.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCommune.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void patchNonExistingCommune() throws Exception {
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();
        commune.setId(count.incrementAndGet());

        // Create the Commune
        CommuneDTO communeDTO = communeMapper.toDto(commune);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommuneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, communeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(communeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommune() throws Exception {
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();
        commune.setId(count.incrementAndGet());

        // Create the Commune
        CommuneDTO communeDTO = communeMapper.toDto(commune);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommuneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(communeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommune() throws Exception {
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();
        commune.setId(count.incrementAndGet());

        // Create the Commune
        CommuneDTO communeDTO = communeMapper.toDto(commune);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommuneMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(communeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        int databaseSizeBeforeDelete = communeRepository.findAll().size();

        // Delete the commune
        restCommuneMockMvc
            .perform(delete(ENTITY_API_URL_ID, commune.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
