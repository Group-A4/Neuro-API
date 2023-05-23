package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "correct_answers_quizz")
public class CorrectAnswerQuizzEntity extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "id_answer", nullable = false)
    private AnswerQuizzEntity answer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CorrectAnswerQuizzEntity that = (CorrectAnswerQuizzEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
