package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.AuthorizationLink;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AuthorizationLink entity.
 */
public interface AuthorizationLinkSearchRepository extends ElasticsearchRepository<AuthorizationLink, Long> {
}
