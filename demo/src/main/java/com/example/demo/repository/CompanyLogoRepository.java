package com.example.demo.repository;

import com.example.demo.entity.CompanyLogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyLogoRepository extends JpaRepository<CompanyLogo, Long> {
    
    List<CompanyLogo> findByCompanyMemberIdAndDelYn(Long companyMemberId, String delYn);
    
    Optional<CompanyLogo> findFirstByCompanyMemberIdAndDelYn(Long companyMemberId, String delYn);
}
