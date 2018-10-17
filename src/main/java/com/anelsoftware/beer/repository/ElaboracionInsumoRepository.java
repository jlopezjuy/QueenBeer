package com.anelsoftware.beer.repository;

import com.anelsoftware.beer.domain.ElaboracionInsumo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ElaboracionInsumo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElaboracionInsumoRepository extends JpaRepository<ElaboracionInsumo, Long> {

}
