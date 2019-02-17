package com.anelsoftware.beer.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import com.anelsoftware.beer.domain.enumeration.TipoProducto;

/**
 * A DTO for the Producto entity.
 */
public class ProductoDTO implements Serializable {

    private Long id;

    private String codigo;

    private String complementario;

    private String estilo;

    private String nombreComercial;

    private BigDecimal precioLitro;

    private TipoProducto tipoProducto;

    @Lob
    private byte[] imagen;

    private String imagenContentType;
    private Long cantidad;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getComplementario() {
        return complementario;
    }

    public void setComplementario(String complementario) {
        this.complementario = complementario;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public BigDecimal getPrecioLitro() {
        return precioLitro;
    }

    public void setPrecioLitro(BigDecimal precioLitro) {
        this.precioLitro = precioLitro;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
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

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductoDTO productoDTO = (ProductoDTO) o;
        if (productoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", complementario='" + getComplementario() + "'" +
            ", estilo='" + getEstilo() + "'" +
            ", nombreComercial='" + getNombreComercial() + "'" +
            ", precioLitro=" + getPrecioLitro() +
            ", tipoProducto='" + getTipoProducto() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", cantidad=" + getCantidad() +
            "}";
    }
}
