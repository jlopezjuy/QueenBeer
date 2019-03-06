package com.anelsoftware.beer.service.impl;

import com.anelsoftware.beer.service.ProductoEnvaseService;
import com.anelsoftware.beer.domain.ProductoEnvase;
import com.anelsoftware.beer.repository.ProductoEnvaseRepository;
import com.anelsoftware.beer.repository.search.ProductoEnvaseSearchRepository;
import com.anelsoftware.beer.service.dto.ProductoEnvaseDTO;
import com.anelsoftware.beer.service.mapper.ProductoEnvaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ProductoEnvase.
 */
@Service
@Transactional
public class ProductoEnvaseServiceImpl implements ProductoEnvaseService {

    private final Logger log = LoggerFactory.getLogger(ProductoEnvaseServiceImpl.class);

    private final ProductoEnvaseRepository productoEnvaseRepository;

    private final ProductoEnvaseMapper productoEnvaseMapper;

    private final ProductoEnvaseSearchRepository productoEnvaseSearchRepository;

    public ProductoEnvaseServiceImpl(ProductoEnvaseRepository productoEnvaseRepository, ProductoEnvaseMapper productoEnvaseMapper, ProductoEnvaseSearchRepository productoEnvaseSearchRepository) {
        this.productoEnvaseRepository = productoEnvaseRepository;
        this.productoEnvaseMapper = productoEnvaseMapper;
        this.productoEnvaseSearchRepository = productoEnvaseSearchRepository;
    }

    /**
     * Save a productoEnvase.
     *
     * @param productoEnvaseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductoEnvaseDTO save(ProductoEnvaseDTO productoEnvaseDTO) {
        log.debug("Request to save ProductoEnvase : {}", productoEnvaseDTO);
        ProductoEnvase productoEnvase = productoEnvaseMapper.toEntity(productoEnvaseDTO);
        productoEnvase = productoEnvaseRepository.save(productoEnvase);
        ProductoEnvaseDTO result = productoEnvaseMapper.toDto(productoEnvase);
        productoEnvaseSearchRepository.save(productoEnvase);
        return result;
    }

    /**
     * Get all the productoEnvases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoEnvaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductoEnvases");
        return productoEnvaseRepository.findAll(pageable)
            .map(productoEnvaseMapper::toDto);
    }


    /**
     * Get one productoEnvase by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoEnvaseDTO> findOne(Long id) {
        log.debug("Request to get ProductoEnvase : {}", id);
        return productoEnvaseRepository.findById(id)
            .map(productoEnvaseMapper::toDto);
    }

    /**
     * Delete the productoEnvase by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductoEnvase : {}", id);        productoEnvaseRepository.deleteById(id);
        productoEnvaseSearchRepository.deleteById(id);
    }

    /**
     * Search for the productoEnvase corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoEnvaseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProductoEnvases for query {}", query);
        return productoEnvaseSearchRepository.search(queryStringQuery(query), pageable)
            .map(productoEnvaseMapper::toDto);
    }
}
