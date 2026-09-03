package com.example.StudyCards.service;

import com.example.StudyCards.entity.Card;
import com.example.StudyCards.entity.Folder;
import com.example.StudyCards.entity.Theme;

import java.util.List;

public interface CatalogService {

    List<Theme> getThemes();

    List<Folder> getFoldersByThemeId(Long id);

    List<Card> getCardsByFolderId(Long id);

    Card getCardById(Long id);

}
