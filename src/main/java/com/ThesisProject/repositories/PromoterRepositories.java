/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.repositories;

import com.ThesisProject.models.Promoter;

/**
 *
 * @author LENOVO
 */
public interface PromoterRepositories extends UserRepositories {
    Promoter findByEmailAndPassword(String email, String password);
}
