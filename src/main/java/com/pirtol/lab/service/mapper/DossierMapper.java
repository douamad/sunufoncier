package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.DossierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dossier} and its DTO {@link DossierDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        LotissementMapper.class, UsageDossierMapper.class, ProprietaireMapper.class, RefParcelaireMapper.class, RefcadastraleMapper.class,
    }
)
public interface DossierMapper extends EntityMapper<DossierDTO, Dossier> {
    @Mapping(target = "dossier", source = "dossier", qualifiedByName = "id")
    @Mapping(target = "usageDossier", source = "usageDossier", qualifiedByName = "id")
    @Mapping(target = "proprietaire", source = "proprietaire", qualifiedByName = "id")
    @Mapping(target = "refParcelaire", source = "refParcelaire", qualifiedByName = "id")
    @Mapping(target = "refcadastrale", source = "refcadastrale", qualifiedByName = "id")
    DossierDTO toDto(Dossier s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DossierDTO toDtoId(Dossier dossier);
}
