package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
@RestController
@RequestMapping("/api")
public class AccountController {
  @Autowired
  private AccountService accountService;
  @Autowired
  private ClientService clientService;

  @GetMapping("/accounts")
  public List<AccountDTO> getAccounts(){
    return accountService.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
  }
  @GetMapping("/accounts/{id}")
  public AccountDTO getAccount(@PathVariable Long id){
    return new AccountDTO(accountService.findById(id)) ;
  }

  @PostMapping(path = "/clients/current/accounts")
  public  ResponseEntity<Object> createAccount(Authentication authentication,  @RequestParam AccountType accountType) {

    Client client = clientService.findByEmail(authentication.getName());
    List<Account> accounts = new ArrayList<>();


    client.getAccounts().forEach(account -> {
      if(account.getActive() == true){accounts.add(account);}
    });


    String number;
    do { number = AccountUtils.getAccountNumber();}
    while(accountService.findByNumber(number) != null);

    if (accounts.size()>3){
      return new ResponseEntity<>("LIMIT OF CREATED ACCOUNTS REACHED", HttpStatus.FORBIDDEN);
    }
    if(accountType == null){
      return new ResponseEntity<>("SELECT A CORRET ACCOUNT TYPE", HttpStatus.FORBIDDEN);
    }

      Account account = new Account( number, LocalDateTime.now(),accountType, 0.0,client, true);
      client.addAccounts(account);
      accountService.save(account);
      clientService.save(client);

      return new ResponseEntity<>("NEW ACCOUNT CREATED", HttpStatus.CREATED);



  }
  @PatchMapping("/clients/current/accounts/delete/{id}")
  public ResponseEntity<Object> accountDelete(@PathVariable Long id) {

    Account account = accountService.findById(id);

    if (account.getBalance() > 0) {
      return new ResponseEntity<>("YOU ALREADY HAVE MONEY IN YOUR ACCOUNT", HttpStatus.FORBIDDEN);
    }

    account.setActive(false);
    accountService.save(account);

    return new ResponseEntity<>("DELETED ACCOUNT", HttpStatus.CREATED);
  }
}
