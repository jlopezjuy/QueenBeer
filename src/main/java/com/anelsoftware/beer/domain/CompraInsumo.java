package com.anelsoftware.beer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.anelsoftware.beer.domain.enumeration.Unidad;

/**
 * A CompraInsumo.
 */
@Entity
@Table(name = "compra_insumo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "comprainsumo")
public class CompraInsumo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad", precision = 10, scale = 2)
    private BigDecimal cantidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad")
    private Unidad unidad;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "costo_total", precision = 10, scale = 2)
    private BigDecimal costoTotal;

    @ManyToOne
    @JsonIgnoreProperties("compraInsumos")
    private Compra compra;

    @ManyToOne
    @JsonIgnoreProperties("compraInsumos")
    private Insumo insumo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public CompraInsumo cantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public CompraInsumo unidad(Unidad unidad) {
        this.unidad = unidad;
        return this;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public CompraInsumo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public CompraInsumo costoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
        return this;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Compra getCompra() {
        return compra;
    }

    public CompraInsumo compra(Compra compra) {
        this.compra = compra;
        return this;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public CompraInsumo insumo(Insumo insumo) {
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
        CompraInsumo compraInsumo = (CompraInsumo) o;
        if (compraInsumo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compraInsumo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompraInsumo{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", unidad='" + getUnidad() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", costoTotal=" + getCostoTotal() +
            "}";
    }
}
