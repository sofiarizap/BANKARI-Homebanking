package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/")
public class TransactionController {

  @Autowired
  TransactionService transactionService;
  @Autowired
  AccountService accountService;
  @Autowired
  ClientService clientService;

  @Transactional
  @PostMapping(path = "clients/current/transactions")
  public ResponseEntity<Object> createTransaction(Authentication authentication,
                                                  @RequestParam  @Nullable Double amount, @RequestParam String description,
                                                  @RequestParam String transactionDestiny, @RequestParam String transactionOrigin){

    Client client = clientService.findByEmail(authentication.getName());
    Account accountDestiny=accountService.findByNumber(transactionDestiny);
    Account accountOrigin=accountService.findByNumber(transactionOrigin);

    if (amount == null || description.isEmpty() || transactionDestiny.isEmpty() || transactionOrigin.isEmpty()) {
      return new ResponseEntity<>("Fill in empty spaces", HttpStatus.FORBIDDEN);
    }

    if (transactionDestiny.equals(transactionOrigin)){
      return new ResponseEntity<>("THE ORIGIN ACCOUNT NUMBER IS THE SAME AS THE DESTINATION" +
              " ACCOUNT NUMBER", HttpStatus.FORBIDDEN);
    }

    if (accountOrigin ==null){
      return new ResponseEntity<>("NONEXISTENT ORIGIN ACCOUNT",HttpStatus.FORBIDDEN);
    }
    if (accountDestiny ==null){
      return new ResponseEntity<>("NONEXISTENT  DESTINATION ACCOUNT",HttpStatus.FORBIDDEN);
    }
    if(!client.getAccounts().contains(accountOrigin)){
      return new ResponseEntity<>("SENDER ACCOUNT DOESN'T BELONG TO LOGGED IN USER", HttpStatus.FORBIDDEN);
    }
    if (accountOrigin.getBalance()< amount ){
      return new ResponseEntity<>("INSUFFICIENT FUNDS",HttpStatus.FORBIDDEN);
    }
    if ( amount <= 0){
      return new ResponseEntity<>("THE AMOUNT COULDN`T BE 0 OR LESS",HttpStatus.FORBIDDEN);
    }


    accountOrigin.setBalance(accountOrigin.getBalance() - amount);
    accountDestiny.setBalance(accountDestiny.getBalance() + amount);

    transactionService.save(new Transaction(DEBIT,amount,LocalDateTime.now(),description , accountOrigin));
    transactionService.save(new Transaction(CREDIT,amount,LocalDateTime.now(),description ,accountDestiny));


    return new ResponseEntity<>("SUCCESSFUL TRANSFER",HttpStatus.CREATED);

  }
}


