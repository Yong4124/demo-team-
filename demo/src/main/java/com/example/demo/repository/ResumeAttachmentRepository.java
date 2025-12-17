package com.example.demo.repository;

import com.example.demo.entity.ResumeAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeAttachmentRepository extends JpaRepository<ResumeAttachment, Long> {
    
    List<ResumeAttachment> findByResumeIdAndDelYn(Long resumeId, String delYn);
}
