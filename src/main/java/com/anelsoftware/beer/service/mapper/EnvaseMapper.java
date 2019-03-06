package com.anelsoftware.beer.service.mapper;

import com.anelsoftware.beer.domain.*;
import com.anelsoftware.beer.service.dto.EnvaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Envase and its DTO EnvaseDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface EnvaseMapper extends EntityMapper<EnvaseDTO, Envase> {

    @Mapping(source = "producto.id", target = "productoId")
    EnvaseDTO toDto(Envase envase);

    @Mapping(source = "productoId", target = "producto")
    Envase toEntity(EnvaseDTO envaseDTO);

    default Envase fromId(Long id) {
        if (id == null) {
            return null;
        }
        Envase envase = new Envase();
        envase.setId(id);
        return envase;
    }
}
