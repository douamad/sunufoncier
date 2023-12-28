package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.Locataire;
import com.pirtol.lab.repository.LocataireRepository;
import com.pirtol.lab.service.LocataireService;
import com.pirtol.lab.service.dto.LocataireDTO;
import com.pirtol.lab.service.mapper.LocataireMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Locataire}.
 */
@Service
@Transactional
public class LocataireServiceImpl implements LocataireService {

    private final Logger log = LoggerFactory.getLogger(LocataireServiceImpl.class);

    private final LocataireRepository locataireRepository;

    private final LocataireMapper locataireMapper;

    public LocataireServiceImpl(LocataireRepository locataireRepository, LocataireMapper locataireMapper) {
        this.locataireRepository = locataireRepository;
        this.locataireMapper = locataireMapper;
    }

    @Override
    public LocataireDTO save(LocataireDTO locataireDTO) {
        log.debug("Request to save Locataire : {}", locataireDTO);
        Locataire locataire = locataireMapper.toEntity(locataireDTO);
        locataire = locataireRepository.save(locataire);
        return locataireMapper.toDto(locataire);
    }

    @Override
    public List<LocataireDTO> saveBulk(List<LocataireDTO> locataireDTO) {
        return locataireDTO.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public Optional<LocataireDTO> partialUpdate(LocataireDTO locataireDTO) {
        log.debug("Request to partially update Locataire : {}", locataireDTO);

        return locataireRepository
            .findById(locataireDTO.getId())
            .map(
                existingLocataire -> {
                    locataireMapper.partialUpdate(existingLocataire, locataireDTO);
                    return existingLocataire;
                }
            )
            .map(locataireRepository::save)
            .map(locataireMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LocataireDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Locataires");
        return locataireRepository.findAll(pageable).map(locataireMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LocataireDTO> findOne(Long id) {
        log.debug("Request to get Locataire : {}", id);
        return locataireRepository.findById(id).map(locataireMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Locataire : {}", id);
        locataireRepository.deleteById(id);
    }
}
