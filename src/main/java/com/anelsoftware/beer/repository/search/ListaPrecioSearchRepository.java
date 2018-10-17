package com.anelsoftware.beer.repository.search;

import com.anelsoftware.beer.domain.ListaPrecio;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ListaPrecio entity.
 */
public interface ListaPrecioSearchRepository extends ElasticsearchRepository<ListaPrecio, Long> {
}
