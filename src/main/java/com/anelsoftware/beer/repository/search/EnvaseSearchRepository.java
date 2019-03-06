package com.anelsoftware.beer.repository.search;

import com.anelsoftware.beer.domain.Envase;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Envase entity.
 */
public interface EnvaseSearchRepository extends ElasticsearchRepository<Envase, Long> {
}
