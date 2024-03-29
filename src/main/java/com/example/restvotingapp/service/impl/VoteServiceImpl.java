package com.example.restvotingapp.service.impl;

import com.example.restvotingapp.dto.MenuWithoutItemsDto;
import com.example.restvotingapp.dto.VoteDto;
import com.example.restvotingapp.dto.VotePlainDto;
import com.example.restvotingapp.entity.Menu;
import com.example.restvotingapp.entity.Restaurant;
import com.example.restvotingapp.entity.User;
import com.example.restvotingapp.entity.Vote;
import com.example.restvotingapp.exceptions.RecordAlreadyExistsException;
import com.example.restvotingapp.exceptions.RecordNotFoundException;
import com.example.restvotingapp.exceptions.WrongRequest;
import com.example.restvotingapp.exceptions.WrongTimeException;
import com.example.restvotingapp.repository.MenuRepository;
import com.example.restvotingapp.repository.RestaurantRepository;
import com.example.restvotingapp.repository.UserRepository;
import com.example.restvotingapp.repository.VoteRepository;
import com.example.restvotingapp.service.VoteServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class VoteServiceImpl implements VoteServices {

    private final LocalTime DEAD_LINE_TIME = LocalTime.of(11, 0);

    private VoteRepository voteRepository;
    private RestaurantRepository restaurantRepository;
    private UserRepository userRepository;
    private MenuRepository menuRepository;

    private ModelMapper modelMapper;

    @Autowired
    public void setVoteRepository(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Autowired
    public void setRestaurantRepository(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setMenuRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<VotePlainDto> listByDate(LocalDate date, int page, int limit) {
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<VotePlainDto> votesPage = voteRepository.findAllByDateQL(date, pageableRequest);

        return votesPage.getContent();
    }

    @Override
    public int countAllByDate(LocalDate date) {
        return voteRepository.countAllByDate(date);
    }

    @Override
    public int countAllByDateAndRestaurant(LocalDate date, int restaurantId) {
        Restaurant restaurantEntity = restaurantRepository.getById(restaurantId)
                .orElseThrow(() -> new RecordNotFoundException("Restaurant with ID: " + restaurantId + " not found"));

        return voteRepository.countAllByDateAndRestaurant(date, restaurantEntity);
    }

    @Override
    @Transactional
    public VoteDto create(VoteDto voteDetails, String userEmail) {
        checkCurrentTime();

        // Check if vote for date and user exists or user already voted

        User userEntity = userRepository.findByEmail(userEmail);
        if (userEntity == null){
            throw new UsernameNotFoundException("User with email: " + userEmail + " not found");
        }

        Integer restaurantId = voteDetails.getRestaurant().getId();
        Restaurant restaurant = restaurantRepository.getById(restaurantId)
                .orElseThrow(() -> new RecordNotFoundException("Restaurant with ID: " + restaurantId + " not found"));

        MenuWithoutItemsDto menuWithoutItemsRest = menuRepository.findByDateAndRestaurantQL(
                voteDetails.getDate(), restaurant)
                .orElseThrow(() -> new RecordNotFoundException("Menu for restaurant ID:" + restaurantId
                        + " and date:" + voteDetails.getDate() + "not found"));

        Long id = voteRepository.findByDateAndUserAndMenuQL(
                menuWithoutItemsRest.getDate(), userEntity, menuWithoutItemsRest.getId());
        if (id != null) {
            throw new RecordAlreadyExistsException("Vote already exists!");
        }

        id = voteRepository.findByDateAndUserQL(menuWithoutItemsRest.getDate(), userEntity);
        if (id != null) {
            voteRepository.deleteById(id);
        }

        // Save Vote entity

        Menu menuEntity = modelMapper.map(menuWithoutItemsRest, Menu.class);
        Vote voteEntity = new Vote();
        voteEntity.setUser(userEntity);
        voteEntity.setMenu(menuEntity);
        voteEntity.setRestaurant(menuEntity.getRestaurant());
        voteEntity.setDate(menuEntity.getDate());

        Vote voteStored = voteRepository.save(voteEntity);

        return modelMapper.map(voteStored, VoteDto.class);
    }

    @Override
    @Transactional
    public void delete(Long id, String userEmail) {
        checkCurrentTime();

        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Vote with ID: " + id + " not found"));

        if (!userEmail.equals(vote.getUser().getEmail())) {
            throw new WrongRequest("Wrong ID:" + id);
        }

        voteRepository.deleteById(id);
    }

    private void checkCurrentTime() {
        if (LocalTime.now().isAfter(DEAD_LINE_TIME)) {
            throw new WrongTimeException("Voting is impossible after " + DEAD_LINE_TIME);
        }
    }
}
