package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.Prompt;
import com.caceis.olis.paramsandscripts.repository.PromptRepository;
import com.caceis.olis.paramsandscripts.repository.search.PromptSearchRepository;

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

import com.caceis.olis.paramsandscripts.domain.enumeration.PromptType;

/**
 * Test class for the PromptResource REST controller.
 *
 * @see PromptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PromptResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_SYSTEM_NAME = "AAAAA";
    private static final String UPDATED_SYSTEM_NAME = "BBBBB";


private static final PromptType DEFAULT_TYPE = PromptType.STR;
    private static final PromptType UPDATED_TYPE = PromptType.INT;
    private static final String DEFAULT_TRANSFORMATION_SCRIPT = "AAAAA";
    private static final String UPDATED_TRANSFORMATION_SCRIPT = "BBBBB";
    private static final String DEFAULT_VISIBLE_NAME = "AAAAA";
    private static final String UPDATED_VISIBLE_NAME = "BBBBB";
    private static final String DEFAULT_DEFAULT_VALUE_SCRIPT = "AAAAA";
    private static final String UPDATED_DEFAULT_VALUE_SCRIPT = "BBBBB";

    private static final Long DEFAULT_ORDER = 1L;
    private static final Long UPDATED_ORDER = 2L;

    @Inject
    private PromptRepository promptRepository;

    @Inject
    private PromptSearchRepository promptSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPromptMockMvc;

    private Prompt prompt;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PromptResource promptResource = new PromptResource();
        ReflectionTestUtils.setField(promptResource, "promptRepository", promptRepository);
        ReflectionTestUtils.setField(promptResource, "promptSearchRepository", promptSearchRepository);
        this.restPromptMockMvc = MockMvcBuilders.standaloneSetup(promptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        prompt = new Prompt();
        prompt.setName(DEFAULT_NAME);
        prompt.setSystemName(DEFAULT_SYSTEM_NAME);
        prompt.setType(DEFAULT_TYPE);
        prompt.setTransformationScript(DEFAULT_TRANSFORMATION_SCRIPT);
        prompt.setVisibleName(DEFAULT_VISIBLE_NAME);
        prompt.setDefaultValueScript(DEFAULT_DEFAULT_VALUE_SCRIPT);
        prompt.setOrder(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createPrompt() throws Exception {
        int databaseSizeBeforeCreate = promptRepository.findAll().size();

        // Create the Prompt

        restPromptMockMvc.perform(post("/api/prompts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(prompt)))
                .andExpect(status().isCreated());

        // Validate the Prompt in the database
        List<Prompt> prompts = promptRepository.findAll();
        assertThat(prompts).hasSize(databaseSizeBeforeCreate + 1);
        Prompt testPrompt = prompts.get(prompts.size() - 1);
        assertThat(testPrompt.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPrompt.getSystemName()).isEqualTo(DEFAULT_SYSTEM_NAME);
        assertThat(testPrompt.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPrompt.getTransformationScript()).isEqualTo(DEFAULT_TRANSFORMATION_SCRIPT);
        assertThat(testPrompt.getVisibleName()).isEqualTo(DEFAULT_VISIBLE_NAME);
        assertThat(testPrompt.getDefaultValueScript()).isEqualTo(DEFAULT_DEFAULT_VALUE_SCRIPT);
        assertThat(testPrompt.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = promptRepository.findAll().size();
        // set the field null
        prompt.setName(null);

        // Create the Prompt, which fails.

        restPromptMockMvc.perform(post("/api/prompts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(prompt)))
                .andExpect(status().isBadRequest());

        List<Prompt> prompts = promptRepository.findAll();
        assertThat(prompts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSystemNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = promptRepository.findAll().size();
        // set the field null
        prompt.setSystemName(null);

        // Create the Prompt, which fails.

        restPromptMockMvc.perform(post("/api/prompts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(prompt)))
                .andExpect(status().isBadRequest());

        List<Prompt> prompts = promptRepository.findAll();
        assertThat(prompts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = promptRepository.findAll().size();
        // set the field null
        prompt.setType(null);

        // Create the Prompt, which fails.

        restPromptMockMvc.perform(post("/api/prompts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(prompt)))
                .andExpect(status().isBadRequest());

        List<Prompt> prompts = promptRepository.findAll();
        assertThat(prompts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = promptRepository.findAll().size();
        // set the field null
        prompt.setOrder(null);

        // Create the Prompt, which fails.

        restPromptMockMvc.perform(post("/api/prompts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(prompt)))
                .andExpect(status().isBadRequest());

        List<Prompt> prompts = promptRepository.findAll();
        assertThat(prompts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrompts() throws Exception {
        // Initialize the database
        promptRepository.saveAndFlush(prompt);

        // Get all the prompts
        restPromptMockMvc.perform(get("/api/prompts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(prompt.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].systemName").value(hasItem(DEFAULT_SYSTEM_NAME.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].transformationScript").value(hasItem(DEFAULT_TRANSFORMATION_SCRIPT.toString())))
                .andExpect(jsonPath("$.[*].visibleName").value(hasItem(DEFAULT_VISIBLE_NAME.toString())))
                .andExpect(jsonPath("$.[*].defaultValueScript").value(hasItem(DEFAULT_DEFAULT_VALUE_SCRIPT.toString())))
                .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER.intValue())));
    }

    @Test
    @Transactional
    public void getPrompt() throws Exception {
        // Initialize the database
        promptRepository.saveAndFlush(prompt);

        // Get the prompt
        restPromptMockMvc.perform(get("/api/prompts/{id}", prompt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(prompt.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.systemName").value(DEFAULT_SYSTEM_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.transformationScript").value(DEFAULT_TRANSFORMATION_SCRIPT.toString()))
            .andExpect(jsonPath("$.visibleName").value(DEFAULT_VISIBLE_NAME.toString()))
            .andExpect(jsonPath("$.defaultValueScript").value(DEFAULT_DEFAULT_VALUE_SCRIPT.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrompt() throws Exception {
        // Get the prompt
        restPromptMockMvc.perform(get("/api/prompts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrompt() throws Exception {
        // Initialize the database
        promptRepository.saveAndFlush(prompt);

		int databaseSizeBeforeUpdate = promptRepository.findAll().size();

        // Update the prompt
        prompt.setName(UPDATED_NAME);
        prompt.setSystemName(UPDATED_SYSTEM_NAME);
        prompt.setType(UPDATED_TYPE);
        prompt.setTransformationScript(UPDATED_TRANSFORMATION_SCRIPT);
        prompt.setVisibleName(UPDATED_VISIBLE_NAME);
        prompt.setDefaultValueScript(UPDATED_DEFAULT_VALUE_SCRIPT);
        prompt.setOrder(UPDATED_ORDER);

        restPromptMockMvc.perform(put("/api/prompts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(prompt)))
                .andExpect(status().isOk());

        // Validate the Prompt in the database
        List<Prompt> prompts = promptRepository.findAll();
        assertThat(prompts).hasSize(databaseSizeBeforeUpdate);
        Prompt testPrompt = prompts.get(prompts.size() - 1);
        assertThat(testPrompt.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPrompt.getSystemName()).isEqualTo(UPDATED_SYSTEM_NAME);
        assertThat(testPrompt.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPrompt.getTransformationScript()).isEqualTo(UPDATED_TRANSFORMATION_SCRIPT);
        assertThat(testPrompt.getVisibleName()).isEqualTo(UPDATED_VISIBLE_NAME);
        assertThat(testPrompt.getDefaultValueScript()).isEqualTo(UPDATED_DEFAULT_VALUE_SCRIPT);
        assertThat(testPrompt.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void deletePrompt() throws Exception {
        // Initialize the database
        promptRepository.saveAndFlush(prompt);

		int databaseSizeBeforeDelete = promptRepository.findAll().size();

        // Get the prompt
        restPromptMockMvc.perform(delete("/api/prompts/{id}", prompt.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Prompt> prompts = promptRepository.findAll();
        assertThat(prompts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
