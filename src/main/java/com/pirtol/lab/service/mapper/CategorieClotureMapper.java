package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.CategorieClotureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategorieCloture} and its DTO {@link CategorieClotureDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategorieClotureMapper extends EntityMapper<CategorieClotureDTO, CategorieCloture> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategorieClotureDTO toDtoId(CategorieCloture categorieCloture);
}
