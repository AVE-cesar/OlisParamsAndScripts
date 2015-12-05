package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.ParamCategory;
import com.caceis.olis.paramsandscripts.repository.ParamCategoryRepository;
import com.caceis.olis.paramsandscripts.repository.search.ParamCategorySearchRepository;

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
 * Test class for the ParamCategoryResource REST controller.
 *
 * @see ParamCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ParamCategoryResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private ParamCategoryRepository paramCategoryRepository;

    @Inject
    private ParamCategorySearchRepository paramCategorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restParamCategoryMockMvc;

    private ParamCategory paramCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ParamCategoryResource paramCategoryResource = new ParamCategoryResource();
        ReflectionTestUtils.setField(paramCategoryResource, "paramCategoryRepository", paramCategoryRepository);
        ReflectionTestUtils.setField(paramCategoryResource, "paramCategorySearchRepository", paramCategorySearchRepository);
        this.restParamCategoryMockMvc = MockMvcBuilders.standaloneSetup(paramCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        paramCategory = new ParamCategory();
        paramCategory.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createParamCategory() throws Exception {
        int databaseSizeBeforeCreate = paramCategoryRepository.findAll().size();

        // Create the ParamCategory

        restParamCategoryMockMvc.perform(post("/api/paramCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(paramCategory)))
                .andExpect(status().isCreated());

        // Validate the ParamCategory in the database
        List<ParamCategory> paramCategorys = paramCategoryRepository.findAll();
        assertThat(paramCategorys).hasSize(databaseSizeBeforeCreate + 1);
        ParamCategory testParamCategory = paramCategorys.get(paramCategorys.size() - 1);
        assertThat(testParamCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllParamCategorys() throws Exception {
        // Initialize the database
        paramCategoryRepository.saveAndFlush(paramCategory);

        // Get all the paramCategorys
        restParamCategoryMockMvc.perform(get("/api/paramCategorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(paramCategory.getId())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getParamCategory() throws Exception {
        // Initialize the database
        paramCategoryRepository.saveAndFlush(paramCategory);

        // Get the paramCategory
        restParamCategoryMockMvc.perform(get("/api/paramCategorys/{id}", paramCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(paramCategory.getId()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParamCategory() throws Exception {
        // Get the paramCategory
        restParamCategoryMockMvc.perform(get("/api/paramCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParamCategory() throws Exception {
        // Initialize the database
        paramCategoryRepository.saveAndFlush(paramCategory);

		int databaseSizeBeforeUpdate = paramCategoryRepository.findAll().size();

        // Update the paramCategory
        paramCategory.setDescription(UPDATED_DESCRIPTION);

        restParamCategoryMockMvc.perform(put("/api/paramCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(paramCategory)))
                .andExpect(status().isOk());

        // Validate the ParamCategory in the database
        List<ParamCategory> paramCategorys = paramCategoryRepository.findAll();
        assertThat(paramCategorys).hasSize(databaseSizeBeforeUpdate);
        ParamCategory testParamCategory = paramCategorys.get(paramCategorys.size() - 1);
        assertThat(testParamCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteParamCategory() throws Exception {
        // Initialize the database
        paramCategoryRepository.saveAndFlush(paramCategory);

		int databaseSizeBeforeDelete = paramCategoryRepository.findAll().size();

        // Get the paramCategory
        restParamCategoryMockMvc.perform(delete("/api/paramCategorys/{id}", paramCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ParamCategory> paramCategorys = paramCategoryRepository.findAll();
        assertThat(paramCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
