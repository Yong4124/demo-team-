package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resume_service_doc")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeServiceDoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(name = "attach_file_name", nullable = false, length = 500)
    private String attachFileName;

    @Column(name = "del_yn", nullable = false, length = 1)
    @Builder.Default
    private String delYn = "N";
}
