package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.PromptPosition;
import com.caceis.olis.paramsandscripts.repository.PromptPositionRepository;
import com.caceis.olis.paramsandscripts.repository.search.PromptPositionSearchRepository;

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
 * Test class for the PromptPositionResource REST controller.
 *
 * @see PromptPositionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PromptPositionResourceIntTest {


    private static final Long DEFAULT_ORDER = 1L;
    private static final Long UPDATED_ORDER = 2L;

    @Inject
    private PromptPositionRepository promptPositionRepository;

    @Inject
    private PromptPositionSearchRepository promptPositionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPromptPositionMockMvc;

    private PromptPosition promptPosition;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PromptPositionResource promptPositionResource = new PromptPositionResource();
        ReflectionTestUtils.setField(promptPositionResource, "promptPositionRepository", promptPositionRepository);
        ReflectionTestUtils.setField(promptPositionResource, "promptPositionSearchRepository", promptPositionSearchRepository);
        this.restPromptPositionMockMvc = MockMvcBuilders.standaloneSetup(promptPositionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        promptPosition = new PromptPosition();
        promptPosition.setOrder(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createPromptPosition() throws Exception {
        int databaseSizeBeforeCreate = promptPositionRepository.findAll().size();

        // Create the PromptPosition

        restPromptPositionMockMvc.perform(post("/api/promptPositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promptPosition)))
                .andExpect(status().isCreated());

        // Validate the PromptPosition in the database
        List<PromptPosition> promptPositions = promptPositionRepository.findAll();
        assertThat(promptPositions).hasSize(databaseSizeBeforeCreate + 1);
        PromptPosition testPromptPosition = promptPositions.get(promptPositions.size() - 1);
        assertThat(testPromptPosition.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void getAllPromptPositions() throws Exception {
        // Initialize the database
        promptPositionRepository.saveAndFlush(promptPosition);

        // Get all the promptPositions
        restPromptPositionMockMvc.perform(get("/api/promptPositions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(promptPosition.getId().intValue())))
                .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER.intValue())));
    }

    @Test
    @Transactional
    public void getPromptPosition() throws Exception {
        // Initialize the database
        promptPositionRepository.saveAndFlush(promptPosition);

        // Get the promptPosition
        restPromptPositionMockMvc.perform(get("/api/promptPositions/{id}", promptPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(promptPosition.getId().intValue()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPromptPosition() throws Exception {
        // Get the promptPosition
        restPromptPositionMockMvc.perform(get("/api/promptPositions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePromptPosition() throws Exception {
        // Initialize the database
        promptPositionRepository.saveAndFlush(promptPosition);

		int databaseSizeBeforeUpdate = promptPositionRepository.findAll().size();

        // Update the promptPosition
        promptPosition.setOrder(UPDATED_ORDER);

        restPromptPositionMockMvc.perform(put("/api/promptPositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promptPosition)))
                .andExpect(status().isOk());

        // Validate the PromptPosition in the database
        List<PromptPosition> promptPositions = promptPositionRepository.findAll();
        assertThat(promptPositions).hasSize(databaseSizeBeforeUpdate);
        PromptPosition testPromptPosition = promptPositions.get(promptPositions.size() - 1);
        assertThat(testPromptPosition.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void deletePromptPosition() throws Exception {
        // Initialize the database
        promptPositionRepository.saveAndFlush(promptPosition);

		int databaseSizeBeforeDelete = promptPositionRepository.findAll().size();

        // Get the promptPosition
        restPromptPositionMockMvc.perform(delete("/api/promptPositions/{id}", promptPosition.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PromptPosition> promptPositions = promptPositionRepository.findAll();
        assertThat(promptPositions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
