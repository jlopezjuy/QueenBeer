package com.anelsoftware.beer.web.rest;

import com.anelsoftware.beer.QueenBeerApp;

import com.anelsoftware.beer.domain.Cliente;
import com.anelsoftware.beer.repository.ClienteRepository;
import com.anelsoftware.beer.repository.search.ClienteSearchRepository;
import com.anelsoftware.beer.service.ClienteService;
import com.anelsoftware.beer.service.dto.ClienteDTO;
import com.anelsoftware.beer.service.mapper.ClienteMapper;
import com.anelsoftware.beer.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static com.anelsoftware.beer.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.anelsoftware.beer.domain.enumeration.CategoriaCliente;
import com.anelsoftware.beer.domain.enumeration.CondicionFiscal;
import com.anelsoftware.beer.domain.enumeration.Provincia;
/**
 * Test class for the ClienteResource REST controller.
 *
 * @see ClienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueenBeerApp.class)
public class ClienteResourceIntTest {

    private static final String DEFAULT_NOMBRE_FANTASIA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_FANTASIA = "BBBBBBBBBB";

    private static final String DEFAULT_RAZON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZON_SOCIAL = "BBBBBBBBBB";

    private static final CategoriaCliente DEFAULT_CATEGORIA = CategoriaCliente.BAR;
    private static final CategoriaCliente UPDATED_CATEGORIA = CategoriaCliente.DISTRIBUIDOR;

    private static final String DEFAULT_CUIT = "AAAAAAAAAA";
    private static final String UPDATED_CUIT = "BBBBBBBBBB";

    private static final CondicionFiscal DEFAULT_CONDICION_FISCAL = CondicionFiscal.RESPONSABLE_INSCRIPTO;
    private static final CondicionFiscal UPDATED_CONDICION_FISCAL = CondicionFiscal.MONOTRIBUTISTA;

    private static final Instant DEFAULT_FECHA_ALTA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ALTA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIDAD_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDAD_CIUDAD = "BBBBBBBBBB";

    private static final Provincia DEFAULT_PRIVINCIA = Provincia.BUENOS_AIRES;
    private static final Provincia UPDATED_PRIVINCIA = Provincia.CATAMARCA;

    private static final Long DEFAULT_CODIGO_POSTAL = 1L;
    private static final Long UPDATED_CODIGO_POSTAL = 2L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;
    
    @Autowired
    private ClienteService clienteService;

    /**
     * This repository is mocked in the com.anelsoftware.beer.repository.search test package.
     *
     * @see com.anelsoftware.beer.repository.search.ClienteSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClienteSearchRepository mockClienteSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteResource clienteResource = new ClienteResource(clienteService);
        this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(clienteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .nombreFantasia(DEFAULT_NOMBRE_FANTASIA)
            .razonSocial(DEFAULT_RAZON_SOCIAL)
            .categoria(DEFAULT_CATEGORIA)
            .cuit(DEFAULT_CUIT)
            .condicionFiscal(DEFAULT_CONDICION_FISCAL)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .telefono(DEFAULT_TELEFONO)
            .direccion(DEFAULT_DIRECCION)
            .localidadCiudad(DEFAULT_LOCALIDAD_CIUDAD)
            .privincia(DEFAULT_PRIVINCIA)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .email(DEFAULT_EMAIL);
        return cliente;
    }

    @Before
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNombreFantasia()).isEqualTo(DEFAULT_NOMBRE_FANTASIA);
        assertThat(testCliente.getRazonSocial()).isEqualTo(DEFAULT_RAZON_SOCIAL);
        assertThat(testCliente.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testCliente.getCuit()).isEqualTo(DEFAULT_CUIT);
        assertThat(testCliente.getCondicionFiscal()).isEqualTo(DEFAULT_CONDICION_FISCAL);
        assertThat(testCliente.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testCliente.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testCliente.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testCliente.getLocalidadCiudad()).isEqualTo(DEFAULT_LOCALIDAD_CIUDAD);
        assertThat(testCliente.getPrivincia()).isEqualTo(DEFAULT_PRIVINCIA);
        assertThat(testCliente.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testCliente.getEmail()).isEqualTo(DEFAULT_EMAIL);

        // Validate the Cliente in Elasticsearch
        verify(mockClienteSearchRepository, times(1)).save(testCliente);
    }

    @Test
    @Transactional
    public void createClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente with an existing ID
        cliente.setId(1L);
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);

        // Validate the Cliente in Elasticsearch
        verify(mockClienteSearchRepository, times(0)).save(cliente);
    }

    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreFantasia").value(hasItem(DEFAULT_NOMBRE_FANTASIA.toString())))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].cuit").value(hasItem(DEFAULT_CUIT.toString())))
            .andExpect(jsonPath("$.[*].condicionFiscal").value(hasItem(DEFAULT_CONDICION_FISCAL.toString())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].localidadCiudad").value(hasItem(DEFAULT_LOCALIDAD_CIUDAD.toString())))
            .andExpect(jsonPath("$.[*].privincia").value(hasItem(DEFAULT_PRIVINCIA.toString())))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.nombreFantasia").value(DEFAULT_NOMBRE_FANTASIA.toString()))
            .andExpect(jsonPath("$.razonSocial").value(DEFAULT_RAZON_SOCIAL.toString()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()))
            .andExpect(jsonPath("$.cuit").value(DEFAULT_CUIT.toString()))
            .andExpect(jsonPath("$.condicionFiscal").value(DEFAULT_CONDICION_FISCAL.toString()))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.localidadCiudad").value(DEFAULT_LOCALIDAD_CIUDAD.toString()))
            .andExpect(jsonPath("$.privincia").value(DEFAULT_PRIVINCIA.toString()))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findById(cliente.getId()).get();
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente
            .nombreFantasia(UPDATED_NOMBRE_FANTASIA)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .categoria(UPDATED_CATEGORIA)
            .cuit(UPDATED_CUIT)
            .condicionFiscal(UPDATED_CONDICION_FISCAL)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .telefono(UPDATED_TELEFONO)
            .direccion(UPDATED_DIRECCION)
            .localidadCiudad(UPDATED_LOCALIDAD_CIUDAD)
            .privincia(UPDATED_PRIVINCIA)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .email(UPDATED_EMAIL);
        ClienteDTO clienteDTO = clienteMapper.toDto(updatedCliente);

        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNombreFantasia()).isEqualTo(UPDATED_NOMBRE_FANTASIA);
        assertThat(testCliente.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testCliente.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testCliente.getCuit()).isEqualTo(UPDATED_CUIT);
        assertThat(testCliente.getCondicionFiscal()).isEqualTo(UPDATED_CONDICION_FISCAL);
        assertThat(testCliente.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testCliente.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testCliente.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testCliente.getLocalidadCiudad()).isEqualTo(UPDATED_LOCALIDAD_CIUDAD);
        assertThat(testCliente.getPrivincia()).isEqualTo(UPDATED_PRIVINCIA);
        assertThat(testCliente.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testCliente.getEmail()).isEqualTo(UPDATED_EMAIL);

        // Validate the Cliente in Elasticsearch
        verify(mockClienteSearchRepository, times(1)).save(testCliente);
    }

    @Test
    @Transactional
    public void updateNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Cliente in Elasticsearch
        verify(mockClienteSearchRepository, times(0)).save(cliente);
    }

    @Test
    @Transactional
    public void deleteCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Get the cliente
        restClienteMockMvc.perform(delete("/api/clientes/{id}", cliente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Cliente in Elasticsearch
        verify(mockClienteSearchRepository, times(1)).deleteById(cliente.getId());
    }

    @Test
    @Transactional
    public void searchCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);
        when(mockClienteSearchRepository.search(queryStringQuery("id:" + cliente.getId())))
            .thenReturn(Collections.singletonList(cliente));
        // Search the cliente
        restClienteMockMvc.perform(get("/api/_search/clientes?query=id:" + cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreFantasia").value(hasItem(DEFAULT_NOMBRE_FANTASIA.toString())))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].cuit").value(hasItem(DEFAULT_CUIT.toString())))
            .andExpect(jsonPath("$.[*].condicionFiscal").value(hasItem(DEFAULT_CONDICION_FISCAL.toString())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].localidadCiudad").value(hasItem(DEFAULT_LOCALIDAD_CIUDAD.toString())))
            .andExpect(jsonPath("$.[*].privincia").value(hasItem(DEFAULT_PRIVINCIA.toString())))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cliente.class);
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        Cliente cliente2 = new Cliente();
        cliente2.setId(cliente1.getId());
        assertThat(cliente1).isEqualTo(cliente2);
        cliente2.setId(2L);
        assertThat(cliente1).isNotEqualTo(cliente2);
        cliente1.setId(null);
        assertThat(cliente1).isNotEqualTo(cliente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteDTO.class);
        ClienteDTO clienteDTO1 = new ClienteDTO();
        clienteDTO1.setId(1L);
        ClienteDTO clienteDTO2 = new ClienteDTO();
        assertThat(clienteDTO1).isNotEqualTo(clienteDTO2);
        clienteDTO2.setId(clienteDTO1.getId());
        assertThat(clienteDTO1).isEqualTo(clienteDTO2);
        clienteDTO2.setId(2L);
        assertThat(clienteDTO1).isNotEqualTo(clienteDTO2);
        clienteDTO1.setId(null);
        assertThat(clienteDTO1).isNotEqualTo(clienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clienteMapper.fromId(null)).isNull();
    }
}
