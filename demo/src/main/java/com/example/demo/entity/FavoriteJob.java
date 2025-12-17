package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorite_job")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_member_id", nullable = false)
    private PersonalMember personalMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    @Column(name = "del_yn", nullable = false, length = 1)
    @Builder.Default
    private String delYn = "N";
}
