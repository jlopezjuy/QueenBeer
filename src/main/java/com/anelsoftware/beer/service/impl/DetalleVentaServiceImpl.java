package com.anelsoftware.beer.service.impl;

import com.anelsoftware.beer.service.DetalleVentaService;
import com.anelsoftware.beer.domain.DetalleVenta;
import com.anelsoftware.beer.repository.DetalleVentaRepository;
import com.anelsoftware.beer.repository.search.DetalleVentaSearchRepository;
import com.anelsoftware.beer.service.dto.DetalleVentaDTO;
import com.anelsoftware.beer.service.mapper.DetalleVentaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DetalleVenta.
 */
@Service
@Transactional
public class DetalleVentaServiceImpl implements DetalleVentaService {

    private final Logger log = LoggerFactory.getLogger(DetalleVentaServiceImpl.class);

    private final DetalleVentaRepository detalleVentaRepository;

    private final DetalleVentaMapper detalleVentaMapper;

    private final DetalleVentaSearchRepository detalleVentaSearchRepository;

    public DetalleVentaServiceImpl(DetalleVentaRepository detalleVentaRepository, DetalleVentaMapper detalleVentaMapper, DetalleVentaSearchRepository detalleVentaSearchRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
        this.detalleVentaMapper = detalleVentaMapper;
        this.detalleVentaSearchRepository = detalleVentaSearchRepository;
    }

    /**
     * Save a detalleVenta.
     *
     * @param detalleVentaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DetalleVentaDTO save(DetalleVentaDTO detalleVentaDTO) {
        log.debug("Request to save DetalleVenta : {}", detalleVentaDTO);
        DetalleVenta detalleVenta = detalleVentaMapper.toEntity(detalleVentaDTO);
        detalleVenta = detalleVentaRepository.save(detalleVenta);
        DetalleVentaDTO result = detalleVentaMapper.toDto(detalleVenta);
        detalleVentaSearchRepository.save(detalleVenta);
        return result;
    }

    /**
     * Get all the detalleVentas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetalleVentaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetalleVentas");
        return detalleVentaRepository.findAll(pageable)
            .map(detalleVentaMapper::toDto);
    }


    /**
     * Get one detalleVenta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleVentaDTO> findOne(Long id) {
        log.debug("Request to get DetalleVenta : {}", id);
        return detalleVentaRepository.findById(id)
            .map(detalleVentaMapper::toDto);
    }

    /**
     * Delete the detalleVenta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetalleVenta : {}", id);        detalleVentaRepository.deleteById(id);
        detalleVentaSearchRepository.deleteById(id);
    }

    /**
     * Search for the detalleVenta corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetalleVentaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DetalleVentas for query {}", query);
        return detalleVentaSearchRepository.search(queryStringQuery(query), pageable)
            .map(detalleVentaMapper::toDto);
    }
}
