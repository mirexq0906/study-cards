package com.example.StudyCards.service.impl;

import com.example.StudyCards.entity.Card;
import com.example.StudyCards.repository.CardRepository;
import com.example.StudyCards.service.CardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;

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
