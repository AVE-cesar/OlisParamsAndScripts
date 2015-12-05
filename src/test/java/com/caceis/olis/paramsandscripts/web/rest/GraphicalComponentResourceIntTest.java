package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.GraphicalComponent;
import com.caceis.olis.paramsandscripts.repository.GraphicalComponentRepository;
import com.caceis.olis.paramsandscripts.repository.search.GraphicalComponentSearchRepository;

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
 * Test class for the GraphicalComponentResource REST controller.
 *
 * @see GraphicalComponentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GraphicalComponentResourceIntTest {

    private static final String DEFAULT_SCRIPT = "AAAAA";
    private static final String UPDATED_SCRIPT = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private GraphicalComponentRepository graphicalComponentRepository;

    @Inject
    private GraphicalComponentSearchRepository graphicalComponentSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGraphicalComponentMockMvc;

    private GraphicalComponent graphicalComponent;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GraphicalComponentResource graphicalComponentResource = new GraphicalComponentResource();
        ReflectionTestUtils.setField(graphicalComponentResource, "graphicalComponentRepository", graphicalComponentRepository);
        ReflectionTestUtils.setField(graphicalComponentResource, "graphicalComponentSearchRepository", graphicalComponentSearchRepository);
        this.restGraphicalComponentMockMvc = MockMvcBuilders.standaloneSetup(graphicalComponentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        graphicalComponent = new GraphicalComponent();
        graphicalComponent.setScript(DEFAULT_SCRIPT);
        graphicalComponent.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createGraphicalComponent() throws Exception {
        int databaseSizeBeforeCreate = graphicalComponentRepository.findAll().size();

        // Create the GraphicalComponent

        restGraphicalComponentMockMvc.perform(post("/api/graphicalComponents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(graphicalComponent)))
                .andExpect(status().isCreated());

        // Validate the GraphicalComponent in the database
        List<GraphicalComponent> graphicalComponents = graphicalComponentRepository.findAll();
        assertThat(graphicalComponents).hasSize(databaseSizeBeforeCreate + 1);
        GraphicalComponent testGraphicalComponent = graphicalComponents.get(graphicalComponents.size() - 1);
        assertThat(testGraphicalComponent.getScript()).isEqualTo(DEFAULT_SCRIPT);
        assertThat(testGraphicalComponent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkScriptIsRequired() throws Exception {
        int databaseSizeBeforeTest = graphicalComponentRepository.findAll().size();
        // set the field null
        graphicalComponent.setScript(null);

        // Create the GraphicalComponent, which fails.

        restGraphicalComponentMockMvc.perform(post("/api/graphicalComponents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(graphicalComponent)))
                .andExpect(status().isBadRequest());

        List<GraphicalComponent> graphicalComponents = graphicalComponentRepository.findAll();
        assertThat(graphicalComponents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGraphicalComponents() throws Exception {
        // Initialize the database
        graphicalComponentRepository.saveAndFlush(graphicalComponent);

        // Get all the graphicalComponents
        restGraphicalComponentMockMvc.perform(get("/api/graphicalComponents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(graphicalComponent.getId().intValue())))
                .andExpect(jsonPath("$.[*].script").value(hasItem(DEFAULT_SCRIPT.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getGraphicalComponent() throws Exception {
        // Initialize the database
        graphicalComponentRepository.saveAndFlush(graphicalComponent);

        // Get the graphicalComponent
        restGraphicalComponentMockMvc.perform(get("/api/graphicalComponents/{id}", graphicalComponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(graphicalComponent.getId().intValue()))
            .andExpect(jsonPath("$.script").value(DEFAULT_SCRIPT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGraphicalComponent() throws Exception {
        // Get the graphicalComponent
        restGraphicalComponentMockMvc.perform(get("/api/graphicalComponents/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGraphicalComponent() throws Exception {
        // Initialize the database
        graphicalComponentRepository.saveAndFlush(graphicalComponent);

		int databaseSizeBeforeUpdate = graphicalComponentRepository.findAll().size();

        // Update the graphicalComponent
        graphicalComponent.setScript(UPDATED_SCRIPT);
        graphicalComponent.setDescription(UPDATED_DESCRIPTION);

        restGraphicalComponentMockMvc.perform(put("/api/graphicalComponents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(graphicalComponent)))
                .andExpect(status().isOk());

        // Validate the GraphicalComponent in the database
        List<GraphicalComponent> graphicalComponents = graphicalComponentRepository.findAll();
        assertThat(graphicalComponents).hasSize(databaseSizeBeforeUpdate);
        GraphicalComponent testGraphicalComponent = graphicalComponents.get(graphicalComponents.size() - 1);
        assertThat(testGraphicalComponent.getScript()).isEqualTo(UPDATED_SCRIPT);
        assertThat(testGraphicalComponent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteGraphicalComponent() throws Exception {
        // Initialize the database
        graphicalComponentRepository.saveAndFlush(graphicalComponent);

		int databaseSizeBeforeDelete = graphicalComponentRepository.findAll().size();

        // Get the graphicalComponent
        restGraphicalComponentMockMvc.perform(delete("/api/graphicalComponents/{id}", graphicalComponent.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GraphicalComponent> graphicalComponents = graphicalComponentRepository.findAll();
        assertThat(graphicalComponents).hasSize(databaseSizeBeforeDelete - 1);
    }
}
