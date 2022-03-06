package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.ArrondissementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Arrondissement} and its DTO {@link ArrondissementDTO}.
 */
@Mapper(componentModel = "spring", uses = { DepartementMapper.class })
public interface ArrondissementMapper extends EntityMapper<ArrondissementDTO, Arrondissement> {
    @Mapping(target = "departement", source = "departement")
    ArrondissementDTO toDto(Arrondissement s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArrondissementDTO toDtoId(Arrondissement arrondissement);
}
