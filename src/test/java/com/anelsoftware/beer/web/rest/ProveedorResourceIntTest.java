package com.anelsoftware.beer.web.rest;

import com.anelsoftware.beer.QueenBeerApp;

import com.anelsoftware.beer.domain.Proveedor;
import com.anelsoftware.beer.repository.ProveedorRepository;
import com.anelsoftware.beer.repository.search.ProveedorSearchRepository;
import com.anelsoftware.beer.service.ProveedorService;
import com.anelsoftware.beer.service.dto.ProveedorDTO;
import com.anelsoftware.beer.service.mapper.ProveedorMapper;
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

import com.anelsoftware.beer.domain.enumeration.CondicionFiscal;
import com.anelsoftware.beer.domain.enumeration.Provincia;
/**
 * Test class for the ProveedorResource REST controller.
 *
 * @see ProveedorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueenBeerApp.class)
public class ProveedorResourceIntTest {

    private static final String DEFAULT_NOMBRE_FANTASIA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_FANTASIA = "BBBBBBBBBB";

    private static final String DEFAULT_RAZON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZON_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_CUIT = "AAAAAAAAAA";
    private static final String UPDATED_CUIT = "BBBBBBBBBB";

    private static final CondicionFiscal DEFAULT_CONDICION_FISCAL = CondicionFiscal.RESPONSABLE_INSCRIPTO;
    private static final CondicionFiscal UPDATED_CONDICION_FISCAL = CondicionFiscal.MONOTRIBUTISTA;

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_ALTA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ALTA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDAD = "BBBBBBBBBB";

    private static final Long DEFAULT_CODIGO_POSTAL = 1L;
    private static final Long UPDATED_CODIGO_POSTAL = 2L;

    private static final String DEFAULT_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Provincia DEFAULT_PROVINCIA = Provincia.BUENOS_AIRES;
    private static final Provincia UPDATED_PROVINCIA = Provincia.CATAMARCA;

    private static final String DEFAULT_NOTAS = "AAAAAAAAAA";
    private static final String UPDATED_NOTAS = "BBBBBBBBBB";

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProveedorMapper proveedorMapper;
    
    @Autowired
    private ProveedorService proveedorService;

    /**
     * This repository is mocked in the com.anelsoftware.beer.repository.search test package.
     *
     * @see com.anelsoftware.beer.repository.search.ProveedorSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProveedorSearchRepository mockProveedorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProveedorMockMvc;

    private Proveedor proveedor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProveedorResource proveedorResource = new ProveedorResource(proveedorService);
        this.restProveedorMockMvc = MockMvcBuilders.standaloneSetup(proveedorResource)
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
    public static Proveedor createEntity(EntityManager em) {
        Proveedor proveedor = new Proveedor()
            .nombreFantasia(DEFAULT_NOMBRE_FANTASIA)
            .razonSocial(DEFAULT_RAZON_SOCIAL)
            .cuit(DEFAULT_CUIT)
            .condicionFiscal(DEFAULT_CONDICION_FISCAL)
            .telefono(DEFAULT_TELEFONO)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .direccion(DEFAULT_DIRECCION)
            .localidad(DEFAULT_LOCALIDAD)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .contacto(DEFAULT_CONTACTO)
            .email(DEFAULT_EMAIL)
            .provincia(DEFAULT_PROVINCIA)
            .notas(DEFAULT_NOTAS);
        return proveedor;
    }

    @Before
    public void initTest() {
        proveedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createProveedor() throws Exception {
        int databaseSizeBeforeCreate = proveedorRepository.findAll().size();

        // Create the Proveedor
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);
        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isCreated());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeCreate + 1);
        Proveedor testProveedor = proveedorList.get(proveedorList.size() - 1);
        assertThat(testProveedor.getNombreFantasia()).isEqualTo(DEFAULT_NOMBRE_FANTASIA);
        assertThat(testProveedor.getRazonSocial()).isEqualTo(DEFAULT_RAZON_SOCIAL);
        assertThat(testProveedor.getCuit()).isEqualTo(DEFAULT_CUIT);
        assertThat(testProveedor.getCondicionFiscal()).isEqualTo(DEFAULT_CONDICION_FISCAL);
        assertThat(testProveedor.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testProveedor.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testProveedor.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testProveedor.getLocalidad()).isEqualTo(DEFAULT_LOCALIDAD);
        assertThat(testProveedor.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testProveedor.getContacto()).isEqualTo(DEFAULT_CONTACTO);
        assertThat(testProveedor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProveedor.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testProveedor.getNotas()).isEqualTo(DEFAULT_NOTAS);

        // Validate the Proveedor in Elasticsearch
        verify(mockProveedorSearchRepository, times(1)).save(testProveedor);
    }

    @Test
    @Transactional
    public void createProveedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proveedorRepository.findAll().size();

        // Create the Proveedor with an existing ID
        proveedor.setId(1L);
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Proveedor in Elasticsearch
        verify(mockProveedorSearchRepository, times(0)).save(proveedor);
    }

    @Test
    @Transactional
    public void getAllProveedors() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        // Get all the proveedorList
        restProveedorMockMvc.perform(get("/api/proveedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proveedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreFantasia").value(hasItem(DEFAULT_NOMBRE_FANTASIA.toString())))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].cuit").value(hasItem(DEFAULT_CUIT.toString())))
            .andExpect(jsonPath("$.[*].condicionFiscal").value(hasItem(DEFAULT_CONDICION_FISCAL.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].localidad").value(hasItem(DEFAULT_LOCALIDAD.toString())))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL.intValue())))
            .andExpect(jsonPath("$.[*].contacto").value(hasItem(DEFAULT_CONTACTO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA.toString())))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS.toString())));
    }
    
    @Test
    @Transactional
    public void getProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        // Get the proveedor
        restProveedorMockMvc.perform(get("/api/proveedors/{id}", proveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proveedor.getId().intValue()))
            .andExpect(jsonPath("$.nombreFantasia").value(DEFAULT_NOMBRE_FANTASIA.toString()))
            .andExpect(jsonPath("$.razonSocial").value(DEFAULT_RAZON_SOCIAL.toString()))
            .andExpect(jsonPath("$.cuit").value(DEFAULT_CUIT.toString()))
            .andExpect(jsonPath("$.condicionFiscal").value(DEFAULT_CONDICION_FISCAL.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.localidad").value(DEFAULT_LOCALIDAD.toString()))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL.intValue()))
            .andExpect(jsonPath("$.contacto").value(DEFAULT_CONTACTO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA.toString()))
            .andExpect(jsonPath("$.notas").value(DEFAULT_NOTAS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProveedor() throws Exception {
        // Get the proveedor
        restProveedorMockMvc.perform(get("/api/proveedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();

        // Update the proveedor
        Proveedor updatedProveedor = proveedorRepository.findById(proveedor.getId()).get();
        // Disconnect from session so that the updates on updatedProveedor are not directly saved in db
        em.detach(updatedProveedor);
        updatedProveedor
            .nombreFantasia(UPDATED_NOMBRE_FANTASIA)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .cuit(UPDATED_CUIT)
            .condicionFiscal(UPDATED_CONDICION_FISCAL)
            .telefono(UPDATED_TELEFONO)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .direccion(UPDATED_DIRECCION)
            .localidad(UPDATED_LOCALIDAD)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .contacto(UPDATED_CONTACTO)
            .email(UPDATED_EMAIL)
            .provincia(UPDATED_PROVINCIA)
            .notas(UPDATED_NOTAS);
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(updatedProveedor);

        restProveedorMockMvc.perform(put("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isOk());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeUpdate);
        Proveedor testProveedor = proveedorList.get(proveedorList.size() - 1);
        assertThat(testProveedor.getNombreFantasia()).isEqualTo(UPDATED_NOMBRE_FANTASIA);
        assertThat(testProveedor.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testProveedor.getCuit()).isEqualTo(UPDATED_CUIT);
        assertThat(testProveedor.getCondicionFiscal()).isEqualTo(UPDATED_CONDICION_FISCAL);
        assertThat(testProveedor.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testProveedor.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testProveedor.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testProveedor.getLocalidad()).isEqualTo(UPDATED_LOCALIDAD);
        assertThat(testProveedor.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testProveedor.getContacto()).isEqualTo(UPDATED_CONTACTO);
        assertThat(testProveedor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProveedor.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testProveedor.getNotas()).isEqualTo(UPDATED_NOTAS);

        // Validate the Proveedor in Elasticsearch
        verify(mockProveedorSearchRepository, times(1)).save(testProveedor);
    }

    @Test
    @Transactional
    public void updateNonExistingProveedor() throws Exception {
        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();

        // Create the Proveedor
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProveedorMockMvc.perform(put("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Proveedor in Elasticsearch
        verify(mockProveedorSearchRepository, times(0)).save(proveedor);
    }

    @Test
    @Transactional
    public void deleteProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        int databaseSizeBeforeDelete = proveedorRepository.findAll().size();

        // Get the proveedor
        restProveedorMockMvc.perform(delete("/api/proveedors/{id}", proveedor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Proveedor in Elasticsearch
        verify(mockProveedorSearchRepository, times(1)).deleteById(proveedor.getId());
    }

    @Test
    @Transactional
    public void searchProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);
        when(mockProveedorSearchRepository.search(queryStringQuery("id:" + proveedor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(proveedor), PageRequest.of(0, 1), 1));
        // Search the proveedor
        restProveedorMockMvc.perform(get("/api/_search/proveedors?query=id:" + proveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proveedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreFantasia").value(hasItem(DEFAULT_NOMBRE_FANTASIA.toString())))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].cuit").value(hasItem(DEFAULT_CUIT.toString())))
            .andExpect(jsonPath("$.[*].condicionFiscal").value(hasItem(DEFAULT_CONDICION_FISCAL.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].localidad").value(hasItem(DEFAULT_LOCALIDAD.toString())))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL.intValue())))
            .andExpect(jsonPath("$.[*].contacto").value(hasItem(DEFAULT_CONTACTO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA.toString())))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proveedor.class);
        Proveedor proveedor1 = new Proveedor();
        proveedor1.setId(1L);
        Proveedor proveedor2 = new Proveedor();
        proveedor2.setId(proveedor1.getId());
        assertThat(proveedor1).isEqualTo(proveedor2);
        proveedor2.setId(2L);
        assertThat(proveedor1).isNotEqualTo(proveedor2);
        proveedor1.setId(null);
        assertThat(proveedor1).isNotEqualTo(proveedor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProveedorDTO.class);
        ProveedorDTO proveedorDTO1 = new ProveedorDTO();
        proveedorDTO1.setId(1L);
        ProveedorDTO proveedorDTO2 = new ProveedorDTO();
        assertThat(proveedorDTO1).isNotEqualTo(proveedorDTO2);
        proveedorDTO2.setId(proveedorDTO1.getId());
        assertThat(proveedorDTO1).isEqualTo(proveedorDTO2);
        proveedorDTO2.setId(2L);
        assertThat(proveedorDTO1).isNotEqualTo(proveedorDTO2);
        proveedorDTO1.setId(null);
        assertThat(proveedorDTO1).isNotEqualTo(proveedorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(proveedorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(proveedorMapper.fromId(null)).isNull();
    }
}
