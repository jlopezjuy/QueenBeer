package com.anelsoftware.beer.service.impl;

import com.anelsoftware.beer.service.CompraInsumoService;
import com.anelsoftware.beer.domain.CompraInsumo;
import com.anelsoftware.beer.repository.CompraInsumoRepository;
import com.anelsoftware.beer.repository.search.CompraInsumoSearchRepository;
import com.anelsoftware.beer.service.dto.CompraInsumoDTO;
import com.anelsoftware.beer.service.mapper.CompraInsumoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CompraInsumo.
 */
@Service
@Transactional
public class CompraInsumoServiceImpl implements CompraInsumoService {

    private final Logger log = LoggerFactory.getLogger(CompraInsumoServiceImpl.class);

    private CompraInsumoRepository compraInsumoRepository;

    private CompraInsumoMapper compraInsumoMapper;

    private CompraInsumoSearchRepository compraInsumoSearchRepository;

    public CompraInsumoServiceImpl(CompraInsumoRepository compraInsumoRepository, CompraInsumoMapper compraInsumoMapper, CompraInsumoSearchRepository compraInsumoSearchRepository) {
        this.compraInsumoRepository = compraInsumoRepository;
        this.compraInsumoMapper = compraInsumoMapper;
        this.compraInsumoSearchRepository = compraInsumoSearchRepository;
    }

    /**
     * Save a compraInsumo.
     *
     * @param compraInsumoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompraInsumoDTO save(CompraInsumoDTO compraInsumoDTO) {
        log.debug("Request to save CompraInsumo : {}", compraInsumoDTO);

        CompraInsumo compraInsumo = compraInsumoMapper.toEntity(compraInsumoDTO);
        compraInsumo = compraInsumoRepository.save(compraInsumo);
        CompraInsumoDTO result = compraInsumoMapper.toDto(compraInsumo);
        compraInsumoSearchRepository.save(compraInsumo);
        return result;
    }

    /**
     * Get all the compraInsumos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompraInsumoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompraInsumos");
        return compraInsumoRepository.findAll(pageable)
            .map(compraInsumoMapper::toDto);
    }


    /**
     * Get one compraInsumo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompraInsumoDTO> findOne(Long id) {
        log.debug("Request to get CompraInsumo : {}", id);
        return compraInsumoRepository.findById(id)
            .map(compraInsumoMapper::toDto);
    }

    /**
     * Delete the compraInsumo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompraInsumo : {}", id);
        compraInsumoRepository.deleteById(id);
        compraInsumoSearchRepository.deleteById(id);
    }

    /**
     * Search for the compraInsumo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompraInsumoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CompraInsumos for query {}", query);
        return compraInsumoSearchRepository.search(queryStringQuery(query), pageable)
            .map(compraInsumoMapper::toDto);
    }
}
