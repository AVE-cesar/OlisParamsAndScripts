package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.AGACUserLink;
import com.caceis.olis.paramsandscripts.repository.AGACUserLinkRepository;
import com.caceis.olis.paramsandscripts.repository.search.AGACUserLinkSearchRepository;

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
 * Test class for the AGACUserLinkResource REST controller.
 *
 * @see AGACUserLinkResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AGACUserLinkResourceIntTest {


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
    private AGACUserLinkRepository aGACUserLinkRepository;

    @Inject
    private AGACUserLinkSearchRepository aGACUserLinkSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAGACUserLinkMockMvc;

    private AGACUserLink aGACUserLink;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AGACUserLinkResource aGACUserLinkResource = new AGACUserLinkResource();
        ReflectionTestUtils.setField(aGACUserLinkResource, "aGACUserLinkRepository", aGACUserLinkRepository);
        ReflectionTestUtils.setField(aGACUserLinkResource, "aGACUserLinkSearchRepository", aGACUserLinkSearchRepository);
        this.restAGACUserLinkMockMvc = MockMvcBuilders.standaloneSetup(aGACUserLinkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        aGACUserLink = new AGACUserLink();
        aGACUserLink.setStatus(DEFAULT_STATUS);
        aGACUserLink.setValidityStartDate(DEFAULT_VALIDITY_START_DATE);
        aGACUserLink.setValidityEndDate(DEFAULT_VALIDITY_END_DATE);
        aGACUserLink.setCreationDate(DEFAULT_CREATION_DATE);
        aGACUserLink.setCreatorUserId(DEFAULT_CREATOR_USER_ID);
        aGACUserLink.setModificationDate(DEFAULT_MODIFICATION_DATE);
        aGACUserLink.setUpdatorUserId(DEFAULT_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void createAGACUserLink() throws Exception {
        int databaseSizeBeforeCreate = aGACUserLinkRepository.findAll().size();

        // Create the AGACUserLink

        restAGACUserLinkMockMvc.perform(post("/api/aGACUserLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aGACUserLink)))
                .andExpect(status().isCreated());

        // Validate the AGACUserLink in the database
        List<AGACUserLink> aGACUserLinks = aGACUserLinkRepository.findAll();
        assertThat(aGACUserLinks).hasSize(databaseSizeBeforeCreate + 1);
        AGACUserLink testAGACUserLink = aGACUserLinks.get(aGACUserLinks.size() - 1);
        assertThat(testAGACUserLink.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAGACUserLink.getValidityStartDate()).isEqualTo(DEFAULT_VALIDITY_START_DATE);
        assertThat(testAGACUserLink.getValidityEndDate()).isEqualTo(DEFAULT_VALIDITY_END_DATE);
        assertThat(testAGACUserLink.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAGACUserLink.getCreatorUserId()).isEqualTo(DEFAULT_CREATOR_USER_ID);
        assertThat(testAGACUserLink.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
        assertThat(testAGACUserLink.getUpdatorUserId()).isEqualTo(DEFAULT_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAGACUserLinks() throws Exception {
        // Initialize the database
        aGACUserLinkRepository.saveAndFlush(aGACUserLink);

        // Get all the aGACUserLinks
        restAGACUserLinkMockMvc.perform(get("/api/aGACUserLinks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aGACUserLink.getId().intValue())))
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
    public void getAGACUserLink() throws Exception {
        // Initialize the database
        aGACUserLinkRepository.saveAndFlush(aGACUserLink);

        // Get the aGACUserLink
        restAGACUserLinkMockMvc.perform(get("/api/aGACUserLinks/{id}", aGACUserLink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(aGACUserLink.getId().intValue()))
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
    public void getNonExistingAGACUserLink() throws Exception {
        // Get the aGACUserLink
        restAGACUserLinkMockMvc.perform(get("/api/aGACUserLinks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAGACUserLink() throws Exception {
        // Initialize the database
        aGACUserLinkRepository.saveAndFlush(aGACUserLink);

		int databaseSizeBeforeUpdate = aGACUserLinkRepository.findAll().size();

        // Update the aGACUserLink
        aGACUserLink.setStatus(UPDATED_STATUS);
        aGACUserLink.setValidityStartDate(UPDATED_VALIDITY_START_DATE);
        aGACUserLink.setValidityEndDate(UPDATED_VALIDITY_END_DATE);
        aGACUserLink.setCreationDate(UPDATED_CREATION_DATE);
        aGACUserLink.setCreatorUserId(UPDATED_CREATOR_USER_ID);
        aGACUserLink.setModificationDate(UPDATED_MODIFICATION_DATE);
        aGACUserLink.setUpdatorUserId(UPDATED_UPDATOR_USER_ID);

        restAGACUserLinkMockMvc.perform(put("/api/aGACUserLinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aGACUserLink)))
                .andExpect(status().isOk());

        // Validate the AGACUserLink in the database
        List<AGACUserLink> aGACUserLinks = aGACUserLinkRepository.findAll();
        assertThat(aGACUserLinks).hasSize(databaseSizeBeforeUpdate);
        AGACUserLink testAGACUserLink = aGACUserLinks.get(aGACUserLinks.size() - 1);
        assertThat(testAGACUserLink.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAGACUserLink.getValidityStartDate()).isEqualTo(UPDATED_VALIDITY_START_DATE);
        assertThat(testAGACUserLink.getValidityEndDate()).isEqualTo(UPDATED_VALIDITY_END_DATE);
        assertThat(testAGACUserLink.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAGACUserLink.getCreatorUserId()).isEqualTo(UPDATED_CREATOR_USER_ID);
        assertThat(testAGACUserLink.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
        assertThat(testAGACUserLink.getUpdatorUserId()).isEqualTo(UPDATED_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void deleteAGACUserLink() throws Exception {
        // Initialize the database
        aGACUserLinkRepository.saveAndFlush(aGACUserLink);

		int databaseSizeBeforeDelete = aGACUserLinkRepository.findAll().size();

        // Get the aGACUserLink
        restAGACUserLinkMockMvc.perform(delete("/api/aGACUserLinks/{id}", aGACUserLink.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AGACUserLink> aGACUserLinks = aGACUserLinkRepository.findAll();
        assertThat(aGACUserLinks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
