package com.example.OnlineShop.repo;

import com.example.OnlineShop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailAndPass(String email, String pass);
}
