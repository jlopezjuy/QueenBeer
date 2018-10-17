package com.anelsoftware.beer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ElaboracionInsumo.
 */
@Entity
@Table(name = "elaboracion_insumo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "elaboracioninsumo")
public class ElaboracionInsumo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("elaboracionInsumos")
    private Elaboracion elaboracion;

    @ManyToOne
    @JsonIgnoreProperties("elaboracionInsumos")
    private Insumo insumo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Elaboracion getElaboracion() {
        return elaboracion;
    }

    public ElaboracionInsumo elaboracion(Elaboracion elaboracion) {
        this.elaboracion = elaboracion;
        return this;
    }

    public void setElaboracion(Elaboracion elaboracion) {
        this.elaboracion = elaboracion;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public ElaboracionInsumo insumo(Insumo insumo) {
        this.insumo = insumo;
        return this;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
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
        ElaboracionInsumo elaboracionInsumo = (ElaboracionInsumo) o;
        if (elaboracionInsumo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), elaboracionInsumo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ElaboracionInsumo{" +
            "id=" + getId() +
            "}";
    }
}
