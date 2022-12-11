package com.example.OnlineShop.controller;

import com.example.OnlineShop.models.Basket;
import com.example.OnlineShop.models.User;
import com.example.OnlineShop.repo.BasketRepo;
import com.example.OnlineShop.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserAPI {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BasketRepo basketRepo;

    @GetMapping(value = "/user/all")
    public Iterable<User> getUsers(){
        return userRepo.findAll();
    }

    @GetMapping(value = "/user/login/{email}:{pass}")
    public String loginUser(@PathVariable String email, @PathVariable String pass){
        if(userRepo.findByEmailAndPass(email, pass) != null)
            return "Авторизация успешна";
        else
            return "Повторите попытку авторизации";
    }

    @PostMapping(value = "/user/register")
    public String registerUser(@RequestBody User user){
        if(userRepo.findByEmail(user.getEmail()) == null) {
            userRepo.save(user);
            Basket basket = new Basket(user.getId());
            basketRepo.save(basket);
            return "Пользователь сохранен";
        }
        else
            return "Пользователь с таким email уже зарегистрирован";
    }


    @PutMapping(value = "/user/update/{idUser}/check_role/{email}:{pass}")
    public String updateUser(@PathVariable long idUser, @PathVariable String email,@PathVariable String pass, @RequestBody User user){
        if(userRepo.findByEmailAndPass(email, pass).getRole().equals("admin") ||
                userRepo.findByEmailAndPass(email, pass).getId() == idUser) {
            User updatedUser = userRepo.findById(idUser).get();
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setSecondName(user.getSecondName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPass(user.getPass());
            updatedUser.setPhone(user.getPhone());
            if(userRepo.findByEmailAndPass(email, pass).getRole().equals("admin"))
                updatedUser.setRole(user.getRole());
            userRepo.save(updatedUser);
            return "Пользователь обновлен";
        }
        else{
            return "У вас недостаточно прав";
        }
    }

    @DeleteMapping(value = "/user/delete/{idUser}/check_role/{email}:{pass}")
    public String deleteUser(@PathVariable long idUser, @PathVariable String email,@PathVariable String pass){
        if(userRepo.findByEmailAndPass(email, pass).getRole().equals("admin")) {
            Basket basket = basketRepo.findBasketByIdUser(userRepo.findByEmailAndPass(email, pass).getId());
            basketRepo.delete(basket);
            User deleteUser = userRepo.findById(idUser).get();
            userRepo.delete(deleteUser);
            return "Пользователь удален";
        }
        else
            return "Пользователь не найден";
    }
}
