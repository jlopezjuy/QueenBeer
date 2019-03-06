package com.anelsoftware.beer.service.mapper;

import com.anelsoftware.beer.domain.*;
import com.anelsoftware.beer.service.dto.FacturaVentaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FacturaVenta and its DTO FacturaVentaDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface FacturaVentaMapper extends EntityMapper<FacturaVentaDTO, FacturaVenta> {

    @Mapping(source = "cliente.id", target = "clienteId")
    FacturaVentaDTO toDto(FacturaVenta facturaVenta);

    @Mapping(source = "clienteId", target = "cliente")
    FacturaVenta toEntity(FacturaVentaDTO facturaVentaDTO);

    default FacturaVenta fromId(Long id) {
        if (id == null) {
            return null;
        }
        FacturaVenta facturaVenta = new FacturaVenta();
        facturaVenta.setId(id);
        return facturaVenta;
    }
}
