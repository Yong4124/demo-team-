package com.example.demo.repository;

import com.example.demo.entity.ResumeCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeCertificateRepository extends JpaRepository<ResumeCertificate, Long> {
    
    List<ResumeCertificate> findByResumeIdAndDelYn(Long resumeId, String delYn);
}
