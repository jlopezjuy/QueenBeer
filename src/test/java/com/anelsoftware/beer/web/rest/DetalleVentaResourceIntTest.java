package com.anelsoftware.beer.web.rest;

import com.anelsoftware.beer.QueenBeerApp;

import com.anelsoftware.beer.domain.DetalleVenta;
import com.anelsoftware.beer.repository.DetalleVentaRepository;
import com.anelsoftware.beer.repository.search.DetalleVentaSearchRepository;
import com.anelsoftware.beer.service.DetalleVentaService;
import com.anelsoftware.beer.service.dto.DetalleVentaDTO;
import com.anelsoftware.beer.service.mapper.DetalleVentaMapper;
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
 * Test class for the DetalleVentaResource REST controller.
 *
 * @see DetalleVentaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueenBeerApp.class)
public class DetalleVentaResourceIntTest {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private DetalleVentaMapper detalleVentaMapper;

    @Autowired
    private DetalleVentaService detalleVentaService;

    /**
     * This repository is mocked in the com.anelsoftware.beer.repository.search test package.
     *
     * @see com.anelsoftware.beer.repository.search.DetalleVentaSearchRepositoryMockConfiguration
     */
    @Autowired
    private DetalleVentaSearchRepository mockDetalleVentaSearchRepository;

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

    private MockMvc restDetalleVentaMockMvc;

    private DetalleVenta detalleVenta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetalleVentaResource detalleVentaResource = new DetalleVentaResource(detalleVentaService);
        this.restDetalleVentaMockMvc = MockMvcBuilders.standaloneSetup(detalleVentaResource)
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
    public static DetalleVenta createEntity(EntityManager em) {
        DetalleVenta detalleVenta = new DetalleVenta();
        return detalleVenta;
    }

    @Before
    public void initTest() {
        detalleVenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetalleVenta() throws Exception {
        int databaseSizeBeforeCreate = detalleVentaRepository.findAll().size();

        // Create the DetalleVenta
        DetalleVentaDTO detalleVentaDTO = detalleVentaMapper.toDto(detalleVenta);
        restDetalleVentaMockMvc.perform(post("/api/detalle-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleVentaDTO)))
            .andExpect(status().isCreated());

        // Validate the DetalleVenta in the database
        List<DetalleVenta> detalleVentaList = detalleVentaRepository.findAll();
        assertThat(detalleVentaList).hasSize(databaseSizeBeforeCreate + 1);
        DetalleVenta testDetalleVenta = detalleVentaList.get(detalleVentaList.size() - 1);

        // Validate the DetalleVenta in Elasticsearch
        verify(mockDetalleVentaSearchRepository, times(1)).save(testDetalleVenta);
    }

    @Test
    @Transactional
    public void createDetalleVentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detalleVentaRepository.findAll().size();

        // Create the DetalleVenta with an existing ID
        detalleVenta.setId(1L);
        DetalleVentaDTO detalleVentaDTO = detalleVentaMapper.toDto(detalleVenta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalleVentaMockMvc.perform(post("/api/detalle-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleVentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleVenta in the database
        List<DetalleVenta> detalleVentaList = detalleVentaRepository.findAll();
        assertThat(detalleVentaList).hasSize(databaseSizeBeforeCreate);

        // Validate the DetalleVenta in Elasticsearch
        verify(mockDetalleVentaSearchRepository, times(0)).save(detalleVenta);
    }

    @Test
    @Transactional
    public void getAllDetalleVentas() throws Exception {
        // Initialize the database
        detalleVentaRepository.saveAndFlush(detalleVenta);

        // Get all the detalleVentaList
        restDetalleVentaMockMvc.perform(get("/api/detalle-ventas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleVenta.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDetalleVenta() throws Exception {
        // Initialize the database
        detalleVentaRepository.saveAndFlush(detalleVenta);

        // Get the detalleVenta
        restDetalleVentaMockMvc.perform(get("/api/detalle-ventas/{id}", detalleVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detalleVenta.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDetalleVenta() throws Exception {
        // Get the detalleVenta
        restDetalleVentaMockMvc.perform(get("/api/detalle-ventas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetalleVenta() throws Exception {
        // Initialize the database
        detalleVentaRepository.saveAndFlush(detalleVenta);

        int databaseSizeBeforeUpdate = detalleVentaRepository.findAll().size();

        // Update the detalleVenta
        DetalleVenta updatedDetalleVenta = detalleVentaRepository.findById(detalleVenta.getId()).get();
        // Disconnect from session so that the updates on updatedDetalleVenta are not directly saved in db
        em.detach(updatedDetalleVenta);
        DetalleVentaDTO detalleVentaDTO = detalleVentaMapper.toDto(updatedDetalleVenta);

        restDetalleVentaMockMvc.perform(put("/api/detalle-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleVentaDTO)))
            .andExpect(status().isOk());

        // Validate the DetalleVenta in the database
        List<DetalleVenta> detalleVentaList = detalleVentaRepository.findAll();
        assertThat(detalleVentaList).hasSize(databaseSizeBeforeUpdate);
        DetalleVenta testDetalleVenta = detalleVentaList.get(detalleVentaList.size() - 1);

        // Validate the DetalleVenta in Elasticsearch
        verify(mockDetalleVentaSearchRepository, times(1)).save(testDetalleVenta);
    }

    @Test
    @Transactional
    public void updateNonExistingDetalleVenta() throws Exception {
        int databaseSizeBeforeUpdate = detalleVentaRepository.findAll().size();

        // Create the DetalleVenta
        DetalleVentaDTO detalleVentaDTO = detalleVentaMapper.toDto(detalleVenta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalleVentaMockMvc.perform(put("/api/detalle-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleVentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleVenta in the database
        List<DetalleVenta> detalleVentaList = detalleVentaRepository.findAll();
        assertThat(detalleVentaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DetalleVenta in Elasticsearch
        verify(mockDetalleVentaSearchRepository, times(0)).save(detalleVenta);
    }

    @Test
    @Transactional
    public void deleteDetalleVenta() throws Exception {
        // Initialize the database
        detalleVentaRepository.saveAndFlush(detalleVenta);

        int databaseSizeBeforeDelete = detalleVentaRepository.findAll().size();

        // Delete the detalleVenta
        restDetalleVentaMockMvc.perform(delete("/api/detalle-ventas/{id}", detalleVenta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DetalleVenta> detalleVentaList = detalleVentaRepository.findAll();
        assertThat(detalleVentaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DetalleVenta in Elasticsearch
        verify(mockDetalleVentaSearchRepository, times(1)).deleteById(detalleVenta.getId());
    }

    @Test
    @Transactional
    public void searchDetalleVenta() throws Exception {
        // Initialize the database
        detalleVentaRepository.saveAndFlush(detalleVenta);
        when(mockDetalleVentaSearchRepository.search(queryStringQuery("id:" + detalleVenta.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(detalleVenta), PageRequest.of(0, 1), 1));
        // Search the detalleVenta
        restDetalleVentaMockMvc.perform(get("/api/_search/detalle-ventas?query=id:" + detalleVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleVenta.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleVenta.class);
        DetalleVenta detalleVenta1 = new DetalleVenta();
        detalleVenta1.setId(1L);
        DetalleVenta detalleVenta2 = new DetalleVenta();
        detalleVenta2.setId(detalleVenta1.getId());
        assertThat(detalleVenta1).isEqualTo(detalleVenta2);
        detalleVenta2.setId(2L);
        assertThat(detalleVenta1).isNotEqualTo(detalleVenta2);
        detalleVenta1.setId(null);
        assertThat(detalleVenta1).isNotEqualTo(detalleVenta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleVentaDTO.class);
        DetalleVentaDTO detalleVentaDTO1 = new DetalleVentaDTO();
        detalleVentaDTO1.setId(1L);
        DetalleVentaDTO detalleVentaDTO2 = new DetalleVentaDTO();
        assertThat(detalleVentaDTO1).isNotEqualTo(detalleVentaDTO2);
        detalleVentaDTO2.setId(detalleVentaDTO1.getId());
        assertThat(detalleVentaDTO1).isEqualTo(detalleVentaDTO2);
        detalleVentaDTO2.setId(2L);
        assertThat(detalleVentaDTO1).isNotEqualTo(detalleVentaDTO2);
        detalleVentaDTO1.setId(null);
        assertThat(detalleVentaDTO1).isNotEqualTo(detalleVentaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(detalleVentaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(detalleVentaMapper.fromId(null)).isNull();
    }
}
