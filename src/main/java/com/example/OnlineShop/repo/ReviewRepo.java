package com.example.OnlineShop.repo;

import com.example.OnlineShop.models.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ReviewRepo extends CrudRepository<Review, Long> {
    List<Review> findAllByIdItem(long idItem);
}
