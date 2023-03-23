package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
public class ClientLoan {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private Long id;
  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;
  @ManyToOne
  @JoinColumn(name = "loan_id")
  private Loan loan;
  private Double amount;
  private int payments;

  public ClientLoan(){}
  public ClientLoan(Double amount, int payments, Client client, Loan loan){
    this.client = client;
    this.loan = loan;
    this.amount = amount;
    this.payments = payments;
  }

  public Long getId(){ return id;}

  @JsonIgnore
  public Client getClient(){ return client ;}

  public void setClient(Client client ){ this.client = client;}
  @JsonIgnore
  public Loan getLoan(){ return loan ;}
  public void setLoan(Loan loan ){ this.loan = loan;}
  public Double getAmount(){ return amount ;}
  public void setAmount(Double amount ){ this.amount = amount;}
  public int getPayments(){ return payments ;}
  public void setPayments(int payments ){ this.payments = payments;}
}
