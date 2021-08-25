package com.pirtol.lab.service.mapper;

import com.pirtol.lab.domain.*;
import com.pirtol.lab.service.dto.NatureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nature} and its DTO {@link NatureDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NatureMapper extends EntityMapper<NatureDTO, Nature> {}
