package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.Datasource;
import com.caceis.olis.paramsandscripts.repository.DatasourceRepository;
import com.caceis.olis.paramsandscripts.repository.search.DatasourceSearchRepository;

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

import com.caceis.olis.paramsandscripts.domain.enumeration.DatasourceType;

/**
 * Test class for the DatasourceResource REST controller.
 *
 * @see DatasourceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DatasourceResourceIntTest {

    private static final String DEFAULT_CONDITION = "AAAAA";
    private static final String UPDATED_CONDITION = "BBBBB";


private static final DatasourceType DEFAULT_TYPE = DatasourceType.BIF;
    private static final DatasourceType UPDATED_TYPE = DatasourceType.WEB;
    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";
    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_DATASOURCE_LINK = "AAAAA";
    private static final String UPDATED_DATASOURCE_LINK = "BBBBB";
    private static final String DEFAULT_SQL = "AAAAA";
    private static final String UPDATED_SQL = "BBBBB";
    private static final String DEFAULT_SCRIPT = "AAAAA";
    private static final String UPDATED_SCRIPT = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_URL = "AAAAA";
    private static final String UPDATED_URL = "BBBBB";
    private static final String DEFAULT_REQUEST = "AAAAA";
    private static final String UPDATED_REQUEST = "BBBBB";
    private static final String DEFAULT_RESPONSE = "AAAAA";
    private static final String UPDATED_RESPONSE = "BBBBB";

    @Inject
    private DatasourceRepository datasourceRepository;

    @Inject
    private DatasourceSearchRepository datasourceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDatasourceMockMvc;

    private Datasource datasource;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DatasourceResource datasourceResource = new DatasourceResource();
        ReflectionTestUtils.setField(datasourceResource, "datasourceRepository", datasourceRepository);
        ReflectionTestUtils.setField(datasourceResource, "datasourceSearchRepository", datasourceSearchRepository);
        this.restDatasourceMockMvc = MockMvcBuilders.standaloneSetup(datasourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        datasource = new Datasource();
        datasource.setCondition(DEFAULT_CONDITION);
        datasource.setType(DEFAULT_TYPE);
        datasource.setValue(DEFAULT_VALUE);
        datasource.setCode(DEFAULT_CODE);
        datasource.setDatasourceLink(DEFAULT_DATASOURCE_LINK);
        datasource.setSql(DEFAULT_SQL);
        datasource.setScript(DEFAULT_SCRIPT);
        datasource.setDescription(DEFAULT_DESCRIPTION);
        datasource.setUrl(DEFAULT_URL);
        datasource.setRequest(DEFAULT_REQUEST);
        datasource.setResponse(DEFAULT_RESPONSE);
    }

    @Test
    @Transactional
    public void createDatasource() throws Exception {
        int databaseSizeBeforeCreate = datasourceRepository.findAll().size();

        // Create the Datasource

        restDatasourceMockMvc.perform(post("/api/datasources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(datasource)))
                .andExpect(status().isCreated());

        // Validate the Datasource in the database
        List<Datasource> datasources = datasourceRepository.findAll();
        assertThat(datasources).hasSize(databaseSizeBeforeCreate + 1);
        Datasource testDatasource = datasources.get(datasources.size() - 1);
        assertThat(testDatasource.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testDatasource.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDatasource.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDatasource.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDatasource.getDatasourceLink()).isEqualTo(DEFAULT_DATASOURCE_LINK);
        assertThat(testDatasource.getSql()).isEqualTo(DEFAULT_SQL);
        assertThat(testDatasource.getScript()).isEqualTo(DEFAULT_SCRIPT);
        assertThat(testDatasource.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDatasource.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testDatasource.getRequest()).isEqualTo(DEFAULT_REQUEST);
        assertThat(testDatasource.getResponse()).isEqualTo(DEFAULT_RESPONSE);
    }

    @Test
    @Transactional
    public void getAllDatasources() throws Exception {
        // Initialize the database
        datasourceRepository.saveAndFlush(datasource);

        // Get all the datasources
        restDatasourceMockMvc.perform(get("/api/datasources"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(datasource.getId().intValue())))
                .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].datasourceLink").value(hasItem(DEFAULT_DATASOURCE_LINK.toString())))
                .andExpect(jsonPath("$.[*].sql").value(hasItem(DEFAULT_SQL.toString())))
                .andExpect(jsonPath("$.[*].script").value(hasItem(DEFAULT_SCRIPT.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
                .andExpect(jsonPath("$.[*].request").value(hasItem(DEFAULT_REQUEST.toString())))
                .andExpect(jsonPath("$.[*].response").value(hasItem(DEFAULT_RESPONSE.toString())));
    }

    @Test
    @Transactional
    public void getDatasource() throws Exception {
        // Initialize the database
        datasourceRepository.saveAndFlush(datasource);

        // Get the datasource
        restDatasourceMockMvc.perform(get("/api/datasources/{id}", datasource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(datasource.getId().intValue()))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.datasourceLink").value(DEFAULT_DATASOURCE_LINK.toString()))
            .andExpect(jsonPath("$.sql").value(DEFAULT_SQL.toString()))
            .andExpect(jsonPath("$.script").value(DEFAULT_SCRIPT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.request").value(DEFAULT_REQUEST.toString()))
            .andExpect(jsonPath("$.response").value(DEFAULT_RESPONSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDatasource() throws Exception {
        // Get the datasource
        restDatasourceMockMvc.perform(get("/api/datasources/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatasource() throws Exception {
        // Initialize the database
        datasourceRepository.saveAndFlush(datasource);

		int databaseSizeBeforeUpdate = datasourceRepository.findAll().size();

        // Update the datasource
        datasource.setCondition(UPDATED_CONDITION);
        datasource.setType(UPDATED_TYPE);
        datasource.setValue(UPDATED_VALUE);
        datasource.setCode(UPDATED_CODE);
        datasource.setDatasourceLink(UPDATED_DATASOURCE_LINK);
        datasource.setSql(UPDATED_SQL);
        datasource.setScript(UPDATED_SCRIPT);
        datasource.setDescription(UPDATED_DESCRIPTION);
        datasource.setUrl(UPDATED_URL);
        datasource.setRequest(UPDATED_REQUEST);
        datasource.setResponse(UPDATED_RESPONSE);

        restDatasourceMockMvc.perform(put("/api/datasources")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(datasource)))
                .andExpect(status().isOk());

        // Validate the Datasource in the database
        List<Datasource> datasources = datasourceRepository.findAll();
        assertThat(datasources).hasSize(databaseSizeBeforeUpdate);
        Datasource testDatasource = datasources.get(datasources.size() - 1);
        assertThat(testDatasource.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testDatasource.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDatasource.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDatasource.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDatasource.getDatasourceLink()).isEqualTo(UPDATED_DATASOURCE_LINK);
        assertThat(testDatasource.getSql()).isEqualTo(UPDATED_SQL);
        assertThat(testDatasource.getScript()).isEqualTo(UPDATED_SCRIPT);
        assertThat(testDatasource.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDatasource.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testDatasource.getRequest()).isEqualTo(UPDATED_REQUEST);
        assertThat(testDatasource.getResponse()).isEqualTo(UPDATED_RESPONSE);
    }

    @Test
    @Transactional
    public void deleteDatasource() throws Exception {
        // Initialize the database
        datasourceRepository.saveAndFlush(datasource);

		int databaseSizeBeforeDelete = datasourceRepository.findAll().size();

        // Get the datasource
        restDatasourceMockMvc.perform(delete("/api/datasources/{id}", datasource.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Datasource> datasources = datasourceRepository.findAll();
        assertThat(datasources).hasSize(databaseSizeBeforeDelete - 1);
    }
}
