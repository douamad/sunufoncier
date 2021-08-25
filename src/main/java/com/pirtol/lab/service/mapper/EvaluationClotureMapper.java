package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.EvaluationClotureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EvaluationCloture} and its DTO {@link EvaluationClotureDTO}.
 */
@Mapper(componentModel = "spring", uses = { CategorieClotureMapper.class, DossierMapper.class })
public interface EvaluationClotureMapper extends EntityMapper<EvaluationClotureDTO, EvaluationCloture> {
    @Mapping(target = "categoriteCloture", source = "categoriteCloture", qualifiedByName = "id")
    @Mapping(target = "dossier", source = "dossier", qualifiedByName = "id")
    EvaluationClotureDTO toDto(EvaluationCloture s);
}
