package com.iot7.repository;

import com.iot7.entity.BusinessUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

//브랜드 필터링할때 필요한 파일

@Repository
public interface BusinessUserRepository extends JpaRepository<BusinessUser, Long> {

    // ✅ "메가커피라는 이름의 브랜드 중에서 본점인 곳의 BUSINESS_ID 주세요!"
    @Query("SELECT b.businessId FROM BusinessUser b WHERE b.businessType = '본점' AND b.businessName = :name  ORDER BY b.businessId ASC")
    Optional<Long> findMainBusinessIdByName(@Param("name") String name);

    // ✅ 프론트에 보여줄 브랜드 목록 뽑는 쿼리
    @Query("SELECT DISTINCT b.businessName FROM BusinessUser b WHERE b.businessType = '본점'")
    List<String> findDistinctBrandNames();

}
