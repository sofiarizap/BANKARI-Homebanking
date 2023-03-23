package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private long id;
  private String cardHolder;
  private String number;
  private int cvv;
  private CardType cardType;
  private CardColor cardColor;
  private LocalDate fromDate;
  private LocalDate thruDate;


  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "client_id")
  private Client client;

  public Card() {
  }

  public Card(String cardHolder, CardType cardtype, CardColor cardColor, String number, int cvv, LocalDate fromDate, LocalDate thruDate, Client client) {
    this.cardHolder = cardHolder;
    this.cardType = cardtype;
    this.cardColor = cardColor;
    this.number = number;
    this.cvv = cvv;
    this.fromDate = fromDate;
    this.thruDate = thruDate;
    this.client= client;

  }

  public Long getId() { return id;}

  public String getCardHolder() { return cardHolder; }

  public void setCardHolder(String cardHolder) { this.cardHolder = cardHolder; }

  public CardType getType() { return cardType;}

 public void setType(CardType cardType) {this.cardType = cardType;}

  public CardColor getCardColor() { return cardColor; }

  public void setCardColor(CardColor cardColor) { this.cardColor = cardColor; }

  public String getNumber() { return number;}

  public void setNumber(String number) { this.number = number;}

  public Integer getCvv() { return cvv;}

  public void setCvv(int cvv) { this.cvv = cvv;}

  public LocalDate getFromDate() { return fromDate;}

  public void setFromDate(LocalDate fromDate) {this.fromDate = fromDate;}

  public LocalDate getThruDate() {return thruDate;}

  public void setThruDate(LocalDate thruDate) {this.thruDate = thruDate;}

  public Client getClient() {return client;}

  public void setClient(Client client) { this.client = client;}



}