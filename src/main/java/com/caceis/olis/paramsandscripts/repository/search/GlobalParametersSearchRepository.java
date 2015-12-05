package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.GlobalParameters;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GlobalParameters entity.
 */
public interface GlobalParametersSearchRepository extends ElasticsearchRepository<GlobalParameters, Long> {
}
