package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.AuthorizationSetLink;
import com.caceis.olis.paramsandscripts.repository.AuthorizationSetLinkRepository;
import com.caceis.olis.paramsandscripts.repository.search.AuthorizationSetLinkSearchRepository;
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
 * REST controller for managing AuthorizationSetLink.
 */
@RestController
@RequestMapping("/api")
public class AuthorizationSetLinkResource {

    private final Logger log = LoggerFactory.getLogger(AuthorizationSetLinkResource.class);

    @Inject
    private AuthorizationSetLinkRepository authorizationSetLinkRepository;

    @Inject
    private AuthorizationSetLinkSearchRepository authorizationSetLinkSearchRepository;

    /**
     * POST  /authorizationSetLinks -> Create a new authorizationSetLink.
     */
    @RequestMapping(value = "/authorizationSetLinks",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorizationSetLink> createAuthorizationSetLink(@RequestBody AuthorizationSetLink authorizationSetLink) throws URISyntaxException {
        log.debug("REST request to save AuthorizationSetLink : {}", authorizationSetLink);
        if (authorizationSetLink.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new authorizationSetLink cannot already have an ID").body(null);
        }
        AuthorizationSetLink result = authorizationSetLinkRepository.save(authorizationSetLink);
        authorizationSetLinkSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/authorizationSetLinks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("authorizationSetLink", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /authorizationSetLinks -> Updates an existing authorizationSetLink.
     */
    @RequestMapping(value = "/authorizationSetLinks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorizationSetLink> updateAuthorizationSetLink(@RequestBody AuthorizationSetLink authorizationSetLink) throws URISyntaxException {
        log.debug("REST request to update AuthorizationSetLink : {}", authorizationSetLink);
        if (authorizationSetLink.getId() == null) {
            return createAuthorizationSetLink(authorizationSetLink);
        }
        AuthorizationSetLink result = authorizationSetLinkRepository.save(authorizationSetLink);
        authorizationSetLinkSearchRepository.save(authorizationSetLink);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("authorizationSetLink", authorizationSetLink.getId().toString()))
            .body(result);
    }

    /**
     * GET  /authorizationSetLinks -> get all the authorizationSetLinks.
     */
    @RequestMapping(value = "/authorizationSetLinks",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AuthorizationSetLink> getAllAuthorizationSetLinks() {
        log.debug("REST request to get all AuthorizationSetLinks");
        return authorizationSetLinkRepository.findAll();
    }

    /**
     * GET  /authorizationSetLinks/:id -> get the "id" authorizationSetLink.
     */
    @RequestMapping(value = "/authorizationSetLinks/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorizationSetLink> getAuthorizationSetLink(@PathVariable Long id) {
        log.debug("REST request to get AuthorizationSetLink : {}", id);
        return Optional.ofNullable(authorizationSetLinkRepository.findOne(id))
            .map(authorizationSetLink -> new ResponseEntity<>(
                authorizationSetLink,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /authorizationSetLinks/:id -> delete the "id" authorizationSetLink.
     */
    @RequestMapping(value = "/authorizationSetLinks/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAuthorizationSetLink(@PathVariable Long id) {
        log.debug("REST request to delete AuthorizationSetLink : {}", id);
        authorizationSetLinkRepository.delete(id);
        authorizationSetLinkSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("authorizationSetLink", id.toString())).build();
    }

    /**
     * SEARCH  /_search/authorizationSetLinks/:query -> search for the authorizationSetLink corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/authorizationSetLinks/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AuthorizationSetLink> searchAuthorizationSetLinks(@PathVariable String query) {
        return StreamSupport
            .stream(authorizationSetLinkSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
