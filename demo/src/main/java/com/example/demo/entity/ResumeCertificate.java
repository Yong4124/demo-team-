package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resume_certificate")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(name = "certificate_name", length = 500)
    private String certificateName;  // 자격/기술명

    @Column(name = "obtain_date", length = 500)
    private String obtainDate;  // 취득년월

    @Column(length = 500)
    private String agency;  // 발급기관

    @Column(name = "certificate_num", length = 500)
    private String certificateNum;  // 자격증 번호

    @Column(name = "del_yn", nullable = false, length = 1)
    @Builder.Default
    private String delYn = "N";
}
