package com.blubank.entity.Card;

import com.blubank.entity.Transaction.Transaction;
import com.blubank.entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "Cards")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Card implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private Calendar expiryDate;
    @Enumerated(EnumType.STRING)
    private CardType type;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User cardHolder;

//    @OneToMany(mappedBy = "cardFrom")
//    private List<Transaction> transactionsFrom = new ArrayList<>();
//    @OneToMany(mappedBy = "cardTo")
//    private List<Transaction> transactionsTo = new ArrayList<>();

    @OneToMany(mappedBy = "cardFrom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set transactionsFrom;
    private Set<Transaction> transactionsFrom = new HashSet(0);

    @OneToMany(mappedBy = "cardTo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaction> transactionsTo = new HashSet(0);

    public Card(String number, Calendar expiryDate, CardType type, User cardHolder) {
        this.number = number;
        this.expiryDate = expiryDate;
        this.type = type;
        this.cardHolder = cardHolder;
    }

    public Card() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Calendar getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Calendar expiryDate) {
        this.expiryDate = expiryDate;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public User getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(User cardHolder) {
        this.cardHolder = cardHolder;
    }

    public Set getTransactionsFrom() {
        return transactionsFrom;
    }

    public void setTransactionsFrom(Set transactionsFrom) {
        this.transactionsFrom = transactionsFrom;
    }

    public Set getTransactionsTo() {
        return transactionsTo;
    }

    public void setTransactionsTo(Set transactionsTo) {
        this.transactionsTo = transactionsTo;
    }
}
