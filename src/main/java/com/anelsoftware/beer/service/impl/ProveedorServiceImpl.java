package com.anelsoftware.beer.service.impl;

import com.anelsoftware.beer.service.ProveedorService;
import com.anelsoftware.beer.domain.Proveedor;
import com.anelsoftware.beer.repository.ProveedorRepository;
import com.anelsoftware.beer.repository.search.ProveedorSearchRepository;
import com.anelsoftware.beer.service.dto.ProveedorDTO;
import com.anelsoftware.beer.service.mapper.ProveedorMapper;
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
 * Service Implementation for managing Proveedor.
 */
@Service
@Transactional
public class ProveedorServiceImpl implements ProveedorService {

    private final Logger log = LoggerFactory.getLogger(ProveedorServiceImpl.class);

    private ProveedorRepository proveedorRepository;

    private ProveedorMapper proveedorMapper;

    private ProveedorSearchRepository proveedorSearchRepository;

    public ProveedorServiceImpl(ProveedorRepository proveedorRepository, ProveedorMapper proveedorMapper, ProveedorSearchRepository proveedorSearchRepository) {
        this.proveedorRepository = proveedorRepository;
        this.proveedorMapper = proveedorMapper;
        this.proveedorSearchRepository = proveedorSearchRepository;
    }

    /**
     * Save a proveedor.
     *
     * @param proveedorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProveedorDTO save(ProveedorDTO proveedorDTO) {
        log.debug("Request to save Proveedor : {}", proveedorDTO);

        Proveedor proveedor = proveedorMapper.toEntity(proveedorDTO);
        proveedor = proveedorRepository.save(proveedor);
        ProveedorDTO result = proveedorMapper.toDto(proveedor);
        proveedorSearchRepository.save(proveedor);
        return result;
    }

    /**
     * Get all the proveedors.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProveedorDTO> findAll() {
        log.debug("Request to get all Proveedors");
        return proveedorRepository.findAll().stream()
            .map(proveedorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one proveedor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProveedorDTO> findOne(Long id) {
        log.debug("Request to get Proveedor : {}", id);
        return proveedorRepository.findById(id)
            .map(proveedorMapper::toDto);
    }

    /**
     * Delete the proveedor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Proveedor : {}", id);
        proveedorRepository.deleteById(id);
        proveedorSearchRepository.deleteById(id);
    }

    /**
     * Search for the proveedor corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProveedorDTO> search(String query) {
        log.debug("Request to search Proveedors for query {}", query);
        return StreamSupport
            .stream(proveedorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(proveedorMapper::toDto)
            .collect(Collectors.toList());
    }
}
