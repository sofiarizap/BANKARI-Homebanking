package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanDTO {
  private Long id;
  private String name;
  private Double  maxAmount;
  private List<Integer> payments=new ArrayList<>();

  public LoanDTO() {}

  public LoanDTO(Loan loan) {
    this.id = loan.getId();
    this.name = loan.getName();
    this. maxAmount = loan.getMaxAmount();
    this.payments = loan.getPayments();

  }

  public long getId() {return id;}

  public String getName() {return name;}

  public void setName(String name) {this.name = name;}

  public Double getAmount() {return maxAmount;}

  public void setAmount(Double amount) {this. maxAmount = amount;}

  public List<Integer> getPayments() {return payments;}

  public void setPayments(List<Integer> payments) {this.payments = payments;}
}
