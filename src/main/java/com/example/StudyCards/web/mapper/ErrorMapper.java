package com.example.StudyCards.web.mapper;

import com.example.StudyCards.exception.BaseException;
import com.example.StudyCards.web.dto.ErrorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ErrorMapper {

    ErrorDto toDto(BaseException exception);

}
