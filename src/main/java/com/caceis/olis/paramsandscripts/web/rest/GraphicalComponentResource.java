package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.GraphicalComponent;
import com.caceis.olis.paramsandscripts.repository.GraphicalComponentRepository;
import com.caceis.olis.paramsandscripts.repository.search.GraphicalComponentSearchRepository;
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
 * REST controller for managing GraphicalComponent.
 */
@RestController
@RequestMapping("/api")
public class GraphicalComponentResource {

    private final Logger log = LoggerFactory.getLogger(GraphicalComponentResource.class);

    @Inject
    private GraphicalComponentRepository graphicalComponentRepository;

    @Inject
    private GraphicalComponentSearchRepository graphicalComponentSearchRepository;

    /**
     * POST  /graphicalComponents -> Create a new graphicalComponent.
     */
    @RequestMapping(value = "/graphicalComponents",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GraphicalComponent> createGraphicalComponent(@Valid @RequestBody GraphicalComponent graphicalComponent) throws URISyntaxException {
        log.debug("REST request to save GraphicalComponent : {}", graphicalComponent);
        if (graphicalComponent.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new graphicalComponent cannot already have an ID").body(null);
        }
        GraphicalComponent result = graphicalComponentRepository.save(graphicalComponent);
        graphicalComponentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/graphicalComponents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("graphicalComponent", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /graphicalComponents -> Updates an existing graphicalComponent.
     */
    @RequestMapping(value = "/graphicalComponents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GraphicalComponent> updateGraphicalComponent(@Valid @RequestBody GraphicalComponent graphicalComponent) throws URISyntaxException {
        log.debug("REST request to update GraphicalComponent : {}", graphicalComponent);
        if (graphicalComponent.getId() == null) {
            return createGraphicalComponent(graphicalComponent);
        }
        GraphicalComponent result = graphicalComponentRepository.save(graphicalComponent);
        graphicalComponentSearchRepository.save(graphicalComponent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("graphicalComponent", graphicalComponent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /graphicalComponents -> get all the graphicalComponents.
     */
    @RequestMapping(value = "/graphicalComponents",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GraphicalComponent> getAllGraphicalComponents() {
        log.debug("REST request to get all GraphicalComponents");
        return graphicalComponentRepository.findAll();
    }
    
    /**
     * B. AVE.
     */
    @RequestMapping(value = "/reIndexAllGraphicalComponents",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void reIndexAllGraphicalComponents() {
        log.debug("REST request to re-index all GraphicalComponents");
        graphicalComponentRepository.findAll().forEach(p -> graphicalComponentSearchRepository.index(p));
    }

    /**
     * GET  /graphicalComponents/:id -> get the "id" graphicalComponent.
     */
    @RequestMapping(value = "/graphicalComponents/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GraphicalComponent> getGraphicalComponent(@PathVariable Long id) {
        log.debug("REST request to get GraphicalComponent : {}", id);
        return Optional.ofNullable(graphicalComponentRepository.findOne(id))
            .map(graphicalComponent -> new ResponseEntity<>(
                graphicalComponent,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /graphicalComponents/:id -> delete the "id" graphicalComponent.
     */
    @RequestMapping(value = "/graphicalComponents/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGraphicalComponent(@PathVariable Long id) {
        log.debug("REST request to delete GraphicalComponent : {}", id);
        graphicalComponentRepository.delete(id);
        graphicalComponentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("graphicalComponent", id.toString())).build();
    }

    /**
     * SEARCH  /_search/graphicalComponents/:query -> search for the graphicalComponent corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/graphicalComponents/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GraphicalComponent> searchGraphicalComponents(@PathVariable String query) {
        return StreamSupport
            .stream(graphicalComponentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
