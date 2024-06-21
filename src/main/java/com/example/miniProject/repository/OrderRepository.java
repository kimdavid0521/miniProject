package com.example.miniProject.repository;

import com.example.miniProject.domain.Order;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    //오더 저장 로직
    public void save(Order order) {
        em.persist(order);
    }

    //오더 단건 조회 로직
    public Order findOrder(Long orderId) {
        return em.find(Order.class, orderId);

    }

    //오더 전체 조회
    public List<Order> findAll() {
        List<Order> orderList = em.createQuery("select o from Order o", Order.class)
                .getResultList();
        return orderList;
    }

    //오더 동적 쿼리로 조회


}
