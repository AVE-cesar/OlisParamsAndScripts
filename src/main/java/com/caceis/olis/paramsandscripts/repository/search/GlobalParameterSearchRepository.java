package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.GlobalParameter;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GlobalParameter entity.
 */
public interface GlobalParameterSearchRepository extends ElasticsearchRepository<GlobalParameter, Long> {
}
