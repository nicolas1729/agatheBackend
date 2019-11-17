package com.ibp.web.rest;

import com.ibp.DemoAgatheApp;
import com.ibp.domain.RBExe;
import com.ibp.repository.RBExeRepository;
import com.ibp.service.RBExeService;
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
 * Integration tests for the {@link RBExeResource} REST controller.
 */
@SpringBootTest(classes = DemoAgatheApp.class)
public class RBExeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ID_TASK = "AAAAAAAAAA";
    private static final String UPDATED_ID_TASK = "BBBBBBBBBB";

    private static final String DEFAULT_ID_PROCESS = "AAAAAAAAAA";
    private static final String UPDATED_ID_PROCESS = "BBBBBBBBBB";

    @Autowired
    private RBExeRepository rBExeRepository;

    @Autowired
    private RBExeService rBExeService;

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

    private MockMvc restRBExeMockMvc;

    private RBExe rBExe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RBExeResource rBExeResource = new RBExeResource(rBExeService);
        this.restRBExeMockMvc = MockMvcBuilders.standaloneSetup(rBExeResource)
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
    public static RBExe createEntity(EntityManager em) {
        RBExe rBExe = new RBExe()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .idTask(DEFAULT_ID_TASK)
            .idProcess(DEFAULT_ID_PROCESS);
        return rBExe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RBExe createUpdatedEntity(EntityManager em) {
        RBExe rBExe = new RBExe()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .idTask(UPDATED_ID_TASK)
            .idProcess(UPDATED_ID_PROCESS);
        return rBExe;
    }

    @BeforeEach
    public void initTest() {
        rBExe = createEntity(em);
    }

    @Test
    @Transactional
    public void createRBExe() throws Exception {
        int databaseSizeBeforeCreate = rBExeRepository.findAll().size();

        // Create the RBExe
        restRBExeMockMvc.perform(post("/api/rb-exes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rBExe)))
            .andExpect(status().isCreated());

        // Validate the RBExe in the database
        List<RBExe> rBExeList = rBExeRepository.findAll();
        assertThat(rBExeList).hasSize(databaseSizeBeforeCreate + 1);
        RBExe testRBExe = rBExeList.get(rBExeList.size() - 1);
        assertThat(testRBExe.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRBExe.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRBExe.getIdTask()).isEqualTo(DEFAULT_ID_TASK);
        assertThat(testRBExe.getIdProcess()).isEqualTo(DEFAULT_ID_PROCESS);
    }

    @Test
    @Transactional
    public void createRBExeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rBExeRepository.findAll().size();

        // Create the RBExe with an existing ID
        rBExe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRBExeMockMvc.perform(post("/api/rb-exes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rBExe)))
            .andExpect(status().isBadRequest());

        // Validate the RBExe in the database
        List<RBExe> rBExeList = rBExeRepository.findAll();
        assertThat(rBExeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = rBExeRepository.findAll().size();
        // set the field null
        rBExe.setNom(null);

        // Create the RBExe, which fails.

        restRBExeMockMvc.perform(post("/api/rb-exes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rBExe)))
            .andExpect(status().isBadRequest());

        List<RBExe> rBExeList = rBExeRepository.findAll();
        assertThat(rBExeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdTaskIsRequired() throws Exception {
        int databaseSizeBeforeTest = rBExeRepository.findAll().size();
        // set the field null
        rBExe.setIdTask(null);

        // Create the RBExe, which fails.

        restRBExeMockMvc.perform(post("/api/rb-exes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rBExe)))
            .andExpect(status().isBadRequest());

        List<RBExe> rBExeList = rBExeRepository.findAll();
        assertThat(rBExeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdProcessIsRequired() throws Exception {
        int databaseSizeBeforeTest = rBExeRepository.findAll().size();
        // set the field null
        rBExe.setIdProcess(null);

        // Create the RBExe, which fails.

        restRBExeMockMvc.perform(post("/api/rb-exes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rBExe)))
            .andExpect(status().isBadRequest());

        List<RBExe> rBExeList = rBExeRepository.findAll();
        assertThat(rBExeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRBExes() throws Exception {
        // Initialize the database
        rBExeRepository.saveAndFlush(rBExe);

        // Get all the rBExeList
        restRBExeMockMvc.perform(get("/api/rb-exes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rBExe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].idTask").value(hasItem(DEFAULT_ID_TASK)))
            .andExpect(jsonPath("$.[*].idProcess").value(hasItem(DEFAULT_ID_PROCESS)));
    }
    
    @Test
    @Transactional
    public void getRBExe() throws Exception {
        // Initialize the database
        rBExeRepository.saveAndFlush(rBExe);

        // Get the rBExe
        restRBExeMockMvc.perform(get("/api/rb-exes/{id}", rBExe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rBExe.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.idTask").value(DEFAULT_ID_TASK))
            .andExpect(jsonPath("$.idProcess").value(DEFAULT_ID_PROCESS));
    }

    @Test
    @Transactional
    public void getNonExistingRBExe() throws Exception {
        // Get the rBExe
        restRBExeMockMvc.perform(get("/api/rb-exes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRBExe() throws Exception {
        // Initialize the database
        rBExeService.save(rBExe);

        int databaseSizeBeforeUpdate = rBExeRepository.findAll().size();

        // Update the rBExe
        RBExe updatedRBExe = rBExeRepository.findById(rBExe.getId()).get();
        // Disconnect from session so that the updates on updatedRBExe are not directly saved in db
        em.detach(updatedRBExe);
        updatedRBExe
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .idTask(UPDATED_ID_TASK)
            .idProcess(UPDATED_ID_PROCESS);

        restRBExeMockMvc.perform(put("/api/rb-exes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRBExe)))
            .andExpect(status().isOk());

        // Validate the RBExe in the database
        List<RBExe> rBExeList = rBExeRepository.findAll();
        assertThat(rBExeList).hasSize(databaseSizeBeforeUpdate);
        RBExe testRBExe = rBExeList.get(rBExeList.size() - 1);
        assertThat(testRBExe.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRBExe.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRBExe.getIdTask()).isEqualTo(UPDATED_ID_TASK);
        assertThat(testRBExe.getIdProcess()).isEqualTo(UPDATED_ID_PROCESS);
    }

    @Test
    @Transactional
    public void updateNonExistingRBExe() throws Exception {
        int databaseSizeBeforeUpdate = rBExeRepository.findAll().size();

        // Create the RBExe

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRBExeMockMvc.perform(put("/api/rb-exes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rBExe)))
            .andExpect(status().isBadRequest());

        // Validate the RBExe in the database
        List<RBExe> rBExeList = rBExeRepository.findAll();
        assertThat(rBExeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRBExe() throws Exception {
        // Initialize the database
        rBExeService.save(rBExe);

        int databaseSizeBeforeDelete = rBExeRepository.findAll().size();

        // Delete the rBExe
        restRBExeMockMvc.perform(delete("/api/rb-exes/{id}", rBExe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RBExe> rBExeList = rBExeRepository.findAll();
        assertThat(rBExeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
