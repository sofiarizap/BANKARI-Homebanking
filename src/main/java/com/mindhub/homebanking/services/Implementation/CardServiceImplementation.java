package com.mindhub.homebanking.services.Implementation;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImplementation implements CardService {

  @Autowired
  CardRepository cardRepository;

  @Override
  public List<Card> findAll() {
    return cardRepository.findAll();
  }

  @Override
  public Card findById(Long id) {
    return cardRepository.findById(id).orElse(null);
  }

  @Override
  public Card save(Card card) {
    return cardRepository.save(card);
  }

  @Override
  public Card findByNumber(String number) {
    return cardRepository.findByNumber(number);
  }
}
