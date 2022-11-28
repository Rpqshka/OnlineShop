package com.example.OnlineShop.repo;

import com.example.OnlineShop.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item,Long>{
}
