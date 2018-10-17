package com.anelsoftware.beer.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import com.anelsoftware.beer.domain.enumeration.CategoriaCliente;
import com.anelsoftware.beer.domain.enumeration.CondicionFiscal;
import com.anelsoftware.beer.domain.enumeration.Provincia;

/**
 * A DTO for the Cliente entity.
 */
public class ClienteDTO implements Serializable {

    private Long id;

    private String nombreFantasia;

    private String razonSocial;

    private CategoriaCliente categoria;

    private String cuit;

    private CondicionFiscal condicionFiscal;

    private Instant fechaAlta;

    private String telefono;

    private String direccion;

    private String localidadCiudad;

    private Provincia privincia;

    private Long codigoPostal;

    private String email;

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

    public CategoriaCliente getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaCliente categoria) {
        this.categoria = categoria;
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

    public Instant getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidadCiudad() {
        return localidadCiudad;
    }

    public void setLocalidadCiudad(String localidadCiudad) {
        this.localidadCiudad = localidadCiudad;
    }

    public Provincia getPrivincia() {
        return privincia;
    }

    public void setPrivincia(Provincia privincia) {
        this.privincia = privincia;
    }

    public Long getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Long codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClienteDTO clienteDTO = (ClienteDTO) o;
        if (clienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
            "id=" + getId() +
            ", nombreFantasia='" + getNombreFantasia() + "'" +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", categoria='" + getCategoria() + "'" +
            ", cuit='" + getCuit() + "'" +
            ", condicionFiscal='" + getCondicionFiscal() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", localidadCiudad='" + getLocalidadCiudad() + "'" +
            ", privincia='" + getPrivincia() + "'" +
            ", codigoPostal=" + getCodigoPostal() +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
