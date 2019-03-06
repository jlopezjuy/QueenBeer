package com.anelsoftware.beer.service.mapper;

import com.anelsoftware.beer.domain.*;
import com.anelsoftware.beer.service.dto.ProductoEnvaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductoEnvase and its DTO ProductoEnvaseDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class, EnvaseMapper.class})
public interface ProductoEnvaseMapper extends EntityMapper<ProductoEnvaseDTO, ProductoEnvase> {

    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "envase.id", target = "envaseId")
    ProductoEnvaseDTO toDto(ProductoEnvase productoEnvase);

    @Mapping(source = "productoId", target = "producto")
    @Mapping(source = "envaseId", target = "envase")
    ProductoEnvase toEntity(ProductoEnvaseDTO productoEnvaseDTO);

    default ProductoEnvase fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductoEnvase productoEnvase = new ProductoEnvase();
        productoEnvase.setId(id);
        return productoEnvase;
    }
}
