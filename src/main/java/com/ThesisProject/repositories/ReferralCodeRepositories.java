/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.repositories;

import com.ThesisProject.models.ReferralCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LENOVO
 */
@Repository
public interface ReferralCodeRepositories extends JpaRepository<ReferralCode, Long> {
    @Query(value = "select * from mst_referral_code where promoter_id = :promoter",nativeQuery = true)
    ReferralCode findByPromoter(@Param("promoter")Long promoter);
    
    @Query(value="select * from mst_referral_code where promoter_id = :promoter and status = :status order by id",nativeQuery = true)
    List<ReferralCode> getAllByPromoterAndStatus(@Param("promoter")Long promoter, @Param("status")Byte status);
    
    @Query(value="select * from mst_referral_code where referral_by_system =:referralCode and referral_by_user=:referralCode",nativeQuery = true)
    List<ReferralCode> checkReferralCode(@Param("referralCode") String referralCode);
}
