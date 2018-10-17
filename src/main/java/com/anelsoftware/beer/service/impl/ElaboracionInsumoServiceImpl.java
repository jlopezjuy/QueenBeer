package com.anelsoftware.beer.service.impl;

import com.anelsoftware.beer.service.ElaboracionInsumoService;
import com.anelsoftware.beer.domain.ElaboracionInsumo;
import com.anelsoftware.beer.repository.ElaboracionInsumoRepository;
import com.anelsoftware.beer.repository.search.ElaboracionInsumoSearchRepository;
import com.anelsoftware.beer.service.dto.ElaboracionInsumoDTO;
import com.anelsoftware.beer.service.mapper.ElaboracionInsumoMapper;
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
 * Service Implementation for managing ElaboracionInsumo.
 */
@Service
@Transactional
public class ElaboracionInsumoServiceImpl implements ElaboracionInsumoService {

    private final Logger log = LoggerFactory.getLogger(ElaboracionInsumoServiceImpl.class);

    private ElaboracionInsumoRepository elaboracionInsumoRepository;

    private ElaboracionInsumoMapper elaboracionInsumoMapper;

    private ElaboracionInsumoSearchRepository elaboracionInsumoSearchRepository;

    public ElaboracionInsumoServiceImpl(ElaboracionInsumoRepository elaboracionInsumoRepository, ElaboracionInsumoMapper elaboracionInsumoMapper, ElaboracionInsumoSearchRepository elaboracionInsumoSearchRepository) {
        this.elaboracionInsumoRepository = elaboracionInsumoRepository;
        this.elaboracionInsumoMapper = elaboracionInsumoMapper;
        this.elaboracionInsumoSearchRepository = elaboracionInsumoSearchRepository;
    }

    /**
     * Save a elaboracionInsumo.
     *
     * @param elaboracionInsumoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ElaboracionInsumoDTO save(ElaboracionInsumoDTO elaboracionInsumoDTO) {
        log.debug("Request to save ElaboracionInsumo : {}", elaboracionInsumoDTO);

        ElaboracionInsumo elaboracionInsumo = elaboracionInsumoMapper.toEntity(elaboracionInsumoDTO);
        elaboracionInsumo = elaboracionInsumoRepository.save(elaboracionInsumo);
        ElaboracionInsumoDTO result = elaboracionInsumoMapper.toDto(elaboracionInsumo);
        elaboracionInsumoSearchRepository.save(elaboracionInsumo);
        return result;
    }

    /**
     * Get all the elaboracionInsumos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ElaboracionInsumoDTO> findAll() {
        log.debug("Request to get all ElaboracionInsumos");
        return elaboracionInsumoRepository.findAll().stream()
            .map(elaboracionInsumoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one elaboracionInsumo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ElaboracionInsumoDTO> findOne(Long id) {
        log.debug("Request to get ElaboracionInsumo : {}", id);
        return elaboracionInsumoRepository.findById(id)
            .map(elaboracionInsumoMapper::toDto);
    }

    /**
     * Delete the elaboracionInsumo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ElaboracionInsumo : {}", id);
        elaboracionInsumoRepository.deleteById(id);
        elaboracionInsumoSearchRepository.deleteById(id);
    }

    /**
     * Search for the elaboracionInsumo corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ElaboracionInsumoDTO> search(String query) {
        log.debug("Request to search ElaboracionInsumos for query {}", query);
        return StreamSupport
            .stream(elaboracionInsumoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(elaboracionInsumoMapper::toDto)
            .collect(Collectors.toList());
    }
}
