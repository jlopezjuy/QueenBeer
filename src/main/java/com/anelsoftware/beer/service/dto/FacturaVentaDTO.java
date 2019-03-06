package com.anelsoftware.beer.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.anelsoftware.beer.domain.enumeration.TipoDocumento;
import com.anelsoftware.beer.domain.enumeration.TipoMoneda;

/**
 * A DTO for the FacturaVenta entity.
 */
public class FacturaVentaDTO implements Serializable {

    private Long id;

    private TipoDocumento tipoDocumento;

    private LocalDate fecha;

    private BigDecimal totalNeto;

    private TipoMoneda tipoMoneda;

    private String nroFactura;


    private Long clienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotalNeto() {
        return totalNeto;
    }

    public void setTotalNeto(BigDecimal totalNeto) {
        this.totalNeto = totalNeto;
    }

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FacturaVentaDTO facturaVentaDTO = (FacturaVentaDTO) o;
        if (facturaVentaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), facturaVentaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FacturaVentaDTO{" +
            "id=" + getId() +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", totalNeto=" + getTotalNeto() +
            ", tipoMoneda='" + getTipoMoneda() + "'" +
            ", nroFactura='" + getNroFactura() + "'" +
            ", cliente=" + getClienteId() +
            "}";
    }
}
