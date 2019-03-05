package com.anelsoftware.beer.service.impl;

import com.anelsoftware.beer.domain.DetalleVenta;
import com.anelsoftware.beer.domain.FacturaVenta;
import com.anelsoftware.beer.repository.DetalleVentaRepository;
import com.anelsoftware.beer.repository.FacturaVentaRepository;
import com.anelsoftware.beer.service.ProductoService;
import com.anelsoftware.beer.domain.Producto;
import com.anelsoftware.beer.repository.ProductoRepository;
import com.anelsoftware.beer.repository.search.ProductoSearchRepository;
import com.anelsoftware.beer.service.dto.ProductoDTO;
import com.anelsoftware.beer.service.mapper.DetalleVentaMapper;
import com.anelsoftware.beer.service.mapper.ProductoMapper;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Producto.
 */
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    private final ProductoSearchRepository productoSearchRepository;

    private final DetalleVentaRepository detalleVentaRepository;

    private final DetalleVentaMapper detalleVentaMapper;

    private final FacturaVentaRepository facturaVentaRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, ProductoMapper productoMapper,
        ProductoSearchRepository productoSearchRepository,
        DetalleVentaRepository detalleVentaRepository,
        DetalleVentaMapper detalleVentaMapper,
        FacturaVentaRepository facturaVentaRepository) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
        this.productoSearchRepository = productoSearchRepository;
        this.detalleVentaRepository = detalleVentaRepository;
        this.detalleVentaMapper = detalleVentaMapper;
        this.facturaVentaRepository = facturaVentaRepository;
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
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Productos");
        return productoRepository.findAll(pageable)
            .map(productoMapper::toDto);
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
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Productos for query {}", query);
        return productoSearchRepository.search(queryStringQuery(query), pageable)
            .map(productoMapper::toDto);
    }

    @Override
    public List<ProductoDTO> findAllByFacturaId(Long facturaId) {
        List<ProductoDTO> list = new ArrayList<ProductoDTO>();
        Optional<FacturaVenta> facturaVenta = facturaVentaRepository.findById(facturaId);
        if(facturaVenta.isPresent()){
            List<DetalleVenta> detalleVentas = detalleVentaRepository
                .findAllByFacturaVenta(facturaVenta.get());
            detalleVentas.forEach( detalleVenta -> {
                list.add(productoMapper.toDto(detalleVenta.getProducto()));
            } );
            return list;
        }
        return null;
    }
}
