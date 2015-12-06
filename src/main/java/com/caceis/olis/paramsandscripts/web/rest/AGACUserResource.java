package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.AGACUser;
import com.caceis.olis.paramsandscripts.repository.AGACUserRepository;
import com.caceis.olis.paramsandscripts.repository.search.AGACUserSearchRepository;
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
 * REST controller for managing AGACUser.
 */
@RestController
@RequestMapping("/api")
public class AGACUserResource {

    private final Logger log = LoggerFactory.getLogger(AGACUserResource.class);

    @Inject
    private AGACUserRepository aGACUserRepository;

    @Inject
    private AGACUserSearchRepository aGACUserSearchRepository;

    /**
     * POST  /aGACUsers -> Create a new aGACUser.
     */
    @RequestMapping(value = "/aGACUsers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AGACUser> createAGACUser(@Valid @RequestBody AGACUser aGACUser) throws URISyntaxException {
        log.debug("REST request to save AGACUser : {}", aGACUser);
        if (aGACUser.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new aGACUser cannot already have an ID").body(null);
        }
        AGACUser result = aGACUserRepository.save(aGACUser);
        aGACUserSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aGACUsers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("aGACUser", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aGACUsers -> Updates an existing aGACUser.
     */
    @RequestMapping(value = "/aGACUsers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AGACUser> updateAGACUser(@Valid @RequestBody AGACUser aGACUser) throws URISyntaxException {
        log.debug("REST request to update AGACUser : {}", aGACUser);
        if (aGACUser.getId() == null) {
            return createAGACUser(aGACUser);
        }
        AGACUser result = aGACUserRepository.save(aGACUser);
        aGACUserSearchRepository.save(aGACUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("aGACUser", aGACUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aGACUsers -> get all the aGACUsers.
     */
    @RequestMapping(value = "/aGACUsers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AGACUser> getAllAGACUsers() {
        log.debug("REST request to get all AGACUsers");
        return aGACUserRepository.findAll();
    }

    /**
     * B. AVE.
     */
    @RequestMapping(value = "/reIndexAGACUsers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void reIndexAGACUsers() {
        log.debug("REST request to re-index all AGACUsers");
        aGACUserRepository.findAll().forEach(p -> aGACUserSearchRepository.index(p));
    }
    
    /**
     * GET  /aGACUsers/:id -> get the "id" aGACUser.
     */
    @RequestMapping(value = "/aGACUsers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AGACUser> getAGACUser(@PathVariable Long id) {
        log.debug("REST request to get AGACUser : {}", id);
        return Optional.ofNullable(aGACUserRepository.findOne(id))
            .map(aGACUser -> new ResponseEntity<>(
                aGACUser,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /aGACUsers/:id -> delete the "id" aGACUser.
     */
    @RequestMapping(value = "/aGACUsers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAGACUser(@PathVariable Long id) {
        log.debug("REST request to delete AGACUser : {}", id);
        aGACUserRepository.delete(id);
        aGACUserSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("aGACUser", id.toString())).build();
    }

    /**
     * SEARCH  /_search/aGACUsers/:query -> search for the aGACUser corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/aGACUsers/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AGACUser> searchAGACUsers(@PathVariable String query) {
        return StreamSupport
            .stream(aGACUserSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
