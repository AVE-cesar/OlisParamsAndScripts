package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.GraphicalComponentLink;
import com.caceis.olis.paramsandscripts.repository.GraphicalComponentLinkRepository;
import com.caceis.olis.paramsandscripts.repository.search.GraphicalComponentLinkSearchRepository;

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
 * Test class for the GraphicalComponentLinkResource REST controller.
 *
 * @see GraphicalComponentLinkResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GraphicalComponentLinkResourceIntTest {



private static final Mode DEFAULT_MODE = Mode.ONL;
    private static final Mode UPDATED_MODE = Mode.OFF;

    @Inject
    private GraphicalComponentLinkRepository graphicalComponentLinkRepository;

    @Inject
    private GraphicalComponentLinkSearchRepository graphicalComponentLinkSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGraphicalComponentLinkMockMvc;

    private GraphicalComponentLink graphicalComponentLink;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GraphicalComponentLinkResource graphicalComponentLinkResource = new GraphicalComponentLinkResource();
        ReflectionTestUtils.setField(graphicalComponentLinkResource, "graphicalComponentLinkRepository", graphicalComponentLinkRepository);
        ReflectionTestUtils.setField(graphicalComponentLinkResource, "graphicalComponentLinkSearchRepository", graphicalComponentLinkSearchRepository);
        this.restGraphicalComponentLinkMockMvc = MockMvcBuilders.standaloneSetup(graphicalComponentLinkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        graphicalComponentLink = new GraphicalComponentLink();
        graphicalComponentLink.setMode(DEFAULT_MODE);
    }

    @Test
    @Transactional
    public void createGraphicalComponentLink() throws Exception {
        int databaseSizeBeforeCreate = graphicalComponentLinkRepository.findAll().size();

        // Create the GraphicalComponentLink

        restGraphicalComponentLinkMockMvc.perform(post("/api/graphicalComponentLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(graphicalComponentLink)))
                .andExpect(status().isCreated());

        // Validate the GraphicalComponentLink in the database
        List<GraphicalComponentLink> graphicalComponentLinks = graphicalComponentLinkRepository.findAll();
        assertThat(graphicalComponentLinks).hasSize(databaseSizeBeforeCreate + 1);
        GraphicalComponentLink testGraphicalComponentLink = graphicalComponentLinks.get(graphicalComponentLinks.size() - 1);
        assertThat(testGraphicalComponentLink.getMode()).isEqualTo(DEFAULT_MODE);
    }

    @Test
    @Transactional
    public void checkModeIsRequired() throws Exception {
        int databaseSizeBeforeTest = graphicalComponentLinkRepository.findAll().size();
        // set the field null
        graphicalComponentLink.setMode(null);

        // Create the GraphicalComponentLink, which fails.

        restGraphicalComponentLinkMockMvc.perform(post("/api/graphicalComponentLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(graphicalComponentLink)))
                .andExpect(status().isBadRequest());

        List<GraphicalComponentLink> graphicalComponentLinks = graphicalComponentLinkRepository.findAll();
        assertThat(graphicalComponentLinks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGraphicalComponentLinks() throws Exception {
        // Initialize the database
        graphicalComponentLinkRepository.saveAndFlush(graphicalComponentLink);

        // Get all the graphicalComponentLinks
        restGraphicalComponentLinkMockMvc.perform(get("/api/graphicalComponentLinks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(graphicalComponentLink.getId().intValue())))
                .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE.toString())));
    }

    @Test
    @Transactional
    public void getGraphicalComponentLink() throws Exception {
        // Initialize the database
        graphicalComponentLinkRepository.saveAndFlush(graphicalComponentLink);

        // Get the graphicalComponentLink
        restGraphicalComponentLinkMockMvc.perform(get("/api/graphicalComponentLinks/{id}", graphicalComponentLink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(graphicalComponentLink.getId().intValue()))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGraphicalComponentLink() throws Exception {
        // Get the graphicalComponentLink
        restGraphicalComponentLinkMockMvc.perform(get("/api/graphicalComponentLinks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGraphicalComponentLink() throws Exception {
        // Initialize the database
        graphicalComponentLinkRepository.saveAndFlush(graphicalComponentLink);

		int databaseSizeBeforeUpdate = graphicalComponentLinkRepository.findAll().size();

        // Update the graphicalComponentLink
        graphicalComponentLink.setMode(UPDATED_MODE);

        restGraphicalComponentLinkMockMvc.perform(put("/api/graphicalComponentLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(graphicalComponentLink)))
                .andExpect(status().isOk());

        // Validate the GraphicalComponentLink in the database
        List<GraphicalComponentLink> graphicalComponentLinks = graphicalComponentLinkRepository.findAll();
        assertThat(graphicalComponentLinks).hasSize(databaseSizeBeforeUpdate);
        GraphicalComponentLink testGraphicalComponentLink = graphicalComponentLinks.get(graphicalComponentLinks.size() - 1);
        assertThat(testGraphicalComponentLink.getMode()).isEqualTo(UPDATED_MODE);
    }

    @Test
    @Transactional
    public void deleteGraphicalComponentLink() throws Exception {
        // Initialize the database
        graphicalComponentLinkRepository.saveAndFlush(graphicalComponentLink);

		int databaseSizeBeforeDelete = graphicalComponentLinkRepository.findAll().size();

        // Get the graphicalComponentLink
        restGraphicalComponentLinkMockMvc.perform(delete("/api/graphicalComponentLinks/{id}", graphicalComponentLink.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GraphicalComponentLink> graphicalComponentLinks = graphicalComponentLinkRepository.findAll();
        assertThat(graphicalComponentLinks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
