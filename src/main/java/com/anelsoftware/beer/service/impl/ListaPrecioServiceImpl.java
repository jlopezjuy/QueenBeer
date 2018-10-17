package com.anelsoftware.beer.service.impl;

import com.anelsoftware.beer.service.ListaPrecioService;
import com.anelsoftware.beer.domain.ListaPrecio;
import com.anelsoftware.beer.repository.ListaPrecioRepository;
import com.anelsoftware.beer.repository.search.ListaPrecioSearchRepository;
import com.anelsoftware.beer.service.dto.ListaPrecioDTO;
import com.anelsoftware.beer.service.mapper.ListaPrecioMapper;
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
 * Service Implementation for managing ListaPrecio.
 */
@Service
@Transactional
public class ListaPrecioServiceImpl implements ListaPrecioService {

    private final Logger log = LoggerFactory.getLogger(ListaPrecioServiceImpl.class);

    private ListaPrecioRepository listaPrecioRepository;

    private ListaPrecioMapper listaPrecioMapper;

    private ListaPrecioSearchRepository listaPrecioSearchRepository;

    public ListaPrecioServiceImpl(ListaPrecioRepository listaPrecioRepository, ListaPrecioMapper listaPrecioMapper, ListaPrecioSearchRepository listaPrecioSearchRepository) {
        this.listaPrecioRepository = listaPrecioRepository;
        this.listaPrecioMapper = listaPrecioMapper;
        this.listaPrecioSearchRepository = listaPrecioSearchRepository;
    }

    /**
     * Save a listaPrecio.
     *
     * @param listaPrecioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ListaPrecioDTO save(ListaPrecioDTO listaPrecioDTO) {
        log.debug("Request to save ListaPrecio : {}", listaPrecioDTO);

        ListaPrecio listaPrecio = listaPrecioMapper.toEntity(listaPrecioDTO);
        listaPrecio = listaPrecioRepository.save(listaPrecio);
        ListaPrecioDTO result = listaPrecioMapper.toDto(listaPrecio);
        listaPrecioSearchRepository.save(listaPrecio);
        return result;
    }

    /**
     * Get all the listaPrecios.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ListaPrecioDTO> findAll() {
        log.debug("Request to get all ListaPrecios");
        return listaPrecioRepository.findAll().stream()
            .map(listaPrecioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one listaPrecio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ListaPrecioDTO> findOne(Long id) {
        log.debug("Request to get ListaPrecio : {}", id);
        return listaPrecioRepository.findById(id)
            .map(listaPrecioMapper::toDto);
    }

    /**
     * Delete the listaPrecio by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ListaPrecio : {}", id);
        listaPrecioRepository.deleteById(id);
        listaPrecioSearchRepository.deleteById(id);
    }

    /**
     * Search for the listaPrecio corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ListaPrecioDTO> search(String query) {
        log.debug("Request to search ListaPrecios for query {}", query);
        return StreamSupport
            .stream(listaPrecioSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(listaPrecioMapper::toDto)
            .collect(Collectors.toList());
    }
}
