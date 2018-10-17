package com.anelsoftware.beer.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the ListaPrecio entity.
 */
public class ListaPrecioDTO implements Serializable {

    private Long id;

    private String nombre;

    private BigDecimal porcentage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPorcentage() {
        return porcentage;
    }

    public void setPorcentage(BigDecimal porcentage) {
        this.porcentage = porcentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ListaPrecioDTO listaPrecioDTO = (ListaPrecioDTO) o;
        if (listaPrecioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), listaPrecioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ListaPrecioDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", porcentage=" + getPorcentage() +
            "}";
    }
}
