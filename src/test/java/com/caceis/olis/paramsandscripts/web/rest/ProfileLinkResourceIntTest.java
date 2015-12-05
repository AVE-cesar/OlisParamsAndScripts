package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.ProfileLink;
import com.caceis.olis.paramsandscripts.repository.ProfileLinkRepository;
import com.caceis.olis.paramsandscripts.repository.search.ProfileLinkSearchRepository;

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
 * Test class for the ProfileLinkResource REST controller.
 *
 * @see ProfileLinkResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProfileLinkResourceIntTest {


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
    private ProfileLinkRepository profileLinkRepository;

    @Inject
    private ProfileLinkSearchRepository profileLinkSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProfileLinkMockMvc;

    private ProfileLink profileLink;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfileLinkResource profileLinkResource = new ProfileLinkResource();
        ReflectionTestUtils.setField(profileLinkResource, "profileLinkRepository", profileLinkRepository);
        ReflectionTestUtils.setField(profileLinkResource, "profileLinkSearchRepository", profileLinkSearchRepository);
        this.restProfileLinkMockMvc = MockMvcBuilders.standaloneSetup(profileLinkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        profileLink = new ProfileLink();
        profileLink.setStatus(DEFAULT_STATUS);
        profileLink.setValidityStartDate(DEFAULT_VALIDITY_START_DATE);
        profileLink.setValidityEndDate(DEFAULT_VALIDITY_END_DATE);
        profileLink.setCreationDate(DEFAULT_CREATION_DATE);
        profileLink.setCreatorUserId(DEFAULT_CREATOR_USER_ID);
        profileLink.setModificationDate(DEFAULT_MODIFICATION_DATE);
        profileLink.setUpdatorUserId(DEFAULT_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void createProfileLink() throws Exception {
        int databaseSizeBeforeCreate = profileLinkRepository.findAll().size();

        // Create the ProfileLink

        restProfileLinkMockMvc.perform(post("/api/profileLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profileLink)))
                .andExpect(status().isCreated());

        // Validate the ProfileLink in the database
        List<ProfileLink> profileLinks = profileLinkRepository.findAll();
        assertThat(profileLinks).hasSize(databaseSizeBeforeCreate + 1);
        ProfileLink testProfileLink = profileLinks.get(profileLinks.size() - 1);
        assertThat(testProfileLink.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProfileLink.getValidityStartDate()).isEqualTo(DEFAULT_VALIDITY_START_DATE);
        assertThat(testProfileLink.getValidityEndDate()).isEqualTo(DEFAULT_VALIDITY_END_DATE);
        assertThat(testProfileLink.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testProfileLink.getCreatorUserId()).isEqualTo(DEFAULT_CREATOR_USER_ID);
        assertThat(testProfileLink.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
        assertThat(testProfileLink.getUpdatorUserId()).isEqualTo(DEFAULT_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void getAllProfileLinks() throws Exception {
        // Initialize the database
        profileLinkRepository.saveAndFlush(profileLink);

        // Get all the profileLinks
        restProfileLinkMockMvc.perform(get("/api/profileLinks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(profileLink.getId().intValue())))
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
    public void getProfileLink() throws Exception {
        // Initialize the database
        profileLinkRepository.saveAndFlush(profileLink);

        // Get the profileLink
        restProfileLinkMockMvc.perform(get("/api/profileLinks/{id}", profileLink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(profileLink.getId().intValue()))
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
    public void getNonExistingProfileLink() throws Exception {
        // Get the profileLink
        restProfileLinkMockMvc.perform(get("/api/profileLinks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfileLink() throws Exception {
        // Initialize the database
        profileLinkRepository.saveAndFlush(profileLink);

		int databaseSizeBeforeUpdate = profileLinkRepository.findAll().size();

        // Update the profileLink
        profileLink.setStatus(UPDATED_STATUS);
        profileLink.setValidityStartDate(UPDATED_VALIDITY_START_DATE);
        profileLink.setValidityEndDate(UPDATED_VALIDITY_END_DATE);
        profileLink.setCreationDate(UPDATED_CREATION_DATE);
        profileLink.setCreatorUserId(UPDATED_CREATOR_USER_ID);
        profileLink.setModificationDate(UPDATED_MODIFICATION_DATE);
        profileLink.setUpdatorUserId(UPDATED_UPDATOR_USER_ID);

        restProfileLinkMockMvc.perform(put("/api/profileLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profileLink)))
                .andExpect(status().isOk());

        // Validate the ProfileLink in the database
        List<ProfileLink> profileLinks = profileLinkRepository.findAll();
        assertThat(profileLinks).hasSize(databaseSizeBeforeUpdate);
        ProfileLink testProfileLink = profileLinks.get(profileLinks.size() - 1);
        assertThat(testProfileLink.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProfileLink.getValidityStartDate()).isEqualTo(UPDATED_VALIDITY_START_DATE);
        assertThat(testProfileLink.getValidityEndDate()).isEqualTo(UPDATED_VALIDITY_END_DATE);
        assertThat(testProfileLink.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testProfileLink.getCreatorUserId()).isEqualTo(UPDATED_CREATOR_USER_ID);
        assertThat(testProfileLink.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
        assertThat(testProfileLink.getUpdatorUserId()).isEqualTo(UPDATED_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void deleteProfileLink() throws Exception {
        // Initialize the database
        profileLinkRepository.saveAndFlush(profileLink);

		int databaseSizeBeforeDelete = profileLinkRepository.findAll().size();

        // Get the profileLink
        restProfileLinkMockMvc.perform(delete("/api/profileLinks/{id}", profileLink.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProfileLink> profileLinks = profileLinkRepository.findAll();
        assertThat(profileLinks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
