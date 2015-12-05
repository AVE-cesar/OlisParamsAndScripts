package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.AGACUserLink;
import com.caceis.olis.paramsandscripts.repository.AGACUserLinkRepository;
import com.caceis.olis.paramsandscripts.repository.search.AGACUserLinkSearchRepository;
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
 * REST controller for managing AGACUserLink.
 */
@RestController
@RequestMapping("/api")
public class AGACUserLinkResource {

    private final Logger log = LoggerFactory.getLogger(AGACUserLinkResource.class);

    @Inject
    private AGACUserLinkRepository aGACUserLinkRepository;

    @Inject
    private AGACUserLinkSearchRepository aGACUserLinkSearchRepository;

    /**
     * POST  /aGACUserLinks -> Create a new aGACUserLink.
     */
    @RequestMapping(value = "/aGACUserLinks",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AGACUserLink> createAGACUserLink(@RequestBody AGACUserLink aGACUserLink) throws URISyntaxException {
        log.debug("REST request to save AGACUserLink : {}", aGACUserLink);
        if (aGACUserLink.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new aGACUserLink cannot already have an ID").body(null);
        }
        AGACUserLink result = aGACUserLinkRepository.save(aGACUserLink);
        aGACUserLinkSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aGACUserLinks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("aGACUserLink", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aGACUserLinks -> Updates an existing aGACUserLink.
     */
    @RequestMapping(value = "/aGACUserLinks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AGACUserLink> updateAGACUserLink(@RequestBody AGACUserLink aGACUserLink) throws URISyntaxException {
        log.debug("REST request to update AGACUserLink : {}", aGACUserLink);
        if (aGACUserLink.getId() == null) {
            return createAGACUserLink(aGACUserLink);
        }
        AGACUserLink result = aGACUserLinkRepository.save(aGACUserLink);
        aGACUserLinkSearchRepository.save(aGACUserLink);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("aGACUserLink", aGACUserLink.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aGACUserLinks -> get all the aGACUserLinks.
     */
    @RequestMapping(value = "/aGACUserLinks",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AGACUserLink> getAllAGACUserLinks() {
        log.debug("REST request to get all AGACUserLinks");
        return aGACUserLinkRepository.findAll();
    }

    /**
     * GET  /aGACUserLinks/:id -> get the "id" aGACUserLink.
     */
    @RequestMapping(value = "/aGACUserLinks/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AGACUserLink> getAGACUserLink(@PathVariable Long id) {
        log.debug("REST request to get AGACUserLink : {}", id);
        return Optional.ofNullable(aGACUserLinkRepository.findOne(id))
            .map(aGACUserLink -> new ResponseEntity<>(
                aGACUserLink,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /aGACUserLinks/:id -> delete the "id" aGACUserLink.
     */
    @RequestMapping(value = "/aGACUserLinks/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAGACUserLink(@PathVariable Long id) {
        log.debug("REST request to delete AGACUserLink : {}", id);
        aGACUserLinkRepository.delete(id);
        aGACUserLinkSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("aGACUserLink", id.toString())).build();
    }

    /**
     * SEARCH  /_search/aGACUserLinks/:query -> search for the aGACUserLink corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/aGACUserLinks/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AGACUserLink> searchAGACUserLinks(@PathVariable String query) {
        return StreamSupport
            .stream(aGACUserLinkSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
