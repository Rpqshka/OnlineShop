package com.example.OnlineShop.repo;

import com.example.OnlineShop.models.Basket;
import org.springframework.data.repository.CrudRepository;

public interface BasketRepo extends CrudRepository<Basket,Long> {
    Basket findBasketByIdUser(long idUser);
}
