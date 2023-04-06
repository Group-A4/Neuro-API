package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "administrators")
@Builder
public class Admin {
    @Id
    @Column(name = "id_user")
    private Long id_users;

}
