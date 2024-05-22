package com.example.miniProject.domain;

import com.example.miniProject.domain.common.BaseEntity;
import com.example.miniProject.domain.enums.DeliveryStatus;
import com.example.miniProject.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders") //테이블 이름 따로 지정해주지 않으면 관례로 그냥 order가 됨.
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // 연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //생성 메서드 (복잡한건 별도의 생성 메서드가 있으면 좋음)

    //주문 생성 메서드(위에 연관관계 편의 메서드를 만든 이유는 여기서 사용하려고 만든것임, 주문이 생성될때 필요한 모든 값들을 다 넣어주고 설정해줌)
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem: orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.COMPLETE);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //비즈니스 로직
    //주문 취소 로직
    public void cancle() {
        if(delivery.getDeliveryStatus() == DeliveryStatus.AFTERDELIVERY) {
            throw new IllegalStateException("이미 배송이 완료된 상품은 취소가 불가능합니다");
        }
        this.setOrderStatus(OrderStatus.NOCOMPELETE); //주문 취소시 상태 다시 반환

        for(OrderItem orderItem: orderItems) {  //주문 취소시 재고 원래로 복귀시키기
            orderItem.cancle();
        }
    }
}
