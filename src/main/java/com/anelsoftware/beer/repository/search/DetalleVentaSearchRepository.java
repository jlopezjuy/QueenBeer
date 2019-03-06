package com.anelsoftware.beer.repository.search;

import com.anelsoftware.beer.domain.DetalleVenta;
import com.anelsoftware.beer.domain.FacturaVenta;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DetalleVenta entity.
 */
public interface DetalleVentaSearchRepository extends ElasticsearchRepository<DetalleVenta, Long> {

    List<DetalleVenta> findAllByFacturaVenta(FacturaVenta facturaVenta);
}
