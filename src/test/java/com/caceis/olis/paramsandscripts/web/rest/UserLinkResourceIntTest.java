package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.UserLink;
import com.caceis.olis.paramsandscripts.repository.UserLinkRepository;
import com.caceis.olis.paramsandscripts.repository.search.UserLinkSearchRepository;

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
 * Test class for the UserLinkResource REST controller.
 *
 * @see UserLinkResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserLinkResourceIntTest {


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
    private UserLinkRepository userLinkRepository;

    @Inject
    private UserLinkSearchRepository userLinkSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUserLinkMockMvc;

    private UserLink userLink;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserLinkResource userLinkResource = new UserLinkResource();
        ReflectionTestUtils.setField(userLinkResource, "userLinkRepository", userLinkRepository);
        ReflectionTestUtils.setField(userLinkResource, "userLinkSearchRepository", userLinkSearchRepository);
        this.restUserLinkMockMvc = MockMvcBuilders.standaloneSetup(userLinkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        userLink = new UserLink();
        userLink.setStatus(DEFAULT_STATUS);
        userLink.setValidityStartDate(DEFAULT_VALIDITY_START_DATE);
        userLink.setValidityEndDate(DEFAULT_VALIDITY_END_DATE);
        userLink.setCreationDate(DEFAULT_CREATION_DATE);
        userLink.setCreatorUserId(DEFAULT_CREATOR_USER_ID);
        userLink.setModificationDate(DEFAULT_MODIFICATION_DATE);
        userLink.setUpdatorUserId(DEFAULT_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void createUserLink() throws Exception {
        int databaseSizeBeforeCreate = userLinkRepository.findAll().size();

        // Create the UserLink

        restUserLinkMockMvc.perform(post("/api/userLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userLink)))
                .andExpect(status().isCreated());

        // Validate the UserLink in the database
        List<UserLink> userLinks = userLinkRepository.findAll();
        assertThat(userLinks).hasSize(databaseSizeBeforeCreate + 1);
        UserLink testUserLink = userLinks.get(userLinks.size() - 1);
        assertThat(testUserLink.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserLink.getValidityStartDate()).isEqualTo(DEFAULT_VALIDITY_START_DATE);
        assertThat(testUserLink.getValidityEndDate()).isEqualTo(DEFAULT_VALIDITY_END_DATE);
        assertThat(testUserLink.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testUserLink.getCreatorUserId()).isEqualTo(DEFAULT_CREATOR_USER_ID);
        assertThat(testUserLink.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
        assertThat(testUserLink.getUpdatorUserId()).isEqualTo(DEFAULT_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void getAllUserLinks() throws Exception {
        // Initialize the database
        userLinkRepository.saveAndFlush(userLink);

        // Get all the userLinks
        restUserLinkMockMvc.perform(get("/api/userLinks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(userLink.getId().intValue())))
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
    public void getUserLink() throws Exception {
        // Initialize the database
        userLinkRepository.saveAndFlush(userLink);

        // Get the userLink
        restUserLinkMockMvc.perform(get("/api/userLinks/{id}", userLink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userLink.getId().intValue()))
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
    public void getNonExistingUserLink() throws Exception {
        // Get the userLink
        restUserLinkMockMvc.perform(get("/api/userLinks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserLink() throws Exception {
        // Initialize the database
        userLinkRepository.saveAndFlush(userLink);

		int databaseSizeBeforeUpdate = userLinkRepository.findAll().size();

        // Update the userLink
        userLink.setStatus(UPDATED_STATUS);
        userLink.setValidityStartDate(UPDATED_VALIDITY_START_DATE);
        userLink.setValidityEndDate(UPDATED_VALIDITY_END_DATE);
        userLink.setCreationDate(UPDATED_CREATION_DATE);
        userLink.setCreatorUserId(UPDATED_CREATOR_USER_ID);
        userLink.setModificationDate(UPDATED_MODIFICATION_DATE);
        userLink.setUpdatorUserId(UPDATED_UPDATOR_USER_ID);

        restUserLinkMockMvc.perform(put("/api/userLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userLink)))
                .andExpect(status().isOk());

        // Validate the UserLink in the database
        List<UserLink> userLinks = userLinkRepository.findAll();
        assertThat(userLinks).hasSize(databaseSizeBeforeUpdate);
        UserLink testUserLink = userLinks.get(userLinks.size() - 1);
        assertThat(testUserLink.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserLink.getValidityStartDate()).isEqualTo(UPDATED_VALIDITY_START_DATE);
        assertThat(testUserLink.getValidityEndDate()).isEqualTo(UPDATED_VALIDITY_END_DATE);
        assertThat(testUserLink.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testUserLink.getCreatorUserId()).isEqualTo(UPDATED_CREATOR_USER_ID);
        assertThat(testUserLink.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
        assertThat(testUserLink.getUpdatorUserId()).isEqualTo(UPDATED_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void deleteUserLink() throws Exception {
        // Initialize the database
        userLinkRepository.saveAndFlush(userLink);

		int databaseSizeBeforeDelete = userLinkRepository.findAll().size();

        // Get the userLink
        restUserLinkMockMvc.perform(delete("/api/userLinks/{id}", userLink.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserLink> userLinks = userLinkRepository.findAll();
        assertThat(userLinks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
