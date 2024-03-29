package com.mindhub.homebanking.models;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
  @Id
  @GeneratedValue (strategy = GenerationType.AUTO, generator = "native")
  private Long id;
  private TransactionType type;
  private Double amount;
  private LocalDateTime date;
  private String description;
  private Double balance;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="account_id")
  private Account account;

  private Transaction(){}
  public Transaction(TransactionType type, Double amount, LocalDateTime date, String description,Account account){
    this.type = type;
    this.amount = amount;
    this.date = date;
    this.description = description;
    this.account=account;
    this.balance=account.getBalance();
  }
  public Long getId(){return id;}

  public TransactionType getType(){ return type;}

  public void setType (TransactionType type){ this.type = type;}

  public Double getAmount() {return amount; }

  public void setAmount(double amount){ this.amount = amount;}

  public LocalDateTime getDate() {return date; }

  public void setDate(LocalDateTime date){ this.date = date;}
  public  String getDescription(){return description;}
  public void setDescription(String description){ this.description = description;}

  public Account getAccount(){return account;}

  public void setAccount(Account account){
    this.account = account;
  }

  public Double getBalance() { return balance; }

  public void setBalance(Double balance) { this.balance = balance; }
}
