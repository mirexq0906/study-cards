package com.example.StudyCards.web.controller;

import com.example.StudyCards.service.FolderService;
import com.example.StudyCards.web.dto.FolderDto;
import com.example.StudyCards.web.mapper.FolderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;
    private final FolderMapper folderMapper;

    @GetMapping
    public List<FolderDto> getByThemeId(@RequestParam Long themeId) {
        return folderMapper.toFolderDtoList(folderService.getByThemeId(themeId));
    }

}
