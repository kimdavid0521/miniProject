package com.example.miniProject.domain;

import com.example.miniProject.domain.Category;
import com.example.miniProject.exception.NotEnoughException;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //한 테이블에 다 박는 싱글테이블 전략 사용
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private Integer price;

    private Integer stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


    //비즈니스 로직 (비즈니스의 넣고빼는 로직은 해당 값이있는 곳에서 하는게 응집력이 좋음)
    //수량 증가시키는 로직
    public void addStock(int quantity) {
        this.stockQuantity = stockQuantity + quantity;
    }

    //수량 감소시키는 로직
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0) {
            throw new NotEnoughException("재고 수량이 더 필요합니다."); //남은 재고수량을 계산해보고 0보다 작으면 예외 터뜨려주고 그게아니면 적용시켜주기
        }
        this.stockQuantity = restStock;
    }

}
