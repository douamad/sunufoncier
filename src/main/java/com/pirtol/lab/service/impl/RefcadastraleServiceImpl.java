package com.pirtol.lab.service.impl;

import com.pirtol.lab.domain.Refcadastrale;
import com.pirtol.lab.repository.RefcadastraleRepository;
import com.pirtol.lab.service.RefcadastraleService;
import com.pirtol.lab.service.dto.RefcadastraleDTO;
import com.pirtol.lab.service.mapper.RefcadastraleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Refcadastrale}.
 */
@Service
@Transactional
public class RefcadastraleServiceImpl implements RefcadastraleService {

    private final Logger log = LoggerFactory.getLogger(RefcadastraleServiceImpl.class);

    private final RefcadastraleRepository refcadastraleRepository;

    private final RefcadastraleMapper refcadastraleMapper;

    public RefcadastraleServiceImpl(RefcadastraleRepository refcadastraleRepository, RefcadastraleMapper refcadastraleMapper) {
        this.refcadastraleRepository = refcadastraleRepository;
        this.refcadastraleMapper = refcadastraleMapper;
    }

    @Override
    public RefcadastraleDTO save(RefcadastraleDTO refcadastraleDTO) {
        log.debug("Request to save Refcadastrale : {}", refcadastraleDTO);
        Refcadastrale refcadastrale = refcadastraleMapper.toEntity(refcadastraleDTO);
        refcadastrale = refcadastraleRepository.save(refcadastrale);
        return refcadastraleMapper.toDto(refcadastrale);
    }

    @Override
    public Optional<RefcadastraleDTO> partialUpdate(RefcadastraleDTO refcadastraleDTO) {
        log.debug("Request to partially update Refcadastrale : {}", refcadastraleDTO);

        return refcadastraleRepository
            .findById(refcadastraleDTO.getId())
            .map(
                existingRefcadastrale -> {
                    refcadastraleMapper.partialUpdate(existingRefcadastrale, refcadastraleDTO);
                    return existingRefcadastrale;
                }
            )
            .map(refcadastraleRepository::save)
            .map(refcadastraleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RefcadastraleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Refcadastrales");
        return refcadastraleRepository.findAll(pageable).map(refcadastraleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RefcadastraleDTO> findOne(Long id) {
        log.debug("Request to get Refcadastrale : {}", id);
        return refcadastraleRepository.findById(id).map(refcadastraleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Refcadastrale : {}", id);
        refcadastraleRepository.deleteById(id);
    }
}
