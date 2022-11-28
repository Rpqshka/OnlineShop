package com.example.OnlineShop.repo;
import com.example.OnlineShop.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepo extends JpaRepository<Basket,Long>{
}