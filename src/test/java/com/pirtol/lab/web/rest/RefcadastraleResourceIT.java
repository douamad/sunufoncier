package com.pirtol.lab.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pirtol.lab.IntegrationTest;
import com.pirtol.lab.domain.Dossier;
import com.pirtol.lab.domain.Refcadastrale;
import com.pirtol.lab.repository.RefcadastraleRepository;
import com.pirtol.lab.service.criteria.RefcadastraleCriteria;
import com.pirtol.lab.service.dto.RefcadastraleDTO;
import com.pirtol.lab.service.mapper.RefcadastraleMapper;
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
    private static final Double SMALLER_SUPERFICI = 1D - 1D;

    private static final String DEFAULT_TITRE_MERE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_MERE = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE_CREE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_CREE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_REQUISITION = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_REQUISITION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_BORNAGE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_BORNAGE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/refcadastrales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RefcadastraleRepository refcadastraleRepository;

    @Autowired
    private RefcadastraleMapper refcadastraleMapper;

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
            .dateBornage(DEFAULT_DATE_BORNAGE);
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
            .dateBornage(UPDATED_DATE_BORNAGE);
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
        RefcadastraleDTO refcadastraleDTO = refcadastraleMapper.toDto(refcadastrale);
        restRefcadastraleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refcadastraleDTO))
            )
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
    }

    @Test
    @Transactional
    void createRefcadastraleWithExistingId() throws Exception {
        // Create the Refcadastrale with an existing ID
        refcadastrale.setId(1L);
        RefcadastraleDTO refcadastraleDTO = refcadastraleMapper.toDto(refcadastrale);

        int databaseSizeBeforeCreate = refcadastraleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefcadastraleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refcadastraleDTO))
            )
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
            .andExpect(jsonPath("$.[*].dateBornage").value(hasItem(DEFAULT_DATE_BORNAGE.toString())));
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
            .andExpect(jsonPath("$.dateBornage").value(DEFAULT_DATE_BORNAGE.toString()));
    }

    @Test
    @Transactional
    void getRefcadastralesByIdFiltering() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        Long id = refcadastrale.getId();

        defaultRefcadastraleShouldBeFound("id.equals=" + id);
        defaultRefcadastraleShouldNotBeFound("id.notEquals=" + id);

        defaultRefcadastraleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRefcadastraleShouldNotBeFound("id.greaterThan=" + id);

        defaultRefcadastraleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRefcadastraleShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByCodeSectionIsEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where codeSection equals to DEFAULT_CODE_SECTION
        defaultRefcadastraleShouldBeFound("codeSection.equals=" + DEFAULT_CODE_SECTION);

        // Get all the refcadastraleList where codeSection equals to UPDATED_CODE_SECTION
        defaultRefcadastraleShouldNotBeFound("codeSection.equals=" + UPDATED_CODE_SECTION);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByCodeSectionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where codeSection not equals to DEFAULT_CODE_SECTION
        defaultRefcadastraleShouldNotBeFound("codeSection.notEquals=" + DEFAULT_CODE_SECTION);

        // Get all the refcadastraleList where codeSection not equals to UPDATED_CODE_SECTION
        defaultRefcadastraleShouldBeFound("codeSection.notEquals=" + UPDATED_CODE_SECTION);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByCodeSectionIsInShouldWork() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where codeSection in DEFAULT_CODE_SECTION or UPDATED_CODE_SECTION
        defaultRefcadastraleShouldBeFound("codeSection.in=" + DEFAULT_CODE_SECTION + "," + UPDATED_CODE_SECTION);

        // Get all the refcadastraleList where codeSection equals to UPDATED_CODE_SECTION
        defaultRefcadastraleShouldNotBeFound("codeSection.in=" + UPDATED_CODE_SECTION);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByCodeSectionIsNullOrNotNull() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where codeSection is not null
        defaultRefcadastraleShouldBeFound("codeSection.specified=true");

        // Get all the refcadastraleList where codeSection is null
        defaultRefcadastraleShouldNotBeFound("codeSection.specified=false");
    }

    @Test
    @Transactional
    void getAllRefcadastralesByCodeSectionContainsSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where codeSection contains DEFAULT_CODE_SECTION
        defaultRefcadastraleShouldBeFound("codeSection.contains=" + DEFAULT_CODE_SECTION);

        // Get all the refcadastraleList where codeSection contains UPDATED_CODE_SECTION
        defaultRefcadastraleShouldNotBeFound("codeSection.contains=" + UPDATED_CODE_SECTION);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByCodeSectionNotContainsSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where codeSection does not contain DEFAULT_CODE_SECTION
        defaultRefcadastraleShouldNotBeFound("codeSection.doesNotContain=" + DEFAULT_CODE_SECTION);

        // Get all the refcadastraleList where codeSection does not contain UPDATED_CODE_SECTION
        defaultRefcadastraleShouldBeFound("codeSection.doesNotContain=" + UPDATED_CODE_SECTION);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByCodeParcelleIsEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where codeParcelle equals to DEFAULT_CODE_PARCELLE
        defaultRefcadastraleShouldBeFound("codeParcelle.equals=" + DEFAULT_CODE_PARCELLE);

        // Get all the refcadastraleList where codeParcelle equals to UPDATED_CODE_PARCELLE
        defaultRefcadastraleShouldNotBeFound("codeParcelle.equals=" + UPDATED_CODE_PARCELLE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByCodeParcelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where codeParcelle not equals to DEFAULT_CODE_PARCELLE
        defaultRefcadastraleShouldNotBeFound("codeParcelle.notEquals=" + DEFAULT_CODE_PARCELLE);

        // Get all the refcadastraleList where codeParcelle not equals to UPDATED_CODE_PARCELLE
        defaultRefcadastraleShouldBeFound("codeParcelle.notEquals=" + UPDATED_CODE_PARCELLE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByCodeParcelleIsInShouldWork() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where codeParcelle in DEFAULT_CODE_PARCELLE or UPDATED_CODE_PARCELLE
        defaultRefcadastraleShouldBeFound("codeParcelle.in=" + DEFAULT_CODE_PARCELLE + "," + UPDATED_CODE_PARCELLE);

        // Get all the refcadastraleList where codeParcelle equals to UPDATED_CODE_PARCELLE
        defaultRefcadastraleShouldNotBeFound("codeParcelle.in=" + UPDATED_CODE_PARCELLE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByCodeParcelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where codeParcelle is not null
        defaultRefcadastraleShouldBeFound("codeParcelle.specified=true");

        // Get all the refcadastraleList where codeParcelle is null
        defaultRefcadastraleShouldNotBeFound("codeParcelle.specified=false");
    }

    @Test
    @Transactional
    void getAllRefcadastralesByCodeParcelleContainsSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where codeParcelle contains DEFAULT_CODE_PARCELLE
        defaultRefcadastraleShouldBeFound("codeParcelle.contains=" + DEFAULT_CODE_PARCELLE);

        // Get all the refcadastraleList where codeParcelle contains UPDATED_CODE_PARCELLE
        defaultRefcadastraleShouldNotBeFound("codeParcelle.contains=" + UPDATED_CODE_PARCELLE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByCodeParcelleNotContainsSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where codeParcelle does not contain DEFAULT_CODE_PARCELLE
        defaultRefcadastraleShouldNotBeFound("codeParcelle.doesNotContain=" + DEFAULT_CODE_PARCELLE);

        // Get all the refcadastraleList where codeParcelle does not contain UPDATED_CODE_PARCELLE
        defaultRefcadastraleShouldBeFound("codeParcelle.doesNotContain=" + UPDATED_CODE_PARCELLE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByNicadIsEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where nicad equals to DEFAULT_NICAD
        defaultRefcadastraleShouldBeFound("nicad.equals=" + DEFAULT_NICAD);

        // Get all the refcadastraleList where nicad equals to UPDATED_NICAD
        defaultRefcadastraleShouldNotBeFound("nicad.equals=" + UPDATED_NICAD);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByNicadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where nicad not equals to DEFAULT_NICAD
        defaultRefcadastraleShouldNotBeFound("nicad.notEquals=" + DEFAULT_NICAD);

        // Get all the refcadastraleList where nicad not equals to UPDATED_NICAD
        defaultRefcadastraleShouldBeFound("nicad.notEquals=" + UPDATED_NICAD);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByNicadIsInShouldWork() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where nicad in DEFAULT_NICAD or UPDATED_NICAD
        defaultRefcadastraleShouldBeFound("nicad.in=" + DEFAULT_NICAD + "," + UPDATED_NICAD);

        // Get all the refcadastraleList where nicad equals to UPDATED_NICAD
        defaultRefcadastraleShouldNotBeFound("nicad.in=" + UPDATED_NICAD);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByNicadIsNullOrNotNull() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where nicad is not null
        defaultRefcadastraleShouldBeFound("nicad.specified=true");

        // Get all the refcadastraleList where nicad is null
        defaultRefcadastraleShouldNotBeFound("nicad.specified=false");
    }

    @Test
    @Transactional
    void getAllRefcadastralesByNicadContainsSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where nicad contains DEFAULT_NICAD
        defaultRefcadastraleShouldBeFound("nicad.contains=" + DEFAULT_NICAD);

        // Get all the refcadastraleList where nicad contains UPDATED_NICAD
        defaultRefcadastraleShouldNotBeFound("nicad.contains=" + UPDATED_NICAD);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByNicadNotContainsSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where nicad does not contain DEFAULT_NICAD
        defaultRefcadastraleShouldNotBeFound("nicad.doesNotContain=" + DEFAULT_NICAD);

        // Get all the refcadastraleList where nicad does not contain UPDATED_NICAD
        defaultRefcadastraleShouldBeFound("nicad.doesNotContain=" + UPDATED_NICAD);
    }

    @Test
    @Transactional
    void getAllRefcadastralesBySuperficiIsEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where superfici equals to DEFAULT_SUPERFICI
        defaultRefcadastraleShouldBeFound("superfici.equals=" + DEFAULT_SUPERFICI);

        // Get all the refcadastraleList where superfici equals to UPDATED_SUPERFICI
        defaultRefcadastraleShouldNotBeFound("superfici.equals=" + UPDATED_SUPERFICI);
    }

    @Test
    @Transactional
    void getAllRefcadastralesBySuperficiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where superfici not equals to DEFAULT_SUPERFICI
        defaultRefcadastraleShouldNotBeFound("superfici.notEquals=" + DEFAULT_SUPERFICI);

        // Get all the refcadastraleList where superfici not equals to UPDATED_SUPERFICI
        defaultRefcadastraleShouldBeFound("superfici.notEquals=" + UPDATED_SUPERFICI);
    }

    @Test
    @Transactional
    void getAllRefcadastralesBySuperficiIsInShouldWork() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where superfici in DEFAULT_SUPERFICI or UPDATED_SUPERFICI
        defaultRefcadastraleShouldBeFound("superfici.in=" + DEFAULT_SUPERFICI + "," + UPDATED_SUPERFICI);

        // Get all the refcadastraleList where superfici equals to UPDATED_SUPERFICI
        defaultRefcadastraleShouldNotBeFound("superfici.in=" + UPDATED_SUPERFICI);
    }

    @Test
    @Transactional
    void getAllRefcadastralesBySuperficiIsNullOrNotNull() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where superfici is not null
        defaultRefcadastraleShouldBeFound("superfici.specified=true");

        // Get all the refcadastraleList where superfici is null
        defaultRefcadastraleShouldNotBeFound("superfici.specified=false");
    }

    @Test
    @Transactional
    void getAllRefcadastralesBySuperficiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where superfici is greater than or equal to DEFAULT_SUPERFICI
        defaultRefcadastraleShouldBeFound("superfici.greaterThanOrEqual=" + DEFAULT_SUPERFICI);

        // Get all the refcadastraleList where superfici is greater than or equal to UPDATED_SUPERFICI
        defaultRefcadastraleShouldNotBeFound("superfici.greaterThanOrEqual=" + UPDATED_SUPERFICI);
    }

    @Test
    @Transactional
    void getAllRefcadastralesBySuperficiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where superfici is less than or equal to DEFAULT_SUPERFICI
        defaultRefcadastraleShouldBeFound("superfici.lessThanOrEqual=" + DEFAULT_SUPERFICI);

        // Get all the refcadastraleList where superfici is less than or equal to SMALLER_SUPERFICI
        defaultRefcadastraleShouldNotBeFound("superfici.lessThanOrEqual=" + SMALLER_SUPERFICI);
    }

    @Test
    @Transactional
    void getAllRefcadastralesBySuperficiIsLessThanSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where superfici is less than DEFAULT_SUPERFICI
        defaultRefcadastraleShouldNotBeFound("superfici.lessThan=" + DEFAULT_SUPERFICI);

        // Get all the refcadastraleList where superfici is less than UPDATED_SUPERFICI
        defaultRefcadastraleShouldBeFound("superfici.lessThan=" + UPDATED_SUPERFICI);
    }

    @Test
    @Transactional
    void getAllRefcadastralesBySuperficiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where superfici is greater than DEFAULT_SUPERFICI
        defaultRefcadastraleShouldNotBeFound("superfici.greaterThan=" + DEFAULT_SUPERFICI);

        // Get all the refcadastraleList where superfici is greater than SMALLER_SUPERFICI
        defaultRefcadastraleShouldBeFound("superfici.greaterThan=" + SMALLER_SUPERFICI);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByTitreMereIsEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where titreMere equals to DEFAULT_TITRE_MERE
        defaultRefcadastraleShouldBeFound("titreMere.equals=" + DEFAULT_TITRE_MERE);

        // Get all the refcadastraleList where titreMere equals to UPDATED_TITRE_MERE
        defaultRefcadastraleShouldNotBeFound("titreMere.equals=" + UPDATED_TITRE_MERE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByTitreMereIsNotEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where titreMere not equals to DEFAULT_TITRE_MERE
        defaultRefcadastraleShouldNotBeFound("titreMere.notEquals=" + DEFAULT_TITRE_MERE);

        // Get all the refcadastraleList where titreMere not equals to UPDATED_TITRE_MERE
        defaultRefcadastraleShouldBeFound("titreMere.notEquals=" + UPDATED_TITRE_MERE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByTitreMereIsInShouldWork() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where titreMere in DEFAULT_TITRE_MERE or UPDATED_TITRE_MERE
        defaultRefcadastraleShouldBeFound("titreMere.in=" + DEFAULT_TITRE_MERE + "," + UPDATED_TITRE_MERE);

        // Get all the refcadastraleList where titreMere equals to UPDATED_TITRE_MERE
        defaultRefcadastraleShouldNotBeFound("titreMere.in=" + UPDATED_TITRE_MERE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByTitreMereIsNullOrNotNull() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where titreMere is not null
        defaultRefcadastraleShouldBeFound("titreMere.specified=true");

        // Get all the refcadastraleList where titreMere is null
        defaultRefcadastraleShouldNotBeFound("titreMere.specified=false");
    }

    @Test
    @Transactional
    void getAllRefcadastralesByTitreMereContainsSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where titreMere contains DEFAULT_TITRE_MERE
        defaultRefcadastraleShouldBeFound("titreMere.contains=" + DEFAULT_TITRE_MERE);

        // Get all the refcadastraleList where titreMere contains UPDATED_TITRE_MERE
        defaultRefcadastraleShouldNotBeFound("titreMere.contains=" + UPDATED_TITRE_MERE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByTitreMereNotContainsSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where titreMere does not contain DEFAULT_TITRE_MERE
        defaultRefcadastraleShouldNotBeFound("titreMere.doesNotContain=" + DEFAULT_TITRE_MERE);

        // Get all the refcadastraleList where titreMere does not contain UPDATED_TITRE_MERE
        defaultRefcadastraleShouldBeFound("titreMere.doesNotContain=" + UPDATED_TITRE_MERE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByTitreCreeIsEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where titreCree equals to DEFAULT_TITRE_CREE
        defaultRefcadastraleShouldBeFound("titreCree.equals=" + DEFAULT_TITRE_CREE);

        // Get all the refcadastraleList where titreCree equals to UPDATED_TITRE_CREE
        defaultRefcadastraleShouldNotBeFound("titreCree.equals=" + UPDATED_TITRE_CREE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByTitreCreeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where titreCree not equals to DEFAULT_TITRE_CREE
        defaultRefcadastraleShouldNotBeFound("titreCree.notEquals=" + DEFAULT_TITRE_CREE);

        // Get all the refcadastraleList where titreCree not equals to UPDATED_TITRE_CREE
        defaultRefcadastraleShouldBeFound("titreCree.notEquals=" + UPDATED_TITRE_CREE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByTitreCreeIsInShouldWork() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where titreCree in DEFAULT_TITRE_CREE or UPDATED_TITRE_CREE
        defaultRefcadastraleShouldBeFound("titreCree.in=" + DEFAULT_TITRE_CREE + "," + UPDATED_TITRE_CREE);

        // Get all the refcadastraleList where titreCree equals to UPDATED_TITRE_CREE
        defaultRefcadastraleShouldNotBeFound("titreCree.in=" + UPDATED_TITRE_CREE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByTitreCreeIsNullOrNotNull() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where titreCree is not null
        defaultRefcadastraleShouldBeFound("titreCree.specified=true");

        // Get all the refcadastraleList where titreCree is null
        defaultRefcadastraleShouldNotBeFound("titreCree.specified=false");
    }

    @Test
    @Transactional
    void getAllRefcadastralesByTitreCreeContainsSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where titreCree contains DEFAULT_TITRE_CREE
        defaultRefcadastraleShouldBeFound("titreCree.contains=" + DEFAULT_TITRE_CREE);

        // Get all the refcadastraleList where titreCree contains UPDATED_TITRE_CREE
        defaultRefcadastraleShouldNotBeFound("titreCree.contains=" + UPDATED_TITRE_CREE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByTitreCreeNotContainsSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where titreCree does not contain DEFAULT_TITRE_CREE
        defaultRefcadastraleShouldNotBeFound("titreCree.doesNotContain=" + DEFAULT_TITRE_CREE);

        // Get all the refcadastraleList where titreCree does not contain UPDATED_TITRE_CREE
        defaultRefcadastraleShouldBeFound("titreCree.doesNotContain=" + UPDATED_TITRE_CREE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByNumeroRequisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where numeroRequisition equals to DEFAULT_NUMERO_REQUISITION
        defaultRefcadastraleShouldBeFound("numeroRequisition.equals=" + DEFAULT_NUMERO_REQUISITION);

        // Get all the refcadastraleList where numeroRequisition equals to UPDATED_NUMERO_REQUISITION
        defaultRefcadastraleShouldNotBeFound("numeroRequisition.equals=" + UPDATED_NUMERO_REQUISITION);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByNumeroRequisitionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where numeroRequisition not equals to DEFAULT_NUMERO_REQUISITION
        defaultRefcadastraleShouldNotBeFound("numeroRequisition.notEquals=" + DEFAULT_NUMERO_REQUISITION);

        // Get all the refcadastraleList where numeroRequisition not equals to UPDATED_NUMERO_REQUISITION
        defaultRefcadastraleShouldBeFound("numeroRequisition.notEquals=" + UPDATED_NUMERO_REQUISITION);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByNumeroRequisitionIsInShouldWork() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where numeroRequisition in DEFAULT_NUMERO_REQUISITION or UPDATED_NUMERO_REQUISITION
        defaultRefcadastraleShouldBeFound("numeroRequisition.in=" + DEFAULT_NUMERO_REQUISITION + "," + UPDATED_NUMERO_REQUISITION);

        // Get all the refcadastraleList where numeroRequisition equals to UPDATED_NUMERO_REQUISITION
        defaultRefcadastraleShouldNotBeFound("numeroRequisition.in=" + UPDATED_NUMERO_REQUISITION);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByNumeroRequisitionIsNullOrNotNull() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where numeroRequisition is not null
        defaultRefcadastraleShouldBeFound("numeroRequisition.specified=true");

        // Get all the refcadastraleList where numeroRequisition is null
        defaultRefcadastraleShouldNotBeFound("numeroRequisition.specified=false");
    }

    @Test
    @Transactional
    void getAllRefcadastralesByNumeroRequisitionContainsSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where numeroRequisition contains DEFAULT_NUMERO_REQUISITION
        defaultRefcadastraleShouldBeFound("numeroRequisition.contains=" + DEFAULT_NUMERO_REQUISITION);

        // Get all the refcadastraleList where numeroRequisition contains UPDATED_NUMERO_REQUISITION
        defaultRefcadastraleShouldNotBeFound("numeroRequisition.contains=" + UPDATED_NUMERO_REQUISITION);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByNumeroRequisitionNotContainsSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where numeroRequisition does not contain DEFAULT_NUMERO_REQUISITION
        defaultRefcadastraleShouldNotBeFound("numeroRequisition.doesNotContain=" + DEFAULT_NUMERO_REQUISITION);

        // Get all the refcadastraleList where numeroRequisition does not contain UPDATED_NUMERO_REQUISITION
        defaultRefcadastraleShouldBeFound("numeroRequisition.doesNotContain=" + UPDATED_NUMERO_REQUISITION);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByDateBornageIsEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where dateBornage equals to DEFAULT_DATE_BORNAGE
        defaultRefcadastraleShouldBeFound("dateBornage.equals=" + DEFAULT_DATE_BORNAGE);

        // Get all the refcadastraleList where dateBornage equals to UPDATED_DATE_BORNAGE
        defaultRefcadastraleShouldNotBeFound("dateBornage.equals=" + UPDATED_DATE_BORNAGE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByDateBornageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where dateBornage not equals to DEFAULT_DATE_BORNAGE
        defaultRefcadastraleShouldNotBeFound("dateBornage.notEquals=" + DEFAULT_DATE_BORNAGE);

        // Get all the refcadastraleList where dateBornage not equals to UPDATED_DATE_BORNAGE
        defaultRefcadastraleShouldBeFound("dateBornage.notEquals=" + UPDATED_DATE_BORNAGE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByDateBornageIsInShouldWork() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where dateBornage in DEFAULT_DATE_BORNAGE or UPDATED_DATE_BORNAGE
        defaultRefcadastraleShouldBeFound("dateBornage.in=" + DEFAULT_DATE_BORNAGE + "," + UPDATED_DATE_BORNAGE);

        // Get all the refcadastraleList where dateBornage equals to UPDATED_DATE_BORNAGE
        defaultRefcadastraleShouldNotBeFound("dateBornage.in=" + UPDATED_DATE_BORNAGE);
    }

    @Test
    @Transactional
    void getAllRefcadastralesByDateBornageIsNullOrNotNull() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);

        // Get all the refcadastraleList where dateBornage is not null
        defaultRefcadastraleShouldBeFound("dateBornage.specified=true");

        // Get all the refcadastraleList where dateBornage is null
        defaultRefcadastraleShouldNotBeFound("dateBornage.specified=false");
    }

    @Test
    @Transactional
    void getAllRefcadastralesByDossierIsEqualToSomething() throws Exception {
        // Initialize the database
        refcadastraleRepository.saveAndFlush(refcadastrale);
        Dossier dossier = DossierResourceIT.createEntity(em);
        em.persist(dossier);
        em.flush();
        refcadastrale.addDossier(dossier);
        refcadastraleRepository.saveAndFlush(refcadastrale);
        Long dossierId = dossier.getId();

        // Get all the refcadastraleList where dossier equals to dossierId
        defaultRefcadastraleShouldBeFound("dossierId.equals=" + dossierId);

        // Get all the refcadastraleList where dossier equals to (dossierId + 1)
        defaultRefcadastraleShouldNotBeFound("dossierId.equals=" + (dossierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRefcadastraleShouldBeFound(String filter) throws Exception {
        restRefcadastraleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
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
            .andExpect(jsonPath("$.[*].dateBornage").value(hasItem(DEFAULT_DATE_BORNAGE.toString())));

        // Check, that the count call also returns 1
        restRefcadastraleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRefcadastraleShouldNotBeFound(String filter) throws Exception {
        restRefcadastraleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRefcadastraleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRefcadastrale() throws Exception {
        // Get the refcadastrale
        restRefcadastraleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRefcadastrale() throws Exception {
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
            .dateBornage(UPDATED_DATE_BORNAGE);
        RefcadastraleDTO refcadastraleDTO = refcadastraleMapper.toDto(updatedRefcadastrale);

        restRefcadastraleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, refcadastraleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refcadastraleDTO))
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
    }

    @Test
    @Transactional
    void putNonExistingRefcadastrale() throws Exception {
        int databaseSizeBeforeUpdate = refcadastraleRepository.findAll().size();
        refcadastrale.setId(count.incrementAndGet());

        // Create the Refcadastrale
        RefcadastraleDTO refcadastraleDTO = refcadastraleMapper.toDto(refcadastrale);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefcadastraleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, refcadastraleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refcadastraleDTO))
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

        // Create the Refcadastrale
        RefcadastraleDTO refcadastraleDTO = refcadastraleMapper.toDto(refcadastrale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefcadastraleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(refcadastraleDTO))
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

        // Create the Refcadastrale
        RefcadastraleDTO refcadastraleDTO = refcadastraleMapper.toDto(refcadastrale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefcadastraleMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(refcadastraleDTO))
            )
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

        partialUpdatedRefcadastrale.nicad(UPDATED_NICAD).superfici(UPDATED_SUPERFICI).titreMere(UPDATED_TITRE_MERE);

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
        assertThat(testRefcadastrale.getCodeSection()).isEqualTo(DEFAULT_CODE_SECTION);
        assertThat(testRefcadastrale.getCodeParcelle()).isEqualTo(DEFAULT_CODE_PARCELLE);
        assertThat(testRefcadastrale.getNicad()).isEqualTo(UPDATED_NICAD);
        assertThat(testRefcadastrale.getSuperfici()).isEqualTo(UPDATED_SUPERFICI);
        assertThat(testRefcadastrale.getTitreMere()).isEqualTo(UPDATED_TITRE_MERE);
        assertThat(testRefcadastrale.getTitreCree()).isEqualTo(DEFAULT_TITRE_CREE);
        assertThat(testRefcadastrale.getNumeroRequisition()).isEqualTo(DEFAULT_NUMERO_REQUISITION);
        assertThat(testRefcadastrale.getDateBornage()).isEqualTo(DEFAULT_DATE_BORNAGE);
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
            .dateBornage(UPDATED_DATE_BORNAGE);

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
    }

    @Test
    @Transactional
    void patchNonExistingRefcadastrale() throws Exception {
        int databaseSizeBeforeUpdate = refcadastraleRepository.findAll().size();
        refcadastrale.setId(count.incrementAndGet());

        // Create the Refcadastrale
        RefcadastraleDTO refcadastraleDTO = refcadastraleMapper.toDto(refcadastrale);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefcadastraleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, refcadastraleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refcadastraleDTO))
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

        // Create the Refcadastrale
        RefcadastraleDTO refcadastraleDTO = refcadastraleMapper.toDto(refcadastrale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefcadastraleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refcadastraleDTO))
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

        // Create the Refcadastrale
        RefcadastraleDTO refcadastraleDTO = refcadastraleMapper.toDto(refcadastrale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRefcadastraleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(refcadastraleDTO))
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
