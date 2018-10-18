package com.anelsoftware.beer.service.mapper;

import com.anelsoftware.beer.domain.*;
import com.anelsoftware.beer.service.dto.CompraInsumoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CompraInsumo and its DTO CompraInsumoDTO.
 */
@Mapper(componentModel = "spring", uses = {CompraMapper.class, InsumoMapper.class})
public interface CompraInsumoMapper extends EntityMapper<CompraInsumoDTO, CompraInsumo> {

    @Mapping(source = "compra.id", target = "compraId")
    @Mapping(source = "insumo.id", target = "insumoId")
    CompraInsumoDTO toDto(CompraInsumo compraInsumo);

    @Mapping(source = "compraId", target = "compra")
    @Mapping(source = "insumoId", target = "insumo")
    CompraInsumo toEntity(CompraInsumoDTO compraInsumoDTO);

    default CompraInsumo fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompraInsumo compraInsumo = new CompraInsumo();
        compraInsumo.setId(id);
        return compraInsumo;
    }
}
