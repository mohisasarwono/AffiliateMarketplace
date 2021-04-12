/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.repositories;

import com.ThesisProject.models.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author LENOVO
 */
public interface ItemRepositories extends JpaRepository<Item, Long> {
    
    @Query(value = "select * from mst_item where store_id = :storeId and status = 1 order by id",nativeQuery = true)
    List<Item> getByStoreAndStatus(@Param("storeId")Long storeId);
    
    @Query(value = "select * from mst_item order by id limit 100",nativeQuery = true)
    List<Item> getAll();
    
}
