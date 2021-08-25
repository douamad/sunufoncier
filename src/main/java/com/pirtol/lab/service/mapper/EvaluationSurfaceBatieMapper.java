package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.EvaluationSurfaceBatieDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EvaluationSurfaceBatie} and its DTO {@link EvaluationSurfaceBatieDTO}.
 */
@Mapper(componentModel = "spring", uses = { CategorieBatieMapper.class, DossierMapper.class })
public interface EvaluationSurfaceBatieMapper extends EntityMapper<EvaluationSurfaceBatieDTO, EvaluationSurfaceBatie> {
    @Mapping(target = "categorieBatie", source = "categorieBatie", qualifiedByName = "id")
    @Mapping(target = "dossier", source = "dossier", qualifiedByName = "id")
    EvaluationSurfaceBatieDTO toDto(EvaluationSurfaceBatie s);
}
