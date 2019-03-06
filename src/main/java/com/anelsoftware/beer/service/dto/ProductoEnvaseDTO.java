package com.anelsoftware.beer.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProductoEnvase entity.
 */
public class ProductoEnvaseDTO implements Serializable {

    private Long id;


    private Long productoId;

    private Long envaseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        ProductoEnvaseDTO productoEnvaseDTO = (ProductoEnvaseDTO) o;
        if (productoEnvaseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoEnvaseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductoEnvaseDTO{" +
            "id=" + getId() +
            ", producto=" + getProductoId() +
            ", envase=" + getEnvaseId() +
            "}";
    }
}
