package com.example.StudyCards.service;

import com.example.StudyCards.entity.Card;

import java.util.List;

public interface CardService {

    List<Card> getByFolderId(Long folderId);

    Card getById(Long id);

}
