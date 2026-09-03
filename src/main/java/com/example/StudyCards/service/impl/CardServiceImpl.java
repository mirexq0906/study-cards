package com.example.StudyCards.service.impl;

import com.example.StudyCards.entity.Card;
import com.example.StudyCards.exception.EntityNotFoundException;
import com.example.StudyCards.repository.CardRepository;
import com.example.StudyCards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    public List<Card> getByFolderId(Long folderId) {
        return cardRepository.findByFolderId(folderId);
    }

    @Override
    public Card getById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Карточка не найдена!"));
    }

}
