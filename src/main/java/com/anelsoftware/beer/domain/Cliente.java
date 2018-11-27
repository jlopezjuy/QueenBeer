package com.anelsoftware.beer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.anelsoftware.beer.domain.enumeration.CategoriaCliente;

import com.anelsoftware.beer.domain.enumeration.CondicionFiscal;

import com.anelsoftware.beer.domain.enumeration.Provincia;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_fantasia")
    private String nombreFantasia;

    @Column(name = "razon_social")
    private String razonSocial;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriaCliente categoria;

    @Column(name = "cuit")
    private String cuit;

    @Enumerated(EnumType.STRING)
    @Column(name = "condicion_fiscal")
    private CondicionFiscal condicionFiscal;

    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "localidad_ciudad")
    private String localidadCiudad;

    @Enumerated(EnumType.STRING)
    @Column(name = "privincia")
    private Provincia privincia;

    @Column(name = "codigo_postal")
    private Long codigoPostal;

    @Column(name = "email")
    private String email;

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

    public Cliente nombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
        return this;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public Cliente razonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
        return this;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public CategoriaCliente getCategoria() {
        return categoria;
    }

    public Cliente categoria(CategoriaCliente categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(CategoriaCliente categoria) {
        this.categoria = categoria;
    }

    public String getCuit() {
        return cuit;
    }

    public Cliente cuit(String cuit) {
        this.cuit = cuit;
        return this;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public CondicionFiscal getCondicionFiscal() {
        return condicionFiscal;
    }

    public Cliente condicionFiscal(CondicionFiscal condicionFiscal) {
        this.condicionFiscal = condicionFiscal;
        return this;
    }

    public void setCondicionFiscal(CondicionFiscal condicionFiscal) {
        this.condicionFiscal = condicionFiscal;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public Cliente fechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getTelefono() {
        return telefono;
    }

    public Cliente telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public Cliente direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidadCiudad() {
        return localidadCiudad;
    }

    public Cliente localidadCiudad(String localidadCiudad) {
        this.localidadCiudad = localidadCiudad;
        return this;
    }

    public void setLocalidadCiudad(String localidadCiudad) {
        this.localidadCiudad = localidadCiudad;
    }

    public Provincia getPrivincia() {
        return privincia;
    }

    public Cliente privincia(Provincia privincia) {
        this.privincia = privincia;
        return this;
    }

    public void setPrivincia(Provincia privincia) {
        this.privincia = privincia;
    }

    public Long getCodigoPostal() {
        return codigoPostal;
    }

    public Cliente codigoPostal(Long codigoPostal) {
        this.codigoPostal = codigoPostal;
        return this;
    }

    public void setCodigoPostal(Long codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getEmail() {
        return email;
    }

    public Cliente email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
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
        Cliente cliente = (Cliente) o;
        if (cliente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cliente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cliente{" +
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
