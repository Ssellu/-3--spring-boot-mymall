package com.megait.mymall.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("al")
@Getter @Setter
@NoArgsConstructor
@SuperBuilder
public class Album extends Item{
    private String title;
    private String artist;
}
