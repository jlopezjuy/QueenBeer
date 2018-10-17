package com.anelsoftware.beer.repository;

import com.anelsoftware.beer.domain.Elaboracion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Elaboracion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElaboracionRepository extends JpaRepository<Elaboracion, Long> {

}
