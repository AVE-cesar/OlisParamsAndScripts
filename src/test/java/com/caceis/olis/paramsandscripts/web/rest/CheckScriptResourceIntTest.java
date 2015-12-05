package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.CheckScript;
import com.caceis.olis.paramsandscripts.repository.CheckScriptRepository;
import com.caceis.olis.paramsandscripts.repository.search.CheckScriptSearchRepository;

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
 * Test class for the CheckScriptResource REST controller.
 *
 * @see CheckScriptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CheckScriptResourceIntTest {

    private static final String DEFAULT_SCRIPT = "AAAAA";
    private static final String UPDATED_SCRIPT = "BBBBB";

    @Inject
    private CheckScriptRepository checkScriptRepository;

    @Inject
    private CheckScriptSearchRepository checkScriptSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCheckScriptMockMvc;

    private CheckScript checkScript;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CheckScriptResource checkScriptResource = new CheckScriptResource();
        ReflectionTestUtils.setField(checkScriptResource, "checkScriptRepository", checkScriptRepository);
        ReflectionTestUtils.setField(checkScriptResource, "checkScriptSearchRepository", checkScriptSearchRepository);
        this.restCheckScriptMockMvc = MockMvcBuilders.standaloneSetup(checkScriptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        checkScript = new CheckScript();
        checkScript.setScript(DEFAULT_SCRIPT);
    }

    @Test
    @Transactional
    public void createCheckScript() throws Exception {
        int databaseSizeBeforeCreate = checkScriptRepository.findAll().size();

        // Create the CheckScript

        restCheckScriptMockMvc.perform(post("/api/checkScripts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(checkScript)))
                .andExpect(status().isCreated());

        // Validate the CheckScript in the database
        List<CheckScript> checkScripts = checkScriptRepository.findAll();
        assertThat(checkScripts).hasSize(databaseSizeBeforeCreate + 1);
        CheckScript testCheckScript = checkScripts.get(checkScripts.size() - 1);
        assertThat(testCheckScript.getScript()).isEqualTo(DEFAULT_SCRIPT);
    }

    @Test
    @Transactional
    public void getAllCheckScripts() throws Exception {
        // Initialize the database
        checkScriptRepository.saveAndFlush(checkScript);

        // Get all the checkScripts
        restCheckScriptMockMvc.perform(get("/api/checkScripts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(checkScript.getId().intValue())))
                .andExpect(jsonPath("$.[*].script").value(hasItem(DEFAULT_SCRIPT.toString())));
    }

    @Test
    @Transactional
    public void getCheckScript() throws Exception {
        // Initialize the database
        checkScriptRepository.saveAndFlush(checkScript);

        // Get the checkScript
        restCheckScriptMockMvc.perform(get("/api/checkScripts/{id}", checkScript.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(checkScript.getId().intValue()))
            .andExpect(jsonPath("$.script").value(DEFAULT_SCRIPT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCheckScript() throws Exception {
        // Get the checkScript
        restCheckScriptMockMvc.perform(get("/api/checkScripts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCheckScript() throws Exception {
        // Initialize the database
        checkScriptRepository.saveAndFlush(checkScript);

		int databaseSizeBeforeUpdate = checkScriptRepository.findAll().size();

        // Update the checkScript
        checkScript.setScript(UPDATED_SCRIPT);

        restCheckScriptMockMvc.perform(put("/api/checkScripts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(checkScript)))
                .andExpect(status().isOk());

        // Validate the CheckScript in the database
        List<CheckScript> checkScripts = checkScriptRepository.findAll();
        assertThat(checkScripts).hasSize(databaseSizeBeforeUpdate);
        CheckScript testCheckScript = checkScripts.get(checkScripts.size() - 1);
        assertThat(testCheckScript.getScript()).isEqualTo(UPDATED_SCRIPT);
    }

    @Test
    @Transactional
    public void deleteCheckScript() throws Exception {
        // Initialize the database
        checkScriptRepository.saveAndFlush(checkScript);

		int databaseSizeBeforeDelete = checkScriptRepository.findAll().size();

        // Get the checkScript
        restCheckScriptMockMvc.perform(delete("/api/checkScripts/{id}", checkScript.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CheckScript> checkScripts = checkScriptRepository.findAll();
        assertThat(checkScripts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
