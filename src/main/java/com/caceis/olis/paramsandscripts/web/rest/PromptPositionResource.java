package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.PromptPosition;
import com.caceis.olis.paramsandscripts.repository.PromptPositionRepository;
import com.caceis.olis.paramsandscripts.repository.search.PromptPositionSearchRepository;
import com.caceis.olis.paramsandscripts.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
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
 * REST controller for managing PromptPosition.
 */
@RestController
@RequestMapping("/api")
public class PromptPositionResource {

    private final Logger log = LoggerFactory.getLogger(PromptPositionResource.class);

    @Inject
    private PromptPositionRepository promptPositionRepository;

    @Inject
    private PromptPositionSearchRepository promptPositionSearchRepository;

    /**
     * POST  /promptPositions -> Create a new promptPosition.
     */
    @RequestMapping(value = "/promptPositions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PromptPosition> createPromptPosition(@RequestBody PromptPosition promptPosition) throws URISyntaxException {
        log.debug("REST request to save PromptPosition : {}", promptPosition);
        if (promptPosition.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new promptPosition cannot already have an ID").body(null);
        }
        PromptPosition result = promptPositionRepository.save(promptPosition);
        promptPositionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/promptPositions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("promptPosition", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /promptPositions -> Updates an existing promptPosition.
     */
    @RequestMapping(value = "/promptPositions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PromptPosition> updatePromptPosition(@RequestBody PromptPosition promptPosition) throws URISyntaxException {
        log.debug("REST request to update PromptPosition : {}", promptPosition);
        if (promptPosition.getId() == null) {
            return createPromptPosition(promptPosition);
        }
        PromptPosition result = promptPositionRepository.save(promptPosition);
        promptPositionSearchRepository.save(promptPosition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("promptPosition", promptPosition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /promptPositions -> get all the promptPositions.
     */
    @RequestMapping(value = "/promptPositions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PromptPosition> getAllPromptPositions() {
        log.debug("REST request to get all PromptPositions");
        return promptPositionRepository.findAll();
    }
    
    /**
     * B. AVE.
     */
    @RequestMapping(value = "/reIndexAllPromptPositions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void reIndexAllPromptPositions() {
        log.debug("REST request to re-index all PromptPositions");
        promptPositionRepository.findAll().forEach(p -> promptPositionSearchRepository.index(p));
    }

    /**
     * GET  /promptPositionsByReportId/:id -> get a list of promptPosition linked to a report id.
     */
    @RequestMapping(value = "/promptPositionsByReportId/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PromptPosition> getPromptPositionByReportId(@PathVariable Long id) {
        log.debug("REST request to get PromptPositionByReportId : {}", id);
        
        return promptPositionRepository.findByReportId(id);
    }
    
    /**
     * GET  /promptPositions/:id -> get the "id" promptPosition.
     */
    @RequestMapping(value = "/promptPositions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PromptPosition> getPromptPosition(@PathVariable Long id) {
        log.debug("REST request to get PromptPosition : {}", id);
        return Optional.ofNullable(promptPositionRepository.findOne(id))
            .map(promptPosition -> new ResponseEntity<>(
                promptPosition,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /promptPositions/:id -> delete the "id" promptPosition.
     */
    @RequestMapping(value = "/promptPositions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePromptPosition(@PathVariable Long id) {
        log.debug("REST request to delete PromptPosition : {}", id);
        promptPositionRepository.delete(id);
        promptPositionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("promptPosition", id.toString())).build();
    }

    /**
     * SEARCH  /_search/promptPositions/:query -> search for the promptPosition corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/promptPositions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PromptPosition> searchPromptPositions(@PathVariable String query) {
    	log.debug("Rechercle ElasticSearch : {}", query);
        return StreamSupport
            .stream(promptPositionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
