package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.RefParcelaireDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RefParcelaire} and its DTO {@link RefParcelaireDTO}.
 */
@Mapper(componentModel = "spring", uses = { CommuneMapper.class })
public interface RefParcelaireMapper extends EntityMapper<RefParcelaireDTO, RefParcelaire> {
    @Mapping(target = "commune", source = "commune", qualifiedByName = "id")
    RefParcelaireDTO toDto(RefParcelaire s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RefParcelaireDTO toDtoId(RefParcelaire refParcelaire);
}
