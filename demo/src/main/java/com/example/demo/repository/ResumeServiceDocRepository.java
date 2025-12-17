package com.example.demo.repository;

import com.example.demo.entity.ResumeServiceDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeServiceDocRepository extends JpaRepository<ResumeServiceDoc, Long> {
    
    List<ResumeServiceDoc> findByResumeIdAndDelYn(Long resumeId, String delYn);
}
