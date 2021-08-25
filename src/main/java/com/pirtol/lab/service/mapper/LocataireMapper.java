package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.LocataireDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Locataire} and its DTO {@link LocataireDTO}.
 */
@Mapper(componentModel = "spring", uses = { DossierMapper.class })
public interface LocataireMapper extends EntityMapper<LocataireDTO, Locataire> {
    @Mapping(target = "dossier", source = "dossier", qualifiedByName = "id")
    LocataireDTO toDto(Locataire s);
}
