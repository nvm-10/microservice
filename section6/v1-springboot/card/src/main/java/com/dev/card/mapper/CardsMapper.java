package com.dev.card.mapper;

import com.dev.card.dto.CardDto;
import com.dev.card.entity.Cards;


public class CardsMapper {

    public static CardDto toCardDto(Cards cards, CardDto cardDto) {
        cardDto.setMobileNumber(cards.getMobileNumber());
        cardDto.setCardNumber(cards.getCardNumber());
        cardDto.setCardType(cards.getCardType());
        cardDto.setAmountUsed(cards.getAmountUsed());
        cardDto.setAvailableAmount(cards.getAvailableAmount());
        cardDto.setTotalLimit(cards.getTotalLimit());
        return cardDto;
    }

    public static Cards toCards(CardDto cardDto, Cards cards) {
        cards.setMobileNumber(cardDto.getMobileNumber());
        cards.setCardNumber(cardDto.getCardNumber());
        cards.setCardType(cardDto.getCardType());
        cards.setAmountUsed(cardDto.getAmountUsed());
        cards.setAvailableAmount(cardDto.getAvailableAmount());
        cards.setTotalLimit(cardDto.getTotalLimit());
        return cards;
    }
}
