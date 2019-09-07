package com.example.restvotingapp.web.controller;

import com.example.restvotingapp.dto.RestarauntDto;
import com.example.restvotingapp.service.RestarauntService;
import com.example.restvotingapp.web.response.RestarauntRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("restaraunts")
public class RestarauntController {

    @Autowired
    RestarauntService restarauntService;

    @GetMapping
    public List<RestarauntRest> list(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<RestarauntRest> returnValue = new ArrayList<>();
        List<RestarauntDto> restaraunts = restarauntService.list(page - 1, limit);

        for (RestarauntDto restarauntDto : restaraunts) {
            RestarauntRest restarauntRest = new RestarauntRest();
            BeanUtils.copyProperties(restarauntDto, restarauntRest);
            returnValue.add(restarauntRest);
        }

        return returnValue;
    }

    @GetMapping("/active")
    public List<RestarauntRest> listActive() {
        List<RestarauntRest> returnValue = new ArrayList<>();

        List<RestarauntDto> restaraunts = restarauntService.listActive();
        for (RestarauntDto restarauntDto : restaraunts) {
            RestarauntRest restarauntRest = new RestarauntRest();
            BeanUtils.copyProperties(restarauntDto, restarauntRest);
            returnValue.add(restarauntRest);
        }

        return returnValue;
    }

    @GetMapping(path = "/{id}")
    public RestarauntRest getById(@PathVariable Integer id) {
        RestarauntRest returnValue = new RestarauntRest();

        RestarauntDto restarauntDto = restarauntService.getById(id);
        BeanUtils.copyProperties(restarauntDto, returnValue);

        return returnValue;
    }

    @PostMapping
    public RestarauntRest create(@RequestBody RestarauntDto restarauntDetails) {
        RestarauntRest returnValue = new RestarauntRest();

        RestarauntDto createdRestaraunt = restarauntService.create(restarauntDetails);
        BeanUtils.copyProperties(createdRestaraunt, returnValue);

        return returnValue;
    }

    @PutMapping(path = "/{id}")
    public RestarauntRest update(@PathVariable Integer id, @RequestBody RestarauntDto restarauntDetails) {
        RestarauntRest returnValue = new RestarauntRest();

        RestarauntDto updatedRestaraunt = restarauntService.update(id, restarauntDetails);
        BeanUtils.copyProperties(updatedRestaraunt, returnValue);

        return returnValue;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        restarauntService.delete(id);
    }

}
