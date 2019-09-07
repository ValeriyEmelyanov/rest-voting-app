package com.example.restvotingapp.service.Impl;

import com.example.restvotingapp.dto.MenuWithoutItemsDto;
import com.example.restvotingapp.dto.VoteDto;
import com.example.restvotingapp.dto.VotePlainDto;
import com.example.restvotingapp.entity.Menu;
import com.example.restvotingapp.entity.Restaraunt;
import com.example.restvotingapp.entity.User;
import com.example.restvotingapp.entity.Vote;
import com.example.restvotingapp.repository.MenuRepository;
import com.example.restvotingapp.repository.RestarauntRepository;
import com.example.restvotingapp.repository.UserRepository;
import com.example.restvotingapp.repository.VoteRepository;
import com.example.restvotingapp.service.VoteServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VoteServiceImpl implements VoteServices {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    RestarauntRepository restarauntRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MenuRepository menuRepository;

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
    public int countAllByDateAndRestaraunt(LocalDate date, int restarauntId) {
        Restaraunt restarauntEntity = restarauntRepository.findById(restarauntId)
                .orElseThrow(() -> new RuntimeException("Restaraunt with ID: " + restarauntId + " not found"));

        return voteRepository.countAllByDateAndRestaraunt(date, restarauntEntity);
    }

    @Override
    public VoteDto create(VoteDto voteDetails) {
        // Check current time
        // TODO: Check current time

        // Check if vote for date and user exists
        Integer userId = voteDetails.getUser().getId();
        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with ID: " + userId + " not found"));
        Integer menuId = voteDetails.getMenu().getId();
        MenuWithoutItemsDto menuWithoutItemsRest = menuRepository.getByIdWithoutItems(menuId);
        if (menuWithoutItemsRest == null) {
            throw  new RuntimeException("Menu with ID: " + menuId + " not found");
        }

        Long id = voteRepository.findByDateAndUserQL(menuWithoutItemsRest.getDate(), userEntity);
        if (id != null) {
            voteRepository.deleteById(id);
        }

        ModelMapper modelMapper = new ModelMapper();

        // Save Vote entity
        Menu menuEntity = modelMapper.map(menuWithoutItemsRest, Menu.class);
        Vote voteEntity = new Vote();
        voteEntity.setUser(userEntity);
        voteEntity.setMenu(menuEntity);
        voteEntity.setRestaraunt(menuEntity.getRestaraunt());
        voteEntity.setDate(menuEntity.getDate());

        Vote voteStored = voteRepository.save(voteEntity);

        return modelMapper.map(voteStored, VoteDto.class);
    }

    @Override
    public void delete(Long id) {
        voteRepository.deleteById(id);
    }
}
