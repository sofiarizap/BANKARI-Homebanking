package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
  private Long id;
  private Long LoanId;
  private String name;
  private Double amount;
  private int payments;

  public ClientLoanDTO() {
  }

  public ClientLoanDTO(ClientLoan clientLoan) {
    this.id = clientLoan.getId();
    this.LoanId = clientLoan.getLoan().getId();
    this.name = clientLoan.getLoan().getName();
    this.amount = clientLoan.getAmount();
    this.payments = clientLoan.getPayments();
  }

  public Long getId(){return id;}
  public Long getLoanId() { return LoanId;}

  public String getName() { return name; }

  public void setName(String name) { this.name = name;}
  public Double getAmount() { return amount;}
  public void setAmount(Double amount) { this.amount = amount;}
  public int getPayments() { return payments; }
  public void setPayments(int payments) { this.payments = payments;}
}
