package com.mindhub.homebanking.services.Implementation;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImplementation implements LoanService {

  @Autowired
  LoanRepository loanRepository;

  @Override
  public List<Loan> findAll() {
    return loanRepository.findAll();
  }

  @Override
  public Loan findByName(String name) {
    return loanRepository.findByName(name);
  }

  @Override
  public Loan save(Loan loan) {
    return loanRepository.save(loan);
  }
}
