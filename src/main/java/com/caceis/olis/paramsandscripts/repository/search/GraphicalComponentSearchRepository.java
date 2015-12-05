package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.GraphicalComponent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GraphicalComponent entity.
 */
public interface GraphicalComponentSearchRepository extends ElasticsearchRepository<GraphicalComponent, Long> {
}
