package com.example.shoping.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "rates")
public class Rate implements Serializable {
    public static enum R{
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE
    }

    @Id
    @Column(name = "username")
    private String username;

    @Id
    @Column(name = "product_id")
    private Long product_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rate")
    private R rate;

    public Rate(){}

    public Rate(String username, Long product_id, R rate) {
        this.username = username;
        this.product_id = product_id;
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate = (Rate) o;
        return Objects.equals(username, rate.username) && Objects.equals(product_id, rate.product_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, product_id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public R getRate() {
        return rate;
    }

    public void setRate(R rate) {
        this.rate = rate;
    }
}
