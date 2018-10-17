package com.anelsoftware.beer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.anelsoftware.beer.domain.enumeration.Unidad;
import com.anelsoftware.beer.domain.enumeration.TipoInsumo;

/**
 * A DTO for the Insumo entity.
 */
public class InsumoDTO implements Serializable {

    private Long id;

    private String nombre;

    private String marca;

    private Long stockMinimo;

    private Unidad unidad;

    private TipoInsumo tipo;

    @Lob
    private byte[] imagen;
    private String imagenContentType;

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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Long getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Long stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public TipoInsumo getTipo() {
        return tipo;
    }

    public void setTipo(TipoInsumo tipo) {
        this.tipo = tipo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InsumoDTO insumoDTO = (InsumoDTO) o;
        if (insumoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insumoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InsumoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", marca='" + getMarca() + "'" +
            ", stockMinimo=" + getStockMinimo() +
            ", unidad='" + getUnidad() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", imagen='" + getImagen() + "'" +
            "}";
    }
}
