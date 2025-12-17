package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company_logo")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyLogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_member_id", nullable = false)
    private CompanyMember companyMember;

    @Column(name = "attach_file_name", nullable = false, length = 500)
    private String attachFileName;

    @Column(name = "del_yn", nullable = false, length = 1)
    @Builder.Default
    private String delYn = "N";
}
