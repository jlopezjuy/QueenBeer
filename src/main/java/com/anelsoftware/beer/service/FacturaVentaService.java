package com.anelsoftware.beer.service;

import com.anelsoftware.beer.service.dto.FacturaVentaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing FacturaVenta.
 */
public interface FacturaVentaService {

    /**
     * Save a facturaVenta.
     *
     * @param facturaVentaDTO the entity to save
     * @return the persisted entity
     */
    FacturaVentaDTO save(FacturaVentaDTO facturaVentaDTO);

    /**
     * Get all the facturaVentas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FacturaVentaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" facturaVenta.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FacturaVentaDTO> findOne(Long id);

    /**
     * Delete the "id" facturaVenta.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the facturaVenta corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FacturaVentaDTO> search(String query, Pageable pageable);
}
