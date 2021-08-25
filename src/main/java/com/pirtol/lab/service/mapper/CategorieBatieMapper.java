package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.CategorieBatieDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategorieBatie} and its DTO {@link CategorieBatieDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategorieBatieMapper extends EntityMapper<CategorieBatieDTO, CategorieBatie> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategorieBatieDTO toDtoId(CategorieBatie categorieBatie);
}
