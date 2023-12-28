package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.Dossier;
import com.pirtol.lab.domain.EvaluationBatiments;
import com.pirtol.lab.domain.EvaluationCloture;
import com.pirtol.lab.domain.EvaluationCoursAmenage;
import com.pirtol.lab.domain.EvaluationSurfaceBatie;
import com.pirtol.lab.domain.Locataire;
import com.pirtol.lab.domain.Lotissement;
import com.pirtol.lab.domain.Proprietaire;
import com.pirtol.lab.domain.RefParcelaire;
import com.pirtol.lab.domain.Refcadastrale;
import com.pirtol.lab.domain.UsageDossier;
import com.pirtol.lab.repository.DossierRepository;
import com.pirtol.lab.repository.LotissementRepository;
import com.pirtol.lab.service.criteria.DossierCriteria;
import com.pirtol.lab.service.dto.DossierDTO;
import com.pirtol.lab.service.mapper.DossierMapper;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
 * Integration tests for the {@link DossierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DossierResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final Double DEFAULT_VALEUR_BATIE = 1D;
    private static final Double UPDATED_VALEUR_BATIE = 2D;
    private static final Double SMALLER_VALEUR_BATIE = 1D - 1D;

    private static final Double DEFAULT_VALEUR_VENALE = 1D;
    private static final Double UPDATED_VALEUR_VENALE = 2D;
    private static final Double SMALLER_VALEUR_VENALE = 1D - 1D;

    private static final Double DEFAULT_VALEUR_LOCATIV = 1D;
    private static final Double UPDATED_VALEUR_LOCATIV = 2D;
    private static final Double SMALLER_VALEUR_LOCATIV = 1D - 1D;

    private static final String DEFAULT_ACTIVITE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dossiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DossierRepository dossierRepository;

    @Autowired
    private DossierMapper dossierMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDossierMockMvc;

    private Dossier dossier;

    @Autowired
    private LotissementRepository lotissementRepository;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dossier createEntity(EntityManager em) {
        Dossier dossier = new Dossier()
            .numero(DEFAULT_NUMERO)
            .valeurBatie(DEFAULT_VALEUR_BATIE)
            .valeurVenale(DEFAULT_VALEUR_VENALE)
            .valeurLocativ(DEFAULT_VALEUR_LOCATIV)
            .activite(DEFAULT_ACTIVITE);
        return dossier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dossier createUpdatedEntity(EntityManager em) {
        Dossier dossier = new Dossier()
            .numero(UPDATED_NUMERO)
            .valeurBatie(UPDATED_VALEUR_BATIE)
            .valeurVenale(UPDATED_VALEUR_VENALE)
            .valeurLocativ(UPDATED_VALEUR_LOCATIV)
            .activite(UPDATED_ACTIVITE);
        return dossier;
    }

    @BeforeEach
    public void initTest() {
        dossier = createEntity(em);
    }

    @Test
    @Transactional
    void createDossier() throws Exception {
        int databaseSizeBeforeCreate = dossierRepository.findAll().size();
        // Create the Dossier
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);
        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossierDTO)))
            .andExpect(status().isCreated());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeCreate + 1);
        Dossier testDossier = dossierList.get(dossierList.size() - 1);
        assertThat(testDossier.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testDossier.getValeurBatie()).isEqualTo(DEFAULT_VALEUR_BATIE);
        assertThat(testDossier.getValeurVenale()).isEqualTo(DEFAULT_VALEUR_VENALE);
        assertThat(testDossier.getValeurLocativ()).isEqualTo(DEFAULT_VALEUR_LOCATIV);
        assertThat(testDossier.getActivite()).isEqualTo(DEFAULT_ACTIVITE);
    }

    @Test
    @Transactional
    void createDossierWithExistingId() throws Exception {
        // Create the Dossier with an existing ID
        dossier.setId(1L);
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);

        int databaseSizeBeforeCreate = dossierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDossierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDossiers() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList
        restDossierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dossier.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].valeurBatie").value(hasItem(DEFAULT_VALEUR_BATIE.doubleValue())))
            .andExpect(jsonPath("$.[*].valeurVenale").value(hasItem(DEFAULT_VALEUR_VENALE.doubleValue())))
            .andExpect(jsonPath("$.[*].valeurLocativ").value(hasItem(DEFAULT_VALEUR_LOCATIV.doubleValue())))
            .andExpect(jsonPath("$.[*].activite").value(hasItem(DEFAULT_ACTIVITE)));
    }

    @Test
    @Transactional
    void getDossier() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get the dossier
        restDossierMockMvc
            .perform(get(ENTITY_API_URL_ID, dossier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dossier.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.valeurBatie").value(DEFAULT_VALEUR_BATIE.doubleValue()))
            .andExpect(jsonPath("$.valeurVenale").value(DEFAULT_VALEUR_VENALE.doubleValue()))
            .andExpect(jsonPath("$.valeurLocativ").value(DEFAULT_VALEUR_LOCATIV.doubleValue()))
            .andExpect(jsonPath("$.activite").value(DEFAULT_ACTIVITE));
    }

    @Test
    @Transactional
    void getDossiersByIdFiltering() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        Long id = dossier.getId();

        defaultDossierShouldBeFound("id.equals=" + id);
        defaultDossierShouldNotBeFound("id.notEquals=" + id);

        defaultDossierShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDossierShouldNotBeFound("id.greaterThan=" + id);

        defaultDossierShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDossierShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDossiersByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where numero equals to DEFAULT_NUMERO
        defaultDossierShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the dossierList where numero equals to UPDATED_NUMERO
        defaultDossierShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllDossiersByNumeroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where numero not equals to DEFAULT_NUMERO
        defaultDossierShouldNotBeFound("numero.notEquals=" + DEFAULT_NUMERO);

        // Get all the dossierList where numero not equals to UPDATED_NUMERO
        defaultDossierShouldBeFound("numero.notEquals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllDossiersByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultDossierShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the dossierList where numero equals to UPDATED_NUMERO
        defaultDossierShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllDossiersByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where numero is not null
        defaultDossierShouldBeFound("numero.specified=true");

        // Get all the dossierList where numero is null
        defaultDossierShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    void getAllDossiersByNumeroContainsSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where numero contains DEFAULT_NUMERO
        defaultDossierShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the dossierList where numero contains UPDATED_NUMERO
        defaultDossierShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllDossiersByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where numero does not contain DEFAULT_NUMERO
        defaultDossierShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the dossierList where numero does not contain UPDATED_NUMERO
        defaultDossierShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurBatieIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurBatie equals to DEFAULT_VALEUR_BATIE
        defaultDossierShouldBeFound("valeurBatie.equals=" + DEFAULT_VALEUR_BATIE);

        // Get all the dossierList where valeurBatie equals to UPDATED_VALEUR_BATIE
        defaultDossierShouldNotBeFound("valeurBatie.equals=" + UPDATED_VALEUR_BATIE);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurBatieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurBatie not equals to DEFAULT_VALEUR_BATIE
        defaultDossierShouldNotBeFound("valeurBatie.notEquals=" + DEFAULT_VALEUR_BATIE);

        // Get all the dossierList where valeurBatie not equals to UPDATED_VALEUR_BATIE
        defaultDossierShouldBeFound("valeurBatie.notEquals=" + UPDATED_VALEUR_BATIE);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurBatieIsInShouldWork() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurBatie in DEFAULT_VALEUR_BATIE or UPDATED_VALEUR_BATIE
        defaultDossierShouldBeFound("valeurBatie.in=" + DEFAULT_VALEUR_BATIE + "," + UPDATED_VALEUR_BATIE);

        // Get all the dossierList where valeurBatie equals to UPDATED_VALEUR_BATIE
        defaultDossierShouldNotBeFound("valeurBatie.in=" + UPDATED_VALEUR_BATIE);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurBatieIsNullOrNotNull() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurBatie is not null
        defaultDossierShouldBeFound("valeurBatie.specified=true");

        // Get all the dossierList where valeurBatie is null
        defaultDossierShouldNotBeFound("valeurBatie.specified=false");
    }

    @Test
    @Transactional
    void getAllDossiersByValeurBatieIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurBatie is greater than or equal to DEFAULT_VALEUR_BATIE
        defaultDossierShouldBeFound("valeurBatie.greaterThanOrEqual=" + DEFAULT_VALEUR_BATIE);

        // Get all the dossierList where valeurBatie is greater than or equal to UPDATED_VALEUR_BATIE
        defaultDossierShouldNotBeFound("valeurBatie.greaterThanOrEqual=" + UPDATED_VALEUR_BATIE);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurBatieIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurBatie is less than or equal to DEFAULT_VALEUR_BATIE
        defaultDossierShouldBeFound("valeurBatie.lessThanOrEqual=" + DEFAULT_VALEUR_BATIE);

        // Get all the dossierList where valeurBatie is less than or equal to SMALLER_VALEUR_BATIE
        defaultDossierShouldNotBeFound("valeurBatie.lessThanOrEqual=" + SMALLER_VALEUR_BATIE);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurBatieIsLessThanSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurBatie is less than DEFAULT_VALEUR_BATIE
        defaultDossierShouldNotBeFound("valeurBatie.lessThan=" + DEFAULT_VALEUR_BATIE);

        // Get all the dossierList where valeurBatie is less than UPDATED_VALEUR_BATIE
        defaultDossierShouldBeFound("valeurBatie.lessThan=" + UPDATED_VALEUR_BATIE);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurBatieIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurBatie is greater than DEFAULT_VALEUR_BATIE
        defaultDossierShouldNotBeFound("valeurBatie.greaterThan=" + DEFAULT_VALEUR_BATIE);

        // Get all the dossierList where valeurBatie is greater than SMALLER_VALEUR_BATIE
        defaultDossierShouldBeFound("valeurBatie.greaterThan=" + SMALLER_VALEUR_BATIE);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurVenaleIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurVenale equals to DEFAULT_VALEUR_VENALE
        defaultDossierShouldBeFound("valeurVenale.equals=" + DEFAULT_VALEUR_VENALE);

        // Get all the dossierList where valeurVenale equals to UPDATED_VALEUR_VENALE
        defaultDossierShouldNotBeFound("valeurVenale.equals=" + UPDATED_VALEUR_VENALE);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurVenaleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurVenale not equals to DEFAULT_VALEUR_VENALE
        defaultDossierShouldNotBeFound("valeurVenale.notEquals=" + DEFAULT_VALEUR_VENALE);

        // Get all the dossierList where valeurVenale not equals to UPDATED_VALEUR_VENALE
        defaultDossierShouldBeFound("valeurVenale.notEquals=" + UPDATED_VALEUR_VENALE);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurVenaleIsInShouldWork() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurVenale in DEFAULT_VALEUR_VENALE or UPDATED_VALEUR_VENALE
        defaultDossierShouldBeFound("valeurVenale.in=" + DEFAULT_VALEUR_VENALE + "," + UPDATED_VALEUR_VENALE);

        // Get all the dossierList where valeurVenale equals to UPDATED_VALEUR_VENALE
        defaultDossierShouldNotBeFound("valeurVenale.in=" + UPDATED_VALEUR_VENALE);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurVenaleIsNullOrNotNull() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurVenale is not null
        defaultDossierShouldBeFound("valeurVenale.specified=true");

        // Get all the dossierList where valeurVenale is null
        defaultDossierShouldNotBeFound("valeurVenale.specified=false");
    }

    @Test
    @Transactional
    void getAllDossiersByValeurVenaleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurVenale is greater than or equal to DEFAULT_VALEUR_VENALE
        defaultDossierShouldBeFound("valeurVenale.greaterThanOrEqual=" + DEFAULT_VALEUR_VENALE);

        // Get all the dossierList where valeurVenale is greater than or equal to UPDATED_VALEUR_VENALE
        defaultDossierShouldNotBeFound("valeurVenale.greaterThanOrEqual=" + UPDATED_VALEUR_VENALE);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurVenaleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurVenale is less than or equal to DEFAULT_VALEUR_VENALE
        defaultDossierShouldBeFound("valeurVenale.lessThanOrEqual=" + DEFAULT_VALEUR_VENALE);

        // Get all the dossierList where valeurVenale is less than or equal to SMALLER_VALEUR_VENALE
        defaultDossierShouldNotBeFound("valeurVenale.lessThanOrEqual=" + SMALLER_VALEUR_VENALE);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurVenaleIsLessThanSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurVenale is less than DEFAULT_VALEUR_VENALE
        defaultDossierShouldNotBeFound("valeurVenale.lessThan=" + DEFAULT_VALEUR_VENALE);

        // Get all the dossierList where valeurVenale is less than UPDATED_VALEUR_VENALE
        defaultDossierShouldBeFound("valeurVenale.lessThan=" + UPDATED_VALEUR_VENALE);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurVenaleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurVenale is greater than DEFAULT_VALEUR_VENALE
        defaultDossierShouldNotBeFound("valeurVenale.greaterThan=" + DEFAULT_VALEUR_VENALE);

        // Get all the dossierList where valeurVenale is greater than SMALLER_VALEUR_VENALE
        defaultDossierShouldBeFound("valeurVenale.greaterThan=" + SMALLER_VALEUR_VENALE);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurLocativIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurLocativ equals to DEFAULT_VALEUR_LOCATIV
        defaultDossierShouldBeFound("valeurLocativ.equals=" + DEFAULT_VALEUR_LOCATIV);

        // Get all the dossierList where valeurLocativ equals to UPDATED_VALEUR_LOCATIV
        defaultDossierShouldNotBeFound("valeurLocativ.equals=" + UPDATED_VALEUR_LOCATIV);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurLocativIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurLocativ not equals to DEFAULT_VALEUR_LOCATIV
        defaultDossierShouldNotBeFound("valeurLocativ.notEquals=" + DEFAULT_VALEUR_LOCATIV);

        // Get all the dossierList where valeurLocativ not equals to UPDATED_VALEUR_LOCATIV
        defaultDossierShouldBeFound("valeurLocativ.notEquals=" + UPDATED_VALEUR_LOCATIV);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurLocativIsInShouldWork() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurLocativ in DEFAULT_VALEUR_LOCATIV or UPDATED_VALEUR_LOCATIV
        defaultDossierShouldBeFound("valeurLocativ.in=" + DEFAULT_VALEUR_LOCATIV + "," + UPDATED_VALEUR_LOCATIV);

        // Get all the dossierList where valeurLocativ equals to UPDATED_VALEUR_LOCATIV
        defaultDossierShouldNotBeFound("valeurLocativ.in=" + UPDATED_VALEUR_LOCATIV);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurLocativIsNullOrNotNull() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurLocativ is not null
        defaultDossierShouldBeFound("valeurLocativ.specified=true");

        // Get all the dossierList where valeurLocativ is null
        defaultDossierShouldNotBeFound("valeurLocativ.specified=false");
    }

    @Test
    @Transactional
    void getAllDossiersByValeurLocativIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurLocativ is greater than or equal to DEFAULT_VALEUR_LOCATIV
        defaultDossierShouldBeFound("valeurLocativ.greaterThanOrEqual=" + DEFAULT_VALEUR_LOCATIV);

        // Get all the dossierList where valeurLocativ is greater than or equal to UPDATED_VALEUR_LOCATIV
        defaultDossierShouldNotBeFound("valeurLocativ.greaterThanOrEqual=" + UPDATED_VALEUR_LOCATIV);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurLocativIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurLocativ is less than or equal to DEFAULT_VALEUR_LOCATIV
        defaultDossierShouldBeFound("valeurLocativ.lessThanOrEqual=" + DEFAULT_VALEUR_LOCATIV);

        // Get all the dossierList where valeurLocativ is less than or equal to SMALLER_VALEUR_LOCATIV
        defaultDossierShouldNotBeFound("valeurLocativ.lessThanOrEqual=" + SMALLER_VALEUR_LOCATIV);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurLocativIsLessThanSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurLocativ is less than DEFAULT_VALEUR_LOCATIV
        defaultDossierShouldNotBeFound("valeurLocativ.lessThan=" + DEFAULT_VALEUR_LOCATIV);

        // Get all the dossierList where valeurLocativ is less than UPDATED_VALEUR_LOCATIV
        defaultDossierShouldBeFound("valeurLocativ.lessThan=" + UPDATED_VALEUR_LOCATIV);
    }

    @Test
    @Transactional
    void getAllDossiersByValeurLocativIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where valeurLocativ is greater than DEFAULT_VALEUR_LOCATIV
        defaultDossierShouldNotBeFound("valeurLocativ.greaterThan=" + DEFAULT_VALEUR_LOCATIV);

        // Get all the dossierList where valeurLocativ is greater than SMALLER_VALEUR_LOCATIV
        defaultDossierShouldBeFound("valeurLocativ.greaterThan=" + SMALLER_VALEUR_LOCATIV);
    }

    @Test
    @Transactional
    void getAllDossiersByActiviteIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where activite equals to DEFAULT_ACTIVITE
        defaultDossierShouldBeFound("activite.equals=" + DEFAULT_ACTIVITE);

        // Get all the dossierList where activite equals to UPDATED_ACTIVITE
        defaultDossierShouldNotBeFound("activite.equals=" + UPDATED_ACTIVITE);
    }

    @Test
    @Transactional
    void getAllDossiersByActiviteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where activite not equals to DEFAULT_ACTIVITE
        defaultDossierShouldNotBeFound("activite.notEquals=" + DEFAULT_ACTIVITE);

        // Get all the dossierList where activite not equals to UPDATED_ACTIVITE
        defaultDossierShouldBeFound("activite.notEquals=" + UPDATED_ACTIVITE);
    }

    @Test
    @Transactional
    void getAllDossiersByActiviteIsInShouldWork() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where activite in DEFAULT_ACTIVITE or UPDATED_ACTIVITE
        defaultDossierShouldBeFound("activite.in=" + DEFAULT_ACTIVITE + "," + UPDATED_ACTIVITE);

        // Get all the dossierList where activite equals to UPDATED_ACTIVITE
        defaultDossierShouldNotBeFound("activite.in=" + UPDATED_ACTIVITE);
    }

    @Test
    @Transactional
    void getAllDossiersByActiviteIsNullOrNotNull() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where activite is not null
        defaultDossierShouldBeFound("activite.specified=true");

        // Get all the dossierList where activite is null
        defaultDossierShouldNotBeFound("activite.specified=false");
    }

    @Test
    @Transactional
    void getAllDossiersByActiviteContainsSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where activite contains DEFAULT_ACTIVITE
        defaultDossierShouldBeFound("activite.contains=" + DEFAULT_ACTIVITE);

        // Get all the dossierList where activite contains UPDATED_ACTIVITE
        defaultDossierShouldNotBeFound("activite.contains=" + UPDATED_ACTIVITE);
    }

    @Test
    @Transactional
    void getAllDossiersByActiviteNotContainsSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        // Get all the dossierList where activite does not contain DEFAULT_ACTIVITE
        defaultDossierShouldNotBeFound("activite.doesNotContain=" + DEFAULT_ACTIVITE);

        // Get all the dossierList where activite does not contain UPDATED_ACTIVITE
        defaultDossierShouldBeFound("activite.doesNotContain=" + UPDATED_ACTIVITE);
    }

    @Test
    @Transactional
    void getAllDossiersByEvaluationSurfaceBatieIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);
        EvaluationSurfaceBatie evaluationSurfaceBatie = EvaluationSurfaceBatieResourceIT.createEntity(em);
        em.persist(evaluationSurfaceBatie);
        em.flush();
        dossier.addEvaluationSurfaceBatie(evaluationSurfaceBatie);
        dossierRepository.saveAndFlush(dossier);
        Long evaluationSurfaceBatieId = evaluationSurfaceBatie.getId();

        // Get all the dossierList where evaluationSurfaceBatie equals to evaluationSurfaceBatieId
        defaultDossierShouldBeFound("evaluationSurfaceBatieId.equals=" + evaluationSurfaceBatieId);

        // Get all the dossierList where evaluationSurfaceBatie equals to (evaluationSurfaceBatieId + 1)
        defaultDossierShouldNotBeFound("evaluationSurfaceBatieId.equals=" + (evaluationSurfaceBatieId + 1));
    }

    @Test
    @Transactional
    void getAllDossiersByEvaluationBatimentsIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);
        EvaluationBatiments evaluationBatiments = EvaluationBatimentsResourceIT.createEntity(em);
        em.persist(evaluationBatiments);
        em.flush();
        dossier.addEvaluationBatiments(evaluationBatiments);
        dossierRepository.saveAndFlush(dossier);
        Long evaluationBatimentsId = evaluationBatiments.getId();

        // Get all the dossierList where evaluationBatiments equals to evaluationBatimentsId
        defaultDossierShouldBeFound("evaluationBatimentsId.equals=" + evaluationBatimentsId);

        // Get all the dossierList where evaluationBatiments equals to (evaluationBatimentsId + 1)
        defaultDossierShouldNotBeFound("evaluationBatimentsId.equals=" + (evaluationBatimentsId + 1));
    }

    @Test
    @Transactional
    void getAllDossiersByEvaluationClotureIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);
        EvaluationCloture evaluationCloture = EvaluationClotureResourceIT.createEntity(em);
        em.persist(evaluationCloture);
        em.flush();
        dossier.addEvaluationCloture(evaluationCloture);
        dossierRepository.saveAndFlush(dossier);
        Long evaluationClotureId = evaluationCloture.getId();

        // Get all the dossierList where evaluationCloture equals to evaluationClotureId
        defaultDossierShouldBeFound("evaluationClotureId.equals=" + evaluationClotureId);

        // Get all the dossierList where evaluationCloture equals to (evaluationClotureId + 1)
        defaultDossierShouldNotBeFound("evaluationClotureId.equals=" + (evaluationClotureId + 1));
    }

    @Test
    @Transactional
    void getAllDossiersByEvaluationCoursAmenageIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);
        EvaluationCoursAmenage evaluationCoursAmenage = EvaluationCoursAmenageResourceIT.createEntity(em);
        em.persist(evaluationCoursAmenage);
        em.flush();
        dossier.addEvaluationCoursAmenage(evaluationCoursAmenage);
        dossierRepository.saveAndFlush(dossier);
        Long evaluationCoursAmenageId = evaluationCoursAmenage.getId();

        // Get all the dossierList where evaluationCoursAmenage equals to evaluationCoursAmenageId
        defaultDossierShouldBeFound("evaluationCoursAmenageId.equals=" + evaluationCoursAmenageId);

        // Get all the dossierList where evaluationCoursAmenage equals to (evaluationCoursAmenageId + 1)
        defaultDossierShouldNotBeFound("evaluationCoursAmenageId.equals=" + (evaluationCoursAmenageId + 1));
    }

    @Test
    @Transactional
    void getAllDossiersByLocataireIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);
        Locataire locataire = LocataireResourceIT.createEntity(em);
        em.persist(locataire);
        em.flush();
        dossier.addLocataire(locataire);
        dossierRepository.saveAndFlush(dossier);
        Long locataireId = locataire.getId();

        // Get all the dossierList where locataire equals to locataireId
        defaultDossierShouldBeFound("locataireId.equals=" + locataireId);

        // Get all the dossierList where locataire equals to (locataireId + 1)
        defaultDossierShouldNotBeFound("locataireId.equals=" + (locataireId + 1));
    }

    @Test
    @Transactional
    void getAllDossiersByDossierIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);
        Lotissement dossier = LotissementResourceIT.createEntity(em);
        em.persist(dossier);
        em.flush();
        dossier.setLotissements((Set<Dossier>) dossier);
        lotissementRepository.saveAndFlush(dossier);
        Long dossierId = dossier.getId();

        // Get all the dossierList where dossier equals to dossierId
        defaultDossierShouldBeFound("dossierId.equals=" + dossierId);

        // Get all the dossierList where dossier equals to (dossierId + 1)
        defaultDossierShouldNotBeFound("dossierId.equals=" + (dossierId + 1));
    }

    @Test
    @Transactional
    void getAllDossiersByUsageDossierIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);
        UsageDossier usageDossier = UsageDossierResourceIT.createEntity(em);
        em.persist(usageDossier);
        em.flush();
        dossier.setUsageDossier(usageDossier);
        dossierRepository.saveAndFlush(dossier);
        Long usageDossierId = usageDossier.getId();

        // Get all the dossierList where usageDossier equals to usageDossierId
        defaultDossierShouldBeFound("usageDossierId.equals=" + usageDossierId);

        // Get all the dossierList where usageDossier equals to (usageDossierId + 1)
        defaultDossierShouldNotBeFound("usageDossierId.equals=" + (usageDossierId + 1));
    }

    @Test
    @Transactional
    void getAllDossiersByProprietaireIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);
        Proprietaire proprietaire = ProprietaireResourceIT.createEntity(em);
        em.persist(proprietaire);
        em.flush();
        dossier.setProprietaire(proprietaire);
        dossierRepository.saveAndFlush(dossier);
        Long proprietaireId = proprietaire.getId();

        // Get all the dossierList where proprietaire equals to proprietaireId
        defaultDossierShouldBeFound("proprietaireId.equals=" + proprietaireId);

        // Get all the dossierList where proprietaire equals to (proprietaireId + 1)
        defaultDossierShouldNotBeFound("proprietaireId.equals=" + (proprietaireId + 1));
    }

    @Test
    @Transactional
    void getAllDossiersByRefParcelaireIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);
        RefParcelaire refParcelaire = RefParcelaireResourceIT.createEntity(em);
        em.persist(refParcelaire);
        em.flush();
        dossier.setRefParcelaire(refParcelaire);
        dossierRepository.saveAndFlush(dossier);
        Long refParcelaireId = refParcelaire.getId();

        // Get all the dossierList where refParcelaire equals to refParcelaireId
        defaultDossierShouldBeFound("refParcelaireId.equals=" + refParcelaireId);

        // Get all the dossierList where refParcelaire equals to (refParcelaireId + 1)
        defaultDossierShouldNotBeFound("refParcelaireId.equals=" + (refParcelaireId + 1));
    }

    @Test
    @Transactional
    void getAllDossiersByRefcadastraleIsEqualToSomething() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);
        Refcadastrale refcadastrale = RefcadastraleResourceIT.createEntity(em);
        em.persist(refcadastrale);
        em.flush();
        dossier.setRefcadastrale(refcadastrale);
        dossierRepository.saveAndFlush(dossier);
        Long refcadastraleId = refcadastrale.getId();

        // Get all the dossierList where refcadastrale equals to refcadastraleId
        defaultDossierShouldBeFound("refcadastraleId.equals=" + refcadastraleId);

        // Get all the dossierList where refcadastrale equals to (refcadastraleId + 1)
        defaultDossierShouldNotBeFound("refcadastraleId.equals=" + (refcadastraleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDossierShouldBeFound(String filter) throws Exception {
        restDossierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dossier.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].valeurBatie").value(hasItem(DEFAULT_VALEUR_BATIE.doubleValue())))
            .andExpect(jsonPath("$.[*].valeurVenale").value(hasItem(DEFAULT_VALEUR_VENALE.doubleValue())))
            .andExpect(jsonPath("$.[*].valeurLocativ").value(hasItem(DEFAULT_VALEUR_LOCATIV.doubleValue())))
            .andExpect(jsonPath("$.[*].activite").value(hasItem(DEFAULT_ACTIVITE)));

        // Check, that the count call also returns 1
        restDossierMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDossierShouldNotBeFound(String filter) throws Exception {
        restDossierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDossierMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDossier() throws Exception {
        // Get the dossier
        restDossierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDossier() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();

        // Update the dossier
        Dossier updatedDossier = dossierRepository.findById(dossier.getId()).get();
        // Disconnect from session so that the updates on updatedDossier are not directly saved in db
        em.detach(updatedDossier);
        updatedDossier
            .numero(UPDATED_NUMERO)
            .valeurBatie(UPDATED_VALEUR_BATIE)
            .valeurVenale(UPDATED_VALEUR_VENALE)
            .valeurLocativ(UPDATED_VALEUR_LOCATIV)
            .activite(UPDATED_ACTIVITE);
        DossierDTO dossierDTO = dossierMapper.toDto(updatedDossier);

        restDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dossierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dossierDTO))
            )
            .andExpect(status().isOk());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
        Dossier testDossier = dossierList.get(dossierList.size() - 1);
        assertThat(testDossier.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDossier.getValeurBatie()).isEqualTo(UPDATED_VALEUR_BATIE);
        assertThat(testDossier.getValeurVenale()).isEqualTo(UPDATED_VALEUR_VENALE);
        assertThat(testDossier.getValeurLocativ()).isEqualTo(UPDATED_VALEUR_LOCATIV);
        assertThat(testDossier.getActivite()).isEqualTo(UPDATED_ACTIVITE);
    }

    @Test
    @Transactional
    void putNonExistingDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // Create the Dossier
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dossierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dossierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // Create the Dossier
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dossierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // Create the Dossier
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDossierWithPatch() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();

        // Update the dossier using partial update
        Dossier partialUpdatedDossier = new Dossier();
        partialUpdatedDossier.setId(dossier.getId());

        partialUpdatedDossier.numero(UPDATED_NUMERO).valeurLocativ(UPDATED_VALEUR_LOCATIV);

        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDossier))
            )
            .andExpect(status().isOk());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
        Dossier testDossier = dossierList.get(dossierList.size() - 1);
        assertThat(testDossier.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDossier.getValeurBatie()).isEqualTo(DEFAULT_VALEUR_BATIE);
        assertThat(testDossier.getValeurVenale()).isEqualTo(DEFAULT_VALEUR_VENALE);
        assertThat(testDossier.getValeurLocativ()).isEqualTo(UPDATED_VALEUR_LOCATIV);
        assertThat(testDossier.getActivite()).isEqualTo(DEFAULT_ACTIVITE);
    }

    @Test
    @Transactional
    void fullUpdateDossierWithPatch() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();

        // Update the dossier using partial update
        Dossier partialUpdatedDossier = new Dossier();
        partialUpdatedDossier.setId(dossier.getId());

        partialUpdatedDossier
            .numero(UPDATED_NUMERO)
            .valeurBatie(UPDATED_VALEUR_BATIE)
            .valeurVenale(UPDATED_VALEUR_VENALE)
            .valeurLocativ(UPDATED_VALEUR_LOCATIV)
            .activite(UPDATED_ACTIVITE);

        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDossier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDossier))
            )
            .andExpect(status().isOk());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
        Dossier testDossier = dossierList.get(dossierList.size() - 1);
        assertThat(testDossier.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDossier.getValeurBatie()).isEqualTo(UPDATED_VALEUR_BATIE);
        assertThat(testDossier.getValeurVenale()).isEqualTo(UPDATED_VALEUR_VENALE);
        assertThat(testDossier.getValeurLocativ()).isEqualTo(UPDATED_VALEUR_LOCATIV);
        assertThat(testDossier.getActivite()).isEqualTo(UPDATED_ACTIVITE);
    }

    @Test
    @Transactional
    void patchNonExistingDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // Create the Dossier
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dossierDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dossierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // Create the Dossier
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dossierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDossier() throws Exception {
        int databaseSizeBeforeUpdate = dossierRepository.findAll().size();
        dossier.setId(count.incrementAndGet());

        // Create the Dossier
        DossierDTO dossierDTO = dossierMapper.toDto(dossier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dossierDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dossier in the database
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDossier() throws Exception {
        // Initialize the database
        dossierRepository.saveAndFlush(dossier);

        int databaseSizeBeforeDelete = dossierRepository.findAll().size();

        // Delete the dossier
        restDossierMockMvc
            .perform(delete(ENTITY_API_URL_ID, dossier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dossier> dossierList = dossierRepository.findAll();
        assertThat(dossierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
