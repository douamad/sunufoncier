package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.Dossier;
import com.pirtol.lab.domain.Locataire;
import com.pirtol.lab.repository.LocataireRepository;
import com.pirtol.lab.service.criteria.LocataireCriteria;
import com.pirtol.lab.service.dto.LocataireDTO;
import com.pirtol.lab.service.mapper.LocataireMapper;
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
 * Integration tests for the {@link LocataireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocataireResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PERSONNE = false;
    private static final Boolean UPDATED_PERSONNE = true;

    private static final String DEFAULT_ACTIVITE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITE = "BBBBBBBBBB";

    private static final String DEFAULT_NINEA = "AAAAAAAAAA";
    private static final String UPDATED_NINEA = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;
    private static final Double SMALLER_MONTANT = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/locataires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LocataireRepository locataireRepository;

    @Autowired
    private LocataireMapper locataireMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocataireMockMvc;

    private Locataire locataire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locataire createEntity(EntityManager em) {
        Locataire locataire = new Locataire()
            .nom(DEFAULT_NOM)
            .personne(DEFAULT_PERSONNE)
            .activite(DEFAULT_ACTIVITE)
            .ninea(DEFAULT_NINEA)
            .montant(DEFAULT_MONTANT);
        return locataire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locataire createUpdatedEntity(EntityManager em) {
        Locataire locataire = new Locataire()
            .nom(UPDATED_NOM)
            .personne(UPDATED_PERSONNE)
            .activite(UPDATED_ACTIVITE)
            .ninea(UPDATED_NINEA)
            .montant(UPDATED_MONTANT);
        return locataire;
    }

    @BeforeEach
    public void initTest() {
        locataire = createEntity(em);
    }

    @Test
    @Transactional
    void createLocataire() throws Exception {
        int databaseSizeBeforeCreate = locataireRepository.findAll().size();
        // Create the Locataire
        LocataireDTO locataireDTO = locataireMapper.toDto(locataire);
        restLocataireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locataireDTO)))
            .andExpect(status().isCreated());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeCreate + 1);
        Locataire testLocataire = locataireList.get(locataireList.size() - 1);
        assertThat(testLocataire.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testLocataire.getPersonne()).isEqualTo(DEFAULT_PERSONNE);
        assertThat(testLocataire.getActivite()).isEqualTo(DEFAULT_ACTIVITE);
        assertThat(testLocataire.getNinea()).isEqualTo(DEFAULT_NINEA);
        assertThat(testLocataire.getMontant()).isEqualTo(DEFAULT_MONTANT);
    }

    @Test
    @Transactional
    void createLocataireWithExistingId() throws Exception {
        // Create the Locataire with an existing ID
        locataire.setId(1L);
        LocataireDTO locataireDTO = locataireMapper.toDto(locataire);

        int databaseSizeBeforeCreate = locataireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocataireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locataireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLocataires() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList
        restLocataireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locataire.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].personne").value(hasItem(DEFAULT_PERSONNE.booleanValue())))
            .andExpect(jsonPath("$.[*].activite").value(hasItem(DEFAULT_ACTIVITE)))
            .andExpect(jsonPath("$.[*].ninea").value(hasItem(DEFAULT_NINEA)))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())));
    }

    @Test
    @Transactional
    void getLocataire() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get the locataire
        restLocataireMockMvc
            .perform(get(ENTITY_API_URL_ID, locataire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locataire.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.personne").value(DEFAULT_PERSONNE.booleanValue()))
            .andExpect(jsonPath("$.activite").value(DEFAULT_ACTIVITE))
            .andExpect(jsonPath("$.ninea").value(DEFAULT_NINEA))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()));
    }

    @Test
    @Transactional
    void getLocatairesByIdFiltering() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        Long id = locataire.getId();

        defaultLocataireShouldBeFound("id.equals=" + id);
        defaultLocataireShouldNotBeFound("id.notEquals=" + id);

        defaultLocataireShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLocataireShouldNotBeFound("id.greaterThan=" + id);

        defaultLocataireShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLocataireShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLocatairesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where nom equals to DEFAULT_NOM
        defaultLocataireShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the locataireList where nom equals to UPDATED_NOM
        defaultLocataireShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    void getAllLocatairesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where nom not equals to DEFAULT_NOM
        defaultLocataireShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the locataireList where nom not equals to UPDATED_NOM
        defaultLocataireShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    void getAllLocatairesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultLocataireShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the locataireList where nom equals to UPDATED_NOM
        defaultLocataireShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    void getAllLocatairesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where nom is not null
        defaultLocataireShouldBeFound("nom.specified=true");

        // Get all the locataireList where nom is null
        defaultLocataireShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    void getAllLocatairesByNomContainsSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where nom contains DEFAULT_NOM
        defaultLocataireShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the locataireList where nom contains UPDATED_NOM
        defaultLocataireShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    void getAllLocatairesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where nom does not contain DEFAULT_NOM
        defaultLocataireShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the locataireList where nom does not contain UPDATED_NOM
        defaultLocataireShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    void getAllLocatairesByPersonneIsEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where personne equals to DEFAULT_PERSONNE
        defaultLocataireShouldBeFound("personne.equals=" + DEFAULT_PERSONNE);

        // Get all the locataireList where personne equals to UPDATED_PERSONNE
        defaultLocataireShouldNotBeFound("personne.equals=" + UPDATED_PERSONNE);
    }

    @Test
    @Transactional
    void getAllLocatairesByPersonneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where personne not equals to DEFAULT_PERSONNE
        defaultLocataireShouldNotBeFound("personne.notEquals=" + DEFAULT_PERSONNE);

        // Get all the locataireList where personne not equals to UPDATED_PERSONNE
        defaultLocataireShouldBeFound("personne.notEquals=" + UPDATED_PERSONNE);
    }

    @Test
    @Transactional
    void getAllLocatairesByPersonneIsInShouldWork() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where personne in DEFAULT_PERSONNE or UPDATED_PERSONNE
        defaultLocataireShouldBeFound("personne.in=" + DEFAULT_PERSONNE + "," + UPDATED_PERSONNE);

        // Get all the locataireList where personne equals to UPDATED_PERSONNE
        defaultLocataireShouldNotBeFound("personne.in=" + UPDATED_PERSONNE);
    }

    @Test
    @Transactional
    void getAllLocatairesByPersonneIsNullOrNotNull() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where personne is not null
        defaultLocataireShouldBeFound("personne.specified=true");

        // Get all the locataireList where personne is null
        defaultLocataireShouldNotBeFound("personne.specified=false");
    }

    @Test
    @Transactional
    void getAllLocatairesByActiviteIsEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where activite equals to DEFAULT_ACTIVITE
        defaultLocataireShouldBeFound("activite.equals=" + DEFAULT_ACTIVITE);

        // Get all the locataireList where activite equals to UPDATED_ACTIVITE
        defaultLocataireShouldNotBeFound("activite.equals=" + UPDATED_ACTIVITE);
    }

    @Test
    @Transactional
    void getAllLocatairesByActiviteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where activite not equals to DEFAULT_ACTIVITE
        defaultLocataireShouldNotBeFound("activite.notEquals=" + DEFAULT_ACTIVITE);

        // Get all the locataireList where activite not equals to UPDATED_ACTIVITE
        defaultLocataireShouldBeFound("activite.notEquals=" + UPDATED_ACTIVITE);
    }

    @Test
    @Transactional
    void getAllLocatairesByActiviteIsInShouldWork() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where activite in DEFAULT_ACTIVITE or UPDATED_ACTIVITE
        defaultLocataireShouldBeFound("activite.in=" + DEFAULT_ACTIVITE + "," + UPDATED_ACTIVITE);

        // Get all the locataireList where activite equals to UPDATED_ACTIVITE
        defaultLocataireShouldNotBeFound("activite.in=" + UPDATED_ACTIVITE);
    }

    @Test
    @Transactional
    void getAllLocatairesByActiviteIsNullOrNotNull() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where activite is not null
        defaultLocataireShouldBeFound("activite.specified=true");

        // Get all the locataireList where activite is null
        defaultLocataireShouldNotBeFound("activite.specified=false");
    }

    @Test
    @Transactional
    void getAllLocatairesByActiviteContainsSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where activite contains DEFAULT_ACTIVITE
        defaultLocataireShouldBeFound("activite.contains=" + DEFAULT_ACTIVITE);

        // Get all the locataireList where activite contains UPDATED_ACTIVITE
        defaultLocataireShouldNotBeFound("activite.contains=" + UPDATED_ACTIVITE);
    }

    @Test
    @Transactional
    void getAllLocatairesByActiviteNotContainsSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where activite does not contain DEFAULT_ACTIVITE
        defaultLocataireShouldNotBeFound("activite.doesNotContain=" + DEFAULT_ACTIVITE);

        // Get all the locataireList where activite does not contain UPDATED_ACTIVITE
        defaultLocataireShouldBeFound("activite.doesNotContain=" + UPDATED_ACTIVITE);
    }

    @Test
    @Transactional
    void getAllLocatairesByNineaIsEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where ninea equals to DEFAULT_NINEA
        defaultLocataireShouldBeFound("ninea.equals=" + DEFAULT_NINEA);

        // Get all the locataireList where ninea equals to UPDATED_NINEA
        defaultLocataireShouldNotBeFound("ninea.equals=" + UPDATED_NINEA);
    }

    @Test
    @Transactional
    void getAllLocatairesByNineaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where ninea not equals to DEFAULT_NINEA
        defaultLocataireShouldNotBeFound("ninea.notEquals=" + DEFAULT_NINEA);

        // Get all the locataireList where ninea not equals to UPDATED_NINEA
        defaultLocataireShouldBeFound("ninea.notEquals=" + UPDATED_NINEA);
    }

    @Test
    @Transactional
    void getAllLocatairesByNineaIsInShouldWork() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where ninea in DEFAULT_NINEA or UPDATED_NINEA
        defaultLocataireShouldBeFound("ninea.in=" + DEFAULT_NINEA + "," + UPDATED_NINEA);

        // Get all the locataireList where ninea equals to UPDATED_NINEA
        defaultLocataireShouldNotBeFound("ninea.in=" + UPDATED_NINEA);
    }

    @Test
    @Transactional
    void getAllLocatairesByNineaIsNullOrNotNull() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where ninea is not null
        defaultLocataireShouldBeFound("ninea.specified=true");

        // Get all the locataireList where ninea is null
        defaultLocataireShouldNotBeFound("ninea.specified=false");
    }

    @Test
    @Transactional
    void getAllLocatairesByNineaContainsSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where ninea contains DEFAULT_NINEA
        defaultLocataireShouldBeFound("ninea.contains=" + DEFAULT_NINEA);

        // Get all the locataireList where ninea contains UPDATED_NINEA
        defaultLocataireShouldNotBeFound("ninea.contains=" + UPDATED_NINEA);
    }

    @Test
    @Transactional
    void getAllLocatairesByNineaNotContainsSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where ninea does not contain DEFAULT_NINEA
        defaultLocataireShouldNotBeFound("ninea.doesNotContain=" + DEFAULT_NINEA);

        // Get all the locataireList where ninea does not contain UPDATED_NINEA
        defaultLocataireShouldBeFound("ninea.doesNotContain=" + UPDATED_NINEA);
    }

    @Test
    @Transactional
    void getAllLocatairesByMontantIsEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where montant equals to DEFAULT_MONTANT
        defaultLocataireShouldBeFound("montant.equals=" + DEFAULT_MONTANT);

        // Get all the locataireList where montant equals to UPDATED_MONTANT
        defaultLocataireShouldNotBeFound("montant.equals=" + UPDATED_MONTANT);
    }

    @Test
    @Transactional
    void getAllLocatairesByMontantIsNotEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where montant not equals to DEFAULT_MONTANT
        defaultLocataireShouldNotBeFound("montant.notEquals=" + DEFAULT_MONTANT);

        // Get all the locataireList where montant not equals to UPDATED_MONTANT
        defaultLocataireShouldBeFound("montant.notEquals=" + UPDATED_MONTANT);
    }

    @Test
    @Transactional
    void getAllLocatairesByMontantIsInShouldWork() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where montant in DEFAULT_MONTANT or UPDATED_MONTANT
        defaultLocataireShouldBeFound("montant.in=" + DEFAULT_MONTANT + "," + UPDATED_MONTANT);

        // Get all the locataireList where montant equals to UPDATED_MONTANT
        defaultLocataireShouldNotBeFound("montant.in=" + UPDATED_MONTANT);
    }

    @Test
    @Transactional
    void getAllLocatairesByMontantIsNullOrNotNull() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where montant is not null
        defaultLocataireShouldBeFound("montant.specified=true");

        // Get all the locataireList where montant is null
        defaultLocataireShouldNotBeFound("montant.specified=false");
    }

    @Test
    @Transactional
    void getAllLocatairesByMontantIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where montant is greater than or equal to DEFAULT_MONTANT
        defaultLocataireShouldBeFound("montant.greaterThanOrEqual=" + DEFAULT_MONTANT);

        // Get all the locataireList where montant is greater than or equal to UPDATED_MONTANT
        defaultLocataireShouldNotBeFound("montant.greaterThanOrEqual=" + UPDATED_MONTANT);
    }

    @Test
    @Transactional
    void getAllLocatairesByMontantIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where montant is less than or equal to DEFAULT_MONTANT
        defaultLocataireShouldBeFound("montant.lessThanOrEqual=" + DEFAULT_MONTANT);

        // Get all the locataireList where montant is less than or equal to SMALLER_MONTANT
        defaultLocataireShouldNotBeFound("montant.lessThanOrEqual=" + SMALLER_MONTANT);
    }

    @Test
    @Transactional
    void getAllLocatairesByMontantIsLessThanSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where montant is less than DEFAULT_MONTANT
        defaultLocataireShouldNotBeFound("montant.lessThan=" + DEFAULT_MONTANT);

        // Get all the locataireList where montant is less than UPDATED_MONTANT
        defaultLocataireShouldBeFound("montant.lessThan=" + UPDATED_MONTANT);
    }

    @Test
    @Transactional
    void getAllLocatairesByMontantIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        // Get all the locataireList where montant is greater than DEFAULT_MONTANT
        defaultLocataireShouldNotBeFound("montant.greaterThan=" + DEFAULT_MONTANT);

        // Get all the locataireList where montant is greater than SMALLER_MONTANT
        defaultLocataireShouldBeFound("montant.greaterThan=" + SMALLER_MONTANT);
    }

    @Test
    @Transactional
    void getAllLocatairesByDossierIsEqualToSomething() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);
        Dossier dossier = DossierResourceIT.createEntity(em);
        em.persist(dossier);
        em.flush();
        locataire.setDossier(dossier);
        locataireRepository.saveAndFlush(locataire);
        Long dossierId = dossier.getId();

        // Get all the locataireList where dossier equals to dossierId
        defaultLocataireShouldBeFound("dossierId.equals=" + dossierId);

        // Get all the locataireList where dossier equals to (dossierId + 1)
        defaultLocataireShouldNotBeFound("dossierId.equals=" + (dossierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocataireShouldBeFound(String filter) throws Exception {
        restLocataireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locataire.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].personne").value(hasItem(DEFAULT_PERSONNE.booleanValue())))
            .andExpect(jsonPath("$.[*].activite").value(hasItem(DEFAULT_ACTIVITE)))
            .andExpect(jsonPath("$.[*].ninea").value(hasItem(DEFAULT_NINEA)))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())));

        // Check, that the count call also returns 1
        restLocataireMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocataireShouldNotBeFound(String filter) throws Exception {
        restLocataireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocataireMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLocataire() throws Exception {
        // Get the locataire
        restLocataireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLocataire() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        int databaseSizeBeforeUpdate = locataireRepository.findAll().size();

        // Update the locataire
        Locataire updatedLocataire = locataireRepository.findById(locataire.getId()).get();
        // Disconnect from session so that the updates on updatedLocataire are not directly saved in db
        em.detach(updatedLocataire);
        updatedLocataire
            .nom(UPDATED_NOM)
            .personne(UPDATED_PERSONNE)
            .activite(UPDATED_ACTIVITE)
            .ninea(UPDATED_NINEA)
            .montant(UPDATED_MONTANT);
        LocataireDTO locataireDTO = locataireMapper.toDto(updatedLocataire);

        restLocataireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locataireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locataireDTO))
            )
            .andExpect(status().isOk());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeUpdate);
        Locataire testLocataire = locataireList.get(locataireList.size() - 1);
        assertThat(testLocataire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testLocataire.getPersonne()).isEqualTo(UPDATED_PERSONNE);
        assertThat(testLocataire.getActivite()).isEqualTo(UPDATED_ACTIVITE);
        assertThat(testLocataire.getNinea()).isEqualTo(UPDATED_NINEA);
        assertThat(testLocataire.getMontant()).isEqualTo(UPDATED_MONTANT);
    }

    @Test
    @Transactional
    void putNonExistingLocataire() throws Exception {
        int databaseSizeBeforeUpdate = locataireRepository.findAll().size();
        locataire.setId(count.incrementAndGet());

        // Create the Locataire
        LocataireDTO locataireDTO = locataireMapper.toDto(locataire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocataireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locataireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locataireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocataire() throws Exception {
        int databaseSizeBeforeUpdate = locataireRepository.findAll().size();
        locataire.setId(count.incrementAndGet());

        // Create the Locataire
        LocataireDTO locataireDTO = locataireMapper.toDto(locataire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocataireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locataireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocataire() throws Exception {
        int databaseSizeBeforeUpdate = locataireRepository.findAll().size();
        locataire.setId(count.incrementAndGet());

        // Create the Locataire
        LocataireDTO locataireDTO = locataireMapper.toDto(locataire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocataireMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locataireDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocataireWithPatch() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        int databaseSizeBeforeUpdate = locataireRepository.findAll().size();

        // Update the locataire using partial update
        Locataire partialUpdatedLocataire = new Locataire();
        partialUpdatedLocataire.setId(locataire.getId());

        partialUpdatedLocataire.nom(UPDATED_NOM).activite(UPDATED_ACTIVITE).montant(UPDATED_MONTANT);

        restLocataireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocataire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocataire))
            )
            .andExpect(status().isOk());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeUpdate);
        Locataire testLocataire = locataireList.get(locataireList.size() - 1);
        assertThat(testLocataire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testLocataire.getPersonne()).isEqualTo(DEFAULT_PERSONNE);
        assertThat(testLocataire.getActivite()).isEqualTo(UPDATED_ACTIVITE);
        assertThat(testLocataire.getNinea()).isEqualTo(DEFAULT_NINEA);
        assertThat(testLocataire.getMontant()).isEqualTo(UPDATED_MONTANT);
    }

    @Test
    @Transactional
    void fullUpdateLocataireWithPatch() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        int databaseSizeBeforeUpdate = locataireRepository.findAll().size();

        // Update the locataire using partial update
        Locataire partialUpdatedLocataire = new Locataire();
        partialUpdatedLocataire.setId(locataire.getId());

        partialUpdatedLocataire
            .nom(UPDATED_NOM)
            .personne(UPDATED_PERSONNE)
            .activite(UPDATED_ACTIVITE)
            .ninea(UPDATED_NINEA)
            .montant(UPDATED_MONTANT);

        restLocataireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocataire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocataire))
            )
            .andExpect(status().isOk());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeUpdate);
        Locataire testLocataire = locataireList.get(locataireList.size() - 1);
        assertThat(testLocataire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testLocataire.getPersonne()).isEqualTo(UPDATED_PERSONNE);
        assertThat(testLocataire.getActivite()).isEqualTo(UPDATED_ACTIVITE);
        assertThat(testLocataire.getNinea()).isEqualTo(UPDATED_NINEA);
        assertThat(testLocataire.getMontant()).isEqualTo(UPDATED_MONTANT);
    }

    @Test
    @Transactional
    void patchNonExistingLocataire() throws Exception {
        int databaseSizeBeforeUpdate = locataireRepository.findAll().size();
        locataire.setId(count.incrementAndGet());

        // Create the Locataire
        LocataireDTO locataireDTO = locataireMapper.toDto(locataire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocataireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locataireDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locataireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocataire() throws Exception {
        int databaseSizeBeforeUpdate = locataireRepository.findAll().size();
        locataire.setId(count.incrementAndGet());

        // Create the Locataire
        LocataireDTO locataireDTO = locataireMapper.toDto(locataire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocataireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locataireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocataire() throws Exception {
        int databaseSizeBeforeUpdate = locataireRepository.findAll().size();
        locataire.setId(count.incrementAndGet());

        // Create the Locataire
        LocataireDTO locataireDTO = locataireMapper.toDto(locataire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocataireMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(locataireDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locataire in the database
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocataire() throws Exception {
        // Initialize the database
        locataireRepository.saveAndFlush(locataire);

        int databaseSizeBeforeDelete = locataireRepository.findAll().size();

        // Delete the locataire
        restLocataireMockMvc
            .perform(delete(ENTITY_API_URL_ID, locataire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Locataire> locataireList = locataireRepository.findAll();
        assertThat(locataireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
