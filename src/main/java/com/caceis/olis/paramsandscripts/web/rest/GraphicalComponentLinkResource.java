package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.GraphicalComponentLink;
import com.caceis.olis.paramsandscripts.repository.GraphicalComponentLinkRepository;
import com.caceis.olis.paramsandscripts.repository.search.GraphicalComponentLinkSearchRepository;
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
 * REST controller for managing GraphicalComponentLink.
 */
@RestController
@RequestMapping("/api")
public class GraphicalComponentLinkResource {

    private final Logger log = LoggerFactory.getLogger(GraphicalComponentLinkResource.class);

    @Inject
    private GraphicalComponentLinkRepository graphicalComponentLinkRepository;

    @Inject
    private GraphicalComponentLinkSearchRepository graphicalComponentLinkSearchRepository;

    /**
     * POST  /graphicalComponentLinks -> Create a new graphicalComponentLink.
     */
    @RequestMapping(value = "/graphicalComponentLinks",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GraphicalComponentLink> createGraphicalComponentLink(@Valid @RequestBody GraphicalComponentLink graphicalComponentLink) throws URISyntaxException {
        log.debug("REST request to save GraphicalComponentLink : {}", graphicalComponentLink);
        if (graphicalComponentLink.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new graphicalComponentLink cannot already have an ID").body(null);
        }
        GraphicalComponentLink result = graphicalComponentLinkRepository.save(graphicalComponentLink);
        graphicalComponentLinkSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/graphicalComponentLinks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("graphicalComponentLink", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /graphicalComponentLinks -> Updates an existing graphicalComponentLink.
     */
    @RequestMapping(value = "/graphicalComponentLinks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GraphicalComponentLink> updateGraphicalComponentLink(@Valid @RequestBody GraphicalComponentLink graphicalComponentLink) throws URISyntaxException {
        log.debug("REST request to update GraphicalComponentLink : {}", graphicalComponentLink);
        if (graphicalComponentLink.getId() == null) {
            return createGraphicalComponentLink(graphicalComponentLink);
        }
        GraphicalComponentLink result = graphicalComponentLinkRepository.save(graphicalComponentLink);
        graphicalComponentLinkSearchRepository.save(graphicalComponentLink);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("graphicalComponentLink", graphicalComponentLink.getId().toString()))
            .body(result);
    }

    /**
     * GET  /graphicalComponentLinks -> get all the graphicalComponentLinks.
     */
    @RequestMapping(value = "/graphicalComponentLinks",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GraphicalComponentLink> getAllGraphicalComponentLinks() {
        log.debug("REST request to get all GraphicalComponentLinks");
        return graphicalComponentLinkRepository.findAll();
    }
    
    /**
     * B. AVE.
     */
    @RequestMapping(value = "/reIndexAllGraphicalComponentLinks",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void reIndexAllGraphicalComponentParams() {
        log.debug("REST request to re-index all GraphicalComponentLinks");
        graphicalComponentLinkRepository.findAll().forEach(p -> graphicalComponentLinkSearchRepository.index(p));
    }
    
    /**
     * GET  /graphicalComponentLinks -> get all the graphicalComponentLinks.
     * AVE B.
     */
    @RequestMapping(value = "/graphicalComponentLinksByPromptId/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GraphicalComponentLink> getAllGraphicalComponentLinksByPromptId(@PathVariable Long id) {
    	log.debug("REST request to get AllGraphicalComponentLinksByPromptId : {}", id);
        return graphicalComponentLinkRepository.findByPromptId(id);
    }

    /**
     * GET  /graphicalComponentLinks/:id -> get the "id" graphicalComponentLink.
     */
    @RequestMapping(value = "/graphicalComponentLinks/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GraphicalComponentLink> getGraphicalComponentLink(@PathVariable Long id) {
        log.debug("REST request to get GraphicalComponentLink : {}", id);
        return Optional.ofNullable(graphicalComponentLinkRepository.findOne(id))
            .map(graphicalComponentLink -> new ResponseEntity<>(
                graphicalComponentLink,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /graphicalComponentLinks/:id -> delete the "id" graphicalComponentLink.
     */
    @RequestMapping(value = "/graphicalComponentLinks/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGraphicalComponentLink(@PathVariable Long id) {
        log.debug("REST request to delete GraphicalComponentLink : {}", id);
        graphicalComponentLinkRepository.delete(id);
        graphicalComponentLinkSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("graphicalComponentLink", id.toString())).build();
    }

    /**
     * SEARCH  /_search/graphicalComponentLinks/:query -> search for the graphicalComponentLink corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/graphicalComponentLinks/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GraphicalComponentLink> searchGraphicalComponentLinks(@PathVariable String query) {
        return StreamSupport
            .stream(graphicalComponentLinkSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
