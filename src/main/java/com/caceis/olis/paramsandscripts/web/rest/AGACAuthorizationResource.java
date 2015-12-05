package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.AGACAuthorization;
import com.caceis.olis.paramsandscripts.repository.AGACAuthorizationRepository;
import com.caceis.olis.paramsandscripts.repository.search.AGACAuthorizationSearchRepository;
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
 * REST controller for managing AGACAuthorization.
 */
@RestController
@RequestMapping("/api")
public class AGACAuthorizationResource {

    private final Logger log = LoggerFactory.getLogger(AGACAuthorizationResource.class);

    @Inject
    private AGACAuthorizationRepository aGACAuthorizationRepository;

    @Inject
    private AGACAuthorizationSearchRepository aGACAuthorizationSearchRepository;

    /**
     * POST  /aGACAuthorizations -> Create a new aGACAuthorization.
     */
    @RequestMapping(value = "/aGACAuthorizations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AGACAuthorization> createAGACAuthorization(@Valid @RequestBody AGACAuthorization aGACAuthorization) throws URISyntaxException {
        log.debug("REST request to save AGACAuthorization : {}", aGACAuthorization);
        if (aGACAuthorization.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new aGACAuthorization cannot already have an ID").body(null);
        }
        AGACAuthorization result = aGACAuthorizationRepository.save(aGACAuthorization);
        aGACAuthorizationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aGACAuthorizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("aGACAuthorization", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aGACAuthorizations -> Updates an existing aGACAuthorization.
     */
    @RequestMapping(value = "/aGACAuthorizations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AGACAuthorization> updateAGACAuthorization(@Valid @RequestBody AGACAuthorization aGACAuthorization) throws URISyntaxException {
        log.debug("REST request to update AGACAuthorization : {}", aGACAuthorization);
        if (aGACAuthorization.getId() == null) {
            return createAGACAuthorization(aGACAuthorization);
        }
        AGACAuthorization result = aGACAuthorizationRepository.save(aGACAuthorization);
        aGACAuthorizationSearchRepository.save(aGACAuthorization);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("aGACAuthorization", aGACAuthorization.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aGACAuthorizations -> get all the aGACAuthorizations.
     */
    @RequestMapping(value = "/aGACAuthorizations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AGACAuthorization> getAllAGACAuthorizations() {
        log.debug("REST request to get all AGACAuthorizations");
        return aGACAuthorizationRepository.findAll();
    }

    /**
     * GET  /aGACAuthorizations/:id -> get the "id" aGACAuthorization.
     */
    @RequestMapping(value = "/aGACAuthorizations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AGACAuthorization> getAGACAuthorization(@PathVariable Long id) {
        log.debug("REST request to get AGACAuthorization : {}", id);
        return Optional.ofNullable(aGACAuthorizationRepository.findOne(id))
            .map(aGACAuthorization -> new ResponseEntity<>(
                aGACAuthorization,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /aGACAuthorizations/:id -> delete the "id" aGACAuthorization.
     */
    @RequestMapping(value = "/aGACAuthorizations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAGACAuthorization(@PathVariable Long id) {
        log.debug("REST request to delete AGACAuthorization : {}", id);
        aGACAuthorizationRepository.delete(id);
        aGACAuthorizationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("aGACAuthorization", id.toString())).build();
    }

    /**
     * SEARCH  /_search/aGACAuthorizations/:query -> search for the aGACAuthorization corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/aGACAuthorizations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AGACAuthorization> searchAGACAuthorizations(@PathVariable String query) {
        return StreamSupport
            .stream(aGACAuthorizationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
