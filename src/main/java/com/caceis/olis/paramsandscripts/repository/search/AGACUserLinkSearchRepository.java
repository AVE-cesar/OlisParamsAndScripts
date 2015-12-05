package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.AGACUserLink;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AGACUserLink entity.
 */
public interface AGACUserLinkSearchRepository extends ElasticsearchRepository<AGACUserLink, Long> {
}
