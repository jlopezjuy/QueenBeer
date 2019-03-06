package com.anelsoftware.beer.repository.search;

import com.anelsoftware.beer.domain.FacturaVenta;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FacturaVenta entity.
 */
public interface FacturaVentaSearchRepository extends ElasticsearchRepository<FacturaVenta, Long> {
}
