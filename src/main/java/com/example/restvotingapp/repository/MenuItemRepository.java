package com.example.restvotingapp.repository;

import com.example.restvotingapp.entity.MenuItem;
import org.springframework.data.repository.CrudRepository;

public interface MenuItemRepository extends CrudRepository<MenuItem, Integer> {
}
