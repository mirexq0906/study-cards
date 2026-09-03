package com.example.StudyCards.web.controller;

import com.example.StudyCards.service.ThemeService;
import com.example.StudyCards.web.dto.ThemeDto;
import com.example.StudyCards.web.mapper.ThemeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;
    private final ThemeMapper themeMapper;

    @GetMapping
    public List<ThemeDto> getAll() {
        return themeMapper.toThemeDtoList(themeService.getAll());
    }

}
