package com.ibp.web.rest;

import com.ibp.DemoAgatheApp;
import com.ibp.domain.ServeurPublication;
import com.ibp.repository.ServeurPublicationRepository;
import com.ibp.service.ServeurPublicationService;
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
 * Integration tests for the {@link ServeurPublicationResource} REST controller.
 */
@SpringBootTest(classes = DemoAgatheApp.class)
public class ServeurPublicationResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_JBOSS = false;
    private static final Boolean UPDATED_JBOSS = true;

    @Autowired
    private ServeurPublicationRepository serveurPublicationRepository;

    @Autowired
    private ServeurPublicationService serveurPublicationService;

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

    private MockMvc restServeurPublicationMockMvc;

    private ServeurPublication serveurPublication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServeurPublicationResource serveurPublicationResource = new ServeurPublicationResource(serveurPublicationService);
        this.restServeurPublicationMockMvc = MockMvcBuilders.standaloneSetup(serveurPublicationResource)
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
    public static ServeurPublication createEntity(EntityManager em) {
        ServeurPublication serveurPublication = new ServeurPublication()
            .nom(DEFAULT_NOM)
            .path(DEFAULT_PATH)
            .jboss(DEFAULT_JBOSS);
        return serveurPublication;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServeurPublication createUpdatedEntity(EntityManager em) {
        ServeurPublication serveurPublication = new ServeurPublication()
            .nom(UPDATED_NOM)
            .path(UPDATED_PATH)
            .jboss(UPDATED_JBOSS);
        return serveurPublication;
    }

    @BeforeEach
    public void initTest() {
        serveurPublication = createEntity(em);
    }

    @Test
    @Transactional
    public void createServeurPublication() throws Exception {
        int databaseSizeBeforeCreate = serveurPublicationRepository.findAll().size();

        // Create the ServeurPublication
        restServeurPublicationMockMvc.perform(post("/api/serveur-publications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveurPublication)))
            .andExpect(status().isCreated());

        // Validate the ServeurPublication in the database
        List<ServeurPublication> serveurPublicationList = serveurPublicationRepository.findAll();
        assertThat(serveurPublicationList).hasSize(databaseSizeBeforeCreate + 1);
        ServeurPublication testServeurPublication = serveurPublicationList.get(serveurPublicationList.size() - 1);
        assertThat(testServeurPublication.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testServeurPublication.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testServeurPublication.isJboss()).isEqualTo(DEFAULT_JBOSS);
    }

    @Test
    @Transactional
    public void createServeurPublicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serveurPublicationRepository.findAll().size();

        // Create the ServeurPublication with an existing ID
        serveurPublication.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServeurPublicationMockMvc.perform(post("/api/serveur-publications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveurPublication)))
            .andExpect(status().isBadRequest());

        // Validate the ServeurPublication in the database
        List<ServeurPublication> serveurPublicationList = serveurPublicationRepository.findAll();
        assertThat(serveurPublicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = serveurPublicationRepository.findAll().size();
        // set the field null
        serveurPublication.setNom(null);

        // Create the ServeurPublication, which fails.

        restServeurPublicationMockMvc.perform(post("/api/serveur-publications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveurPublication)))
            .andExpect(status().isBadRequest());

        List<ServeurPublication> serveurPublicationList = serveurPublicationRepository.findAll();
        assertThat(serveurPublicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = serveurPublicationRepository.findAll().size();
        // set the field null
        serveurPublication.setPath(null);

        // Create the ServeurPublication, which fails.

        restServeurPublicationMockMvc.perform(post("/api/serveur-publications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveurPublication)))
            .andExpect(status().isBadRequest());

        List<ServeurPublication> serveurPublicationList = serveurPublicationRepository.findAll();
        assertThat(serveurPublicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJbossIsRequired() throws Exception {
        int databaseSizeBeforeTest = serveurPublicationRepository.findAll().size();
        // set the field null
        serveurPublication.setJboss(null);

        // Create the ServeurPublication, which fails.

        restServeurPublicationMockMvc.perform(post("/api/serveur-publications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveurPublication)))
            .andExpect(status().isBadRequest());

        List<ServeurPublication> serveurPublicationList = serveurPublicationRepository.findAll();
        assertThat(serveurPublicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServeurPublications() throws Exception {
        // Initialize the database
        serveurPublicationRepository.saveAndFlush(serveurPublication);

        // Get all the serveurPublicationList
        restServeurPublicationMockMvc.perform(get("/api/serveur-publications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serveurPublication.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].jboss").value(hasItem(DEFAULT_JBOSS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getServeurPublication() throws Exception {
        // Initialize the database
        serveurPublicationRepository.saveAndFlush(serveurPublication);

        // Get the serveurPublication
        restServeurPublicationMockMvc.perform(get("/api/serveur-publications/{id}", serveurPublication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serveurPublication.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.jboss").value(DEFAULT_JBOSS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingServeurPublication() throws Exception {
        // Get the serveurPublication
        restServeurPublicationMockMvc.perform(get("/api/serveur-publications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServeurPublication() throws Exception {
        // Initialize the database
        serveurPublicationService.save(serveurPublication);

        int databaseSizeBeforeUpdate = serveurPublicationRepository.findAll().size();

        // Update the serveurPublication
        ServeurPublication updatedServeurPublication = serveurPublicationRepository.findById(serveurPublication.getId()).get();
        // Disconnect from session so that the updates on updatedServeurPublication are not directly saved in db
        em.detach(updatedServeurPublication);
        updatedServeurPublication
            .nom(UPDATED_NOM)
            .path(UPDATED_PATH)
            .jboss(UPDATED_JBOSS);

        restServeurPublicationMockMvc.perform(put("/api/serveur-publications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServeurPublication)))
            .andExpect(status().isOk());

        // Validate the ServeurPublication in the database
        List<ServeurPublication> serveurPublicationList = serveurPublicationRepository.findAll();
        assertThat(serveurPublicationList).hasSize(databaseSizeBeforeUpdate);
        ServeurPublication testServeurPublication = serveurPublicationList.get(serveurPublicationList.size() - 1);
        assertThat(testServeurPublication.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testServeurPublication.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testServeurPublication.isJboss()).isEqualTo(UPDATED_JBOSS);
    }

    @Test
    @Transactional
    public void updateNonExistingServeurPublication() throws Exception {
        int databaseSizeBeforeUpdate = serveurPublicationRepository.findAll().size();

        // Create the ServeurPublication

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServeurPublicationMockMvc.perform(put("/api/serveur-publications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveurPublication)))
            .andExpect(status().isBadRequest());

        // Validate the ServeurPublication in the database
        List<ServeurPublication> serveurPublicationList = serveurPublicationRepository.findAll();
        assertThat(serveurPublicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServeurPublication() throws Exception {
        // Initialize the database
        serveurPublicationService.save(serveurPublication);

        int databaseSizeBeforeDelete = serveurPublicationRepository.findAll().size();

        // Delete the serveurPublication
        restServeurPublicationMockMvc.perform(delete("/api/serveur-publications/{id}", serveurPublication.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServeurPublication> serveurPublicationList = serveurPublicationRepository.findAll();
        assertThat(serveurPublicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
