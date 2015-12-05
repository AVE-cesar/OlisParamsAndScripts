package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.Report;
import com.caceis.olis.paramsandscripts.repository.ReportRepository;
import com.caceis.olis.paramsandscripts.repository.search.ReportSearchRepository;
import com.caceis.olis.paramsandscripts.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Report.
 */
@RestController
@RequestMapping("/api")
public class ReportResource {

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);

    @Inject
    private ReportRepository reportRepository;

    @Inject
    private ReportSearchRepository reportSearchRepository;

    /**
     * POST  /reports -> Create a new report.
     */
    @RequestMapping(value = "/reports",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Report> createReport(@Valid @RequestBody Report report) throws URISyntaxException {
        log.debug("REST request to save Report : {}", report);
        if (report.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new report cannot already have an ID").body(null);
        }
        Report result = reportRepository.save(report);
        reportSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("report", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reports -> Updates an existing report.
     */
    @RequestMapping(value = "/reports",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Report> updateReport(@Valid @RequestBody Report report) throws URISyntaxException {
        log.debug("REST request to update Report : {}", report);
        if (report.getId() == null) {
            return createReport(report);
        }
        Report result = reportRepository.save(report);
        reportSearchRepository.save(report);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("report", report.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reports -> get all the reports.
     */
    @RequestMapping(value = "/reports",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Report> getAllReports() {
        log.debug("REST request to get all Reports");
        
        return reportRepository.findAll(); 
    }
    
    /**
     * B. AVE.
     */
    @RequestMapping(value = "/reIndexAllReports",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void reIndexAllReports() {
        log.debug("REST request to re-index all reports");
        reportRepository.findAll().forEach(p -> reportSearchRepository.index(p));
    }

    /**
     * GET  /reports/:id -> get the "id" report.
     */
    @RequestMapping(value = "/reports/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Report> getReport(@PathVariable Long id) {
        log.debug("REST request to get Report : {}", id);
        return Optional.ofNullable(reportRepository.findOne(id))
            .map(report -> new ResponseEntity<>(
                report,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /reports/:id -> delete the "id" report.
     */
    @RequestMapping(value = "/reports/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        log.debug("REST request to delete Report : {}", id);
        reportRepository.delete(id);
        reportSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("report", id.toString())).build();
    }

    /**
     * SEARCH  /_search/reports/:query -> search for the report corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/reports/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Report> searchReports(@PathVariable String query) {
        return StreamSupport
            .stream(reportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
