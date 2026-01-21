package com.LandSV.landSV.repository;

import com.LandSV.landSV.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    @Query("SELECT v FROM VerificationCode v WHERE LOWER(v.email) = LOWER(:email) AND v.code = :code")
    Optional<VerificationCode> findByEmailAndCodeIgnoreCase(@Param("email") String email, @Param("code") String code);
}
