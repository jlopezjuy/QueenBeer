package com.anelsoftware.beer.web.rest;

import com.anelsoftware.beer.QueenBeerApp;

import com.anelsoftware.beer.domain.Elaboracion;
import com.anelsoftware.beer.repository.ElaboracionRepository;
import com.anelsoftware.beer.repository.search.ElaboracionSearchRepository;
import com.anelsoftware.beer.service.ElaboracionService;
import com.anelsoftware.beer.service.dto.ElaboracionDTO;
import com.anelsoftware.beer.service.mapper.ElaboracionMapper;
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

import com.anelsoftware.beer.domain.enumeration.TipoMacerado;
/**
 * Test class for the ElaboracionResource REST controller.
 *
 * @see ElaboracionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueenBeerApp.class)
public class ElaboracionResourceIntTest {

    private static final String DEFAULT_LOTE = "AAAAAAAAAA";
    private static final String UPDATED_LOTE = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_ESTILO = "AAAAAAAAAA";
    private static final String UPDATED_ESTILO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_CHEQUEO = false;
    private static final Boolean UPDATED_CHEQUEO = true;

    private static final Boolean DEFAULT_LIMPIEZA = false;
    private static final Boolean UPDATED_LIMPIEZA = true;

    private static final Boolean DEFAULT_LIMPIEZA_OLLA = false;
    private static final Boolean UPDATED_LIMPIEZA_OLLA = true;

    private static final Boolean DEFAULT_LIMPIEZA_MANGUERA = false;
    private static final Boolean UPDATED_LIMPIEZA_MANGUERA = true;

    private static final TipoMacerado DEFAULT_MACERADO = TipoMacerado.INFUCION;
    private static final TipoMacerado UPDATED_MACERADO = TipoMacerado.ESCALONADO;

    private static final Instant DEFAULT_INICIO_MACERADO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INICIO_MACERADO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_INFUCION = false;
    private static final Boolean UPDATED_INFUCION = true;

    private static final Boolean DEFAULT_PRIMER_ESCALON = false;
    private static final Boolean UPDATED_PRIMER_ESCALON = true;

    private static final Boolean DEFAULT_SEGUNDO_ESCALON = false;
    private static final Boolean UPDATED_SEGUNDO_ESCALON = true;

    private static final Boolean DEFAULT_TERCER_ESCALON = false;
    private static final Boolean UPDATED_TERCER_ESCALON = true;

    private static final BigDecimal DEFAULT_LITRO_INICIAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_LITRO_INICIAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RELACION_MALTA_AGUA = new BigDecimal(1);
    private static final BigDecimal UPDATED_RELACION_MALTA_AGUA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LITRO_FALSO_FONDO = new BigDecimal(1);
    private static final BigDecimal UPDATED_LITRO_FALSO_FONDO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LITRO_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_LITRO_TOTAL = new BigDecimal(2);

    @Autowired
    private ElaboracionRepository elaboracionRepository;

    @Autowired
    private ElaboracionMapper elaboracionMapper;
    
    @Autowired
    private ElaboracionService elaboracionService;

    /**
     * This repository is mocked in the com.anelsoftware.beer.repository.search test package.
     *
     * @see com.anelsoftware.beer.repository.search.ElaboracionSearchRepositoryMockConfiguration
     */
    @Autowired
    private ElaboracionSearchRepository mockElaboracionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restElaboracionMockMvc;

    private Elaboracion elaboracion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ElaboracionResource elaboracionResource = new ElaboracionResource(elaboracionService);
        this.restElaboracionMockMvc = MockMvcBuilders.standaloneSetup(elaboracionResource)
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
    public static Elaboracion createEntity(EntityManager em) {
        Elaboracion elaboracion = new Elaboracion()
            .lote(DEFAULT_LOTE)
            .nombre(DEFAULT_NOMBRE)
            .estilo(DEFAULT_ESTILO)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .chequeo(DEFAULT_CHEQUEO)
            .limpieza(DEFAULT_LIMPIEZA)
            .limpiezaOlla(DEFAULT_LIMPIEZA_OLLA)
            .limpiezaManguera(DEFAULT_LIMPIEZA_MANGUERA)
            .macerado(DEFAULT_MACERADO)
            .inicioMacerado(DEFAULT_INICIO_MACERADO)
            .infucion(DEFAULT_INFUCION)
            .primerEscalon(DEFAULT_PRIMER_ESCALON)
            .segundoEscalon(DEFAULT_SEGUNDO_ESCALON)
            .tercerEscalon(DEFAULT_TERCER_ESCALON)
            .litroInicial(DEFAULT_LITRO_INICIAL)
            .relacionMaltaAgua(DEFAULT_RELACION_MALTA_AGUA)
            .litroFalsoFondo(DEFAULT_LITRO_FALSO_FONDO)
            .litroTotal(DEFAULT_LITRO_TOTAL);
        return elaboracion;
    }

    @Before
    public void initTest() {
        elaboracion = createEntity(em);
    }

    @Test
    @Transactional
    public void createElaboracion() throws Exception {
        int databaseSizeBeforeCreate = elaboracionRepository.findAll().size();

        // Create the Elaboracion
        ElaboracionDTO elaboracionDTO = elaboracionMapper.toDto(elaboracion);
        restElaboracionMockMvc.perform(post("/api/elaboracions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elaboracionDTO)))
            .andExpect(status().isCreated());

        // Validate the Elaboracion in the database
        List<Elaboracion> elaboracionList = elaboracionRepository.findAll();
        assertThat(elaboracionList).hasSize(databaseSizeBeforeCreate + 1);
        Elaboracion testElaboracion = elaboracionList.get(elaboracionList.size() - 1);
        assertThat(testElaboracion.getLote()).isEqualTo(DEFAULT_LOTE);
        assertThat(testElaboracion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testElaboracion.getEstilo()).isEqualTo(DEFAULT_ESTILO);
        assertThat(testElaboracion.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testElaboracion.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testElaboracion.isChequeo()).isEqualTo(DEFAULT_CHEQUEO);
        assertThat(testElaboracion.isLimpieza()).isEqualTo(DEFAULT_LIMPIEZA);
        assertThat(testElaboracion.isLimpiezaOlla()).isEqualTo(DEFAULT_LIMPIEZA_OLLA);
        assertThat(testElaboracion.isLimpiezaManguera()).isEqualTo(DEFAULT_LIMPIEZA_MANGUERA);
        assertThat(testElaboracion.getMacerado()).isEqualTo(DEFAULT_MACERADO);
        assertThat(testElaboracion.getInicioMacerado()).isEqualTo(DEFAULT_INICIO_MACERADO);
        assertThat(testElaboracion.isInfucion()).isEqualTo(DEFAULT_INFUCION);
        assertThat(testElaboracion.isPrimerEscalon()).isEqualTo(DEFAULT_PRIMER_ESCALON);
        assertThat(testElaboracion.isSegundoEscalon()).isEqualTo(DEFAULT_SEGUNDO_ESCALON);
        assertThat(testElaboracion.isTercerEscalon()).isEqualTo(DEFAULT_TERCER_ESCALON);
        assertThat(testElaboracion.getLitroInicial()).isEqualTo(DEFAULT_LITRO_INICIAL);
        assertThat(testElaboracion.getRelacionMaltaAgua()).isEqualTo(DEFAULT_RELACION_MALTA_AGUA);
        assertThat(testElaboracion.getLitroFalsoFondo()).isEqualTo(DEFAULT_LITRO_FALSO_FONDO);
        assertThat(testElaboracion.getLitroTotal()).isEqualTo(DEFAULT_LITRO_TOTAL);

        // Validate the Elaboracion in Elasticsearch
        verify(mockElaboracionSearchRepository, times(1)).save(testElaboracion);
    }

    @Test
    @Transactional
    public void createElaboracionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = elaboracionRepository.findAll().size();

        // Create the Elaboracion with an existing ID
        elaboracion.setId(1L);
        ElaboracionDTO elaboracionDTO = elaboracionMapper.toDto(elaboracion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElaboracionMockMvc.perform(post("/api/elaboracions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elaboracionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Elaboracion in the database
        List<Elaboracion> elaboracionList = elaboracionRepository.findAll();
        assertThat(elaboracionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Elaboracion in Elasticsearch
        verify(mockElaboracionSearchRepository, times(0)).save(elaboracion);
    }

    @Test
    @Transactional
    public void getAllElaboracions() throws Exception {
        // Initialize the database
        elaboracionRepository.saveAndFlush(elaboracion);

        // Get all the elaboracionList
        restElaboracionMockMvc.perform(get("/api/elaboracions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elaboracion.getId().intValue())))
            .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].estilo").value(hasItem(DEFAULT_ESTILO.toString())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].chequeo").value(hasItem(DEFAULT_CHEQUEO.booleanValue())))
            .andExpect(jsonPath("$.[*].limpieza").value(hasItem(DEFAULT_LIMPIEZA.booleanValue())))
            .andExpect(jsonPath("$.[*].limpiezaOlla").value(hasItem(DEFAULT_LIMPIEZA_OLLA.booleanValue())))
            .andExpect(jsonPath("$.[*].limpiezaManguera").value(hasItem(DEFAULT_LIMPIEZA_MANGUERA.booleanValue())))
            .andExpect(jsonPath("$.[*].macerado").value(hasItem(DEFAULT_MACERADO.toString())))
            .andExpect(jsonPath("$.[*].inicioMacerado").value(hasItem(DEFAULT_INICIO_MACERADO.toString())))
            .andExpect(jsonPath("$.[*].infucion").value(hasItem(DEFAULT_INFUCION.booleanValue())))
            .andExpect(jsonPath("$.[*].primerEscalon").value(hasItem(DEFAULT_PRIMER_ESCALON.booleanValue())))
            .andExpect(jsonPath("$.[*].segundoEscalon").value(hasItem(DEFAULT_SEGUNDO_ESCALON.booleanValue())))
            .andExpect(jsonPath("$.[*].tercerEscalon").value(hasItem(DEFAULT_TERCER_ESCALON.booleanValue())))
            .andExpect(jsonPath("$.[*].litroInicial").value(hasItem(DEFAULT_LITRO_INICIAL.intValue())))
            .andExpect(jsonPath("$.[*].relacionMaltaAgua").value(hasItem(DEFAULT_RELACION_MALTA_AGUA.intValue())))
            .andExpect(jsonPath("$.[*].litroFalsoFondo").value(hasItem(DEFAULT_LITRO_FALSO_FONDO.intValue())))
            .andExpect(jsonPath("$.[*].litroTotal").value(hasItem(DEFAULT_LITRO_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getElaboracion() throws Exception {
        // Initialize the database
        elaboracionRepository.saveAndFlush(elaboracion);

        // Get the elaboracion
        restElaboracionMockMvc.perform(get("/api/elaboracions/{id}", elaboracion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(elaboracion.getId().intValue()))
            .andExpect(jsonPath("$.lote").value(DEFAULT_LOTE.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.estilo").value(DEFAULT_ESTILO.toString()))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.chequeo").value(DEFAULT_CHEQUEO.booleanValue()))
            .andExpect(jsonPath("$.limpieza").value(DEFAULT_LIMPIEZA.booleanValue()))
            .andExpect(jsonPath("$.limpiezaOlla").value(DEFAULT_LIMPIEZA_OLLA.booleanValue()))
            .andExpect(jsonPath("$.limpiezaManguera").value(DEFAULT_LIMPIEZA_MANGUERA.booleanValue()))
            .andExpect(jsonPath("$.macerado").value(DEFAULT_MACERADO.toString()))
            .andExpect(jsonPath("$.inicioMacerado").value(DEFAULT_INICIO_MACERADO.toString()))
            .andExpect(jsonPath("$.infucion").value(DEFAULT_INFUCION.booleanValue()))
            .andExpect(jsonPath("$.primerEscalon").value(DEFAULT_PRIMER_ESCALON.booleanValue()))
            .andExpect(jsonPath("$.segundoEscalon").value(DEFAULT_SEGUNDO_ESCALON.booleanValue()))
            .andExpect(jsonPath("$.tercerEscalon").value(DEFAULT_TERCER_ESCALON.booleanValue()))
            .andExpect(jsonPath("$.litroInicial").value(DEFAULT_LITRO_INICIAL.intValue()))
            .andExpect(jsonPath("$.relacionMaltaAgua").value(DEFAULT_RELACION_MALTA_AGUA.intValue()))
            .andExpect(jsonPath("$.litroFalsoFondo").value(DEFAULT_LITRO_FALSO_FONDO.intValue()))
            .andExpect(jsonPath("$.litroTotal").value(DEFAULT_LITRO_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingElaboracion() throws Exception {
        // Get the elaboracion
        restElaboracionMockMvc.perform(get("/api/elaboracions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElaboracion() throws Exception {
        // Initialize the database
        elaboracionRepository.saveAndFlush(elaboracion);

        int databaseSizeBeforeUpdate = elaboracionRepository.findAll().size();

        // Update the elaboracion
        Elaboracion updatedElaboracion = elaboracionRepository.findById(elaboracion.getId()).get();
        // Disconnect from session so that the updates on updatedElaboracion are not directly saved in db
        em.detach(updatedElaboracion);
        updatedElaboracion
            .lote(UPDATED_LOTE)
            .nombre(UPDATED_NOMBRE)
            .estilo(UPDATED_ESTILO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .chequeo(UPDATED_CHEQUEO)
            .limpieza(UPDATED_LIMPIEZA)
            .limpiezaOlla(UPDATED_LIMPIEZA_OLLA)
            .limpiezaManguera(UPDATED_LIMPIEZA_MANGUERA)
            .macerado(UPDATED_MACERADO)
            .inicioMacerado(UPDATED_INICIO_MACERADO)
            .infucion(UPDATED_INFUCION)
            .primerEscalon(UPDATED_PRIMER_ESCALON)
            .segundoEscalon(UPDATED_SEGUNDO_ESCALON)
            .tercerEscalon(UPDATED_TERCER_ESCALON)
            .litroInicial(UPDATED_LITRO_INICIAL)
            .relacionMaltaAgua(UPDATED_RELACION_MALTA_AGUA)
            .litroFalsoFondo(UPDATED_LITRO_FALSO_FONDO)
            .litroTotal(UPDATED_LITRO_TOTAL);
        ElaboracionDTO elaboracionDTO = elaboracionMapper.toDto(updatedElaboracion);

        restElaboracionMockMvc.perform(put("/api/elaboracions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elaboracionDTO)))
            .andExpect(status().isOk());

        // Validate the Elaboracion in the database
        List<Elaboracion> elaboracionList = elaboracionRepository.findAll();
        assertThat(elaboracionList).hasSize(databaseSizeBeforeUpdate);
        Elaboracion testElaboracion = elaboracionList.get(elaboracionList.size() - 1);
        assertThat(testElaboracion.getLote()).isEqualTo(UPDATED_LOTE);
        assertThat(testElaboracion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testElaboracion.getEstilo()).isEqualTo(UPDATED_ESTILO);
        assertThat(testElaboracion.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testElaboracion.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testElaboracion.isChequeo()).isEqualTo(UPDATED_CHEQUEO);
        assertThat(testElaboracion.isLimpieza()).isEqualTo(UPDATED_LIMPIEZA);
        assertThat(testElaboracion.isLimpiezaOlla()).isEqualTo(UPDATED_LIMPIEZA_OLLA);
        assertThat(testElaboracion.isLimpiezaManguera()).isEqualTo(UPDATED_LIMPIEZA_MANGUERA);
        assertThat(testElaboracion.getMacerado()).isEqualTo(UPDATED_MACERADO);
        assertThat(testElaboracion.getInicioMacerado()).isEqualTo(UPDATED_INICIO_MACERADO);
        assertThat(testElaboracion.isInfucion()).isEqualTo(UPDATED_INFUCION);
        assertThat(testElaboracion.isPrimerEscalon()).isEqualTo(UPDATED_PRIMER_ESCALON);
        assertThat(testElaboracion.isSegundoEscalon()).isEqualTo(UPDATED_SEGUNDO_ESCALON);
        assertThat(testElaboracion.isTercerEscalon()).isEqualTo(UPDATED_TERCER_ESCALON);
        assertThat(testElaboracion.getLitroInicial()).isEqualTo(UPDATED_LITRO_INICIAL);
        assertThat(testElaboracion.getRelacionMaltaAgua()).isEqualTo(UPDATED_RELACION_MALTA_AGUA);
        assertThat(testElaboracion.getLitroFalsoFondo()).isEqualTo(UPDATED_LITRO_FALSO_FONDO);
        assertThat(testElaboracion.getLitroTotal()).isEqualTo(UPDATED_LITRO_TOTAL);

        // Validate the Elaboracion in Elasticsearch
        verify(mockElaboracionSearchRepository, times(1)).save(testElaboracion);
    }

    @Test
    @Transactional
    public void updateNonExistingElaboracion() throws Exception {
        int databaseSizeBeforeUpdate = elaboracionRepository.findAll().size();

        // Create the Elaboracion
        ElaboracionDTO elaboracionDTO = elaboracionMapper.toDto(elaboracion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElaboracionMockMvc.perform(put("/api/elaboracions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elaboracionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Elaboracion in the database
        List<Elaboracion> elaboracionList = elaboracionRepository.findAll();
        assertThat(elaboracionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Elaboracion in Elasticsearch
        verify(mockElaboracionSearchRepository, times(0)).save(elaboracion);
    }

    @Test
    @Transactional
    public void deleteElaboracion() throws Exception {
        // Initialize the database
        elaboracionRepository.saveAndFlush(elaboracion);

        int databaseSizeBeforeDelete = elaboracionRepository.findAll().size();

        // Get the elaboracion
        restElaboracionMockMvc.perform(delete("/api/elaboracions/{id}", elaboracion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Elaboracion> elaboracionList = elaboracionRepository.findAll();
        assertThat(elaboracionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Elaboracion in Elasticsearch
        verify(mockElaboracionSearchRepository, times(1)).deleteById(elaboracion.getId());
    }

    @Test
    @Transactional
    public void searchElaboracion() throws Exception {
        // Initialize the database
        elaboracionRepository.saveAndFlush(elaboracion);
        when(mockElaboracionSearchRepository.search(queryStringQuery("id:" + elaboracion.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(elaboracion), PageRequest.of(0, 1), 1));
        // Search the elaboracion
        restElaboracionMockMvc.perform(get("/api/_search/elaboracions?query=id:" + elaboracion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elaboracion.getId().intValue())))
            .andExpect(jsonPath("$.[*].lote").value(hasItem(DEFAULT_LOTE.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].estilo").value(hasItem(DEFAULT_ESTILO.toString())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].chequeo").value(hasItem(DEFAULT_CHEQUEO.booleanValue())))
            .andExpect(jsonPath("$.[*].limpieza").value(hasItem(DEFAULT_LIMPIEZA.booleanValue())))
            .andExpect(jsonPath("$.[*].limpiezaOlla").value(hasItem(DEFAULT_LIMPIEZA_OLLA.booleanValue())))
            .andExpect(jsonPath("$.[*].limpiezaManguera").value(hasItem(DEFAULT_LIMPIEZA_MANGUERA.booleanValue())))
            .andExpect(jsonPath("$.[*].macerado").value(hasItem(DEFAULT_MACERADO.toString())))
            .andExpect(jsonPath("$.[*].inicioMacerado").value(hasItem(DEFAULT_INICIO_MACERADO.toString())))
            .andExpect(jsonPath("$.[*].infucion").value(hasItem(DEFAULT_INFUCION.booleanValue())))
            .andExpect(jsonPath("$.[*].primerEscalon").value(hasItem(DEFAULT_PRIMER_ESCALON.booleanValue())))
            .andExpect(jsonPath("$.[*].segundoEscalon").value(hasItem(DEFAULT_SEGUNDO_ESCALON.booleanValue())))
            .andExpect(jsonPath("$.[*].tercerEscalon").value(hasItem(DEFAULT_TERCER_ESCALON.booleanValue())))
            .andExpect(jsonPath("$.[*].litroInicial").value(hasItem(DEFAULT_LITRO_INICIAL.intValue())))
            .andExpect(jsonPath("$.[*].relacionMaltaAgua").value(hasItem(DEFAULT_RELACION_MALTA_AGUA.intValue())))
            .andExpect(jsonPath("$.[*].litroFalsoFondo").value(hasItem(DEFAULT_LITRO_FALSO_FONDO.intValue())))
            .andExpect(jsonPath("$.[*].litroTotal").value(hasItem(DEFAULT_LITRO_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Elaboracion.class);
        Elaboracion elaboracion1 = new Elaboracion();
        elaboracion1.setId(1L);
        Elaboracion elaboracion2 = new Elaboracion();
        elaboracion2.setId(elaboracion1.getId());
        assertThat(elaboracion1).isEqualTo(elaboracion2);
        elaboracion2.setId(2L);
        assertThat(elaboracion1).isNotEqualTo(elaboracion2);
        elaboracion1.setId(null);
        assertThat(elaboracion1).isNotEqualTo(elaboracion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElaboracionDTO.class);
        ElaboracionDTO elaboracionDTO1 = new ElaboracionDTO();
        elaboracionDTO1.setId(1L);
        ElaboracionDTO elaboracionDTO2 = new ElaboracionDTO();
        assertThat(elaboracionDTO1).isNotEqualTo(elaboracionDTO2);
        elaboracionDTO2.setId(elaboracionDTO1.getId());
        assertThat(elaboracionDTO1).isEqualTo(elaboracionDTO2);
        elaboracionDTO2.setId(2L);
        assertThat(elaboracionDTO1).isNotEqualTo(elaboracionDTO2);
        elaboracionDTO1.setId(null);
        assertThat(elaboracionDTO1).isNotEqualTo(elaboracionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(elaboracionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(elaboracionMapper.fromId(null)).isNull();
    }
}
