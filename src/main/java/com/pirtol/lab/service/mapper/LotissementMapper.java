package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.LotissementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lotissement} and its DTO {@link LotissementDTO}.
 */
@Mapper(componentModel = "spring", uses = { QuartierMapper.class })
public interface LotissementMapper extends EntityMapper<LotissementDTO, Lotissement> {
    @Mapping(target = "quartier", source = "quartier", qualifiedByName = "id")
    LotissementDTO toDto(Lotissement s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LotissementDTO toDtoId(Lotissement lotissement);
}
