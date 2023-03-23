package com.mindhub.homebanking.services.Implementation;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImplementation  implements TransactionService {

  @Autowired
  TransactionRepository transactionRepository;

  @Override
  public Transaction save(Transaction transaction) {
    return transactionRepository.save(transaction);
  }
}
