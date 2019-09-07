package com.example.restvotingapp.service;

import com.example.restvotingapp.dto.VoteDto;
import com.example.restvotingapp.web.response.VoteRest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface VoteServices {
    List<VoteRest> listByDate(LocalDate date, int page, int limit);
}
