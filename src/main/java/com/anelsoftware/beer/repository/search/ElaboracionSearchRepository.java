package com.anelsoftware.beer.repository.search;

import com.anelsoftware.beer.domain.Elaboracion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Elaboracion entity.
 */
public interface ElaboracionSearchRepository extends ElasticsearchRepository<Elaboracion, Long> {
}
