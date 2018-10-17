package com.anelsoftware.beer.service.impl;

import com.anelsoftware.beer.service.ElaboracionInsumoService;
import com.anelsoftware.beer.domain.ElaboracionInsumo;
import com.anelsoftware.beer.repository.ElaboracionInsumoRepository;
import com.anelsoftware.beer.repository.search.ElaboracionInsumoSearchRepository;
import com.anelsoftware.beer.service.dto.ElaboracionInsumoDTO;
import com.anelsoftware.beer.service.mapper.ElaboracionInsumoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ElaboracionInsumoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ElaboracionInsumos");
        return elaboracionInsumoRepository.findAll(pageable)
            .map(elaboracionInsumoMapper::toDto);
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
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ElaboracionInsumoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ElaboracionInsumos for query {}", query);
        return elaboracionInsumoSearchRepository.search(queryStringQuery(query), pageable)
            .map(elaboracionInsumoMapper::toDto);
    }
}
