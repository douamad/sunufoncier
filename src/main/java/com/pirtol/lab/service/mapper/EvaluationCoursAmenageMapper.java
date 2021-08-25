package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.EvaluationCoursAmenageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EvaluationCoursAmenage} and its DTO {@link EvaluationCoursAmenageDTO}.
 */
@Mapper(componentModel = "spring", uses = { CategorieCoursAmenageMapper.class, DossierMapper.class })
public interface EvaluationCoursAmenageMapper extends EntityMapper<EvaluationCoursAmenageDTO, EvaluationCoursAmenage> {
    @Mapping(target = "categorieCoursAmenage", source = "categorieCoursAmenage", qualifiedByName = "id")
    @Mapping(target = "dossier", source = "dossier", qualifiedByName = "id")
    EvaluationCoursAmenageDTO toDto(EvaluationCoursAmenage s);
}
