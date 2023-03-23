package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {
  private String name;
  private Double amount;
  private Integer payments;

  private String destinyAccount;





  public LoanApplicationDTO(){}

  public LoanApplicationDTO(String name, Double amount, Integer payments, String destinyAccount) {
    this.name = name;
    this.amount = amount;
    this.payments = payments;
    this.destinyAccount = destinyAccount;
  }


  public String getName() {return name;}

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Integer getPayments() {
    return payments;
  }

  public void setPayments(Integer payments) {
    this.payments = payments;
  }

  public String getDestinyAccount() {
    return destinyAccount;
  }

  public void setDestinyAccount(String destinyAccount) {
    this.destinyAccount = destinyAccount;
  }

}
