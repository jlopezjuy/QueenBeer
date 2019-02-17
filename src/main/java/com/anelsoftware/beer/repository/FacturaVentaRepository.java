package com.anelsoftware.beer.repository;

import com.anelsoftware.beer.domain.FacturaVenta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FacturaVenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacturaVentaRepository extends JpaRepository<FacturaVenta, Long> {

}
