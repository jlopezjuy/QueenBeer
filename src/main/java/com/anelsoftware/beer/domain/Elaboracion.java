package com.anelsoftware.beer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.anelsoftware.beer.domain.enumeration.TipoMacerado;

/**
 * A Elaboracion.
 */
@Entity
@Table(name = "elaboracion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "elaboracion")
public class Elaboracion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lote")
    private String lote;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "estilo")
    private String estilo;

    @Column(name = "fecha_inicio")
    private Instant fechaInicio;

    @Column(name = "fecha_fin")
    private Instant fechaFin;

    @Column(name = "chequeo")
    private Boolean chequeo;

    @Column(name = "limpieza")
    private Boolean limpieza;

    @Column(name = "limpieza_olla")
    private Boolean limpiezaOlla;

    @Column(name = "limpieza_manguera")
    private Boolean limpiezaManguera;

    @Enumerated(EnumType.STRING)
    @Column(name = "macerado")
    private TipoMacerado macerado;

    @Column(name = "inicio_macerado")
    private Instant inicioMacerado;

    @Column(name = "infucion")
    private Boolean infucion;

    @Column(name = "primer_escalon")
    private Boolean primerEscalon;

    @Column(name = "segundo_escalon")
    private Boolean segundoEscalon;

    @Column(name = "tercer_escalon")
    private Boolean tercerEscalon;

    @Column(name = "litro_inicial", precision = 10, scale = 2)
    private BigDecimal litroInicial;

    @Column(name = "relacion_malta_agua", precision = 10, scale = 2)
    private BigDecimal relacionMaltaAgua;

    @Column(name = "litro_falso_fondo", precision = 10, scale = 2)
    private BigDecimal litroFalsoFondo;

    @Column(name = "litro_total", precision = 10, scale = 2)
    private BigDecimal litroTotal;

    @OneToMany(mappedBy = "elaboracion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ElaboracionInsumo> elaboracionInsumos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLote() {
        return lote;
    }

    public Elaboracion lote(String lote) {
        this.lote = lote;
        return this;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNombre() {
        return nombre;
    }

    public Elaboracion nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstilo() {
        return estilo;
    }

    public Elaboracion estilo(String estilo) {
        this.estilo = estilo;
        return this;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public Instant getFechaInicio() {
        return fechaInicio;
    }

    public Elaboracion fechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFin() {
        return fechaFin;
    }

    public Elaboracion fechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean isChequeo() {
        return chequeo;
    }

    public Elaboracion chequeo(Boolean chequeo) {
        this.chequeo = chequeo;
        return this;
    }

    public void setChequeo(Boolean chequeo) {
        this.chequeo = chequeo;
    }

    public Boolean isLimpieza() {
        return limpieza;
    }

    public Elaboracion limpieza(Boolean limpieza) {
        this.limpieza = limpieza;
        return this;
    }

    public void setLimpieza(Boolean limpieza) {
        this.limpieza = limpieza;
    }

    public Boolean isLimpiezaOlla() {
        return limpiezaOlla;
    }

    public Elaboracion limpiezaOlla(Boolean limpiezaOlla) {
        this.limpiezaOlla = limpiezaOlla;
        return this;
    }

    public void setLimpiezaOlla(Boolean limpiezaOlla) {
        this.limpiezaOlla = limpiezaOlla;
    }

    public Boolean isLimpiezaManguera() {
        return limpiezaManguera;
    }

    public Elaboracion limpiezaManguera(Boolean limpiezaManguera) {
        this.limpiezaManguera = limpiezaManguera;
        return this;
    }

    public void setLimpiezaManguera(Boolean limpiezaManguera) {
        this.limpiezaManguera = limpiezaManguera;
    }

    public TipoMacerado getMacerado() {
        return macerado;
    }

    public Elaboracion macerado(TipoMacerado macerado) {
        this.macerado = macerado;
        return this;
    }

    public void setMacerado(TipoMacerado macerado) {
        this.macerado = macerado;
    }

    public Instant getInicioMacerado() {
        return inicioMacerado;
    }

    public Elaboracion inicioMacerado(Instant inicioMacerado) {
        this.inicioMacerado = inicioMacerado;
        return this;
    }

    public void setInicioMacerado(Instant inicioMacerado) {
        this.inicioMacerado = inicioMacerado;
    }

    public Boolean isInfucion() {
        return infucion;
    }

    public Elaboracion infucion(Boolean infucion) {
        this.infucion = infucion;
        return this;
    }

    public void setInfucion(Boolean infucion) {
        this.infucion = infucion;
    }

    public Boolean isPrimerEscalon() {
        return primerEscalon;
    }

    public Elaboracion primerEscalon(Boolean primerEscalon) {
        this.primerEscalon = primerEscalon;
        return this;
    }

    public void setPrimerEscalon(Boolean primerEscalon) {
        this.primerEscalon = primerEscalon;
    }

    public Boolean isSegundoEscalon() {
        return segundoEscalon;
    }

    public Elaboracion segundoEscalon(Boolean segundoEscalon) {
        this.segundoEscalon = segundoEscalon;
        return this;
    }

    public void setSegundoEscalon(Boolean segundoEscalon) {
        this.segundoEscalon = segundoEscalon;
    }

    public Boolean isTercerEscalon() {
        return tercerEscalon;
    }

    public Elaboracion tercerEscalon(Boolean tercerEscalon) {
        this.tercerEscalon = tercerEscalon;
        return this;
    }

    public void setTercerEscalon(Boolean tercerEscalon) {
        this.tercerEscalon = tercerEscalon;
    }

    public BigDecimal getLitroInicial() {
        return litroInicial;
    }

    public Elaboracion litroInicial(BigDecimal litroInicial) {
        this.litroInicial = litroInicial;
        return this;
    }

    public void setLitroInicial(BigDecimal litroInicial) {
        this.litroInicial = litroInicial;
    }

    public BigDecimal getRelacionMaltaAgua() {
        return relacionMaltaAgua;
    }

    public Elaboracion relacionMaltaAgua(BigDecimal relacionMaltaAgua) {
        this.relacionMaltaAgua = relacionMaltaAgua;
        return this;
    }

    public void setRelacionMaltaAgua(BigDecimal relacionMaltaAgua) {
        this.relacionMaltaAgua = relacionMaltaAgua;
    }

    public BigDecimal getLitroFalsoFondo() {
        return litroFalsoFondo;
    }

    public Elaboracion litroFalsoFondo(BigDecimal litroFalsoFondo) {
        this.litroFalsoFondo = litroFalsoFondo;
        return this;
    }

    public void setLitroFalsoFondo(BigDecimal litroFalsoFondo) {
        this.litroFalsoFondo = litroFalsoFondo;
    }

    public BigDecimal getLitroTotal() {
        return litroTotal;
    }

    public Elaboracion litroTotal(BigDecimal litroTotal) {
        this.litroTotal = litroTotal;
        return this;
    }

    public void setLitroTotal(BigDecimal litroTotal) {
        this.litroTotal = litroTotal;
    }

    public Set<ElaboracionInsumo> getElaboracionInsumos() {
        return elaboracionInsumos;
    }

    public Elaboracion elaboracionInsumos(Set<ElaboracionInsumo> elaboracionInsumos) {
        this.elaboracionInsumos = elaboracionInsumos;
        return this;
    }

    public Elaboracion addElaboracionInsumo(ElaboracionInsumo elaboracionInsumo) {
        this.elaboracionInsumos.add(elaboracionInsumo);
        elaboracionInsumo.setElaboracion(this);
        return this;
    }

    public Elaboracion removeElaboracionInsumo(ElaboracionInsumo elaboracionInsumo) {
        this.elaboracionInsumos.remove(elaboracionInsumo);
        elaboracionInsumo.setElaboracion(null);
        return this;
    }

    public void setElaboracionInsumos(Set<ElaboracionInsumo> elaboracionInsumos) {
        this.elaboracionInsumos = elaboracionInsumos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Elaboracion elaboracion = (Elaboracion) o;
        if (elaboracion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), elaboracion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Elaboracion{" +
            "id=" + getId() +
            ", lote='" + getLote() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", estilo='" + getEstilo() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", chequeo='" + isChequeo() + "'" +
            ", limpieza='" + isLimpieza() + "'" +
            ", limpiezaOlla='" + isLimpiezaOlla() + "'" +
            ", limpiezaManguera='" + isLimpiezaManguera() + "'" +
            ", macerado='" + getMacerado() + "'" +
            ", inicioMacerado='" + getInicioMacerado() + "'" +
            ", infucion='" + isInfucion() + "'" +
            ", primerEscalon='" + isPrimerEscalon() + "'" +
            ", segundoEscalon='" + isSegundoEscalon() + "'" +
            ", tercerEscalon='" + isTercerEscalon() + "'" +
            ", litroInicial=" + getLitroInicial() +
            ", relacionMaltaAgua=" + getRelacionMaltaAgua() +
            ", litroFalsoFondo=" + getLitroFalsoFondo() +
            ", litroTotal=" + getLitroTotal() +
            "}";
    }
}
