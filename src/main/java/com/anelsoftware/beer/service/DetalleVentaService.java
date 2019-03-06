package com.anelsoftware.beer.service;

import com.anelsoftware.beer.service.dto.DetalleVentaDTO;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DetalleVenta.
 */
public interface DetalleVentaService {

    /**
     * Save a detalleVenta.
     *
     * @param detalleVentaDTO the entity to save
     * @return the persisted entity
     */
    DetalleVentaDTO save(DetalleVentaDTO detalleVentaDTO);

    /**
     * Get all the detalleVentas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DetalleVentaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" detalleVenta.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DetalleVentaDTO> findOne(Long id);

    /**
     * Delete the "id" detalleVenta.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the detalleVenta corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DetalleVentaDTO> search(String query, Pageable pageable);

    /**
     *
     * @param facturaId
     * @return
     */
    List<DetalleVentaDTO> findAllByFactura(Long facturaId);
}
