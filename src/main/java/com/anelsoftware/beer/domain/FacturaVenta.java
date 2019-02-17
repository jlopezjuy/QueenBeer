package com.anelsoftware.beer.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.anelsoftware.beer.domain.enumeration.TipoDocumento;

import com.anelsoftware.beer.domain.enumeration.TipoMoneda;

/**
 * A FacturaVenta.
 */
@Entity
@Table(name = "factura_venta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "facturaventa")
public class FacturaVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "total_neto", precision = 10, scale = 2)
    private BigDecimal totalNeto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_moneda")
    private TipoMoneda tipoMoneda;

    @Column(name = "nro_factura")
    private String nroFactura;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public FacturaVenta tipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public FacturaVenta fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotalNeto() {
        return totalNeto;
    }

    public FacturaVenta totalNeto(BigDecimal totalNeto) {
        this.totalNeto = totalNeto;
        return this;
    }

    public void setTotalNeto(BigDecimal totalNeto) {
        this.totalNeto = totalNeto;
    }

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public FacturaVenta tipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
        return this;
    }

    public void setTipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getNroFactura() {
        return nroFactura;
    }

    public FacturaVenta nroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
        return this;
    }

    public void setNroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public FacturaVenta cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
        FacturaVenta facturaVenta = (FacturaVenta) o;
        if (facturaVenta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), facturaVenta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FacturaVenta{" +
            "id=" + getId() +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", totalNeto=" + getTotalNeto() +
            ", tipoMoneda='" + getTipoMoneda() + "'" +
            ", nroFactura='" + getNroFactura() + "'" +
            "}";
    }
}
