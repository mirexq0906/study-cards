package com.example.StudyCards.web.controller;

import com.example.StudyCards.exception.BaseException;
import com.example.StudyCards.web.dto.ErrorDto;
import com.example.StudyCards.web.mapper.ErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    private final ErrorMapper errorMapper;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorDto> handleBaseException(BaseException e) {
        return ResponseEntity
                .badRequest()
                .body(errorMapper.toDto(e));
    }

}
