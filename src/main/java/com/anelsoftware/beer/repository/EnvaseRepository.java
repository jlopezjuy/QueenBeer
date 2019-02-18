package com.anelsoftware.beer.repository;

import com.anelsoftware.beer.domain.Envase;
import com.anelsoftware.beer.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Envase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnvaseRepository extends JpaRepository<Envase, Long> {


    List<Envase> findAllByProducto(Producto producto);
}
