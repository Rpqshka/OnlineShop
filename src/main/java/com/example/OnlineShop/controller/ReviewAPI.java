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

    @GetMapping(value = "/item/reviews")
    public Iterable<Review> getReviews(){
        return reviewRepo.findAll();
    }

    @RequestMapping(value = "/item/reviews/idItem{idItem}")
    public List<Review> getReviewsByIdItem(@PathVariable long idItem){
        return reviewRepo.findAllByIdItem(idItem);
    }

    @PostMapping(value = "/item/reviews/add")
    public String saveReview(@RequestBody Review review){
        reviewRepo.save(review);
        return "Отзыв на товар сохранен";
    }
    @PutMapping(value = "/item/reviews/id{reviewId}/update")
    public String updateReview(@PathVariable long reviewId,@RequestBody Review review){
        Review updatedReview = reviewRepo.findById(reviewId).get();
        updatedReview.setIdItem(review.getIdItem());
        updatedReview.setComment(review.getComment());
        updatedReview.setRating(review.getRating());
        reviewRepo.save(updatedReview);
        return "Отзыв обновлен";
    }
    @DeleteMapping(value = "/item/reviews/id{reviewId}/delete")
    public String deleteReview(@PathVariable long reviewId){
        Review deleteReview = reviewRepo.findById(reviewId).get();
        reviewRepo.delete(deleteReview);
        return "Отзыв с id " + reviewId + "удален";
    }

}
