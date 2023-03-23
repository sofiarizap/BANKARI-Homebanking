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
  TransactionRepository transactionRepository;
  @Autowired
  AccountRepository accountRepository;
  @Autowired
  ClientRepository clientRepository;

  @Transactional
  @PostMapping(path = "clients/current/transactions")
  public ResponseEntity<Object> createTransaction(Authentication authentication,
                                                  @RequestParam  @Nullable Double amount, @RequestParam String description,
                                                  @RequestParam String transactionDestiny, @RequestParam String transactionOrigin){

    Client client = clientRepository.findByEmail(authentication.getName());
    Account accountDestiny=accountRepository.findByNumber(transactionDestiny);
    Account accountOrigin=accountRepository.findByNumber(transactionOrigin);

    if (amount == null || description.isEmpty() || transactionDestiny.isEmpty() || transactionOrigin.isEmpty()) {
      return new ResponseEntity<>("Fill in empty spaces", HttpStatus.FORBIDDEN);
    }

    if (transactionDestiny.equals(transactionOrigin)){
      return new ResponseEntity<>("The origin account number is the" +
              " same as the destination account number", HttpStatus.FORBIDDEN);
    }
    if (accountOrigin ==null){
      return new ResponseEntity<>("Nonexistent Origin account",HttpStatus.FORBIDDEN);
    }
    if (accountDestiny ==null){
      return new ResponseEntity<>("Nonexistent  destination account",HttpStatus.FORBIDDEN);
    }
    if(!client.getAccounts().contains(accountOrigin)){
      return new ResponseEntity<>("Sender account doesn't belong to logged in user", HttpStatus.FORBIDDEN);
    }
    if(accountDestiny == null){
      return new ResponseEntity<>("Destiny account doesn't exists", HttpStatus.FORBIDDEN);
    }
    if (accountOrigin.getBalance()< amount || amount <= 0){
      return new ResponseEntity<>("Insufficient funds",HttpStatus.FORBIDDEN);
    }

    accountOrigin.setBalance(accountOrigin.getBalance() - amount);
    accountDestiny.setBalance(accountDestiny.getBalance() + amount);
    transactionRepository.save(new Transaction(DEBIT,amount,LocalDateTime.now(),description , accountOrigin));
    transactionRepository.save(new Transaction(CREDIT,amount,LocalDateTime.now(),description ,accountDestiny));


    return new ResponseEntity<>("Successful transfer",HttpStatus.CREATED);

  }
}


