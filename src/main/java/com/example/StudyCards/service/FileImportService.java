package com.example.StudyCards.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileImportService {

    void importData(MultipartFile file);

}
