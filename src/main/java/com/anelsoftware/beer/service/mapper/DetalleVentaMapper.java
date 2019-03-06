package com.anelsoftware.beer.service.mapper;

import com.anelsoftware.beer.domain.*;
import com.anelsoftware.beer.service.dto.DetalleVentaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DetalleVenta and its DTO DetalleVentaDTO.
 */
@Mapper(componentModel = "spring", uses = {FacturaVentaMapper.class, ProductoMapper.class, EnvaseMapper.class})
public interface DetalleVentaMapper extends EntityMapper<DetalleVentaDTO, DetalleVenta> {

    @Mapping(source = "facturaVenta.id", target = "facturaVentaId")
    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "envase.id", target = "envaseId")
    DetalleVentaDTO toDto(DetalleVenta detalleVenta);

    @Mapping(source = "facturaVentaId", target = "facturaVenta")
    @Mapping(source = "productoId", target = "producto")
    @Mapping(source = "envaseId", target = "envase")
    DetalleVenta toEntity(DetalleVentaDTO detalleVentaDTO);

    default DetalleVenta fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setId(id);
        return detalleVenta;
    }
}
