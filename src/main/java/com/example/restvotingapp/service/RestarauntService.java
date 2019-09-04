package com.example.restvotingapp.service;

import com.example.restvotingapp.dto.RestarauntDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestarauntService {
    List<RestarauntDto> list(int page, int limit);
    List<RestarauntDto> listActive();
    RestarauntDto getById(Integer id);
    RestarauntDto create(RestarauntDto restarauntDetails);
    RestarauntDto update(Integer id, RestarauntDto restarauntDetails);
    void delete(Integer id);
}
