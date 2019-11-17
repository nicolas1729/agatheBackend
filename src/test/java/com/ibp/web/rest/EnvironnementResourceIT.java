package com.ibp.web.rest;

import com.ibp.DemoAgatheApp;
import com.ibp.domain.Environnement;
import com.ibp.repository.EnvironnementRepository;
import com.ibp.service.EnvironnementService;
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
 * Integration tests for the {@link EnvironnementResource} REST controller.
 */
@SpringBootTest(classes = DemoAgatheApp.class)
public class EnvironnementResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private EnvironnementRepository environnementRepository;

    @Autowired
    private EnvironnementService environnementService;

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

    private MockMvc restEnvironnementMockMvc;

    private Environnement environnement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnvironnementResource environnementResource = new EnvironnementResource(environnementService);
        this.restEnvironnementMockMvc = MockMvcBuilders.standaloneSetup(environnementResource)
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
    public static Environnement createEntity(EntityManager em) {
        Environnement environnement = new Environnement()
            .nom(DEFAULT_NOM);
        return environnement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Environnement createUpdatedEntity(EntityManager em) {
        Environnement environnement = new Environnement()
            .nom(UPDATED_NOM);
        return environnement;
    }

    @BeforeEach
    public void initTest() {
        environnement = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnvironnement() throws Exception {
        int databaseSizeBeforeCreate = environnementRepository.findAll().size();

        // Create the Environnement
        restEnvironnementMockMvc.perform(post("/api/environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(environnement)))
            .andExpect(status().isCreated());

        // Validate the Environnement in the database
        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeCreate + 1);
        Environnement testEnvironnement = environnementList.get(environnementList.size() - 1);
        assertThat(testEnvironnement.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createEnvironnementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = environnementRepository.findAll().size();

        // Create the Environnement with an existing ID
        environnement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnvironnementMockMvc.perform(post("/api/environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(environnement)))
            .andExpect(status().isBadRequest());

        // Validate the Environnement in the database
        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = environnementRepository.findAll().size();
        // set the field null
        environnement.setNom(null);

        // Create the Environnement, which fails.

        restEnvironnementMockMvc.perform(post("/api/environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(environnement)))
            .andExpect(status().isBadRequest());

        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnvironnements() throws Exception {
        // Initialize the database
        environnementRepository.saveAndFlush(environnement);

        // Get all the environnementList
        restEnvironnementMockMvc.perform(get("/api/environnements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(environnement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }
    
    @Test
    @Transactional
    public void getEnvironnement() throws Exception {
        // Initialize the database
        environnementRepository.saveAndFlush(environnement);

        // Get the environnement
        restEnvironnementMockMvc.perform(get("/api/environnements/{id}", environnement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(environnement.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }

    @Test
    @Transactional
    public void getNonExistingEnvironnement() throws Exception {
        // Get the environnement
        restEnvironnementMockMvc.perform(get("/api/environnements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnvironnement() throws Exception {
        // Initialize the database
        environnementService.save(environnement);

        int databaseSizeBeforeUpdate = environnementRepository.findAll().size();

        // Update the environnement
        Environnement updatedEnvironnement = environnementRepository.findById(environnement.getId()).get();
        // Disconnect from session so that the updates on updatedEnvironnement are not directly saved in db
        em.detach(updatedEnvironnement);
        updatedEnvironnement
            .nom(UPDATED_NOM);

        restEnvironnementMockMvc.perform(put("/api/environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEnvironnement)))
            .andExpect(status().isOk());

        // Validate the Environnement in the database
        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeUpdate);
        Environnement testEnvironnement = environnementList.get(environnementList.size() - 1);
        assertThat(testEnvironnement.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingEnvironnement() throws Exception {
        int databaseSizeBeforeUpdate = environnementRepository.findAll().size();

        // Create the Environnement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnvironnementMockMvc.perform(put("/api/environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(environnement)))
            .andExpect(status().isBadRequest());

        // Validate the Environnement in the database
        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnvironnement() throws Exception {
        // Initialize the database
        environnementService.save(environnement);

        int databaseSizeBeforeDelete = environnementRepository.findAll().size();

        // Delete the environnement
        restEnvironnementMockMvc.perform(delete("/api/environnements/{id}", environnement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
