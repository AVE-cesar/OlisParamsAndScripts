package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.ParameterDependency;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ParameterDependency entity.
 */
public interface ParameterDependencySearchRepository extends ElasticsearchRepository<ParameterDependency, Long> {
}
