package com.example.restvotingapp.service.Impl;

import com.example.restvotingapp.dto.MenuWithoutItemsDto;
import com.example.restvotingapp.dto.VoteDto;
import com.example.restvotingapp.dto.VotePlainDto;
import com.example.restvotingapp.entity.Menu;
import com.example.restvotingapp.entity.Restaurant;
import com.example.restvotingapp.entity.User;
import com.example.restvotingapp.entity.Vote;
import com.example.restvotingapp.exceptions.RecordAlreadyExistsException;
import com.example.restvotingapp.exceptions.RecordNotFoundException;
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
        Restaurant restaurantEntity = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RecordNotFoundException("Restaurant with ID: " + restaurantId + " not found"));

        return voteRepository.countAllByDateAndRestaurant(date, restaurantEntity);
    }

    @Override
    @Transactional
    public VoteDto create(VoteDto voteDetails) {
        // Check current time
        if (LocalTime.now().isAfter(DEAD_LINE_TIME)) {
            throw new WrongTimeException("Voting is impossible after " + DEAD_LINE_TIME);
        }

        // Check if vote for date and user exists
        Integer userId = voteDetails.getUser().getId();
        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with ID: " + userId + " not found"));
        Integer menuId = voteDetails.getMenu().getId();
        MenuWithoutItemsDto menuWithoutItemsRest = menuRepository.getByIdWithoutItems(menuId);
        if (menuWithoutItemsRest == null) {
            throw  new RecordNotFoundException("Menu with ID: " + menuId + " not found");
        }

        Long id = voteRepository.findByDateAndUserAndMenuQL(menuWithoutItemsRest.getDate(), userEntity, menuId);
        if (id != null) {
            throw new RecordAlreadyExistsException("Vote already exists!");
        }

        id = voteRepository.findByDateAndUserQL(menuWithoutItemsRest.getDate(), userEntity);
        if (id != null) {
            voteRepository.deleteById(id);
        }

        ModelMapper modelMapper = new ModelMapper();

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
    public void delete(Long id) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Vote with ID: " + id + " not found"));

        voteRepository.deleteById(id);
    }
}
