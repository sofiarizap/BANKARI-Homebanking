package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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
  CardService cardService;
  @Autowired
  ClientService clientService;

  @GetMapping("/cards")
  public List<CardDTO> getCards() {
    return cardService.findAll().stream().map(card-> new CardDTO(card)).collect(toList());
  }
  @GetMapping("/cards/{id}")
  public CardDTO getCard(@PathVariable Long id) {
    return new CardDTO(cardService.findById(id)) ;
  }


  @PostMapping(path = "clients/current/cards")
  public ResponseEntity<Object> createCard(Authentication authentication,
                                           @RequestParam @Nullable CardType cardType, @RequestParam @Nullable CardColor cardColor) {

    Client client = clientService.findByEmail(authentication.getName());
    Set<Card> cards = client.getCards();
    List<CardDTO> CardDTOAll = cardService.findAll().stream().map(CardDTO::new).collect(Collectors.toList());
    List<CardDTO> CardDTOActive= CardDTOAll.stream().filter(CardDTO::getActive).collect(Collectors.toList());

    List<Card> numCards = cards.stream().filter(card -> card.getType() == cardType).collect(toList());

    if(cardColor == null ){
      return new ResponseEntity<>("MISSING DATA, SPECIFIES THE COLOR  OF THE CARD", HttpStatus.FORBIDDEN);
    }
    if(cardType == null){
      return new ResponseEntity<>("MISSING DATA, SPECIFIES THE TYPE OF THE CARD", HttpStatus.FORBIDDEN);
    }
    if (numCards.size() == 3) {
      return new ResponseEntity<>("LIMIT OF CREATED CARDS OF THE SAME TYPE REACHED", HttpStatus.FORBIDDEN);
    }
    if( CardDTOActive.stream().anyMatch(card -> card.getCardColor() == cardColor && card.getType() == cardType)){
      return new ResponseEntity<>("YOU ALREADY HAVE THAT CARD", HttpStatus.FORBIDDEN);
    }

    String cardNumber;
    do {
      cardNumber = CardUtils.getCardNumber();
    }
    while(cardService.findByNumber(cardNumber) != null);

    LocalDate thruDate = LocalDate.now();
    LocalDate fromDate = thruDate.plusYears(5);

    Card card = new Card(client.getFirstName() + "" + client.getLastName(), cardType, cardColor, cardNumber,CardUtils.getCVV() , fromDate, thruDate, client, true);
    cardService.save(card);
    return new ResponseEntity<>("NEW CARD CREATED", HttpStatus.CREATED);

  }

  @PatchMapping("/clients/current/cards/delete/{id}")
  public ResponseEntity<Object> deleteCard(@PathVariable Long id){
    Card card = cardService.findById(id);


    card.setActive(false);
    cardService.save(card);

    return new ResponseEntity<>("CARD DISABLED", HttpStatus.CREATED);
  }

}
