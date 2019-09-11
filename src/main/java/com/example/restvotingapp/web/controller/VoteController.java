package com.example.restvotingapp.web.controller;

import com.example.restvotingapp.dto.VoteDto;
import com.example.restvotingapp.dto.VotePlainDto;
import com.example.restvotingapp.service.VoteServices;
import com.example.restvotingapp.web.response.VoteRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("votes")
@Transactional(readOnly = true)
public class VoteController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private VoteServices voteService;

    @Autowired
    public void setVoteService(VoteServices voteService) {
        this.voteService = voteService;
    }

    @GetMapping(path = "/date/{date}")
    List<VoteRest> listByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "25") int limit) {
        log.info("Get votes for {}, page {} and limit {}", date, page, limit);

        List<VoteRest> returnValue = new ArrayList<>();

        List<VotePlainDto> votes = voteService.listByDate(date, page - 1, limit);
        for (VotePlainDto votePlainDto : votes) {
            VoteRest voteRest = new VoteRest();
            BeanUtils.copyProperties(votePlainDto, voteRest);
            returnValue.add(voteRest);
        }

        return returnValue;
    }

    @GetMapping(path = "/date/{date}/count")
    int getCountByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Get votes count for {}", date);

        return voteService.countAllByDate(date);
    }

    @GetMapping(path = "/date/{date}/count/restaraunt/{restaraunt_id}")
    int getCountByDateAndRestaraunt(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable(name = "restaraunt_id") int restarauntId) {
        log.info("Get votes count for {} and restaraunt {}", date, restarauntId);

        return voteService.countAllByDateAndRestaraunt(date, restarauntId);
    }

    @PostMapping
    @Transactional
    VoteRest create(@RequestBody VoteDto voteDetails) {
        log.info("Create vote");

        VoteRest returnValue = new VoteRest();

        VoteDto createdVote = voteService.create(voteDetails);
        BeanUtils.copyProperties(createdVote, returnValue, "user,menu,restaraunt");
        returnValue.setUserId(createdVote.getUser().getId());
        returnValue.setUserName(createdVote.getUser().getName());
        returnValue.setMenuId(createdVote.getMenu().getId());
        returnValue.setRestarauntId(createdVote.getRestaraunt().getId());
        returnValue.setRestarauntName(createdVote.getRestaraunt().getName());

        return returnValue;
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Delete vote {}", id);

        voteService.delete(id);
    }
}
