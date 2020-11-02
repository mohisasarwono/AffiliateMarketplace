/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.repositories;

import com.ThesisProject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author LENOVO
 */
public interface UserRepositories extends JpaRepository<User, Long> {
    User getByEmail(String email);
}
