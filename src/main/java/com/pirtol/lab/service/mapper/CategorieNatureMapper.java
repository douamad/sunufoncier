package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.CategorieNatureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategorieNature} and its DTO {@link CategorieNatureDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategorieNatureMapper extends EntityMapper<CategorieNatureDTO, CategorieNature> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategorieNatureDTO toDtoId(CategorieNature categorieNature);
}
