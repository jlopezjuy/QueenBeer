package com.anelsoftware.beer.service.mapper;

import com.anelsoftware.beer.domain.*;
import com.anelsoftware.beer.service.dto.ProveedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Proveedor and its DTO ProveedorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProveedorMapper extends EntityMapper<ProveedorDTO, Proveedor> {



    default Proveedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Proveedor proveedor = new Proveedor();
        proveedor.setId(id);
        return proveedor;
    }
}
