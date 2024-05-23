package com.example.miniProject.service;

import com.example.miniProject.domain.Address;
import com.example.miniProject.domain.Item;
import com.example.miniProject.domain.Member;
import com.example.miniProject.domain.Order;
import com.example.miniProject.domain.enums.OrderStatus;
import com.example.miniProject.domain.item.Book;
import com.example.miniProject.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    //상품 주문 테스트 코드
    @Test
    public void orderTest() throws Exception {

        Member member = new Member();
        member.setName("김태영1");
        member.setAddress(new Address("서울", "광진구", "123"));
        em.persist(member);

        Book book = new Book();
        book.setName("책이름");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;

        Long orderId = orderService.makeOrder(member.getId(), book.getId(), orderCount);

        Order order = orderRepository.findOrder(orderId);
        //assertEquals(orderId, order.getId());

        assertEquals(OrderStatus.COMPLETE, order.getOrderStatus(), "상품 주문시 상태는 order");
        assertEquals(2, order.getOrderItems().size(), "주문한 상품의 수가 같아야함");
    }

    //상품 주문 취소 테스트 코드
    @Test
    public void cancleOrderTest() throws Exception {

    }

    //제고수량 초과 테스트 코드
    @Test
    public void limitStockTest() throws Exception {

    }

}