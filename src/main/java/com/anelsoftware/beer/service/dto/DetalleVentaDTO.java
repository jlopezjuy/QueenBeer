package com.anelsoftware.beer.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DetalleVenta entity.
 */
public class DetalleVentaDTO implements Serializable {

    private Long id;


    private Long facturaVentaId;

    private Long productoId;

    private Long envaseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFacturaVentaId() {
        return facturaVentaId;
    }

    public void setFacturaVentaId(Long facturaVentaId) {
        this.facturaVentaId = facturaVentaId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Long getEnvaseId() {
        return envaseId;
    }

    public void setEnvaseId(Long envaseId) {
        this.envaseId = envaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetalleVentaDTO detalleVentaDTO = (DetalleVentaDTO) o;
        if (detalleVentaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detalleVentaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetalleVentaDTO{" +
            "id=" + getId() +
            ", facturaVenta=" + getFacturaVentaId() +
            ", producto=" + getProductoId() +
            ", envase=" + getEnvaseId() +
            "}";
    }
}
