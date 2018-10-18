package com.anelsoftware.beer.web.rest;

import com.anelsoftware.beer.QueenBeerApp;

import com.anelsoftware.beer.domain.CompraInsumo;
import com.anelsoftware.beer.repository.CompraInsumoRepository;
import com.anelsoftware.beer.repository.search.CompraInsumoSearchRepository;
import com.anelsoftware.beer.service.CompraInsumoService;
import com.anelsoftware.beer.service.dto.CompraInsumoDTO;
import com.anelsoftware.beer.service.mapper.CompraInsumoMapper;
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
import java.util.Collections;
import java.util.List;


import static com.anelsoftware.beer.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.anelsoftware.beer.domain.enumeration.Unidad;
/**
 * Test class for the CompraInsumoResource REST controller.
 *
 * @see CompraInsumoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueenBeerApp.class)
public class CompraInsumoResourceIntTest {

    private static final BigDecimal DEFAULT_CANTIDAD = new BigDecimal(1);
    private static final BigDecimal UPDATED_CANTIDAD = new BigDecimal(2);

    private static final Unidad DEFAULT_UNIDAD = Unidad.KILOGRAMO;
    private static final Unidad UPDATED_UNIDAD = Unidad.GRAMO;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_COSTO_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_COSTO_TOTAL = new BigDecimal(2);

    @Autowired
    private CompraInsumoRepository compraInsumoRepository;

    @Autowired
    private CompraInsumoMapper compraInsumoMapper;
    
    @Autowired
    private CompraInsumoService compraInsumoService;

    /**
     * This repository is mocked in the com.anelsoftware.beer.repository.search test package.
     *
     * @see com.anelsoftware.beer.repository.search.CompraInsumoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CompraInsumoSearchRepository mockCompraInsumoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompraInsumoMockMvc;

    private CompraInsumo compraInsumo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompraInsumoResource compraInsumoResource = new CompraInsumoResource(compraInsumoService);
        this.restCompraInsumoMockMvc = MockMvcBuilders.standaloneSetup(compraInsumoResource)
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
    public static CompraInsumo createEntity(EntityManager em) {
        CompraInsumo compraInsumo = new CompraInsumo()
            .cantidad(DEFAULT_CANTIDAD)
            .unidad(DEFAULT_UNIDAD)
            .descripcion(DEFAULT_DESCRIPCION)
            .costoTotal(DEFAULT_COSTO_TOTAL);
        return compraInsumo;
    }

    @Before
    public void initTest() {
        compraInsumo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompraInsumo() throws Exception {
        int databaseSizeBeforeCreate = compraInsumoRepository.findAll().size();

        // Create the CompraInsumo
        CompraInsumoDTO compraInsumoDTO = compraInsumoMapper.toDto(compraInsumo);
        restCompraInsumoMockMvc.perform(post("/api/compra-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraInsumoDTO)))
            .andExpect(status().isCreated());

        // Validate the CompraInsumo in the database
        List<CompraInsumo> compraInsumoList = compraInsumoRepository.findAll();
        assertThat(compraInsumoList).hasSize(databaseSizeBeforeCreate + 1);
        CompraInsumo testCompraInsumo = compraInsumoList.get(compraInsumoList.size() - 1);
        assertThat(testCompraInsumo.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testCompraInsumo.getUnidad()).isEqualTo(DEFAULT_UNIDAD);
        assertThat(testCompraInsumo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCompraInsumo.getCostoTotal()).isEqualTo(DEFAULT_COSTO_TOTAL);

        // Validate the CompraInsumo in Elasticsearch
        verify(mockCompraInsumoSearchRepository, times(1)).save(testCompraInsumo);
    }

    @Test
    @Transactional
    public void createCompraInsumoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compraInsumoRepository.findAll().size();

        // Create the CompraInsumo with an existing ID
        compraInsumo.setId(1L);
        CompraInsumoDTO compraInsumoDTO = compraInsumoMapper.toDto(compraInsumo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompraInsumoMockMvc.perform(post("/api/compra-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraInsumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompraInsumo in the database
        List<CompraInsumo> compraInsumoList = compraInsumoRepository.findAll();
        assertThat(compraInsumoList).hasSize(databaseSizeBeforeCreate);

        // Validate the CompraInsumo in Elasticsearch
        verify(mockCompraInsumoSearchRepository, times(0)).save(compraInsumo);
    }

    @Test
    @Transactional
    public void getAllCompraInsumos() throws Exception {
        // Initialize the database
        compraInsumoRepository.saveAndFlush(compraInsumo);

        // Get all the compraInsumoList
        restCompraInsumoMockMvc.perform(get("/api/compra-insumos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compraInsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())))
            .andExpect(jsonPath("$.[*].unidad").value(hasItem(DEFAULT_UNIDAD.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].costoTotal").value(hasItem(DEFAULT_COSTO_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getCompraInsumo() throws Exception {
        // Initialize the database
        compraInsumoRepository.saveAndFlush(compraInsumo);

        // Get the compraInsumo
        restCompraInsumoMockMvc.perform(get("/api/compra-insumos/{id}", compraInsumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compraInsumo.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()))
            .andExpect(jsonPath("$.unidad").value(DEFAULT_UNIDAD.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.costoTotal").value(DEFAULT_COSTO_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompraInsumo() throws Exception {
        // Get the compraInsumo
        restCompraInsumoMockMvc.perform(get("/api/compra-insumos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompraInsumo() throws Exception {
        // Initialize the database
        compraInsumoRepository.saveAndFlush(compraInsumo);

        int databaseSizeBeforeUpdate = compraInsumoRepository.findAll().size();

        // Update the compraInsumo
        CompraInsumo updatedCompraInsumo = compraInsumoRepository.findById(compraInsumo.getId()).get();
        // Disconnect from session so that the updates on updatedCompraInsumo are not directly saved in db
        em.detach(updatedCompraInsumo);
        updatedCompraInsumo
            .cantidad(UPDATED_CANTIDAD)
            .unidad(UPDATED_UNIDAD)
            .descripcion(UPDATED_DESCRIPCION)
            .costoTotal(UPDATED_COSTO_TOTAL);
        CompraInsumoDTO compraInsumoDTO = compraInsumoMapper.toDto(updatedCompraInsumo);

        restCompraInsumoMockMvc.perform(put("/api/compra-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraInsumoDTO)))
            .andExpect(status().isOk());

        // Validate the CompraInsumo in the database
        List<CompraInsumo> compraInsumoList = compraInsumoRepository.findAll();
        assertThat(compraInsumoList).hasSize(databaseSizeBeforeUpdate);
        CompraInsumo testCompraInsumo = compraInsumoList.get(compraInsumoList.size() - 1);
        assertThat(testCompraInsumo.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testCompraInsumo.getUnidad()).isEqualTo(UPDATED_UNIDAD);
        assertThat(testCompraInsumo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCompraInsumo.getCostoTotal()).isEqualTo(UPDATED_COSTO_TOTAL);

        // Validate the CompraInsumo in Elasticsearch
        verify(mockCompraInsumoSearchRepository, times(1)).save(testCompraInsumo);
    }

    @Test
    @Transactional
    public void updateNonExistingCompraInsumo() throws Exception {
        int databaseSizeBeforeUpdate = compraInsumoRepository.findAll().size();

        // Create the CompraInsumo
        CompraInsumoDTO compraInsumoDTO = compraInsumoMapper.toDto(compraInsumo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompraInsumoMockMvc.perform(put("/api/compra-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraInsumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompraInsumo in the database
        List<CompraInsumo> compraInsumoList = compraInsumoRepository.findAll();
        assertThat(compraInsumoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CompraInsumo in Elasticsearch
        verify(mockCompraInsumoSearchRepository, times(0)).save(compraInsumo);
    }

    @Test
    @Transactional
    public void deleteCompraInsumo() throws Exception {
        // Initialize the database
        compraInsumoRepository.saveAndFlush(compraInsumo);

        int databaseSizeBeforeDelete = compraInsumoRepository.findAll().size();

        // Get the compraInsumo
        restCompraInsumoMockMvc.perform(delete("/api/compra-insumos/{id}", compraInsumo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompraInsumo> compraInsumoList = compraInsumoRepository.findAll();
        assertThat(compraInsumoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CompraInsumo in Elasticsearch
        verify(mockCompraInsumoSearchRepository, times(1)).deleteById(compraInsumo.getId());
    }

    @Test
    @Transactional
    public void searchCompraInsumo() throws Exception {
        // Initialize the database
        compraInsumoRepository.saveAndFlush(compraInsumo);
        when(mockCompraInsumoSearchRepository.search(queryStringQuery("id:" + compraInsumo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(compraInsumo), PageRequest.of(0, 1), 1));
        // Search the compraInsumo
        restCompraInsumoMockMvc.perform(get("/api/_search/compra-insumos?query=id:" + compraInsumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compraInsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())))
            .andExpect(jsonPath("$.[*].unidad").value(hasItem(DEFAULT_UNIDAD.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].costoTotal").value(hasItem(DEFAULT_COSTO_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraInsumo.class);
        CompraInsumo compraInsumo1 = new CompraInsumo();
        compraInsumo1.setId(1L);
        CompraInsumo compraInsumo2 = new CompraInsumo();
        compraInsumo2.setId(compraInsumo1.getId());
        assertThat(compraInsumo1).isEqualTo(compraInsumo2);
        compraInsumo2.setId(2L);
        assertThat(compraInsumo1).isNotEqualTo(compraInsumo2);
        compraInsumo1.setId(null);
        assertThat(compraInsumo1).isNotEqualTo(compraInsumo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraInsumoDTO.class);
        CompraInsumoDTO compraInsumoDTO1 = new CompraInsumoDTO();
        compraInsumoDTO1.setId(1L);
        CompraInsumoDTO compraInsumoDTO2 = new CompraInsumoDTO();
        assertThat(compraInsumoDTO1).isNotEqualTo(compraInsumoDTO2);
        compraInsumoDTO2.setId(compraInsumoDTO1.getId());
        assertThat(compraInsumoDTO1).isEqualTo(compraInsumoDTO2);
        compraInsumoDTO2.setId(2L);
        assertThat(compraInsumoDTO1).isNotEqualTo(compraInsumoDTO2);
        compraInsumoDTO1.setId(null);
        assertThat(compraInsumoDTO1).isNotEqualTo(compraInsumoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(compraInsumoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(compraInsumoMapper.fromId(null)).isNull();
    }
}
