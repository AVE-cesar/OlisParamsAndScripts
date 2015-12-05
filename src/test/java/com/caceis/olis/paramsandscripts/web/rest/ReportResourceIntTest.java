package com.caceis.olis.paramsandscripts.web.rest;

import com.caceis.olis.paramsandscripts.Application;
import com.caceis.olis.paramsandscripts.domain.Report;
import com.caceis.olis.paramsandscripts.repository.ReportRepository;
import com.caceis.olis.paramsandscripts.repository.search.ReportSearchRepository;

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

import com.caceis.olis.paramsandscripts.domain.enumeration.Specific;
import com.caceis.olis.paramsandscripts.domain.enumeration.Raw;

/**
 * Test class for the ReportResource REST controller.
 *
 * @see ReportResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ReportResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_TRIGGER_CODE = "AAAAA";
    private static final String UPDATED_TRIGGER_CODE = "BBBBB";
    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";
    private static final String DEFAULT_DOMAIN = "AAAAA";
    private static final String UPDATED_DOMAIN = "BBBBB";

    private static final Integer DEFAULT_GENERATION_TYPE = 1;
    private static final Integer UPDATED_GENERATION_TYPE = 2;


private static final Specific DEFAULT_SPECIFIC = Specific.YES;
    private static final Specific UPDATED_SPECIFIC = Specific.NO;


private static final Raw DEFAULT_RAW = Raw.Y;
    private static final Raw UPDATED_RAW = Raw.N;

    private static final Long DEFAULT_FAT_RAW_REPORT = 1L;
    private static final Long UPDATED_FAT_RAW_REPORT = 2L;
    private static final String DEFAULT_USER_ACCESS = "AAAAA";
    private static final String UPDATED_USER_ACCESS = "BBBBB";

    @Inject
    private ReportRepository reportRepository;

    @Inject
    private ReportSearchRepository reportSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restReportMockMvc;

    private Report report;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReportResource reportResource = new ReportResource();
        ReflectionTestUtils.setField(reportResource, "reportRepository", reportRepository);
        ReflectionTestUtils.setField(reportResource, "reportSearchRepository", reportSearchRepository);
        this.restReportMockMvc = MockMvcBuilders.standaloneSetup(reportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        report = new Report();
        report.setName(DEFAULT_NAME);
        report.setTriggerCode(DEFAULT_TRIGGER_CODE);
        report.setType(DEFAULT_TYPE);
        report.setDomain(DEFAULT_DOMAIN);
        report.setGenerationType(DEFAULT_GENERATION_TYPE);
        report.setSpecific(DEFAULT_SPECIFIC);
        report.setRaw(DEFAULT_RAW);
        report.setFatRawReport(DEFAULT_FAT_RAW_REPORT);
        report.setUserAccess(DEFAULT_USER_ACCESS);
    }

    @Test
    @Transactional
    public void createReport() throws Exception {
        int databaseSizeBeforeCreate = reportRepository.findAll().size();

        // Create the Report

        restReportMockMvc.perform(post("/api/reports")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(report)))
                .andExpect(status().isCreated());

        // Validate the Report in the database
        List<Report> reports = reportRepository.findAll();
        assertThat(reports).hasSize(databaseSizeBeforeCreate + 1);
        Report testReport = reports.get(reports.size() - 1);
        assertThat(testReport.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReport.getTriggerCode()).isEqualTo(DEFAULT_TRIGGER_CODE);
        assertThat(testReport.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testReport.getDomain()).isEqualTo(DEFAULT_DOMAIN);
        assertThat(testReport.getGenerationType()).isEqualTo(DEFAULT_GENERATION_TYPE);
        assertThat(testReport.getSpecific()).isEqualTo(DEFAULT_SPECIFIC);
        assertThat(testReport.getRaw()).isEqualTo(DEFAULT_RAW);
        assertThat(testReport.getFatRawReport()).isEqualTo(DEFAULT_FAT_RAW_REPORT);
        assertThat(testReport.getUserAccess()).isEqualTo(DEFAULT_USER_ACCESS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportRepository.findAll().size();
        // set the field null
        report.setName(null);

        // Create the Report, which fails.

        restReportMockMvc.perform(post("/api/reports")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(report)))
                .andExpect(status().isBadRequest());

        List<Report> reports = reportRepository.findAll();
        assertThat(reports).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRawIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportRepository.findAll().size();
        // set the field null
        report.setRaw(null);

        // Create the Report, which fails.

        restReportMockMvc.perform(post("/api/reports")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(report)))
                .andExpect(status().isBadRequest());

        List<Report> reports = reportRepository.findAll();
        assertThat(reports).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReports() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reports
        restReportMockMvc.perform(get("/api/reports"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(report.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].triggerCode").value(hasItem(DEFAULT_TRIGGER_CODE.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())))
                .andExpect(jsonPath("$.[*].generationType").value(hasItem(DEFAULT_GENERATION_TYPE)))
                .andExpect(jsonPath("$.[*].specific").value(hasItem(DEFAULT_SPECIFIC.toString())))
                .andExpect(jsonPath("$.[*].raw").value(hasItem(DEFAULT_RAW.toString())))
                .andExpect(jsonPath("$.[*].fatRawReport").value(hasItem(DEFAULT_FAT_RAW_REPORT.intValue())))
                .andExpect(jsonPath("$.[*].userAccess").value(hasItem(DEFAULT_USER_ACCESS.toString())));
    }

    @Test
    @Transactional
    public void getReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get the report
        restReportMockMvc.perform(get("/api/reports/{id}", report.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(report.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.triggerCode").value(DEFAULT_TRIGGER_CODE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN.toString()))
            .andExpect(jsonPath("$.generationType").value(DEFAULT_GENERATION_TYPE))
            .andExpect(jsonPath("$.specific").value(DEFAULT_SPECIFIC.toString()))
            .andExpect(jsonPath("$.raw").value(DEFAULT_RAW.toString()))
            .andExpect(jsonPath("$.fatRawReport").value(DEFAULT_FAT_RAW_REPORT.intValue()))
            .andExpect(jsonPath("$.userAccess").value(DEFAULT_USER_ACCESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReport() throws Exception {
        // Get the report
        restReportMockMvc.perform(get("/api/reports/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

		int databaseSizeBeforeUpdate = reportRepository.findAll().size();

        // Update the report
        report.setName(UPDATED_NAME);
        report.setTriggerCode(UPDATED_TRIGGER_CODE);
        report.setType(UPDATED_TYPE);
        report.setDomain(UPDATED_DOMAIN);
        report.setGenerationType(UPDATED_GENERATION_TYPE);
        report.setSpecific(UPDATED_SPECIFIC);
        report.setRaw(UPDATED_RAW);
        report.setFatRawReport(UPDATED_FAT_RAW_REPORT);
        report.setUserAccess(UPDATED_USER_ACCESS);

        restReportMockMvc.perform(put("/api/reports")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(report)))
                .andExpect(status().isOk());

        // Validate the Report in the database
        List<Report> reports = reportRepository.findAll();
        assertThat(reports).hasSize(databaseSizeBeforeUpdate);
        Report testReport = reports.get(reports.size() - 1);
        assertThat(testReport.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReport.getTriggerCode()).isEqualTo(UPDATED_TRIGGER_CODE);
        assertThat(testReport.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReport.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testReport.getGenerationType()).isEqualTo(UPDATED_GENERATION_TYPE);
        assertThat(testReport.getSpecific()).isEqualTo(UPDATED_SPECIFIC);
        assertThat(testReport.getRaw()).isEqualTo(UPDATED_RAW);
        assertThat(testReport.getFatRawReport()).isEqualTo(UPDATED_FAT_RAW_REPORT);
        assertThat(testReport.getUserAccess()).isEqualTo(UPDATED_USER_ACCESS);
    }

    @Test
    @Transactional
    public void deleteReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

		int databaseSizeBeforeDelete = reportRepository.findAll().size();

        // Get the report
        restReportMockMvc.perform(delete("/api/reports/{id}", report.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Report> reports = reportRepository.findAll();
        assertThat(reports).hasSize(databaseSizeBeforeDelete - 1);
    }
}
