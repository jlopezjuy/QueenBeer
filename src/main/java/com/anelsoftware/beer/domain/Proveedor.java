package com.anelsoftware.beer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.anelsoftware.beer.domain.enumeration.CondicionFiscal;

import com.anelsoftware.beer.domain.enumeration.Provincia;

/**
 * A Proveedor.
 */
@Entity
@Table(name = "proveedor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "proveedor")
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_fantasia")
    private String nombreFantasia;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "cuit")
    private String cuit;

    @Enumerated(EnumType.STRING)
    @Column(name = "condicion_fiscal")
    private CondicionFiscal condicionFiscal;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "fecha_alta")
    private Instant fechaAlta;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "codigo_postal")
    private Long codigoPostal;

    @Column(name = "contacto")
    private String contacto;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "provincia")
    private Provincia provincia;

    @Column(name = "notas")
    private String notas;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public Proveedor nombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
        return this;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public Proveedor razonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
        return this;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public Proveedor cuit(String cuit) {
        this.cuit = cuit;
        return this;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public CondicionFiscal getCondicionFiscal() {
        return condicionFiscal;
    }

    public Proveedor condicionFiscal(CondicionFiscal condicionFiscal) {
        this.condicionFiscal = condicionFiscal;
        return this;
    }

    public void setCondicionFiscal(CondicionFiscal condicionFiscal) {
        this.condicionFiscal = condicionFiscal;
    }

    public String getTelefono() {
        return telefono;
    }

    public Proveedor telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Instant getFechaAlta() {
        return fechaAlta;
    }

    public Proveedor fechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(Instant fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getDireccion() {
        return direccion;
    }

    public Proveedor direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public Proveedor localidad(String localidad) {
        this.localidad = localidad;
        return this;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Long getCodigoPostal() {
        return codigoPostal;
    }

    public Proveedor codigoPostal(Long codigoPostal) {
        this.codigoPostal = codigoPostal;
        return this;
    }

    public void setCodigoPostal(Long codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getContacto() {
        return contacto;
    }

    public Proveedor contacto(String contacto) {
        this.contacto = contacto;
        return this;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmail() {
        return email;
    }

    public Proveedor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public Proveedor provincia(Provincia provincia) {
        this.provincia = provincia;
        return this;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public String getNotas() {
        return notas;
    }

    public Proveedor notas(String notas) {
        this.notas = notas;
        return this;
    }

    public void setNotas(String notas) {
        this.notas = notas;
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
        Proveedor proveedor = (Proveedor) o;
        if (proveedor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proveedor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Proveedor{" +
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
