package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "admin")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", nullable = false, length = 100)
    private String loginId;

    @Column(nullable = false, length = 500)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 500)
    private String department;

    @Column(nullable = false, length = 100)
    private String phone;

    @Column(nullable = false, length = 1)
    private String auth;  // 1: 슈퍼관리자, 2: 대한상공회의소, 3: 한미동맹재단

    @Column(name = "insert_date")
    private LocalDate insertDate;

    @Column(name = "del_yn", nullable = false, length = 1)
    @Builder.Default
    private String delYn = "N";
}
