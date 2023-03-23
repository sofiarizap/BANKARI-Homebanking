package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {

  public List<Account> findAll();
  public Account findById(Long id);
  public Account save(Account account);
  public Account findByNumber(String number);
}
