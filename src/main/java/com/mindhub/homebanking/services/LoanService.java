package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

  public List<Loan> findAll();
  public Loan findByName(String name);
  public Loan save(Loan loan);



}
