package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.GraphicalComponentParam;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GraphicalComponentParam entity.
 */
public interface GraphicalComponentParamSearchRepository extends ElasticsearchRepository<GraphicalComponentParam, Long> {
}
