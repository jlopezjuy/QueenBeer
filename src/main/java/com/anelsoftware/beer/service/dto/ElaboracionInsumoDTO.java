package com.anelsoftware.beer.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ElaboracionInsumo entity.
 */
public class ElaboracionInsumoDTO implements Serializable {

    private Long id;

    private Long elaboracionId;

    private Long insumoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getElaboracionId() {
        return elaboracionId;
    }

    public void setElaboracionId(Long elaboracionId) {
        this.elaboracionId = elaboracionId;
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

        ElaboracionInsumoDTO elaboracionInsumoDTO = (ElaboracionInsumoDTO) o;
        if (elaboracionInsumoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), elaboracionInsumoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ElaboracionInsumoDTO{" +
            "id=" + getId() +
            ", elaboracion=" + getElaboracionId() +
            ", insumo=" + getInsumoId() +
            "}";
    }
}
