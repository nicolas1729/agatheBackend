package com.ibp.web.rest;

import com.ibp.DemoAgatheApp;
import com.ibp.domain.PF;
import com.ibp.repository.PFRepository;
import com.ibp.service.PFService;
import com.ibp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ibp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PFResource} REST controller.
 */
@SpringBootTest(classes = DemoAgatheApp.class)
public class PFResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ID_TASK = "AAAAAAAAAA";
    private static final String UPDATED_ID_TASK = "BBBBBBBBBB";

    private static final String DEFAULT_ID_PROCESS = "AAAAAAAAAA";
    private static final String UPDATED_ID_PROCESS = "BBBBBBBBBB";

    @Autowired
    private PFRepository pFRepository;

    @Autowired
    private PFService pFService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPFMockMvc;

    private PF pF;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PFResource pFResource = new PFResource(pFService);
        this.restPFMockMvc = MockMvcBuilders.standaloneSetup(pFResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PF createEntity(EntityManager em) {
        PF pF = new PF()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .idTask(DEFAULT_ID_TASK)
            .idProcess(DEFAULT_ID_PROCESS);
        return pF;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PF createUpdatedEntity(EntityManager em) {
        PF pF = new PF()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .idTask(UPDATED_ID_TASK)
            .idProcess(UPDATED_ID_PROCESS);
        return pF;
    }

    @BeforeEach
    public void initTest() {
        pF = createEntity(em);
    }

    @Test
    @Transactional
    public void createPF() throws Exception {
        int databaseSizeBeforeCreate = pFRepository.findAll().size();

        // Create the PF
        restPFMockMvc.perform(post("/api/pfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pF)))
            .andExpect(status().isCreated());

        // Validate the PF in the database
        List<PF> pFList = pFRepository.findAll();
        assertThat(pFList).hasSize(databaseSizeBeforeCreate + 1);
        PF testPF = pFList.get(pFList.size() - 1);
        assertThat(testPF.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPF.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPF.getIdTask()).isEqualTo(DEFAULT_ID_TASK);
        assertThat(testPF.getIdProcess()).isEqualTo(DEFAULT_ID_PROCESS);
    }

    @Test
    @Transactional
    public void createPFWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pFRepository.findAll().size();

        // Create the PF with an existing ID
        pF.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPFMockMvc.perform(post("/api/pfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pF)))
            .andExpect(status().isBadRequest());

        // Validate the PF in the database
        List<PF> pFList = pFRepository.findAll();
        assertThat(pFList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = pFRepository.findAll().size();
        // set the field null
        pF.setNom(null);

        // Create the PF, which fails.

        restPFMockMvc.perform(post("/api/pfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pF)))
            .andExpect(status().isBadRequest());

        List<PF> pFList = pFRepository.findAll();
        assertThat(pFList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdTaskIsRequired() throws Exception {
        int databaseSizeBeforeTest = pFRepository.findAll().size();
        // set the field null
        pF.setIdTask(null);

        // Create the PF, which fails.

        restPFMockMvc.perform(post("/api/pfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pF)))
            .andExpect(status().isBadRequest());

        List<PF> pFList = pFRepository.findAll();
        assertThat(pFList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdProcessIsRequired() throws Exception {
        int databaseSizeBeforeTest = pFRepository.findAll().size();
        // set the field null
        pF.setIdProcess(null);

        // Create the PF, which fails.

        restPFMockMvc.perform(post("/api/pfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pF)))
            .andExpect(status().isBadRequest());

        List<PF> pFList = pFRepository.findAll();
        assertThat(pFList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPFS() throws Exception {
        // Initialize the database
        pFRepository.saveAndFlush(pF);

        // Get all the pFList
        restPFMockMvc.perform(get("/api/pfs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pF.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].idTask").value(hasItem(DEFAULT_ID_TASK)))
            .andExpect(jsonPath("$.[*].idProcess").value(hasItem(DEFAULT_ID_PROCESS)));
    }
    
    @Test
    @Transactional
    public void getPF() throws Exception {
        // Initialize the database
        pFRepository.saveAndFlush(pF);

        // Get the pF
        restPFMockMvc.perform(get("/api/pfs/{id}", pF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pF.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.idTask").value(DEFAULT_ID_TASK))
            .andExpect(jsonPath("$.idProcess").value(DEFAULT_ID_PROCESS));
    }

    @Test
    @Transactional
    public void getNonExistingPF() throws Exception {
        // Get the pF
        restPFMockMvc.perform(get("/api/pfs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePF() throws Exception {
        // Initialize the database
        pFService.save(pF);

        int databaseSizeBeforeUpdate = pFRepository.findAll().size();

        // Update the pF
        PF updatedPF = pFRepository.findById(pF.getId()).get();
        // Disconnect from session so that the updates on updatedPF are not directly saved in db
        em.detach(updatedPF);
        updatedPF
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .idTask(UPDATED_ID_TASK)
            .idProcess(UPDATED_ID_PROCESS);

        restPFMockMvc.perform(put("/api/pfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPF)))
            .andExpect(status().isOk());

        // Validate the PF in the database
        List<PF> pFList = pFRepository.findAll();
        assertThat(pFList).hasSize(databaseSizeBeforeUpdate);
        PF testPF = pFList.get(pFList.size() - 1);
        assertThat(testPF.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPF.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPF.getIdTask()).isEqualTo(UPDATED_ID_TASK);
        assertThat(testPF.getIdProcess()).isEqualTo(UPDATED_ID_PROCESS);
    }

    @Test
    @Transactional
    public void updateNonExistingPF() throws Exception {
        int databaseSizeBeforeUpdate = pFRepository.findAll().size();

        // Create the PF

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPFMockMvc.perform(put("/api/pfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pF)))
            .andExpect(status().isBadRequest());

        // Validate the PF in the database
        List<PF> pFList = pFRepository.findAll();
        assertThat(pFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePF() throws Exception {
        // Initialize the database
        pFService.save(pF);

        int databaseSizeBeforeDelete = pFRepository.findAll().size();

        // Delete the pF
        restPFMockMvc.perform(delete("/api/pfs/{id}", pF.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PF> pFList = pFRepository.findAll();
        assertThat(pFList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
