package com.example.miniProject.service;

import com.example.miniProject.domain.*;
import com.example.miniProject.repository.ItemRepository;
import com.example.miniProject.repository.MemberRepository;
import com.example.miniProject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository; //멤버 id도 있어여하기때문
    private final ItemRepository itemRepository; //item id 도 있어야하기때문

    //주문 서비스 로직
    @Transactional
    public Long makeOrder(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //베송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress()); //실제로는 배송지정보 따로 입력해야하는데 그냥 간단하게 하기위해 회원 주소정보를 넣음

        //주문 상품 생성 (아까 만들어둔 생성자 메서드로 간편하게 생성)
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성 (아까 만들어둔 생성자 메서드로 간편하게 생성)
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    //주문 취소 서비스 로직
    @Transactional
    public void cancleOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOrder(orderId);

        //주문 취소 이때 cancleOrder메서드는 여기서 구현된게아니라 Order 엔티티에서 메서드로 구현된것
        order.cancleOrder();
    }
}
