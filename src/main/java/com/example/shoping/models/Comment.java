package com.example.shoping.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "username")
    private String username;

    @Column(name = "comment")
    private String comment;

    public Comment() {}

    public Comment(long id, Product product, String username, String message) {
        this.id = id;
        this.product = product;
        this.username = username;
        this.comment = message;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comments = (Comment) o;
        return id == comments.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonBackReference
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String message) {
        this.comment = message;
    }
}
