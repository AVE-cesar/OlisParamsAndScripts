package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.CheckScript;
import com.caceis.olis.paramsandscripts.repository.CheckScriptRepository;
import com.caceis.olis.paramsandscripts.repository.search.CheckScriptSearchRepository;
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
 * REST controller for managing CheckScript.
 */
@RestController
@RequestMapping("/api")
public class CheckScriptResource {

    private final Logger log = LoggerFactory.getLogger(CheckScriptResource.class);

    @Inject
    private CheckScriptRepository checkScriptRepository;

    @Inject
    private CheckScriptSearchRepository checkScriptSearchRepository;

    /**
     * POST  /checkScripts -> Create a new checkScript.
     */
    @RequestMapping(value = "/checkScripts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CheckScript> createCheckScript(@RequestBody CheckScript checkScript) throws URISyntaxException {
        log.debug("REST request to save CheckScript : {}", checkScript);
        if (checkScript.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new checkScript cannot already have an ID").body(null);
        }
        CheckScript result = checkScriptRepository.save(checkScript);
        checkScriptSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/checkScripts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("checkScript", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /checkScripts -> Updates an existing checkScript.
     */
    @RequestMapping(value = "/checkScripts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CheckScript> updateCheckScript(@RequestBody CheckScript checkScript) throws URISyntaxException {
        log.debug("REST request to update CheckScript : {}", checkScript);
        if (checkScript.getId() == null) {
            return createCheckScript(checkScript);
        }
        CheckScript result = checkScriptRepository.save(checkScript);
        checkScriptSearchRepository.save(checkScript);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("checkScript", checkScript.getId().toString()))
            .body(result);
    }

    /**
     * GET  /checkScripts -> get all the checkScripts.
     */
    @RequestMapping(value = "/checkScripts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CheckScript> getAllCheckScripts() {
        log.debug("REST request to get all CheckScripts");
        return checkScriptRepository.findAll();
    }

    /**
     * B. AVE.
     */
    @RequestMapping(value = "/reIndexAllCheckScripts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void reIndexAllCheckScripts() {
        log.debug("REST request to re-index all CheckScripts");
        checkScriptRepository.findAll().forEach(p -> checkScriptSearchRepository.index(p));
    }
    
    /**
     * GET  /checkScripts/:id -> get the "id" checkScript.
     */
    @RequestMapping(value = "/checkScripts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CheckScript> getCheckScript(@PathVariable Long id) {
        log.debug("REST request to get CheckScript : {}", id);
        return Optional.ofNullable(checkScriptRepository.findOne(id))
            .map(checkScript -> new ResponseEntity<>(
                checkScript,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /checkScripts/:id -> delete the "id" checkScript.
     */
    @RequestMapping(value = "/checkScripts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCheckScript(@PathVariable Long id) {
        log.debug("REST request to delete CheckScript : {}", id);
        checkScriptRepository.delete(id);
        checkScriptSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("checkScript", id.toString())).build();
    }

    /**
     * SEARCH  /_search/checkScripts/:query -> search for the checkScript corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/checkScripts/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CheckScript> searchCheckScripts(@PathVariable String query) {
        return StreamSupport
            .stream(checkScriptSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
