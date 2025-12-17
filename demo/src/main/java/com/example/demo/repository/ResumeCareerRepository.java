package com.example.demo.repository;

import com.example.demo.entity.ResumeCareer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeCareerRepository extends JpaRepository<ResumeCareer, Long> {
    
    List<ResumeCareer> findByResumeIdAndDelYn(Long resumeId, String delYn);
}
