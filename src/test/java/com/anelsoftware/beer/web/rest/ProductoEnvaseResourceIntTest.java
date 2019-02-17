package com.anelsoftware.beer.web.rest;

import com.anelsoftware.beer.QueenBeerApp;

import com.anelsoftware.beer.domain.ProductoEnvase;
import com.anelsoftware.beer.repository.ProductoEnvaseRepository;
import com.anelsoftware.beer.repository.search.ProductoEnvaseSearchRepository;
import com.anelsoftware.beer.service.ProductoEnvaseService;
import com.anelsoftware.beer.service.dto.ProductoEnvaseDTO;
import com.anelsoftware.beer.service.mapper.ProductoEnvaseMapper;
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
 * Test class for the ProductoEnvaseResource REST controller.
 *
 * @see ProductoEnvaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueenBeerApp.class)
public class ProductoEnvaseResourceIntTest {

    @Autowired
    private ProductoEnvaseRepository productoEnvaseRepository;

    @Autowired
    private ProductoEnvaseMapper productoEnvaseMapper;

    @Autowired
    private ProductoEnvaseService productoEnvaseService;

    /**
     * This repository is mocked in the com.anelsoftware.beer.repository.search test package.
     *
     * @see com.anelsoftware.beer.repository.search.ProductoEnvaseSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductoEnvaseSearchRepository mockProductoEnvaseSearchRepository;

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

    private MockMvc restProductoEnvaseMockMvc;

    private ProductoEnvase productoEnvase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductoEnvaseResource productoEnvaseResource = new ProductoEnvaseResource(productoEnvaseService);
        this.restProductoEnvaseMockMvc = MockMvcBuilders.standaloneSetup(productoEnvaseResource)
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
    public static ProductoEnvase createEntity(EntityManager em) {
        ProductoEnvase productoEnvase = new ProductoEnvase();
        return productoEnvase;
    }

    @Before
    public void initTest() {
        productoEnvase = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductoEnvase() throws Exception {
        int databaseSizeBeforeCreate = productoEnvaseRepository.findAll().size();

        // Create the ProductoEnvase
        ProductoEnvaseDTO productoEnvaseDTO = productoEnvaseMapper.toDto(productoEnvase);
        restProductoEnvaseMockMvc.perform(post("/api/producto-envases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoEnvaseDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductoEnvase in the database
        List<ProductoEnvase> productoEnvaseList = productoEnvaseRepository.findAll();
        assertThat(productoEnvaseList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoEnvase testProductoEnvase = productoEnvaseList.get(productoEnvaseList.size() - 1);

        // Validate the ProductoEnvase in Elasticsearch
        verify(mockProductoEnvaseSearchRepository, times(1)).save(testProductoEnvase);
    }

    @Test
    @Transactional
    public void createProductoEnvaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoEnvaseRepository.findAll().size();

        // Create the ProductoEnvase with an existing ID
        productoEnvase.setId(1L);
        ProductoEnvaseDTO productoEnvaseDTO = productoEnvaseMapper.toDto(productoEnvase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoEnvaseMockMvc.perform(post("/api/producto-envases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoEnvaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoEnvase in the database
        List<ProductoEnvase> productoEnvaseList = productoEnvaseRepository.findAll();
        assertThat(productoEnvaseList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProductoEnvase in Elasticsearch
        verify(mockProductoEnvaseSearchRepository, times(0)).save(productoEnvase);
    }

    @Test
    @Transactional
    public void getAllProductoEnvases() throws Exception {
        // Initialize the database
        productoEnvaseRepository.saveAndFlush(productoEnvase);

        // Get all the productoEnvaseList
        restProductoEnvaseMockMvc.perform(get("/api/producto-envases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoEnvase.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getProductoEnvase() throws Exception {
        // Initialize the database
        productoEnvaseRepository.saveAndFlush(productoEnvase);

        // Get the productoEnvase
        restProductoEnvaseMockMvc.perform(get("/api/producto-envases/{id}", productoEnvase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productoEnvase.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductoEnvase() throws Exception {
        // Get the productoEnvase
        restProductoEnvaseMockMvc.perform(get("/api/producto-envases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductoEnvase() throws Exception {
        // Initialize the database
        productoEnvaseRepository.saveAndFlush(productoEnvase);

        int databaseSizeBeforeUpdate = productoEnvaseRepository.findAll().size();

        // Update the productoEnvase
        ProductoEnvase updatedProductoEnvase = productoEnvaseRepository.findById(productoEnvase.getId()).get();
        // Disconnect from session so that the updates on updatedProductoEnvase are not directly saved in db
        em.detach(updatedProductoEnvase);
        ProductoEnvaseDTO productoEnvaseDTO = productoEnvaseMapper.toDto(updatedProductoEnvase);

        restProductoEnvaseMockMvc.perform(put("/api/producto-envases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoEnvaseDTO)))
            .andExpect(status().isOk());

        // Validate the ProductoEnvase in the database
        List<ProductoEnvase> productoEnvaseList = productoEnvaseRepository.findAll();
        assertThat(productoEnvaseList).hasSize(databaseSizeBeforeUpdate);
        ProductoEnvase testProductoEnvase = productoEnvaseList.get(productoEnvaseList.size() - 1);

        // Validate the ProductoEnvase in Elasticsearch
        verify(mockProductoEnvaseSearchRepository, times(1)).save(testProductoEnvase);
    }

    @Test
    @Transactional
    public void updateNonExistingProductoEnvase() throws Exception {
        int databaseSizeBeforeUpdate = productoEnvaseRepository.findAll().size();

        // Create the ProductoEnvase
        ProductoEnvaseDTO productoEnvaseDTO = productoEnvaseMapper.toDto(productoEnvase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoEnvaseMockMvc.perform(put("/api/producto-envases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoEnvaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoEnvase in the database
        List<ProductoEnvase> productoEnvaseList = productoEnvaseRepository.findAll();
        assertThat(productoEnvaseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProductoEnvase in Elasticsearch
        verify(mockProductoEnvaseSearchRepository, times(0)).save(productoEnvase);
    }

    @Test
    @Transactional
    public void deleteProductoEnvase() throws Exception {
        // Initialize the database
        productoEnvaseRepository.saveAndFlush(productoEnvase);

        int databaseSizeBeforeDelete = productoEnvaseRepository.findAll().size();

        // Delete the productoEnvase
        restProductoEnvaseMockMvc.perform(delete("/api/producto-envases/{id}", productoEnvase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductoEnvase> productoEnvaseList = productoEnvaseRepository.findAll();
        assertThat(productoEnvaseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProductoEnvase in Elasticsearch
        verify(mockProductoEnvaseSearchRepository, times(1)).deleteById(productoEnvase.getId());
    }

    @Test
    @Transactional
    public void searchProductoEnvase() throws Exception {
        // Initialize the database
        productoEnvaseRepository.saveAndFlush(productoEnvase);
        when(mockProductoEnvaseSearchRepository.search(queryStringQuery("id:" + productoEnvase.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(productoEnvase), PageRequest.of(0, 1), 1));
        // Search the productoEnvase
        restProductoEnvaseMockMvc.perform(get("/api/_search/producto-envases?query=id:" + productoEnvase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoEnvase.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoEnvase.class);
        ProductoEnvase productoEnvase1 = new ProductoEnvase();
        productoEnvase1.setId(1L);
        ProductoEnvase productoEnvase2 = new ProductoEnvase();
        productoEnvase2.setId(productoEnvase1.getId());
        assertThat(productoEnvase1).isEqualTo(productoEnvase2);
        productoEnvase2.setId(2L);
        assertThat(productoEnvase1).isNotEqualTo(productoEnvase2);
        productoEnvase1.setId(null);
        assertThat(productoEnvase1).isNotEqualTo(productoEnvase2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoEnvaseDTO.class);
        ProductoEnvaseDTO productoEnvaseDTO1 = new ProductoEnvaseDTO();
        productoEnvaseDTO1.setId(1L);
        ProductoEnvaseDTO productoEnvaseDTO2 = new ProductoEnvaseDTO();
        assertThat(productoEnvaseDTO1).isNotEqualTo(productoEnvaseDTO2);
        productoEnvaseDTO2.setId(productoEnvaseDTO1.getId());
        assertThat(productoEnvaseDTO1).isEqualTo(productoEnvaseDTO2);
        productoEnvaseDTO2.setId(2L);
        assertThat(productoEnvaseDTO1).isNotEqualTo(productoEnvaseDTO2);
        productoEnvaseDTO1.setId(null);
        assertThat(productoEnvaseDTO1).isNotEqualTo(productoEnvaseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productoEnvaseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productoEnvaseMapper.fromId(null)).isNull();
    }
}
