package com.example.spring_boot_rest_API.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tag")
@Getter
@Setter
public class Tag {
    @Id // Marks this field as the primary key of the entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tells Hibernate to auto-generate the value for this field
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Product> products;

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {

    }
}
