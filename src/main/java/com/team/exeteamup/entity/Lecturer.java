package com.team.exeteamup.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "lecturer")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lecturerId;

    @Column(name = "lecturer_name", nullable = false)
    private String lecturerName;

    @Column(name = "department")
    private String department;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
