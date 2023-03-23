package com.mindhub.homebanking.models;

import com.mindhub.homebanking.dtos.ClientDTO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Loan {
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
 @GenericGenerator(name = "native", strategy = "native")
 private Long id;
 private String name;
 private Double maxAmount;

 private Double interest;

 @ElementCollection
 @Column (name = "payments")
 private List<Integer> payments = new ArrayList<>();

 @OneToMany(mappedBy = "loan",fetch = FetchType.EAGER)
 Set<ClientLoan> clientLoans =new HashSet<>();

 public Loan(){}
 public Loan (String name,Double maxAmount, List<Integer> payments,Double interest){
  this.name= name;
  this.maxAmount = maxAmount;
  this.payments = payments;
  this.interest = interest;
 }
 public Long getId(){ return id;}

 public Double getInterest() { return interest;}

 public void setInterest(Double interest) {this.interest = interest;}

 public String getName(){ return name ;}

 public void setName(String name ){ this.name = name;}
 public Double getMaxAmount(){ return maxAmount ;}
 public void setMaxAmount(Double maxAmount ){ this.maxAmount = maxAmount;}

 public List<Integer> getPayments(){ return payments ;}

 public void setPayments(List<Integer> payments ){ this.payments = payments;}

 public Set<ClientLoan> getClientLoans() { return clientLoans; }

}
