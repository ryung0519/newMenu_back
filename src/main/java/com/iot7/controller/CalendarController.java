package com.iot7.controller;

import com.iot7.service.MenuService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.iot7.dto.CalendarMenuDTO;

import java.util.List;

@RestController
@RequestMapping("/calendar")
@CrossOrigin(origins = "*")
public class CalendarController {
    private final MenuService menuService;

    public CalendarController(MenuService menuService) {
        this.menuService = menuService;
    }
    @GetMapping("/menus")
    public List<CalendarMenuDTO> getCalendarMenus(){
        return menuService.getAllMenuForCalendar();
    }
}
