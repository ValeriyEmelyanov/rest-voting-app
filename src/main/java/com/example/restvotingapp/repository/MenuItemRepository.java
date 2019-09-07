package com.example.restvotingapp.repository;

import com.example.restvotingapp.entity.MenuItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends CrudRepository<MenuItem, Integer> {
}
