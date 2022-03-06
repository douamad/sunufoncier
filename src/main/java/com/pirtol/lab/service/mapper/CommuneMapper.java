package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.CommuneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commune} and its DTO {@link CommuneDTO}.
 */
@Mapper(componentModel = "spring", uses = { ArrondissementMapper.class })
public interface CommuneMapper extends EntityMapper<CommuneDTO, Commune> {
    @Mapping(target = "arrondissement", source = "arrondissement")
    CommuneDTO toDto(Commune s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommuneDTO toDtoId(Commune commune);
}
