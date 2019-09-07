package com.example.restvotingapp.service.Impl;

import com.example.restvotingapp.dto.VoteDto;
import com.example.restvotingapp.entity.Vote;
import com.example.restvotingapp.repository.VoteRepository;
import com.example.restvotingapp.service.VoteServices;
import com.example.restvotingapp.web.response.VoteRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoteServiceImpl implements VoteServices {

    @Autowired
    VoteRepository voteRepository;

//    @Override
//    public List<VoteDto> listByDate(LocalDate date) {
//        List<Vote> votes = voteRepository.findAllByDate(date);
//        ModelMapper modelMapper = new ModelMapper();
//        return votes
//                .stream()
//                .map(vote -> modelMapper.map(vote, VoteDto.class))
//                .collect(Collectors.toList());
//    }

    @Override
    public List<VoteRest> listByDate(LocalDate date) {
        return voteRepository.findAllByDateNative(date);
    }
}
