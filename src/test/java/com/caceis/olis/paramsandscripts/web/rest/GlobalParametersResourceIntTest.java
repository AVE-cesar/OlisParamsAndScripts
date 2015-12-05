package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.GlobalParameters;
import com.caceis.olis.paramsandscripts.repository.GlobalParametersRepository;
import com.caceis.olis.paramsandscripts.repository.search.GlobalParametersSearchRepository;

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
 * Test class for the GlobalParametersResource REST controller.
 *
 * @see GlobalParametersResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GlobalParametersResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_TECHNICAL_NAME = "AAAAA";
    private static final String UPDATED_TECHNICAL_NAME = "BBBBB";
    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";
    private static final String DEFAULT_SCRIPT = "AAAAA";
    private static final String UPDATED_SCRIPT = "BBBBB";
    private static final String DEFAULT_TTL = "AAAAA";
    private static final String UPDATED_TTL = "BBBBB";
    private static final String DEFAULT_DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    @Inject
    private GlobalParametersRepository globalParametersRepository;

    @Inject
    private GlobalParametersSearchRepository globalParametersSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGlobalParametersMockMvc;

    private GlobalParameters globalParameters;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GlobalParametersResource globalParametersResource = new GlobalParametersResource();
        ReflectionTestUtils.setField(globalParametersResource, "globalParametersRepository", globalParametersRepository);
        ReflectionTestUtils.setField(globalParametersResource, "globalParametersSearchRepository", globalParametersSearchRepository);
        this.restGlobalParametersMockMvc = MockMvcBuilders.standaloneSetup(globalParametersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        globalParameters = new GlobalParameters();
        globalParameters.setName(DEFAULT_NAME);
        globalParameters.setTechnicalName(DEFAULT_TECHNICAL_NAME);
        globalParameters.setType(DEFAULT_TYPE);
        globalParameters.setScript(DEFAULT_SCRIPT);
        globalParameters.setTtl(DEFAULT_TTL);
        globalParameters.setDefaultValue(DEFAULT_DEFAULT_VALUE);
        globalParameters.setOrder(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createGlobalParameters() throws Exception {
        int databaseSizeBeforeCreate = globalParametersRepository.findAll().size();

        // Create the GlobalParameters

        restGlobalParametersMockMvc.perform(post("/api/globalParameterss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(globalParameters)))
                .andExpect(status().isCreated());

        // Validate the GlobalParameters in the database
        List<GlobalParameters> globalParameterss = globalParametersRepository.findAll();
        assertThat(globalParameterss).hasSize(databaseSizeBeforeCreate + 1);
        GlobalParameters testGlobalParameters = globalParameterss.get(globalParameterss.size() - 1);
        assertThat(testGlobalParameters.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGlobalParameters.getTechnicalName()).isEqualTo(DEFAULT_TECHNICAL_NAME);
        assertThat(testGlobalParameters.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testGlobalParameters.getScript()).isEqualTo(DEFAULT_SCRIPT);
        assertThat(testGlobalParameters.getTtl()).isEqualTo(DEFAULT_TTL);
        assertThat(testGlobalParameters.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testGlobalParameters.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = globalParametersRepository.findAll().size();
        // set the field null
        globalParameters.setName(null);

        // Create the GlobalParameters, which fails.

        restGlobalParametersMockMvc.perform(post("/api/globalParameterss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(globalParameters)))
                .andExpect(status().isBadRequest());

        List<GlobalParameters> globalParameterss = globalParametersRepository.findAll();
        assertThat(globalParameterss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTechnicalNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = globalParametersRepository.findAll().size();
        // set the field null
        globalParameters.setTechnicalName(null);

        // Create the GlobalParameters, which fails.

        restGlobalParametersMockMvc.perform(post("/api/globalParameterss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(globalParameters)))
                .andExpect(status().isBadRequest());

        List<GlobalParameters> globalParameterss = globalParametersRepository.findAll();
        assertThat(globalParameterss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = globalParametersRepository.findAll().size();
        // set the field null
        globalParameters.setType(null);

        // Create the GlobalParameters, which fails.

        restGlobalParametersMockMvc.perform(post("/api/globalParameterss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(globalParameters)))
                .andExpect(status().isBadRequest());

        List<GlobalParameters> globalParameterss = globalParametersRepository.findAll();
        assertThat(globalParameterss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = globalParametersRepository.findAll().size();
        // set the field null
        globalParameters.setOrder(null);

        // Create the GlobalParameters, which fails.

        restGlobalParametersMockMvc.perform(post("/api/globalParameterss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(globalParameters)))
                .andExpect(status().isBadRequest());

        List<GlobalParameters> globalParameterss = globalParametersRepository.findAll();
        assertThat(globalParameterss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGlobalParameterss() throws Exception {
        // Initialize the database
        globalParametersRepository.saveAndFlush(globalParameters);

        // Get all the globalParameterss
        restGlobalParametersMockMvc.perform(get("/api/globalParameterss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(globalParameters.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].technicalName").value(hasItem(DEFAULT_TECHNICAL_NAME.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].script").value(hasItem(DEFAULT_SCRIPT.toString())))
                .andExpect(jsonPath("$.[*].ttl").value(hasItem(DEFAULT_TTL.toString())))
                .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE.toString())))
                .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }

    @Test
    @Transactional
    public void getGlobalParameters() throws Exception {
        // Initialize the database
        globalParametersRepository.saveAndFlush(globalParameters);

        // Get the globalParameters
        restGlobalParametersMockMvc.perform(get("/api/globalParameterss/{id}", globalParameters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(globalParameters.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.technicalName").value(DEFAULT_TECHNICAL_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.script").value(DEFAULT_SCRIPT.toString()))
            .andExpect(jsonPath("$.ttl").value(DEFAULT_TTL.toString()))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingGlobalParameters() throws Exception {
        // Get the globalParameters
        restGlobalParametersMockMvc.perform(get("/api/globalParameterss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGlobalParameters() throws Exception {
        // Initialize the database
        globalParametersRepository.saveAndFlush(globalParameters);

		int databaseSizeBeforeUpdate = globalParametersRepository.findAll().size();

        // Update the globalParameters
        globalParameters.setName(UPDATED_NAME);
        globalParameters.setTechnicalName(UPDATED_TECHNICAL_NAME);
        globalParameters.setType(UPDATED_TYPE);
        globalParameters.setScript(UPDATED_SCRIPT);
        globalParameters.setTtl(UPDATED_TTL);
        globalParameters.setDefaultValue(UPDATED_DEFAULT_VALUE);
        globalParameters.setOrder(UPDATED_ORDER);

        restGlobalParametersMockMvc.perform(put("/api/globalParameterss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(globalParameters)))
                .andExpect(status().isOk());

        // Validate the GlobalParameters in the database
        List<GlobalParameters> globalParameterss = globalParametersRepository.findAll();
        assertThat(globalParameterss).hasSize(databaseSizeBeforeUpdate);
        GlobalParameters testGlobalParameters = globalParameterss.get(globalParameterss.size() - 1);
        assertThat(testGlobalParameters.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGlobalParameters.getTechnicalName()).isEqualTo(UPDATED_TECHNICAL_NAME);
        assertThat(testGlobalParameters.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testGlobalParameters.getScript()).isEqualTo(UPDATED_SCRIPT);
        assertThat(testGlobalParameters.getTtl()).isEqualTo(UPDATED_TTL);
        assertThat(testGlobalParameters.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testGlobalParameters.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void deleteGlobalParameters() throws Exception {
        // Initialize the database
        globalParametersRepository.saveAndFlush(globalParameters);

		int databaseSizeBeforeDelete = globalParametersRepository.findAll().size();

        // Get the globalParameters
        restGlobalParametersMockMvc.perform(delete("/api/globalParameterss/{id}", globalParameters.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GlobalParameters> globalParameterss = globalParametersRepository.findAll();
        assertThat(globalParameterss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
