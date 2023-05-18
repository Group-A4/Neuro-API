package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name="general_info")
public class GeneralInfoEntity extends BaseEntity{
    @Column(name="quizz_time")
    @Min(value= 0, message = "Time for quiz should not be negative")
    private float quizTime;

    public GeneralInfoEntity() {
        this.quizTime = 15;
    }
}
