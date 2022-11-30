package com.example.OnlineShop.controller;


import com.example.OnlineShop.models.Item;
//import com.example.OnlineShop.repo.BasketRepo;
import com.example.OnlineShop.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemAPI {
    @Autowired
    private ItemRepo itemRepo;
    //@Autowired
    //private BasketRepo basketRepo;

    @GetMapping(value = "/list")
    public List<Item> getListItems(){
        return itemRepo.findAll();
    }


    @GetMapping(value = "/item/id{itemId}")
    public Item getItem(@PathVariable long itemId){
        return itemRepo.findById(itemId).get();
    }
    @PostMapping(value = "/item/save")
    public String saveItem(@RequestBody Item item){
        //TODO Добавить проверку продавца


        itemRepo.save(item);
        return "Товар сохранен";
    }

    @PutMapping(value = "/item/update/{id}")
    public String updateUser(@PathVariable long id, @RequestBody Item item){
        Item updatedItem = itemRepo.findById(id).get();
        updatedItem.setName(item.getName());
        updatedItem.setDescription(item.getDescription());
        updatedItem.setPrice(item.getPrice());
        updatedItem.setAmount(item.getAmount());
        updatedItem.setRating(item.getRating());
        itemRepo.save(updatedItem);
        return "Товар обновлен";
    }
    @DeleteMapping(value = "/item/delete/{id}")
    public String deleteItem(@PathVariable long id){
        Item deleteItem = itemRepo.findById(id).get();
        itemRepo.delete(deleteItem);
        return "Товар с id " +id+ " удален";
    }
}
