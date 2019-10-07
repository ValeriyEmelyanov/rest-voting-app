package com.example.restvotingapp.service;

import com.example.restvotingapp.dto.VoteDto;
import com.example.restvotingapp.dto.VotePlainDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface VoteServices {
    List<VotePlainDto> listByDate(LocalDate date, int page, int limit);
    int countAllByDate(LocalDate date);
    int countAllByDateAndRestaurant(LocalDate date, int restaurantId);
    VoteDto create(VoteDto voteDetails, String userEmail);
    void delete(Long id, String userEmail);
}
