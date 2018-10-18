package com.anelsoftware.beer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.anelsoftware.beer.domain.enumeration.Unidad;

import com.anelsoftware.beer.domain.enumeration.TipoInsumo;

/**
 * A Insumo.
 */
@Entity
@Table(name = "insumo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "insumo")
public class Insumo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "marca")
    private String marca;

    @Column(name = "stock_minimo")
    private Long stockMinimo;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad")
    private Unidad unidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoInsumo tipo;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @OneToMany(mappedBy = "insumo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ElaboracionInsumo> elaboracionInsumos = new HashSet<>();
    @OneToMany(mappedBy = "insumo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompraInsumo> compraInsumos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Insumo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public Insumo marca(String marca) {
        this.marca = marca;
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getStockMinimo() {
        return stockMinimo;
    }

    public Insumo stockMinimo(Long stockMinimo) {
        this.stockMinimo = stockMinimo;
        return this;
    }

    public void setStockMinimo(Long stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public Insumo unidad(Unidad unidad) {
        this.unidad = unidad;
        return this;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public TipoInsumo getTipo() {
        return tipo;
    }

    public Insumo tipo(TipoInsumo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoInsumo tipo) {
        this.tipo = tipo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Insumo imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Insumo imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Set<ElaboracionInsumo> getElaboracionInsumos() {
        return elaboracionInsumos;
    }

    public Insumo elaboracionInsumos(Set<ElaboracionInsumo> elaboracionInsumos) {
        this.elaboracionInsumos = elaboracionInsumos;
        return this;
    }

    public Insumo addElaboracionInsumo(ElaboracionInsumo elaboracionInsumo) {
        this.elaboracionInsumos.add(elaboracionInsumo);
        elaboracionInsumo.setInsumo(this);
        return this;
    }

    public Insumo removeElaboracionInsumo(ElaboracionInsumo elaboracionInsumo) {
        this.elaboracionInsumos.remove(elaboracionInsumo);
        elaboracionInsumo.setInsumo(null);
        return this;
    }

    public void setElaboracionInsumos(Set<ElaboracionInsumo> elaboracionInsumos) {
        this.elaboracionInsumos = elaboracionInsumos;
    }

    public Set<CompraInsumo> getCompraInsumos() {
        return compraInsumos;
    }

    public Insumo compraInsumos(Set<CompraInsumo> compraInsumos) {
        this.compraInsumos = compraInsumos;
        return this;
    }

    public Insumo addCompraInsumo(CompraInsumo compraInsumo) {
        this.compraInsumos.add(compraInsumo);
        compraInsumo.setInsumo(this);
        return this;
    }

    public Insumo removeCompraInsumo(CompraInsumo compraInsumo) {
        this.compraInsumos.remove(compraInsumo);
        compraInsumo.setInsumo(null);
        return this;
    }

    public void setCompraInsumos(Set<CompraInsumo> compraInsumos) {
        this.compraInsumos = compraInsumos;
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
        Insumo insumo = (Insumo) o;
        if (insumo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insumo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Insumo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", marca='" + getMarca() + "'" +
            ", stockMinimo=" + getStockMinimo() +
            ", unidad='" + getUnidad() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            "}";
    }
}
