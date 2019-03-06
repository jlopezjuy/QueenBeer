package com.anelsoftware.beer.service.impl;

import com.anelsoftware.beer.service.FacturaVentaService;
import com.anelsoftware.beer.domain.FacturaVenta;
import com.anelsoftware.beer.repository.FacturaVentaRepository;
import com.anelsoftware.beer.repository.ClienteRepository;
import com.anelsoftware.beer.repository.search.FacturaVentaSearchRepository;
import com.anelsoftware.beer.service.dto.FacturaVentaDTO;
import com.anelsoftware.beer.service.mapper.FacturaVentaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FacturaVenta.
 */
@Service
@Transactional
public class FacturaVentaServiceImpl implements FacturaVentaService {

    private final Logger log = LoggerFactory.getLogger(FacturaVentaServiceImpl.class);

    private final FacturaVentaRepository facturaVentaRepository;

    private final FacturaVentaMapper facturaVentaMapper;

    private final FacturaVentaSearchRepository facturaVentaSearchRepository;

    private final ClienteRepository clienteRepository;

    public FacturaVentaServiceImpl(FacturaVentaRepository facturaVentaRepository, FacturaVentaMapper facturaVentaMapper, FacturaVentaSearchRepository facturaVentaSearchRepository, ClienteRepository clienteRepository) {
        this.facturaVentaRepository = facturaVentaRepository;
        this.facturaVentaMapper = facturaVentaMapper;
        this.facturaVentaSearchRepository = facturaVentaSearchRepository;
        this.clienteRepository = clienteRepository;
    }

    /**
     * Save a facturaVenta.
     *
     * @param facturaVentaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FacturaVentaDTO save(FacturaVentaDTO facturaVentaDTO) {
        log.debug("Request to save FacturaVenta : {}", facturaVentaDTO);
        FacturaVenta facturaVenta = facturaVentaMapper.toEntity(facturaVentaDTO);
        long clienteId = facturaVentaDTO.getClienteId();
        clienteRepository.findById(clienteId).ifPresent(facturaVenta::cliente);
        facturaVenta = facturaVentaRepository.save(facturaVenta);
        FacturaVentaDTO result = facturaVentaMapper.toDto(facturaVenta);
        facturaVentaSearchRepository.save(facturaVenta);
        return result;
    }

    /**
     * Get all the facturaVentas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FacturaVentaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FacturaVentas");
        return facturaVentaRepository.findAll(pageable)
            .map(facturaVentaMapper::toDto);
    }


    /**
     * Get one facturaVenta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FacturaVentaDTO> findOne(Long id) {
        log.debug("Request to get FacturaVenta : {}", id);
        return facturaVentaRepository.findById(id)
            .map(facturaVentaMapper::toDto);
    }

    /**
     * Delete the facturaVenta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FacturaVenta : {}", id);        facturaVentaRepository.deleteById(id);
        facturaVentaSearchRepository.deleteById(id);
    }

    /**
     * Search for the facturaVenta corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FacturaVentaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FacturaVentas for query {}", query);
        return facturaVentaSearchRepository.search(queryStringQuery(query), pageable)
            .map(facturaVentaMapper::toDto);
    }
}
