package com.anelsoftware.beer.service.impl;

import com.anelsoftware.beer.service.ProductoService;
import com.anelsoftware.beer.domain.Producto;
import com.anelsoftware.beer.repository.ProductoRepository;
import com.anelsoftware.beer.repository.search.ProductoSearchRepository;
import com.anelsoftware.beer.service.dto.ProductoDTO;
import com.anelsoftware.beer.service.mapper.ProductoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Producto.
 */
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private ProductoRepository productoRepository;

    private ProductoMapper productoMapper;

    private ProductoSearchRepository productoSearchRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, ProductoMapper productoMapper, ProductoSearchRepository productoSearchRepository) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
        this.productoSearchRepository = productoSearchRepository;
    }

    /**
     * Save a producto.
     *
     * @param productoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductoDTO save(ProductoDTO productoDTO) {
        log.debug("Request to save Producto : {}", productoDTO);

        Producto producto = productoMapper.toEntity(productoDTO);
        producto = productoRepository.save(producto);
        ProductoDTO result = productoMapper.toDto(producto);
        productoSearchRepository.save(producto);
        return result;
    }

    /**
     * Get all the productos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findAll() {
        log.debug("Request to get all Productos");
        return productoRepository.findAll().stream()
            .map(productoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one producto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoDTO> findOne(Long id) {
        log.debug("Request to get Producto : {}", id);
        return productoRepository.findById(id)
            .map(productoMapper::toDto);
    }

    /**
     * Delete the producto by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Producto : {}", id);
        productoRepository.deleteById(id);
        productoSearchRepository.deleteById(id);
    }

    /**
     * Search for the producto corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> search(String query) {
        log.debug("Request to search Productos for query {}", query);
        return StreamSupport
            .stream(productoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productoMapper::toDto)
            .collect(Collectors.toList());
    }
}
