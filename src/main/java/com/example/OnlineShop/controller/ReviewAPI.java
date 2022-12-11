package com.example.OnlineShop.controller;


import com.example.OnlineShop.models.Item;
import com.example.OnlineShop.models.Review;
import com.example.OnlineShop.models.User;
import com.example.OnlineShop.repo.ItemRepo;
import com.example.OnlineShop.repo.ReviewRepo;
import com.example.OnlineShop.repo.UserRepo;
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

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ItemRepo itemRepo;

    @GetMapping(value = "/item/review/all")
    public Iterable<Review> getReviews(){
        return reviewRepo.findAll();
    }

    @RequestMapping(value = "/item/review/{idItem}")
    public List<Review> getReviewsByIdItem(@PathVariable long idItem){
        return reviewRepo.findAllByIdItem(idItem);
    }

    //TODO Убрать повторные добавления
    @PostMapping(value = "/item/review/add/check_role/{email}:{pass}")
    public String saveReview(@PathVariable String email, @PathVariable String pass, @RequestBody Review review){
        String userRole = userRepo.findByEmailAndPass(email,pass).getRole();
        if (userRole.equals("user")) {
            reviewRepo.save(review);
            Item item = itemRepo.findById(review.getIdItem()).get();
            item.calculateRating(review.getRating());
            itemRepo.save(item);
            return "Отзыв на товар сохранен";
        }
        else
            return "Вы не можете оставить отзыв";
    }
    @PutMapping(value = "/item/review/update/{idReview}")
    public String updateReview(@PathVariable long idReview,@PathVariable String email, @PathVariable String pass,@RequestBody Review review){
        if(userRepo.findByEmailAndPass(email,pass).getRole().equals("admin") ||
                userRepo.findByEmailAndPass(email, pass).getId() == reviewRepo.findById(idReview).get().getIdUser()) {
            Review updatedReview = reviewRepo.findById(idReview).get();
            updatedReview.setIdItem(review.getIdItem());
            updatedReview.setComment(review.getComment());
            updatedReview.setRating(review.getRating());
            reviewRepo.save(updatedReview);
            return "Отзыв обновлен";
        }
        else
            return "Вы не можете обновить этот отзыв";
    }
    @DeleteMapping(value = "/item/review/delete/{idReview}/check_role/{email}:{pass}")
    public String deleteReview(@PathVariable long idReview, @PathVariable String email, @PathVariable String pass){
        if(userRepo.findByEmailAndPass(email,pass).getRole().equals("admin") ||
                userRepo.findByEmailAndPass(email, pass).getId() == reviewRepo.findById(idReview).get().getIdUser()) {
            Review deleteReview = reviewRepo.findById(idReview).get();
            reviewRepo.delete(deleteReview);
            return "Отзыв с id " + idReview + " удален";
        }
        else
            return "Вы не можете удалить отзыв";
    }

}
