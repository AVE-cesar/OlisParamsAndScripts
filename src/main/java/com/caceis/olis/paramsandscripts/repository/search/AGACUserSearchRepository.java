package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.AGACUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AGACUser entity.
 */
public interface AGACUserSearchRepository extends ElasticsearchRepository<AGACUser, Long> {
}
