package com.anelsoftware.beer.service.impl;

import com.anelsoftware.beer.service.ClienteService;
import com.anelsoftware.beer.domain.Cliente;
import com.anelsoftware.beer.repository.ClienteRepository;
import com.anelsoftware.beer.repository.search.ClienteSearchRepository;
import com.anelsoftware.beer.service.dto.ClienteDTO;
import com.anelsoftware.beer.service.mapper.ClienteMapper;
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
 * Service Implementation for managing Cliente.
 */
@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private ClienteRepository clienteRepository;

    private ClienteMapper clienteMapper;

    private ClienteSearchRepository clienteSearchRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper, ClienteSearchRepository clienteSearchRepository) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.clienteSearchRepository = clienteSearchRepository;
    }

    /**
     * Save a cliente.
     *
     * @param clienteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClienteDTO save(ClienteDTO clienteDTO) {
        log.debug("Request to save Cliente : {}", clienteDTO);

        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente = clienteRepository.save(cliente);
        ClienteDTO result = clienteMapper.toDto(cliente);
        clienteSearchRepository.save(cliente);
        return result;
    }

    /**
     * Get all the clientes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> findAll() {
        log.debug("Request to get all Clientes");
        return clienteRepository.findAll().stream()
            .map(clienteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one cliente by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> findOne(Long id) {
        log.debug("Request to get Cliente : {}", id);
        return clienteRepository.findById(id)
            .map(clienteMapper::toDto);
    }

    /**
     * Delete the cliente by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cliente : {}", id);
        clienteRepository.deleteById(id);
        clienteSearchRepository.deleteById(id);
    }

    /**
     * Search for the cliente corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> search(String query) {
        log.debug("Request to search Clientes for query {}", query);
        return StreamSupport
            .stream(clienteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(clienteMapper::toDto)
            .collect(Collectors.toList());
    }
}
