package com.example.StudyCards.web.mapper;

import com.example.StudyCards.entity.Theme;
import com.example.StudyCards.web.dto.ThemeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThemeMapper {

    ThemeDto toThemeDto(Theme theme);

    List<ThemeDto> toThemeDtoList(List<Theme> themes);

}
