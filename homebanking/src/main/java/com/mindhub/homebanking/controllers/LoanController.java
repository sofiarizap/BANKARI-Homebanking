package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  LoanRepository loanRepository;
  @Autowired
  ClientRepository clientRepository;
  @Autowired
  AccountRepository accountRepository;
  @Autowired
  TransactionRepository transactionRepository;
  @Autowired
  ClientLoanRepository clientLoansRepository;

  @GetMapping(path = "/loans")
  public List<LoanDTO> getLoanDTO() {
    return loanRepository.findAll().stream().map(loan->new LoanDTO(loan)).collect(toList());
  }


  @Transactional
  @PostMapping(path="/clients/current/loans")
  public ResponseEntity<Object> newLoans(@RequestBody LoanApplicationDTO loanApplicationDTO,
                                        Authentication authentication) {

    Client client = clientRepository.findByEmail(authentication.getName());
    Loan loan = loanRepository.findByName(loanApplicationDTO.getName());
    Account account = accountRepository.findByNumber(loanApplicationDTO.getDestinyAccount());


    if (loanApplicationDTO.getAmount()== 0 ||loanApplicationDTO.getAmount() <=0 ) {
      return new ResponseEntity<>("The amount can`t be equal or less than 0", HttpStatus.FORBIDDEN);
    }
    if ( loanApplicationDTO.getDestinyAccount().isEmpty() ) {
      return new ResponseEntity<>("You have to select a destination Account ", HttpStatus.FORBIDDEN);
    }
    if ( loanApplicationDTO.getPayments()==0 || loanApplicationDTO.getPayments()== null ) {
      return new ResponseEntity<>("Select a correct option of number of payments", HttpStatus.FORBIDDEN);
    }
    if ( Objects.equals(loanApplicationDTO.getName(), "")) {
      return new ResponseEntity<>("Select a correct option of type of loan", HttpStatus.FORBIDDEN);
    }
    if(account== null){
      return new ResponseEntity<>("The account doesn´t exist  ",HttpStatus.FORBIDDEN);
    }
    if (!client.getAccounts().contains(account)){
      return new ResponseEntity<>("The account is not autenticated ",HttpStatus.FORBIDDEN);
    }
    if (loan==null){
      return new ResponseEntity<>("The loan doesn´t exist",HttpStatus.FORBIDDEN);
    }
    if (loanApplicationDTO.getAmount()>loan.getMaxAmount() ){
      return new ResponseEntity<>(" Exceeds the maximun amount available",HttpStatus.FORBIDDEN);
    }
    if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
      return new ResponseEntity<>("Select a correct option of number of payments ",HttpStatus.FORBIDDEN);
    }

    Double loanInterest=(loanApplicationDTO.getAmount()*0.20)+loanApplicationDTO.getAmount();
    Double loanPayments=Math.floor(loanInterest/loanApplicationDTO.getPayments());


    ClientLoan clientLoans=new ClientLoan(loanApplicationDTO.getAmount(),loanApplicationDTO.getPayments(),client,loan);
    Transaction transactionsLoanCredit=new Transaction(CREDIT ,loanApplicationDTO.getAmount(),LocalDateTime.now(),loan.getName()+" Loan approved",account);

    transactionRepository.save(transactionsLoanCredit);
    clientLoansRepository.save(clientLoans);


    Double auxDestiny= loanApplicationDTO.getAmount()+ account.getBalance();

    account.setBalance(auxDestiny);


    return new ResponseEntity<>("New Loan Created ",HttpStatus.CREATED);
}
}