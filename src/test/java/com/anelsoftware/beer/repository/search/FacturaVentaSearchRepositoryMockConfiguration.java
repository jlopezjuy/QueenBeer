package com.anelsoftware.beer.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of FacturaVentaSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class FacturaVentaSearchRepositoryMockConfiguration {

    @MockBean
    private FacturaVentaSearchRepository mockFacturaVentaSearchRepository;

}
