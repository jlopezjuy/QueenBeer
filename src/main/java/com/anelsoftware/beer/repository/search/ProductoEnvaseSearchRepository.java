package com.anelsoftware.beer.repository.search;

import com.anelsoftware.beer.domain.ProductoEnvase;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductoEnvase entity.
 */
public interface ProductoEnvaseSearchRepository extends ElasticsearchRepository<ProductoEnvase, Long> {
}
