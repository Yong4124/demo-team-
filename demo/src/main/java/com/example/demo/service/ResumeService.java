package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final ResumeCareerRepository resumeCareerRepository;
    private final ResumeCertificateRepository resumeCertificateRepository;
    private final ResumePhotoRepository resumePhotoRepository;
    private final ResumeServiceDocRepository resumeServiceDocRepository;
    private final ResumeAttachmentRepository resumeAttachmentRepository;

    // 이력서 조회
    public Optional<Resume> findById(Long id) {
        return resumeRepository.findById(id)
                .filter(r -> "N".equals(r.getDelYn()));
    }

    // 개인회원별 이력서 목록
    public List<Resume> findByPersonalMemberId(Long personalMemberId) {
        return resumeRepository.findByPersonalMemberIdAndDelYnOrderByIdDesc(personalMemberId, "N");
    }

    // 최신 이력서 조회 (불러오기용)
    public Optional<Resume> findLatestByPersonalMemberId(Long personalMemberId) {
        return resumeRepository.findFirstByPersonalMemberIdAndDelYnOrderByIdDesc(personalMemberId, "N");
    }

    // 이력서 저장
    @Transactional
    public Resume save(Resume resume) {
        resume.setInsertDate(LocalDate.now());
        resume.setDelYn("N");
        return resumeRepository.save(resume);
    }

    // 이력서 임시저장
    @Transactional
    public Resume saveTemp(Resume resume) {
        resume.setApplyYn("2");  // 임시저장
        resume.setInsertDate(LocalDate.now());
        resume.setDelYn("N");
        return resumeRepository.save(resume);
    }

    // 이력서 제출
    @Transactional
    public Resume submit(Resume resume) {
        resume.setApplyYn("1");  // 지원완료
        resume.setInsertDate(LocalDate.now());
        resume.setDelYn("N");
        return resumeRepository.save(resume);
    }

    // 이력서 수정
    @Transactional
    public Resume update(Resume resume) {
        return resumeRepository.save(resume);
    }

    // 이력서 삭제 (soft delete)
    @Transactional
    public void delete(Long id) {
        resumeRepository.findById(id).ifPresent(resume -> {
            resume.setDelYn("Y");
        });
    }

    // 경력 조회
    public List<ResumeCareer> findCareersByResumeId(Long resumeId) {
        return resumeCareerRepository.findByResumeIdAndDelYn(resumeId, "N");
    }

    // 경력 저장
    @Transactional
    public ResumeCareer saveCareer(ResumeCareer career) {
        career.setDelYn("N");
        return resumeCareerRepository.save(career);
    }

    // 자격증 조회
    public List<ResumeCertificate> findCertificatesByResumeId(Long resumeId) {
        return resumeCertificateRepository.findByResumeIdAndDelYn(resumeId, "N");
    }

    // 자격증 저장
    @Transactional
    public ResumeCertificate saveCertificate(ResumeCertificate certificate) {
        certificate.setDelYn("N");
        return resumeCertificateRepository.save(certificate);
    }

    // 사진 저장
    @Transactional
    public ResumePhoto savePhoto(ResumePhoto photo) {
        photo.setDelYn("N");
        return resumePhotoRepository.save(photo);
    }

    // 복무증명서 저장
    @Transactional
    public ResumeServiceDoc saveServiceDoc(ResumeServiceDoc serviceDoc) {
        serviceDoc.setDelYn("N");
        return resumeServiceDocRepository.save(serviceDoc);
    }

    // 첨부파일 저장
    @Transactional
    public ResumeAttachment saveAttachment(ResumeAttachment attachment) {
        attachment.setDelYn("N");
        return resumeAttachmentRepository.save(attachment);
    }
}
