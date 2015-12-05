package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.GraphicalComponentLink;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GraphicalComponentLink entity.
 */
public interface GraphicalComponentLinkSearchRepository extends ElasticsearchRepository<GraphicalComponentLink, Long> {
}
