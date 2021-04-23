/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.repositories;

import com.ThesisProject.models.Store;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author LENOVO
 */
public interface StoreRepositories extends JpaRepository<Store, Long> {
    Store findByEmailAndPassword(String email, String password);
    
    @Query(value="select id from mst_store limit :limitData",nativeQuery = true)
    List<Long> getLimitDataForMarketplace(@Param("limitData") Long limitData);
    
    @Query(value="select id, name,store_description, phone_number, photo_profile_url from mst_store where id in (:storeIds) and status = 1 order by id", nativeQuery = true)
    List<Object[]> getAllDataForMarketplace(@Param("storeIds") List<Long> storeIds);
    
}
