package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.DatasourcePosition;
import com.caceis.olis.paramsandscripts.domain.GraphicalComponentParam;
import com.caceis.olis.paramsandscripts.repository.DatasourcePositionRepository;
import com.caceis.olis.paramsandscripts.repository.search.DatasourcePositionSearchRepository;
import com.caceis.olis.paramsandscripts.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DatasourcePosition.
 */
@RestController
@RequestMapping("/api")
public class DatasourcePositionResource {

    private final Logger log = LoggerFactory.getLogger(DatasourcePositionResource.class);

    @Inject
    private DatasourcePositionRepository datasourcePositionRepository;

    @Inject
    private DatasourcePositionSearchRepository datasourcePositionSearchRepository;

    /**
     * POST  /datasourcePositions -> Create a new datasourcePosition.
     */
    @RequestMapping(value = "/datasourcePositions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DatasourcePosition> createDatasourcePosition(@RequestBody DatasourcePosition datasourcePosition) throws URISyntaxException {
        log.debug("REST request to save DatasourcePosition : {}", datasourcePosition);
        if (datasourcePosition.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new datasourcePosition cannot already have an ID").body(null);
        }
        DatasourcePosition result = datasourcePositionRepository.save(datasourcePosition);
        datasourcePositionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/datasourcePositions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("datasourcePosition", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /datasourcePositions -> Updates an existing datasourcePosition.
     */
    @RequestMapping(value = "/datasourcePositions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DatasourcePosition> updateDatasourcePosition(@RequestBody DatasourcePosition datasourcePosition) throws URISyntaxException {
        log.debug("REST request to update DatasourcePosition : {}", datasourcePosition);
        if (datasourcePosition.getId() == null) {
            return createDatasourcePosition(datasourcePosition);
        }
        DatasourcePosition result = datasourcePositionRepository.save(datasourcePosition);
        datasourcePositionSearchRepository.save(datasourcePosition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("datasourcePosition", datasourcePosition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /datasourcePositions -> get all the datasourcePositions.
     */
    @RequestMapping(value = "/datasourcePositions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DatasourcePosition> getAllDatasourcePositions() {
        log.debug("REST request to get all DatasourcePositions");
        return datasourcePositionRepository.findAll();
    }

    /**
     * B. AVE.
     */
    @RequestMapping(value = "/reIndexAllDatasourcePositions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void reIndexAllDatasourcePositions() {
        log.debug("REST request to re-index all datasource Positions");
        datasourcePositionRepository.findAll().forEach(p -> datasourcePositionSearchRepository.index(p));
    }
    
    /**
     * B. AVE
     */
    @RequestMapping(value = "/datasourcePositionsByPromptId/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DatasourcePosition> getAllDatasourcePositionsByPromptId(@PathVariable Long id) {
        log.debug("REST request to get all DatasourcePositionsByPromptId : {}", id);
        return datasourcePositionRepository.findByPromptId(id);
    }
  
    /**
     * GET  /datasourcePositions/:id -> get the "id" datasourcePosition.
     */
    @RequestMapping(value = "/datasourcePositions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DatasourcePosition> getDatasourcePosition(@PathVariable Long id) {
        log.debug("REST request to get DatasourcePosition : {}", id);
        return Optional.ofNullable(datasourcePositionRepository.findOne(id))
            .map(datasourcePosition -> new ResponseEntity<>(
                datasourcePosition,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /datasourcePositions/:id -> delete the "id" datasourcePosition.
     */
    @RequestMapping(value = "/datasourcePositions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDatasourcePosition(@PathVariable Long id) {
        log.debug("REST request to delete DatasourcePosition : {}", id);
        datasourcePositionRepository.delete(id);
        datasourcePositionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("datasourcePosition", id.toString())).build();
    }

    /**
     * SEARCH  /_search/datasourcePositions/:query -> search for the datasourcePosition corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/datasourcePositions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DatasourcePosition> searchDatasourcePositions(@PathVariable String query) {
        return StreamSupport
            .stream(datasourcePositionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
