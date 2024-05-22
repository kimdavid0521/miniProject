package com.example.miniProject.repository;

import com.example.miniProject.domain.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    //아이템 JPA에 저장
    public void save(Item item) {
        if(item.getId() == null) {  //만약 등록하려는 아이템의 아이디가 없으면 저장(신규 등록)
            em.persist(item);
        } else {
            em.merge(item); //merge는 업데이트 비슷한것 (업데이트)
        }

    }

    //아이템 id로 아이템 조회하기
    public Item findOne(Long itemId) {
        return em.find(Item.class, itemId);
    }

    //아이템 전체 조회하기
    public List<Item> findAll() {
        List<Item> itemList = em.createQuery("select i from Item i", Item.class)
                .getResultList();
        return itemList;
    }

    //아이템 이름으로 조회하기
    public List<Item> findByName(String itemName) {
        List<Item> itemList = em.createQuery("select i from Item i where i.name = :name", Item.class)
                .setParameter("name", itemName)
                .getResultList();
        return itemList;
    }

 }
