package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;
public class CardDTO {
  private long id;
  private String cardHolder;
  private CardType cardType;
  private CardColor cardColor;
  private String number;
  private Integer cvv;
  private LocalDate fromDate;
  private LocalDate thruDate;

  private Boolean active=true;


  public CardDTO() {}

  public CardDTO(Card card) {
    this.id = card.getId();
    this.cardHolder = card.getCardHolder();
    this.cardType=card.getType();
    this.cardColor =card.getCardColor();
    this.number = card.getNumber();
    this.cvv = card.getCvv();
    this.fromDate = card.getFromDate();
    this.thruDate = card.getThruDate();
    this.active=card.getActive();
  }


  public long getId() {return id;}

  public Boolean getActive() {return active;}

  public void setActive(Boolean active) { this.active = active;}

  public String getCardHolder() {return cardHolder;}

  public void setCardHolder(String cardHolder) {this.cardHolder = cardHolder;}

  public CardType getType() {return cardType;}

  public void setType(CardType type) {this.cardType = type;}

  public CardColor getCardColor() {return cardColor;}
  public void setCardColor(CardColor cardColor) {this.cardColor = cardColor;}

  public String getNumber() {return number;}
  public void setNumber(String number) {this.number = number;}

  public Integer getCvv() {return cvv;}

  public void setCvv(Integer cvv) {this.cvv = cvv;}

  public LocalDate getFromDate() {return fromDate;}

  public void setFromDate(LocalDate fromDate) {this.fromDate = fromDate;}

  public LocalDate getTruDate() {return thruDate;}

  public void setTruDate(LocalDate truDate) {this.thruDate = thruDate;}




}