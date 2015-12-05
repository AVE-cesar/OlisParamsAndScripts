package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.AGACOrganization;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AGACOrganization entity.
 */
public interface AGACOrganizationSearchRepository extends ElasticsearchRepository<AGACOrganization, Long> {
}
