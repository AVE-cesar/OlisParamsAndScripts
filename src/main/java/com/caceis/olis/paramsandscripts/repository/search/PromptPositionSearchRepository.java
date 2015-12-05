package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.PromptPosition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PromptPosition entity.
 */
public interface PromptPositionSearchRepository extends ElasticsearchRepository<PromptPosition, Long> {
}
