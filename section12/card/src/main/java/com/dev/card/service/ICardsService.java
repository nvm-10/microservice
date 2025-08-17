package com.dev.card.service;

import com.dev.card.dto.CardDto;

public interface ICardsService {

    void createCard(String mobileNumber);

    CardDto fetchCard(String mobileNumber);

    boolean updateCard(CardDto cardDto);

    boolean deleteCard(String mobileNumber);
}
