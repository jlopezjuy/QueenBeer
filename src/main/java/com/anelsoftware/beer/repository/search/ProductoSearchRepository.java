package com.anelsoftware.beer.repository.search;

import com.anelsoftware.beer.domain.Producto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Producto entity.
 */
public interface ProductoSearchRepository extends ElasticsearchRepository<Producto, Long> {
}
