package ru.kpfu.itis.lobanov.data.services;

import ru.kpfu.itis.lobanov.dtos.*;

import java.util.List;

public interface OperationService {
    List<OperationDto> getAllOperationsFromUser(UserDto userDto);

    List<OperationDto> findAllByUserLimitRecent(BankAccountDto bankAccountDto);

    OperationDto transferByPhone(Long accountId, PhoneTransferForm phoneTransferForm);

    OperationDto transferBetweenAccounts(BetweenAccountsTransferForm betweenAccountsTransferForm);

    OperationDto transferByCard(CardTransferForm cardTransferForm);

    OperationDto transferByAccountDetails(AccountDetailsTransferForm accountDetailsTransferForm);

    OperationDto replenishByCard(CardReplenishForm cardReplenishForm);
}
