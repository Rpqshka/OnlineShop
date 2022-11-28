package com.example.OnlineShop.controller;


import com.example.OnlineShop.models.Item;
import com.example.OnlineShop.models.Review;
import com.example.OnlineShop.repo.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;

import javax.management.Query;
import java.util.List;
import java.util.Objects;

@RestController
public class ReviewAPI {
    @Autowired
    private ReviewRepo reviewRepo;
    private ReviewRepo reviewRepoBuff;

    @GetMapping(value = "/item/reviews")
    public List<Review> getReviews(){
        return reviewRepo.findAll();
    }

   /* @RequestMapping(value = "/item/reviews/id{idItem}")
    public List<Review> getReviews(@PathVariable long idItem){

    }*/

    @PostMapping(value = "/item/reviews/id{itemId}/add")
    public String saveReview(@RequestBody Review review){
        reviewRepo.save(review);
        return "Отзыв на товар сохранен";
    }

}
