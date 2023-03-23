package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;

import java.util.List;

public interface CardService {

  public List<Card> findAll();
  public Card findById(Long id);
  public  Card save(Card card);
  public Card findByNumber (String number);
}
