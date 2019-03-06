package com.anelsoftware.beer.repository;

import com.anelsoftware.beer.domain.ProductoEnvase;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductoEnvase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoEnvaseRepository extends JpaRepository<ProductoEnvase, Long> {

}
