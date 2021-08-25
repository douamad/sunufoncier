package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.ProprietaireDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Proprietaire} and its DTO {@link ProprietaireDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProprietaireMapper extends EntityMapper<ProprietaireDTO, Proprietaire> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProprietaireDTO toDtoId(Proprietaire proprietaire);
}
