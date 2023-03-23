package com.mindhub.homebanking.dtos;

public class CardPaymentDTO {
  private String number;
  private int cvv;
  private Double amount;
  private String description;
  private String account;

  public CardPaymentDTO(){}
  public CardPaymentDTO(String number, int cvv, Double amount, String description, String account){
    setNumber(number);
    setCvv(cvv);
    setAmount(amount);
    setDescription(description);
    setAccount(account);
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public int getCvv() {
    return cvv;
  }

  public void setCvv(int cvv) {
    this.cvv = cvv;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }
}
