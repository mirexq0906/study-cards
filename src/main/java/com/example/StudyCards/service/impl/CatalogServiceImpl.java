package com.example.StudyCards.service.impl;

import com.example.StudyCards.entity.Card;
import com.example.StudyCards.entity.Folder;
import com.example.StudyCards.entity.Theme;
import com.example.StudyCards.repository.CardRepository;
import com.example.StudyCards.repository.FolderRepository;
import com.example.StudyCards.repository.ThemeRepository;
import com.example.StudyCards.service.CatalogService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CardRepository cardRepository;
    private final FolderRepository folderRepository;
    private final ThemeRepository themeRepository;

    @Override
    public List<Theme> getThemes() {
        return themeRepository.findAll();
    }

    @Override
    public List<Folder> getFoldersByThemeId(Long id) {
        return folderRepository.findByThemeId(id);
    }

    @Override
    public List<Card> getCardsByFolderId(Long id) {
        return cardRepository.findByFolderId(id);
    }

    @Override
    public Card getCardById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Карточка не найдена!"));
    }

}
