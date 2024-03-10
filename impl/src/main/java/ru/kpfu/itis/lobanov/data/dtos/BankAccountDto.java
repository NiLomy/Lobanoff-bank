package ru.kpfu.itis.lobanov.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDto {
    private Long id;
    private String name;
    private Long deposit;
    private UserDto owner;
    private List<OperationDto> operations;
    private List<CardDto> cards;

    public static BankAccountDto fromBankAccount(BankAccount bankAccount) {
        BankAccountDtoBuilder bankAccountDtoBuilder = BankAccountDto.builder()
                .id(bankAccount.getId())
                .name(bankAccount.getName())
                .deposit(bankAccount.getDeposit())
                .owner(UserDto.fromUser(bankAccount.getOwner()))
                .operations(bankAccount.getOperations().stream().map(OperationDto::fromOperation).collect(Collectors.toList()));
        if (bankAccount.getCards() != null && !bankAccount.getCards().isEmpty()) {
            bankAccountDtoBuilder.cards(bankAccount.getCards().stream().map(CardDto::fromCard).collect(Collectors.toList()));
        }
        return bankAccountDtoBuilder.build();
    }
}
