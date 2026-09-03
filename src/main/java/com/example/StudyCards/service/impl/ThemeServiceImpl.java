package com.example.StudyCards.service.impl;

import com.example.StudyCards.entity.Theme;
import com.example.StudyCards.repository.ThemeRepository;
import com.example.StudyCards.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;

    @Override
    public List<Theme> getAll() {
        return themeRepository.findAll();
    }

}
