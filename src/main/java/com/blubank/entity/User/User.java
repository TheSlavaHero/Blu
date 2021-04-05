package com.blubank.entity.User;

import com.blubank.entity.Card.Card;
import com.blubank.entity.Card.CardType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table (name = "Clients")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String email;
    private String phone;
    private String age;
    private String authkey;
    @OneToMany(mappedBy = "cardHolder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //    private Set cards;
    //    private List<Card> cards = new ArrayList<>();
    private Set<Card> cards = new HashSet(0);
    public User() {
    }

    public User(String name, String surname, String password, UserRole role, String email, String phone, String age) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAuthKey() {
        return authkey;
    }

    public void setAuthKey(String authKey) {
        this.authkey = authKey;
    }

    public Set getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
    public void addCard(Card card) {
        this.cards.add(card);
    }
}
