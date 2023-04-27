package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@Builder
@Table(name = "professors")
public class ProfessorEntity {
    @Id
    private Long idUser;

    @Column(name="code", unique = true)
    @NotNull(message = "Code cannot be null")
    private String code;

    @Column(name="degree")
    private String degree;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "professor")
    private List<MaterialEntity> materials;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "professor")
    private List<DidacticEntity> teachings;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "professor")
    private List<QuestionQuizzEntity> questionsQuizzes;

    public ProfessorEntity(){
        this.materials = new ArrayList<>();
        this.teachings = new ArrayList<>();

        this.questionsQuizzes = new ArrayList<>();
    }
}
