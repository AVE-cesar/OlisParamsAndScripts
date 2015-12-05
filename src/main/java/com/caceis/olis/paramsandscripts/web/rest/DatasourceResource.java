package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.Datasource;
import com.caceis.olis.paramsandscripts.repository.DatasourceRepository;
import com.caceis.olis.paramsandscripts.repository.search.DatasourceSearchRepository;
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
 * REST controller for managing Datasource.
 */
@RestController
@RequestMapping("/api")
public class DatasourceResource {

    private final Logger log = LoggerFactory.getLogger(DatasourceResource.class);

    @Inject
    private DatasourceRepository datasourceRepository;

    @Inject
    private DatasourceSearchRepository datasourceSearchRepository;

    /**
     * POST  /datasources -> Create a new datasource.
     */
    @RequestMapping(value = "/datasources",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Datasource> createDatasource(@Valid @RequestBody Datasource datasource) throws URISyntaxException {
        log.debug("REST request to save Datasource : {}", datasource);
        if (datasource.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new datasource cannot already have an ID").body(null);
        }
        Datasource result = datasourceRepository.save(datasource);
        datasourceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/datasources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("datasource", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /datasources -> Updates an existing datasource.
     */
    @RequestMapping(value = "/datasources",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Datasource> updateDatasource(@Valid @RequestBody Datasource datasource) throws URISyntaxException {
        log.debug("REST request to update Datasource : {}", datasource);
        if (datasource.getId() == null) {
            return createDatasource(datasource);
        }
        Datasource result = datasourceRepository.save(datasource);
        datasourceSearchRepository.save(datasource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("datasource", datasource.getId().toString()))
            .body(result);
    }

    /**
     * GET  /datasources -> get all the datasources.
     */
    @RequestMapping(value = "/datasources",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Datasource> getAllDatasources() {
        log.debug("REST request to get all Datasources");
        return datasourceRepository.findAll();
    }

    /**
     * B. AVE.
     */
    @RequestMapping(value = "/reIndexAllDatasources",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void reIndexAllDatasources() {
        log.debug("REST request to re-index all datasources");
        datasourceRepository.findAll().forEach(p -> datasourceSearchRepository.index(p));
    }
    
    /**
     * GET  /datasources/:id -> get the "id" datasource.
     */
    @RequestMapping(value = "/datasources/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Datasource> getDatasource(@PathVariable Long id) {
        log.debug("REST request to get Datasource : {}", id);
        return Optional.ofNullable(datasourceRepository.findOne(id))
            .map(datasource -> new ResponseEntity<>(
                datasource,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /datasources/:id -> delete the "id" datasource.
     */
    @RequestMapping(value = "/datasources/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDatasource(@PathVariable Long id) {
        log.debug("REST request to delete Datasource : {}", id);
        datasourceRepository.delete(id);
        datasourceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("datasource", id.toString())).build();
    }

    /**
     * SEARCH  /_search/datasources/:query -> search for the datasource corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/datasources/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Datasource> searchDatasources(@PathVariable String query) {
        return StreamSupport
            .stream(datasourceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
