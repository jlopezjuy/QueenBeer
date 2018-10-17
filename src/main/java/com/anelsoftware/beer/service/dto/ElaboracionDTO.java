package com.anelsoftware.beer.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.anelsoftware.beer.domain.enumeration.TipoMacerado;

/**
 * A DTO for the Elaboracion entity.
 */
public class ElaboracionDTO implements Serializable {

    private Long id;

    private String lote;

    private String nombre;

    private String estilo;

    private Instant fechaInicio;

    private LocalDate fechaFin;

    private Boolean chequeo;

    private Boolean limpieza;

    private Boolean limpiezaOlla;

    private Boolean limpiezaManguera;

    private TipoMacerado macerado;

    private LocalDate inicioMacerado;

    private Boolean infucion;

    private Boolean primerEscalon;

    private Boolean segundoEscalon;

    private Boolean tercerEscalon;

    private BigDecimal litroInicial;

    private BigDecimal relacionMaltaAgua;

    private BigDecimal litroFalsoFondo;

    private BigDecimal litroTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public Instant getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean isChequeo() {
        return chequeo;
    }

    public void setChequeo(Boolean chequeo) {
        this.chequeo = chequeo;
    }

    public Boolean isLimpieza() {
        return limpieza;
    }

    public void setLimpieza(Boolean limpieza) {
        this.limpieza = limpieza;
    }

    public Boolean isLimpiezaOlla() {
        return limpiezaOlla;
    }

    public void setLimpiezaOlla(Boolean limpiezaOlla) {
        this.limpiezaOlla = limpiezaOlla;
    }

    public Boolean isLimpiezaManguera() {
        return limpiezaManguera;
    }

    public void setLimpiezaManguera(Boolean limpiezaManguera) {
        this.limpiezaManguera = limpiezaManguera;
    }

    public TipoMacerado getMacerado() {
        return macerado;
    }

    public void setMacerado(TipoMacerado macerado) {
        this.macerado = macerado;
    }

    public LocalDate getInicioMacerado() {
        return inicioMacerado;
    }

    public void setInicioMacerado(LocalDate inicioMacerado) {
        this.inicioMacerado = inicioMacerado;
    }

    public Boolean isInfucion() {
        return infucion;
    }

    public void setInfucion(Boolean infucion) {
        this.infucion = infucion;
    }

    public Boolean isPrimerEscalon() {
        return primerEscalon;
    }

    public void setPrimerEscalon(Boolean primerEscalon) {
        this.primerEscalon = primerEscalon;
    }

    public Boolean isSegundoEscalon() {
        return segundoEscalon;
    }

    public void setSegundoEscalon(Boolean segundoEscalon) {
        this.segundoEscalon = segundoEscalon;
    }

    public Boolean isTercerEscalon() {
        return tercerEscalon;
    }

    public void setTercerEscalon(Boolean tercerEscalon) {
        this.tercerEscalon = tercerEscalon;
    }

    public BigDecimal getLitroInicial() {
        return litroInicial;
    }

    public void setLitroInicial(BigDecimal litroInicial) {
        this.litroInicial = litroInicial;
    }

    public BigDecimal getRelacionMaltaAgua() {
        return relacionMaltaAgua;
    }

    public void setRelacionMaltaAgua(BigDecimal relacionMaltaAgua) {
        this.relacionMaltaAgua = relacionMaltaAgua;
    }

    public BigDecimal getLitroFalsoFondo() {
        return litroFalsoFondo;
    }

    public void setLitroFalsoFondo(BigDecimal litroFalsoFondo) {
        this.litroFalsoFondo = litroFalsoFondo;
    }

    public BigDecimal getLitroTotal() {
        return litroTotal;
    }

    public void setLitroTotal(BigDecimal litroTotal) {
        this.litroTotal = litroTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ElaboracionDTO elaboracionDTO = (ElaboracionDTO) o;
        if (elaboracionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), elaboracionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ElaboracionDTO{" +
            "id=" + getId() +
            ", lote='" + getLote() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", estilo='" + getEstilo() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", chequeo='" + isChequeo() + "'" +
            ", limpieza='" + isLimpieza() + "'" +
            ", limpiezaOlla='" + isLimpiezaOlla() + "'" +
            ", limpiezaManguera='" + isLimpiezaManguera() + "'" +
            ", macerado='" + getMacerado() + "'" +
            ", inicioMacerado='" + getInicioMacerado() + "'" +
            ", infucion='" + isInfucion() + "'" +
            ", primerEscalon='" + isPrimerEscalon() + "'" +
            ", segundoEscalon='" + isSegundoEscalon() + "'" +
            ", tercerEscalon='" + isTercerEscalon() + "'" +
            ", litroInicial=" + getLitroInicial() +
            ", relacionMaltaAgua=" + getRelacionMaltaAgua() +
            ", litroFalsoFondo=" + getLitroFalsoFondo() +
            ", litroTotal=" + getLitroTotal() +
            "}";
    }
}
