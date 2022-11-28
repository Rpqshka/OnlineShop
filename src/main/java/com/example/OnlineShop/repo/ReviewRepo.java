package com.example.OnlineShop.repo;

import com.example.OnlineShop.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReviewRepo extends JpaRepository<Review, Long>{

}
