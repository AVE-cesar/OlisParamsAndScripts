package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.GraphicalComponentLink;
import com.caceis.olis.paramsandscripts.domain.GraphicalComponentParam;
import com.caceis.olis.paramsandscripts.repository.GraphicalComponentParamRepository;
import com.caceis.olis.paramsandscripts.repository.search.GraphicalComponentParamSearchRepository;
import com.caceis.olis.paramsandscripts.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing GraphicalComponentParam.
 */
@RestController
@RequestMapping("/api")
public class GraphicalComponentParamResource {

    private final Logger log = LoggerFactory.getLogger(GraphicalComponentParamResource.class);

    @Inject
    private GraphicalComponentParamRepository graphicalComponentParamRepository;

    @Inject
    private GraphicalComponentParamSearchRepository graphicalComponentParamSearchRepository;

    /**
     * POST  /graphicalComponentParams -> Create a new graphicalComponentParam.
     */
    @RequestMapping(value = "/graphicalComponentParams",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GraphicalComponentParam> createGraphicalComponentParam(@Valid @RequestBody GraphicalComponentParam graphicalComponentParam) throws URISyntaxException {
        log.debug("REST request to save GraphicalComponentParam : {}", graphicalComponentParam);
        if (graphicalComponentParam.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new graphicalComponentParam cannot already have an ID").body(null);
        }
        GraphicalComponentParam result = graphicalComponentParamRepository.save(graphicalComponentParam);
        graphicalComponentParamSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/graphicalComponentParams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("graphicalComponentParam", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /graphicalComponentParams -> Updates an existing graphicalComponentParam.
     */
    @RequestMapping(value = "/graphicalComponentParams",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GraphicalComponentParam> updateGraphicalComponentParam(@Valid @RequestBody GraphicalComponentParam graphicalComponentParam) throws URISyntaxException {
        log.debug("REST request to update GraphicalComponentParam : {}", graphicalComponentParam);
        if (graphicalComponentParam.getId() == null) {
            return createGraphicalComponentParam(graphicalComponentParam);
        }
        GraphicalComponentParam result = graphicalComponentParamRepository.save(graphicalComponentParam);
        graphicalComponentParamSearchRepository.save(graphicalComponentParam);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("graphicalComponentParam", graphicalComponentParam.getId().toString()))
            .body(result);
    }

    /**
     * GET  /graphicalComponentParams -> get all the graphicalComponentParams.
     */
    @RequestMapping(value = "/graphicalComponentParams",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GraphicalComponentParam> getAllGraphicalComponentParams() {
        log.debug("REST request to get all GraphicalComponentParams");
        return graphicalComponentParamRepository.findAll();
    }
    
    /**
     * B. AVE.
     */
    @RequestMapping(value = "/reIndexAllGraphicalComponentParams",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void reIndexAllGraphicalComponentParams() {
        log.debug("REST request to re-index all GraphicalComponentParams");
        graphicalComponentParamRepository.findAll().forEach(p -> graphicalComponentParamSearchRepository.index(p));
    }
    
    /**
     * 
     * AVE B.
     */
    @RequestMapping(value = "/graphicalComponentParamsByPromptId/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GraphicalComponentParam> getAllGraphicalComponentParamsByPromptId(@PathVariable Long id) {
    	log.debug("REST request to get AllGraphicalComponentParamsByPromptId : {}", id);
        return graphicalComponentParamRepository.findByPromptId(id);
    }

    /**
     * GET  /graphicalComponentParams/:id -> get the "id" graphicalComponentParam.
     */
    @RequestMapping(value = "/graphicalComponentParams/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GraphicalComponentParam> getGraphicalComponentParam(@PathVariable Long id) {
        log.debug("REST request to get GraphicalComponentParam : {}", id);
        return Optional.ofNullable(graphicalComponentParamRepository.findOne(id))
            .map(graphicalComponentParam -> new ResponseEntity<>(
                graphicalComponentParam,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /graphicalComponentParams/:id -> delete the "id" graphicalComponentParam.
     */
    @RequestMapping(value = "/graphicalComponentParams/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGraphicalComponentParam(@PathVariable Long id) {
        log.debug("REST request to delete GraphicalComponentParam : {}", id);
        graphicalComponentParamRepository.delete(id);
        graphicalComponentParamSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("graphicalComponentParam", id.toString())).build();
    }

    /**
     * SEARCH  /_search/graphicalComponentParams/:query -> search for the graphicalComponentParam corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/graphicalComponentParams/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GraphicalComponentParam> searchGraphicalComponentParams(@PathVariable String query) {
        return StreamSupport
            .stream(graphicalComponentParamSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
