package com.example.miniProject.service;

import com.example.miniProject.domain.Item;
import com.example.miniProject.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    //아이템 저장 서비스
    public void addItem(Item item) {
        itemRepository.save(item);
    }

    //아이템 전체 조회
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    //아이템 단일 조회
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
