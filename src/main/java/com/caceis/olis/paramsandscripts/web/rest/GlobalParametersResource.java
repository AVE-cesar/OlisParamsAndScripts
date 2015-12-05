package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.GlobalParameters;
import com.caceis.olis.paramsandscripts.repository.GlobalParametersRepository;
import com.caceis.olis.paramsandscripts.repository.search.GlobalParametersSearchRepository;
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
 * REST controller for managing GlobalParameters.
 */
@RestController
@RequestMapping("/api")
public class GlobalParametersResource {

    private final Logger log = LoggerFactory.getLogger(GlobalParametersResource.class);

    @Inject
    private GlobalParametersRepository globalParametersRepository;

    @Inject
    private GlobalParametersSearchRepository globalParametersSearchRepository;

    /**
     * POST  /globalParameterss -> Create a new globalParameters.
     */
    @RequestMapping(value = "/globalParameterss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GlobalParameters> createGlobalParameters(@Valid @RequestBody GlobalParameters globalParameters) throws URISyntaxException {
        log.debug("REST request to save GlobalParameters : {}", globalParameters);
        if (globalParameters.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new globalParameters cannot already have an ID").body(null);
        }
        GlobalParameters result = globalParametersRepository.save(globalParameters);
        globalParametersSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/globalParameterss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("globalParameters", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /globalParameterss -> Updates an existing globalParameters.
     */
    @RequestMapping(value = "/globalParameterss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GlobalParameters> updateGlobalParameters(@Valid @RequestBody GlobalParameters globalParameters) throws URISyntaxException {
        log.debug("REST request to update GlobalParameters : {}", globalParameters);
        if (globalParameters.getId() == null) {
            return createGlobalParameters(globalParameters);
        }
        GlobalParameters result = globalParametersRepository.save(globalParameters);
        globalParametersSearchRepository.save(globalParameters);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("globalParameters", globalParameters.getId().toString()))
            .body(result);
    }

    /**
     * GET  /globalParameterss -> get all the globalParameterss.
     */
    @RequestMapping(value = "/globalParameterss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GlobalParameters> getAllGlobalParameterss() {
        log.debug("REST request to get all GlobalParameterss");
        return globalParametersRepository.findAll();
    }

    /**
     * GET  /globalParameterss/:id -> get the "id" globalParameters.
     */
    @RequestMapping(value = "/globalParameterss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GlobalParameters> getGlobalParameters(@PathVariable Long id) {
        log.debug("REST request to get GlobalParameters : {}", id);
        return Optional.ofNullable(globalParametersRepository.findOne(id))
            .map(globalParameters -> new ResponseEntity<>(
                globalParameters,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /globalParameterss/:id -> delete the "id" globalParameters.
     */
    @RequestMapping(value = "/globalParameterss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGlobalParameters(@PathVariable Long id) {
        log.debug("REST request to delete GlobalParameters : {}", id);
        globalParametersRepository.delete(id);
        globalParametersSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("globalParameters", id.toString())).build();
    }

    /**
     * SEARCH  /_search/globalParameterss/:query -> search for the globalParameters corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/globalParameterss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GlobalParameters> searchGlobalParameterss(@PathVariable String query) {
        return StreamSupport
            .stream(globalParametersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
