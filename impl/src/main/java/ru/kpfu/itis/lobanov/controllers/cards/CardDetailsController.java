package ru.kpfu.itis.lobanov.controllers.cards;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.kpfu.itis.lobanov.api.cards.CardDetailsApi;
import ru.kpfu.itis.lobanov.data.services.CardService;
import ru.kpfu.itis.lobanov.dtos.CardDto;

import static ru.kpfu.itis.lobanov.utils.NamingConstants.CURRENT_CARD_KEY;
import static ru.kpfu.itis.lobanov.utils.NamingConstants.CURRENT_USER_KEY;

@Controller
@SessionAttributes(CURRENT_USER_KEY)
@RequiredArgsConstructor
public class CardDetailsController implements CardDetailsApi {
    private final CardService cardService;

    @Override
    public String getCardDetailsPage(String cardId, Model model) {
        CardDto currentCard = cardService.getById(Long.parseLong(cardId));

        model.addAttribute(CURRENT_CARD_KEY, currentCard);

        return "cards/card_details";
    }
}
