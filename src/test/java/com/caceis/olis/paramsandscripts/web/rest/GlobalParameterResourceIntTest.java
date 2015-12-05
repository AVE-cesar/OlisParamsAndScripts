package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.GlobalParameter;
import com.caceis.olis.paramsandscripts.repository.GlobalParameterRepository;
import com.caceis.olis.paramsandscripts.repository.search.GlobalParameterSearchRepository;

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
 * Test class for the GlobalParameterResource REST controller.
 *
 * @see GlobalParameterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GlobalParameterResourceIntTest {

    private static final String DEFAULT_KEY = "AAAAA";
    private static final String UPDATED_KEY = "BBBBB";
    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private GlobalParameterRepository globalParameterRepository;

    @Inject
    private GlobalParameterSearchRepository globalParameterSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGlobalParameterMockMvc;

    private GlobalParameter globalParameter;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GlobalParameterResource globalParameterResource = new GlobalParameterResource();
        ReflectionTestUtils.setField(globalParameterResource, "globalParameterRepository", globalParameterRepository);
        ReflectionTestUtils.setField(globalParameterResource, "globalParameterSearchRepository", globalParameterSearchRepository);
        this.restGlobalParameterMockMvc = MockMvcBuilders.standaloneSetup(globalParameterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        globalParameter = new GlobalParameter();
        globalParameter.setKey(DEFAULT_KEY);
        globalParameter.setValue(DEFAULT_VALUE);
        globalParameter.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createGlobalParameter() throws Exception {
        int databaseSizeBeforeCreate = globalParameterRepository.findAll().size();

        // Create the GlobalParameter

        restGlobalParameterMockMvc.perform(post("/api/globalParameters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(globalParameter)))
                .andExpect(status().isCreated());

        // Validate the GlobalParameter in the database
        List<GlobalParameter> globalParameters = globalParameterRepository.findAll();
        assertThat(globalParameters).hasSize(databaseSizeBeforeCreate + 1);
        GlobalParameter testGlobalParameter = globalParameters.get(globalParameters.size() - 1);
        assertThat(testGlobalParameter.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testGlobalParameter.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testGlobalParameter.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = globalParameterRepository.findAll().size();
        // set the field null
        globalParameter.setKey(null);

        // Create the GlobalParameter, which fails.

        restGlobalParameterMockMvc.perform(post("/api/globalParameters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(globalParameter)))
                .andExpect(status().isBadRequest());

        List<GlobalParameter> globalParameters = globalParameterRepository.findAll();
        assertThat(globalParameters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = globalParameterRepository.findAll().size();
        // set the field null
        globalParameter.setValue(null);

        // Create the GlobalParameter, which fails.

        restGlobalParameterMockMvc.perform(post("/api/globalParameters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(globalParameter)))
                .andExpect(status().isBadRequest());

        List<GlobalParameter> globalParameters = globalParameterRepository.findAll();
        assertThat(globalParameters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGlobalParameters() throws Exception {
        // Initialize the database
        globalParameterRepository.saveAndFlush(globalParameter);

        // Get all the globalParameters
        restGlobalParameterMockMvc.perform(get("/api/globalParameters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(globalParameter.getId())))
                .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getGlobalParameter() throws Exception {
        // Initialize the database
        globalParameterRepository.saveAndFlush(globalParameter);

        // Get the globalParameter
        restGlobalParameterMockMvc.perform(get("/api/globalParameters/{id}", globalParameter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(globalParameter.getId()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGlobalParameter() throws Exception {
        // Get the globalParameter
        restGlobalParameterMockMvc.perform(get("/api/globalParameters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGlobalParameter() throws Exception {
        // Initialize the database
        globalParameterRepository.saveAndFlush(globalParameter);

		int databaseSizeBeforeUpdate = globalParameterRepository.findAll().size();

        // Update the globalParameter
        globalParameter.setKey(UPDATED_KEY);
        globalParameter.setValue(UPDATED_VALUE);
        globalParameter.setDescription(UPDATED_DESCRIPTION);

        restGlobalParameterMockMvc.perform(put("/api/globalParameters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(globalParameter)))
                .andExpect(status().isOk());

        // Validate the GlobalParameter in the database
        List<GlobalParameter> globalParameters = globalParameterRepository.findAll();
        assertThat(globalParameters).hasSize(databaseSizeBeforeUpdate);
        GlobalParameter testGlobalParameter = globalParameters.get(globalParameters.size() - 1);
        assertThat(testGlobalParameter.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testGlobalParameter.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testGlobalParameter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteGlobalParameter() throws Exception {
        // Initialize the database
        globalParameterRepository.saveAndFlush(globalParameter);

		int databaseSizeBeforeDelete = globalParameterRepository.findAll().size();

        // Get the globalParameter
        restGlobalParameterMockMvc.perform(delete("/api/globalParameters/{id}", globalParameter.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GlobalParameter> globalParameters = globalParameterRepository.findAll();
        assertThat(globalParameters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
