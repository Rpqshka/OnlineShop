package com.example.OnlineShop.controller;

import com.example.OnlineShop.models.User;
import com.example.OnlineShop.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserAPI {

    @GetMapping(value = "/")
    public String getPage(){
        return "Welcome";
    }

    @Autowired
    private UserRepo userRepo;

    @GetMapping(value = "/users")
    public Iterable<User> getUsers(){
        return userRepo.findAll();
    }

    @PostMapping(value = "/user/add")
    public String saveUser(@RequestBody User user){
        userRepo.save(user);
        return "Пользователь сохранен";
    }


    @PutMapping(value = "/user{id}/update/check_admin/{passAdmin}")
    public String updateUser(@PathVariable long id,@PathVariable String passAdmin, @RequestBody User user){
        if(userRepo.findByPass(passAdmin).getRole().equals("admin")) {
            User updatedUser = userRepo.findById(id).get();
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setSecondName(user.getSecondName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPass(user.getPass());
            updatedUser.setPhone(user.getPhone());
            updatedUser.setRole(user.getRole());
            userRepo.save(updatedUser);
            return "Пользователь обновлен";
        }
        else{
            return "У вас недостаточно прав";
        }
    }

    @DeleteMapping(value = "/user{id}/delete/check_admin/{passAdmin}")
    public String deleteUser(@PathVariable long id){
        User deleteUser = userRepo.findById(id).get();
        userRepo.delete(deleteUser);
        return "Пользователь с id " +id+ " удален";
    }
}
