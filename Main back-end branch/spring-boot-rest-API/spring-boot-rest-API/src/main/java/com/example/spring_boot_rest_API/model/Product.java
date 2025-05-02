package com.example.spring_boot_rest_API.model; // yeah, all of these Controllers, repo, services should be in separate folders/packages

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//hibernate is an ORM framework
@Entity
@Table(name = "product") // uses hibernate to automatically create a table named "product", in the
                         // database(application.properties)
@Getter // lombok dependency creates the get and set methods for me with these
@Setter //These get and set methods are created for each variable/column (increases compile time)
public class Product {
    @Id // Marks this field as the primary key of the entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tells Hibernate to auto-generate the value for this field
    private Long id; //Long is just a way bigger int

    @Column(nullable = false, unique = true) // uses hibernate to automatically create the column name for this table ()
    private String name;

    // Hibernate will still create a column for price, despite the lack of(@Column),
    private Double price;

    private String description;

    private int quantity;

    @ManyToMany
    @JoinTable(
            name = "product_tag", // this is the join table name
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

}
