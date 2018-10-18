package com.anelsoftware.beer.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the Compra entity.
 */
public class CompraDTO implements Serializable {

    private Long id;

    private LocalDate fechaCompra;

    private LocalDate fechaEntrega;

    private String nroFactura;

    private BigDecimal subtotal;

    private BigDecimal impuestos;

    private BigDecimal total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompraDTO compraDTO = (CompraDTO) o;
        if (compraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompraDTO{" +
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
