package com.example.restvotingapp.repository;

import com.example.restvotingapp.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User findById(String userId);
    User findByEmail(String email);
}
