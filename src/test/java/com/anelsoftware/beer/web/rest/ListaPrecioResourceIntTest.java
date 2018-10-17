package com.anelsoftware.beer.web.rest;

import com.anelsoftware.beer.QueenBeerApp;

import com.anelsoftware.beer.domain.ListaPrecio;
import com.anelsoftware.beer.repository.ListaPrecioRepository;
import com.anelsoftware.beer.repository.search.ListaPrecioSearchRepository;
import com.anelsoftware.beer.service.ListaPrecioService;
import com.anelsoftware.beer.service.dto.ListaPrecioDTO;
import com.anelsoftware.beer.service.mapper.ListaPrecioMapper;
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

/**
 * Test class for the ListaPrecioResource REST controller.
 *
 * @see ListaPrecioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueenBeerApp.class)
public class ListaPrecioResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PORCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PORCENTAGE = new BigDecimal(2);

    @Autowired
    private ListaPrecioRepository listaPrecioRepository;

    @Autowired
    private ListaPrecioMapper listaPrecioMapper;
    
    @Autowired
    private ListaPrecioService listaPrecioService;

    /**
     * This repository is mocked in the com.anelsoftware.beer.repository.search test package.
     *
     * @see com.anelsoftware.beer.repository.search.ListaPrecioSearchRepositoryMockConfiguration
     */
    @Autowired
    private ListaPrecioSearchRepository mockListaPrecioSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restListaPrecioMockMvc;

    private ListaPrecio listaPrecio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListaPrecioResource listaPrecioResource = new ListaPrecioResource(listaPrecioService);
        this.restListaPrecioMockMvc = MockMvcBuilders.standaloneSetup(listaPrecioResource)
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
    public static ListaPrecio createEntity(EntityManager em) {
        ListaPrecio listaPrecio = new ListaPrecio()
            .nombre(DEFAULT_NOMBRE)
            .porcentage(DEFAULT_PORCENTAGE);
        return listaPrecio;
    }

    @Before
    public void initTest() {
        listaPrecio = createEntity(em);
    }

    @Test
    @Transactional
    public void createListaPrecio() throws Exception {
        int databaseSizeBeforeCreate = listaPrecioRepository.findAll().size();

        // Create the ListaPrecio
        ListaPrecioDTO listaPrecioDTO = listaPrecioMapper.toDto(listaPrecio);
        restListaPrecioMockMvc.perform(post("/api/lista-precios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listaPrecioDTO)))
            .andExpect(status().isCreated());

        // Validate the ListaPrecio in the database
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeCreate + 1);
        ListaPrecio testListaPrecio = listaPrecioList.get(listaPrecioList.size() - 1);
        assertThat(testListaPrecio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testListaPrecio.getPorcentage()).isEqualTo(DEFAULT_PORCENTAGE);

        // Validate the ListaPrecio in Elasticsearch
        verify(mockListaPrecioSearchRepository, times(1)).save(testListaPrecio);
    }

    @Test
    @Transactional
    public void createListaPrecioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listaPrecioRepository.findAll().size();

        // Create the ListaPrecio with an existing ID
        listaPrecio.setId(1L);
        ListaPrecioDTO listaPrecioDTO = listaPrecioMapper.toDto(listaPrecio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListaPrecioMockMvc.perform(post("/api/lista-precios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listaPrecioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListaPrecio in the database
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeCreate);

        // Validate the ListaPrecio in Elasticsearch
        verify(mockListaPrecioSearchRepository, times(0)).save(listaPrecio);
    }

    @Test
    @Transactional
    public void getAllListaPrecios() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get all the listaPrecioList
        restListaPrecioMockMvc.perform(get("/api/lista-precios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listaPrecio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].porcentage").value(hasItem(DEFAULT_PORCENTAGE.intValue())));
    }
    
    @Test
    @Transactional
    public void getListaPrecio() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        // Get the listaPrecio
        restListaPrecioMockMvc.perform(get("/api/lista-precios/{id}", listaPrecio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listaPrecio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.porcentage").value(DEFAULT_PORCENTAGE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingListaPrecio() throws Exception {
        // Get the listaPrecio
        restListaPrecioMockMvc.perform(get("/api/lista-precios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListaPrecio() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        int databaseSizeBeforeUpdate = listaPrecioRepository.findAll().size();

        // Update the listaPrecio
        ListaPrecio updatedListaPrecio = listaPrecioRepository.findById(listaPrecio.getId()).get();
        // Disconnect from session so that the updates on updatedListaPrecio are not directly saved in db
        em.detach(updatedListaPrecio);
        updatedListaPrecio
            .nombre(UPDATED_NOMBRE)
            .porcentage(UPDATED_PORCENTAGE);
        ListaPrecioDTO listaPrecioDTO = listaPrecioMapper.toDto(updatedListaPrecio);

        restListaPrecioMockMvc.perform(put("/api/lista-precios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listaPrecioDTO)))
            .andExpect(status().isOk());

        // Validate the ListaPrecio in the database
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeUpdate);
        ListaPrecio testListaPrecio = listaPrecioList.get(listaPrecioList.size() - 1);
        assertThat(testListaPrecio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testListaPrecio.getPorcentage()).isEqualTo(UPDATED_PORCENTAGE);

        // Validate the ListaPrecio in Elasticsearch
        verify(mockListaPrecioSearchRepository, times(1)).save(testListaPrecio);
    }

    @Test
    @Transactional
    public void updateNonExistingListaPrecio() throws Exception {
        int databaseSizeBeforeUpdate = listaPrecioRepository.findAll().size();

        // Create the ListaPrecio
        ListaPrecioDTO listaPrecioDTO = listaPrecioMapper.toDto(listaPrecio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListaPrecioMockMvc.perform(put("/api/lista-precios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listaPrecioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListaPrecio in the database
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ListaPrecio in Elasticsearch
        verify(mockListaPrecioSearchRepository, times(0)).save(listaPrecio);
    }

    @Test
    @Transactional
    public void deleteListaPrecio() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);

        int databaseSizeBeforeDelete = listaPrecioRepository.findAll().size();

        // Get the listaPrecio
        restListaPrecioMockMvc.perform(delete("/api/lista-precios/{id}", listaPrecio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ListaPrecio> listaPrecioList = listaPrecioRepository.findAll();
        assertThat(listaPrecioList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ListaPrecio in Elasticsearch
        verify(mockListaPrecioSearchRepository, times(1)).deleteById(listaPrecio.getId());
    }

    @Test
    @Transactional
    public void searchListaPrecio() throws Exception {
        // Initialize the database
        listaPrecioRepository.saveAndFlush(listaPrecio);
        when(mockListaPrecioSearchRepository.search(queryStringQuery("id:" + listaPrecio.getId())))
            .thenReturn(Collections.singletonList(listaPrecio));
        // Search the listaPrecio
        restListaPrecioMockMvc.perform(get("/api/_search/lista-precios?query=id:" + listaPrecio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listaPrecio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].porcentage").value(hasItem(DEFAULT_PORCENTAGE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListaPrecio.class);
        ListaPrecio listaPrecio1 = new ListaPrecio();
        listaPrecio1.setId(1L);
        ListaPrecio listaPrecio2 = new ListaPrecio();
        listaPrecio2.setId(listaPrecio1.getId());
        assertThat(listaPrecio1).isEqualTo(listaPrecio2);
        listaPrecio2.setId(2L);
        assertThat(listaPrecio1).isNotEqualTo(listaPrecio2);
        listaPrecio1.setId(null);
        assertThat(listaPrecio1).isNotEqualTo(listaPrecio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListaPrecioDTO.class);
        ListaPrecioDTO listaPrecioDTO1 = new ListaPrecioDTO();
        listaPrecioDTO1.setId(1L);
        ListaPrecioDTO listaPrecioDTO2 = new ListaPrecioDTO();
        assertThat(listaPrecioDTO1).isNotEqualTo(listaPrecioDTO2);
        listaPrecioDTO2.setId(listaPrecioDTO1.getId());
        assertThat(listaPrecioDTO1).isEqualTo(listaPrecioDTO2);
        listaPrecioDTO2.setId(2L);
        assertThat(listaPrecioDTO1).isNotEqualTo(listaPrecioDTO2);
        listaPrecioDTO1.setId(null);
        assertThat(listaPrecioDTO1).isNotEqualTo(listaPrecioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(listaPrecioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(listaPrecioMapper.fromId(null)).isNull();
    }
}
