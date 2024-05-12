package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.data.entities.Account;
import ru.kpfu.itis.lobanov.data.mappers.impl.RequisitesMapper;
import ru.kpfu.itis.lobanov.data.repositories.AccountRepository;
import ru.kpfu.itis.lobanov.data.repositories.RequisitesRepository;
import ru.kpfu.itis.lobanov.data.services.RequisitesService;
import ru.kpfu.itis.lobanov.dtos.RequisitesDto;

@Service
@Transactional
@RequiredArgsConstructor
public class RequisitesServiceImpl implements RequisitesService {
    private final AccountRepository accountRepository;
    private final RequisitesRepository requisitesRepository;
    private final RequisitesMapper requisitesMapper;

    @Override
    public RequisitesDto getById(Long requisitesId) {
        return requisitesMapper.toResponse(requisitesRepository.findById(requisitesId).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public RequisitesDto getRequisites(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(IllegalArgumentException::new);
        return requisitesMapper.toResponse(requisitesRepository.findByPayeeAccount(account));
    }
}
