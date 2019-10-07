package com.example.restvotingapp.web.controller;

import com.example.restvotingapp.dto.VoteDto;
import com.example.restvotingapp.dto.VotePlainDto;
import com.example.restvotingapp.security.SecurityUtil;
import com.example.restvotingapp.service.UserService;
import com.example.restvotingapp.service.VoteServices;
import com.example.restvotingapp.util.EndPoins;
import com.example.restvotingapp.web.response.VoteRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@Transactional(readOnly = true)
public class VoteController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private VoteServices voteService;
    private UserService userService;

    @Autowired
    public void setVoteService(VoteServices voteService) {
        this.voteService = voteService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = EndPoins.VOTES_DATE)
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

    @GetMapping(path = EndPoins.VOTES_DATE_COUNT)
    int getCountByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Get votes count for {}", date);

        return voteService.countAllByDate(date);
    }

    @GetMapping(path = EndPoins.VOTES_DATE_COUNT_RESTAURANT)
    int getCountByDateAndRestaurant(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable(name = "restaurant_id") int restaurantId) {
        log.info("Get votes count for {} and restaurant {}", date, restaurantId);

        return voteService.countAllByDateAndRestaurant(date, restaurantId);
    }

    @PostMapping(path = EndPoins.VOTES)
    @Transactional
    @ResponseStatus(value = HttpStatus.CREATED)
    VoteRest create(@Valid @RequestBody VoteDto voteDetails, HttpServletRequest request) {
        log.info("Create vote");

        // Get current user from request
        String userEmail = SecurityUtil.getUserFromRequest(request);
        if (userEmail == null) {
            throw new RuntimeException("Unexpected error");
        }

        VoteDto createdVote = voteService.create(voteDetails, userEmail);

        VoteRest returnValue = new VoteRest();
        BeanUtils.copyProperties(createdVote, returnValue, "user,menu,restaurant");
        returnValue.setUserId(createdVote.getUser().getId());
        returnValue.setUserName(createdVote.getUser().getName());
        returnValue.setMenuId(createdVote.getMenu().getId());
        returnValue.setRestaurantId(createdVote.getRestaurant().getId());
        returnValue.setRestaurantName(createdVote.getRestaurant().getName());

        return returnValue;
    }

    @DeleteMapping(path = EndPoins.VOTES_ID)
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, HttpServletRequest request) {
        log.info("Delete vote {}", id);

        // Get current user from request
        String userEmail = SecurityUtil.getUserFromRequest(request);
        if (userEmail == null) {
            throw new RuntimeException("Unexpected error");
        }

        voteService.delete(id, userEmail);
    }
}
