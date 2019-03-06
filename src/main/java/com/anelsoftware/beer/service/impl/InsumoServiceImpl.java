package com.anelsoftware.beer.service.impl;

import com.anelsoftware.beer.domain.enumeration.TipoInsumo;
import com.anelsoftware.beer.service.InsumoService;
import com.anelsoftware.beer.domain.Insumo;
import com.anelsoftware.beer.repository.InsumoRepository;
import com.anelsoftware.beer.repository.search.InsumoSearchRepository;
import com.anelsoftware.beer.service.dto.InsumoDTO;
import com.anelsoftware.beer.service.mapper.InsumoMapper;
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
 * Service Implementation for managing Insumo.
 */
@Service
@Transactional
public class InsumoServiceImpl implements InsumoService {

    private final Logger log = LoggerFactory.getLogger(InsumoServiceImpl.class);

    private InsumoRepository insumoRepository;

    private InsumoMapper insumoMapper;

    private InsumoSearchRepository insumoSearchRepository;

    public InsumoServiceImpl(InsumoRepository insumoRepository, InsumoMapper insumoMapper, InsumoSearchRepository insumoSearchRepository) {
        this.insumoRepository = insumoRepository;
        this.insumoMapper = insumoMapper;
        this.insumoSearchRepository = insumoSearchRepository;
    }

    /**
     * Save a insumo.
     *
     * @param insumoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InsumoDTO save(InsumoDTO insumoDTO) {
        log.debug("Request to save Insumo : {}", insumoDTO);

        Insumo insumo = insumoMapper.toEntity(insumoDTO);
        insumo = insumoRepository.save(insumo);
        InsumoDTO result = insumoMapper.toDto(insumo);
        insumoSearchRepository.save(insumo);
        return result;
    }

    /**
     * Get all the insumos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InsumoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Insumos");
        return insumoRepository.findAll(pageable)
            .map(insumoMapper::toDto);
    }

    /**
     *
     * @return
     */
    @Override
    public List<InsumoDTO> findAll() {
        return insumoMapper.toDto(insumoRepository.findAllByOrderByNombre());
    }

    @Override
    public List<InsumoDTO> findAllMalta() {
        return insumoMapper.toDto(insumoRepository.findAllByTipoOrderByNombre(TipoInsumo.MALTA));
    }

    @Override
    public List<InsumoDTO> findAllLupulo() {
        return insumoMapper.toDto(insumoRepository.findAllByTipoOrderByNombre(TipoInsumo.LUPULO));
    }


    /**
     * Get one insumo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InsumoDTO> findOne(Long id) {
        log.debug("Request to get Insumo : {}", id);
        return insumoRepository.findById(id)
            .map(insumoMapper::toDto);
    }

    /**
     * Delete the insumo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Insumo : {}", id);
        insumoRepository.deleteById(id);
        insumoSearchRepository.deleteById(id);
    }

    /**
     * Search for the insumo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InsumoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Insumos for query {}", query);
        return insumoSearchRepository.search(queryStringQuery(query), pageable)
            .map(insumoMapper::toDto);
    }
}
