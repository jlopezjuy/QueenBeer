package com.anelsoftware.beer.repository;

import com.anelsoftware.beer.domain.Insumo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Insumo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {

}
