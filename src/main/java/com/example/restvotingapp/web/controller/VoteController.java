package com.example.restvotingapp.web.controller;

import com.example.restvotingapp.dto.VoteDto;
import com.example.restvotingapp.entity.Vote;
import com.example.restvotingapp.service.VoteServices;
import com.example.restvotingapp.web.response.VoteRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("votes")
public class VoteController {

    @Autowired
    VoteServices voteServices;

    @GetMapping(path = "/date/{date}")
    List<VoteRest> listByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        List<VoteDto> votes = voteServices.listByDate(date);
//
//        ModelMapper modelMapper = new ModelMapper();
//
//        return votes
//                .stream()
//                .map(voteDto -> modelMapper.map(voteDto, VoteRest.class))
//                .collect(Collectors.toList());
        return voteServices.listByDate(date);
    }
}
