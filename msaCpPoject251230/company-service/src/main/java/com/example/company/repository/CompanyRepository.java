package com.example.company.repository;

import com.example.company.model.ApprovalYn;
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
public interface CompanyRepository extends JpaRepository<Company, Long> {

    // ID로 조회 (삭제되지 않은 회원)
    Optional<Company> findByLoginIdAndDelYn(String loginId, DelYn delYn);

    // ID 중복 체크
    boolean existsByLoginIdAndDelYn(String loginId, DelYn delYn);

    // 이메일 중복 체크
    boolean existsByEmailAndDelYn(String email, DelYn delYn);

    // 사업자등록번호 중복 체크
    boolean existsByBizNoAndDelYn(String bizNo, DelYn delYn);

    // 삭제되지 않은 전체 기업 목록 (최신순)
    List<Company> findAllByDelYnOrderBySeqNoM200Desc(DelYn delYn);

    // 승인된 기업 목록
    List<Company> findAllByApprovalYnAndDelYnOrderBySeqNoM200Desc(ApprovalYn approvalYn, DelYn delYn);

    // 검색 (회사명 또는 담당자명)
    @Query("SELECT c FROM Company c " +
            "WHERE c.delYn = :delYn AND (" +
            "c.companyNmKo LIKE CONCAT('%', :keyword, '%') OR " +
            "c.companyNmEn LIKE CONCAT('%', :keyword, '%') OR " +
            "c.managerNm LIKE CONCAT('%', :keyword, '%'))")
    List<Company> searchByKeyword(@Param("keyword") String keyword,
                                  @Param("delYn") DelYn delYn);

    // Admin 통계용: 기간별 가입 수
    @Query("SELECT FUNCTION('DATE_FORMAT', c.insertDate, '%Y-%m') as month, COUNT(c) as cnt " +
            "FROM Company c " +
            "WHERE c.insertDate BETWEEN :from AND :to " +
            "AND c.delYn = com.example.company.model.DelYn.N " +
            "GROUP BY FUNCTION('DATE_FORMAT', c.insertDate, '%Y-%m') " +
            "ORDER BY month")
    List<Object[]> countByInsertDateBetween(@Param("from") LocalDate from,
                                            @Param("to") LocalDate to);

    // 월별 가입 통계 인터페이스 (※ 현재 코드에서는 미사용)
    interface MonthlyJoinCount {
        String getMonth();
        Long getCnt();
    }
}
