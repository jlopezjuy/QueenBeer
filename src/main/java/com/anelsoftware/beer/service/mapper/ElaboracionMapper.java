package com.anelsoftware.beer.service.mapper;

import com.anelsoftware.beer.domain.*;
import com.anelsoftware.beer.service.dto.ElaboracionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Elaboracion and its DTO ElaboracionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ElaboracionMapper extends EntityMapper<ElaboracionDTO, Elaboracion> {


    @Mapping(target = "elaboracionInsumos", ignore = true)
    Elaboracion toEntity(ElaboracionDTO elaboracionDTO);

    default Elaboracion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Elaboracion elaboracion = new Elaboracion();
        elaboracion.setId(id);
        return elaboracion;
    }
}
