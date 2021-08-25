package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.Proprietaire;
import com.pirtol.lab.domain.enumeration.SituationProprietaire;
import com.pirtol.lab.domain.enumeration.TypeStructure;
import com.pirtol.lab.repository.ProprietaireRepository;
import com.pirtol.lab.service.dto.ProprietaireDTO;
import com.pirtol.lab.service.mapper.ProprietaireMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ProprietaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProprietaireResourceIT {

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final SituationProprietaire DEFAULT_SITUATION = SituationProprietaire.RESIDENT;
    private static final SituationProprietaire UPDATED_SITUATION = SituationProprietaire.DECEDE;

    private static final Boolean DEFAULT_GESTION_DELEGUE = false;
    private static final Boolean UPDATED_GESTION_DELEGUE = true;

    private static final String DEFAULT_RAISON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAISON_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_SIEGE_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_SIEGE_SOCIAL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PERSONNE_MORALE = false;
    private static final Boolean UPDATED_PERSONNE_MORALE = true;

    private static final Instant DEFAULT_DATE_NAISS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_NAISS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LIEU_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISSANCE = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_CNI = "AAAAAAAAAA";
    private static final String UPDATED_NUM_CNI = "BBBBBBBBBB";

    private static final String DEFAULT_NINEA = "AAAAAAAAAA";
    private static final String UPDATED_NINEA = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_3 = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_AQUISITION = "AAAAAAAAAA";
    private static final String UPDATED_AQUISITION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT_PERSONE_STRUCTURE = "AAAAAAAAAA";
    private static final String UPDATED_STATUT_PERSONE_STRUCTURE = "BBBBBBBBBB";

    private static final TypeStructure DEFAULT_TYPE_STRUCTURE = TypeStructure.ENTREPRISE_INDIVIDUEL;
    private static final TypeStructure UPDATED_TYPE_STRUCTURE = TypeStructure.SURL;

    private static final Integer DEFAULT_NOMBRE_HERITIERS = 1;
    private static final Integer UPDATED_NOMBRE_HERITIERS = 2;

    private static final String DEFAULT_SERVICE_OCUPANT = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_OCUPANT = "BBBBBBBBBB";

    private static final String DEFAULT_ETABLSSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_ETABLSSEMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/proprietaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProprietaireRepository proprietaireRepository;

    @Autowired
    private ProprietaireMapper proprietaireMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProprietaireMockMvc;

    private Proprietaire proprietaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proprietaire createEntity(EntityManager em) {
        Proprietaire proprietaire = new Proprietaire()
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .situation(DEFAULT_SITUATION)
            .gestionDelegue(DEFAULT_GESTION_DELEGUE)
            .raisonSocial(DEFAULT_RAISON_SOCIAL)
            .siegeSocial(DEFAULT_SIEGE_SOCIAL)
            .personneMorale(DEFAULT_PERSONNE_MORALE)
            .dateNaiss(DEFAULT_DATE_NAISS)
            .lieuNaissance(DEFAULT_LIEU_NAISSANCE)
            .numCNI(DEFAULT_NUM_CNI)
            .ninea(DEFAULT_NINEA)
            .adresse(DEFAULT_ADRESSE)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .telephone2(DEFAULT_TELEPHONE_2)
            .telephone3(DEFAULT_TELEPHONE_3)
            .aquisition(DEFAULT_AQUISITION)
            .statutPersoneStructure(DEFAULT_STATUT_PERSONE_STRUCTURE)
            .typeStructure(DEFAULT_TYPE_STRUCTURE)
            .nombreHeritiers(DEFAULT_NOMBRE_HERITIERS)
            .serviceOcupant(DEFAULT_SERVICE_OCUPANT)
            .etablssement(DEFAULT_ETABLSSEMENT);
        return proprietaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proprietaire createUpdatedEntity(EntityManager em) {
        Proprietaire proprietaire = new Proprietaire()
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .situation(UPDATED_SITUATION)
            .gestionDelegue(UPDATED_GESTION_DELEGUE)
            .raisonSocial(UPDATED_RAISON_SOCIAL)
            .siegeSocial(UPDATED_SIEGE_SOCIAL)
            .personneMorale(UPDATED_PERSONNE_MORALE)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .numCNI(UPDATED_NUM_CNI)
            .ninea(UPDATED_NINEA)
            .adresse(UPDATED_ADRESSE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .telephone2(UPDATED_TELEPHONE_2)
            .telephone3(UPDATED_TELEPHONE_3)
            .aquisition(UPDATED_AQUISITION)
            .statutPersoneStructure(UPDATED_STATUT_PERSONE_STRUCTURE)
            .typeStructure(UPDATED_TYPE_STRUCTURE)
            .nombreHeritiers(UPDATED_NOMBRE_HERITIERS)
            .serviceOcupant(UPDATED_SERVICE_OCUPANT)
            .etablssement(UPDATED_ETABLSSEMENT);
        return proprietaire;
    }

    @BeforeEach
    public void initTest() {
        proprietaire = createEntity(em);
    }

    @Test
    @Transactional
    void createProprietaire() throws Exception {
        int databaseSizeBeforeCreate = proprietaireRepository.findAll().size();
        // Create the Proprietaire
        ProprietaireDTO proprietaireDTO = proprietaireMapper.toDto(proprietaire);
        restProprietaireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proprietaireDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Proprietaire in the database
        List<Proprietaire> proprietaireList = proprietaireRepository.findAll();
        assertThat(proprietaireList).hasSize(databaseSizeBeforeCreate + 1);
        Proprietaire testProprietaire = proprietaireList.get(proprietaireList.size() - 1);
        assertThat(testProprietaire.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testProprietaire.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProprietaire.getSituation()).isEqualTo(DEFAULT_SITUATION);
        assertThat(testProprietaire.getGestionDelegue()).isEqualTo(DEFAULT_GESTION_DELEGUE);
        assertThat(testProprietaire.getRaisonSocial()).isEqualTo(DEFAULT_RAISON_SOCIAL);
        assertThat(testProprietaire.getSiegeSocial()).isEqualTo(DEFAULT_SIEGE_SOCIAL);
        assertThat(testProprietaire.getPersonneMorale()).isEqualTo(DEFAULT_PERSONNE_MORALE);
        assertThat(testProprietaire.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testProprietaire.getLieuNaissance()).isEqualTo(DEFAULT_LIEU_NAISSANCE);
        assertThat(testProprietaire.getNumCNI()).isEqualTo(DEFAULT_NUM_CNI);
        assertThat(testProprietaire.getNinea()).isEqualTo(DEFAULT_NINEA);
        assertThat(testProprietaire.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testProprietaire.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProprietaire.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testProprietaire.getTelephone2()).isEqualTo(DEFAULT_TELEPHONE_2);
        assertThat(testProprietaire.getTelephone3()).isEqualTo(DEFAULT_TELEPHONE_3);
        assertThat(testProprietaire.getAquisition()).isEqualTo(DEFAULT_AQUISITION);
        assertThat(testProprietaire.getStatutPersoneStructure()).isEqualTo(DEFAULT_STATUT_PERSONE_STRUCTURE);
        assertThat(testProprietaire.getTypeStructure()).isEqualTo(DEFAULT_TYPE_STRUCTURE);
        assertThat(testProprietaire.getNombreHeritiers()).isEqualTo(DEFAULT_NOMBRE_HERITIERS);
        assertThat(testProprietaire.getServiceOcupant()).isEqualTo(DEFAULT_SERVICE_OCUPANT);
        assertThat(testProprietaire.getEtablssement()).isEqualTo(DEFAULT_ETABLSSEMENT);
    }

    @Test
    @Transactional
    void createProprietaireWithExistingId() throws Exception {
        // Create the Proprietaire with an existing ID
        proprietaire.setId(1L);
        ProprietaireDTO proprietaireDTO = proprietaireMapper.toDto(proprietaire);

        int databaseSizeBeforeCreate = proprietaireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProprietaireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proprietaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proprietaire in the database
        List<Proprietaire> proprietaireList = proprietaireRepository.findAll();
        assertThat(proprietaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProprietaires() throws Exception {
        // Initialize the database
        proprietaireRepository.saveAndFlush(proprietaire);

        // Get all the proprietaireList
        restProprietaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proprietaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].situation").value(hasItem(DEFAULT_SITUATION.toString())))
            .andExpect(jsonPath("$.[*].gestionDelegue").value(hasItem(DEFAULT_GESTION_DELEGUE.booleanValue())))
            .andExpect(jsonPath("$.[*].raisonSocial").value(hasItem(DEFAULT_RAISON_SOCIAL)))
            .andExpect(jsonPath("$.[*].siegeSocial").value(hasItem(DEFAULT_SIEGE_SOCIAL)))
            .andExpect(jsonPath("$.[*].personneMorale").value(hasItem(DEFAULT_PERSONNE_MORALE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateNaiss").value(hasItem(DEFAULT_DATE_NAISS.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE)))
            .andExpect(jsonPath("$.[*].numCNI").value(hasItem(DEFAULT_NUM_CNI)))
            .andExpect(jsonPath("$.[*].ninea").value(hasItem(DEFAULT_NINEA)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].telephone2").value(hasItem(DEFAULT_TELEPHONE_2)))
            .andExpect(jsonPath("$.[*].telephone3").value(hasItem(DEFAULT_TELEPHONE_3)))
            .andExpect(jsonPath("$.[*].aquisition").value(hasItem(DEFAULT_AQUISITION)))
            .andExpect(jsonPath("$.[*].statutPersoneStructure").value(hasItem(DEFAULT_STATUT_PERSONE_STRUCTURE)))
            .andExpect(jsonPath("$.[*].typeStructure").value(hasItem(DEFAULT_TYPE_STRUCTURE.toString())))
            .andExpect(jsonPath("$.[*].nombreHeritiers").value(hasItem(DEFAULT_NOMBRE_HERITIERS)))
            .andExpect(jsonPath("$.[*].serviceOcupant").value(hasItem(DEFAULT_SERVICE_OCUPANT)))
            .andExpect(jsonPath("$.[*].etablssement").value(hasItem(DEFAULT_ETABLSSEMENT)));
    }

    @Test
    @Transactional
    void getProprietaire() throws Exception {
        // Initialize the database
        proprietaireRepository.saveAndFlush(proprietaire);

        // Get the proprietaire
        restProprietaireMockMvc
            .perform(get(ENTITY_API_URL_ID, proprietaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proprietaire.getId().intValue()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.situation").value(DEFAULT_SITUATION.toString()))
            .andExpect(jsonPath("$.gestionDelegue").value(DEFAULT_GESTION_DELEGUE.booleanValue()))
            .andExpect(jsonPath("$.raisonSocial").value(DEFAULT_RAISON_SOCIAL))
            .andExpect(jsonPath("$.siegeSocial").value(DEFAULT_SIEGE_SOCIAL))
            .andExpect(jsonPath("$.personneMorale").value(DEFAULT_PERSONNE_MORALE.booleanValue()))
            .andExpect(jsonPath("$.dateNaiss").value(DEFAULT_DATE_NAISS.toString()))
            .andExpect(jsonPath("$.lieuNaissance").value(DEFAULT_LIEU_NAISSANCE))
            .andExpect(jsonPath("$.numCNI").value(DEFAULT_NUM_CNI))
            .andExpect(jsonPath("$.ninea").value(DEFAULT_NINEA))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.telephone2").value(DEFAULT_TELEPHONE_2))
            .andExpect(jsonPath("$.telephone3").value(DEFAULT_TELEPHONE_3))
            .andExpect(jsonPath("$.aquisition").value(DEFAULT_AQUISITION))
            .andExpect(jsonPath("$.statutPersoneStructure").value(DEFAULT_STATUT_PERSONE_STRUCTURE))
            .andExpect(jsonPath("$.typeStructure").value(DEFAULT_TYPE_STRUCTURE.toString()))
            .andExpect(jsonPath("$.nombreHeritiers").value(DEFAULT_NOMBRE_HERITIERS))
            .andExpect(jsonPath("$.serviceOcupant").value(DEFAULT_SERVICE_OCUPANT))
            .andExpect(jsonPath("$.etablssement").value(DEFAULT_ETABLSSEMENT));
    }

    @Test
    @Transactional
    void getNonExistingProprietaire() throws Exception {
        // Get the proprietaire
        restProprietaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProprietaire() throws Exception {
        // Initialize the database
        proprietaireRepository.saveAndFlush(proprietaire);

        int databaseSizeBeforeUpdate = proprietaireRepository.findAll().size();

        // Update the proprietaire
        Proprietaire updatedProprietaire = proprietaireRepository.findById(proprietaire.getId()).get();
        // Disconnect from session so that the updates on updatedProprietaire are not directly saved in db
        em.detach(updatedProprietaire);
        updatedProprietaire
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .situation(UPDATED_SITUATION)
            .gestionDelegue(UPDATED_GESTION_DELEGUE)
            .raisonSocial(UPDATED_RAISON_SOCIAL)
            .siegeSocial(UPDATED_SIEGE_SOCIAL)
            .personneMorale(UPDATED_PERSONNE_MORALE)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .numCNI(UPDATED_NUM_CNI)
            .ninea(UPDATED_NINEA)
            .adresse(UPDATED_ADRESSE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .telephone2(UPDATED_TELEPHONE_2)
            .telephone3(UPDATED_TELEPHONE_3)
            .aquisition(UPDATED_AQUISITION)
            .statutPersoneStructure(UPDATED_STATUT_PERSONE_STRUCTURE)
            .typeStructure(UPDATED_TYPE_STRUCTURE)
            .nombreHeritiers(UPDATED_NOMBRE_HERITIERS)
            .serviceOcupant(UPDATED_SERVICE_OCUPANT)
            .etablssement(UPDATED_ETABLSSEMENT);
        ProprietaireDTO proprietaireDTO = proprietaireMapper.toDto(updatedProprietaire);

        restProprietaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proprietaireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(proprietaireDTO))
            )
            .andExpect(status().isOk());

        // Validate the Proprietaire in the database
        List<Proprietaire> proprietaireList = proprietaireRepository.findAll();
        assertThat(proprietaireList).hasSize(databaseSizeBeforeUpdate);
        Proprietaire testProprietaire = proprietaireList.get(proprietaireList.size() - 1);
        assertThat(testProprietaire.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testProprietaire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProprietaire.getSituation()).isEqualTo(UPDATED_SITUATION);
        assertThat(testProprietaire.getGestionDelegue()).isEqualTo(UPDATED_GESTION_DELEGUE);
        assertThat(testProprietaire.getRaisonSocial()).isEqualTo(UPDATED_RAISON_SOCIAL);
        assertThat(testProprietaire.getSiegeSocial()).isEqualTo(UPDATED_SIEGE_SOCIAL);
        assertThat(testProprietaire.getPersonneMorale()).isEqualTo(UPDATED_PERSONNE_MORALE);
        assertThat(testProprietaire.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testProprietaire.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testProprietaire.getNumCNI()).isEqualTo(UPDATED_NUM_CNI);
        assertThat(testProprietaire.getNinea()).isEqualTo(UPDATED_NINEA);
        assertThat(testProprietaire.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testProprietaire.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProprietaire.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testProprietaire.getTelephone2()).isEqualTo(UPDATED_TELEPHONE_2);
        assertThat(testProprietaire.getTelephone3()).isEqualTo(UPDATED_TELEPHONE_3);
        assertThat(testProprietaire.getAquisition()).isEqualTo(UPDATED_AQUISITION);
        assertThat(testProprietaire.getStatutPersoneStructure()).isEqualTo(UPDATED_STATUT_PERSONE_STRUCTURE);
        assertThat(testProprietaire.getTypeStructure()).isEqualTo(UPDATED_TYPE_STRUCTURE);
        assertThat(testProprietaire.getNombreHeritiers()).isEqualTo(UPDATED_NOMBRE_HERITIERS);
        assertThat(testProprietaire.getServiceOcupant()).isEqualTo(UPDATED_SERVICE_OCUPANT);
        assertThat(testProprietaire.getEtablssement()).isEqualTo(UPDATED_ETABLSSEMENT);
    }

    @Test
    @Transactional
    void putNonExistingProprietaire() throws Exception {
        int databaseSizeBeforeUpdate = proprietaireRepository.findAll().size();
        proprietaire.setId(count.incrementAndGet());

        // Create the Proprietaire
        ProprietaireDTO proprietaireDTO = proprietaireMapper.toDto(proprietaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProprietaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proprietaireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(proprietaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proprietaire in the database
        List<Proprietaire> proprietaireList = proprietaireRepository.findAll();
        assertThat(proprietaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProprietaire() throws Exception {
        int databaseSizeBeforeUpdate = proprietaireRepository.findAll().size();
        proprietaire.setId(count.incrementAndGet());

        // Create the Proprietaire
        ProprietaireDTO proprietaireDTO = proprietaireMapper.toDto(proprietaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProprietaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(proprietaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proprietaire in the database
        List<Proprietaire> proprietaireList = proprietaireRepository.findAll();
        assertThat(proprietaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProprietaire() throws Exception {
        int databaseSizeBeforeUpdate = proprietaireRepository.findAll().size();
        proprietaire.setId(count.incrementAndGet());

        // Create the Proprietaire
        ProprietaireDTO proprietaireDTO = proprietaireMapper.toDto(proprietaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProprietaireMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proprietaireDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Proprietaire in the database
        List<Proprietaire> proprietaireList = proprietaireRepository.findAll();
        assertThat(proprietaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProprietaireWithPatch() throws Exception {
        // Initialize the database
        proprietaireRepository.saveAndFlush(proprietaire);

        int databaseSizeBeforeUpdate = proprietaireRepository.findAll().size();

        // Update the proprietaire using partial update
        Proprietaire partialUpdatedProprietaire = new Proprietaire();
        partialUpdatedProprietaire.setId(proprietaire.getId());

        partialUpdatedProprietaire
            .nom(UPDATED_NOM)
            .situation(UPDATED_SITUATION)
            .raisonSocial(UPDATED_RAISON_SOCIAL)
            .personneMorale(UPDATED_PERSONNE_MORALE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .numCNI(UPDATED_NUM_CNI)
            .adresse(UPDATED_ADRESSE)
            .telephone2(UPDATED_TELEPHONE_2)
            .telephone3(UPDATED_TELEPHONE_3)
            .typeStructure(UPDATED_TYPE_STRUCTURE)
            .nombreHeritiers(UPDATED_NOMBRE_HERITIERS)
            .serviceOcupant(UPDATED_SERVICE_OCUPANT);

        restProprietaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProprietaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProprietaire))
            )
            .andExpect(status().isOk());

        // Validate the Proprietaire in the database
        List<Proprietaire> proprietaireList = proprietaireRepository.findAll();
        assertThat(proprietaireList).hasSize(databaseSizeBeforeUpdate);
        Proprietaire testProprietaire = proprietaireList.get(proprietaireList.size() - 1);
        assertThat(testProprietaire.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testProprietaire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProprietaire.getSituation()).isEqualTo(UPDATED_SITUATION);
        assertThat(testProprietaire.getGestionDelegue()).isEqualTo(DEFAULT_GESTION_DELEGUE);
        assertThat(testProprietaire.getRaisonSocial()).isEqualTo(UPDATED_RAISON_SOCIAL);
        assertThat(testProprietaire.getSiegeSocial()).isEqualTo(DEFAULT_SIEGE_SOCIAL);
        assertThat(testProprietaire.getPersonneMorale()).isEqualTo(UPDATED_PERSONNE_MORALE);
        assertThat(testProprietaire.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testProprietaire.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testProprietaire.getNumCNI()).isEqualTo(UPDATED_NUM_CNI);
        assertThat(testProprietaire.getNinea()).isEqualTo(DEFAULT_NINEA);
        assertThat(testProprietaire.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testProprietaire.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProprietaire.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testProprietaire.getTelephone2()).isEqualTo(UPDATED_TELEPHONE_2);
        assertThat(testProprietaire.getTelephone3()).isEqualTo(UPDATED_TELEPHONE_3);
        assertThat(testProprietaire.getAquisition()).isEqualTo(DEFAULT_AQUISITION);
        assertThat(testProprietaire.getStatutPersoneStructure()).isEqualTo(DEFAULT_STATUT_PERSONE_STRUCTURE);
        assertThat(testProprietaire.getTypeStructure()).isEqualTo(UPDATED_TYPE_STRUCTURE);
        assertThat(testProprietaire.getNombreHeritiers()).isEqualTo(UPDATED_NOMBRE_HERITIERS);
        assertThat(testProprietaire.getServiceOcupant()).isEqualTo(UPDATED_SERVICE_OCUPANT);
        assertThat(testProprietaire.getEtablssement()).isEqualTo(DEFAULT_ETABLSSEMENT);
    }

    @Test
    @Transactional
    void fullUpdateProprietaireWithPatch() throws Exception {
        // Initialize the database
        proprietaireRepository.saveAndFlush(proprietaire);

        int databaseSizeBeforeUpdate = proprietaireRepository.findAll().size();

        // Update the proprietaire using partial update
        Proprietaire partialUpdatedProprietaire = new Proprietaire();
        partialUpdatedProprietaire.setId(proprietaire.getId());

        partialUpdatedProprietaire
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .situation(UPDATED_SITUATION)
            .gestionDelegue(UPDATED_GESTION_DELEGUE)
            .raisonSocial(UPDATED_RAISON_SOCIAL)
            .siegeSocial(UPDATED_SIEGE_SOCIAL)
            .personneMorale(UPDATED_PERSONNE_MORALE)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .numCNI(UPDATED_NUM_CNI)
            .ninea(UPDATED_NINEA)
            .adresse(UPDATED_ADRESSE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .telephone2(UPDATED_TELEPHONE_2)
            .telephone3(UPDATED_TELEPHONE_3)
            .aquisition(UPDATED_AQUISITION)
            .statutPersoneStructure(UPDATED_STATUT_PERSONE_STRUCTURE)
            .typeStructure(UPDATED_TYPE_STRUCTURE)
            .nombreHeritiers(UPDATED_NOMBRE_HERITIERS)
            .serviceOcupant(UPDATED_SERVICE_OCUPANT)
            .etablssement(UPDATED_ETABLSSEMENT);

        restProprietaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProprietaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProprietaire))
            )
            .andExpect(status().isOk());

        // Validate the Proprietaire in the database
        List<Proprietaire> proprietaireList = proprietaireRepository.findAll();
        assertThat(proprietaireList).hasSize(databaseSizeBeforeUpdate);
        Proprietaire testProprietaire = proprietaireList.get(proprietaireList.size() - 1);
        assertThat(testProprietaire.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testProprietaire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProprietaire.getSituation()).isEqualTo(UPDATED_SITUATION);
        assertThat(testProprietaire.getGestionDelegue()).isEqualTo(UPDATED_GESTION_DELEGUE);
        assertThat(testProprietaire.getRaisonSocial()).isEqualTo(UPDATED_RAISON_SOCIAL);
        assertThat(testProprietaire.getSiegeSocial()).isEqualTo(UPDATED_SIEGE_SOCIAL);
        assertThat(testProprietaire.getPersonneMorale()).isEqualTo(UPDATED_PERSONNE_MORALE);
        assertThat(testProprietaire.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testProprietaire.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testProprietaire.getNumCNI()).isEqualTo(UPDATED_NUM_CNI);
        assertThat(testProprietaire.getNinea()).isEqualTo(UPDATED_NINEA);
        assertThat(testProprietaire.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testProprietaire.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProprietaire.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testProprietaire.getTelephone2()).isEqualTo(UPDATED_TELEPHONE_2);
        assertThat(testProprietaire.getTelephone3()).isEqualTo(UPDATED_TELEPHONE_3);
        assertThat(testProprietaire.getAquisition()).isEqualTo(UPDATED_AQUISITION);
        assertThat(testProprietaire.getStatutPersoneStructure()).isEqualTo(UPDATED_STATUT_PERSONE_STRUCTURE);
        assertThat(testProprietaire.getTypeStructure()).isEqualTo(UPDATED_TYPE_STRUCTURE);
        assertThat(testProprietaire.getNombreHeritiers()).isEqualTo(UPDATED_NOMBRE_HERITIERS);
        assertThat(testProprietaire.getServiceOcupant()).isEqualTo(UPDATED_SERVICE_OCUPANT);
        assertThat(testProprietaire.getEtablssement()).isEqualTo(UPDATED_ETABLSSEMENT);
    }

    @Test
    @Transactional
    void patchNonExistingProprietaire() throws Exception {
        int databaseSizeBeforeUpdate = proprietaireRepository.findAll().size();
        proprietaire.setId(count.incrementAndGet());

        // Create the Proprietaire
        ProprietaireDTO proprietaireDTO = proprietaireMapper.toDto(proprietaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProprietaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, proprietaireDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(proprietaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proprietaire in the database
        List<Proprietaire> proprietaireList = proprietaireRepository.findAll();
        assertThat(proprietaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProprietaire() throws Exception {
        int databaseSizeBeforeUpdate = proprietaireRepository.findAll().size();
        proprietaire.setId(count.incrementAndGet());

        // Create the Proprietaire
        ProprietaireDTO proprietaireDTO = proprietaireMapper.toDto(proprietaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProprietaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(proprietaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proprietaire in the database
        List<Proprietaire> proprietaireList = proprietaireRepository.findAll();
        assertThat(proprietaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProprietaire() throws Exception {
        int databaseSizeBeforeUpdate = proprietaireRepository.findAll().size();
        proprietaire.setId(count.incrementAndGet());

        // Create the Proprietaire
        ProprietaireDTO proprietaireDTO = proprietaireMapper.toDto(proprietaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProprietaireMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(proprietaireDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Proprietaire in the database
        List<Proprietaire> proprietaireList = proprietaireRepository.findAll();
        assertThat(proprietaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProprietaire() throws Exception {
        // Initialize the database
        proprietaireRepository.saveAndFlush(proprietaire);

        int databaseSizeBeforeDelete = proprietaireRepository.findAll().size();

        // Delete the proprietaire
        restProprietaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, proprietaire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Proprietaire> proprietaireList = proprietaireRepository.findAll();
        assertThat(proprietaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
