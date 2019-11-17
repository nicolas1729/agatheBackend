package com.ibp.web.rest;

import com.ibp.DemoAgatheApp;
import com.ibp.domain.VersionPrivative;
import com.ibp.repository.VersionPrivativeRepository;
import com.ibp.service.VersionPrivativeService;
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
 * Integration tests for the {@link VersionPrivativeResource} REST controller.
 */
@SpringBootTest(classes = DemoAgatheApp.class)
public class VersionPrivativeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    @Autowired
    private VersionPrivativeRepository versionPrivativeRepository;

    @Autowired
    private VersionPrivativeService versionPrivativeService;

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

    private MockMvc restVersionPrivativeMockMvc;

    private VersionPrivative versionPrivative;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VersionPrivativeResource versionPrivativeResource = new VersionPrivativeResource(versionPrivativeService);
        this.restVersionPrivativeMockMvc = MockMvcBuilders.standaloneSetup(versionPrivativeResource)
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
    public static VersionPrivative createEntity(EntityManager em) {
        VersionPrivative versionPrivative = new VersionPrivative()
            .nom(DEFAULT_NOM)
            .commentaire(DEFAULT_COMMENTAIRE);
        return versionPrivative;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VersionPrivative createUpdatedEntity(EntityManager em) {
        VersionPrivative versionPrivative = new VersionPrivative()
            .nom(UPDATED_NOM)
            .commentaire(UPDATED_COMMENTAIRE);
        return versionPrivative;
    }

    @BeforeEach
    public void initTest() {
        versionPrivative = createEntity(em);
    }

    @Test
    @Transactional
    public void createVersionPrivative() throws Exception {
        int databaseSizeBeforeCreate = versionPrivativeRepository.findAll().size();

        // Create the VersionPrivative
        restVersionPrivativeMockMvc.perform(post("/api/version-privatives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionPrivative)))
            .andExpect(status().isCreated());

        // Validate the VersionPrivative in the database
        List<VersionPrivative> versionPrivativeList = versionPrivativeRepository.findAll();
        assertThat(versionPrivativeList).hasSize(databaseSizeBeforeCreate + 1);
        VersionPrivative testVersionPrivative = versionPrivativeList.get(versionPrivativeList.size() - 1);
        assertThat(testVersionPrivative.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testVersionPrivative.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void createVersionPrivativeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = versionPrivativeRepository.findAll().size();

        // Create the VersionPrivative with an existing ID
        versionPrivative.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVersionPrivativeMockMvc.perform(post("/api/version-privatives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionPrivative)))
            .andExpect(status().isBadRequest());

        // Validate the VersionPrivative in the database
        List<VersionPrivative> versionPrivativeList = versionPrivativeRepository.findAll();
        assertThat(versionPrivativeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = versionPrivativeRepository.findAll().size();
        // set the field null
        versionPrivative.setNom(null);

        // Create the VersionPrivative, which fails.

        restVersionPrivativeMockMvc.perform(post("/api/version-privatives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionPrivative)))
            .andExpect(status().isBadRequest());

        List<VersionPrivative> versionPrivativeList = versionPrivativeRepository.findAll();
        assertThat(versionPrivativeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVersionPrivatives() throws Exception {
        // Initialize the database
        versionPrivativeRepository.saveAndFlush(versionPrivative);

        // Get all the versionPrivativeList
        restVersionPrivativeMockMvc.perform(get("/api/version-privatives?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(versionPrivative.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)));
    }
    
    @Test
    @Transactional
    public void getVersionPrivative() throws Exception {
        // Initialize the database
        versionPrivativeRepository.saveAndFlush(versionPrivative);

        // Get the versionPrivative
        restVersionPrivativeMockMvc.perform(get("/api/version-privatives/{id}", versionPrivative.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(versionPrivative.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE));
    }

    @Test
    @Transactional
    public void getNonExistingVersionPrivative() throws Exception {
        // Get the versionPrivative
        restVersionPrivativeMockMvc.perform(get("/api/version-privatives/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVersionPrivative() throws Exception {
        // Initialize the database
        versionPrivativeService.save(versionPrivative);

        int databaseSizeBeforeUpdate = versionPrivativeRepository.findAll().size();

        // Update the versionPrivative
        VersionPrivative updatedVersionPrivative = versionPrivativeRepository.findById(versionPrivative.getId()).get();
        // Disconnect from session so that the updates on updatedVersionPrivative are not directly saved in db
        em.detach(updatedVersionPrivative);
        updatedVersionPrivative
            .nom(UPDATED_NOM)
            .commentaire(UPDATED_COMMENTAIRE);

        restVersionPrivativeMockMvc.perform(put("/api/version-privatives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVersionPrivative)))
            .andExpect(status().isOk());

        // Validate the VersionPrivative in the database
        List<VersionPrivative> versionPrivativeList = versionPrivativeRepository.findAll();
        assertThat(versionPrivativeList).hasSize(databaseSizeBeforeUpdate);
        VersionPrivative testVersionPrivative = versionPrivativeList.get(versionPrivativeList.size() - 1);
        assertThat(testVersionPrivative.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testVersionPrivative.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingVersionPrivative() throws Exception {
        int databaseSizeBeforeUpdate = versionPrivativeRepository.findAll().size();

        // Create the VersionPrivative

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVersionPrivativeMockMvc.perform(put("/api/version-privatives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionPrivative)))
            .andExpect(status().isBadRequest());

        // Validate the VersionPrivative in the database
        List<VersionPrivative> versionPrivativeList = versionPrivativeRepository.findAll();
        assertThat(versionPrivativeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVersionPrivative() throws Exception {
        // Initialize the database
        versionPrivativeService.save(versionPrivative);

        int databaseSizeBeforeDelete = versionPrivativeRepository.findAll().size();

        // Delete the versionPrivative
        restVersionPrivativeMockMvc.perform(delete("/api/version-privatives/{id}", versionPrivative.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VersionPrivative> versionPrivativeList = versionPrivativeRepository.findAll();
        assertThat(versionPrivativeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
