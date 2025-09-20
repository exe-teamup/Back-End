package com.team.exeteamup.entity;

import com.team.exeteamup.enums.AccountRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(generator = "uuid-v7")
    @GenericGenerator(name = "uuiv-v7", strategy = "com.team.exeteamup.util.UUIDv7Generator")
    private UUID uuid;
    private String mail;
    private AccountRole role;

}
