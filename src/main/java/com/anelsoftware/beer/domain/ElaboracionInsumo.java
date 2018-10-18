package com.anelsoftware.beer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.anelsoftware.beer.domain.enumeration.UsoLupulo;

import com.anelsoftware.beer.domain.enumeration.ModoLupulo;

/**
 * A ElaboracionInsumo.
 */
@Entity
@Table(name = "elaboracion_insumo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "elaboracioninsumo")
public class ElaboracionInsumo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "extracto", precision = 10, scale = 2)
    private BigDecimal extracto;

    @Column(name = "color")
    private Long color;

    @Column(name = "porcentage", precision = 10, scale = 2)
    private BigDecimal porcentage;

    @Column(name = "kilogramos", precision = 10, scale = 2)
    private BigDecimal kilogramos;

    @Enumerated(EnumType.STRING)
    @Column(name = "uso")
    private UsoLupulo uso;

    @Column(name = "alpha", precision = 10, scale = 2)
    private BigDecimal alpha;

    @Enumerated(EnumType.STRING)
    @Column(name = "modo")
    private ModoLupulo modo;

    @Column(name = "gramos", precision = 10, scale = 2)
    private BigDecimal gramos;

    @Column(name = "gl", precision = 10, scale = 2)
    private BigDecimal gl;

    @Column(name = "tiempo")
    private Long tiempo;

    @Column(name = "ibu", precision = 10, scale = 2)
    private BigDecimal ibu;

    @ManyToOne
    @JsonIgnoreProperties("elaboracionInsumos")
    private Elaboracion elaboracion;

    @ManyToOne
    @JsonIgnoreProperties("elaboracionInsumos")
    private Insumo insumo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getExtracto() {
        return extracto;
    }

    public ElaboracionInsumo extracto(BigDecimal extracto) {
        this.extracto = extracto;
        return this;
    }

    public void setExtracto(BigDecimal extracto) {
        this.extracto = extracto;
    }

    public Long getColor() {
        return color;
    }

    public ElaboracionInsumo color(Long color) {
        this.color = color;
        return this;
    }

    public void setColor(Long color) {
        this.color = color;
    }

    public BigDecimal getPorcentage() {
        return porcentage;
    }

    public ElaboracionInsumo porcentage(BigDecimal porcentage) {
        this.porcentage = porcentage;
        return this;
    }

    public void setPorcentage(BigDecimal porcentage) {
        this.porcentage = porcentage;
    }

    public BigDecimal getKilogramos() {
        return kilogramos;
    }

    public ElaboracionInsumo kilogramos(BigDecimal kilogramos) {
        this.kilogramos = kilogramos;
        return this;
    }

    public void setKilogramos(BigDecimal kilogramos) {
        this.kilogramos = kilogramos;
    }

    public UsoLupulo getUso() {
        return uso;
    }

    public ElaboracionInsumo uso(UsoLupulo uso) {
        this.uso = uso;
        return this;
    }

    public void setUso(UsoLupulo uso) {
        this.uso = uso;
    }

    public BigDecimal getAlpha() {
        return alpha;
    }

    public ElaboracionInsumo alpha(BigDecimal alpha) {
        this.alpha = alpha;
        return this;
    }

    public void setAlpha(BigDecimal alpha) {
        this.alpha = alpha;
    }

    public ModoLupulo getModo() {
        return modo;
    }

    public ElaboracionInsumo modo(ModoLupulo modo) {
        this.modo = modo;
        return this;
    }

    public void setModo(ModoLupulo modo) {
        this.modo = modo;
    }

    public BigDecimal getGramos() {
        return gramos;
    }

    public ElaboracionInsumo gramos(BigDecimal gramos) {
        this.gramos = gramos;
        return this;
    }

    public void setGramos(BigDecimal gramos) {
        this.gramos = gramos;
    }

    public BigDecimal getGl() {
        return gl;
    }

    public ElaboracionInsumo gl(BigDecimal gl) {
        this.gl = gl;
        return this;
    }

    public void setGl(BigDecimal gl) {
        this.gl = gl;
    }

    public Long getTiempo() {
        return tiempo;
    }

    public ElaboracionInsumo tiempo(Long tiempo) {
        this.tiempo = tiempo;
        return this;
    }

    public void setTiempo(Long tiempo) {
        this.tiempo = tiempo;
    }

    public BigDecimal getIbu() {
        return ibu;
    }

    public ElaboracionInsumo ibu(BigDecimal ibu) {
        this.ibu = ibu;
        return this;
    }

    public void setIbu(BigDecimal ibu) {
        this.ibu = ibu;
    }

    public Elaboracion getElaboracion() {
        return elaboracion;
    }

    public ElaboracionInsumo elaboracion(Elaboracion elaboracion) {
        this.elaboracion = elaboracion;
        return this;
    }

    public void setElaboracion(Elaboracion elaboracion) {
        this.elaboracion = elaboracion;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public ElaboracionInsumo insumo(Insumo insumo) {
        this.insumo = insumo;
        return this;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
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
        ElaboracionInsumo elaboracionInsumo = (ElaboracionInsumo) o;
        if (elaboracionInsumo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), elaboracionInsumo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ElaboracionInsumo{" +
            "id=" + getId() +
            ", extracto=" + getExtracto() +
            ", color=" + getColor() +
            ", porcentage=" + getPorcentage() +
            ", kilogramos=" + getKilogramos() +
            ", uso='" + getUso() + "'" +
            ", alpha=" + getAlpha() +
            ", modo='" + getModo() + "'" +
            ", gramos=" + getGramos() +
            ", gl=" + getGl() +
            ", tiempo=" + getTiempo() +
            ", ibu=" + getIbu() +
            "}";
    }
}
