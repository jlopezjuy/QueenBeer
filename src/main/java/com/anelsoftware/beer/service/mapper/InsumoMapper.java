package com.anelsoftware.beer.service.mapper;

import com.anelsoftware.beer.domain.*;
import com.anelsoftware.beer.service.dto.InsumoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Insumo and its DTO InsumoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InsumoMapper extends EntityMapper<InsumoDTO, Insumo> {


    @Mapping(target = "elaboracionInsumos", ignore = true)
    Insumo toEntity(InsumoDTO insumoDTO);

    default Insumo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Insumo insumo = new Insumo();
        insumo.setId(id);
        return insumo;
    }
}
