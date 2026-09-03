package com.example.StudyCards.web.mapper;

import com.example.StudyCards.entity.Folder;
import com.example.StudyCards.web.dto.FolderDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FolderMapper {

    FolderDto toFolderDto(Folder folder);

    List<FolderDto> toFolderDtoList(List<Folder> folders);

}
