package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.DatasourcePosition;
import com.caceis.olis.paramsandscripts.domain.ParameterDependency;
import com.caceis.olis.paramsandscripts.repository.ParameterDependencyRepository;
import com.caceis.olis.paramsandscripts.repository.search.ParameterDependencySearchRepository;
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
 * REST controller for managing ParameterDependency.
 */
@RestController
@RequestMapping("/api")
public class ParameterDependencyResource {

    private final Logger log = LoggerFactory.getLogger(ParameterDependencyResource.class);

    @Inject
    private ParameterDependencyRepository parameterDependencyRepository;

    @Inject
    private ParameterDependencySearchRepository parameterDependencySearchRepository;

    /**
     * POST  /parameterDependencys -> Create a new parameterDependency.
     */
    @RequestMapping(value = "/parameterDependencys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ParameterDependency> createParameterDependency(@Valid @RequestBody ParameterDependency parameterDependency) throws URISyntaxException {
        log.debug("REST request to save ParameterDependency : {}", parameterDependency);
        if (parameterDependency.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new parameterDependency cannot already have an ID").body(null);
        }
        ParameterDependency result = parameterDependencyRepository.save(parameterDependency);
        parameterDependencySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/parameterDependencys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("parameterDependency", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parameterDependencys -> Updates an existing parameterDependency.
     */
    @RequestMapping(value = "/parameterDependencys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ParameterDependency> updateParameterDependency(@Valid @RequestBody ParameterDependency parameterDependency) throws URISyntaxException {
        log.debug("REST request to update ParameterDependency : {}", parameterDependency);
        if (parameterDependency.getId() == null) {
            return createParameterDependency(parameterDependency);
        }
        ParameterDependency result = parameterDependencyRepository.save(parameterDependency);
        parameterDependencySearchRepository.save(parameterDependency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("parameterDependency", parameterDependency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parameterDependencys -> get all the parameterDependencys.
     */
    @RequestMapping(value = "/parameterDependencys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ParameterDependency> getAllParameterDependencys() {
        log.debug("REST request to get all ParameterDependencys");
        return parameterDependencyRepository.findAll();
    }
    
    /**
     * B. AVE.
     */
    @RequestMapping(value = "/reIndexAllParameterDependencys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void reIndexAllParameterDependencys() {
        log.debug("REST request to re-index all ParameterDependencys");
        parameterDependencyRepository.findAll().forEach(p -> parameterDependencySearchRepository.index(p));
    }

    /**
     * B. AVE
     */
    @RequestMapping(value = "/parameterDependencysByPromptId/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ParameterDependency> getAllParameterDependencysByPromptId(@PathVariable Long id) {
        log.debug("REST request to get all ParameterDependencysByPromptId : {}", id);
        return parameterDependencyRepository.findByPromptFatherId(id);
    }
    
    /**
     * GET  /parameterDependencys/:id -> get the "id" parameterDependency.
     */
    @RequestMapping(value = "/parameterDependencys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ParameterDependency> getParameterDependency(@PathVariable Long id) {
        log.debug("REST request to get ParameterDependency : {}", id);
        return Optional.ofNullable(parameterDependencyRepository.findOne(id))
            .map(parameterDependency -> new ResponseEntity<>(
                parameterDependency,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /parameterDependencys/:id -> delete the "id" parameterDependency.
     */
    @RequestMapping(value = "/parameterDependencys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteParameterDependency(@PathVariable Long id) {
        log.debug("REST request to delete ParameterDependency : {}", id);
        parameterDependencyRepository.delete(id);
        parameterDependencySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("parameterDependency", id.toString())).build();
    }

    /**
     * SEARCH  /_search/parameterDependencys/:query -> search for the parameterDependency corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/parameterDependencys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ParameterDependency> searchParameterDependencys(@PathVariable String query) {
        return StreamSupport
            .stream(parameterDependencySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
