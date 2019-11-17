package com.ibp.web.rest;

import com.ibp.DemoAgatheApp;
import com.ibp.domain.VersionCommunautaire;
import com.ibp.repository.VersionCommunautaireRepository;
import com.ibp.service.VersionCommunautaireService;
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
 * Integration tests for the {@link VersionCommunautaireResource} REST controller.
 */
@SpringBootTest(classes = DemoAgatheApp.class)
public class VersionCommunautaireResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    @Autowired
    private VersionCommunautaireRepository versionCommunautaireRepository;

    @Autowired
    private VersionCommunautaireService versionCommunautaireService;

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

    private MockMvc restVersionCommunautaireMockMvc;

    private VersionCommunautaire versionCommunautaire;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VersionCommunautaireResource versionCommunautaireResource = new VersionCommunautaireResource(versionCommunautaireService);
        this.restVersionCommunautaireMockMvc = MockMvcBuilders.standaloneSetup(versionCommunautaireResource)
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
    public static VersionCommunautaire createEntity(EntityManager em) {
        VersionCommunautaire versionCommunautaire = new VersionCommunautaire()
            .nom(DEFAULT_NOM)
            .commentaire(DEFAULT_COMMENTAIRE);
        return versionCommunautaire;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VersionCommunautaire createUpdatedEntity(EntityManager em) {
        VersionCommunautaire versionCommunautaire = new VersionCommunautaire()
            .nom(UPDATED_NOM)
            .commentaire(UPDATED_COMMENTAIRE);
        return versionCommunautaire;
    }

    @BeforeEach
    public void initTest() {
        versionCommunautaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createVersionCommunautaire() throws Exception {
        int databaseSizeBeforeCreate = versionCommunautaireRepository.findAll().size();

        // Create the VersionCommunautaire
        restVersionCommunautaireMockMvc.perform(post("/api/version-communautaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionCommunautaire)))
            .andExpect(status().isCreated());

        // Validate the VersionCommunautaire in the database
        List<VersionCommunautaire> versionCommunautaireList = versionCommunautaireRepository.findAll();
        assertThat(versionCommunautaireList).hasSize(databaseSizeBeforeCreate + 1);
        VersionCommunautaire testVersionCommunautaire = versionCommunautaireList.get(versionCommunautaireList.size() - 1);
        assertThat(testVersionCommunautaire.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testVersionCommunautaire.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void createVersionCommunautaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = versionCommunautaireRepository.findAll().size();

        // Create the VersionCommunautaire with an existing ID
        versionCommunautaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVersionCommunautaireMockMvc.perform(post("/api/version-communautaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionCommunautaire)))
            .andExpect(status().isBadRequest());

        // Validate the VersionCommunautaire in the database
        List<VersionCommunautaire> versionCommunautaireList = versionCommunautaireRepository.findAll();
        assertThat(versionCommunautaireList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = versionCommunautaireRepository.findAll().size();
        // set the field null
        versionCommunautaire.setNom(null);

        // Create the VersionCommunautaire, which fails.

        restVersionCommunautaireMockMvc.perform(post("/api/version-communautaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionCommunautaire)))
            .andExpect(status().isBadRequest());

        List<VersionCommunautaire> versionCommunautaireList = versionCommunautaireRepository.findAll();
        assertThat(versionCommunautaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVersionCommunautaires() throws Exception {
        // Initialize the database
        versionCommunautaireRepository.saveAndFlush(versionCommunautaire);

        // Get all the versionCommunautaireList
        restVersionCommunautaireMockMvc.perform(get("/api/version-communautaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(versionCommunautaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)));
    }
    
    @Test
    @Transactional
    public void getVersionCommunautaire() throws Exception {
        // Initialize the database
        versionCommunautaireRepository.saveAndFlush(versionCommunautaire);

        // Get the versionCommunautaire
        restVersionCommunautaireMockMvc.perform(get("/api/version-communautaires/{id}", versionCommunautaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(versionCommunautaire.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE));
    }

    @Test
    @Transactional
    public void getNonExistingVersionCommunautaire() throws Exception {
        // Get the versionCommunautaire
        restVersionCommunautaireMockMvc.perform(get("/api/version-communautaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVersionCommunautaire() throws Exception {
        // Initialize the database
        versionCommunautaireService.save(versionCommunautaire);

        int databaseSizeBeforeUpdate = versionCommunautaireRepository.findAll().size();

        // Update the versionCommunautaire
        VersionCommunautaire updatedVersionCommunautaire = versionCommunautaireRepository.findById(versionCommunautaire.getId()).get();
        // Disconnect from session so that the updates on updatedVersionCommunautaire are not directly saved in db
        em.detach(updatedVersionCommunautaire);
        updatedVersionCommunautaire
            .nom(UPDATED_NOM)
            .commentaire(UPDATED_COMMENTAIRE);

        restVersionCommunautaireMockMvc.perform(put("/api/version-communautaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVersionCommunautaire)))
            .andExpect(status().isOk());

        // Validate the VersionCommunautaire in the database
        List<VersionCommunautaire> versionCommunautaireList = versionCommunautaireRepository.findAll();
        assertThat(versionCommunautaireList).hasSize(databaseSizeBeforeUpdate);
        VersionCommunautaire testVersionCommunautaire = versionCommunautaireList.get(versionCommunautaireList.size() - 1);
        assertThat(testVersionCommunautaire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testVersionCommunautaire.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingVersionCommunautaire() throws Exception {
        int databaseSizeBeforeUpdate = versionCommunautaireRepository.findAll().size();

        // Create the VersionCommunautaire

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVersionCommunautaireMockMvc.perform(put("/api/version-communautaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(versionCommunautaire)))
            .andExpect(status().isBadRequest());

        // Validate the VersionCommunautaire in the database
        List<VersionCommunautaire> versionCommunautaireList = versionCommunautaireRepository.findAll();
        assertThat(versionCommunautaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVersionCommunautaire() throws Exception {
        // Initialize the database
        versionCommunautaireService.save(versionCommunautaire);

        int databaseSizeBeforeDelete = versionCommunautaireRepository.findAll().size();

        // Delete the versionCommunautaire
        restVersionCommunautaireMockMvc.perform(delete("/api/version-communautaires/{id}", versionCommunautaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VersionCommunautaire> versionCommunautaireList = versionCommunautaireRepository.findAll();
        assertThat(versionCommunautaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
