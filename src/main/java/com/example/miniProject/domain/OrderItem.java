package com.example.miniProject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer orderPrice;

    private Integer count;

    //생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); //오더 아이템을 생성하면서 재고까지 같이 까주는것
        return orderItem;
    }
    //주문 취소시 재고 원복 로직
    public void cancle() {
        getItem().addStock(count);
    }

    //주문 수량과 주문 량을 곱한 최종 가격(총 가격 조회시 필요함)
    public int totalPrice() {
        return getOrderPrice() * getCount();
    }
}
