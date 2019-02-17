package com.anelsoftware.beer.repository.search;

import com.anelsoftware.beer.domain.DetalleVenta;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DetalleVenta entity.
 */
public interface DetalleVentaSearchRepository extends ElasticsearchRepository<DetalleVenta, Long> {
}
