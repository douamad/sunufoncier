package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.RepresentantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Representant} and its DTO {@link RepresentantDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProprietaireMapper.class })
public interface RepresentantMapper extends EntityMapper<RepresentantDTO, Representant> {
    @Mapping(target = "proprietaire", source = "proprietaire", qualifiedByName = "id")
    RepresentantDTO toDto(Representant s);
}
