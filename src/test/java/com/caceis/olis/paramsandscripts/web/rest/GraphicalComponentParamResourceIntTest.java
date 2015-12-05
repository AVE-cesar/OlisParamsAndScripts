package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.GraphicalComponentParam;
import com.caceis.olis.paramsandscripts.repository.GraphicalComponentParamRepository;
import com.caceis.olis.paramsandscripts.repository.search.GraphicalComponentParamSearchRepository;

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

import com.caceis.olis.paramsandscripts.domain.enumeration.Mode;

/**
 * Test class for the GraphicalComponentParamResource REST controller.
 *
 * @see GraphicalComponentParamResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GraphicalComponentParamResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";


private static final Mode DEFAULT_MODE = Mode.ONL;
    private static final Mode UPDATED_MODE = Mode.OFF;

    @Inject
    private GraphicalComponentParamRepository graphicalComponentParamRepository;

    @Inject
    private GraphicalComponentParamSearchRepository graphicalComponentParamSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGraphicalComponentParamMockMvc;

    private GraphicalComponentParam graphicalComponentParam;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GraphicalComponentParamResource graphicalComponentParamResource = new GraphicalComponentParamResource();
        ReflectionTestUtils.setField(graphicalComponentParamResource, "graphicalComponentParamRepository", graphicalComponentParamRepository);
        ReflectionTestUtils.setField(graphicalComponentParamResource, "graphicalComponentParamSearchRepository", graphicalComponentParamSearchRepository);
        this.restGraphicalComponentParamMockMvc = MockMvcBuilders.standaloneSetup(graphicalComponentParamResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        graphicalComponentParam = new GraphicalComponentParam();
        graphicalComponentParam.setName(DEFAULT_NAME);
        graphicalComponentParam.setValue(DEFAULT_VALUE);
        graphicalComponentParam.setMode(DEFAULT_MODE);
    }

    @Test
    @Transactional
    public void createGraphicalComponentParam() throws Exception {
        int databaseSizeBeforeCreate = graphicalComponentParamRepository.findAll().size();

        // Create the GraphicalComponentParam

        restGraphicalComponentParamMockMvc.perform(post("/api/graphicalComponentParams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(graphicalComponentParam)))
                .andExpect(status().isCreated());

        // Validate the GraphicalComponentParam in the database
        List<GraphicalComponentParam> graphicalComponentParams = graphicalComponentParamRepository.findAll();
        assertThat(graphicalComponentParams).hasSize(databaseSizeBeforeCreate + 1);
        GraphicalComponentParam testGraphicalComponentParam = graphicalComponentParams.get(graphicalComponentParams.size() - 1);
        assertThat(testGraphicalComponentParam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGraphicalComponentParam.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testGraphicalComponentParam.getMode()).isEqualTo(DEFAULT_MODE);
    }

    @Test
    @Transactional
    public void getAllGraphicalComponentParams() throws Exception {
        // Initialize the database
        graphicalComponentParamRepository.saveAndFlush(graphicalComponentParam);

        // Get all the graphicalComponentParams
        restGraphicalComponentParamMockMvc.perform(get("/api/graphicalComponentParams"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(graphicalComponentParam.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
                .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE.toString())));
    }

    @Test
    @Transactional
    public void getGraphicalComponentParam() throws Exception {
        // Initialize the database
        graphicalComponentParamRepository.saveAndFlush(graphicalComponentParam);

        // Get the graphicalComponentParam
        restGraphicalComponentParamMockMvc.perform(get("/api/graphicalComponentParams/{id}", graphicalComponentParam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(graphicalComponentParam.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGraphicalComponentParam() throws Exception {
        // Get the graphicalComponentParam
        restGraphicalComponentParamMockMvc.perform(get("/api/graphicalComponentParams/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGraphicalComponentParam() throws Exception {
        // Initialize the database
        graphicalComponentParamRepository.saveAndFlush(graphicalComponentParam);

		int databaseSizeBeforeUpdate = graphicalComponentParamRepository.findAll().size();

        // Update the graphicalComponentParam
        graphicalComponentParam.setName(UPDATED_NAME);
        graphicalComponentParam.setValue(UPDATED_VALUE);
        graphicalComponentParam.setMode(UPDATED_MODE);

        restGraphicalComponentParamMockMvc.perform(put("/api/graphicalComponentParams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(graphicalComponentParam)))
                .andExpect(status().isOk());

        // Validate the GraphicalComponentParam in the database
        List<GraphicalComponentParam> graphicalComponentParams = graphicalComponentParamRepository.findAll();
        assertThat(graphicalComponentParams).hasSize(databaseSizeBeforeUpdate);
        GraphicalComponentParam testGraphicalComponentParam = graphicalComponentParams.get(graphicalComponentParams.size() - 1);
        assertThat(testGraphicalComponentParam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGraphicalComponentParam.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testGraphicalComponentParam.getMode()).isEqualTo(UPDATED_MODE);
    }

    @Test
    @Transactional
    public void deleteGraphicalComponentParam() throws Exception {
        // Initialize the database
        graphicalComponentParamRepository.saveAndFlush(graphicalComponentParam);

		int databaseSizeBeforeDelete = graphicalComponentParamRepository.findAll().size();

        // Get the graphicalComponentParam
        restGraphicalComponentParamMockMvc.perform(delete("/api/graphicalComponentParams/{id}", graphicalComponentParam.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GraphicalComponentParam> graphicalComponentParams = graphicalComponentParamRepository.findAll();
        assertThat(graphicalComponentParams).hasSize(databaseSizeBeforeDelete - 1);
    }
}
