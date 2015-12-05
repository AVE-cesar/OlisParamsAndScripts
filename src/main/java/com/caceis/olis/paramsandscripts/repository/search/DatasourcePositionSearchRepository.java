package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.DatasourcePosition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DatasourcePosition entity.
 */
public interface DatasourcePositionSearchRepository extends ElasticsearchRepository<DatasourcePosition, Long> {
}
