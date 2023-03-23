package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
  private Long id;
  private TransactionType type;
  private Double amount;
  private LocalDateTime date;
  private String description;
  public TransactionDTO(){}
  public TransactionDTO(Transaction transaction){
    this.id = transaction.getId();
    this.date = transaction.getDate();
    this.type = transaction.getType();
    this.amount = transaction.getAmount();
    this.description = transaction.getDescription();
  }
  public Long getId(){return id;}

  public TransactionType getType(){ return type;}

  public void setType (TransactionType type){ this.type = type;}

  public Double getAmount() {return amount; }

  public void setAmount(Double amount){ this.amount = amount;}

  public LocalDateTime getDate() {return date; }
  public void setDate(LocalDateTime date){ this.date = date;}
  public  String getDescription(){return description;}
  public void setDescription(String description){ this.description = description;}
}
