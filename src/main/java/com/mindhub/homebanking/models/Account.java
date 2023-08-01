package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String number;
    private LocalDateTime creationDate;
    private Double balance;

    private AccountType accountType;

    private Boolean active = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;
    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();

    public Account(){ }

    public Account (String numberAccount, LocalDateTime date, AccountType accountType ,Double balanceAccount,Client client, Boolean active){
        this.number = numberAccount;
        this.creationDate = date;
        this.balance = balanceAccount;
        this.accountType = accountType;
        this.active = active;
        this.client=client;
    }
    public Boolean getActive(){return active;}
    public void setActive(Boolean active){ this.active= active;}

    public AccountType getAccountType(){return accountType;}

    public void setAccountType(AccountType accountType) {this.accountType = accountType;}

    public String getNumber(){
        return number;
    }

    public void setNumber(String number){
        this.number = number;
    }
    public LocalDateTime getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate){
        this.creationDate = creationDate;
    }
    public Double getBalance(){
        return balance;
    }
    public void setBalance(Double balance){
        this.balance = balance;
    }

    public Long getId(){return id;}
    public Set<Transaction> getTransaction() {
        return transactions;
    }

    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public void addTransactions(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }
    public Set<Transaction>  getTransactions() {return transactions;}
}
