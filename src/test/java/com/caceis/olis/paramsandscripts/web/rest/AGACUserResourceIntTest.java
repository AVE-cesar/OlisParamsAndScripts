package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.AGACUser;
import com.caceis.olis.paramsandscripts.repository.AGACUserRepository;
import com.caceis.olis.paramsandscripts.repository.search.AGACUserSearchRepository;

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

import com.caceis.olis.paramsandscripts.domain.enumeration.Gender;
import com.caceis.olis.paramsandscripts.domain.enumeration.Language;

/**
 * Test class for the AGACUserResource REST controller.
 *
 * @see AGACUserResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AGACUserResourceIntTest {


    private static final Long DEFAULT_STATUS = 1L;
    private static final Long UPDATED_STATUS = 2L;
    private static final String DEFAULT_LOGIN = "AAAAA";
    private static final String UPDATED_LOGIN = "BBBBB";
    private static final String DEFAULT_EXTERNAL_ID = "AAAAA";
    private static final String UPDATED_EXTERNAL_ID = "BBBBB";


private static final Gender DEFAULT_GENDER = Gender.MR;
    private static final Gender UPDATED_GENDER = Gender.M;
    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";


private static final Language DEFAULT_LANGUAGE = Language.FR;
    private static final Language UPDATED_LANGUAGE = Language.EN;
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";
    private static final String DEFAULT_CELLULAR_PHONE = "AAAAA";
    private static final String UPDATED_CELLULAR_PHONE = "BBBBB";
    private static final String DEFAULT_FAX = "AAAAA";
    private static final String UPDATED_FAX = "BBBBB";

    private static final Long DEFAULT_AUTHENTICATION_TYPE = 1L;
    private static final Long UPDATED_AUTHENTICATION_TYPE = 2L;
    private static final String DEFAULT_THEME = "AAAAA";
    private static final String UPDATED_THEME = "BBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATOR_USER_ID = 1L;
    private static final Long UPDATED_CREATOR_USER_ID = 2L;

    private static final LocalDate DEFAULT_MODIFICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATOR_USER_ID = 1L;
    private static final Long UPDATED_UPDATOR_USER_ID = 2L;

    @Inject
    private AGACUserRepository aGACUserRepository;

    @Inject
    private AGACUserSearchRepository aGACUserSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAGACUserMockMvc;

    private AGACUser aGACUser;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AGACUserResource aGACUserResource = new AGACUserResource();
        ReflectionTestUtils.setField(aGACUserResource, "aGACUserRepository", aGACUserRepository);
        ReflectionTestUtils.setField(aGACUserResource, "aGACUserSearchRepository", aGACUserSearchRepository);
        this.restAGACUserMockMvc = MockMvcBuilders.standaloneSetup(aGACUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        aGACUser = new AGACUser();
        aGACUser.setStatus(DEFAULT_STATUS);
        aGACUser.setLogin(DEFAULT_LOGIN);
        aGACUser.setExternalId(DEFAULT_EXTERNAL_ID);
        aGACUser.setGender(DEFAULT_GENDER);
        aGACUser.setFirstName(DEFAULT_FIRST_NAME);
        aGACUser.setLastName(DEFAULT_LAST_NAME);
        aGACUser.setLanguage(DEFAULT_LANGUAGE);
        aGACUser.setEmail(DEFAULT_EMAIL);
        aGACUser.setPhone(DEFAULT_PHONE);
        aGACUser.setCellularPhone(DEFAULT_CELLULAR_PHONE);
        aGACUser.setFax(DEFAULT_FAX);
        aGACUser.setAuthenticationType(DEFAULT_AUTHENTICATION_TYPE);
        aGACUser.setTheme(DEFAULT_THEME);
        aGACUser.setCreationDate(DEFAULT_CREATION_DATE);
        aGACUser.setCreatorUserId(DEFAULT_CREATOR_USER_ID);
        aGACUser.setModificationDate(DEFAULT_MODIFICATION_DATE);
        aGACUser.setUpdatorUserId(DEFAULT_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void createAGACUser() throws Exception {
        int databaseSizeBeforeCreate = aGACUserRepository.findAll().size();

        // Create the AGACUser

        restAGACUserMockMvc.perform(post("/api/aGACUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aGACUser)))
                .andExpect(status().isCreated());

        // Validate the AGACUser in the database
        List<AGACUser> aGACUsers = aGACUserRepository.findAll();
        assertThat(aGACUsers).hasSize(databaseSizeBeforeCreate + 1);
        AGACUser testAGACUser = aGACUsers.get(aGACUsers.size() - 1);
        assertThat(testAGACUser.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAGACUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testAGACUser.getExternalId()).isEqualTo(DEFAULT_EXTERNAL_ID);
        assertThat(testAGACUser.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testAGACUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAGACUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAGACUser.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testAGACUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAGACUser.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testAGACUser.getCellularPhone()).isEqualTo(DEFAULT_CELLULAR_PHONE);
        assertThat(testAGACUser.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testAGACUser.getAuthenticationType()).isEqualTo(DEFAULT_AUTHENTICATION_TYPE);
        assertThat(testAGACUser.getTheme()).isEqualTo(DEFAULT_THEME);
        assertThat(testAGACUser.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAGACUser.getCreatorUserId()).isEqualTo(DEFAULT_CREATOR_USER_ID);
        assertThat(testAGACUser.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
        assertThat(testAGACUser.getUpdatorUserId()).isEqualTo(DEFAULT_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void checkCreatorUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = aGACUserRepository.findAll().size();
        // set the field null
        aGACUser.setCreatorUserId(null);

        // Create the AGACUser, which fails.

        restAGACUserMockMvc.perform(post("/api/aGACUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aGACUser)))
                .andExpect(status().isBadRequest());

        List<AGACUser> aGACUsers = aGACUserRepository.findAll();
        assertThat(aGACUsers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAGACUsers() throws Exception {
        // Initialize the database
        aGACUserRepository.saveAndFlush(aGACUser);

        // Get all the aGACUsers
        restAGACUserMockMvc.perform(get("/api/aGACUsers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aGACUser.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.intValue())))
                .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
                .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].cellularPhone").value(hasItem(DEFAULT_CELLULAR_PHONE.toString())))
                .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
                .andExpect(jsonPath("$.[*].authenticationType").value(hasItem(DEFAULT_AUTHENTICATION_TYPE.intValue())))
                .andExpect(jsonPath("$.[*].theme").value(hasItem(DEFAULT_THEME.toString())))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].creatorUserId").value(hasItem(DEFAULT_CREATOR_USER_ID.intValue())))
                .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].updatorUserId").value(hasItem(DEFAULT_UPDATOR_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getAGACUser() throws Exception {
        // Initialize the database
        aGACUserRepository.saveAndFlush(aGACUser);

        // Get the aGACUser
        restAGACUserMockMvc.perform(get("/api/aGACUsers/{id}", aGACUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(aGACUser.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.externalId").value(DEFAULT_EXTERNAL_ID.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.cellularPhone").value(DEFAULT_CELLULAR_PHONE.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.authenticationType").value(DEFAULT_AUTHENTICATION_TYPE.intValue()))
            .andExpect(jsonPath("$.theme").value(DEFAULT_THEME.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.creatorUserId").value(DEFAULT_CREATOR_USER_ID.intValue()))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE.toString()))
            .andExpect(jsonPath("$.updatorUserId").value(DEFAULT_UPDATOR_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAGACUser() throws Exception {
        // Get the aGACUser
        restAGACUserMockMvc.perform(get("/api/aGACUsers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAGACUser() throws Exception {
        // Initialize the database
        aGACUserRepository.saveAndFlush(aGACUser);

		int databaseSizeBeforeUpdate = aGACUserRepository.findAll().size();

        // Update the aGACUser
        aGACUser.setStatus(UPDATED_STATUS);
        aGACUser.setLogin(UPDATED_LOGIN);
        aGACUser.setExternalId(UPDATED_EXTERNAL_ID);
        aGACUser.setGender(UPDATED_GENDER);
        aGACUser.setFirstName(UPDATED_FIRST_NAME);
        aGACUser.setLastName(UPDATED_LAST_NAME);
        aGACUser.setLanguage(UPDATED_LANGUAGE);
        aGACUser.setEmail(UPDATED_EMAIL);
        aGACUser.setPhone(UPDATED_PHONE);
        aGACUser.setCellularPhone(UPDATED_CELLULAR_PHONE);
        aGACUser.setFax(UPDATED_FAX);
        aGACUser.setAuthenticationType(UPDATED_AUTHENTICATION_TYPE);
        aGACUser.setTheme(UPDATED_THEME);
        aGACUser.setCreationDate(UPDATED_CREATION_DATE);
        aGACUser.setCreatorUserId(UPDATED_CREATOR_USER_ID);
        aGACUser.setModificationDate(UPDATED_MODIFICATION_DATE);
        aGACUser.setUpdatorUserId(UPDATED_UPDATOR_USER_ID);

        restAGACUserMockMvc.perform(put("/api/aGACUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aGACUser)))
                .andExpect(status().isOk());

        // Validate the AGACUser in the database
        List<AGACUser> aGACUsers = aGACUserRepository.findAll();
        assertThat(aGACUsers).hasSize(databaseSizeBeforeUpdate);
        AGACUser testAGACUser = aGACUsers.get(aGACUsers.size() - 1);
        assertThat(testAGACUser.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAGACUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testAGACUser.getExternalId()).isEqualTo(UPDATED_EXTERNAL_ID);
        assertThat(testAGACUser.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testAGACUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAGACUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAGACUser.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testAGACUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAGACUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAGACUser.getCellularPhone()).isEqualTo(UPDATED_CELLULAR_PHONE);
        assertThat(testAGACUser.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testAGACUser.getAuthenticationType()).isEqualTo(UPDATED_AUTHENTICATION_TYPE);
        assertThat(testAGACUser.getTheme()).isEqualTo(UPDATED_THEME);
        assertThat(testAGACUser.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAGACUser.getCreatorUserId()).isEqualTo(UPDATED_CREATOR_USER_ID);
        assertThat(testAGACUser.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
        assertThat(testAGACUser.getUpdatorUserId()).isEqualTo(UPDATED_UPDATOR_USER_ID);
    }

    @Test
    @Transactional
    public void deleteAGACUser() throws Exception {
        // Initialize the database
        aGACUserRepository.saveAndFlush(aGACUser);

		int databaseSizeBeforeDelete = aGACUserRepository.findAll().size();

        // Get the aGACUser
        restAGACUserMockMvc.perform(delete("/api/aGACUsers/{id}", aGACUser.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AGACUser> aGACUsers = aGACUserRepository.findAll();
        assertThat(aGACUsers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
