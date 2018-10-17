package com.anelsoftware.beer.service.impl;

import com.anelsoftware.beer.service.ElaboracionService;
import com.anelsoftware.beer.domain.Elaboracion;
import com.anelsoftware.beer.repository.ElaboracionRepository;
import com.anelsoftware.beer.repository.search.ElaboracionSearchRepository;
import com.anelsoftware.beer.service.dto.ElaboracionDTO;
import com.anelsoftware.beer.service.mapper.ElaboracionMapper;
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
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ElaboracionDTO> findAll() {
        log.debug("Request to get all Elaboracions");
        return elaboracionRepository.findAll().stream()
            .map(elaboracionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ElaboracionDTO> search(String query) {
        log.debug("Request to search Elaboracions for query {}", query);
        return StreamSupport
            .stream(elaboracionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(elaboracionMapper::toDto)
            .collect(Collectors.toList());
    }
}
