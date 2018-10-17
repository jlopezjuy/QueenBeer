package com.anelsoftware.beer.repository;

import com.anelsoftware.beer.domain.ListaPrecio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ListaPrecio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListaPrecioRepository extends JpaRepository<ListaPrecio, Long> {

}
