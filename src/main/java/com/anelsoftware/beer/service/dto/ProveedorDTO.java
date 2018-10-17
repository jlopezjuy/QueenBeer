package com.anelsoftware.beer.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import com.anelsoftware.beer.domain.enumeration.CondicionFiscal;
import com.anelsoftware.beer.domain.enumeration.Provincia;

/**
 * A DTO for the Proveedor entity.
 */
public class ProveedorDTO implements Serializable {

    private Long id;

    private String nombreFantasia;

    private String razonSocial;

    private String cuit;

    private CondicionFiscal condicionFiscal;

    private String telefono;

    private Instant fechaAlta;

    private String direccion;

    private String localidad;

    private Long codigoPostal;

    private String contacto;

    private String email;

    private Provincia provincia;

    private String notas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public CondicionFiscal getCondicionFiscal() {
        return condicionFiscal;
    }

    public void setCondicionFiscal(CondicionFiscal condicionFiscal) {
        this.condicionFiscal = condicionFiscal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Instant getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Long getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Long codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProveedorDTO proveedorDTO = (ProveedorDTO) o;
        if (proveedorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proveedorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProveedorDTO{" +
            "id=" + getId() +
            ", nombreFantasia='" + getNombreFantasia() + "'" +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", cuit='" + getCuit() + "'" +
            ", condicionFiscal='" + getCondicionFiscal() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", localidad='" + getLocalidad() + "'" +
            ", codigoPostal=" + getCodigoPostal() +
            ", contacto='" + getContacto() + "'" +
            ", email='" + getEmail() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", notas='" + getNotas() + "'" +
            "}";
    }
}
