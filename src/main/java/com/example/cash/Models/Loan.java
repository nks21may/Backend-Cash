package com.example.cash.Models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

@Entity
public class Loan {

    private @Id @GeneratedValue Integer id;
    
    @Column(nullable = false)
    private BigDecimal total;

    @JsonRawValue
    @JsonProperty("userId")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
    
    
    public Loan() {
    }

    public Loan(BigDecimal total, User user) {
        this.total = total;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "{" + 
                    "\"id\":" + id + 
                    ", \"total\":" + total + 
                    ", \"userId\":" + user.getId() + 
                '}';
    }
}
