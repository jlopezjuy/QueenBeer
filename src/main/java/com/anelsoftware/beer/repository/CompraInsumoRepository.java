package com.anelsoftware.beer.repository;

import com.anelsoftware.beer.domain.CompraInsumo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompraInsumo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompraInsumoRepository extends JpaRepository<CompraInsumo, Long> {

}
