package com.example.demo.service;

import com.example.demo.entity.CompanyMember;
import com.example.demo.entity.JobPosting;
import com.example.demo.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;

    // 공개 채용공고 목록 (마감일 지나지 않은 것만)
    public List<JobPosting> findActiveJobPostings() {
        return jobPostingRepository.findActiveJobPostings(LocalDate.now());
    }

    // 채용공고 상세 조회
    public Optional<JobPosting> findById(Long id) {
        return jobPostingRepository.findById(id)
                .filter(j -> "N".equals(j.getDelYn()));
    }

    // 기업회원별 채용공고 목록
    public List<JobPosting> findByCompanyMember(CompanyMember companyMember) {
        return jobPostingRepository.findByCompanyMemberAndDelYnOrderByIdDesc(companyMember, "N");
    }

    // 기업회원 ID로 채용공고 목록
    public List<JobPosting> findByCompanyMemberId(Long companyMemberId) {
        return jobPostingRepository.findByCompanyMemberIdAndDelYnOrderByIdDesc(companyMemberId, "N");
    }

    // 키워드 검색
    public List<JobPosting> search(String keyword) {
        return jobPostingRepository.searchByKeyword(keyword, LocalDate.now());
    }

    // 채용공고 등록
    @Transactional
    public JobPosting save(JobPosting jobPosting) {
        jobPosting.setDelYn("N");
        return jobPostingRepository.save(jobPosting);
    }

    // 채용공고 임시저장
    @Transactional
    public JobPosting saveTemp(JobPosting jobPosting) {
        jobPosting.setPostingYn("2");  // 임시저장
        jobPosting.setDelYn("N");
        return jobPostingRepository.save(jobPosting);
    }

    // 채용공고 게시
    @Transactional
    public JobPosting publish(JobPosting jobPosting) {
        jobPosting.setPostingYn("1");  // 공고완료
        jobPosting.setDelYn("N");
        return jobPostingRepository.save(jobPosting);
    }

    // 채용공고 수정
    @Transactional
    public JobPosting update(JobPosting jobPosting) {
        return jobPostingRepository.save(jobPosting);
    }

    // 채용공고 삭제 (soft delete)
    @Transactional
    public void delete(Long id) {
        jobPostingRepository.findById(id).ifPresent(job -> {
            job.setDelYn("Y");
        });
    }

    // 관리자용: 전체 채용공고 목록
    public List<JobPosting> findAll() {
        return jobPostingRepository.findByDelYnOrderByIdDesc("N");
    }

    // 통계: 월별 채용공고 수
    public List<Object[]> getMonthlyStats(int year) {
        return jobPostingRepository.countByMonth(year);
    }

    // 통계: 회사별 채용공고 수
    public List<Object[]> getCompanyStats() {
        return jobPostingRepository.countByCompany();
    }
}
