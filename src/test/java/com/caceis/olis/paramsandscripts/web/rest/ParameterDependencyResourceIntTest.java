package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.ParameterDependency;
import com.caceis.olis.paramsandscripts.repository.ParameterDependencyRepository;
import com.caceis.olis.paramsandscripts.repository.search.ParameterDependencySearchRepository;

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

import com.caceis.olis.paramsandscripts.domain.enumeration.DependencyType;

/**
 * Test class for the ParameterDependencyResource REST controller.
 *
 * @see ParameterDependencyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ParameterDependencyResourceIntTest {



private static final DependencyType DEFAULT_TYPE = DependencyType.FIL;
    private static final DependencyType UPDATED_TYPE = DependencyType.VIS;
    private static final String DEFAULT_CHECK_OPERATION = "AAAAA";
    private static final String UPDATED_CHECK_OPERATION = "BBBBB";
    private static final String DEFAULT_SCRIPT = "AAAAA";
    private static final String UPDATED_SCRIPT = "BBBBB";

    @Inject
    private ParameterDependencyRepository parameterDependencyRepository;

    @Inject
    private ParameterDependencySearchRepository parameterDependencySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restParameterDependencyMockMvc;

    private ParameterDependency parameterDependency;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ParameterDependencyResource parameterDependencyResource = new ParameterDependencyResource();
        ReflectionTestUtils.setField(parameterDependencyResource, "parameterDependencyRepository", parameterDependencyRepository);
        ReflectionTestUtils.setField(parameterDependencyResource, "parameterDependencySearchRepository", parameterDependencySearchRepository);
        this.restParameterDependencyMockMvc = MockMvcBuilders.standaloneSetup(parameterDependencyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        parameterDependency = new ParameterDependency();
        parameterDependency.setType(DEFAULT_TYPE);
        parameterDependency.setCheckOperation(DEFAULT_CHECK_OPERATION);
        parameterDependency.setScript(DEFAULT_SCRIPT);
    }

    @Test
    @Transactional
    public void createParameterDependency() throws Exception {
        int databaseSizeBeforeCreate = parameterDependencyRepository.findAll().size();

        // Create the ParameterDependency

        restParameterDependencyMockMvc.perform(post("/api/parameterDependencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(parameterDependency)))
                .andExpect(status().isCreated());

        // Validate the ParameterDependency in the database
        List<ParameterDependency> parameterDependencys = parameterDependencyRepository.findAll();
        assertThat(parameterDependencys).hasSize(databaseSizeBeforeCreate + 1);
        ParameterDependency testParameterDependency = parameterDependencys.get(parameterDependencys.size() - 1);
        assertThat(testParameterDependency.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testParameterDependency.getCheckOperation()).isEqualTo(DEFAULT_CHECK_OPERATION);
        assertThat(testParameterDependency.getScript()).isEqualTo(DEFAULT_SCRIPT);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = parameterDependencyRepository.findAll().size();
        // set the field null
        parameterDependency.setType(null);

        // Create the ParameterDependency, which fails.

        restParameterDependencyMockMvc.perform(post("/api/parameterDependencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(parameterDependency)))
                .andExpect(status().isBadRequest());

        List<ParameterDependency> parameterDependencys = parameterDependencyRepository.findAll();
        assertThat(parameterDependencys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParameterDependencys() throws Exception {
        // Initialize the database
        parameterDependencyRepository.saveAndFlush(parameterDependency);

        // Get all the parameterDependencys
        restParameterDependencyMockMvc.perform(get("/api/parameterDependencys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(parameterDependency.getId().intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].checkOperation").value(hasItem(DEFAULT_CHECK_OPERATION.toString())))
                .andExpect(jsonPath("$.[*].script").value(hasItem(DEFAULT_SCRIPT.toString())));
    }

    @Test
    @Transactional
    public void getParameterDependency() throws Exception {
        // Initialize the database
        parameterDependencyRepository.saveAndFlush(parameterDependency);

        // Get the parameterDependency
        restParameterDependencyMockMvc.perform(get("/api/parameterDependencys/{id}", parameterDependency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(parameterDependency.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.checkOperation").value(DEFAULT_CHECK_OPERATION.toString()))
            .andExpect(jsonPath("$.script").value(DEFAULT_SCRIPT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParameterDependency() throws Exception {
        // Get the parameterDependency
        restParameterDependencyMockMvc.perform(get("/api/parameterDependencys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParameterDependency() throws Exception {
        // Initialize the database
        parameterDependencyRepository.saveAndFlush(parameterDependency);

		int databaseSizeBeforeUpdate = parameterDependencyRepository.findAll().size();

        // Update the parameterDependency
        parameterDependency.setType(UPDATED_TYPE);
        parameterDependency.setCheckOperation(UPDATED_CHECK_OPERATION);
        parameterDependency.setScript(UPDATED_SCRIPT);

        restParameterDependencyMockMvc.perform(put("/api/parameterDependencys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(parameterDependency)))
                .andExpect(status().isOk());

        // Validate the ParameterDependency in the database
        List<ParameterDependency> parameterDependencys = parameterDependencyRepository.findAll();
        assertThat(parameterDependencys).hasSize(databaseSizeBeforeUpdate);
        ParameterDependency testParameterDependency = parameterDependencys.get(parameterDependencys.size() - 1);
        assertThat(testParameterDependency.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testParameterDependency.getCheckOperation()).isEqualTo(UPDATED_CHECK_OPERATION);
        assertThat(testParameterDependency.getScript()).isEqualTo(UPDATED_SCRIPT);
    }

    @Test
    @Transactional
    public void deleteParameterDependency() throws Exception {
        // Initialize the database
        parameterDependencyRepository.saveAndFlush(parameterDependency);

		int databaseSizeBeforeDelete = parameterDependencyRepository.findAll().size();

        // Get the parameterDependency
        restParameterDependencyMockMvc.perform(delete("/api/parameterDependencys/{id}", parameterDependency.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ParameterDependency> parameterDependencys = parameterDependencyRepository.findAll();
        assertThat(parameterDependencys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
