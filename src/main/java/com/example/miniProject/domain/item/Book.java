package com.example.miniProject.domain.item;

import com.example.miniProject.domain.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@DiscriminatorValue("Book")
public class Book extends Item {

    private String author;

    private String isbn;
}
