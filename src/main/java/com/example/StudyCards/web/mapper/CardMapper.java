package com.example.StudyCards.web.mapper;

import com.example.StudyCards.entity.Card;
import com.example.StudyCards.web.dto.CardDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDto toCardDto(Card card);

    List<CardDto> toCardDtoList(List<Card> cards);

}
