package com.example.restvotingapp.service.Impl;

import com.example.restvotingapp.dto.MenuDto;
import com.example.restvotingapp.dto.MenuItemDto;
import com.example.restvotingapp.entity.Menu;
import com.example.restvotingapp.entity.MenuItem;
import com.example.restvotingapp.entity.Restaraunt;
import com.example.restvotingapp.repository.MenuItemRepository;
import com.example.restvotingapp.repository.MenuRepository;
import com.example.restvotingapp.repository.RestarauntRepository;
import com.example.restvotingapp.service.MenuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    private MenuRepository menuRepository;
    private RestarauntRepository restarauntRepository;
    private MenuItemRepository itemRepository;

    @Autowired
    public void setMenuRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Autowired
    public void setRestarauntRepository(RestarauntRepository restarauntRepository) {
        this.restarauntRepository = restarauntRepository;
    }

    @Autowired
    public void setItemRepository(MenuItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<MenuDto> listByDate(LocalDate date) {
        List<Menu> menus = menuRepository.findAllByDate(date);
        ModelMapper modelMapper = new ModelMapper();
        return menus
                .stream()
                .map(menu -> modelMapper.map(menu, MenuDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public MenuDto getByDateAndRestaraunt(LocalDate date, Integer restarauntId) {
        Restaraunt restarauntEntity = restarauntRepository.findById(restarauntId)
                .orElseThrow(() -> new RuntimeException("Restaraunt with ID: " + restarauntId + " not found"));

        Menu menuEntity = menuRepository.findByDateAndRestaraunt(date, restarauntEntity);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(menuEntity, MenuDto.class);
    }

    @Override
    public MenuDto getById(Integer id) {
        Menu menuEntity = menuRepository.getById(id)
                .orElseThrow(() -> new RuntimeException("Menu with ID: " + id + " not found"));

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(menuEntity, MenuDto.class);
    }

    @Override
    @Transactional
    public MenuDto create(MenuDto menuDetails) {
        // Find restaraunt
        Integer restarauntId = menuDetails.getRestaraunt().getId();
        Restaraunt restaraunt = restarauntRepository.findById(restarauntId)
                .orElseThrow(() -> new RuntimeException("Restaraunt with ID: " + restarauntId + " not found"));

        // Check if menu exist for date and restaraunt
        if (menuRepository.findByDateAndRestaraunt(menuDetails.getDate(), restaraunt) != null) {
            throw new RuntimeException("Menu already exists!");
        }

        // Create new menu
        Menu menuEntity = new Menu();
        menuEntity.setRestaraunt(restaraunt);
        menuEntity.setDate(menuDetails.getDate());

        // Create and set new menu items collections
        List<MenuItem> items = new ArrayList<>();
        for (MenuItemDto itemDto : menuDetails.getItems()) {
            MenuItem itemEntity = new MenuItem();
            BeanUtils.copyProperties(itemDto, itemEntity, "id,menu");
            itemEntity.setMenu(menuEntity);
            items.add(itemEntity);
        }
        menuEntity.setItems(items);

        Menu storedMenu = menuRepository.save(menuEntity);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(storedMenu, MenuDto.class);
    }

    @Override
    @Transactional
    public MenuDto update(Integer id, MenuDto menuDetails) {
        // Find menu
        Menu menuEntity = menuRepository.getById(id)
                .orElseThrow(() -> new RuntimeException("Menu with ID: " + id + " not found"));

        // Delete old menu items
        List<MenuItem> itemsToDelete = menuEntity.getItems();
        menuEntity.setItems(new ArrayList<>());
        for (MenuItem item : itemsToDelete) {
            itemRepository.delete(item);
        }

        // Create and set new menu items collections
        List<MenuItem> items = new ArrayList<>();
        for (MenuItemDto itemDto : menuDetails.getItems()) {
            MenuItem itemEntity = new MenuItem();
            BeanUtils.copyProperties(itemDto, itemEntity, "id,menu");
            itemEntity.setMenu(menuEntity);
            items.add(itemEntity);
        }
        menuEntity.setItems(items);

        Menu updatedMenu = menuRepository.save(menuEntity);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(updatedMenu, MenuDto.class);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Menu userEntity = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu with ID: " + id + " not found"));

        menuRepository.delete(userEntity);
    }
}
