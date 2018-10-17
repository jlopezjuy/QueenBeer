package com.anelsoftware.beer.service;

import com.anelsoftware.beer.service.dto.ElaboracionInsumoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ElaboracionInsumo.
 */
public interface ElaboracionInsumoService {

    /**
     * Save a elaboracionInsumo.
     *
     * @param elaboracionInsumoDTO the entity to save
     * @return the persisted entity
     */
    ElaboracionInsumoDTO save(ElaboracionInsumoDTO elaboracionInsumoDTO);

    /**
     * Get all the elaboracionInsumos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ElaboracionInsumoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" elaboracionInsumo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ElaboracionInsumoDTO> findOne(Long id);

    /**
     * Delete the "id" elaboracionInsumo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the elaboracionInsumo corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ElaboracionInsumoDTO> search(String query, Pageable pageable);
}
