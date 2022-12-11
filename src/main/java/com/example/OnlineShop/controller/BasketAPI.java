package com.example.OnlineShop.controller;

import com.example.OnlineShop.models.Basket;
import com.example.OnlineShop.models.Item;
import com.example.OnlineShop.repo.BasketRepo;
import com.example.OnlineShop.repo.ItemRepo;
import com.example.OnlineShop.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BasketAPI {
    @Autowired
    private BasketRepo basketRepo;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ItemRepo itemRepo;


    @GetMapping(value = "user/{idUser}/basket/check_role/{email}:{pass}")
    public Basket showUserBasket(@PathVariable long idUser, @PathVariable String email, @PathVariable String pass){
        if(userRepo.findByEmailAndPass(email,pass).getRole().equals("admin") ||
                userRepo.findByEmailAndPass(email, pass).getId() == basketRepo.findById(idUser).get().getIdUser()) {
            return basketRepo.findBasketByIdUser(idUser);
        }
        return null;
    }

    @PutMapping(value = "user/{idUser}/basket/update/{idItem}/check_role/{email}:{pass}")
    public String updateBasket(@PathVariable long idUser,@PathVariable long idItem, @PathVariable String email, @PathVariable String pass){
        if(userRepo.findByEmailAndPass(email,pass).getRole().equals("admin") ||
                userRepo.findByEmailAndPass(email, pass).getId() == basketRepo.findBasketByIdUser(idUser).getIdUser()) {
            Item updatedItem = itemRepo.findById(idItem).get();
            //TODO ДОДЕЛАТЬ КОЛ_ВО
            if (updatedItem.getAmount() > 0) {
                Basket basket = basketRepo.findBasketByIdUser(userRepo.findByEmailAndPass(email, pass).getId());
                basket.subIdItems(idItem);
                basket.subItemCount();
                basket.subPrice(updatedItem.getPrice());
                basketRepo.save(basket);
                return "Корзина обновлена";
            }
            else return "Вы не можете убрать этот товар";
        }
        else return "Вы не имеете доступ к этой корзине";
    }
    @DeleteMapping(value = "user/{idUser}/basket/delete/{idItem}/check_role/{email}:{pass}")
    public String deleteBasket(@PathVariable long idUser,@PathVariable long idItem, @PathVariable String email, @PathVariable String pass){
        if(userRepo.findByEmailAndPass(email,pass).getRole().equals("admin") ||
                userRepo.findByEmailAndPass(email, pass).getId() == basketRepo.findBasketByIdUser(idUser).getIdUser()) {
            Basket basket = basketRepo.findBasketByIdUser(userRepo.findByEmailAndPass(email, pass).getId());
            basket.setIdItems("");
            basket.setPrice(0);
            basket.setItemCount(0);
            basketRepo.save(basket);
            return "Вы очистили корзину";
        }
        else return"Вы не можете очистить корзину";

    }
}
