package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.AGACAuthorization;
import com.caceis.olis.paramsandscripts.repository.AGACAuthorizationRepository;
import com.caceis.olis.paramsandscripts.repository.search.AGACAuthorizationSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AGACAuthorizationResource REST controller.
 *
 * @see AGACAuthorizationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AGACAuthorizationResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private AGACAuthorizationRepository aGACAuthorizationRepository;

    @Inject
    private AGACAuthorizationSearchRepository aGACAuthorizationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAGACAuthorizationMockMvc;

    private AGACAuthorization aGACAuthorization;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AGACAuthorizationResource aGACAuthorizationResource = new AGACAuthorizationResource();
        ReflectionTestUtils.setField(aGACAuthorizationResource, "aGACAuthorizationRepository", aGACAuthorizationRepository);
        ReflectionTestUtils.setField(aGACAuthorizationResource, "aGACAuthorizationSearchRepository", aGACAuthorizationSearchRepository);
        this.restAGACAuthorizationMockMvc = MockMvcBuilders.standaloneSetup(aGACAuthorizationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        aGACAuthorization = new AGACAuthorization();
        aGACAuthorization.setCode(DEFAULT_CODE);
        aGACAuthorization.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAGACAuthorization() throws Exception {
        int databaseSizeBeforeCreate = aGACAuthorizationRepository.findAll().size();

        // Create the AGACAuthorization

        restAGACAuthorizationMockMvc.perform(post("/api/aGACAuthorizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aGACAuthorization)))
                .andExpect(status().isCreated());

        // Validate the AGACAuthorization in the database
        List<AGACAuthorization> aGACAuthorizations = aGACAuthorizationRepository.findAll();
        assertThat(aGACAuthorizations).hasSize(databaseSizeBeforeCreate + 1);
        AGACAuthorization testAGACAuthorization = aGACAuthorizations.get(aGACAuthorizations.size() - 1);
        assertThat(testAGACAuthorization.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAGACAuthorization.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = aGACAuthorizationRepository.findAll().size();
        // set the field null
        aGACAuthorization.setCode(null);

        // Create the AGACAuthorization, which fails.

        restAGACAuthorizationMockMvc.perform(post("/api/aGACAuthorizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aGACAuthorization)))
                .andExpect(status().isBadRequest());

        List<AGACAuthorization> aGACAuthorizations = aGACAuthorizationRepository.findAll();
        assertThat(aGACAuthorizations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAGACAuthorizations() throws Exception {
        // Initialize the database
        aGACAuthorizationRepository.saveAndFlush(aGACAuthorization);

        // Get all the aGACAuthorizations
        restAGACAuthorizationMockMvc.perform(get("/api/aGACAuthorizations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aGACAuthorization.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAGACAuthorization() throws Exception {
        // Initialize the database
        aGACAuthorizationRepository.saveAndFlush(aGACAuthorization);

        // Get the aGACAuthorization
        restAGACAuthorizationMockMvc.perform(get("/api/aGACAuthorizations/{id}", aGACAuthorization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(aGACAuthorization.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAGACAuthorization() throws Exception {
        // Get the aGACAuthorization
        restAGACAuthorizationMockMvc.perform(get("/api/aGACAuthorizations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAGACAuthorization() throws Exception {
        // Initialize the database
        aGACAuthorizationRepository.saveAndFlush(aGACAuthorization);

		int databaseSizeBeforeUpdate = aGACAuthorizationRepository.findAll().size();

        // Update the aGACAuthorization
        aGACAuthorization.setCode(UPDATED_CODE);
        aGACAuthorization.setName(UPDATED_NAME);

        restAGACAuthorizationMockMvc.perform(put("/api/aGACAuthorizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aGACAuthorization)))
                .andExpect(status().isOk());

        // Validate the AGACAuthorization in the database
        List<AGACAuthorization> aGACAuthorizations = aGACAuthorizationRepository.findAll();
        assertThat(aGACAuthorizations).hasSize(databaseSizeBeforeUpdate);
        AGACAuthorization testAGACAuthorization = aGACAuthorizations.get(aGACAuthorizations.size() - 1);
        assertThat(testAGACAuthorization.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAGACAuthorization.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteAGACAuthorization() throws Exception {
        // Initialize the database
        aGACAuthorizationRepository.saveAndFlush(aGACAuthorization);

		int databaseSizeBeforeDelete = aGACAuthorizationRepository.findAll().size();

        // Get the aGACAuthorization
        restAGACAuthorizationMockMvc.perform(delete("/api/aGACAuthorizations/{id}", aGACAuthorization.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AGACAuthorization> aGACAuthorizations = aGACAuthorizationRepository.findAll();
        assertThat(aGACAuthorizations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
