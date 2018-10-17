package com.anelsoftware.beer.service.mapper;

import com.anelsoftware.beer.domain.*;
import com.anelsoftware.beer.service.dto.ElaboracionInsumoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ElaboracionInsumo and its DTO ElaboracionInsumoDTO.
 */
@Mapper(componentModel = "spring", uses = {ElaboracionMapper.class, InsumoMapper.class})
public interface ElaboracionInsumoMapper extends EntityMapper<ElaboracionInsumoDTO, ElaboracionInsumo> {

    @Mapping(source = "elaboracion.id", target = "elaboracionId")
    @Mapping(source = "insumo.id", target = "insumoId")
    ElaboracionInsumoDTO toDto(ElaboracionInsumo elaboracionInsumo);

    @Mapping(source = "elaboracionId", target = "elaboracion")
    @Mapping(source = "insumoId", target = "insumo")
    ElaboracionInsumo toEntity(ElaboracionInsumoDTO elaboracionInsumoDTO);

    default ElaboracionInsumo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ElaboracionInsumo elaboracionInsumo = new ElaboracionInsumo();
        elaboracionInsumo.setId(id);
        return elaboracionInsumo;
    }
}
