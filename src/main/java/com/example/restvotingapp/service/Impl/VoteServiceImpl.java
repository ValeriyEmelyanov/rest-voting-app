package com.example.restvotingapp.service.Impl;

import com.example.restvotingapp.dto.VoteDto;
import com.example.restvotingapp.entity.Vote;
import com.example.restvotingapp.repository.VoteRepository;
import com.example.restvotingapp.service.VoteServices;
import com.example.restvotingapp.web.response.VoteRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoteServiceImpl implements VoteServices {

    @Autowired
    VoteRepository voteRepository;

    @Override
    public List<VoteRest> listByDate(LocalDate date, int page, int limit) {
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<VoteRest> votesPage = voteRepository.findAllByDateNative(date, pageableRequest);

        return votesPage.getContent();
    }
}
