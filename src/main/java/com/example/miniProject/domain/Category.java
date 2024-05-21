package com.example.miniProject.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "catregory_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")) //다대다 관계에서는 1대다로 풀어낼수있는 중간테이블이 필요함
    private List<Item> items = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //연관관계 편의 메서드
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
