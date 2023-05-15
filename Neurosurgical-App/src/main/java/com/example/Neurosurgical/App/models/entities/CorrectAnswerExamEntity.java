package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "correct_answers_exam")
public class CorrectAnswerExamEntity extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "id_answer", nullable = false)
    private AnswerExamEntity answer;



}
