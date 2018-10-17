package com.anelsoftware.beer.repository.search;

import com.anelsoftware.beer.domain.Proveedor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Proveedor entity.
 */
public interface ProveedorSearchRepository extends ElasticsearchRepository<Proveedor, Long> {
}
