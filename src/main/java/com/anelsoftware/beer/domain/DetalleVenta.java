package com.anelsoftware.beer.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DetalleVenta.
 */
@Entity
@Table(name = "detalle_venta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "detalleventa")
public class DetalleVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad")
    private Long cantidad;

    @Column(name = "precio_sub_total", precision = 10, scale = 2)
    private BigDecimal precioSubTotal;

    @ManyToOne
    @JsonIgnoreProperties("detalleVentas")
    private FacturaVenta facturaVenta;

    @ManyToOne
    @JsonIgnoreProperties("detalleVentas")
    private Producto producto;

    @ManyToOne
    @JsonIgnoreProperties("detalleVentas")
    private Envase envase;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public DetalleVenta cantidad(Long cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioSubTotal() {
        return precioSubTotal;
    }

    public DetalleVenta precioSubTotal(BigDecimal precioSubTotal) {
        this.precioSubTotal = precioSubTotal;
        return this;
    }

    public void setPrecioSubTotal(BigDecimal precioSubTotal) {
        this.precioSubTotal = precioSubTotal;
    }

    public FacturaVenta getFacturaVenta() {
        return facturaVenta;
    }

    public DetalleVenta facturaVenta(FacturaVenta facturaVenta) {
        this.facturaVenta = facturaVenta;
        return this;
    }

    public void setFacturaVenta(FacturaVenta facturaVenta) {
        this.facturaVenta = facturaVenta;
    }

    public Producto getProducto() {
        return producto;
    }

    public DetalleVenta producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Envase getEnvase() {
        return envase;
    }

    public DetalleVenta envase(Envase envase) {
        this.envase = envase;
        return this;
    }

    public void setEnvase(Envase envase) {
        this.envase = envase;
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
        DetalleVenta detalleVenta = (DetalleVenta) o;
        if (detalleVenta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detalleVenta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetalleVenta{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", precioSubTotal=" + getPrecioSubTotal() +
            "}";
    }
}
