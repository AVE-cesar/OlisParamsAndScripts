package com.caceis.olis.paramsandscripts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caceis.olis.paramsandscripts.domain.Prompt;
import com.caceis.olis.paramsandscripts.repository.PromptRepository;
import com.caceis.olis.paramsandscripts.repository.search.PromptSearchRepository;
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
 * REST controller for managing Prompt.
 */
@RestController
@RequestMapping("/api")
public class PromptResource {

    private final Logger log = LoggerFactory.getLogger(PromptResource.class);

    @Inject
    private PromptRepository promptRepository;

    @Inject
    private PromptSearchRepository promptSearchRepository;

    /**
     * POST  /prompts -> Create a new prompt.
     */
    @RequestMapping(value = "/prompts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Prompt> createPrompt(@Valid @RequestBody Prompt prompt) throws URISyntaxException {
        log.debug("REST request to save Prompt : {}", prompt);
        if (prompt.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new prompt cannot already have an ID").body(null);
        }
        Prompt result = promptRepository.save(prompt);
        promptSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prompts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prompt", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prompts -> Updates an existing prompt.
     */
    @RequestMapping(value = "/prompts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Prompt> updatePrompt(@Valid @RequestBody Prompt prompt) throws URISyntaxException {
        log.debug("REST request to update Prompt : {}", prompt);
        if (prompt.getId() == null) {
            return createPrompt(prompt);
        }
        Prompt result = promptRepository.save(prompt);
        promptSearchRepository.save(prompt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prompt", prompt.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prompts -> get all the prompts.
     */
    @RequestMapping(value = "/prompts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Prompt> getAllPrompts() {
        log.debug("REST request to get all Prompts");
        return promptRepository.findAll();
    }
    
    /**
     * B. AVE.
     */
    @RequestMapping(value = "/reIndexAllPrompts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void reIndexAllPrompts() {
        log.debug("REST request to re-index all Prompts");
        /*
        List<Prompt> allPrompts = promptRepository.findAll(); 
        for (int i = 0; i < allPrompts.size(); i++) {
        	promptSearchRepository.index(allPrompts.get(i));
		}
		*/
        promptRepository.findAll().forEach(p -> promptSearchRepository.index(p));
    }

    /**
     * GET  /prompts/:id -> get the "id" prompt.
     */
    @RequestMapping(value = "/prompts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Prompt> getPrompt(@PathVariable Long id) {
        log.debug("REST request to get Prompt : {}", id);
        return Optional.ofNullable(promptRepository.findOne(id))
            .map(prompt -> new ResponseEntity<>(
                prompt,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prompts/:id -> delete the "id" prompt.
     */
    @RequestMapping(value = "/prompts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrompt(@PathVariable Long id) {
        log.debug("REST request to delete Prompt : {}", id);
        promptRepository.delete(id);
        promptSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prompt", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prompts/:query -> search for the prompt corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prompts/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Prompt> searchPrompts(@PathVariable String query) {
        return StreamSupport
            .stream(promptSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
