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
import org.springframework.stereotype.Repository;

/**
 *
 * @author LENOVO
 */
@Repository
public interface StoreRepositories extends JpaRepository<Store, Long> {
    Store findByEmailAndPassword(String email, String password);
    
    @Query(value="select id from mst_store limit :limitData",nativeQuery = true)
    List<Long> getLimitDataForMarketplace(@Param("limitData") Long limitData);
    
    @Query(value="select id, name,store_description, phone_number, photo_profile_url from mst_store where id in (:storeIds) and status = 1 order by id", nativeQuery = true)
    List<Object[]> getAllDataForMarketplace(@Param("storeIds") List<Long> storeIds);
    
    @Query(value="select id from mst_store where name ilike(concat('%',:name,'%')) and status = 1 order by id ", nativeQuery = true)
    List<Long> getStoreByName(@Param("name")String name);
    
    @Query(value="select id from mst_store  where type = :type and status = 1", nativeQuery = true )
    List<Long> getStoreByType(@Param("type")Integer type);
    
    @Query(value="select s.id from mst_store as s " +
    "join mst_item as i on s.id = i.store_id " +
    "join mst_peripheral as p on p.item_id = i.id " +
    "join trx_commission as c on c.peripheral_id = p.id " +
    "where c.total_transaction >= :totalTranscation and s.status = 1 order by s.id ", nativeQuery = true)
    List<Long> getStoreByTotalTransaction(@Param("totalTranscation")Integer totalTransaction);
    
    
    
}
