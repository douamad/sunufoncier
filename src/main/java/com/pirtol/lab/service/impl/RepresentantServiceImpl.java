package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.Representant;
import com.pirtol.lab.repository.RepresentantRepository;
import com.pirtol.lab.service.RepresentantService;
import com.pirtol.lab.service.dto.RepresentantDTO;
import com.pirtol.lab.service.mapper.RepresentantMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Representant}.
 */
@Service
@Transactional
public class RepresentantServiceImpl implements RepresentantService {

    private final Logger log = LoggerFactory.getLogger(RepresentantServiceImpl.class);

    private final RepresentantRepository representantRepository;

    private final RepresentantMapper representantMapper;

    public RepresentantServiceImpl(RepresentantRepository representantRepository, RepresentantMapper representantMapper) {
        this.representantRepository = representantRepository;
        this.representantMapper = representantMapper;
    }

    @Override
    public RepresentantDTO save(RepresentantDTO representantDTO) {
        log.debug("Request to save Representant : {}", representantDTO);
        Representant representant = representantMapper.toEntity(representantDTO);
        representant = representantRepository.save(representant);
        return representantMapper.toDto(representant);
    }

    @Override
    public Optional<RepresentantDTO> partialUpdate(RepresentantDTO representantDTO) {
        log.debug("Request to partially update Representant : {}", representantDTO);

        return representantRepository
            .findById(representantDTO.getId())
            .map(
                existingRepresentant -> {
                    representantMapper.partialUpdate(existingRepresentant, representantDTO);
                    return existingRepresentant;
                }
            )
            .map(representantRepository::save)
            .map(representantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RepresentantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Representants");
        return representantRepository.findAll(pageable).map(representantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RepresentantDTO> findOne(Long id) {
        log.debug("Request to get Representant : {}", id);
        return representantRepository.findById(id).map(representantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Representant : {}", id);
        representantRepository.deleteById(id);
    }
}
