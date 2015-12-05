package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.AGACAuthorization;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AGACAuthorization entity.
 */
public interface AGACAuthorizationSearchRepository extends ElasticsearchRepository<AGACAuthorization, Long> {
}
