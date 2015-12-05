package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.AuthorizationSetLink;
import com.caceis.olis.paramsandscripts.repository.AuthorizationSetLinkRepository;
import com.caceis.olis.paramsandscripts.repository.search.AuthorizationSetLinkSearchRepository;

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
 * Test class for the AuthorizationSetLinkResource REST controller.
 *
 * @see AuthorizationSetLinkResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AuthorizationSetLinkResourceIntTest {


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
    private AuthorizationSetLinkRepository authorizationSetLinkRepository;

    @Inject
    private AuthorizationSetLinkSearchRepository authorizationSetLinkSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAuthorizationSetLinkMockMvc;

    private AuthorizationSetLink authorizationSetLink;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AuthorizationSetLinkResource authorizationSetLinkResource = new AuthorizationSetLinkResource();
        ReflectionTestUtils.setField(authorizationSetLinkResource, "authorizationSetLinkRepository", authorizationSetLinkRepository);
        ReflectionTestUtils.setField(authorizationSetLinkResource, "authorizationSetLinkSearchRepository", authorizationSetLinkSearchRepository);
        this.restAuthorizationSetLinkMockMvc = MockMvcBuilders.standaloneSetup(authorizationSetLinkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        authorizationSetLink = new AuthorizationSetLink();
        authorizationSetLink.setStatus(DEFAULT_STATUS);
        authorizationSetLink.setValidityStartDate(DEFAULT_VALIDITY_START_DATE);
        authorizationSetLink.setValidityEndDate(DEFAULT_VALIDITY_END_DATE);
        authorizationSetLink.setCreationDate(DEFAULT_CREATION_DATE);
        authorizationSetLink.setCreatorUserId(DEFAULT_CREATOR_USER_ID);
        authorizationSetLink.setModificationDate(DEFAULT_MODIFICATION_DATE);
        authorizationSetLink.setUpdatorUserId(DEFAULT_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void createAuthorizationSetLink() throws Exception {
        int databaseSizeBeforeCreate = authorizationSetLinkRepository.findAll().size();

        // Create the AuthorizationSetLink

        restAuthorizationSetLinkMockMvc.perform(post("/api/authorizationSetLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(authorizationSetLink)))
                .andExpect(status().isCreated());

        // Validate the AuthorizationSetLink in the database
        List<AuthorizationSetLink> authorizationSetLinks = authorizationSetLinkRepository.findAll();
        assertThat(authorizationSetLinks).hasSize(databaseSizeBeforeCreate + 1);
        AuthorizationSetLink testAuthorizationSetLink = authorizationSetLinks.get(authorizationSetLinks.size() - 1);
        assertThat(testAuthorizationSetLink.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAuthorizationSetLink.getValidityStartDate()).isEqualTo(DEFAULT_VALIDITY_START_DATE);
        assertThat(testAuthorizationSetLink.getValidityEndDate()).isEqualTo(DEFAULT_VALIDITY_END_DATE);
        assertThat(testAuthorizationSetLink.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAuthorizationSetLink.getCreatorUserId()).isEqualTo(DEFAULT_CREATOR_USER_ID);
        assertThat(testAuthorizationSetLink.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
        assertThat(testAuthorizationSetLink.getUpdatorUserId()).isEqualTo(DEFAULT_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAuthorizationSetLinks() throws Exception {
        // Initialize the database
        authorizationSetLinkRepository.saveAndFlush(authorizationSetLink);

        // Get all the authorizationSetLinks
        restAuthorizationSetLinkMockMvc.perform(get("/api/authorizationSetLinks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(authorizationSetLink.getId().intValue())))
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
    public void getAuthorizationSetLink() throws Exception {
        // Initialize the database
        authorizationSetLinkRepository.saveAndFlush(authorizationSetLink);

        // Get the authorizationSetLink
        restAuthorizationSetLinkMockMvc.perform(get("/api/authorizationSetLinks/{id}", authorizationSetLink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(authorizationSetLink.getId().intValue()))
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
    public void getNonExistingAuthorizationSetLink() throws Exception {
        // Get the authorizationSetLink
        restAuthorizationSetLinkMockMvc.perform(get("/api/authorizationSetLinks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuthorizationSetLink() throws Exception {
        // Initialize the database
        authorizationSetLinkRepository.saveAndFlush(authorizationSetLink);

		int databaseSizeBeforeUpdate = authorizationSetLinkRepository.findAll().size();

        // Update the authorizationSetLink
        authorizationSetLink.setStatus(UPDATED_STATUS);
        authorizationSetLink.setValidityStartDate(UPDATED_VALIDITY_START_DATE);
        authorizationSetLink.setValidityEndDate(UPDATED_VALIDITY_END_DATE);
        authorizationSetLink.setCreationDate(UPDATED_CREATION_DATE);
        authorizationSetLink.setCreatorUserId(UPDATED_CREATOR_USER_ID);
        authorizationSetLink.setModificationDate(UPDATED_MODIFICATION_DATE);
        authorizationSetLink.setUpdatorUserId(UPDATED_UPDATOR_USER_ID);

        restAuthorizationSetLinkMockMvc.perform(put("/api/authorizationSetLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(authorizationSetLink)))
                .andExpect(status().isOk());

        // Validate the AuthorizationSetLink in the database
        List<AuthorizationSetLink> authorizationSetLinks = authorizationSetLinkRepository.findAll();
        assertThat(authorizationSetLinks).hasSize(databaseSizeBeforeUpdate);
        AuthorizationSetLink testAuthorizationSetLink = authorizationSetLinks.get(authorizationSetLinks.size() - 1);
        assertThat(testAuthorizationSetLink.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAuthorizationSetLink.getValidityStartDate()).isEqualTo(UPDATED_VALIDITY_START_DATE);
        assertThat(testAuthorizationSetLink.getValidityEndDate()).isEqualTo(UPDATED_VALIDITY_END_DATE);
        assertThat(testAuthorizationSetLink.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAuthorizationSetLink.getCreatorUserId()).isEqualTo(UPDATED_CREATOR_USER_ID);
        assertThat(testAuthorizationSetLink.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
        assertThat(testAuthorizationSetLink.getUpdatorUserId()).isEqualTo(UPDATED_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void deleteAuthorizationSetLink() throws Exception {
        // Initialize the database
        authorizationSetLinkRepository.saveAndFlush(authorizationSetLink);

		int databaseSizeBeforeDelete = authorizationSetLinkRepository.findAll().size();

        // Get the authorizationSetLink
        restAuthorizationSetLinkMockMvc.perform(delete("/api/authorizationSetLinks/{id}", authorizationSetLink.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AuthorizationSetLink> authorizationSetLinks = authorizationSetLinkRepository.findAll();
        assertThat(authorizationSetLinks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
