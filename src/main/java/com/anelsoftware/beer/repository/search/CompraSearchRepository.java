package com.anelsoftware.beer.repository.search;

import com.anelsoftware.beer.domain.Compra;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Compra entity.
 */
public interface CompraSearchRepository extends ElasticsearchRepository<Compra, Long> {
}
