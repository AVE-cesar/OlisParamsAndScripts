package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.CheckScript;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CheckScript entity.
 */
public interface CheckScriptSearchRepository extends ElasticsearchRepository<CheckScript, Long> {
}
