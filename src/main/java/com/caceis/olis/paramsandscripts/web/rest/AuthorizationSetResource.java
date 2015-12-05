package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.AuthorizationSet;
import com.caceis.olis.paramsandscripts.repository.AuthorizationSetRepository;
import com.caceis.olis.paramsandscripts.repository.search.AuthorizationSetSearchRepository;
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
 * REST controller for managing AuthorizationSet.
 */
@RestController
@RequestMapping("/api")
public class AuthorizationSetResource {

    private final Logger log = LoggerFactory.getLogger(AuthorizationSetResource.class);

    @Inject
    private AuthorizationSetRepository authorizationSetRepository;

    @Inject
    private AuthorizationSetSearchRepository authorizationSetSearchRepository;

    /**
     * POST  /authorizationSets -> Create a new authorizationSet.
     */
    @RequestMapping(value = "/authorizationSets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorizationSet> createAuthorizationSet(@Valid @RequestBody AuthorizationSet authorizationSet) throws URISyntaxException {
        log.debug("REST request to save AuthorizationSet : {}", authorizationSet);
        if (authorizationSet.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new authorizationSet cannot already have an ID").body(null);
        }
        AuthorizationSet result = authorizationSetRepository.save(authorizationSet);
        authorizationSetSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/authorizationSets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("authorizationSet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /authorizationSets -> Updates an existing authorizationSet.
     */
    @RequestMapping(value = "/authorizationSets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorizationSet> updateAuthorizationSet(@Valid @RequestBody AuthorizationSet authorizationSet) throws URISyntaxException {
        log.debug("REST request to update AuthorizationSet : {}", authorizationSet);
        if (authorizationSet.getId() == null) {
            return createAuthorizationSet(authorizationSet);
        }
        AuthorizationSet result = authorizationSetRepository.save(authorizationSet);
        authorizationSetSearchRepository.save(authorizationSet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("authorizationSet", authorizationSet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /authorizationSets -> get all the authorizationSets.
     */
    @RequestMapping(value = "/authorizationSets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AuthorizationSet> getAllAuthorizationSets() {
        log.debug("REST request to get all AuthorizationSets");
        return authorizationSetRepository.findAll();
    }

    /**
     * GET  /authorizationSets/:id -> get the "id" authorizationSet.
     */
    @RequestMapping(value = "/authorizationSets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorizationSet> getAuthorizationSet(@PathVariable Long id) {
        log.debug("REST request to get AuthorizationSet : {}", id);
        return Optional.ofNullable(authorizationSetRepository.findOne(id))
            .map(authorizationSet -> new ResponseEntity<>(
                authorizationSet,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /authorizationSets/:id -> delete the "id" authorizationSet.
     */
    @RequestMapping(value = "/authorizationSets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAuthorizationSet(@PathVariable Long id) {
        log.debug("REST request to delete AuthorizationSet : {}", id);
        authorizationSetRepository.delete(id);
        authorizationSetSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("authorizationSet", id.toString())).build();
    }

    /**
     * SEARCH  /_search/authorizationSets/:query -> search for the authorizationSet corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/authorizationSets/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AuthorizationSet> searchAuthorizationSets(@PathVariable String query) {
        return StreamSupport
            .stream(authorizationSetSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
