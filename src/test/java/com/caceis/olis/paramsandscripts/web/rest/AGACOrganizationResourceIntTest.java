package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.AGACOrganization;
import com.caceis.olis.paramsandscripts.repository.AGACOrganizationRepository;
import com.caceis.olis.paramsandscripts.repository.search.AGACOrganizationSearchRepository;

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

import com.caceis.olis.paramsandscripts.domain.enumeration.Level;
import com.caceis.olis.paramsandscripts.domain.enumeration.BoolValue;

/**
 * Test class for the AGACOrganizationResource REST controller.
 *
 * @see AGACOrganizationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AGACOrganizationResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Long DEFAULT_TYPE = 1L;
    private static final Long UPDATED_TYPE = 2L;

    private static final Long DEFAULT_STATUS = 1L;
    private static final Long UPDATED_STATUS = 2L;

    private static final Long DEFAULT_ROOT_ORGANIZATION = 1L;
    private static final Long UPDATED_ROOT_ORGANIZATION = 2L;

    private static final Long DEFAULT_FATHER_ORGANIZATION = 1L;
    private static final Long UPDATED_FATHER_ORGANIZATION = 2L;
    private static final String DEFAULT_THEME = "AAAAA";
    private static final String UPDATED_THEME = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";


private static final Level DEFAULT_LEVEL = Level.ROOT;
    private static final Level UPDATED_LEVEL = Level.SUB;


private static final BoolValue DEFAULT_INTERNAL = BoolValue.Y;
    private static final BoolValue UPDATED_INTERNAL = BoolValue.N;

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATOR_USER_ID = 1L;
    private static final Long UPDATED_CREATOR_USER_ID = 2L;

    private static final LocalDate DEFAULT_MODIFICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATOR_USER_ID = 1L;
    private static final Long UPDATED_UPDATOR_USER_ID = 2L;

    @Inject
    private AGACOrganizationRepository aGACOrganizationRepository;

    @Inject
    private AGACOrganizationSearchRepository aGACOrganizationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAGACOrganizationMockMvc;

    private AGACOrganization aGACOrganization;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AGACOrganizationResource aGACOrganizationResource = new AGACOrganizationResource();
        ReflectionTestUtils.setField(aGACOrganizationResource, "aGACOrganizationRepository", aGACOrganizationRepository);
        ReflectionTestUtils.setField(aGACOrganizationResource, "aGACOrganizationSearchRepository", aGACOrganizationSearchRepository);
        this.restAGACOrganizationMockMvc = MockMvcBuilders.standaloneSetup(aGACOrganizationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        aGACOrganization = new AGACOrganization();
        aGACOrganization.setCode(DEFAULT_CODE);
        aGACOrganization.setName(DEFAULT_NAME);
        aGACOrganization.setType(DEFAULT_TYPE);
        aGACOrganization.setStatus(DEFAULT_STATUS);
        aGACOrganization.setRootOrganization(DEFAULT_ROOT_ORGANIZATION);
        aGACOrganization.setFatherOrganization(DEFAULT_FATHER_ORGANIZATION);
        aGACOrganization.setTheme(DEFAULT_THEME);
        aGACOrganization.setEmail(DEFAULT_EMAIL);
        aGACOrganization.setLevel(DEFAULT_LEVEL);
        aGACOrganization.setInternal(DEFAULT_INTERNAL);
        aGACOrganization.setCreationDate(DEFAULT_CREATION_DATE);
        aGACOrganization.setCreatorUserId(DEFAULT_CREATOR_USER_ID);
        aGACOrganization.setModificationDate(DEFAULT_MODIFICATION_DATE);
        aGACOrganization.setUpdatorUserId(DEFAULT_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void createAGACOrganization() throws Exception {
        int databaseSizeBeforeCreate = aGACOrganizationRepository.findAll().size();

        // Create the AGACOrganization

        restAGACOrganizationMockMvc.perform(post("/api/aGACOrganizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aGACOrganization)))
                .andExpect(status().isCreated());

        // Validate the AGACOrganization in the database
        List<AGACOrganization> aGACOrganizations = aGACOrganizationRepository.findAll();
        assertThat(aGACOrganizations).hasSize(databaseSizeBeforeCreate + 1);
        AGACOrganization testAGACOrganization = aGACOrganizations.get(aGACOrganizations.size() - 1);
        assertThat(testAGACOrganization.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAGACOrganization.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAGACOrganization.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAGACOrganization.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAGACOrganization.getRootOrganization()).isEqualTo(DEFAULT_ROOT_ORGANIZATION);
        assertThat(testAGACOrganization.getFatherOrganization()).isEqualTo(DEFAULT_FATHER_ORGANIZATION);
        assertThat(testAGACOrganization.getTheme()).isEqualTo(DEFAULT_THEME);
        assertThat(testAGACOrganization.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAGACOrganization.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testAGACOrganization.getInternal()).isEqualTo(DEFAULT_INTERNAL);
        assertThat(testAGACOrganization.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAGACOrganization.getCreatorUserId()).isEqualTo(DEFAULT_CREATOR_USER_ID);
        assertThat(testAGACOrganization.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
        assertThat(testAGACOrganization.getUpdatorUserId()).isEqualTo(DEFAULT_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = aGACOrganizationRepository.findAll().size();
        // set the field null
        aGACOrganization.setCreationDate(null);

        // Create the AGACOrganization, which fails.

        restAGACOrganizationMockMvc.perform(post("/api/aGACOrganizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aGACOrganization)))
                .andExpect(status().isBadRequest());

        List<AGACOrganization> aGACOrganizations = aGACOrganizationRepository.findAll();
        assertThat(aGACOrganizations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatorUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = aGACOrganizationRepository.findAll().size();
        // set the field null
        aGACOrganization.setCreatorUserId(null);

        // Create the AGACOrganization, which fails.

        restAGACOrganizationMockMvc.perform(post("/api/aGACOrganizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aGACOrganization)))
                .andExpect(status().isBadRequest());

        List<AGACOrganization> aGACOrganizations = aGACOrganizationRepository.findAll();
        assertThat(aGACOrganizations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAGACOrganizations() throws Exception {
        // Initialize the database
        aGACOrganizationRepository.saveAndFlush(aGACOrganization);

        // Get all the aGACOrganizations
        restAGACOrganizationMockMvc.perform(get("/api/aGACOrganizations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aGACOrganization.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.intValue())))
                .andExpect(jsonPath("$.[*].rootOrganization").value(hasItem(DEFAULT_ROOT_ORGANIZATION.intValue())))
                .andExpect(jsonPath("$.[*].fatherOrganization").value(hasItem(DEFAULT_FATHER_ORGANIZATION.intValue())))
                .andExpect(jsonPath("$.[*].theme").value(hasItem(DEFAULT_THEME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
                .andExpect(jsonPath("$.[*].internal").value(hasItem(DEFAULT_INTERNAL.toString())))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].creatorUserId").value(hasItem(DEFAULT_CREATOR_USER_ID.intValue())))
                .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatorUserId").value(hasItem(DEFAULT_UPDATOR_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getAGACOrganization() throws Exception {
        // Initialize the database
        aGACOrganizationRepository.saveAndFlush(aGACOrganization);

        // Get the aGACOrganization
        restAGACOrganizationMockMvc.perform(get("/api/aGACOrganizations/{id}", aGACOrganization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(aGACOrganization.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.intValue()))
            .andExpect(jsonPath("$.rootOrganization").value(DEFAULT_ROOT_ORGANIZATION.intValue()))
            .andExpect(jsonPath("$.fatherOrganization").value(DEFAULT_FATHER_ORGANIZATION.intValue()))
            .andExpect(jsonPath("$.theme").value(DEFAULT_THEME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.internal").value(DEFAULT_INTERNAL.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.creatorUserId").value(DEFAULT_CREATOR_USER_ID.intValue()))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE.toString()))
            .andExpect(jsonPath("$.updatorUserId").value(DEFAULT_UPDATOR_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAGACOrganization() throws Exception {
        // Get the aGACOrganization
        restAGACOrganizationMockMvc.perform(get("/api/aGACOrganizations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAGACOrganization() throws Exception {
        // Initialize the database
        aGACOrganizationRepository.saveAndFlush(aGACOrganization);

		int databaseSizeBeforeUpdate = aGACOrganizationRepository.findAll().size();

        // Update the aGACOrganization
        aGACOrganization.setCode(UPDATED_CODE);
        aGACOrganization.setName(UPDATED_NAME);
        aGACOrganization.setType(UPDATED_TYPE);
        aGACOrganization.setStatus(UPDATED_STATUS);
        aGACOrganization.setRootOrganization(UPDATED_ROOT_ORGANIZATION);
        aGACOrganization.setFatherOrganization(UPDATED_FATHER_ORGANIZATION);
        aGACOrganization.setTheme(UPDATED_THEME);
        aGACOrganization.setEmail(UPDATED_EMAIL);
        aGACOrganization.setLevel(UPDATED_LEVEL);
        aGACOrganization.setInternal(UPDATED_INTERNAL);
        aGACOrganization.setCreationDate(UPDATED_CREATION_DATE);
        aGACOrganization.setCreatorUserId(UPDATED_CREATOR_USER_ID);
        aGACOrganization.setModificationDate(UPDATED_MODIFICATION_DATE);
        aGACOrganization.setUpdatorUserId(UPDATED_UPDATOR_USER_ID);

        restAGACOrganizationMockMvc.perform(put("/api/aGACOrganizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aGACOrganization)))
                .andExpect(status().isOk());

        // Validate the AGACOrganization in the database
        List<AGACOrganization> aGACOrganizations = aGACOrganizationRepository.findAll();
        assertThat(aGACOrganizations).hasSize(databaseSizeBeforeUpdate);
        AGACOrganization testAGACOrganization = aGACOrganizations.get(aGACOrganizations.size() - 1);
        assertThat(testAGACOrganization.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAGACOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAGACOrganization.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAGACOrganization.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAGACOrganization.getRootOrganization()).isEqualTo(UPDATED_ROOT_ORGANIZATION);
        assertThat(testAGACOrganization.getFatherOrganization()).isEqualTo(UPDATED_FATHER_ORGANIZATION);
        assertThat(testAGACOrganization.getTheme()).isEqualTo(UPDATED_THEME);
        assertThat(testAGACOrganization.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAGACOrganization.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testAGACOrganization.getInternal()).isEqualTo(UPDATED_INTERNAL);
        assertThat(testAGACOrganization.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAGACOrganization.getCreatorUserId()).isEqualTo(UPDATED_CREATOR_USER_ID);
        assertThat(testAGACOrganization.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
        assertThat(testAGACOrganization.getUpdatorUserId()).isEqualTo(UPDATED_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void deleteAGACOrganization() throws Exception {
        // Initialize the database
        aGACOrganizationRepository.saveAndFlush(aGACOrganization);

		int databaseSizeBeforeDelete = aGACOrganizationRepository.findAll().size();

        // Get the aGACOrganization
        restAGACOrganizationMockMvc.perform(delete("/api/aGACOrganizations/{id}", aGACOrganization.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AGACOrganization> aGACOrganizations = aGACOrganizationRepository.findAll();
        assertThat(aGACOrganizations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
