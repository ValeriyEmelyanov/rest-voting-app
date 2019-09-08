package com.example.restvotingapp.service.Impl;

import com.example.restvotingapp.dto.RestarauntDto;
import com.example.restvotingapp.entity.Restaraunt;
import com.example.restvotingapp.repository.RestarauntRepository;
import com.example.restvotingapp.service.RestarauntService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestarauntServiceImpl implements RestarauntService {

    private RestarauntRepository restarauntRepository;

    @Autowired
    public void setRestarauntRepository(RestarauntRepository restarauntRepository) {
        this.restarauntRepository = restarauntRepository;
    }

    @Override
    public List<RestarauntDto> list(int page, int limit) {
        List<RestarauntDto> returnValue = new ArrayList<>();

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<Restaraunt> restarauntsPage = restarauntRepository.findAll(pageableRequest);
        List<Restaraunt> restaraunts = restarauntsPage.getContent();

        for (Restaraunt restarauntEntity : restaraunts) {
            RestarauntDto restarauntDto = new RestarauntDto();
            BeanUtils.copyProperties(restarauntEntity, restarauntDto);
            returnValue.add(restarauntDto);
        }

        return returnValue;
    }

    @Override
    public List<RestarauntDto> listActive() {
        List<RestarauntDto> returnValue = new ArrayList<>();

        // Variant
        //List<Restaraunt> restaraunts = restarauntRepository.findAllByActive(true);

        Optional<List<Restaraunt>> optionalRestaraunt = restarauntRepository.getByActive(true);
        if (optionalRestaraunt.isPresent()) {
            List<Restaraunt> restaraunts = optionalRestaraunt.get();

            for (Restaraunt restaraunt : restaraunts) {
                RestarauntDto restarauntDto = new RestarauntDto();
                BeanUtils.copyProperties(restaraunt, restarauntDto);
                returnValue.add(restarauntDto);
            }
        }

        return returnValue;
    }

    @Override
    public RestarauntDto getById(Integer id) {
        Restaraunt restarauntEntity = restarauntRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaraunt with ID: " + id + " not found"));

        RestarauntDto returnValue = new RestarauntDto();
        BeanUtils.copyProperties(restarauntEntity, returnValue);

        return returnValue;
    }

    @Override
    public RestarauntDto create(RestarauntDto restarauntDetails) {
        if (restarauntRepository.findByName(restarauntDetails.getName()) != null) {
            throw new RuntimeException("Restaraunt already exists!");
        }

        Restaraunt restarauntEntity = new Restaraunt();
        BeanUtils.copyProperties(restarauntDetails, restarauntEntity);

        Restaraunt storedRestaraunt = restarauntRepository.save(restarauntEntity);

        RestarauntDto returnValue = new RestarauntDto();
        BeanUtils.copyProperties(storedRestaraunt, returnValue);

        return returnValue;
    }

    @Override
    public RestarauntDto update(Integer id, RestarauntDto restarauntDetails) {
        Restaraunt restarauntEntity = restarauntRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaraunt with ID: " + id + " not found"));

        restarauntEntity.setName(restarauntDetails.getName());
        restarauntEntity.setActive(restarauntDetails.isActive());
        Restaraunt updatedRestaraunt = restarauntRepository.save(restarauntEntity);

        RestarauntDto returnValue = new RestarauntDto();
        BeanUtils.copyProperties(updatedRestaraunt, returnValue);

        return returnValue;
    }

    @Override
    public void delete(Integer id) {
        Restaraunt userEntity = restarauntRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaraunt with ID: " + id + " not found"));

        restarauntRepository.delete(userEntity);
    }
}
