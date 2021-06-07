/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.repositories;

import com.ThesisProject.models.Promoter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LENOVO
 */
@Repository
public interface PromoterRepositories extends JpaRepository<Promoter, Long>{
    Promoter findByIdAndPassword(Long id, String password);
    Promoter findByEmailAndPassword(String email, String password);
    Promoter getByEmail(String email);
    
    @Query(value = "select * from mst_promoter as p " +
    "join mst_referral_code as r on r.promoter_id = p.id " +
    "join mst_peripheral as ph on ph.referral_id = r.id " +
    "where ph.id = :peripheralId", nativeQuery = true)
    Promoter findByPeripheral(@Param("peripheralId")Long peripheralId);
}
