package com.anelsoftware.beer.repository.search;

import com.anelsoftware.beer.domain.CompraInsumo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CompraInsumo entity.
 */
public interface CompraInsumoSearchRepository extends ElasticsearchRepository<CompraInsumo, Long> {
}
