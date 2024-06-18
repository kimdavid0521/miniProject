package com.example.miniProject.service;

import com.example.miniProject.domain.Address;
import com.example.miniProject.domain.Item;
import com.example.miniProject.domain.Member;
import com.example.miniProject.domain.Order;
import com.example.miniProject.domain.enums.OrderStatus;
import com.example.miniProject.domain.item.Book;
import com.example.miniProject.exception.NotEnoughException;
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
        assertEquals(1, order.getOrderItems().size(), "주문한 상품 종류의 수가 같아야함");
        assertEquals(10000 * orderCount, order.getTotalPrice(),"주문한 상품의 총 가격");
        assertEquals(10-orderCount, book.getStockQuantity(), "주문한 수량 만큼 재고가 빠졌는지");

    }

    //상품 주문 재고 수량 초과 테스트
    @Test
    public void overStaockTest() throws Exception {
        Member member = new Member();
        member.setName("김태영");
        member.setAddress(new Address("서울","광진구","2312"));
        em.persist(member);

        Book book = new Book();
        book.setName("책임");
        book.setPrice(20000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 12;

        orderService.makeOrder(member.getId(), book.getId(), orderCount);

        fail("재고 수량 예외가 발생해야함");

    }

    //상품 주문 취소 테스트 코드
    @Test
    public void cancleOrderTest() throws Exception {

        Member member = new Member();
        member.setName("김태영");
        member.setAddress(new Address("서울", "어ㅜㄴ어","ㅇㅈㅈㅇ"));
        em.persist(member);

        Book book = new Book();
        book.setName("ㅊㄴ우");
        book.setPrice(1000);
        book.setStockQuantity(12);
        em.persist(book);

        int orderCount = 10;
        Long orderId = orderService.makeOrder(member.getId(), book.getId(), orderCount);

        orderService.cancleOrder(orderId);

        Order getOrder = orderRepository.findOrder(orderId);

        assertEquals(OrderStatus.NOCOMPELETE, getOrder.getOrderStatus(),"주문 취소 상태엔 NOCOMPELET");
        assertEquals(book.getStockQuantity(), 12,"주문 취소되면 재고는 원복해야됨");

    }

    //제고수량 초과 테스트 코드
    @Test
    public void limitStockTest() throws Exception {

    }

}