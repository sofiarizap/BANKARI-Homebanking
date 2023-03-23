package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
  private Long id;
  private String number;
  private LocalDateTime creationDate;
  private Double balance;

  private boolean active;
  private AccountType accountType;
  private Set<TransactionDTO> transactions  = new HashSet<>();

  public AccountDTO(){ }

  public AccountDTO (Account account){
    this.transactions= account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    this.id = account.getId();
    this.number = account.getNumber();
    this.creationDate = account.getCreationDate();
    this.balance = account.getBalance();
    this.active = account.getActive();
    this.accountType = account.getAccountType();
  }


  public Boolean getActive(){
    return active;
  }

  public void setActive(Boolean active){
    this.active = active;
  }

  public String getNumber(){
    return number;
  }

  public void setNumber(String number){
    this.number = number;
  }
  public LocalDateTime getCreationDate(){
    return creationDate;
  }
  public void setCreationDate(LocalDateTime creationDate){
    this.creationDate = creationDate;
  }
  public Double getBalance(){
    return balance;
  }
  public void setBalance(Double balance){
    this.balance = balance;
  }

  public Long getId(){return id;}

  public Set<TransactionDTO> getTransactions() {
    return transactions;
  }

  public AccountType getAccountType(){return accountType;}

  public void setAccountType(AccountType accountType){
    this.accountType = accountType;
  }
}
