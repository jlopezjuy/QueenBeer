package com.anelsoftware.beer.service.mapper;

import com.anelsoftware.beer.domain.*;
import com.anelsoftware.beer.service.dto.CompraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Compra and its DTO CompraDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompraMapper extends EntityMapper<CompraDTO, Compra> {


    @Mapping(target = "compraInsumos", ignore = true)
    Compra toEntity(CompraDTO compraDTO);

    default Compra fromId(Long id) {
        if (id == null) {
            return null;
        }
        Compra compra = new Compra();
        compra.setId(id);
        return compra;
    }
}
