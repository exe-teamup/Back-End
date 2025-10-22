    package com.team.exeteamup.entity;

    import com.team.exeteamup.enums.LecturerStatus;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.hibernate.annotations.GenericGenerator;

    import java.util.UUID;

@Entity
@Table(name = "lecturers")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecturer_id")
    private Long lecturerId;

    @OneToOne
    @JoinColumn(name = "account_id", unique = true)
    private Account account;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "lecturer_status",length = 20, nullable = false)
    private LecturerStatus lecturerStatus;
}
