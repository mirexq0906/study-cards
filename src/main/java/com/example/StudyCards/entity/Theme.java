package com.example.StudyCards.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Table(name = "themes")
@Entity
@Data
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "theme", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Folder> folders;

}
