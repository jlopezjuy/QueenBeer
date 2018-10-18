package com.anelsoftware.beer.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.anelsoftware.beer.domain.enumeration.UsoLupulo;
import com.anelsoftware.beer.domain.enumeration.ModoLupulo;

/**
 * A DTO for the ElaboracionInsumo entity.
 */
public class ElaboracionInsumoDTO implements Serializable {

    private Long id;

    private BigDecimal extracto;

    private Long color;

    private BigDecimal porcentage;

    private BigDecimal kilogramos;

    private UsoLupulo uso;

    private BigDecimal alpha;

    private ModoLupulo modo;

    private BigDecimal gramos;

    private BigDecimal gl;

    private Long tiempo;

    private BigDecimal ibu;

    private Long elaboracionId;

    private Long insumoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getExtracto() {
        return extracto;
    }

    public void setExtracto(BigDecimal extracto) {
        this.extracto = extracto;
    }

    public Long getColor() {
        return color;
    }

    public void setColor(Long color) {
        this.color = color;
    }

    public BigDecimal getPorcentage() {
        return porcentage;
    }

    public void setPorcentage(BigDecimal porcentage) {
        this.porcentage = porcentage;
    }

    public BigDecimal getKilogramos() {
        return kilogramos;
    }

    public void setKilogramos(BigDecimal kilogramos) {
        this.kilogramos = kilogramos;
    }

    public UsoLupulo getUso() {
        return uso;
    }

    public void setUso(UsoLupulo uso) {
        this.uso = uso;
    }

    public BigDecimal getAlpha() {
        return alpha;
    }

    public void setAlpha(BigDecimal alpha) {
        this.alpha = alpha;
    }

    public ModoLupulo getModo() {
        return modo;
    }

    public void setModo(ModoLupulo modo) {
        this.modo = modo;
    }

    public BigDecimal getGramos() {
        return gramos;
    }

    public void setGramos(BigDecimal gramos) {
        this.gramos = gramos;
    }

    public BigDecimal getGl() {
        return gl;
    }

    public void setGl(BigDecimal gl) {
        this.gl = gl;
    }

    public Long getTiempo() {
        return tiempo;
    }

    public void setTiempo(Long tiempo) {
        this.tiempo = tiempo;
    }

    public BigDecimal getIbu() {
        return ibu;
    }

    public void setIbu(BigDecimal ibu) {
        this.ibu = ibu;
    }

    public Long getElaboracionId() {
        return elaboracionId;
    }

    public void setElaboracionId(Long elaboracionId) {
        this.elaboracionId = elaboracionId;
    }

    public Long getInsumoId() {
        return insumoId;
    }

    public void setInsumoId(Long insumoId) {
        this.insumoId = insumoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ElaboracionInsumoDTO elaboracionInsumoDTO = (ElaboracionInsumoDTO) o;
        if (elaboracionInsumoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), elaboracionInsumoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ElaboracionInsumoDTO{" +
            "id=" + getId() +
            ", extracto=" + getExtracto() +
            ", color=" + getColor() +
            ", porcentage=" + getPorcentage() +
            ", kilogramos=" + getKilogramos() +
            ", uso='" + getUso() + "'" +
            ", alpha=" + getAlpha() +
            ", modo='" + getModo() + "'" +
            ", gramos=" + getGramos() +
            ", gl=" + getGl() +
            ", tiempo=" + getTiempo() +
            ", ibu=" + getIbu() +
            ", elaboracion=" + getElaboracionId() +
            ", insumo=" + getInsumoId() +
            "}";
    }
}
