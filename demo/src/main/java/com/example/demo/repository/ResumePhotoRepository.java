package com.example.demo.repository;

import com.example.demo.entity.ResumePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumePhotoRepository extends JpaRepository<ResumePhoto, Long> {
    
    List<ResumePhoto> findByResumeIdAndDelYn(Long resumeId, String delYn);
    
    Optional<ResumePhoto> findFirstByResumeIdAndDelYn(Long resumeId, String delYn);
}
