package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.Representant;
import com.pirtol.lab.domain.enumeration.TypeStructure;
import com.pirtol.lab.repository.RepresentantRepository;
import com.pirtol.lab.service.dto.RepresentantDTO;
import com.pirtol.lab.service.mapper.RepresentantMapper;
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
 * Integration tests for the {@link RepresentantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RepresentantResourceIT {

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_PROPRIETAIRE = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_PROPRIETAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIF = false;
    private static final Boolean UPDATED_ACTIF = true;

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

    private static final String DEFAULT_STATUT_PERSONE_STRUCTURE = "AAAAAAAAAA";
    private static final String UPDATED_STATUT_PERSONE_STRUCTURE = "BBBBBBBBBB";

    private static final TypeStructure DEFAULT_TYPE_STRUCTURE = TypeStructure.ENTREPRISE_INDIVIDUEL;
    private static final TypeStructure UPDATED_TYPE_STRUCTURE = TypeStructure.SURL;

    private static final String ENTITY_API_URL = "/api/representants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RepresentantRepository representantRepository;

    @Autowired
    private RepresentantMapper representantMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRepresentantMockMvc;

    private Representant representant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Representant createEntity(EntityManager em) {
        Representant representant = new Representant()
            .prenom(DEFAULT_PRENOM)
            .lienProprietaire(DEFAULT_LIEN_PROPRIETAIRE)
            .nom(DEFAULT_NOM)
            .actif(DEFAULT_ACTIF)
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
            .statutPersoneStructure(DEFAULT_STATUT_PERSONE_STRUCTURE)
            .typeStructure(DEFAULT_TYPE_STRUCTURE);
        return representant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Representant createUpdatedEntity(EntityManager em) {
        Representant representant = new Representant()
            .prenom(UPDATED_PRENOM)
            .lienProprietaire(UPDATED_LIEN_PROPRIETAIRE)
            .nom(UPDATED_NOM)
            .actif(UPDATED_ACTIF)
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
            .statutPersoneStructure(UPDATED_STATUT_PERSONE_STRUCTURE)
            .typeStructure(UPDATED_TYPE_STRUCTURE);
        return representant;
    }

    @BeforeEach
    public void initTest() {
        representant = createEntity(em);
    }

    @Test
    @Transactional
    void createRepresentant() throws Exception {
        int databaseSizeBeforeCreate = representantRepository.findAll().size();
        // Create the Representant
        RepresentantDTO representantDTO = representantMapper.toDto(representant);
        restRepresentantMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(representantDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeCreate + 1);
        Representant testRepresentant = representantList.get(representantList.size() - 1);
        assertThat(testRepresentant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testRepresentant.getLienProprietaire()).isEqualTo(DEFAULT_LIEN_PROPRIETAIRE);
        assertThat(testRepresentant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRepresentant.getActif()).isEqualTo(DEFAULT_ACTIF);
        assertThat(testRepresentant.getRaisonSocial()).isEqualTo(DEFAULT_RAISON_SOCIAL);
        assertThat(testRepresentant.getSiegeSocial()).isEqualTo(DEFAULT_SIEGE_SOCIAL);
        assertThat(testRepresentant.getPersonneMorale()).isEqualTo(DEFAULT_PERSONNE_MORALE);
        assertThat(testRepresentant.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testRepresentant.getLieuNaissance()).isEqualTo(DEFAULT_LIEU_NAISSANCE);
        assertThat(testRepresentant.getNumCNI()).isEqualTo(DEFAULT_NUM_CNI);
        assertThat(testRepresentant.getNinea()).isEqualTo(DEFAULT_NINEA);
        assertThat(testRepresentant.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testRepresentant.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRepresentant.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testRepresentant.getTelephone2()).isEqualTo(DEFAULT_TELEPHONE_2);
        assertThat(testRepresentant.getTelephone3()).isEqualTo(DEFAULT_TELEPHONE_3);
        assertThat(testRepresentant.getStatutPersoneStructure()).isEqualTo(DEFAULT_STATUT_PERSONE_STRUCTURE);
        assertThat(testRepresentant.getTypeStructure()).isEqualTo(DEFAULT_TYPE_STRUCTURE);
    }

    @Test
    @Transactional
    void createRepresentantWithExistingId() throws Exception {
        // Create the Representant with an existing ID
        representant.setId(1L);
        RepresentantDTO representantDTO = representantMapper.toDto(representant);

        int databaseSizeBeforeCreate = representantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRepresentantMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(representantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRepresentants() throws Exception {
        // Initialize the database
        representantRepository.saveAndFlush(representant);

        // Get all the representantList
        restRepresentantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(representant.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].lienProprietaire").value(hasItem(DEFAULT_LIEN_PROPRIETAIRE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].actif").value(hasItem(DEFAULT_ACTIF.booleanValue())))
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
            .andExpect(jsonPath("$.[*].statutPersoneStructure").value(hasItem(DEFAULT_STATUT_PERSONE_STRUCTURE)))
            .andExpect(jsonPath("$.[*].typeStructure").value(hasItem(DEFAULT_TYPE_STRUCTURE.toString())));
    }

    @Test
    @Transactional
    void getRepresentant() throws Exception {
        // Initialize the database
        representantRepository.saveAndFlush(representant);

        // Get the representant
        restRepresentantMockMvc
            .perform(get(ENTITY_API_URL_ID, representant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(representant.getId().intValue()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.lienProprietaire").value(DEFAULT_LIEN_PROPRIETAIRE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.actif").value(DEFAULT_ACTIF.booleanValue()))
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
            .andExpect(jsonPath("$.statutPersoneStructure").value(DEFAULT_STATUT_PERSONE_STRUCTURE))
            .andExpect(jsonPath("$.typeStructure").value(DEFAULT_TYPE_STRUCTURE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRepresentant() throws Exception {
        // Get the representant
        restRepresentantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRepresentant() throws Exception {
        // Initialize the database
        representantRepository.saveAndFlush(representant);

        int databaseSizeBeforeUpdate = representantRepository.findAll().size();

        // Update the representant
        Representant updatedRepresentant = representantRepository.findById(representant.getId()).get();
        // Disconnect from session so that the updates on updatedRepresentant are not directly saved in db
        em.detach(updatedRepresentant);
        updatedRepresentant
            .prenom(UPDATED_PRENOM)
            .lienProprietaire(UPDATED_LIEN_PROPRIETAIRE)
            .nom(UPDATED_NOM)
            .actif(UPDATED_ACTIF)
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
            .statutPersoneStructure(UPDATED_STATUT_PERSONE_STRUCTURE)
            .typeStructure(UPDATED_TYPE_STRUCTURE);
        RepresentantDTO representantDTO = representantMapper.toDto(updatedRepresentant);

        restRepresentantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, representantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(representantDTO))
            )
            .andExpect(status().isOk());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
        Representant testRepresentant = representantList.get(representantList.size() - 1);
        assertThat(testRepresentant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testRepresentant.getLienProprietaire()).isEqualTo(UPDATED_LIEN_PROPRIETAIRE);
        assertThat(testRepresentant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRepresentant.getActif()).isEqualTo(UPDATED_ACTIF);
        assertThat(testRepresentant.getRaisonSocial()).isEqualTo(UPDATED_RAISON_SOCIAL);
        assertThat(testRepresentant.getSiegeSocial()).isEqualTo(UPDATED_SIEGE_SOCIAL);
        assertThat(testRepresentant.getPersonneMorale()).isEqualTo(UPDATED_PERSONNE_MORALE);
        assertThat(testRepresentant.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testRepresentant.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testRepresentant.getNumCNI()).isEqualTo(UPDATED_NUM_CNI);
        assertThat(testRepresentant.getNinea()).isEqualTo(UPDATED_NINEA);
        assertThat(testRepresentant.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testRepresentant.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRepresentant.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testRepresentant.getTelephone2()).isEqualTo(UPDATED_TELEPHONE_2);
        assertThat(testRepresentant.getTelephone3()).isEqualTo(UPDATED_TELEPHONE_3);
        assertThat(testRepresentant.getStatutPersoneStructure()).isEqualTo(UPDATED_STATUT_PERSONE_STRUCTURE);
        assertThat(testRepresentant.getTypeStructure()).isEqualTo(UPDATED_TYPE_STRUCTURE);
    }

    @Test
    @Transactional
    void putNonExistingRepresentant() throws Exception {
        int databaseSizeBeforeUpdate = representantRepository.findAll().size();
        representant.setId(count.incrementAndGet());

        // Create the Representant
        RepresentantDTO representantDTO = representantMapper.toDto(representant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepresentantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, representantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(representantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRepresentant() throws Exception {
        int databaseSizeBeforeUpdate = representantRepository.findAll().size();
        representant.setId(count.incrementAndGet());

        // Create the Representant
        RepresentantDTO representantDTO = representantMapper.toDto(representant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(representantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRepresentant() throws Exception {
        int databaseSizeBeforeUpdate = representantRepository.findAll().size();
        representant.setId(count.incrementAndGet());

        // Create the Representant
        RepresentantDTO representantDTO = representantMapper.toDto(representant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentantMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(representantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRepresentantWithPatch() throws Exception {
        // Initialize the database
        representantRepository.saveAndFlush(representant);

        int databaseSizeBeforeUpdate = representantRepository.findAll().size();

        // Update the representant using partial update
        Representant partialUpdatedRepresentant = new Representant();
        partialUpdatedRepresentant.setId(representant.getId());

        partialUpdatedRepresentant
            .lienProprietaire(UPDATED_LIEN_PROPRIETAIRE)
            .actif(UPDATED_ACTIF)
            .raisonSocial(UPDATED_RAISON_SOCIAL)
            .siegeSocial(UPDATED_SIEGE_SOCIAL)
            .personneMorale(UPDATED_PERSONNE_MORALE)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .numCNI(UPDATED_NUM_CNI)
            .adresse(UPDATED_ADRESSE)
            .telephone3(UPDATED_TELEPHONE_3);

        restRepresentantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRepresentant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRepresentant))
            )
            .andExpect(status().isOk());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
        Representant testRepresentant = representantList.get(representantList.size() - 1);
        assertThat(testRepresentant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testRepresentant.getLienProprietaire()).isEqualTo(UPDATED_LIEN_PROPRIETAIRE);
        assertThat(testRepresentant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRepresentant.getActif()).isEqualTo(UPDATED_ACTIF);
        assertThat(testRepresentant.getRaisonSocial()).isEqualTo(UPDATED_RAISON_SOCIAL);
        assertThat(testRepresentant.getSiegeSocial()).isEqualTo(UPDATED_SIEGE_SOCIAL);
        assertThat(testRepresentant.getPersonneMorale()).isEqualTo(UPDATED_PERSONNE_MORALE);
        assertThat(testRepresentant.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testRepresentant.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testRepresentant.getNumCNI()).isEqualTo(UPDATED_NUM_CNI);
        assertThat(testRepresentant.getNinea()).isEqualTo(DEFAULT_NINEA);
        assertThat(testRepresentant.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testRepresentant.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRepresentant.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testRepresentant.getTelephone2()).isEqualTo(DEFAULT_TELEPHONE_2);
        assertThat(testRepresentant.getTelephone3()).isEqualTo(UPDATED_TELEPHONE_3);
        assertThat(testRepresentant.getStatutPersoneStructure()).isEqualTo(DEFAULT_STATUT_PERSONE_STRUCTURE);
        assertThat(testRepresentant.getTypeStructure()).isEqualTo(DEFAULT_TYPE_STRUCTURE);
    }

    @Test
    @Transactional
    void fullUpdateRepresentantWithPatch() throws Exception {
        // Initialize the database
        representantRepository.saveAndFlush(representant);

        int databaseSizeBeforeUpdate = representantRepository.findAll().size();

        // Update the representant using partial update
        Representant partialUpdatedRepresentant = new Representant();
        partialUpdatedRepresentant.setId(representant.getId());

        partialUpdatedRepresentant
            .prenom(UPDATED_PRENOM)
            .lienProprietaire(UPDATED_LIEN_PROPRIETAIRE)
            .nom(UPDATED_NOM)
            .actif(UPDATED_ACTIF)
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
            .statutPersoneStructure(UPDATED_STATUT_PERSONE_STRUCTURE)
            .typeStructure(UPDATED_TYPE_STRUCTURE);

        restRepresentantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRepresentant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRepresentant))
            )
            .andExpect(status().isOk());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
        Representant testRepresentant = representantList.get(representantList.size() - 1);
        assertThat(testRepresentant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testRepresentant.getLienProprietaire()).isEqualTo(UPDATED_LIEN_PROPRIETAIRE);
        assertThat(testRepresentant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRepresentant.getActif()).isEqualTo(UPDATED_ACTIF);
        assertThat(testRepresentant.getRaisonSocial()).isEqualTo(UPDATED_RAISON_SOCIAL);
        assertThat(testRepresentant.getSiegeSocial()).isEqualTo(UPDATED_SIEGE_SOCIAL);
        assertThat(testRepresentant.getPersonneMorale()).isEqualTo(UPDATED_PERSONNE_MORALE);
        assertThat(testRepresentant.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testRepresentant.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testRepresentant.getNumCNI()).isEqualTo(UPDATED_NUM_CNI);
        assertThat(testRepresentant.getNinea()).isEqualTo(UPDATED_NINEA);
        assertThat(testRepresentant.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testRepresentant.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRepresentant.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testRepresentant.getTelephone2()).isEqualTo(UPDATED_TELEPHONE_2);
        assertThat(testRepresentant.getTelephone3()).isEqualTo(UPDATED_TELEPHONE_3);
        assertThat(testRepresentant.getStatutPersoneStructure()).isEqualTo(UPDATED_STATUT_PERSONE_STRUCTURE);
        assertThat(testRepresentant.getTypeStructure()).isEqualTo(UPDATED_TYPE_STRUCTURE);
    }

    @Test
    @Transactional
    void patchNonExistingRepresentant() throws Exception {
        int databaseSizeBeforeUpdate = representantRepository.findAll().size();
        representant.setId(count.incrementAndGet());

        // Create the Representant
        RepresentantDTO representantDTO = representantMapper.toDto(representant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepresentantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, representantDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(representantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRepresentant() throws Exception {
        int databaseSizeBeforeUpdate = representantRepository.findAll().size();
        representant.setId(count.incrementAndGet());

        // Create the Representant
        RepresentantDTO representantDTO = representantMapper.toDto(representant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(representantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRepresentant() throws Exception {
        int databaseSizeBeforeUpdate = representantRepository.findAll().size();
        representant.setId(count.incrementAndGet());

        // Create the Representant
        RepresentantDTO representantDTO = representantMapper.toDto(representant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentantMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(representantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRepresentant() throws Exception {
        // Initialize the database
        representantRepository.saveAndFlush(representant);

        int databaseSizeBeforeDelete = representantRepository.findAll().size();

        // Delete the representant
        restRepresentantMockMvc
            .perform(delete(ENTITY_API_URL_ID, representant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
