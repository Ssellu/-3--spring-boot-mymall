package com.megait.mymall.repository;

import com.megait.mymall.domain.Category;
import com.megait.mymall.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // select     WHERE name = ? AND price = ?
//    Optional<Item> findByNameAndPrice(String name, int price);
//    Item getByNameAndPrice(String name, int price);
//    boolean deleteByCategories(List<Category> categories);
}
