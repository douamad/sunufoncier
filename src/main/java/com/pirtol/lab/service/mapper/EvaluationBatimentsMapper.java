package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.EvaluationBatimentsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EvaluationBatiments} and its DTO {@link EvaluationBatimentsDTO}.
 */
@Mapper(componentModel = "spring", uses = { CategorieNatureMapper.class, DossierMapper.class })
public interface EvaluationBatimentsMapper extends EntityMapper<EvaluationBatimentsDTO, EvaluationBatiments> {
    @Mapping(target = "categorieNature", source = "categorieNature", qualifiedByName = "id")
    @Mapping(target = "dossier", source = "dossier", qualifiedByName = "id")
    EvaluationBatimentsDTO toDto(EvaluationBatiments s);
}
