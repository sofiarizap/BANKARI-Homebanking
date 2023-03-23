package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
@RestController
@RequestMapping("/api")
public class AccountController {
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  ClientRepository clientRepository;

  @GetMapping("/accounts")
  public List<AccountDTO> getAccounts(){
    return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
  }
  @GetMapping("/accounts/{id}")
  public AccountDTO getAccounts(@PathVariable Long id){
    return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
  }
  int min = 00000000;
  int max = 99999999;

  public int getRandomNum(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  public String getStringRandomNum() {
    int randomNum = getRandomNum(min, max);
    return String.valueOf(randomNum);
  }

  @PostMapping(path = "/clients/current/accounts")
  public ResponseEntity<Object> register(Authentication authentication) {


    String email = authentication.getName();
    Client client = clientRepository.findByEmail(email);

    String number;
    do { number ="VIN" +getStringRandomNum();}
    while(accountRepository.findByNumber(number) != null);


    if( client == null){
      return new ResponseEntity<>( HttpStatus.FORBIDDEN);
    }
    if ( client.getAccounts().size()>=3){
      return new ResponseEntity<>("Limit of created accounts reached", HttpStatus.FORBIDDEN);

    }else{
      Account account = new Account( number, LocalDateTime.now(), 0.0);
      client.addAccounts(account);
      accountRepository.save(account);

      return new ResponseEntity<>("New account created", HttpStatus.CREATED);
    }
  }
  @PatchMapping("/clients/current/accounts/delete/{id}")
  public ResponseEntity<Object> accountDelete(@PathVariable Long id) {
    Account account = accountRepository.findById(id).orElse(null);


    if (account.getBalance() > 0) {
      return new ResponseEntity<>("You can´t delete an acoount with a balance diferent to 0 ", HttpStatus.FORBIDDEN);
    }

    accountRepository.save(account);

    return new ResponseEntity<>("Account deleted", HttpStatus.CREATED);
  }
}
