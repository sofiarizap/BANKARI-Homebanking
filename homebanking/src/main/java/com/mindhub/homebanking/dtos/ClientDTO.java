package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import static java.util.stream.Collectors.toList;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private Set<AccountDTO> accounts;
  private Set<ClientLoanDTO> loans;
  private Set<CardDTO> card;

  public ClientDTO(){
  }

  public ClientDTO(Client client) {

    this.id = client.getId();

    this.firstName = client.getFirstName();

    this.lastName = client.getLastName();

    this.email = client.getEmail();

    this.accounts=client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());

    this.loans=client.getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toSet());

    this.card= client.getCards().stream().map(CardDTO::new).collect(Collectors.toSet());

  }
  public Long getId(){return id;}
  public String getEmail(){
    return email;
  }
  public void setEmail(String Email){
    this.email = Email;
  }
  public String getFirstName(){
    return firstName;
  }
  public void setFirstName(String firstName){
    this.firstName = firstName;
  }
  public String getLastName(){
    return lastName;
  }
  public void setLastName(String lastName){
    this.lastName = lastName;
  }

  public Set<AccountDTO> getAccounts() {
    return accounts;
  }
  public Set<ClientLoanDTO> getLoans() { return loans;}
  public void setLoans(Set<ClientLoanDTO> loans) { this.loans = loans; }

  public Set<CardDTO> getCard() { return card;}

  public void setCard(Set<CardDTO> card) { this.card = card;}


}
