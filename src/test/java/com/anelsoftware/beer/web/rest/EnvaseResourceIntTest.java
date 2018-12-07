package com.anelsoftware.beer.web.rest;

import com.anelsoftware.beer.QueenBeerApp;

import com.anelsoftware.beer.domain.Envase;
import com.anelsoftware.beer.repository.EnvaseRepository;
import com.anelsoftware.beer.repository.search.EnvaseSearchRepository;
import com.anelsoftware.beer.service.EnvaseService;
import com.anelsoftware.beer.service.dto.EnvaseDTO;
import com.anelsoftware.beer.service.mapper.EnvaseMapper;
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

/**
 * Test class for the EnvaseResource REST controller.
 *
 * @see EnvaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueenBeerApp.class)
public class EnvaseResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRECIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO = new BigDecimal(2);

    @Autowired
    private EnvaseRepository envaseRepository;

    @Autowired
    private EnvaseMapper envaseMapper;

    @Autowired
    private EnvaseService envaseService;

    /**
     * This repository is mocked in the com.anelsoftware.beer.repository.search test package.
     *
     * @see com.anelsoftware.beer.repository.search.EnvaseSearchRepositoryMockConfiguration
     */
    @Autowired
    private EnvaseSearchRepository mockEnvaseSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEnvaseMockMvc;

    private Envase envase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnvaseResource envaseResource = new EnvaseResource(envaseService);
        this.restEnvaseMockMvc = MockMvcBuilders.standaloneSetup(envaseResource)
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
    public static Envase createEntity(EntityManager em) {
        Envase envase = new Envase()
            .nombre(DEFAULT_NOMBRE)
            .precio(DEFAULT_PRECIO);
        return envase;
    }

    @Before
    public void initTest() {
        envase = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnvase() throws Exception {
        int databaseSizeBeforeCreate = envaseRepository.findAll().size();

        // Create the Envase
        EnvaseDTO envaseDTO = envaseMapper.toDto(envase);
        restEnvaseMockMvc.perform(post("/api/envases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(envaseDTO)))
            .andExpect(status().isCreated());

        // Validate the Envase in the database
        List<Envase> envaseList = envaseRepository.findAll();
        assertThat(envaseList).hasSize(databaseSizeBeforeCreate + 1);
        Envase testEnvase = envaseList.get(envaseList.size() - 1);
        assertThat(testEnvase.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEnvase.getPrecio()).isEqualTo(DEFAULT_PRECIO);

        // Validate the Envase in Elasticsearch
        verify(mockEnvaseSearchRepository, times(1)).save(testEnvase);
    }

    @Test
    @Transactional
    public void createEnvaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = envaseRepository.findAll().size();

        // Create the Envase with an existing ID
        envase.setId(1L);
        EnvaseDTO envaseDTO = envaseMapper.toDto(envase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnvaseMockMvc.perform(post("/api/envases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(envaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Envase in the database
        List<Envase> envaseList = envaseRepository.findAll();
        assertThat(envaseList).hasSize(databaseSizeBeforeCreate);

        // Validate the Envase in Elasticsearch
        verify(mockEnvaseSearchRepository, times(0)).save(envase);
    }

    @Test
    @Transactional
    public void getAllEnvases() throws Exception {
        // Initialize the database
        envaseRepository.saveAndFlush(envase);

        // Get all the envaseList
        restEnvaseMockMvc.perform(get("/api/envases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(envase.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())));
    }
    
    @Test
    @Transactional
    public void getEnvase() throws Exception {
        // Initialize the database
        envaseRepository.saveAndFlush(envase);

        // Get the envase
        restEnvaseMockMvc.perform(get("/api/envases/{id}", envase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(envase.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEnvase() throws Exception {
        // Get the envase
        restEnvaseMockMvc.perform(get("/api/envases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnvase() throws Exception {
        // Initialize the database
        envaseRepository.saveAndFlush(envase);

        int databaseSizeBeforeUpdate = envaseRepository.findAll().size();

        // Update the envase
        Envase updatedEnvase = envaseRepository.findById(envase.getId()).get();
        // Disconnect from session so that the updates on updatedEnvase are not directly saved in db
        em.detach(updatedEnvase);
        updatedEnvase
            .nombre(UPDATED_NOMBRE)
            .precio(UPDATED_PRECIO);
        EnvaseDTO envaseDTO = envaseMapper.toDto(updatedEnvase);

        restEnvaseMockMvc.perform(put("/api/envases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(envaseDTO)))
            .andExpect(status().isOk());

        // Validate the Envase in the database
        List<Envase> envaseList = envaseRepository.findAll();
        assertThat(envaseList).hasSize(databaseSizeBeforeUpdate);
        Envase testEnvase = envaseList.get(envaseList.size() - 1);
        assertThat(testEnvase.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEnvase.getPrecio()).isEqualTo(UPDATED_PRECIO);

        // Validate the Envase in Elasticsearch
        verify(mockEnvaseSearchRepository, times(1)).save(testEnvase);
    }

    @Test
    @Transactional
    public void updateNonExistingEnvase() throws Exception {
        int databaseSizeBeforeUpdate = envaseRepository.findAll().size();

        // Create the Envase
        EnvaseDTO envaseDTO = envaseMapper.toDto(envase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnvaseMockMvc.perform(put("/api/envases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(envaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Envase in the database
        List<Envase> envaseList = envaseRepository.findAll();
        assertThat(envaseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Envase in Elasticsearch
        verify(mockEnvaseSearchRepository, times(0)).save(envase);
    }

    @Test
    @Transactional
    public void deleteEnvase() throws Exception {
        // Initialize the database
        envaseRepository.saveAndFlush(envase);

        int databaseSizeBeforeDelete = envaseRepository.findAll().size();

        // Get the envase
        restEnvaseMockMvc.perform(delete("/api/envases/{id}", envase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Envase> envaseList = envaseRepository.findAll();
        assertThat(envaseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Envase in Elasticsearch
        verify(mockEnvaseSearchRepository, times(1)).deleteById(envase.getId());
    }

    @Test
    @Transactional
    public void searchEnvase() throws Exception {
        // Initialize the database
        envaseRepository.saveAndFlush(envase);
        when(mockEnvaseSearchRepository.search(queryStringQuery("id:" + envase.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(envase), PageRequest.of(0, 1), 1));
        // Search the envase
        restEnvaseMockMvc.perform(get("/api/_search/envases?query=id:" + envase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(envase.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Envase.class);
        Envase envase1 = new Envase();
        envase1.setId(1L);
        Envase envase2 = new Envase();
        envase2.setId(envase1.getId());
        assertThat(envase1).isEqualTo(envase2);
        envase2.setId(2L);
        assertThat(envase1).isNotEqualTo(envase2);
        envase1.setId(null);
        assertThat(envase1).isNotEqualTo(envase2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnvaseDTO.class);
        EnvaseDTO envaseDTO1 = new EnvaseDTO();
        envaseDTO1.setId(1L);
        EnvaseDTO envaseDTO2 = new EnvaseDTO();
        assertThat(envaseDTO1).isNotEqualTo(envaseDTO2);
        envaseDTO2.setId(envaseDTO1.getId());
        assertThat(envaseDTO1).isEqualTo(envaseDTO2);
        envaseDTO2.setId(2L);
        assertThat(envaseDTO1).isNotEqualTo(envaseDTO2);
        envaseDTO1.setId(null);
        assertThat(envaseDTO1).isNotEqualTo(envaseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(envaseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(envaseMapper.fromId(null)).isNull();
    }
}
