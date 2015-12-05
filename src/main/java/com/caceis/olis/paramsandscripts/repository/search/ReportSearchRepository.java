package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.Report;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Report entity.
 */
public interface ReportSearchRepository extends ElasticsearchRepository<Report, Long> {
}
