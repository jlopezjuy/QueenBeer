package com.anelsoftware.beer.repository.search;

import com.anelsoftware.beer.domain.ElaboracionInsumo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ElaboracionInsumo entity.
 */
public interface ElaboracionInsumoSearchRepository extends ElasticsearchRepository<ElaboracionInsumo, Long> {
}
