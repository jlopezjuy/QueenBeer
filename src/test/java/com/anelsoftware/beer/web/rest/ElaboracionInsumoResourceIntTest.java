package com.anelsoftware.beer.web.rest;

import com.anelsoftware.beer.QueenBeerApp;

import com.anelsoftware.beer.domain.ElaboracionInsumo;
import com.anelsoftware.beer.repository.ElaboracionInsumoRepository;
import com.anelsoftware.beer.repository.search.ElaboracionInsumoSearchRepository;
import com.anelsoftware.beer.service.ElaboracionInsumoService;
import com.anelsoftware.beer.service.dto.ElaboracionInsumoDTO;
import com.anelsoftware.beer.service.mapper.ElaboracionInsumoMapper;
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

import com.anelsoftware.beer.domain.enumeration.UsoLupulo;
import com.anelsoftware.beer.domain.enumeration.ModoLupulo;
/**
 * Test class for the ElaboracionInsumoResource REST controller.
 *
 * @see ElaboracionInsumoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueenBeerApp.class)
public class ElaboracionInsumoResourceIntTest {

    private static final BigDecimal DEFAULT_EXTRACTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTRACTO = new BigDecimal(2);

    private static final Long DEFAULT_COLOR = 1L;
    private static final Long UPDATED_COLOR = 2L;

    private static final BigDecimal DEFAULT_PORCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PORCENTAGE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_KILOGRAMOS = new BigDecimal(1);
    private static final BigDecimal UPDATED_KILOGRAMOS = new BigDecimal(2);

    private static final UsoLupulo DEFAULT_USO = UsoLupulo.BOIL;
    private static final UsoLupulo UPDATED_USO = UsoLupulo.FIRST_WORT;

    private static final BigDecimal DEFAULT_ALPHA = new BigDecimal(1);
    private static final BigDecimal UPDATED_ALPHA = new BigDecimal(2);

    private static final ModoLupulo DEFAULT_MODO = ModoLupulo.PELET;
    private static final ModoLupulo UPDATED_MODO = ModoLupulo.FLOR;

    private static final BigDecimal DEFAULT_GRAMOS = new BigDecimal(1);
    private static final BigDecimal UPDATED_GRAMOS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_GL = new BigDecimal(1);
    private static final BigDecimal UPDATED_GL = new BigDecimal(2);

    private static final Long DEFAULT_TIEMPO = 1L;
    private static final Long UPDATED_TIEMPO = 2L;

    private static final BigDecimal DEFAULT_IBU = new BigDecimal(1);
    private static final BigDecimal UPDATED_IBU = new BigDecimal(2);

    @Autowired
    private ElaboracionInsumoRepository elaboracionInsumoRepository;

    @Autowired
    private ElaboracionInsumoMapper elaboracionInsumoMapper;
    
    @Autowired
    private ElaboracionInsumoService elaboracionInsumoService;

    /**
     * This repository is mocked in the com.anelsoftware.beer.repository.search test package.
     *
     * @see com.anelsoftware.beer.repository.search.ElaboracionInsumoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ElaboracionInsumoSearchRepository mockElaboracionInsumoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restElaboracionInsumoMockMvc;

    private ElaboracionInsumo elaboracionInsumo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ElaboracionInsumoResource elaboracionInsumoResource = new ElaboracionInsumoResource(elaboracionInsumoService);
        this.restElaboracionInsumoMockMvc = MockMvcBuilders.standaloneSetup(elaboracionInsumoResource)
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
    public static ElaboracionInsumo createEntity(EntityManager em) {
        ElaboracionInsumo elaboracionInsumo = new ElaboracionInsumo()
            .extracto(DEFAULT_EXTRACTO)
            .color(DEFAULT_COLOR)
            .porcentage(DEFAULT_PORCENTAGE)
            .kilogramos(DEFAULT_KILOGRAMOS)
            .uso(DEFAULT_USO)
            .alpha(DEFAULT_ALPHA)
            .modo(DEFAULT_MODO)
            .gramos(DEFAULT_GRAMOS)
            .gl(DEFAULT_GL)
            .tiempo(DEFAULT_TIEMPO)
            .ibu(DEFAULT_IBU);
        return elaboracionInsumo;
    }

    @Before
    public void initTest() {
        elaboracionInsumo = createEntity(em);
    }

    @Test
    @Transactional
    public void createElaboracionInsumo() throws Exception {
        int databaseSizeBeforeCreate = elaboracionInsumoRepository.findAll().size();

        // Create the ElaboracionInsumo
        ElaboracionInsumoDTO elaboracionInsumoDTO = elaboracionInsumoMapper.toDto(elaboracionInsumo);
        restElaboracionInsumoMockMvc.perform(post("/api/elaboracion-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elaboracionInsumoDTO)))
            .andExpect(status().isCreated());

        // Validate the ElaboracionInsumo in the database
        List<ElaboracionInsumo> elaboracionInsumoList = elaboracionInsumoRepository.findAll();
        assertThat(elaboracionInsumoList).hasSize(databaseSizeBeforeCreate + 1);
        ElaboracionInsumo testElaboracionInsumo = elaboracionInsumoList.get(elaboracionInsumoList.size() - 1);
        assertThat(testElaboracionInsumo.getExtracto()).isEqualTo(DEFAULT_EXTRACTO);
        assertThat(testElaboracionInsumo.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testElaboracionInsumo.getPorcentage()).isEqualTo(DEFAULT_PORCENTAGE);
        assertThat(testElaboracionInsumo.getKilogramos()).isEqualTo(DEFAULT_KILOGRAMOS);
        assertThat(testElaboracionInsumo.getUso()).isEqualTo(DEFAULT_USO);
        assertThat(testElaboracionInsumo.getAlpha()).isEqualTo(DEFAULT_ALPHA);
        assertThat(testElaboracionInsumo.getModo()).isEqualTo(DEFAULT_MODO);
        assertThat(testElaboracionInsumo.getGramos()).isEqualTo(DEFAULT_GRAMOS);
        assertThat(testElaboracionInsumo.getGl()).isEqualTo(DEFAULT_GL);
        assertThat(testElaboracionInsumo.getTiempo()).isEqualTo(DEFAULT_TIEMPO);
        assertThat(testElaboracionInsumo.getIbu()).isEqualTo(DEFAULT_IBU);

        // Validate the ElaboracionInsumo in Elasticsearch
        verify(mockElaboracionInsumoSearchRepository, times(1)).save(testElaboracionInsumo);
    }

    @Test
    @Transactional
    public void createElaboracionInsumoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = elaboracionInsumoRepository.findAll().size();

        // Create the ElaboracionInsumo with an existing ID
        elaboracionInsumo.setId(1L);
        ElaboracionInsumoDTO elaboracionInsumoDTO = elaboracionInsumoMapper.toDto(elaboracionInsumo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElaboracionInsumoMockMvc.perform(post("/api/elaboracion-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elaboracionInsumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ElaboracionInsumo in the database
        List<ElaboracionInsumo> elaboracionInsumoList = elaboracionInsumoRepository.findAll();
        assertThat(elaboracionInsumoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ElaboracionInsumo in Elasticsearch
        verify(mockElaboracionInsumoSearchRepository, times(0)).save(elaboracionInsumo);
    }

    @Test
    @Transactional
    public void getAllElaboracionInsumos() throws Exception {
        // Initialize the database
        elaboracionInsumoRepository.saveAndFlush(elaboracionInsumo);

        // Get all the elaboracionInsumoList
        restElaboracionInsumoMockMvc.perform(get("/api/elaboracion-insumos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elaboracionInsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].extracto").value(hasItem(DEFAULT_EXTRACTO.intValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.intValue())))
            .andExpect(jsonPath("$.[*].porcentage").value(hasItem(DEFAULT_PORCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].kilogramos").value(hasItem(DEFAULT_KILOGRAMOS.intValue())))
            .andExpect(jsonPath("$.[*].uso").value(hasItem(DEFAULT_USO.toString())))
            .andExpect(jsonPath("$.[*].alpha").value(hasItem(DEFAULT_ALPHA.intValue())))
            .andExpect(jsonPath("$.[*].modo").value(hasItem(DEFAULT_MODO.toString())))
            .andExpect(jsonPath("$.[*].gramos").value(hasItem(DEFAULT_GRAMOS.intValue())))
            .andExpect(jsonPath("$.[*].gl").value(hasItem(DEFAULT_GL.intValue())))
            .andExpect(jsonPath("$.[*].tiempo").value(hasItem(DEFAULT_TIEMPO.intValue())))
            .andExpect(jsonPath("$.[*].ibu").value(hasItem(DEFAULT_IBU.intValue())));
    }
    
    @Test
    @Transactional
    public void getElaboracionInsumo() throws Exception {
        // Initialize the database
        elaboracionInsumoRepository.saveAndFlush(elaboracionInsumo);

        // Get the elaboracionInsumo
        restElaboracionInsumoMockMvc.perform(get("/api/elaboracion-insumos/{id}", elaboracionInsumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(elaboracionInsumo.getId().intValue()))
            .andExpect(jsonPath("$.extracto").value(DEFAULT_EXTRACTO.intValue()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.intValue()))
            .andExpect(jsonPath("$.porcentage").value(DEFAULT_PORCENTAGE.intValue()))
            .andExpect(jsonPath("$.kilogramos").value(DEFAULT_KILOGRAMOS.intValue()))
            .andExpect(jsonPath("$.uso").value(DEFAULT_USO.toString()))
            .andExpect(jsonPath("$.alpha").value(DEFAULT_ALPHA.intValue()))
            .andExpect(jsonPath("$.modo").value(DEFAULT_MODO.toString()))
            .andExpect(jsonPath("$.gramos").value(DEFAULT_GRAMOS.intValue()))
            .andExpect(jsonPath("$.gl").value(DEFAULT_GL.intValue()))
            .andExpect(jsonPath("$.tiempo").value(DEFAULT_TIEMPO.intValue()))
            .andExpect(jsonPath("$.ibu").value(DEFAULT_IBU.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingElaboracionInsumo() throws Exception {
        // Get the elaboracionInsumo
        restElaboracionInsumoMockMvc.perform(get("/api/elaboracion-insumos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElaboracionInsumo() throws Exception {
        // Initialize the database
        elaboracionInsumoRepository.saveAndFlush(elaboracionInsumo);

        int databaseSizeBeforeUpdate = elaboracionInsumoRepository.findAll().size();

        // Update the elaboracionInsumo
        ElaboracionInsumo updatedElaboracionInsumo = elaboracionInsumoRepository.findById(elaboracionInsumo.getId()).get();
        // Disconnect from session so that the updates on updatedElaboracionInsumo are not directly saved in db
        em.detach(updatedElaboracionInsumo);
        updatedElaboracionInsumo
            .extracto(UPDATED_EXTRACTO)
            .color(UPDATED_COLOR)
            .porcentage(UPDATED_PORCENTAGE)
            .kilogramos(UPDATED_KILOGRAMOS)
            .uso(UPDATED_USO)
            .alpha(UPDATED_ALPHA)
            .modo(UPDATED_MODO)
            .gramos(UPDATED_GRAMOS)
            .gl(UPDATED_GL)
            .tiempo(UPDATED_TIEMPO)
            .ibu(UPDATED_IBU);
        ElaboracionInsumoDTO elaboracionInsumoDTO = elaboracionInsumoMapper.toDto(updatedElaboracionInsumo);

        restElaboracionInsumoMockMvc.perform(put("/api/elaboracion-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elaboracionInsumoDTO)))
            .andExpect(status().isOk());

        // Validate the ElaboracionInsumo in the database
        List<ElaboracionInsumo> elaboracionInsumoList = elaboracionInsumoRepository.findAll();
        assertThat(elaboracionInsumoList).hasSize(databaseSizeBeforeUpdate);
        ElaboracionInsumo testElaboracionInsumo = elaboracionInsumoList.get(elaboracionInsumoList.size() - 1);
        assertThat(testElaboracionInsumo.getExtracto()).isEqualTo(UPDATED_EXTRACTO);
        assertThat(testElaboracionInsumo.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testElaboracionInsumo.getPorcentage()).isEqualTo(UPDATED_PORCENTAGE);
        assertThat(testElaboracionInsumo.getKilogramos()).isEqualTo(UPDATED_KILOGRAMOS);
        assertThat(testElaboracionInsumo.getUso()).isEqualTo(UPDATED_USO);
        assertThat(testElaboracionInsumo.getAlpha()).isEqualTo(UPDATED_ALPHA);
        assertThat(testElaboracionInsumo.getModo()).isEqualTo(UPDATED_MODO);
        assertThat(testElaboracionInsumo.getGramos()).isEqualTo(UPDATED_GRAMOS);
        assertThat(testElaboracionInsumo.getGl()).isEqualTo(UPDATED_GL);
        assertThat(testElaboracionInsumo.getTiempo()).isEqualTo(UPDATED_TIEMPO);
        assertThat(testElaboracionInsumo.getIbu()).isEqualTo(UPDATED_IBU);

        // Validate the ElaboracionInsumo in Elasticsearch
        verify(mockElaboracionInsumoSearchRepository, times(1)).save(testElaboracionInsumo);
    }

    @Test
    @Transactional
    public void updateNonExistingElaboracionInsumo() throws Exception {
        int databaseSizeBeforeUpdate = elaboracionInsumoRepository.findAll().size();

        // Create the ElaboracionInsumo
        ElaboracionInsumoDTO elaboracionInsumoDTO = elaboracionInsumoMapper.toDto(elaboracionInsumo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElaboracionInsumoMockMvc.perform(put("/api/elaboracion-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elaboracionInsumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ElaboracionInsumo in the database
        List<ElaboracionInsumo> elaboracionInsumoList = elaboracionInsumoRepository.findAll();
        assertThat(elaboracionInsumoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ElaboracionInsumo in Elasticsearch
        verify(mockElaboracionInsumoSearchRepository, times(0)).save(elaboracionInsumo);
    }

    @Test
    @Transactional
    public void deleteElaboracionInsumo() throws Exception {
        // Initialize the database
        elaboracionInsumoRepository.saveAndFlush(elaboracionInsumo);

        int databaseSizeBeforeDelete = elaboracionInsumoRepository.findAll().size();

        // Get the elaboracionInsumo
        restElaboracionInsumoMockMvc.perform(delete("/api/elaboracion-insumos/{id}", elaboracionInsumo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ElaboracionInsumo> elaboracionInsumoList = elaboracionInsumoRepository.findAll();
        assertThat(elaboracionInsumoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ElaboracionInsumo in Elasticsearch
        verify(mockElaboracionInsumoSearchRepository, times(1)).deleteById(elaboracionInsumo.getId());
    }

    @Test
    @Transactional
    public void searchElaboracionInsumo() throws Exception {
        // Initialize the database
        elaboracionInsumoRepository.saveAndFlush(elaboracionInsumo);
        when(mockElaboracionInsumoSearchRepository.search(queryStringQuery("id:" + elaboracionInsumo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(elaboracionInsumo), PageRequest.of(0, 1), 1));
        // Search the elaboracionInsumo
        restElaboracionInsumoMockMvc.perform(get("/api/_search/elaboracion-insumos?query=id:" + elaboracionInsumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elaboracionInsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].extracto").value(hasItem(DEFAULT_EXTRACTO.intValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.intValue())))
            .andExpect(jsonPath("$.[*].porcentage").value(hasItem(DEFAULT_PORCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].kilogramos").value(hasItem(DEFAULT_KILOGRAMOS.intValue())))
            .andExpect(jsonPath("$.[*].uso").value(hasItem(DEFAULT_USO.toString())))
            .andExpect(jsonPath("$.[*].alpha").value(hasItem(DEFAULT_ALPHA.intValue())))
            .andExpect(jsonPath("$.[*].modo").value(hasItem(DEFAULT_MODO.toString())))
            .andExpect(jsonPath("$.[*].gramos").value(hasItem(DEFAULT_GRAMOS.intValue())))
            .andExpect(jsonPath("$.[*].gl").value(hasItem(DEFAULT_GL.intValue())))
            .andExpect(jsonPath("$.[*].tiempo").value(hasItem(DEFAULT_TIEMPO.intValue())))
            .andExpect(jsonPath("$.[*].ibu").value(hasItem(DEFAULT_IBU.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElaboracionInsumo.class);
        ElaboracionInsumo elaboracionInsumo1 = new ElaboracionInsumo();
        elaboracionInsumo1.setId(1L);
        ElaboracionInsumo elaboracionInsumo2 = new ElaboracionInsumo();
        elaboracionInsumo2.setId(elaboracionInsumo1.getId());
        assertThat(elaboracionInsumo1).isEqualTo(elaboracionInsumo2);
        elaboracionInsumo2.setId(2L);
        assertThat(elaboracionInsumo1).isNotEqualTo(elaboracionInsumo2);
        elaboracionInsumo1.setId(null);
        assertThat(elaboracionInsumo1).isNotEqualTo(elaboracionInsumo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElaboracionInsumoDTO.class);
        ElaboracionInsumoDTO elaboracionInsumoDTO1 = new ElaboracionInsumoDTO();
        elaboracionInsumoDTO1.setId(1L);
        ElaboracionInsumoDTO elaboracionInsumoDTO2 = new ElaboracionInsumoDTO();
        assertThat(elaboracionInsumoDTO1).isNotEqualTo(elaboracionInsumoDTO2);
        elaboracionInsumoDTO2.setId(elaboracionInsumoDTO1.getId());
        assertThat(elaboracionInsumoDTO1).isEqualTo(elaboracionInsumoDTO2);
        elaboracionInsumoDTO2.setId(2L);
        assertThat(elaboracionInsumoDTO1).isNotEqualTo(elaboracionInsumoDTO2);
        elaboracionInsumoDTO1.setId(null);
        assertThat(elaboracionInsumoDTO1).isNotEqualTo(elaboracionInsumoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(elaboracionInsumoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(elaboracionInsumoMapper.fromId(null)).isNull();
    }
}
