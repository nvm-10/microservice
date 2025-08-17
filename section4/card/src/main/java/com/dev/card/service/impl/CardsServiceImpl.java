package com.dev.card.service.impl;

import com.dev.card.constants.CardsConstants;
import com.dev.card.dto.CardDto;
import com.dev.card.entity.Cards;
import com.dev.card.exception.CardAlreadyExistException;
import com.dev.card.exception.ResourceNotFoundException;
import com.dev.card.mapper.CardsMapper;
import com.dev.card.repository.CardsRepository;
import com.dev.card.service.ICardsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class CardsServiceImpl implements ICardsService {

    private final CardsRepository cardsRepository;

    public CardsServiceImpl(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
    }


    @Override
    public void createCard(String mobileNumber) {
    Optional<Cards> cards = cardsRepository.findByMobileNumber(mobileNumber);
    if (cards.isPresent()) {
        throw new CardAlreadyExistException("Card already exist for mobile number "
                + mobileNumber);
    }
    cardsRepository.save(createNewCard(mobileNumber));

    }

    @Override
    public CardDto fetchCard(String mobileNumber) {
        Cards card = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Cards", "mobileNumber", mobileNumber));

        return CardsMapper.toCardDto(card, new CardDto());
    }

    @Override
    public boolean updateCard(CardDto cardDto) {
        Cards cards = cardsRepository.findByMobileNumber(cardDto.getCardNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Cards", "mobileNumber", cardDto.getCardNumber()));
        cardsRepository.save(CardsMapper.toCards(cardDto, cards));
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }


    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        newCard.setCreatedAt(LocalDateTime.now());
        newCard.setCreatedBy(System.getProperty("user.name"));
        return newCard;
    }
}
