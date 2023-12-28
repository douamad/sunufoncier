package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.Refcadastrale;
import com.pirtol.lab.domain.enumeration.DependantDomaine;
import com.pirtol.lab.domain.enumeration.TypeBornage;
import com.pirtol.lab.repository.RefcadastraleRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RefcadastraleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RefcadastraleResourceIT {

    private static final String DEFAULT_CODE_SECTION = "AAAAAAAAAA";
    private static final String UPDATED_CODE_SECTION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_PARCELLE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PARCELLE = "BBBBBBBBBB";

    private static final String DEFAULT_NICAD = "AAAAAAAAAA";
    private static final String UPDATED_NICAD = "BBBBBBBBBB";

    private static final Double DEFAULT_SUPERFICI = 1D;
    private static final Double UPDATED_SUPERFICI = 2D;

    private static final String DEFAULT_TITRE_MERE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_MERE = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE_CREE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_CREE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_REQUISITION = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_REQUISITION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_BORNAGE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_BORNAGE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final DependantDomaine DEFAULT_DEPENDANT_DOMAINE = DependantDomaine.DONAIME_NATIONAL;
    private static final DependantDomaine UPDATED_DEPENDANT_DOMAINE = DependantDomaine.DOMAINE_PUBLIC;

    private static final String DEFAULT_NUMERO_DELIBERATION = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DELIBERATION = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_DELIBERATION = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DELIBERATION = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_GEOMETRE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_GEOMETRE = "BBBBBBBBBB";

    private static final TypeBornage DEFAULT_ISSUE_BORNAGE = TypeBornage.IMMATRICULATION;
    private static final TypeBornage UPDATED_ISSUE_BORNAGE = TypeBornage.MORCELLEMENT;

    private static final String ENTITY_API_URL = "/api/refcadastrales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RefcadastraleRepository refcadastraleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRefcadastraleMockMvc;

    private Refcadastrale refcadastrale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Refcadastrale createEntity(EntityManager em) {
        Refcadastrale refcadastrale = new Refcadastrale()
            .codeSection(DEFAULT_CODE_SECTION)
            .codeParcelle(DEFAULT_CODE_PARCELLE)
            .nicad(DEFAULT_NICAD)
            .superfici(DEFAULT_SUPERFICI)
            .titreMere(DEFAULT_TITRE_MERE)
            .titreCree(DEFAULT_TITRE_CREE)
            .numeroRequisition(DEFAULT_NUMERO_REQUISITION)
            .dateBornage(DEFAULT_DATE_BORNAGE)
            .dependantDomaine(DEFAULT_DEPENDANT_DOMAINE)
            .numeroDeliberation(DEFAULT_NUMERO_DELIBERATION)
            .dateDeliberation(DEFAULT_DATE_DELIBERATION)
            .nomGeometre(DEFAULT_NOM_GEOMETRE)
            .issueBornage(DEFAULT_ISSUE_BORNAGE);
        return refcadastrale;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Refcadastrale createUpdatedEntity(EntityManager em) {
        Refcadastrale refcadastrale = new Refcadastrale()
            .codeSection(UPDATED_CODE_SECTION)
            .codeParcelle(UPDATED_CODE_PARCELLE)
            .nicad(UPDATED_NICAD)
            .superfici(UPDATED_SUPERFICI)
            .titreMere(UPDATED_TITRE_MERE)
            .titreCree(UPDATED_TITRE_CREE)
            .numeroRequisition(UPDATED_NUMERO_REQUISITION)
            .dateBornage(UPDATED_DATE_BORNAGE)
            .dependantDomaine(UPDATED_DEPENDANT_DOMAINE)
            .numeroDeliberation(UPDATED_NUMERO_DELIBERATION)
            .dateDeliberation(UPDATED_DATE_DELIBERATION)
            .nomGeometre(UPDATED_NOM_GEOMETRE)
            .issueBornage(UPDATED_ISSUE_BORNAGE);
        return refcadastrale;
    }

    @BeforeEach
    public void initTest() {
        refcadastrale = createEntity(em);
    }

    @Test
    @Transactional
    void createRefcadastrale() throws Exception {
        int databaseSizeBeforeCreate = refcadastraleRepository.findAll().size();
        // Create the Refcadastrale
        restRefcadastraleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refcadastrale)))
            .andExpect(status().isCreated());

        // Validate the Refcadastrale in the database
        List<Refcadastrale> refcadastraleList = refcadastraleRepository.findAll();
        assertThat(refcadastraleList).hasSize(databaseSizeBeforeCreate + 1);
        Refcadastrale testRefcadastrale = refcadastraleList.get(refcadastraleList.size() - 1);
        assertThat(testRefcadastrale.getCodeSection()).isEqualTo(DEFAULT_CODE_SECTION);
        assertThat(testRefcadastrale.getCodeParcelle()).isEqualTo(DEFAULT_CODE_PARCELLE);
        assertThat(testRefcadastrale.getNicad()).isEqualTo(DEFAULT_NICAD);
        assertThat(testRefcadastrale.getSuperfici()).isEqualTo(DEFAULT_SUPERFICI);
        assertThat(testRefcadastrale.getTitreMere()).isEqualTo(DEFAULT_TITRE_MERE);
        assertThat(testRefcadastrale.getTitreCree()).isEqualTo(DEFAULT_TITRE_CREE);
        assertThat(testRefcadastrale.getNumeroRequisition()).isEqualTo(DEFAULT_NUMERO_REQUISITION);
        assertThat(testRefcadastrale.getDateBornage()).isEqualTo(DEFAULT_DATE_BORNAGE);
        assertThat(testRefcadastrale.getDependantDomaine()).isEqualTo(DEFAULT_DEPENDANT_DOMAINE);
        assertThat(testRefcadastrale.getNumeroDeliberation()).isEqualTo(DEFAULT_NUMERO_DELIBERATION);
        assertThat(testRefcadastrale.getDateDeliberation()).isEqualTo(DEFAULT_DATE_DELIBERATION);
        assertThat(testRefcadastrale.getNomGeometre()).isEqualTo(DEFAULT_NOM_GEOMETRE);
        assertThat(testRefcadastrale.getIssueBornage()).isEqualTo(DEFAULT_ISSUE_BORNAGE);
    }

    @Test
    @Transactional
    void createRefcadastraleWithExistingId() throws Exception {
        // Create the Refcadastrale with an existing ID
        refcadastrale.setId(1L);

        int databaseSizeBeforeCreate = refcadastraleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefcadastraleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refcadastrale)))
            .andExpect(status().isBadRequest());

        // Validate the Refcadastrale in the database
        List<Refcadastrale> refcadastraleList = refcadastraleRepository.findAll();
        assertThat(refcadastraleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRefcadastrales() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList
        restRefcadastraleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refcadastrale.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeSection").value(hasItem(DEFAULT_CODE_SECTION)))
            .andExpect(jsonPath("$.[*].codeParcelle").value(hasItem(DEFAULT_CODE_PARCELLE)))
            .andExpect(jsonPath("$.[*].nicad").value(hasItem(DEFAULT_NICAD)))
            .andExpect(jsonPath("$.[*].superfici").value(hasItem(DEFAULT_SUPERFICI.doubleValue())))
            .andExpect(jsonPath("$.[*].titreMere").value(hasItem(DEFAULT_TITRE_MERE)))
            .andExpect(jsonPath("$.[*].titreCree").value(hasItem(DEFAULT_TITRE_CREE)))
            .andExpect(jsonPath("$.[*].numeroRequisition").value(hasItem(DEFAULT_NUMERO_REQUISITION)))
            .andExpect(jsonPath("$.[*].dateBornage").value(hasItem(DEFAULT_DATE_BORNAGE.toString())))
            .andExpect(jsonPath("$.[*].dependantDomaine").value(hasItem(DEFAULT_DEPENDANT_DOMAINE.toString())))
            .andExpect(jsonPath("$.[*].numeroDeliberation").value(hasItem(DEFAULT_NUMERO_DELIBERATION)))
            .andExpect(jsonPath("$.[*].dateDeliberation").value(hasItem(DEFAULT_DATE_DELIBERATION)))
            .andExpect(jsonPath("$.[*].nomGeometre").value(hasItem(DEFAULT_NOM_GEOMETRE)))
            .andExpect(jsonPath("$.[*].issueBornage").value(hasItem(DEFAULT_ISSUE_BORNAGE.toString())));
    }

    @Test
    @Transactional
    void getRefcadastrale() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get the refcadastrale
        restRefcadastraleMockMvc
            .perform(get(ENTITY_API_URL_ID, refcadastrale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(refcadastrale.getId().intValue()))
            .andExpect(jsonPath("$.codeSection").value(DEFAULT_CODE_SECTION))
            .andExpect(jsonPath("$.codeParcelle").value(DEFAULT_CODE_PARCELLE))
            .andExpect(jsonPath("$.nicad").value(DEFAULT_NICAD))
            .andExpect(jsonPath("$.superfici").value(DEFAULT_SUPERFICI.doubleValue()))
            .andExpect(jsonPath("$.titreMere").value(DEFAULT_TITRE_MERE))
            .andExpect(jsonPath("$.titreCree").value(DEFAULT_TITRE_CREE))
            .andExpect(jsonPath("$.numeroRequisition").value(DEFAULT_NUMERO_REQUISITION))
            .andExpect(jsonPath("$.dateBornage").value(DEFAULT_DATE_BORNAGE.toString()))
            .andExpect(jsonPath("$.dependantDomaine").value(DEFAULT_DEPENDANT_DOMAINE.toString()))
            .andExpect(jsonPath("$.numeroDeliberation").value(DEFAULT_NUMERO_DELIBERATION))
            .andExpect(jsonPath("$.dateDeliberation").value(DEFAULT_DATE_DELIBERATION))
            .andExpect(jsonPath("$.nomGeometre").value(DEFAULT_NOM_GEOMETRE))
            .andExpect(jsonPath("$.issueBornage").value(DEFAULT_ISSUE_BORNAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRefcadastrale() throws Exception {
        // Get the refcadastrale
        restRefcadastraleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRefcadastrale() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        int databaseSizeBeforeUpdate = refcadastraleRepository.findAll().size();

        // Update the refcadastrale
        Refcadastrale updatedRefcadastrale = refcadastraleRepository.findById(refcadastrale.getId()).get();
        // Disconnect from session so that the updates on updatedRefcadastrale are not directly saved in db
        em.detach(updatedRefcadastrale);
        updatedRefcadastrale
            .codeSection(UPDATED_CODE_SECTION)
            .codeParcelle(UPDATED_CODE_PARCELLE)
            .nicad(UPDATED_NICAD)
            .superfici(UPDATED_SUPERFICI)
            .titreMere(UPDATED_TITRE_MERE)
            .titreCree(UPDATED_TITRE_CREE)
            .numeroRequisition(UPDATED_NUMERO_REQUISITION)
            .dateBornage(UPDATED_DATE_BORNAGE)
            .dependantDomaine(UPDATED_DEPENDANT_DOMAINE)
            .numeroDeliberation(UPDATED_NUMERO_DELIBERATION)
            .dateDeliberation(UPDATED_DATE_DELIBERATION)
            .nomGeometre(UPDATED_NOM_GEOMETRE)
            .issueBornage(UPDATED_ISSUE_BORNAGE);

        restRefcadastraleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRefcadastrale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRefcadastrale))
            )
            .andExpect(status().isOk());

        // Validate the Refcadastrale in the database
        List<Refcadastrale> refcadastraleList = refcadastraleRepository.findAll();
        assertThat(refcadastraleList).hasSize(databaseSizeBeforeUpdate);
        Refcadastrale testRefcadastrale = refcadastraleList.get(refcadastraleList.size() - 1);
        assertThat(testRefcadastrale.getCodeSection()).isEqualTo(UPDATED_CODE_SECTION);
        assertThat(testRefcadastrale.getCodeParcelle()).isEqualTo(UPDATED_CODE_PARCELLE);
        assertThat(testRefcadastrale.getNicad()).isEqualTo(UPDATED_NICAD);
        assertThat(testRefcadastrale.getSuperfici()).isEqualTo(UPDATED_SUPERFICI);
        assertThat(testRefcadastrale.getTitreMere()).isEqualTo(UPDATED_TITRE_MERE);
        assertThat(testRefcadastrale.getTitreCree()).isEqualTo(UPDATED_TITRE_CREE);
        assertThat(testRefcadastrale.getNumeroRequisition()).isEqualTo(UPDATED_NUMERO_REQUISITION);
        assertThat(testRefcadastrale.getDateBornage()).isEqualTo(UPDATED_DATE_BORNAGE);
        assertThat(testRefcadastrale.getDependantDomaine()).isEqualTo(UPDATED_DEPENDANT_DOMAINE);
        assertThat(testRefcadastrale.getNumeroDeliberation()).isEqualTo(UPDATED_NUMERO_DELIBERATION);
        assertThat(testRefcadastrale.getDateDeliberation()).isEqualTo(UPDATED_DATE_DELIBERATION);
        assertThat(testRefcadastrale.getNomGeometre()).isEqualTo(UPDATED_NOM_GEOMETRE);
        assertThat(testRefcadastrale.getIssueBornage()).isEqualTo(UPDATED_ISSUE_BORNAGE);
    }

    @Test
    @Transactional
    void putNonExistingRefcadastrale() throws Exception {
        int databaseSizeBeforeUpdate = refcadastraleRepository.findAll().size();
        refcadastrale.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefcadastraleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, refcadastrale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refcadastrale))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refcadastrale in the database
        List<Refcadastrale> refcadastraleList = refcadastraleRepository.findAll();
        assertThat(refcadastraleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRefcadastrale() throws Exception {
        int databaseSizeBeforeUpdate = refcadastraleRepository.findAll().size();
        refcadastrale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefcadastraleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refcadastrale))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refcadastrale in the database
        List<Refcadastrale> refcadastraleList = refcadastraleRepository.findAll();
        assertThat(refcadastraleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRefcadastrale() throws Exception {
        int databaseSizeBeforeUpdate = refcadastraleRepository.findAll().size();
        refcadastrale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefcadastraleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refcadastrale)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Refcadastrale in the database
        List<Refcadastrale> refcadastraleList = refcadastraleRepository.findAll();
        assertThat(refcadastraleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRefcadastraleWithPatch() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        int databaseSizeBeforeUpdate = refcadastraleRepository.findAll().size();

        // Update the refcadastrale using partial update
        Refcadastrale partialUpdatedRefcadastrale = new Refcadastrale();
        partialUpdatedRefcadastrale.setId(refcadastrale.getId());

        partialUpdatedRefcadastrale
            .codeSection(UPDATED_CODE_SECTION)
            .superfici(UPDATED_SUPERFICI)
            .numeroRequisition(UPDATED_NUMERO_REQUISITION)
            .dependantDomaine(UPDATED_DEPENDANT_DOMAINE)
            .dateDeliberation(UPDATED_DATE_DELIBERATION);

        restRefcadastraleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRefcadastrale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRefcadastrale))
            )
            .andExpect(status().isOk());

        // Validate the Refcadastrale in the database
        List<Refcadastrale> refcadastraleList = refcadastraleRepository.findAll();
        assertThat(refcadastraleList).hasSize(databaseSizeBeforeUpdate);
        Refcadastrale testRefcadastrale = refcadastraleList.get(refcadastraleList.size() - 1);
        assertThat(testRefcadastrale.getCodeSection()).isEqualTo(UPDATED_CODE_SECTION);
        assertThat(testRefcadastrale.getCodeParcelle()).isEqualTo(DEFAULT_CODE_PARCELLE);
        assertThat(testRefcadastrale.getNicad()).isEqualTo(DEFAULT_NICAD);
        assertThat(testRefcadastrale.getSuperfici()).isEqualTo(UPDATED_SUPERFICI);
        assertThat(testRefcadastrale.getTitreMere()).isEqualTo(DEFAULT_TITRE_MERE);
        assertThat(testRefcadastrale.getTitreCree()).isEqualTo(DEFAULT_TITRE_CREE);
        assertThat(testRefcadastrale.getNumeroRequisition()).isEqualTo(UPDATED_NUMERO_REQUISITION);
        assertThat(testRefcadastrale.getDateBornage()).isEqualTo(DEFAULT_DATE_BORNAGE);
        assertThat(testRefcadastrale.getDependantDomaine()).isEqualTo(UPDATED_DEPENDANT_DOMAINE);
        assertThat(testRefcadastrale.getNumeroDeliberation()).isEqualTo(DEFAULT_NUMERO_DELIBERATION);
        assertThat(testRefcadastrale.getDateDeliberation()).isEqualTo(UPDATED_DATE_DELIBERATION);
        assertThat(testRefcadastrale.getNomGeometre()).isEqualTo(DEFAULT_NOM_GEOMETRE);
        assertThat(testRefcadastrale.getIssueBornage()).isEqualTo(DEFAULT_ISSUE_BORNAGE);
    }

    @Test
    @Transactional
    void fullUpdateRefcadastraleWithPatch() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        int databaseSizeBeforeUpdate = refcadastraleRepository.findAll().size();

        // Update the refcadastrale using partial update
        Refcadastrale partialUpdatedRefcadastrale = new Refcadastrale();
        partialUpdatedRefcadastrale.setId(refcadastrale.getId());

        partialUpdatedRefcadastrale
            .codeSection(UPDATED_CODE_SECTION)
            .codeParcelle(UPDATED_CODE_PARCELLE)
            .nicad(UPDATED_NICAD)
            .superfici(UPDATED_SUPERFICI)
            .titreMere(UPDATED_TITRE_MERE)
            .titreCree(UPDATED_TITRE_CREE)
            .numeroRequisition(UPDATED_NUMERO_REQUISITION)
            .dateBornage(UPDATED_DATE_BORNAGE)
            .dependantDomaine(UPDATED_DEPENDANT_DOMAINE)
            .numeroDeliberation(UPDATED_NUMERO_DELIBERATION)
            .dateDeliberation(UPDATED_DATE_DELIBERATION)
            .nomGeometre(UPDATED_NOM_GEOMETRE)
            .issueBornage(UPDATED_ISSUE_BORNAGE);

        restRefcadastraleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRefcadastrale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRefcadastrale))
            )
            .andExpect(status().isOk());

        // Validate the Refcadastrale in the database
        List<Refcadastrale> refcadastraleList = refcadastraleRepository.findAll();
        assertThat(refcadastraleList).hasSize(databaseSizeBeforeUpdate);
        Refcadastrale testRefcadastrale = refcadastraleList.get(refcadastraleList.size() - 1);
        assertThat(testRefcadastrale.getCodeSection()).isEqualTo(UPDATED_CODE_SECTION);
        assertThat(testRefcadastrale.getCodeParcelle()).isEqualTo(UPDATED_CODE_PARCELLE);
        assertThat(testRefcadastrale.getNicad()).isEqualTo(UPDATED_NICAD);
        assertThat(testRefcadastrale.getSuperfici()).isEqualTo(UPDATED_SUPERFICI);
        assertThat(testRefcadastrale.getTitreMere()).isEqualTo(UPDATED_TITRE_MERE);
        assertThat(testRefcadastrale.getTitreCree()).isEqualTo(UPDATED_TITRE_CREE);
        assertThat(testRefcadastrale.getNumeroRequisition()).isEqualTo(UPDATED_NUMERO_REQUISITION);
        assertThat(testRefcadastrale.getDateBornage()).isEqualTo(UPDATED_DATE_BORNAGE);
        assertThat(testRefcadastrale.getDependantDomaine()).isEqualTo(UPDATED_DEPENDANT_DOMAINE);
        assertThat(testRefcadastrale.getNumeroDeliberation()).isEqualTo(UPDATED_NUMERO_DELIBERATION);
        assertThat(testRefcadastrale.getDateDeliberation()).isEqualTo(UPDATED_DATE_DELIBERATION);
        assertThat(testRefcadastrale.getNomGeometre()).isEqualTo(UPDATED_NOM_GEOMETRE);
        assertThat(testRefcadastrale.getIssueBornage()).isEqualTo(UPDATED_ISSUE_BORNAGE);
    }

    @Test
    @Transactional
    void patchNonExistingRefcadastrale() throws Exception {
        int databaseSizeBeforeUpdate = refcadastraleRepository.findAll().size();
        refcadastrale.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefcadastraleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, refcadastrale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refcadastrale))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refcadastrale in the database
        List<Refcadastrale> refcadastraleList = refcadastraleRepository.findAll();
        assertThat(refcadastraleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRefcadastrale() throws Exception {
        int databaseSizeBeforeUpdate = refcadastraleRepository.findAll().size();
        refcadastrale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefcadastraleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refcadastrale))
            )
            .andExpect(status().isBadRequest());

        // Validate the Refcadastrale in the database
        List<Refcadastrale> refcadastraleList = refcadastraleRepository.findAll();
        assertThat(refcadastraleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRefcadastrale() throws Exception {
        int databaseSizeBeforeUpdate = refcadastraleRepository.findAll().size();
        refcadastrale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefcadastraleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(refcadastrale))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Refcadastrale in the database
        List<Refcadastrale> refcadastraleList = refcadastraleRepository.findAll();
        assertThat(refcadastraleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRefcadastrale() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        int databaseSizeBeforeDelete = refcadastraleRepository.findAll().size();

        // Delete the refcadastrale
        restRefcadastraleMockMvc
            .perform(delete(ENTITY_API_URL_ID, refcadastrale.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Refcadastrale> refcadastraleList = refcadastraleRepository.findAll();
        assertThat(refcadastraleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
