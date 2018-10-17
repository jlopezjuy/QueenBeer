package com.anelsoftware.beer.service.mapper;

import com.anelsoftware.beer.domain.*;
import com.anelsoftware.beer.service.dto.ListaPrecioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ListaPrecio and its DTO ListaPrecioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ListaPrecioMapper extends EntityMapper<ListaPrecioDTO, ListaPrecio> {



    default ListaPrecio fromId(Long id) {
        if (id == null) {
            return null;
        }
        ListaPrecio listaPrecio = new ListaPrecio();
        listaPrecio.setId(id);
        return listaPrecio;
    }
}
