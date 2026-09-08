package com.example.StudyCards.service.impl;

import com.example.StudyCards.entity.Theme;
import com.example.StudyCards.repository.ThemeRepository;
import com.example.StudyCards.service.FileImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

@Service
@RequiredArgsConstructor
public class JsonImportService implements FileImportService, ApplicationRunner {

    private final ObjectMapper objectMapper;
    private final ThemeRepository themeRepository;

    @Value("${app.import.location}")
    private Resource importResource;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws IOException {
        try (InputStream inputStream = importResource.getInputStream()) {
            importData(inputStream);
        }
    }

    @Override
    @Transactional
    public void importData(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            importData(inputStream);
        } catch (IOException exception) {
            throw new UncheckedIOException("Не удалось импортировать JSON-файл", exception);
        }
    }

    private void importData(InputStream inputStream) throws IOException {
        Theme theme = objectMapper.readValue(inputStream, Theme.class);

        theme.getFolders().forEach(folder -> {
            folder.setTheme(theme);
            folder.getCards().forEach(card -> card.setFolder(folder));
        });

        themeRepository.save(theme);
    }
}
