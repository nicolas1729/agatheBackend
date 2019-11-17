package com.ibp.web.rest;

import com.ibp.DemoAgatheApp;
import com.ibp.domain.VersionEnvironnement;
import com.ibp.repository.VersionEnvironnementRepository;
import com.ibp.service.VersionEnvironnementService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.ibp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VersionEnvironnementResource} REST controller.
 */
@SpringBootTest(classes = DemoAgatheApp.class)
public class VersionEnvironnementResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_PUBLICATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_PUBLICATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VersionEnvironnementRepository versionEnvironnementRepository;

    @Autowired
    private VersionEnvironnementService versionEnvironnementService;

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

    private MockMvc restVersionEnvironnementMockMvc;

    private VersionEnvironnement versionEnvironnement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VersionEnvironnementResource versionEnvironnementResource = new VersionEnvironnementResource(versionEnvironnementService);
        this.restVersionEnvironnementMockMvc = MockMvcBuilders.standaloneSetup(versionEnvironnementResource)
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
    public static VersionEnvironnement createEntity(EntityManager em) {
        VersionEnvironnement versionEnvironnement = new VersionEnvironnement()
            .nom(DEFAULT_NOM)
            .datePublication(DEFAULT_DATE_PUBLICATION);
        return versionEnvironnement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VersionEnvironnement createUpdatedEntity(EntityManager em) {
        VersionEnvironnement versionEnvironnement = new VersionEnvironnement()
            .nom(UPDATED_NOM)
            .datePublication(UPDATED_DATE_PUBLICATION);
        return versionEnvironnement;
    }

    @BeforeEach
    public void initTest() {
        versionEnvironnement = createEntity(em);
    }

    @Test
    @Transactional
    public void createVersionEnvironnement() throws Exception {
        int databaseSizeBeforeCreate = versionEnvironnementRepository.findAll().size();

        // Create the VersionEnvironnement
        restVersionEnvironnementMockMvc.perform(post("/api/version-environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionEnvironnement)))
            .andExpect(status().isCreated());

        // Validate the VersionEnvironnement in the database
        List<VersionEnvironnement> versionEnvironnementList = versionEnvironnementRepository.findAll();
        assertThat(versionEnvironnementList).hasSize(databaseSizeBeforeCreate + 1);
        VersionEnvironnement testVersionEnvironnement = versionEnvironnementList.get(versionEnvironnementList.size() - 1);
        assertThat(testVersionEnvironnement.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testVersionEnvironnement.getDatePublication()).isEqualTo(DEFAULT_DATE_PUBLICATION);
    }

    @Test
    @Transactional
    public void createVersionEnvironnementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = versionEnvironnementRepository.findAll().size();

        // Create the VersionEnvironnement with an existing ID
        versionEnvironnement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVersionEnvironnementMockMvc.perform(post("/api/version-environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionEnvironnement)))
            .andExpect(status().isBadRequest());

        // Validate the VersionEnvironnement in the database
        List<VersionEnvironnement> versionEnvironnementList = versionEnvironnementRepository.findAll();
        assertThat(versionEnvironnementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = versionEnvironnementRepository.findAll().size();
        // set the field null
        versionEnvironnement.setNom(null);

        // Create the VersionEnvironnement, which fails.

        restVersionEnvironnementMockMvc.perform(post("/api/version-environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionEnvironnement)))
            .andExpect(status().isBadRequest());

        List<VersionEnvironnement> versionEnvironnementList = versionEnvironnementRepository.findAll();
        assertThat(versionEnvironnementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVersionEnvironnements() throws Exception {
        // Initialize the database
        versionEnvironnementRepository.saveAndFlush(versionEnvironnement);

        // Get all the versionEnvironnementList
        restVersionEnvironnementMockMvc.perform(get("/api/version-environnements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(versionEnvironnement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].datePublication").value(hasItem(DEFAULT_DATE_PUBLICATION.toString())));
    }
    
    @Test
    @Transactional
    public void getVersionEnvironnement() throws Exception {
        // Initialize the database
        versionEnvironnementRepository.saveAndFlush(versionEnvironnement);

        // Get the versionEnvironnement
        restVersionEnvironnementMockMvc.perform(get("/api/version-environnements/{id}", versionEnvironnement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(versionEnvironnement.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.datePublication").value(DEFAULT_DATE_PUBLICATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVersionEnvironnement() throws Exception {
        // Get the versionEnvironnement
        restVersionEnvironnementMockMvc.perform(get("/api/version-environnements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVersionEnvironnement() throws Exception {
        // Initialize the database
        versionEnvironnementService.save(versionEnvironnement);

        int databaseSizeBeforeUpdate = versionEnvironnementRepository.findAll().size();

        // Update the versionEnvironnement
        VersionEnvironnement updatedVersionEnvironnement = versionEnvironnementRepository.findById(versionEnvironnement.getId()).get();
        // Disconnect from session so that the updates on updatedVersionEnvironnement are not directly saved in db
        em.detach(updatedVersionEnvironnement);
        updatedVersionEnvironnement
            .nom(UPDATED_NOM)
            .datePublication(UPDATED_DATE_PUBLICATION);

        restVersionEnvironnementMockMvc.perform(put("/api/version-environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVersionEnvironnement)))
            .andExpect(status().isOk());

        // Validate the VersionEnvironnement in the database
        List<VersionEnvironnement> versionEnvironnementList = versionEnvironnementRepository.findAll();
        assertThat(versionEnvironnementList).hasSize(databaseSizeBeforeUpdate);
        VersionEnvironnement testVersionEnvironnement = versionEnvironnementList.get(versionEnvironnementList.size() - 1);
        assertThat(testVersionEnvironnement.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testVersionEnvironnement.getDatePublication()).isEqualTo(UPDATED_DATE_PUBLICATION);
    }

    @Test
    @Transactional
    public void updateNonExistingVersionEnvironnement() throws Exception {
        int databaseSizeBeforeUpdate = versionEnvironnementRepository.findAll().size();

        // Create the VersionEnvironnement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVersionEnvironnementMockMvc.perform(put("/api/version-environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionEnvironnement)))
            .andExpect(status().isBadRequest());

        // Validate the VersionEnvironnement in the database
        List<VersionEnvironnement> versionEnvironnementList = versionEnvironnementRepository.findAll();
        assertThat(versionEnvironnementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVersionEnvironnement() throws Exception {
        // Initialize the database
        versionEnvironnementService.save(versionEnvironnement);

        int databaseSizeBeforeDelete = versionEnvironnementRepository.findAll().size();

        // Delete the versionEnvironnement
        restVersionEnvironnementMockMvc.perform(delete("/api/version-environnements/{id}", versionEnvironnement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VersionEnvironnement> versionEnvironnementList = versionEnvironnementRepository.findAll();
        assertThat(versionEnvironnementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
