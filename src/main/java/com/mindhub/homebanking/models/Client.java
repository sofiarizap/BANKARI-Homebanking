package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Entity
public class Client{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    public Client(){ }
    public Client (String name, String lastName, String email,String password){
        this.firstName = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public void save(Client client) {}

    public Set<Account> getAccounts() {
        return accounts;
    }
    public Set<ClientLoan> getClientLoans() { return clientLoans; }

    public void setClientLoans(Set<ClientLoan> clientLoans) {this.clientLoans = clientLoans;}

    public Set<Card> getCards() { return cards; }

    public String getPassword() { return password;}

    public void setPassword(String password) { this.password = password;}

    public void addAccounts(Account account) {
        account.setClient(this);
        accounts.add(account);
    }

    @Override
    public String toString() {

        return "Clien{" +
                "first Name=" + firstName + "/" +
                "Last Name=" + lastName + "/" +
                "Email=" + email + "/" +
                "}";
    }


}
