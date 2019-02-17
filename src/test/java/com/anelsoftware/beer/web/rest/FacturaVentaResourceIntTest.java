package com.anelsoftware.beer.web.rest;

import com.anelsoftware.beer.QueenBeerApp;

import com.anelsoftware.beer.domain.FacturaVenta;
import com.anelsoftware.beer.domain.Cliente;
import com.anelsoftware.beer.repository.FacturaVentaRepository;
import com.anelsoftware.beer.repository.search.FacturaVentaSearchRepository;
import com.anelsoftware.beer.service.FacturaVentaService;
import com.anelsoftware.beer.service.dto.FacturaVentaDTO;
import com.anelsoftware.beer.service.mapper.FacturaVentaMapper;
import com.anelsoftware.beer.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static com.anelsoftware.beer.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.anelsoftware.beer.domain.enumeration.TipoDocumento;
import com.anelsoftware.beer.domain.enumeration.TipoMoneda;
/**
 * Test class for the FacturaVentaResource REST controller.
 *
 * @see FacturaVentaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueenBeerApp.class)
public class FacturaVentaResourceIntTest {

    private static final TipoDocumento DEFAULT_TIPO_DOCUMENTO = TipoDocumento.FACTURA;
    private static final TipoDocumento UPDATED_TIPO_DOCUMENTO = TipoDocumento.REMITO;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_TOTAL_NETO = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_NETO = new BigDecimal(2);

    private static final TipoMoneda DEFAULT_TIPO_MONEDA = TipoMoneda.PESOS_ARGENTINOS;
    private static final TipoMoneda UPDATED_TIPO_MONEDA = TipoMoneda.DOLARES;

    private static final String DEFAULT_NRO_FACTURA = "AAAAAAAAAA";
    private static final String UPDATED_NRO_FACTURA = "BBBBBBBBBB";

    @Autowired
    private FacturaVentaRepository facturaVentaRepository;

    @Autowired
    private FacturaVentaMapper facturaVentaMapper;

    @Autowired
    private FacturaVentaService facturaVentaService;

    /**
     * This repository is mocked in the com.anelsoftware.beer.repository.search test package.
     *
     * @see com.anelsoftware.beer.repository.search.FacturaVentaSearchRepositoryMockConfiguration
     */
    @Autowired
    private FacturaVentaSearchRepository mockFacturaVentaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFacturaVentaMockMvc;

    private FacturaVenta facturaVenta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FacturaVentaResource facturaVentaResource = new FacturaVentaResource(facturaVentaService);
        this.restFacturaVentaMockMvc = MockMvcBuilders.standaloneSetup(facturaVentaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacturaVenta createEntity(EntityManager em) {
        FacturaVenta facturaVenta = new FacturaVenta()
            .tipoDocumento(DEFAULT_TIPO_DOCUMENTO)
            .fecha(DEFAULT_FECHA)
            .totalNeto(DEFAULT_TOTAL_NETO)
            .tipoMoneda(DEFAULT_TIPO_MONEDA)
            .nroFactura(DEFAULT_NRO_FACTURA);
        // Add required entity
        Cliente cliente = ClienteResourceIntTest.createEntity(em);
        em.persist(cliente);
        em.flush();
        facturaVenta.setCliente(cliente);
        return facturaVenta;
    }

    @Before
    public void initTest() {
        facturaVenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacturaVenta() throws Exception {
        int databaseSizeBeforeCreate = facturaVentaRepository.findAll().size();

        // Create the FacturaVenta
        FacturaVentaDTO facturaVentaDTO = facturaVentaMapper.toDto(facturaVenta);
        restFacturaVentaMockMvc.perform(post("/api/factura-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturaVentaDTO)))
            .andExpect(status().isCreated());

        // Validate the FacturaVenta in the database
        List<FacturaVenta> facturaVentaList = facturaVentaRepository.findAll();
        assertThat(facturaVentaList).hasSize(databaseSizeBeforeCreate + 1);
        FacturaVenta testFacturaVenta = facturaVentaList.get(facturaVentaList.size() - 1);
        assertThat(testFacturaVenta.getTipoDocumento()).isEqualTo(DEFAULT_TIPO_DOCUMENTO);
        assertThat(testFacturaVenta.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testFacturaVenta.getTotalNeto()).isEqualTo(DEFAULT_TOTAL_NETO);
        assertThat(testFacturaVenta.getTipoMoneda()).isEqualTo(DEFAULT_TIPO_MONEDA);
        assertThat(testFacturaVenta.getNroFactura()).isEqualTo(DEFAULT_NRO_FACTURA);

        // Validate the id for MapsId, the ids must be same
        assertThat(testFacturaVenta.getId()).isEqualTo(testFacturaVenta.getCliente().getId());

        // Validate the FacturaVenta in Elasticsearch
        verify(mockFacturaVentaSearchRepository, times(1)).save(testFacturaVenta);
    }

    @Test
    @Transactional
    public void createFacturaVentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facturaVentaRepository.findAll().size();

        // Create the FacturaVenta with an existing ID
        facturaVenta.setId(1L);
        FacturaVentaDTO facturaVentaDTO = facturaVentaMapper.toDto(facturaVenta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacturaVentaMockMvc.perform(post("/api/factura-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturaVentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FacturaVenta in the database
        List<FacturaVenta> facturaVentaList = facturaVentaRepository.findAll();
        assertThat(facturaVentaList).hasSize(databaseSizeBeforeCreate);

        // Validate the FacturaVenta in Elasticsearch
        verify(mockFacturaVentaSearchRepository, times(0)).save(facturaVenta);
    }

    @Test
    @Transactional
    public void getAllFacturaVentas() throws Exception {
        // Initialize the database
        facturaVentaRepository.saveAndFlush(facturaVenta);

        // Get all the facturaVentaList
        restFacturaVentaMockMvc.perform(get("/api/factura-ventas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facturaVenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].totalNeto").value(hasItem(DEFAULT_TOTAL_NETO.intValue())))
            .andExpect(jsonPath("$.[*].tipoMoneda").value(hasItem(DEFAULT_TIPO_MONEDA.toString())))
            .andExpect(jsonPath("$.[*].nroFactura").value(hasItem(DEFAULT_NRO_FACTURA.toString())));
    }
    
    @Test
    @Transactional
    public void getFacturaVenta() throws Exception {
        // Initialize the database
        facturaVentaRepository.saveAndFlush(facturaVenta);

        // Get the facturaVenta
        restFacturaVentaMockMvc.perform(get("/api/factura-ventas/{id}", facturaVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(facturaVenta.getId().intValue()))
            .andExpect(jsonPath("$.tipoDocumento").value(DEFAULT_TIPO_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.totalNeto").value(DEFAULT_TOTAL_NETO.intValue()))
            .andExpect(jsonPath("$.tipoMoneda").value(DEFAULT_TIPO_MONEDA.toString()))
            .andExpect(jsonPath("$.nroFactura").value(DEFAULT_NRO_FACTURA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFacturaVenta() throws Exception {
        // Get the facturaVenta
        restFacturaVentaMockMvc.perform(get("/api/factura-ventas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacturaVenta() throws Exception {
        // Initialize the database
        facturaVentaRepository.saveAndFlush(facturaVenta);

        int databaseSizeBeforeUpdate = facturaVentaRepository.findAll().size();

        // Update the facturaVenta
        FacturaVenta updatedFacturaVenta = facturaVentaRepository.findById(facturaVenta.getId()).get();
        // Disconnect from session so that the updates on updatedFacturaVenta are not directly saved in db
        em.detach(updatedFacturaVenta);
        updatedFacturaVenta
            .tipoDocumento(UPDATED_TIPO_DOCUMENTO)
            .fecha(UPDATED_FECHA)
            .totalNeto(UPDATED_TOTAL_NETO)
            .tipoMoneda(UPDATED_TIPO_MONEDA)
            .nroFactura(UPDATED_NRO_FACTURA);
        FacturaVentaDTO facturaVentaDTO = facturaVentaMapper.toDto(updatedFacturaVenta);

        restFacturaVentaMockMvc.perform(put("/api/factura-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturaVentaDTO)))
            .andExpect(status().isOk());

        // Validate the FacturaVenta in the database
        List<FacturaVenta> facturaVentaList = facturaVentaRepository.findAll();
        assertThat(facturaVentaList).hasSize(databaseSizeBeforeUpdate);
        FacturaVenta testFacturaVenta = facturaVentaList.get(facturaVentaList.size() - 1);
        assertThat(testFacturaVenta.getTipoDocumento()).isEqualTo(UPDATED_TIPO_DOCUMENTO);
        assertThat(testFacturaVenta.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testFacturaVenta.getTotalNeto()).isEqualTo(UPDATED_TOTAL_NETO);
        assertThat(testFacturaVenta.getTipoMoneda()).isEqualTo(UPDATED_TIPO_MONEDA);
        assertThat(testFacturaVenta.getNroFactura()).isEqualTo(UPDATED_NRO_FACTURA);

        // Validate the FacturaVenta in Elasticsearch
        verify(mockFacturaVentaSearchRepository, times(1)).save(testFacturaVenta);
    }

    @Test
    @Transactional
    public void updateNonExistingFacturaVenta() throws Exception {
        int databaseSizeBeforeUpdate = facturaVentaRepository.findAll().size();

        // Create the FacturaVenta
        FacturaVentaDTO facturaVentaDTO = facturaVentaMapper.toDto(facturaVenta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacturaVentaMockMvc.perform(put("/api/factura-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturaVentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FacturaVenta in the database
        List<FacturaVenta> facturaVentaList = facturaVentaRepository.findAll();
        assertThat(facturaVentaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FacturaVenta in Elasticsearch
        verify(mockFacturaVentaSearchRepository, times(0)).save(facturaVenta);
    }

    @Test
    @Transactional
    public void deleteFacturaVenta() throws Exception {
        // Initialize the database
        facturaVentaRepository.saveAndFlush(facturaVenta);

        int databaseSizeBeforeDelete = facturaVentaRepository.findAll().size();

        // Delete the facturaVenta
        restFacturaVentaMockMvc.perform(delete("/api/factura-ventas/{id}", facturaVenta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FacturaVenta> facturaVentaList = facturaVentaRepository.findAll();
        assertThat(facturaVentaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FacturaVenta in Elasticsearch
        verify(mockFacturaVentaSearchRepository, times(1)).deleteById(facturaVenta.getId());
    }

    @Test
    @Transactional
    public void searchFacturaVenta() throws Exception {
        // Initialize the database
        facturaVentaRepository.saveAndFlush(facturaVenta);
        when(mockFacturaVentaSearchRepository.search(queryStringQuery("id:" + facturaVenta.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(facturaVenta), PageRequest.of(0, 1), 1));
        // Search the facturaVenta
        restFacturaVentaMockMvc.perform(get("/api/_search/factura-ventas?query=id:" + facturaVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facturaVenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].totalNeto").value(hasItem(DEFAULT_TOTAL_NETO.intValue())))
            .andExpect(jsonPath("$.[*].tipoMoneda").value(hasItem(DEFAULT_TIPO_MONEDA.toString())))
            .andExpect(jsonPath("$.[*].nroFactura").value(hasItem(DEFAULT_NRO_FACTURA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacturaVenta.class);
        FacturaVenta facturaVenta1 = new FacturaVenta();
        facturaVenta1.setId(1L);
        FacturaVenta facturaVenta2 = new FacturaVenta();
        facturaVenta2.setId(facturaVenta1.getId());
        assertThat(facturaVenta1).isEqualTo(facturaVenta2);
        facturaVenta2.setId(2L);
        assertThat(facturaVenta1).isNotEqualTo(facturaVenta2);
        facturaVenta1.setId(null);
        assertThat(facturaVenta1).isNotEqualTo(facturaVenta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacturaVentaDTO.class);
        FacturaVentaDTO facturaVentaDTO1 = new FacturaVentaDTO();
        facturaVentaDTO1.setId(1L);
        FacturaVentaDTO facturaVentaDTO2 = new FacturaVentaDTO();
        assertThat(facturaVentaDTO1).isNotEqualTo(facturaVentaDTO2);
        facturaVentaDTO2.setId(facturaVentaDTO1.getId());
        assertThat(facturaVentaDTO1).isEqualTo(facturaVentaDTO2);
        facturaVentaDTO2.setId(2L);
        assertThat(facturaVentaDTO1).isNotEqualTo(facturaVentaDTO2);
        facturaVentaDTO1.setId(null);
        assertThat(facturaVentaDTO1).isNotEqualTo(facturaVentaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(facturaVentaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(facturaVentaMapper.fromId(null)).isNull();
    }
}
