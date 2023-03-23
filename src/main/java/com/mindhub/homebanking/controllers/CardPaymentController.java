package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardPaymentDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class CardPaymentController {

  @Autowired
  CardService cardService;
  @Autowired
  ClientService clientService;
  @Autowired
  AccountService accountService;
  @Autowired
  TransactionService transactionService;

  @Transactional
  @PostMapping("/payment")
  public ResponseEntity<Object> paymentCard(Authentication authentication, @RequestBody CardPaymentDTO cardPaymentDTO){
    Client client = clientService.findByEmail(authentication.getName());
    Card card = cardService.findByNumber(cardPaymentDTO.getNumber());
    Account account = accountService.findByNumber(cardPaymentDTO.getAccount());
    Boolean hasCard = client.getCards().contains(card);
    Boolean hasAccount = client.getAccounts().contains(account);

    if(client == null){
      return new ResponseEntity<>("CLIENT NOT FOUND", HttpStatus.FORBIDDEN);
    }
    if(!hasCard){
      return new ResponseEntity<>("THE CARD DOESN'T BELONG TO YOU", HttpStatus.FORBIDDEN);
    }
    if(!hasAccount){
      return new ResponseEntity<>("THE ACCOUNT DOESN'T BELONG TO YOU", HttpStatus.FORBIDDEN);
    }
    if(!account.getActive()){
      return new ResponseEntity<>("ACCOUNT IS NOT ACTIVE", HttpStatus.FORBIDDEN);
    }
    if( cardPaymentDTO.getAmount()<=0 ){
      return new ResponseEntity<>("THE AMOUNT COULDN`T BE 0 OR LESS THAN 0", HttpStatus.FORBIDDEN);
    }
    if(cardPaymentDTO.getDescription().isEmpty()){
      return new ResponseEntity<>("ADD A DESCRIPTION OF YOUR PAYMENT", HttpStatus.FORBIDDEN);
    }
    if(account.getBalance() < cardPaymentDTO.getAmount()){
      return new ResponseEntity<>("INSUFFICIENT FUNDS", HttpStatus.FORBIDDEN);
    }
    if(!card.getActive()){
      return new ResponseEntity<>("CARD IS NOT ACTIVE", HttpStatus.FORBIDDEN);
    }
    if(card.getCvv() != cardPaymentDTO.getCvv()){
      return new ResponseEntity<>("CVV NUMBER IS NOT CORRECT", HttpStatus.FORBIDDEN);
    }
    if(LocalDate.now().isAfter(card.getThruDate())){
      return new ResponseEntity<>("CARD HAS EXPIRED", HttpStatus.FORBIDDEN);
    }
    account.setBalance(account.getBalance() - cardPaymentDTO.getAmount());
    transactionService.save(new Transaction(TransactionType.DEBIT, cardPaymentDTO.getAmount(), LocalDateTime.now(),"Payment Successful:"+cardPaymentDTO.getDescription(), account));

    return new ResponseEntity<>("SUCCESSFUL PAYMENT",HttpStatus.CREATED);
  }


}
