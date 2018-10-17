package com.anelsoftware.beer.repository.search;

import com.anelsoftware.beer.domain.Insumo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Insumo entity.
 */
public interface InsumoSearchRepository extends ElasticsearchRepository<Insumo, Long> {
}
