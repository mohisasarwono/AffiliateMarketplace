/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.repositories;

import com.ThesisProject.models.Peripheral;
import com.ThesisProject.models.Promoter;
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
public interface PeripheralRepositories extends JpaRepository<Peripheral, Long>{
    Peripheral getByPeripheralLink(String peripheralLink);
    @Query(value ="select * from mst_peripheral where item_id =:itemId and referral_id =:referralId and status = 1", nativeQuery = true)
    Peripheral getByItemAndReferral(@Param("itemId")Long itemId, @Param("referralId")Long referralId);
    
    List<Peripheral> getAllByReferralCode(ReferralCode referralCode); 
}
