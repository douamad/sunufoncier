package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.CategorieCoursAmenageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategorieCoursAmenage} and its DTO {@link CategorieCoursAmenageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategorieCoursAmenageMapper extends EntityMapper<CategorieCoursAmenageDTO, CategorieCoursAmenage> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategorieCoursAmenageDTO toDtoId(CategorieCoursAmenage categorieCoursAmenage);
}
