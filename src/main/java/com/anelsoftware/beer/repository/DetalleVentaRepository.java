package com.anelsoftware.beer.repository;

import com.anelsoftware.beer.domain.DetalleVenta;
import com.anelsoftware.beer.domain.FacturaVenta;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DetalleVenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    List<DetalleVenta> findAllByFacturaVenta(FacturaVenta facturaVenta);
}
