package com.example.StudyCards.service.impl;

import com.example.StudyCards.entity.Folder;
import com.example.StudyCards.repository.FolderRepository;
import com.example.StudyCards.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;

    @Override
    public List<Folder> getByThemeId(Long themeId) {
        return folderRepository.findByThemeId(themeId);
    }
}
