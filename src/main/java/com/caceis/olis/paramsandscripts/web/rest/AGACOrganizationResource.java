package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.AGACOrganization;
import com.caceis.olis.paramsandscripts.repository.AGACOrganizationRepository;
import com.caceis.olis.paramsandscripts.repository.search.AGACOrganizationSearchRepository;
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
 * REST controller for managing AGACOrganization.
 */
@RestController
@RequestMapping("/api")
public class AGACOrganizationResource {

    private final Logger log = LoggerFactory.getLogger(AGACOrganizationResource.class);

    @Inject
    private AGACOrganizationRepository aGACOrganizationRepository;

    @Inject
    private AGACOrganizationSearchRepository aGACOrganizationSearchRepository;

    /**
     * POST  /aGACOrganizations -> Create a new aGACOrganization.
     */
    @RequestMapping(value = "/aGACOrganizations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AGACOrganization> createAGACOrganization(@Valid @RequestBody AGACOrganization aGACOrganization) throws URISyntaxException {
        log.debug("REST request to save AGACOrganization : {}", aGACOrganization);
        if (aGACOrganization.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new aGACOrganization cannot already have an ID").body(null);
        }
        AGACOrganization result = aGACOrganizationRepository.save(aGACOrganization);
        aGACOrganizationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aGACOrganizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("aGACOrganization", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aGACOrganizations -> Updates an existing aGACOrganization.
     */
    @RequestMapping(value = "/aGACOrganizations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AGACOrganization> updateAGACOrganization(@Valid @RequestBody AGACOrganization aGACOrganization) throws URISyntaxException {
        log.debug("REST request to update AGACOrganization : {}", aGACOrganization);
        if (aGACOrganization.getId() == null) {
            return createAGACOrganization(aGACOrganization);
        }
        AGACOrganization result = aGACOrganizationRepository.save(aGACOrganization);
        aGACOrganizationSearchRepository.save(aGACOrganization);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("aGACOrganization", aGACOrganization.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aGACOrganizations -> get all the aGACOrganizations.
     */
    @RequestMapping(value = "/aGACOrganizations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AGACOrganization> getAllAGACOrganizations() {
        log.debug("REST request to get all AGACOrganizations");
        return aGACOrganizationRepository.findAll();
    }

    /**
     * B. AVE.
     */
    @RequestMapping(value = "/reIndexAGACOrganizations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void reIndexAGACOrganizations() {
        log.debug("REST request to re-index all AGACOrganizations");
        aGACOrganizationRepository.findAll().forEach(p -> aGACOrganizationSearchRepository.index(p));
    }
    
    /**
     * GET  /aGACOrganizations/:id -> get the "id" aGACOrganization.
     */
    @RequestMapping(value = "/aGACOrganizations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AGACOrganization> getAGACOrganization(@PathVariable Long id) {
        log.debug("REST request to get AGACOrganization : {}", id);
        return Optional.ofNullable(aGACOrganizationRepository.findOne(id))
            .map(aGACOrganization -> new ResponseEntity<>(
                aGACOrganization,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /aGACOrganizations/:id -> delete the "id" aGACOrganization.
     */
    @RequestMapping(value = "/aGACOrganizations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAGACOrganization(@PathVariable Long id) {
        log.debug("REST request to delete AGACOrganization : {}", id);
        aGACOrganizationRepository.delete(id);
        aGACOrganizationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("aGACOrganization", id.toString())).build();
    }

    /**
     * SEARCH  /_search/aGACOrganizations/:query -> search for the aGACOrganization corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/aGACOrganizations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AGACOrganization> searchAGACOrganizations(@PathVariable String query) {
        return StreamSupport
            .stream(aGACOrganizationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
