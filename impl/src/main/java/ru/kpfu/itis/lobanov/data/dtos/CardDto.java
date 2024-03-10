package ru.kpfu.itis.lobanov.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.lobanov.data.entities.Card;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {
    private Long id;
    private String number;
    private UserDto owner;

    public static CardDto fromCard(Card card) {
        return CardDto.builder()
                .id(card.getId())
                .number(card.getNumber())
                .owner(UserDto.fromUser(card.getOwner()))
                .build();
    }
}
