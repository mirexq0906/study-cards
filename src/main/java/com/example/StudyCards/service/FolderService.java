package com.example.StudyCards.service;

import com.example.StudyCards.entity.Folder;

import java.util.List;

public interface FolderService {

    List<Folder> getByThemeId(Long themeId);

}
