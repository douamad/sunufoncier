package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.Nature;
import com.pirtol.lab.repository.NatureRepository;
import com.pirtol.lab.service.criteria.NatureCriteria;
import com.pirtol.lab.service.dto.NatureDTO;
import com.pirtol.lab.service.mapper.NatureMapper;
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
 * Integration tests for the {@link NatureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NatureResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/natures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NatureRepository natureRepository;

    @Autowired
    private NatureMapper natureMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNatureMockMvc;

    private Nature nature;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nature createEntity(EntityManager em) {
        Nature nature = new Nature().code(DEFAULT_CODE).libelle(DEFAULT_LIBELLE);
        return nature;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nature createUpdatedEntity(EntityManager em) {
        Nature nature = new Nature().code(UPDATED_CODE).libelle(UPDATED_LIBELLE);
        return nature;
    }

    @BeforeEach
    public void initTest() {
        nature = createEntity(em);
    }

    @Test
    @Transactional
    void createNature() throws Exception {
        int databaseSizeBeforeCreate = natureRepository.findAll().size();
        // Create the Nature
        NatureDTO natureDTO = natureMapper.toDto(nature);
        restNatureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureDTO)))
            .andExpect(status().isCreated());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeCreate + 1);
        Nature testNature = natureList.get(natureList.size() - 1);
        assertThat(testNature.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNature.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void createNatureWithExistingId() throws Exception {
        // Create the Nature with an existing ID
        nature.setId(1L);
        NatureDTO natureDTO = natureMapper.toDto(nature);

        int databaseSizeBeforeCreate = natureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNatures() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get all the natureList
        restNatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nature.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getNature() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get the nature
        restNatureMockMvc
            .perform(get(ENTITY_API_URL_ID, nature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nature.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getNaturesByIdFiltering() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        Long id = nature.getId();

        defaultNatureShouldBeFound("id.equals=" + id);
        defaultNatureShouldNotBeFound("id.notEquals=" + id);

        defaultNatureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNatureShouldNotBeFound("id.greaterThan=" + id);

        defaultNatureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNatureShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNaturesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get all the natureList where code equals to DEFAULT_CODE
        defaultNatureShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the natureList where code equals to UPDATED_CODE
        defaultNatureShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllNaturesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get all the natureList where code not equals to DEFAULT_CODE
        defaultNatureShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the natureList where code not equals to UPDATED_CODE
        defaultNatureShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllNaturesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get all the natureList where code in DEFAULT_CODE or UPDATED_CODE
        defaultNatureShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the natureList where code equals to UPDATED_CODE
        defaultNatureShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllNaturesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get all the natureList where code is not null
        defaultNatureShouldBeFound("code.specified=true");

        // Get all the natureList where code is null
        defaultNatureShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllNaturesByCodeContainsSomething() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get all the natureList where code contains DEFAULT_CODE
        defaultNatureShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the natureList where code contains UPDATED_CODE
        defaultNatureShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllNaturesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get all the natureList where code does not contain DEFAULT_CODE
        defaultNatureShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the natureList where code does not contain UPDATED_CODE
        defaultNatureShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllNaturesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get all the natureList where libelle equals to DEFAULT_LIBELLE
        defaultNatureShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the natureList where libelle equals to UPDATED_LIBELLE
        defaultNatureShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllNaturesByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get all the natureList where libelle not equals to DEFAULT_LIBELLE
        defaultNatureShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the natureList where libelle not equals to UPDATED_LIBELLE
        defaultNatureShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllNaturesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get all the natureList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultNatureShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the natureList where libelle equals to UPDATED_LIBELLE
        defaultNatureShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllNaturesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get all the natureList where libelle is not null
        defaultNatureShouldBeFound("libelle.specified=true");

        // Get all the natureList where libelle is null
        defaultNatureShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    void getAllNaturesByLibelleContainsSomething() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get all the natureList where libelle contains DEFAULT_LIBELLE
        defaultNatureShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the natureList where libelle contains UPDATED_LIBELLE
        defaultNatureShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllNaturesByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get all the natureList where libelle does not contain DEFAULT_LIBELLE
        defaultNatureShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the natureList where libelle does not contain UPDATED_LIBELLE
        defaultNatureShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNatureShouldBeFound(String filter) throws Exception {
        restNatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nature.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restNatureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNatureShouldNotBeFound(String filter) throws Exception {
        restNatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNatureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNature() throws Exception {
        // Get the nature
        restNatureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNature() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        int databaseSizeBeforeUpdate = natureRepository.findAll().size();

        // Update the nature
        Nature updatedNature = natureRepository.findById(nature.getId()).get();
        // Disconnect from session so that the updates on updatedNature are not directly saved in db
        em.detach(updatedNature);
        updatedNature.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);
        NatureDTO natureDTO = natureMapper.toDto(updatedNature);

        restNatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureDTO))
            )
            .andExpect(status().isOk());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
        Nature testNature = natureList.get(natureList.size() - 1);
        assertThat(testNature.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNature.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void putNonExistingNature() throws Exception {
        int databaseSizeBeforeUpdate = natureRepository.findAll().size();
        nature.setId(count.incrementAndGet());

        // Create the Nature
        NatureDTO natureDTO = natureMapper.toDto(nature);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNature() throws Exception {
        int databaseSizeBeforeUpdate = natureRepository.findAll().size();
        nature.setId(count.incrementAndGet());

        // Create the Nature
        NatureDTO natureDTO = natureMapper.toDto(nature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNature() throws Exception {
        int databaseSizeBeforeUpdate = natureRepository.findAll().size();
        nature.setId(count.incrementAndGet());

        // Create the Nature
        NatureDTO natureDTO = natureMapper.toDto(nature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNatureWithPatch() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        int databaseSizeBeforeUpdate = natureRepository.findAll().size();

        // Update the nature using partial update
        Nature partialUpdatedNature = new Nature();
        partialUpdatedNature.setId(nature.getId());

        restNatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNature))
            )
            .andExpect(status().isOk());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
        Nature testNature = natureList.get(natureList.size() - 1);
        assertThat(testNature.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNature.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void fullUpdateNatureWithPatch() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        int databaseSizeBeforeUpdate = natureRepository.findAll().size();

        // Update the nature using partial update
        Nature partialUpdatedNature = new Nature();
        partialUpdatedNature.setId(nature.getId());

        partialUpdatedNature.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);

        restNatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNature))
            )
            .andExpect(status().isOk());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
        Nature testNature = natureList.get(natureList.size() - 1);
        assertThat(testNature.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNature.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void patchNonExistingNature() throws Exception {
        int databaseSizeBeforeUpdate = natureRepository.findAll().size();
        nature.setId(count.incrementAndGet());

        // Create the Nature
        NatureDTO natureDTO = natureMapper.toDto(nature);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, natureDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNature() throws Exception {
        int databaseSizeBeforeUpdate = natureRepository.findAll().size();
        nature.setId(count.incrementAndGet());

        // Create the Nature
        NatureDTO natureDTO = natureMapper.toDto(nature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNature() throws Exception {
        int databaseSizeBeforeUpdate = natureRepository.findAll().size();
        nature.setId(count.incrementAndGet());

        // Create the Nature
        NatureDTO natureDTO = natureMapper.toDto(nature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(natureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNature() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        int databaseSizeBeforeDelete = natureRepository.findAll().size();

        // Delete the nature
        restNatureMockMvc
            .perform(delete(ENTITY_API_URL_ID, nature.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
