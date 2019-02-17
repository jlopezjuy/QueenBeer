package com.anelsoftware.beer.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.anelsoftware.beer.domain.enumeration.TipoProducto;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "complementario")
    private String complementario;

    @Column(name = "estilo")
    private String estilo;

    @Column(name = "nombre_comercial")
    private String nombreComercial;

    @Column(name = "precio_litro", precision = 10, scale = 2)
    private BigDecimal precioLitro;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_producto")
    private TipoProducto tipoProducto;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @Column(name = "cantidad")
    private Long cantidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Producto codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getComplementario() {
        return complementario;
    }

    public Producto complementario(String complementario) {
        this.complementario = complementario;
        return this;
    }

    public void setComplementario(String complementario) {
        this.complementario = complementario;
    }

    public String getEstilo() {
        return estilo;
    }

    public Producto estilo(String estilo) {
        this.estilo = estilo;
        return this;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public Producto nombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
        return this;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public BigDecimal getPrecioLitro() {
        return precioLitro;
    }

    public Producto precioLitro(BigDecimal precioLitro) {
        this.precioLitro = precioLitro;
        return this;
    }

    public void setPrecioLitro(BigDecimal precioLitro) {
        this.precioLitro = precioLitro;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public Producto tipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
        return this;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Producto imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Producto imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public Producto cantidad(Long cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
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
        Producto producto = (Producto) o;
        if (producto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), producto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", complementario='" + getComplementario() + "'" +
            ", estilo='" + getEstilo() + "'" +
            ", nombreComercial='" + getNombreComercial() + "'" +
            ", precioLitro=" + getPrecioLitro() +
            ", tipoProducto='" + getTipoProducto() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            ", cantidad=" + getCantidad() +
            "}";
    }
}
