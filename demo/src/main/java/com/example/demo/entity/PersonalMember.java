package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "personal_member")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", nullable = false, length = 100, unique = true)
    private String loginId;

    @Column(nullable = false, length = 500)
    private String password;

    @Column(name = "first_name", nullable = false, length = 500)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 500)
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(length = 1)
    private String gender;  // 1: 남성, 2: 여성, 3: 기타, 4: 무응답

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 1)
    private String residence;  // 1: 기타, 2: 대한민국, 3: 미국

    @Column(name = "last_rank", length = 500)
    private String lastRank;  // 계급

    @Column(name = "service_category", length = 100)
    private String serviceCategory;  // 복무형태: USFK, KATUSA, CFC 등

    @Column(name = "service_branch", length = 100)
    private String serviceBranch;  // 소속군

    @Column(name = "service_year", columnDefinition = "TEXT")
    private String serviceYear;  // 복무년도

    @Column(name = "service_station", columnDefinition = "TEXT")
    private String serviceStation;  // 주둔지

    @Column(name = "unit_position", length = 100)
    private String unitPosition;  // 부대 및 직책

    @Column(name = "insert_date")
    private LocalDate insertDate;

    @Column(name = "approval_yn", nullable = false, length = 1)
    @Builder.Default
    private String approvalYn = "N";  // Y: 승인, N: 미승인

    @Column(name = "del_yn", nullable = false, length = 1)
    @Builder.Default
    private String delYn = "N";

    // 연관관계
    @OneToMany(mappedBy = "personalMember", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Resume> resumes = new ArrayList<>();

    @OneToMany(mappedBy = "personalMember", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Application> applications = new ArrayList<>();

    @OneToMany(mappedBy = "personalMember", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FavoriteJob> favoriteJobs = new ArrayList<>();
}
