package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.AuthorizationSetLink;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AuthorizationSetLink entity.
 */
public interface AuthorizationSetLinkSearchRepository extends ElasticsearchRepository<AuthorizationSetLink, Long> {
}
