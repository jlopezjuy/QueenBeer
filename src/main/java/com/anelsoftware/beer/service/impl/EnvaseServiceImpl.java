package com.anelsoftware.beer.service.impl;

import com.anelsoftware.beer.repository.ProductoRepository;
import com.anelsoftware.beer.service.EnvaseService;
import com.anelsoftware.beer.domain.Envase;
import com.anelsoftware.beer.repository.EnvaseRepository;
import com.anelsoftware.beer.repository.search.EnvaseSearchRepository;
import com.anelsoftware.beer.service.dto.EnvaseDTO;
import com.anelsoftware.beer.service.mapper.EnvaseMapper;
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
 * Service Implementation for managing Envase.
 */
@Service
@Transactional
public class EnvaseServiceImpl implements EnvaseService {

    private final Logger log = LoggerFactory.getLogger(EnvaseServiceImpl.class);

    private final EnvaseRepository envaseRepository;

    private final ProductoRepository productoRepository;

    private final EnvaseMapper envaseMapper;

    private final EnvaseSearchRepository envaseSearchRepository;

    public EnvaseServiceImpl(EnvaseRepository envaseRepository,
        ProductoRepository productoRepository,
        EnvaseMapper envaseMapper, EnvaseSearchRepository envaseSearchRepository) {
        this.envaseRepository = envaseRepository;
        this.productoRepository = productoRepository;
        this.envaseMapper = envaseMapper;
        this.envaseSearchRepository = envaseSearchRepository;
    }

    /**
     * Save a envase.
     *
     * @param envaseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EnvaseDTO save(EnvaseDTO envaseDTO) {
        log.debug("Request to save Envase : {}", envaseDTO);

        Envase envase = envaseMapper.toEntity(envaseDTO);
        envase = envaseRepository.save(envase);
        EnvaseDTO result = envaseMapper.toDto(envase);
        envaseSearchRepository.save(envase);
        return result;
    }

    /**
     * Get all the envases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EnvaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Envases");
        return envaseRepository.findAll(pageable)
            .map(envaseMapper::toDto);
    }


    /**
     * Get one envase by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EnvaseDTO> findOne(Long id) {
        log.debug("Request to get Envase : {}", id);
        return envaseRepository.findById(id)
            .map(envaseMapper::toDto);
    }

    /**
     * Delete the envase by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Envase : {}", id);
        envaseRepository.deleteById(id);
        envaseSearchRepository.deleteById(id);
    }

    /**
     * Search for the envase corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EnvaseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Envases for query {}", query);
        return envaseSearchRepository.search(queryStringQuery(query), pageable)
            .map(envaseMapper::toDto);
    }

    @Override
    public List<EnvaseDTO> findAllByProductoId(Long productoId) {
        return envaseMapper.toDto(envaseRepository.findAllByProducto(productoRepository.getOne(productoId)));
    }
}
