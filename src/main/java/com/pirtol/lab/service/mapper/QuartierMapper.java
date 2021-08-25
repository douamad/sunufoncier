package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.QuartierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Quartier} and its DTO {@link QuartierDTO}.
 */
@Mapper(componentModel = "spring", uses = { CommuneMapper.class })
public interface QuartierMapper extends EntityMapper<QuartierDTO, Quartier> {
    @Mapping(target = "communune", source = "communune", qualifiedByName = "id")
    QuartierDTO toDto(Quartier s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QuartierDTO toDtoId(Quartier quartier);
}
