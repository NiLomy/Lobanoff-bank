package ru.kpfu.itis.lobanov.commissionservice.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.commissionservice.entities.Transaction;
import ru.kpfu.itis.lobanov.commissionservice.services.CommissionChargeService;

@Service
@RequiredArgsConstructor
public class CommissionChargeServiceImpl implements CommissionChargeService {
    @Override
    public void chargeCommission(@NonNull Transaction transaction) {

    }
}
