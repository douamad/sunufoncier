package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.RefParcelaire;
import com.pirtol.lab.repository.RefParcelaireRepository;
import com.pirtol.lab.service.RefParcelaireService;
import com.pirtol.lab.service.dto.RefParcelaireDTO;
import com.pirtol.lab.service.mapper.RefParcelaireMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RefParcelaire}.
 */
@Service
@Transactional
public class RefParcelaireServiceImpl implements RefParcelaireService {

    private final Logger log = LoggerFactory.getLogger(RefParcelaireServiceImpl.class);

    private final RefParcelaireRepository refParcelaireRepository;

    private final RefParcelaireMapper refParcelaireMapper;

    public RefParcelaireServiceImpl(RefParcelaireRepository refParcelaireRepository, RefParcelaireMapper refParcelaireMapper) {
        this.refParcelaireRepository = refParcelaireRepository;
        this.refParcelaireMapper = refParcelaireMapper;
    }

    @Override
    public RefParcelaireDTO save(RefParcelaireDTO refParcelaireDTO) {
        log.debug("Request to save RefParcelaire : {}", refParcelaireDTO);
        RefParcelaire refParcelaire = refParcelaireMapper.toEntity(refParcelaireDTO);
        refParcelaire = refParcelaireRepository.save(refParcelaire);
        return refParcelaireMapper.toDto(refParcelaire);
    }

    @Override
    public Optional<RefParcelaireDTO> partialUpdate(RefParcelaireDTO refParcelaireDTO) {
        log.debug("Request to partially update RefParcelaire : {}", refParcelaireDTO);

        return refParcelaireRepository
            .findById(refParcelaireDTO.getId())
            .map(
                existingRefParcelaire -> {
                    refParcelaireMapper.partialUpdate(existingRefParcelaire, refParcelaireDTO);
                    return existingRefParcelaire;
                }
            )
            .map(refParcelaireRepository::save)
            .map(refParcelaireMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RefParcelaireDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefParcelaires");
        return refParcelaireRepository.findAll(pageable).map(refParcelaireMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RefParcelaireDTO> findOne(Long id) {
        log.debug("Request to get RefParcelaire : {}", id);
        return refParcelaireRepository.findById(id).map(refParcelaireMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefParcelaire : {}", id);
        refParcelaireRepository.deleteById(id);
    }
}
