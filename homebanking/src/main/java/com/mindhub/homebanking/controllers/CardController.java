package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {

  @Autowired
  CardRepository cardRepository;
  @Autowired
  ClientRepository clientRepository;

  @GetMapping("/cards")
  public List<CardDTO> getCards() {
    return cardRepository.findAll().stream().map(card-> new CardDTO(card)).collect(toList());
  }
  @GetMapping("/cards/{id}")
  public CardDTO getCard(@PathVariable Long id) {
    return cardRepository.findById(id).map( card -> new CardDTO(card)).orElse(null);
  }

  int min1 = 0000;
  int max1 = 9999;

  public int getRandomNum(int min1, int max1) {
    return (int) ((Math.random() * (max1 - min1)) - min1);
  }

  public String getStringNum() {
    int randomCardNumber = getRandomNum(min1, max1);
    return String.valueOf(randomCardNumber);
  }

  public String getStringCard() {
    String cardNumber = "";
    for (int i = 0; i <= 3; i++) {
      String serie = getStringNum();
      cardNumber += ("-" + serie);
    }
    return cardNumber.substring(1);
  }

  int min2 = 000;
  int max2 = 999;

  public int getCvvNumber(int min2, int max2) {
    return (int) ((Math.random() * (this.max2 - this.min2)) + this.min2);
  }

  @PostMapping(path = "clients/current/cards")
  public ResponseEntity<Object> createCard(Authentication authentication,
                                           @RequestParam CardType cardType, @RequestParam CardColor cardColor) {

    Client client = clientRepository.findByEmail(authentication.getName());
    Set<Card> cards = client.getCards();
    List<Card> numCards = cards.stream().filter(card -> card.getType() == cardType).collect(toList());

    if(cardColor == null || cardType == null){
      return new ResponseEntity<>("Missing data, specifies the color and type of the card", HttpStatus.FORBIDDEN);
    }
    if (numCards.size() == 3) {
      return new ResponseEntity<>("Limit of created cards of the same type reached", HttpStatus.FORBIDDEN);
    }
    if( cards.stream().anyMatch(card -> card.getCardColor() == cardColor && card.getType() == cardType)){
      return new ResponseEntity<>("You already have that card.", HttpStatus.FORBIDDEN);
    }

    String cardNumber = getStringCard();
    int cvv = getCvvNumber(000, 999);

    LocalDate thruDate = LocalDate.now();
    LocalDate fromDate = thruDate.plusYears(5);

    Card card = new Card(client.getFirstName() + "" + client.getLastName(), cardType, cardColor, cardNumber, cvv, fromDate, thruDate, client);
    cardRepository.save(card);
    return new ResponseEntity<>("New Card created", HttpStatus.CREATED);

  }
}
