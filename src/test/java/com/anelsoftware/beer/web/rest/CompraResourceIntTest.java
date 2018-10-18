package com.anelsoftware.beer.web.rest;

import com.anelsoftware.beer.QueenBeerApp;

import com.anelsoftware.beer.domain.Compra;
import com.anelsoftware.beer.repository.CompraRepository;
import com.anelsoftware.beer.repository.search.CompraSearchRepository;
import com.anelsoftware.beer.service.CompraService;
import com.anelsoftware.beer.service.dto.CompraDTO;
import com.anelsoftware.beer.service.mapper.CompraMapper;
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

/**
 * Test class for the CompraResource REST controller.
 *
 * @see CompraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueenBeerApp.class)
public class CompraResourceIntTest {

    private static final LocalDate DEFAULT_FECHA_COMPRA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_COMPRA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_ENTREGA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ENTREGA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NRO_FACTURA = "AAAAAAAAAA";
    private static final String UPDATED_NRO_FACTURA = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SUBTOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBTOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IMPUESTOS = new BigDecimal(1);
    private static final BigDecimal UPDATED_IMPUESTOS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private CompraMapper compraMapper;
    
    @Autowired
    private CompraService compraService;

    /**
     * This repository is mocked in the com.anelsoftware.beer.repository.search test package.
     *
     * @see com.anelsoftware.beer.repository.search.CompraSearchRepositoryMockConfiguration
     */
    @Autowired
    private CompraSearchRepository mockCompraSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompraMockMvc;

    private Compra compra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompraResource compraResource = new CompraResource(compraService);
        this.restCompraMockMvc = MockMvcBuilders.standaloneSetup(compraResource)
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
    public static Compra createEntity(EntityManager em) {
        Compra compra = new Compra()
            .fechaCompra(DEFAULT_FECHA_COMPRA)
            .fechaEntrega(DEFAULT_FECHA_ENTREGA)
            .nroFactura(DEFAULT_NRO_FACTURA)
            .subtotal(DEFAULT_SUBTOTAL)
            .impuestos(DEFAULT_IMPUESTOS)
            .total(DEFAULT_TOTAL);
        return compra;
    }

    @Before
    public void initTest() {
        compra = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompra() throws Exception {
        int databaseSizeBeforeCreate = compraRepository.findAll().size();

        // Create the Compra
        CompraDTO compraDTO = compraMapper.toDto(compra);
        restCompraMockMvc.perform(post("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isCreated());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeCreate + 1);
        Compra testCompra = compraList.get(compraList.size() - 1);
        assertThat(testCompra.getFechaCompra()).isEqualTo(DEFAULT_FECHA_COMPRA);
        assertThat(testCompra.getFechaEntrega()).isEqualTo(DEFAULT_FECHA_ENTREGA);
        assertThat(testCompra.getNroFactura()).isEqualTo(DEFAULT_NRO_FACTURA);
        assertThat(testCompra.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testCompra.getImpuestos()).isEqualTo(DEFAULT_IMPUESTOS);
        assertThat(testCompra.getTotal()).isEqualTo(DEFAULT_TOTAL);

        // Validate the Compra in Elasticsearch
        verify(mockCompraSearchRepository, times(1)).save(testCompra);
    }

    @Test
    @Transactional
    public void createCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compraRepository.findAll().size();

        // Create the Compra with an existing ID
        compra.setId(1L);
        CompraDTO compraDTO = compraMapper.toDto(compra);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompraMockMvc.perform(post("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeCreate);

        // Validate the Compra in Elasticsearch
        verify(mockCompraSearchRepository, times(0)).save(compra);
    }

    @Test
    @Transactional
    public void getAllCompras() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList
        restCompraMockMvc.perform(get("/api/compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compra.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaCompra").value(hasItem(DEFAULT_FECHA_COMPRA.toString())))
            .andExpect(jsonPath("$.[*].fechaEntrega").value(hasItem(DEFAULT_FECHA_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].nroFactura").value(hasItem(DEFAULT_NRO_FACTURA.toString())))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())))
            .andExpect(jsonPath("$.[*].impuestos").value(hasItem(DEFAULT_IMPUESTOS.intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get the compra
        restCompraMockMvc.perform(get("/api/compras/{id}", compra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compra.getId().intValue()))
            .andExpect(jsonPath("$.fechaCompra").value(DEFAULT_FECHA_COMPRA.toString()))
            .andExpect(jsonPath("$.fechaEntrega").value(DEFAULT_FECHA_ENTREGA.toString()))
            .andExpect(jsonPath("$.nroFactura").value(DEFAULT_NRO_FACTURA.toString()))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.intValue()))
            .andExpect(jsonPath("$.impuestos").value(DEFAULT_IMPUESTOS.intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompra() throws Exception {
        // Get the compra
        restCompraMockMvc.perform(get("/api/compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        int databaseSizeBeforeUpdate = compraRepository.findAll().size();

        // Update the compra
        Compra updatedCompra = compraRepository.findById(compra.getId()).get();
        // Disconnect from session so that the updates on updatedCompra are not directly saved in db
        em.detach(updatedCompra);
        updatedCompra
            .fechaCompra(UPDATED_FECHA_COMPRA)
            .fechaEntrega(UPDATED_FECHA_ENTREGA)
            .nroFactura(UPDATED_NRO_FACTURA)
            .subtotal(UPDATED_SUBTOTAL)
            .impuestos(UPDATED_IMPUESTOS)
            .total(UPDATED_TOTAL);
        CompraDTO compraDTO = compraMapper.toDto(updatedCompra);

        restCompraMockMvc.perform(put("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isOk());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeUpdate);
        Compra testCompra = compraList.get(compraList.size() - 1);
        assertThat(testCompra.getFechaCompra()).isEqualTo(UPDATED_FECHA_COMPRA);
        assertThat(testCompra.getFechaEntrega()).isEqualTo(UPDATED_FECHA_ENTREGA);
        assertThat(testCompra.getNroFactura()).isEqualTo(UPDATED_NRO_FACTURA);
        assertThat(testCompra.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testCompra.getImpuestos()).isEqualTo(UPDATED_IMPUESTOS);
        assertThat(testCompra.getTotal()).isEqualTo(UPDATED_TOTAL);

        // Validate the Compra in Elasticsearch
        verify(mockCompraSearchRepository, times(1)).save(testCompra);
    }

    @Test
    @Transactional
    public void updateNonExistingCompra() throws Exception {
        int databaseSizeBeforeUpdate = compraRepository.findAll().size();

        // Create the Compra
        CompraDTO compraDTO = compraMapper.toDto(compra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompraMockMvc.perform(put("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Compra in Elasticsearch
        verify(mockCompraSearchRepository, times(0)).save(compra);
    }

    @Test
    @Transactional
    public void deleteCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        int databaseSizeBeforeDelete = compraRepository.findAll().size();

        // Get the compra
        restCompraMockMvc.perform(delete("/api/compras/{id}", compra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Compra in Elasticsearch
        verify(mockCompraSearchRepository, times(1)).deleteById(compra.getId());
    }

    @Test
    @Transactional
    public void searchCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);
        when(mockCompraSearchRepository.search(queryStringQuery("id:" + compra.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(compra), PageRequest.of(0, 1), 1));
        // Search the compra
        restCompraMockMvc.perform(get("/api/_search/compras?query=id:" + compra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compra.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaCompra").value(hasItem(DEFAULT_FECHA_COMPRA.toString())))
            .andExpect(jsonPath("$.[*].fechaEntrega").value(hasItem(DEFAULT_FECHA_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].nroFactura").value(hasItem(DEFAULT_NRO_FACTURA.toString())))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())))
            .andExpect(jsonPath("$.[*].impuestos").value(hasItem(DEFAULT_IMPUESTOS.intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Compra.class);
        Compra compra1 = new Compra();
        compra1.setId(1L);
        Compra compra2 = new Compra();
        compra2.setId(compra1.getId());
        assertThat(compra1).isEqualTo(compra2);
        compra2.setId(2L);
        assertThat(compra1).isNotEqualTo(compra2);
        compra1.setId(null);
        assertThat(compra1).isNotEqualTo(compra2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraDTO.class);
        CompraDTO compraDTO1 = new CompraDTO();
        compraDTO1.setId(1L);
        CompraDTO compraDTO2 = new CompraDTO();
        assertThat(compraDTO1).isNotEqualTo(compraDTO2);
        compraDTO2.setId(compraDTO1.getId());
        assertThat(compraDTO1).isEqualTo(compraDTO2);
        compraDTO2.setId(2L);
        assertThat(compraDTO1).isNotEqualTo(compraDTO2);
        compraDTO1.setId(null);
        assertThat(compraDTO1).isNotEqualTo(compraDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(compraMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(compraMapper.fromId(null)).isNull();
    }
}
