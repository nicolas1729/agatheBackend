package com.ibp.web.rest;

import com.ibp.DemoAgatheApp;
import com.ibp.domain.RBUrl;
import com.ibp.repository.RBUrlRepository;
import com.ibp.service.RBUrlService;
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
 * Integration tests for the {@link RBUrlResource} REST controller.
 */
@SpringBootTest(classes = DemoAgatheApp.class)
public class RBUrlResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ID_TASK = "AAAAAAAAAA";
    private static final String UPDATED_ID_TASK = "BBBBBBBBBB";

    private static final String DEFAULT_ID_PROCESS = "AAAAAAAAAA";
    private static final String UPDATED_ID_PROCESS = "BBBBBBBBBB";

    @Autowired
    private RBUrlRepository rBUrlRepository;

    @Autowired
    private RBUrlService rBUrlService;

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

    private MockMvc restRBUrlMockMvc;

    private RBUrl rBUrl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RBUrlResource rBUrlResource = new RBUrlResource(rBUrlService);
        this.restRBUrlMockMvc = MockMvcBuilders.standaloneSetup(rBUrlResource)
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
    public static RBUrl createEntity(EntityManager em) {
        RBUrl rBUrl = new RBUrl()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .idTask(DEFAULT_ID_TASK)
            .idProcess(DEFAULT_ID_PROCESS);
        return rBUrl;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RBUrl createUpdatedEntity(EntityManager em) {
        RBUrl rBUrl = new RBUrl()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .idTask(UPDATED_ID_TASK)
            .idProcess(UPDATED_ID_PROCESS);
        return rBUrl;
    }

    @BeforeEach
    public void initTest() {
        rBUrl = createEntity(em);
    }

    @Test
    @Transactional
    public void createRBUrl() throws Exception {
        int databaseSizeBeforeCreate = rBUrlRepository.findAll().size();

        // Create the RBUrl
        restRBUrlMockMvc.perform(post("/api/rb-urls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rBUrl)))
            .andExpect(status().isCreated());

        // Validate the RBUrl in the database
        List<RBUrl> rBUrlList = rBUrlRepository.findAll();
        assertThat(rBUrlList).hasSize(databaseSizeBeforeCreate + 1);
        RBUrl testRBUrl = rBUrlList.get(rBUrlList.size() - 1);
        assertThat(testRBUrl.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRBUrl.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRBUrl.getIdTask()).isEqualTo(DEFAULT_ID_TASK);
        assertThat(testRBUrl.getIdProcess()).isEqualTo(DEFAULT_ID_PROCESS);
    }

    @Test
    @Transactional
    public void createRBUrlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rBUrlRepository.findAll().size();

        // Create the RBUrl with an existing ID
        rBUrl.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRBUrlMockMvc.perform(post("/api/rb-urls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rBUrl)))
            .andExpect(status().isBadRequest());

        // Validate the RBUrl in the database
        List<RBUrl> rBUrlList = rBUrlRepository.findAll();
        assertThat(rBUrlList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = rBUrlRepository.findAll().size();
        // set the field null
        rBUrl.setNom(null);

        // Create the RBUrl, which fails.

        restRBUrlMockMvc.perform(post("/api/rb-urls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rBUrl)))
            .andExpect(status().isBadRequest());

        List<RBUrl> rBUrlList = rBUrlRepository.findAll();
        assertThat(rBUrlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdTaskIsRequired() throws Exception {
        int databaseSizeBeforeTest = rBUrlRepository.findAll().size();
        // set the field null
        rBUrl.setIdTask(null);

        // Create the RBUrl, which fails.

        restRBUrlMockMvc.perform(post("/api/rb-urls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rBUrl)))
            .andExpect(status().isBadRequest());

        List<RBUrl> rBUrlList = rBUrlRepository.findAll();
        assertThat(rBUrlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdProcessIsRequired() throws Exception {
        int databaseSizeBeforeTest = rBUrlRepository.findAll().size();
        // set the field null
        rBUrl.setIdProcess(null);

        // Create the RBUrl, which fails.

        restRBUrlMockMvc.perform(post("/api/rb-urls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rBUrl)))
            .andExpect(status().isBadRequest());

        List<RBUrl> rBUrlList = rBUrlRepository.findAll();
        assertThat(rBUrlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRBUrls() throws Exception {
        // Initialize the database
        rBUrlRepository.saveAndFlush(rBUrl);

        // Get all the rBUrlList
        restRBUrlMockMvc.perform(get("/api/rb-urls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rBUrl.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].idTask").value(hasItem(DEFAULT_ID_TASK)))
            .andExpect(jsonPath("$.[*].idProcess").value(hasItem(DEFAULT_ID_PROCESS)));
    }
    
    @Test
    @Transactional
    public void getRBUrl() throws Exception {
        // Initialize the database
        rBUrlRepository.saveAndFlush(rBUrl);

        // Get the rBUrl
        restRBUrlMockMvc.perform(get("/api/rb-urls/{id}", rBUrl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rBUrl.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.idTask").value(DEFAULT_ID_TASK))
            .andExpect(jsonPath("$.idProcess").value(DEFAULT_ID_PROCESS));
    }

    @Test
    @Transactional
    public void getNonExistingRBUrl() throws Exception {
        // Get the rBUrl
        restRBUrlMockMvc.perform(get("/api/rb-urls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRBUrl() throws Exception {
        // Initialize the database
        rBUrlService.save(rBUrl);

        int databaseSizeBeforeUpdate = rBUrlRepository.findAll().size();

        // Update the rBUrl
        RBUrl updatedRBUrl = rBUrlRepository.findById(rBUrl.getId()).get();
        // Disconnect from session so that the updates on updatedRBUrl are not directly saved in db
        em.detach(updatedRBUrl);
        updatedRBUrl
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .idTask(UPDATED_ID_TASK)
            .idProcess(UPDATED_ID_PROCESS);

        restRBUrlMockMvc.perform(put("/api/rb-urls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRBUrl)))
            .andExpect(status().isOk());

        // Validate the RBUrl in the database
        List<RBUrl> rBUrlList = rBUrlRepository.findAll();
        assertThat(rBUrlList).hasSize(databaseSizeBeforeUpdate);
        RBUrl testRBUrl = rBUrlList.get(rBUrlList.size() - 1);
        assertThat(testRBUrl.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRBUrl.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRBUrl.getIdTask()).isEqualTo(UPDATED_ID_TASK);
        assertThat(testRBUrl.getIdProcess()).isEqualTo(UPDATED_ID_PROCESS);
    }

    @Test
    @Transactional
    public void updateNonExistingRBUrl() throws Exception {
        int databaseSizeBeforeUpdate = rBUrlRepository.findAll().size();

        // Create the RBUrl

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRBUrlMockMvc.perform(put("/api/rb-urls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rBUrl)))
            .andExpect(status().isBadRequest());

        // Validate the RBUrl in the database
        List<RBUrl> rBUrlList = rBUrlRepository.findAll();
        assertThat(rBUrlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRBUrl() throws Exception {
        // Initialize the database
        rBUrlService.save(rBUrl);

        int databaseSizeBeforeDelete = rBUrlRepository.findAll().size();

        // Delete the rBUrl
        restRBUrlMockMvc.perform(delete("/api/rb-urls/{id}", rBUrl.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RBUrl> rBUrlList = rBUrlRepository.findAll();
        assertThat(rBUrlList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
