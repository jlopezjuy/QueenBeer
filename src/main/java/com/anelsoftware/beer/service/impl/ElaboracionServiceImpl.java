package com.anelsoftware.beer.service.impl;

import com.anelsoftware.beer.service.ElaboracionService;
import com.anelsoftware.beer.domain.Elaboracion;
import com.anelsoftware.beer.repository.ElaboracionRepository;
import com.anelsoftware.beer.repository.search.ElaboracionSearchRepository;
import com.anelsoftware.beer.service.dto.ElaboracionDTO;
import com.anelsoftware.beer.service.mapper.ElaboracionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Elaboracion.
 */
@Service
@Transactional
public class ElaboracionServiceImpl implements ElaboracionService {

    private final Logger log = LoggerFactory.getLogger(ElaboracionServiceImpl.class);

    private ElaboracionRepository elaboracionRepository;

    private ElaboracionMapper elaboracionMapper;

    private ElaboracionSearchRepository elaboracionSearchRepository;

    public ElaboracionServiceImpl(ElaboracionRepository elaboracionRepository, ElaboracionMapper elaboracionMapper, ElaboracionSearchRepository elaboracionSearchRepository) {
        this.elaboracionRepository = elaboracionRepository;
        this.elaboracionMapper = elaboracionMapper;
        this.elaboracionSearchRepository = elaboracionSearchRepository;
    }

    /**
     * Save a elaboracion.
     *
     * @param elaboracionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ElaboracionDTO save(ElaboracionDTO elaboracionDTO) {
        log.debug("Request to save Elaboracion : {}", elaboracionDTO);

        Elaboracion elaboracion = elaboracionMapper.toEntity(elaboracionDTO);
        elaboracion = elaboracionRepository.save(elaboracion);
        ElaboracionDTO result = elaboracionMapper.toDto(elaboracion);
        elaboracionSearchRepository.save(elaboracion);
        return result;
    }

    /**
     * Get all the elaboracions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ElaboracionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Elaboracions");
        return elaboracionRepository.findAll(pageable)
            .map(elaboracionMapper::toDto);
    }


    /**
     * Get one elaboracion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ElaboracionDTO> findOne(Long id) {
        log.debug("Request to get Elaboracion : {}", id);
        return elaboracionRepository.findById(id)
            .map(elaboracionMapper::toDto);
    }

    /**
     * Delete the elaboracion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Elaboracion : {}", id);
        elaboracionRepository.deleteById(id);
        elaboracionSearchRepository.deleteById(id);
    }

    /**
     * Search for the elaboracion corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ElaboracionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Elaboracions for query {}", query);
        return elaboracionSearchRepository.search(queryStringQuery(query), pageable)
            .map(elaboracionMapper::toDto);
    }
}
