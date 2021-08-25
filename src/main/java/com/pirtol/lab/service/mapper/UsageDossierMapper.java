package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.UsageDossierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UsageDossier} and its DTO {@link UsageDossierDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UsageDossierMapper extends EntityMapper<UsageDossierDTO, UsageDossier> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsageDossierDTO toDtoId(UsageDossier usageDossier);
}
