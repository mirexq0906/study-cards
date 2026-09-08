package com.example.StudyCards;

import com.example.StudyCards.repository.CardRepository;
import com.example.StudyCards.repository.FolderRepository;
import com.example.StudyCards.repository.ThemeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StudyCardsApplicationTests {

	@Autowired
	private ThemeRepository themeRepository;

	@Autowired
	private FolderRepository folderRepository;

	@Autowired
	private CardRepository cardRepository;

	@Test
	void importsSkillboxCardsOnStartup() {
		assertThat(themeRepository.count()).isEqualTo(1);
		assertThat(folderRepository.count()).isEqualTo(5);
		assertThat(cardRepository.count()).isEqualTo(620);
	}

}
