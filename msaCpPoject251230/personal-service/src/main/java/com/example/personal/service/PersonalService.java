package com.example.personal.service;

import com.example.personal.model.DelYn;
import com.example.personal.model.Personal;
import com.example.personal.repository.PersonalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonalService {

    private final PersonalRepository personalRepository;

    // 회원가입
    @Transactional
    public Personal register(Personal personal){
        if (personalRepository.existsByLoginId(personal.getLoginId())) {
            throw new IllegalArgumentException("이미 존재하는 ID입니다.");
        }
        if (personalRepository.existsByEmail(personal.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }
        return personalRepository.save(personal);
    }

    public boolean checkIdDuplication(String loginId) {
        return personalRepository.existsByLoginId(loginId);
    }

    public boolean checkEmailDuplication(String email) {
        return personalRepository.existsByEmail(email);
    }

    public Optional<Personal> findById(String loginId) {
        return personalRepository.findByLoginId(loginId);
    }

    public Iterable<Personal> findAll() {
        return personalRepository.findAll();
    }

    public List<Personal> adminList() {
        return personalRepository.findAllByDelYnOrderBySeqNoM100Desc(DelYn.N);
    }

    public Optional<Personal> findBySeq(Integer seq) {
        return personalRepository.findById(seq);
    }

    // ✅ 기간별 (총 가입수 + 년-월별 가입수)
    public Map<String, Object> getJoinStats(LocalDate from, LocalDate to) {

        // JPQL로 바꾼 Repository 기준: DelYn.N을 파라미터로 넘김
        long total = personalRepository.countTotalJoins(DelYn.N, from, to);

        List<Map<String, Object>> rows = personalRepository.countMonthlyJoins(DelYn.N, from, to).stream()
                .map(r -> Map.<String, Object>of(
                        // y,m을 "YYYY-MM" 형태로 만들어서 내려줌
                        "ym", String.format("%04d-%02d", r.getY(), r.getM()),
                        "count", r.getCnt()
                ))
                .toList();

        return Map.of(
                "total", total,
                "rows", rows
        );
    }
}
