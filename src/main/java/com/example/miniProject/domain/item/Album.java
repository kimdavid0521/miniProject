package com.example.miniProject.domain.item;

import com.example.miniProject.domain.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DiscriminatorValue("Album")
public class Album extends Item {

    private String artist;

    private String etc;
}
