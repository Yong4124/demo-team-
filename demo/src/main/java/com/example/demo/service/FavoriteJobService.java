package com.example.demo.service;

import com.example.demo.entity.FavoriteJob;
import com.example.demo.entity.JobPosting;
import com.example.demo.entity.PersonalMember;
import com.example.demo.repository.FavoriteJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteJobService {

    private final FavoriteJobRepository favoriteJobRepository;

    // 개인회원별 관심 채용공고 목록
    public List<FavoriteJob> findByPersonalMemberId(Long personalMemberId) {
        return favoriteJobRepository.findByPersonalMemberIdAndDelYnOrderByIdDesc(personalMemberId, "N");
    }

    // 관심 등록 여부 확인
    public boolean isFavorite(Long personalMemberId, Long jobPostingId) {
        return favoriteJobRepository.existsByPersonalMemberIdAndJobPostingIdAndDelYn(personalMemberId, jobPostingId, "N");
    }

    // 관심 등록
    @Transactional
    public FavoriteJob addFavorite(PersonalMember personalMember, JobPosting jobPosting) {
        // 이미 등록되어 있는지 확인
        Optional<FavoriteJob> existing = favoriteJobRepository
                .findByPersonalMemberIdAndJobPostingIdAndDelYn(personalMember.getId(), jobPosting.getId(), "N");
        
        if (existing.isPresent()) {
            return existing.get();
        }

        FavoriteJob favoriteJob = FavoriteJob.builder()
                .personalMember(personalMember)
                .jobPosting(jobPosting)
                .delYn("N")
                .build();
        return favoriteJobRepository.save(favoriteJob);
    }

    // 관심 삭제
    @Transactional
    public void removeFavorite(Long personalMemberId, Long jobPostingId) {
        favoriteJobRepository.findByPersonalMemberIdAndJobPostingIdAndDelYn(personalMemberId, jobPostingId, "N")
                .ifPresent(favorite -> {
                    favorite.setDelYn("Y");
                });
    }
}
