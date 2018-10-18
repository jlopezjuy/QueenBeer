package com.anelsoftware.beer.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CompraInsumoSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CompraInsumoSearchRepositoryMockConfiguration {

    @MockBean
    private CompraInsumoSearchRepository mockCompraInsumoSearchRepository;

}
