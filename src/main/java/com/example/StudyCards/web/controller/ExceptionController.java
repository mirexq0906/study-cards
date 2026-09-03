package com.example.StudyCards.web.controller;

import com.example.StudyCards.exception.BaseException;
import com.example.StudyCards.web.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorDto> handleBaseException(BaseException e) {
//        return ResponseEntity
//                .badRequest()
//                .body();

        return null;
    }

}
