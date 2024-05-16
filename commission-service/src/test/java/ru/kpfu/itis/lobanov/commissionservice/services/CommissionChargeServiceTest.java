package ru.kpfu.itis.lobanov.commissionservice.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kpfu.itis.lobanov.commissionservice.entities.Transaction;
import ru.kpfu.itis.lobanov.commissionservice.services.impl.CommissionChargeServiceImpl;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CommissionChargeServiceTest {
    private static MessagingService messagingService;
    private static Transaction transaction;
    private CommissionChargeService commissionChargeService;

    @BeforeAll
    public static void setUpMocks() {
        messagingService = Mockito.mock(MessagingService.class);

        transaction = Transaction.builder()
                .id(1L)
                .date(new Timestamp(100000))
                .initAmount(BigDecimal.TEN)
                .from(1L)
                .to(2L)
                .bankNameFrom("Lobanoff bank")
                .bankNameTo("Lobanoff bank")
                .serviceCompany("Lobanoff bank")
                .build();
        doNothing().when(messagingService).sendTransactionToChargeCashback(transaction);
    }

    @BeforeEach
    public void setUpCommissionChargeService() {
        commissionChargeService = new CommissionChargeServiceImpl(messagingService);
    }

    @Test
    public void testChargeCommission() {
        commissionChargeService.chargeCommission(transaction);
        verify(messagingService, times(1)).sendTransactionToChargeCashback(transaction);
        verifyNoMoreInteractions(messagingService);
    }

}
