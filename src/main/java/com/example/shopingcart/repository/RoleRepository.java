package com.example.shopingcart.repository;

import com.example.shopingcart.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRoleName(String roleName);
}
