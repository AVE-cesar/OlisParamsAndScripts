package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.ParamCategory;
import com.caceis.olis.paramsandscripts.repository.ParamCategoryRepository;
import com.caceis.olis.paramsandscripts.repository.search.ParamCategorySearchRepository;
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
 * REST controller for managing ParamCategory.
 */
@RestController
@RequestMapping("/api")
public class ParamCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ParamCategoryResource.class);

    @Inject
    private ParamCategoryRepository paramCategoryRepository;

    @Inject
    private ParamCategorySearchRepository paramCategorySearchRepository;

    /**
     * POST  /paramCategorys -> Create a new paramCategory.
     */
    @RequestMapping(value = "/paramCategorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ParamCategory> createParamCategory(@Valid @RequestBody ParamCategory paramCategory) throws URISyntaxException {
        log.debug("REST request to save ParamCategory : {}", paramCategory);
        if (paramCategory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new paramCategory cannot already have an ID").body(null);
        }
        ParamCategory result = paramCategoryRepository.save(paramCategory);
        paramCategorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/paramCategorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("paramCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /paramCategorys -> Updates an existing paramCategory.
     */
    @RequestMapping(value = "/paramCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ParamCategory> updateParamCategory(@Valid @RequestBody ParamCategory paramCategory) throws URISyntaxException {
        log.debug("REST request to update ParamCategory : {}", paramCategory);
        if (paramCategory.getId() == null) {
            return createParamCategory(paramCategory);
        }
        ParamCategory result = paramCategoryRepository.save(paramCategory);
        paramCategorySearchRepository.save(paramCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("paramCategory", paramCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /paramCategorys -> get all the paramCategorys.
     */
    @RequestMapping(value = "/paramCategorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ParamCategory> getAllParamCategorys() {
        log.debug("REST request to get all ParamCategorys");
        return paramCategoryRepository.findAll();
    }

    /**
     * GET  /paramCategorys/:id -> get the "id" paramCategory.
     */
    @RequestMapping(value = "/paramCategorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ParamCategory> getParamCategory(@PathVariable String id) {
        log.debug("REST request to get ParamCategory : {}", id);
        return Optional.ofNullable(paramCategoryRepository.findOne(id))
            .map(paramCategory -> new ResponseEntity<>(
                paramCategory,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /paramCategorys/:id -> delete the "id" paramCategory.
     */
    @RequestMapping(value = "/paramCategorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteParamCategory(@PathVariable String id) {
        log.debug("REST request to delete ParamCategory : {}", id);
        paramCategoryRepository.delete(id);
        //paramCategorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("paramCategory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/paramCategorys/:query -> search for the paramCategory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/paramCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ParamCategory> searchParamCategorys(@PathVariable String query) {
        return StreamSupport
            .stream(paramCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
