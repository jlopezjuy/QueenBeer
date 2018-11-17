package com.anelsoftware.beer.repository;

import com.anelsoftware.beer.domain.Compra;
import com.anelsoftware.beer.domain.CompraInsumo;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompraInsumo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompraInsumoRepository extends JpaRepository<CompraInsumo, Long> {

    @Query(value = "select c from CompraInsumo c where c.compra.id = :compraId")
    List<CompraInsumo> getAllComprasInsumosByCompraId(@Param(value = "compraId") Long compraId);
}
