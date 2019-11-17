package com.ibp.web.rest;

import com.ibp.DemoAgatheApp;
import com.ibp.domain.Entree;
import com.ibp.repository.EntreeRepository;
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
 * Integration tests for the {@link EntreeResource} REST controller.
 */
@SpringBootTest(classes = DemoAgatheApp.class)
public class EntreeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_INFOBULLE = "AAAAAAAAAA";
    private static final String UPDATED_INFOBULLE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EntreeRepository entreeRepository;

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

    private MockMvc restEntreeMockMvc;

    private Entree entree;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntreeResource entreeResource = new EntreeResource(entreeRepository);
        this.restEntreeMockMvc = MockMvcBuilders.standaloneSetup(entreeResource)
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
    public static Entree createEntity(EntityManager em) {
        Entree entree = new Entree()
            .nom(DEFAULT_NOM)
            .infobulle(DEFAULT_INFOBULLE)
            .commentaire(DEFAULT_COMMENTAIRE)
            .description(DEFAULT_DESCRIPTION);
        return entree;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entree createUpdatedEntity(EntityManager em) {
        Entree entree = new Entree()
            .nom(UPDATED_NOM)
            .infobulle(UPDATED_INFOBULLE)
            .commentaire(UPDATED_COMMENTAIRE)
            .description(UPDATED_DESCRIPTION);
        return entree;
    }

    @BeforeEach
    public void initTest() {
        entree = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntree() throws Exception {
        int databaseSizeBeforeCreate = entreeRepository.findAll().size();

        // Create the Entree
        restEntreeMockMvc.perform(post("/api/entrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entree)))
            .andExpect(status().isCreated());

        // Validate the Entree in the database
        List<Entree> entreeList = entreeRepository.findAll();
        assertThat(entreeList).hasSize(databaseSizeBeforeCreate + 1);
        Entree testEntree = entreeList.get(entreeList.size() - 1);
        assertThat(testEntree.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEntree.getInfobulle()).isEqualTo(DEFAULT_INFOBULLE);
        assertThat(testEntree.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testEntree.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createEntreeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entreeRepository.findAll().size();

        // Create the Entree with an existing ID
        entree.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntreeMockMvc.perform(post("/api/entrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entree)))
            .andExpect(status().isBadRequest());

        // Validate the Entree in the database
        List<Entree> entreeList = entreeRepository.findAll();
        assertThat(entreeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = entreeRepository.findAll().size();
        // set the field null
        entree.setNom(null);

        // Create the Entree, which fails.

        restEntreeMockMvc.perform(post("/api/entrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entree)))
            .andExpect(status().isBadRequest());

        List<Entree> entreeList = entreeRepository.findAll();
        assertThat(entreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntrees() throws Exception {
        // Initialize the database
        entreeRepository.saveAndFlush(entree);

        // Get all the entreeList
        restEntreeMockMvc.perform(get("/api/entrees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entree.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].infobulle").value(hasItem(DEFAULT_INFOBULLE)))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getEntree() throws Exception {
        // Initialize the database
        entreeRepository.saveAndFlush(entree);

        // Get the entree
        restEntreeMockMvc.perform(get("/api/entrees/{id}", entree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entree.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.infobulle").value(DEFAULT_INFOBULLE))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingEntree() throws Exception {
        // Get the entree
        restEntreeMockMvc.perform(get("/api/entrees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntree() throws Exception {
        // Initialize the database
        entreeRepository.saveAndFlush(entree);

        int databaseSizeBeforeUpdate = entreeRepository.findAll().size();

        // Update the entree
        Entree updatedEntree = entreeRepository.findById(entree.getId()).get();
        // Disconnect from session so that the updates on updatedEntree are not directly saved in db
        em.detach(updatedEntree);
        updatedEntree
            .nom(UPDATED_NOM)
            .infobulle(UPDATED_INFOBULLE)
            .commentaire(UPDATED_COMMENTAIRE)
            .description(UPDATED_DESCRIPTION);

        restEntreeMockMvc.perform(put("/api/entrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntree)))
            .andExpect(status().isOk());

        // Validate the Entree in the database
        List<Entree> entreeList = entreeRepository.findAll();
        assertThat(entreeList).hasSize(databaseSizeBeforeUpdate);
        Entree testEntree = entreeList.get(entreeList.size() - 1);
        assertThat(testEntree.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEntree.getInfobulle()).isEqualTo(UPDATED_INFOBULLE);
        assertThat(testEntree.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testEntree.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingEntree() throws Exception {
        int databaseSizeBeforeUpdate = entreeRepository.findAll().size();

        // Create the Entree

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntreeMockMvc.perform(put("/api/entrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entree)))
            .andExpect(status().isBadRequest());

        // Validate the Entree in the database
        List<Entree> entreeList = entreeRepository.findAll();
        assertThat(entreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntree() throws Exception {
        // Initialize the database
        entreeRepository.saveAndFlush(entree);

        int databaseSizeBeforeDelete = entreeRepository.findAll().size();

        // Delete the entree
        restEntreeMockMvc.perform(delete("/api/entrees/{id}", entree.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entree> entreeList = entreeRepository.findAll();
        assertThat(entreeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
