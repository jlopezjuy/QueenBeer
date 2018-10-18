package com.anelsoftware.beer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Compra.
 */
@Entity
@Table(name = "compra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "compra")
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_compra")
    private LocalDate fechaCompra;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Column(name = "nro_factura")
    private String nroFactura;

    @Column(name = "subtotal", precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "impuestos", precision = 10, scale = 2)
    private BigDecimal impuestos;

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;

    @OneToMany(mappedBy = "compra")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompraInsumo> compraInsumos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public Compra fechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
        return this;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public Compra fechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
        return this;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getNroFactura() {
        return nroFactura;
    }

    public Compra nroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
        return this;
    }

    public void setNroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public Compra subtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
        return this;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public Compra impuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
        return this;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Compra total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Set<CompraInsumo> getCompraInsumos() {
        return compraInsumos;
    }

    public Compra compraInsumos(Set<CompraInsumo> compraInsumos) {
        this.compraInsumos = compraInsumos;
        return this;
    }

    public Compra addCompraInsumo(CompraInsumo compraInsumo) {
        this.compraInsumos.add(compraInsumo);
        compraInsumo.setCompra(this);
        return this;
    }

    public Compra removeCompraInsumo(CompraInsumo compraInsumo) {
        this.compraInsumos.remove(compraInsumo);
        compraInsumo.setCompra(null);
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
        Compra compra = (Compra) o;
        if (compra.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Compra{" +
            "id=" + getId() +
            ", fechaCompra='" + getFechaCompra() + "'" +
            ", fechaEntrega='" + getFechaEntrega() + "'" +
            ", nroFactura='" + getNroFactura() + "'" +
            ", subtotal=" + getSubtotal() +
            ", impuestos=" + getImpuestos() +
            ", total=" + getTotal() +
            "}";
    }
}
