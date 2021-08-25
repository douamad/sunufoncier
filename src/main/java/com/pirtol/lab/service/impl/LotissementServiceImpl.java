package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.Lotissement;
import com.pirtol.lab.repository.LotissementRepository;
import com.pirtol.lab.service.LotissementService;
import com.pirtol.lab.service.dto.LotissementDTO;
import com.pirtol.lab.service.mapper.LotissementMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Lotissement}.
 */
@Service
@Transactional
public class LotissementServiceImpl implements LotissementService {

    private final Logger log = LoggerFactory.getLogger(LotissementServiceImpl.class);

    private final LotissementRepository lotissementRepository;

    private final LotissementMapper lotissementMapper;

    public LotissementServiceImpl(LotissementRepository lotissementRepository, LotissementMapper lotissementMapper) {
        this.lotissementRepository = lotissementRepository;
        this.lotissementMapper = lotissementMapper;
    }

    @Override
    public LotissementDTO save(LotissementDTO lotissementDTO) {
        log.debug("Request to save Lotissement : {}", lotissementDTO);
        Lotissement lotissement = lotissementMapper.toEntity(lotissementDTO);
        lotissement = lotissementRepository.save(lotissement);
        return lotissementMapper.toDto(lotissement);
    }

    @Override
    public Optional<LotissementDTO> partialUpdate(LotissementDTO lotissementDTO) {
        log.debug("Request to partially update Lotissement : {}", lotissementDTO);

        return lotissementRepository
            .findById(lotissementDTO.getId())
            .map(
                existingLotissement -> {
                    lotissementMapper.partialUpdate(existingLotissement, lotissementDTO);
                    return existingLotissement;
                }
            )
            .map(lotissementRepository::save)
            .map(lotissementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LotissementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lotissements");
        return lotissementRepository.findAll(pageable).map(lotissementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LotissementDTO> findOne(Long id) {
        log.debug("Request to get Lotissement : {}", id);
        return lotissementRepository.findById(id).map(lotissementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lotissement : {}", id);
        lotissementRepository.deleteById(id);
    }
}
