package com.anelsoftware.beer.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * ListaPrecio entity.
 * esta entidad se usaria para aumentar
 * o disminuir precios globalmente
 * @author The JHipster team.
 */
@ApiModel(description = "ListaPrecio entity. esta entidad se usaria para aumentar o disminuir precios globalmente @author The JHipster team.")
@Entity
@Table(name = "lista_precio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "listaprecio")
public class ListaPrecio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "porcentage", precision = 10, scale = 2)
    private BigDecimal porcentage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public ListaPrecio nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPorcentage() {
        return porcentage;
    }

    public ListaPrecio porcentage(BigDecimal porcentage) {
        this.porcentage = porcentage;
        return this;
    }

    public void setPorcentage(BigDecimal porcentage) {
        this.porcentage = porcentage;
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
        ListaPrecio listaPrecio = (ListaPrecio) o;
        if (listaPrecio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), listaPrecio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ListaPrecio{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", porcentage=" + getPorcentage() +
            "}";
    }
}
