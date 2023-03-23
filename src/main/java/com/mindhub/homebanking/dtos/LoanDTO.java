package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanDTO {
  private Long id;
  private String name;
  private Double  maxAmount;
  private List<Integer> payments=new ArrayList<>();

  private Double interest;

  public LoanDTO() {}

  public LoanDTO(Loan loan) {
    this.id = loan.getId();
    this.name = loan.getName();
    this.maxAmount = loan.getMaxAmount();
    this.payments = loan.getPayments();
    this.interest=loan.getInterest();

  }

  public Long getId() {return id;}

  public Double getInterest() { return interest;}

  public void setInterest(Double interest) {this.interest = interest;}

  public String getName() {return name;}

  public void setName(String name) {this.name = name;}

  public Double getMaxAmount() {return maxAmount;}

  public void setMaxAmount(Double amount) {this.maxAmount = amount;}

  public List<Integer> getPayments() {return payments;}

  public void setPayments(List<Integer> payments) {this.payments = payments;}
}
