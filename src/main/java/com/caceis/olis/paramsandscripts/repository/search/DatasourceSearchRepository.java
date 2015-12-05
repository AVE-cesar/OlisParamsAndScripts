package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.Datasource;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Datasource entity.
 */
public interface DatasourceSearchRepository extends ElasticsearchRepository<Datasource, Long> {
}
