package com.ibp.web.rest;

import com.ibp.DemoAgatheApp;
import com.ibp.domain.WebApp;
import com.ibp.repository.WebAppRepository;
import com.ibp.service.WebAppService;
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
 * Integration tests for the {@link WebAppResource} REST controller.
 */
@SpringBootTest(classes = DemoAgatheApp.class)
public class WebAppResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ID_TASK = "AAAAAAAAAA";
    private static final String UPDATED_ID_TASK = "BBBBBBBBBB";

    private static final String DEFAULT_ID_PROCESS = "AAAAAAAAAA";
    private static final String UPDATED_ID_PROCESS = "BBBBBBBBBB";

    @Autowired
    private WebAppRepository webAppRepository;

    @Autowired
    private WebAppService webAppService;

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

    private MockMvc restWebAppMockMvc;

    private WebApp webApp;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WebAppResource webAppResource = new WebAppResource(webAppService);
        this.restWebAppMockMvc = MockMvcBuilders.standaloneSetup(webAppResource)
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
    public static WebApp createEntity(EntityManager em) {
        WebApp webApp = new WebApp()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .idTask(DEFAULT_ID_TASK)
            .idProcess(DEFAULT_ID_PROCESS);
        return webApp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WebApp createUpdatedEntity(EntityManager em) {
        WebApp webApp = new WebApp()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .idTask(UPDATED_ID_TASK)
            .idProcess(UPDATED_ID_PROCESS);
        return webApp;
    }

    @BeforeEach
    public void initTest() {
        webApp = createEntity(em);
    }

    @Test
    @Transactional
    public void createWebApp() throws Exception {
        int databaseSizeBeforeCreate = webAppRepository.findAll().size();

        // Create the WebApp
        restWebAppMockMvc.perform(post("/api/web-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webApp)))
            .andExpect(status().isCreated());

        // Validate the WebApp in the database
        List<WebApp> webAppList = webAppRepository.findAll();
        assertThat(webAppList).hasSize(databaseSizeBeforeCreate + 1);
        WebApp testWebApp = webAppList.get(webAppList.size() - 1);
        assertThat(testWebApp.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testWebApp.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWebApp.getIdTask()).isEqualTo(DEFAULT_ID_TASK);
        assertThat(testWebApp.getIdProcess()).isEqualTo(DEFAULT_ID_PROCESS);
    }

    @Test
    @Transactional
    public void createWebAppWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webAppRepository.findAll().size();

        // Create the WebApp with an existing ID
        webApp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebAppMockMvc.perform(post("/api/web-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webApp)))
            .andExpect(status().isBadRequest());

        // Validate the WebApp in the database
        List<WebApp> webAppList = webAppRepository.findAll();
        assertThat(webAppList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = webAppRepository.findAll().size();
        // set the field null
        webApp.setNom(null);

        // Create the WebApp, which fails.

        restWebAppMockMvc.perform(post("/api/web-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webApp)))
            .andExpect(status().isBadRequest());

        List<WebApp> webAppList = webAppRepository.findAll();
        assertThat(webAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdTaskIsRequired() throws Exception {
        int databaseSizeBeforeTest = webAppRepository.findAll().size();
        // set the field null
        webApp.setIdTask(null);

        // Create the WebApp, which fails.

        restWebAppMockMvc.perform(post("/api/web-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webApp)))
            .andExpect(status().isBadRequest());

        List<WebApp> webAppList = webAppRepository.findAll();
        assertThat(webAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdProcessIsRequired() throws Exception {
        int databaseSizeBeforeTest = webAppRepository.findAll().size();
        // set the field null
        webApp.setIdProcess(null);

        // Create the WebApp, which fails.

        restWebAppMockMvc.perform(post("/api/web-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webApp)))
            .andExpect(status().isBadRequest());

        List<WebApp> webAppList = webAppRepository.findAll();
        assertThat(webAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWebApps() throws Exception {
        // Initialize the database
        webAppRepository.saveAndFlush(webApp);

        // Get all the webAppList
        restWebAppMockMvc.perform(get("/api/web-apps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webApp.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].idTask").value(hasItem(DEFAULT_ID_TASK)))
            .andExpect(jsonPath("$.[*].idProcess").value(hasItem(DEFAULT_ID_PROCESS)));
    }
    
    @Test
    @Transactional
    public void getWebApp() throws Exception {
        // Initialize the database
        webAppRepository.saveAndFlush(webApp);

        // Get the webApp
        restWebAppMockMvc.perform(get("/api/web-apps/{id}", webApp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(webApp.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.idTask").value(DEFAULT_ID_TASK))
            .andExpect(jsonPath("$.idProcess").value(DEFAULT_ID_PROCESS));
    }

    @Test
    @Transactional
    public void getNonExistingWebApp() throws Exception {
        // Get the webApp
        restWebAppMockMvc.perform(get("/api/web-apps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWebApp() throws Exception {
        // Initialize the database
        webAppService.save(webApp);

        int databaseSizeBeforeUpdate = webAppRepository.findAll().size();

        // Update the webApp
        WebApp updatedWebApp = webAppRepository.findById(webApp.getId()).get();
        // Disconnect from session so that the updates on updatedWebApp are not directly saved in db
        em.detach(updatedWebApp);
        updatedWebApp
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .idTask(UPDATED_ID_TASK)
            .idProcess(UPDATED_ID_PROCESS);

        restWebAppMockMvc.perform(put("/api/web-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWebApp)))
            .andExpect(status().isOk());

        // Validate the WebApp in the database
        List<WebApp> webAppList = webAppRepository.findAll();
        assertThat(webAppList).hasSize(databaseSizeBeforeUpdate);
        WebApp testWebApp = webAppList.get(webAppList.size() - 1);
        assertThat(testWebApp.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testWebApp.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWebApp.getIdTask()).isEqualTo(UPDATED_ID_TASK);
        assertThat(testWebApp.getIdProcess()).isEqualTo(UPDATED_ID_PROCESS);
    }

    @Test
    @Transactional
    public void updateNonExistingWebApp() throws Exception {
        int databaseSizeBeforeUpdate = webAppRepository.findAll().size();

        // Create the WebApp

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWebAppMockMvc.perform(put("/api/web-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webApp)))
            .andExpect(status().isBadRequest());

        // Validate the WebApp in the database
        List<WebApp> webAppList = webAppRepository.findAll();
        assertThat(webAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWebApp() throws Exception {
        // Initialize the database
        webAppService.save(webApp);

        int databaseSizeBeforeDelete = webAppRepository.findAll().size();

        // Delete the webApp
        restWebAppMockMvc.perform(delete("/api/web-apps/{id}", webApp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WebApp> webAppList = webAppRepository.findAll();
        assertThat(webAppList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
