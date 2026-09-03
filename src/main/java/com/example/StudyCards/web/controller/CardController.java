package com.example.StudyCards.web.controller;

import com.example.StudyCards.service.CardService;
import com.example.StudyCards.web.dto.CardDto;
import com.example.StudyCards.web.mapper.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final CardMapper cardMapper;

    @GetMapping
    public List<CardDto> getByFolderId(@RequestParam Long folderId) {
        return cardMapper.toCardDtoList(cardService.getByFolderId(folderId));
    }

    @GetMapping("/{id}")
    public CardDto getById(@PathVariable Long id) {
        return cardMapper.toCardDto(cardService.getById(id));
    }

}
