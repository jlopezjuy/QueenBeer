package com.anelsoftware.beer.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.anelsoftware.beer.domain.enumeration.Unidad;

/**
 * A DTO for the CompraInsumo entity.
 */
public class CompraInsumoDTO implements Serializable {

    private Long id;

    private BigDecimal cantidad;

    private Unidad unidad;

    private String descripcion;

    private BigDecimal costoTotal;

    private Long compraId;

    private Long insumoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Long getCompraId() {
        return compraId;
    }

    public void setCompraId(Long compraId) {
        this.compraId = compraId;
    }

    public Long getInsumoId() {
        return insumoId;
    }

    public void setInsumoId(Long insumoId) {
        this.insumoId = insumoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompraInsumoDTO compraInsumoDTO = (CompraInsumoDTO) o;
        if (compraInsumoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compraInsumoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompraInsumoDTO{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", unidad='" + getUnidad() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", costoTotal=" + getCostoTotal() +
            ", compra=" + getCompraId() +
            ", insumo=" + getInsumoId() +
            "}";
    }
}
