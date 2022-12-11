package com.example.OnlineShop.controller;


import com.example.OnlineShop.models.Basket;
import com.example.OnlineShop.models.Item;
import com.example.OnlineShop.repo.BasketRepo;
import com.example.OnlineShop.repo.ItemRepo;
import com.example.OnlineShop.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemAPI {
    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BasketRepo basketRepo;


    @GetMapping(value = "/item/all")
    public List<Item> getListItems(){
        return itemRepo.findAll();
    }


    @GetMapping(value = "/item/{itemId}")
    public Item getItem(@PathVariable long itemId){
        return itemRepo.findById(itemId).get();
    }
    @PostMapping(value = "/item/add/check_role/{email}:{pass}")
    public String addItem(@PathVariable String email, @PathVariable String pass,@RequestBody Item item){
        if (userRepo.findByEmailAndPass(email, pass).getRole().equals("seller")) {
            itemRepo.save(item);
            return "Товар сохранен";
        }
        else
            return "Только продавец может добавлять новый товар";
    }

    @PutMapping(value = "/item/buy/{idItem}/check_role/{email}:{pass}")
    public String buyItem(@PathVariable long idItem,@PathVariable String email, @PathVariable String pass){
        if (userRepo.findByEmailAndPass(email, pass).getRole().equals("user")) {
            Item updatedItem = itemRepo.findById(idItem).get();
            if (updatedItem.getAmount() > 0) {
                updatedItem.setAmount(updatedItem.getAmount() - 1);
                itemRepo.save(updatedItem);
                Basket basket = basketRepo.findBasketByIdUser(userRepo.findByEmailAndPass(email, pass).getId());
                basket.addIdItems(idItem);
                basket.addItemCount();
                basket.addPrice(updatedItem.getPrice());
                basketRepo.save(basket);
                return "Товар добавлен в корзину";
            } else
                return "Товар закончился";
        }
        else
            return "Только пользователи могут купить товар";
    }

    @PutMapping(value = "/item/update/{idItem}/check_role/{email}:{pass}")
    public String updateItem(@PathVariable long idItem,@PathVariable String email, @PathVariable String pass, @RequestBody Item item){
        if (userRepo.findByEmailAndPass(email, pass).getRole().equals("seller") ||
                userRepo.findByEmailAndPass(email, pass).getRole().equals("admin")) {
            Item updatedItem = itemRepo.findById(idItem).get();
            updatedItem.setName(item.getName());
            updatedItem.setDescription(item.getDescription());
            updatedItem.setPrice(item.getPrice());
            updatedItem.setAmount(item.getAmount());
            updatedItem.setRating(item.getRating());
            itemRepo.save(updatedItem);
            return "Товар обновлен";
        }
        else
            return "Только продавец или администратор могут изменить карточку товара";
    }

    @DeleteMapping(value = "/item/delete/{idItem}/check_role/{email}:{pass}")
    public String deleteItem(@PathVariable long idItem,@PathVariable String email, @PathVariable String pass){
        if (userRepo.findByEmailAndPass(email, pass).getRole().equals("seller") ||
                userRepo.findByEmailAndPass(email, pass).getRole().equals("admin")) {
            Item deleteItem = itemRepo.findById(idItem).get();
            itemRepo.delete(deleteItem);
            return "Товар с id " + idItem + " удален";
        }
        else
            return "Только продавец или администратор могут удалить товар";
    }
}
