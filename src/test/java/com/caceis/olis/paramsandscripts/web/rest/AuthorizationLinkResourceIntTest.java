package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.AuthorizationLink;
import com.caceis.olis.paramsandscripts.repository.AuthorizationLinkRepository;
import com.caceis.olis.paramsandscripts.repository.search.AuthorizationLinkSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AuthorizationLinkResource REST controller.
 *
 * @see AuthorizationLinkResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AuthorizationLinkResourceIntTest {


    private static final Long DEFAULT_STATUS = 1L;
    private static final Long UPDATED_STATUS = 2L;

    private static final LocalDate DEFAULT_VALIDITY_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDITY_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALIDITY_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDITY_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATOR_USER_ID = 1L;
    private static final Long UPDATED_CREATOR_USER_ID = 2L;

    private static final LocalDate DEFAULT_MODIFICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATOR_USER_ID = 1L;
    private static final Long UPDATED_UPDATOR_USER_ID = 2L;

    @Inject
    private AuthorizationLinkRepository authorizationLinkRepository;

    @Inject
    private AuthorizationLinkSearchRepository authorizationLinkSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAuthorizationLinkMockMvc;

    private AuthorizationLink authorizationLink;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AuthorizationLinkResource authorizationLinkResource = new AuthorizationLinkResource();
        ReflectionTestUtils.setField(authorizationLinkResource, "authorizationLinkRepository", authorizationLinkRepository);
        ReflectionTestUtils.setField(authorizationLinkResource, "authorizationLinkSearchRepository", authorizationLinkSearchRepository);
        this.restAuthorizationLinkMockMvc = MockMvcBuilders.standaloneSetup(authorizationLinkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        authorizationLink = new AuthorizationLink();
        authorizationLink.setStatus(DEFAULT_STATUS);
        authorizationLink.setValidityStartDate(DEFAULT_VALIDITY_START_DATE);
        authorizationLink.setValidityEndDate(DEFAULT_VALIDITY_END_DATE);
        authorizationLink.setCreationDate(DEFAULT_CREATION_DATE);
        authorizationLink.setCreatorUserId(DEFAULT_CREATOR_USER_ID);
        authorizationLink.setModificationDate(DEFAULT_MODIFICATION_DATE);
        authorizationLink.setUpdatorUserId(DEFAULT_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void createAuthorizationLink() throws Exception {
        int databaseSizeBeforeCreate = authorizationLinkRepository.findAll().size();

        // Create the AuthorizationLink

        restAuthorizationLinkMockMvc.perform(post("/api/authorizationLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(authorizationLink)))
                .andExpect(status().isCreated());

        // Validate the AuthorizationLink in the database
        List<AuthorizationLink> authorizationLinks = authorizationLinkRepository.findAll();
        assertThat(authorizationLinks).hasSize(databaseSizeBeforeCreate + 1);
        AuthorizationLink testAuthorizationLink = authorizationLinks.get(authorizationLinks.size() - 1);
        assertThat(testAuthorizationLink.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAuthorizationLink.getValidityStartDate()).isEqualTo(DEFAULT_VALIDITY_START_DATE);
        assertThat(testAuthorizationLink.getValidityEndDate()).isEqualTo(DEFAULT_VALIDITY_END_DATE);
        assertThat(testAuthorizationLink.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAuthorizationLink.getCreatorUserId()).isEqualTo(DEFAULT_CREATOR_USER_ID);
        assertThat(testAuthorizationLink.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
        assertThat(testAuthorizationLink.getUpdatorUserId()).isEqualTo(DEFAULT_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAuthorizationLinks() throws Exception {
        // Initialize the database
        authorizationLinkRepository.saveAndFlush(authorizationLink);

        // Get all the authorizationLinks
        restAuthorizationLinkMockMvc.perform(get("/api/authorizationLinks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(authorizationLink.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.intValue())))
                .andExpect(jsonPath("$.[*].validityStartDate").value(hasItem(DEFAULT_VALIDITY_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].validityEndDate").value(hasItem(DEFAULT_VALIDITY_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].creatorUserId").value(hasItem(DEFAULT_CREATOR_USER_ID.intValue())))
                .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatorUserId").value(hasItem(DEFAULT_UPDATOR_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getAuthorizationLink() throws Exception {
        // Initialize the database
        authorizationLinkRepository.saveAndFlush(authorizationLink);

        // Get the authorizationLink
        restAuthorizationLinkMockMvc.perform(get("/api/authorizationLinks/{id}", authorizationLink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(authorizationLink.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.intValue()))
            .andExpect(jsonPath("$.validityStartDate").value(DEFAULT_VALIDITY_START_DATE.toString()))
            .andExpect(jsonPath("$.validityEndDate").value(DEFAULT_VALIDITY_END_DATE.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.creatorUserId").value(DEFAULT_CREATOR_USER_ID.intValue()))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE.toString()))
            .andExpect(jsonPath("$.updatorUserId").value(DEFAULT_UPDATOR_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAuthorizationLink() throws Exception {
        // Get the authorizationLink
        restAuthorizationLinkMockMvc.perform(get("/api/authorizationLinks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuthorizationLink() throws Exception {
        // Initialize the database
        authorizationLinkRepository.saveAndFlush(authorizationLink);

		int databaseSizeBeforeUpdate = authorizationLinkRepository.findAll().size();

        // Update the authorizationLink
        authorizationLink.setStatus(UPDATED_STATUS);
        authorizationLink.setValidityStartDate(UPDATED_VALIDITY_START_DATE);
        authorizationLink.setValidityEndDate(UPDATED_VALIDITY_END_DATE);
        authorizationLink.setCreationDate(UPDATED_CREATION_DATE);
        authorizationLink.setCreatorUserId(UPDATED_CREATOR_USER_ID);
        authorizationLink.setModificationDate(UPDATED_MODIFICATION_DATE);
        authorizationLink.setUpdatorUserId(UPDATED_UPDATOR_USER_ID);

        restAuthorizationLinkMockMvc.perform(put("/api/authorizationLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(authorizationLink)))
                .andExpect(status().isOk());

        // Validate the AuthorizationLink in the database
        List<AuthorizationLink> authorizationLinks = authorizationLinkRepository.findAll();
        assertThat(authorizationLinks).hasSize(databaseSizeBeforeUpdate);
        AuthorizationLink testAuthorizationLink = authorizationLinks.get(authorizationLinks.size() - 1);
        assertThat(testAuthorizationLink.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAuthorizationLink.getValidityStartDate()).isEqualTo(UPDATED_VALIDITY_START_DATE);
        assertThat(testAuthorizationLink.getValidityEndDate()).isEqualTo(UPDATED_VALIDITY_END_DATE);
        assertThat(testAuthorizationLink.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAuthorizationLink.getCreatorUserId()).isEqualTo(UPDATED_CREATOR_USER_ID);
        assertThat(testAuthorizationLink.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
        assertThat(testAuthorizationLink.getUpdatorUserId()).isEqualTo(UPDATED_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void deleteAuthorizationLink() throws Exception {
        // Initialize the database
        authorizationLinkRepository.saveAndFlush(authorizationLink);

		int databaseSizeBeforeDelete = authorizationLinkRepository.findAll().size();

        // Get the authorizationLink
        restAuthorizationLinkMockMvc.perform(delete("/api/authorizationLinks/{id}", authorizationLink.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AuthorizationLink> authorizationLinks = authorizationLinkRepository.findAll();
        assertThat(authorizationLinks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
