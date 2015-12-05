package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.GlobalParameter;
import com.caceis.olis.paramsandscripts.repository.GlobalParameterRepository;
import com.caceis.olis.paramsandscripts.repository.search.GlobalParameterSearchRepository;
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
 * REST controller for managing GlobalParameter.
 */
@RestController
@RequestMapping("/api")
public class GlobalParameterResource {

    private final Logger log = LoggerFactory.getLogger(GlobalParameterResource.class);

    @Inject
    private GlobalParameterRepository globalParameterRepository;

    @Inject
    private GlobalParameterSearchRepository globalParameterSearchRepository;

    /**
     * POST  /globalParameters -> Create a new globalParameter.
     */
    @RequestMapping(value = "/globalParameters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GlobalParameter> createGlobalParameter(@Valid @RequestBody GlobalParameter globalParameter) throws URISyntaxException {
        log.debug("REST request to save GlobalParameter : {}", globalParameter);
        if (globalParameter.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new globalParameter cannot already have an ID").body(null);
        }
        GlobalParameter result = globalParameterRepository.save(globalParameter);
        globalParameterSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/globalParameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("globalParameter", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /globalParameters -> Updates an existing globalParameter.
     */
    @RequestMapping(value = "/globalParameters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GlobalParameter> updateGlobalParameter(@Valid @RequestBody GlobalParameter globalParameter) throws URISyntaxException {
        log.debug("REST request to update GlobalParameter : {}", globalParameter);
        if (globalParameter.getId() == null) {
            return createGlobalParameter(globalParameter);
        }
        GlobalParameter result = globalParameterRepository.save(globalParameter);
        globalParameterSearchRepository.save(globalParameter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("globalParameter", globalParameter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /globalParameters -> get all the globalParameters.
     */
    @RequestMapping(value = "/globalParameters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GlobalParameter> getAllGlobalParameters() {
        log.debug("REST request to get all GlobalParameters");
        return globalParameterRepository.findAll();
    }

    /**
     * GET  /globalParameters/:id -> get the "id" globalParameter.
     */
    @RequestMapping(value = "/globalParameters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GlobalParameter> getGlobalParameter(@PathVariable String id) {
        log.debug("REST request to get GlobalParameter : {}", id);
        return Optional.ofNullable(globalParameterRepository.findOne(id))
            .map(globalParameter -> new ResponseEntity<>(
                globalParameter,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /globalParameters/:id -> delete the "id" globalParameter.
     */
    @RequestMapping(value = "/globalParameters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGlobalParameter(@PathVariable String id) {
        log.debug("REST request to delete GlobalParameter : {}", id);
        globalParameterRepository.delete(id);
        //globalParameterSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("globalParameter", id.toString())).build();
    }

    /**
     * SEARCH  /_search/globalParameters/:query -> search for the globalParameter corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/globalParameters/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GlobalParameter> searchGlobalParameters(@PathVariable String query) {
        return StreamSupport
            .stream(globalParameterSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
