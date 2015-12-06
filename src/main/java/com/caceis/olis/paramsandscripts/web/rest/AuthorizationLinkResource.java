package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.AuthorizationLink;
import com.caceis.olis.paramsandscripts.repository.AuthorizationLinkRepository;
import com.caceis.olis.paramsandscripts.repository.search.AuthorizationLinkSearchRepository;
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
 * REST controller for managing AuthorizationLink.
 */
@RestController
@RequestMapping("/api")
public class AuthorizationLinkResource {

    private final Logger log = LoggerFactory.getLogger(AuthorizationLinkResource.class);

    @Inject
    private AuthorizationLinkRepository authorizationLinkRepository;

    @Inject
    private AuthorizationLinkSearchRepository authorizationLinkSearchRepository;

    /**
     * POST  /authorizationLinks -> Create a new authorizationLink.
     */
    @RequestMapping(value = "/authorizationLinks",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorizationLink> createAuthorizationLink(@RequestBody AuthorizationLink authorizationLink) throws URISyntaxException {
        log.debug("REST request to save AuthorizationLink : {}", authorizationLink);
        if (authorizationLink.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new authorizationLink cannot already have an ID").body(null);
        }
        AuthorizationLink result = authorizationLinkRepository.save(authorizationLink);
        authorizationLinkSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/authorizationLinks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("authorizationLink", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /authorizationLinks -> Updates an existing authorizationLink.
     */
    @RequestMapping(value = "/authorizationLinks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorizationLink> updateAuthorizationLink(@RequestBody AuthorizationLink authorizationLink) throws URISyntaxException {
        log.debug("REST request to update AuthorizationLink : {}", authorizationLink);
        if (authorizationLink.getId() == null) {
            return createAuthorizationLink(authorizationLink);
        }
        AuthorizationLink result = authorizationLinkRepository.save(authorizationLink);
        authorizationLinkSearchRepository.save(authorizationLink);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("authorizationLink", authorizationLink.getId().toString()))
            .body(result);
    }

    /**
     * GET  /authorizationLinks -> get all the authorizationLinks.
     */
    @RequestMapping(value = "/authorizationLinks",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AuthorizationLink> getAllAuthorizationLinks() {
        log.debug("REST request to get all AuthorizationLinks");
        return authorizationLinkRepository.findAll();
    }

    /**
     * B. AVE.
     */
    @RequestMapping(value = "/reIndexAuthorizationLinks",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void reIndexAuthorizationLinks() {
        log.debug("REST request to re-index all AuthorizationLinks");
        authorizationLinkRepository.findAll().forEach(p -> authorizationLinkSearchRepository.index(p));
    }
    
    /**
     * GET  /authorizationLinks/:id -> get the "id" authorizationLink.
     */
    @RequestMapping(value = "/authorizationLinks/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorizationLink> getAuthorizationLink(@PathVariable Long id) {
        log.debug("REST request to get AuthorizationLink : {}", id);
        return Optional.ofNullable(authorizationLinkRepository.findOne(id))
            .map(authorizationLink -> new ResponseEntity<>(
                authorizationLink,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /authorizationLinks/:id -> delete the "id" authorizationLink.
     */
    @RequestMapping(value = "/authorizationLinks/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAuthorizationLink(@PathVariable Long id) {
        log.debug("REST request to delete AuthorizationLink : {}", id);
        authorizationLinkRepository.delete(id);
        authorizationLinkSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("authorizationLink", id.toString())).build();
    }

    /**
     * SEARCH  /_search/authorizationLinks/:query -> search for the authorizationLink corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/authorizationLinks/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AuthorizationLink> searchAuthorizationLinks(@PathVariable String query) {
        return StreamSupport
            .stream(authorizationLinkSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
