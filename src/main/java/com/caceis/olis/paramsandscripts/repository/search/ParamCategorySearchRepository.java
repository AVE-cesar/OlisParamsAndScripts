package com.caceis.olis.paramsandscripts.repository.search;

import com.caceis.olis.paramsandscripts.domain.ParamCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ParamCategory entity.
 */
public interface ParamCategorySearchRepository extends ElasticsearchRepository<ParamCategory, Long> {
}
