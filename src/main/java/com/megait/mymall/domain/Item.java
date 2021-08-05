package com.megait.mymall.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter @Setter
@SuperBuilder // @Builder 대신
@AllArgsConstructor @NoArgsConstructor
public abstract class Item {
    @Id @GeneratedValue
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany
    private List<Category> categories = new ArrayList<>();

    private String imageUrl;

    private int liked;


}
