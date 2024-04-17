package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;
import ru.kpfu.itis.lobanov.data.mappers.impl.RequisitesMapper;
import ru.kpfu.itis.lobanov.data.repositories.BankAccountRepository;
import ru.kpfu.itis.lobanov.data.repositories.RequisitesRepository;
import ru.kpfu.itis.lobanov.data.services.RequisitesService;
import ru.kpfu.itis.lobanov.dtos.RequisitesDto;

@Service
@Transactional
@RequiredArgsConstructor
public class RequisitesServiceImpl implements RequisitesService {
    private final BankAccountRepository bankAccountRepository;
    private final RequisitesRepository requisitesRepository;
    private final RequisitesMapper requisitesMapper;

    @Override
    public RequisitesDto getRequisites(Long accountId) {
        BankAccount account = bankAccountRepository.findById(accountId).orElseThrow(IllegalArgumentException::new);
        return requisitesMapper.toResponse(requisitesRepository.findByPayeeAccount(account));
    }
}
