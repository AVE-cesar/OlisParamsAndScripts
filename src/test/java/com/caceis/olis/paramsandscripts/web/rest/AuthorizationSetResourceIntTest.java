package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.AuthorizationSet;
import com.caceis.olis.paramsandscripts.repository.AuthorizationSetRepository;
import com.caceis.olis.paramsandscripts.repository.search.AuthorizationSetSearchRepository;

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
 * Test class for the AuthorizationSetResource REST controller.
 *
 * @see AuthorizationSetResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AuthorizationSetResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private AuthorizationSetRepository authorizationSetRepository;

    @Inject
    private AuthorizationSetSearchRepository authorizationSetSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAuthorizationSetMockMvc;

    private AuthorizationSet authorizationSet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AuthorizationSetResource authorizationSetResource = new AuthorizationSetResource();
        ReflectionTestUtils.setField(authorizationSetResource, "authorizationSetRepository", authorizationSetRepository);
        ReflectionTestUtils.setField(authorizationSetResource, "authorizationSetSearchRepository", authorizationSetSearchRepository);
        this.restAuthorizationSetMockMvc = MockMvcBuilders.standaloneSetup(authorizationSetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        authorizationSet = new AuthorizationSet();
        authorizationSet.setCode(DEFAULT_CODE);
        authorizationSet.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAuthorizationSet() throws Exception {
        int databaseSizeBeforeCreate = authorizationSetRepository.findAll().size();

        // Create the AuthorizationSet

        restAuthorizationSetMockMvc.perform(post("/api/authorizationSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(authorizationSet)))
                .andExpect(status().isCreated());

        // Validate the AuthorizationSet in the database
        List<AuthorizationSet> authorizationSets = authorizationSetRepository.findAll();
        assertThat(authorizationSets).hasSize(databaseSizeBeforeCreate + 1);
        AuthorizationSet testAuthorizationSet = authorizationSets.get(authorizationSets.size() - 1);
        assertThat(testAuthorizationSet.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuthorizationSet.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllAuthorizationSets() throws Exception {
        // Initialize the database
        authorizationSetRepository.saveAndFlush(authorizationSet);

        // Get all the authorizationSets
        restAuthorizationSetMockMvc.perform(get("/api/authorizationSets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(authorizationSet.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAuthorizationSet() throws Exception {
        // Initialize the database
        authorizationSetRepository.saveAndFlush(authorizationSet);

        // Get the authorizationSet
        restAuthorizationSetMockMvc.perform(get("/api/authorizationSets/{id}", authorizationSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(authorizationSet.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuthorizationSet() throws Exception {
        // Get the authorizationSet
        restAuthorizationSetMockMvc.perform(get("/api/authorizationSets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuthorizationSet() throws Exception {
        // Initialize the database
        authorizationSetRepository.saveAndFlush(authorizationSet);

		int databaseSizeBeforeUpdate = authorizationSetRepository.findAll().size();

        // Update the authorizationSet
        authorizationSet.setCode(UPDATED_CODE);
        authorizationSet.setName(UPDATED_NAME);

        restAuthorizationSetMockMvc.perform(put("/api/authorizationSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(authorizationSet)))
                .andExpect(status().isOk());

        // Validate the AuthorizationSet in the database
        List<AuthorizationSet> authorizationSets = authorizationSetRepository.findAll();
        assertThat(authorizationSets).hasSize(databaseSizeBeforeUpdate);
        AuthorizationSet testAuthorizationSet = authorizationSets.get(authorizationSets.size() - 1);
        assertThat(testAuthorizationSet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuthorizationSet.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteAuthorizationSet() throws Exception {
        // Initialize the database
        authorizationSetRepository.saveAndFlush(authorizationSet);

		int databaseSizeBeforeDelete = authorizationSetRepository.findAll().size();

        // Get the authorizationSet
        restAuthorizationSetMockMvc.perform(delete("/api/authorizationSets/{id}", authorizationSet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AuthorizationSet> authorizationSets = authorizationSetRepository.findAll();
        assertThat(authorizationSets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
