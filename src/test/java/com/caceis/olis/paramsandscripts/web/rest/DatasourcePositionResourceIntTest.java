package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.DatasourcePosition;
import com.caceis.olis.paramsandscripts.repository.DatasourcePositionRepository;
import com.caceis.olis.paramsandscripts.repository.search.DatasourcePositionSearchRepository;

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
 * Test class for the DatasourcePositionResource REST controller.
 *
 * @see DatasourcePositionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DatasourcePositionResourceIntTest {


    private static final Long DEFAULT_ORDER = 1L;
    private static final Long UPDATED_ORDER = 2L;

    @Inject
    private DatasourcePositionRepository datasourcePositionRepository;

    @Inject
    private DatasourcePositionSearchRepository datasourcePositionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDatasourcePositionMockMvc;

    private DatasourcePosition datasourcePosition;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DatasourcePositionResource datasourcePositionResource = new DatasourcePositionResource();
        ReflectionTestUtils.setField(datasourcePositionResource, "datasourcePositionRepository", datasourcePositionRepository);
        ReflectionTestUtils.setField(datasourcePositionResource, "datasourcePositionSearchRepository", datasourcePositionSearchRepository);
        this.restDatasourcePositionMockMvc = MockMvcBuilders.standaloneSetup(datasourcePositionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        datasourcePosition = new DatasourcePosition();
        datasourcePosition.setOrder(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createDatasourcePosition() throws Exception {
        int databaseSizeBeforeCreate = datasourcePositionRepository.findAll().size();

        // Create the DatasourcePosition

        restDatasourcePositionMockMvc.perform(post("/api/datasourcePositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(datasourcePosition)))
                .andExpect(status().isCreated());

        // Validate the DatasourcePosition in the database
        List<DatasourcePosition> datasourcePositions = datasourcePositionRepository.findAll();
        assertThat(datasourcePositions).hasSize(databaseSizeBeforeCreate + 1);
        DatasourcePosition testDatasourcePosition = datasourcePositions.get(datasourcePositions.size() - 1);
        assertThat(testDatasourcePosition.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void getAllDatasourcePositions() throws Exception {
        // Initialize the database
        datasourcePositionRepository.saveAndFlush(datasourcePosition);

        // Get all the datasourcePositions
        restDatasourcePositionMockMvc.perform(get("/api/datasourcePositions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(datasourcePosition.getId().intValue())))
                .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER.intValue())));
    }

    @Test
    @Transactional
    public void getDatasourcePosition() throws Exception {
        // Initialize the database
        datasourcePositionRepository.saveAndFlush(datasourcePosition);

        // Get the datasourcePosition
        restDatasourcePositionMockMvc.perform(get("/api/datasourcePositions/{id}", datasourcePosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(datasourcePosition.getId().intValue()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDatasourcePosition() throws Exception {
        // Get the datasourcePosition
        restDatasourcePositionMockMvc.perform(get("/api/datasourcePositions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatasourcePosition() throws Exception {
        // Initialize the database
        datasourcePositionRepository.saveAndFlush(datasourcePosition);

		int databaseSizeBeforeUpdate = datasourcePositionRepository.findAll().size();

        // Update the datasourcePosition
        datasourcePosition.setOrder(UPDATED_ORDER);

        restDatasourcePositionMockMvc.perform(put("/api/datasourcePositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(datasourcePosition)))
                .andExpect(status().isOk());

        // Validate the DatasourcePosition in the database
        List<DatasourcePosition> datasourcePositions = datasourcePositionRepository.findAll();
        assertThat(datasourcePositions).hasSize(databaseSizeBeforeUpdate);
        DatasourcePosition testDatasourcePosition = datasourcePositions.get(datasourcePositions.size() - 1);
        assertThat(testDatasourcePosition.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void deleteDatasourcePosition() throws Exception {
        // Initialize the database
        datasourcePositionRepository.saveAndFlush(datasourcePosition);

		int databaseSizeBeforeDelete = datasourcePositionRepository.findAll().size();

        // Get the datasourcePosition
        restDatasourcePositionMockMvc.perform(delete("/api/datasourcePositions/{id}", datasourcePosition.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DatasourcePosition> datasourcePositions = datasourcePositionRepository.findAll();
        assertThat(datasourcePositions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
