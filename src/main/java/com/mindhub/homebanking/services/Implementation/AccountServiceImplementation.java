package com.mindhub.homebanking.services.Implementation;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImplementation implements AccountService {

  @Autowired
  AccountRepository accountRepository;

  @Override
  public List<Account> findAll() {
    return accountRepository.findAll();
  }

  @Override
  public Account findById(Long id) {
    return accountRepository.findById(id).orElse(null);
  }

  @Override
  public Account save(Account account) {
    return accountRepository.save(account);
  }

  @Override
  public Account findByNumber(String number) {
    return accountRepository.findByNumber(number);
  }
}
