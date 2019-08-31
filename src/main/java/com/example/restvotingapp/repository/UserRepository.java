package com.example.restvotingapp.repository;

import com.example.restvotingapp.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    Optional<User> findById(Integer id);
    User findByEmail(String email);
}
