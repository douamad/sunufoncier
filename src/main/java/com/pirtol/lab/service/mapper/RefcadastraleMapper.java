package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.RefcadastraleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Refcadastrale} and its DTO {@link RefcadastraleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefcadastraleMapper extends EntityMapper<RefcadastraleDTO, Refcadastrale> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RefcadastraleDTO toDtoId(Refcadastrale refcadastrale);
}
