package com.example.miniProject.domain.item;

import com.example.miniProject.domain.Item;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DiscriminatorValue("Movie")
public class Movie extends Item {

    private String director;

    private String actor;
}
