package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.AuthorizationSet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AuthorizationSet entity.
 */
public interface AuthorizationSetSearchRepository extends ElasticsearchRepository<AuthorizationSet, Long> {
}
