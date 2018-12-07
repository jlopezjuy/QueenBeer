package com.anelsoftware.beer.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of EnvaseSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class EnvaseSearchRepositoryMockConfiguration {

    @MockBean
    private EnvaseSearchRepository mockEnvaseSearchRepository;

}
