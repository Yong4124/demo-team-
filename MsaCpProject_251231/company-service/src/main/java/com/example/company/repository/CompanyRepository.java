package com.example.company.repository;

import com.example.company.model.Company;
import com.example.company.model.DelYn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    // ID로 회원 찾기 (로그인용)
    Optional<Company> findByLoginId(String loginId);

    // ID 중복 체크
    boolean existsByLoginId(String loginId);

    // 이메일로 회원 찾기
    Optional<Company> findByEmail(String email);

    // 이메일 중복 체크
    boolean existsByEmail(String email);

    // 사업자등록번호 중복 체크
    boolean existsByBusinessRegistNum(String businessRegistNum);

    // admin - 삭제되지 않은 기업회원 목록 (최신순)
    List<Company> findAllByDelYnOrderBySeqNoM200Desc(DelYn delYn);

    // admin 통계 - 월별 가입수(년/월)
    interface MonthlyJoinCount {
        Integer getY();
        Integer getM();
        Long getCnt();
    }

    @Query("""
        select year(c.insertDate) as y,
               month(c.insertDate) as m,
               count(c) as cnt
        from Company c
        where c.delYn = :delYn
          and c.insertDate between :from and :to
        group by year(c.insertDate), month(c.insertDate)
        order by year(c.insertDate), month(c.insertDate)
    """)
    List<MonthlyJoinCount> countMonthlyJoins(
            @Param("delYn") DelYn delYn,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    // 총 가입수
    @Query("""
        select count(c)
        from Company c
        where c.delYn = :delYn
          and c.insertDate between :from and :to
    """)
    long countTotalJoins(
            @Param("delYn") DelYn delYn,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );
}
