package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;

@RestController
@RequestMapping("/api")
public class LoanController {

  @Autowired
  LoanService loanService;
  @Autowired
  ClientService clientService;
  @Autowired
  AccountService accountService;
  @Autowired
  TransactionService transactionService;
  @Autowired
  ClientLoanService clientLoansService;


  @GetMapping(path = "/loans")
  public List<LoanDTO> getLoansDTO() {
    return loanService.findAll().stream().map(loan->new LoanDTO(loan)).collect(toList());
  }


  @Transactional
  @PostMapping(path="/clients/current/loans")
  public ResponseEntity<Object> newLoans(@RequestBody LoanApplicationDTO loanApplicationDTO,
                                        Authentication authentication) {

    Client client = clientService.findByEmail(authentication.getName());
    Loan loan = loanService.findByName(loanApplicationDTO.getName());
    Account account = accountService.findByNumber(loanApplicationDTO.getDestinyAccount());


    if (loanApplicationDTO.getAmount()== null ||loanApplicationDTO.getAmount() <=0 ) {
      return new ResponseEntity<>("THE AMOUNT CAN`T BE EQUAL OR LESS THAN 0", HttpStatus.FORBIDDEN);
    }
    if ( loanApplicationDTO.getDestinyAccount().isEmpty() ) {
      return new ResponseEntity<>("YOU HAVE TO SELECT A DESTINATION ACCOUNT ", HttpStatus.FORBIDDEN);
    }
    if ( loanApplicationDTO.getPayments()==0 || loanApplicationDTO.getPayments()== null ) {
      return new ResponseEntity<>("SELECT A CORRECT OPTION OF NUMBER OF PAYMENTS", HttpStatus.FORBIDDEN);
    }
    if ( Objects.equals(loanApplicationDTO.getName(), "")) {
      return new ResponseEntity<>("SELECT A CORRECT OPTION OF TYPE OF LOAN", HttpStatus.FORBIDDEN);
    }
    if(account== null){
      return new ResponseEntity<>("THE ACCOUNT DOESN´T EXIST  ",HttpStatus.FORBIDDEN);
    }
    if (!client.getAccounts().contains(account)){
      return new ResponseEntity<>("THE ACCOUNT IS NOT AUTENTICATED ",HttpStatus.FORBIDDEN);
    }
    if (loan==null){
      return new ResponseEntity<>("THE LOAN DOESN´T EXIST",HttpStatus.FORBIDDEN);
    }
    if (loanApplicationDTO.getAmount()>loan.getMaxAmount() ){
      return new ResponseEntity<>(" EXCEEDS THE MAXIMUN AMOUNT AVAILABLE",HttpStatus.FORBIDDEN);
    }
    if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
      return new ResponseEntity<>("SELECT A CORRECT OPTION OF NUMBER OF PAYMENTS ",HttpStatus.FORBIDDEN);
    }

    account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());

    ClientLoan clientLoans=new ClientLoan((loanApplicationDTO.getAmount()*loan.getInterest())+loanApplicationDTO.getAmount(),loanApplicationDTO.getPayments(),client,loan);
    Transaction transactionsLoanCredit=new Transaction(CREDIT ,loanApplicationDTO.getAmount(),LocalDateTime.now(),loan.getName()+" Loan approved",account);

    transactionService.save(transactionsLoanCredit);
    clientLoansService.save(clientLoans);


    return new ResponseEntity<>("NEW LOAN CREATED ",HttpStatus.CREATED);
}

  @PostMapping(path="/admin/loan")
  public ResponseEntity<Object> createLoan(Authentication authentication, @RequestParam String name,
                                           @RequestParam @Nullable Double maxAmount, @RequestParam @Nullable List<Integer> payments,
                                           @RequestParam @Nullable Double interest){
    String nameLoan = name.toUpperCase();
    if(name.isEmpty()|| maxAmount == null || interest == null  || payments.size() == 0 ){
      return new ResponseEntity<>("You need to complete all the fields.", HttpStatus.FORBIDDEN);
    }else if(maxAmount == 0 || interest == 0){
      return new ResponseEntity<>("You need to complete with more than $0.", HttpStatus.FORBIDDEN);
    }else if(loanService.findByName(nameLoan) != null){
      return new ResponseEntity<>("The name requested is alredy in use.", HttpStatus.FORBIDDEN);
    }

    Loan loan = new Loan(nameLoan, maxAmount, payments,interest);

    loanService.save(loan);
    return new ResponseEntity<>(nameLoan + " LOAN CREATED",HttpStatus.CREATED);
}
}