package com.anelsoftware.beer.repository;

import com.anelsoftware.beer.domain.Insumo;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Insumo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {

    /**
     *
     * @return
     */
    List<Insumo> findAllByOrderByNombre();
}
