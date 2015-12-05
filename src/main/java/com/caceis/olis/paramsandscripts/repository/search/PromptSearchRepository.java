package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.Prompt;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Prompt entity.
 */
public interface PromptSearchRepository extends ElasticsearchRepository<Prompt, Long> {
}
