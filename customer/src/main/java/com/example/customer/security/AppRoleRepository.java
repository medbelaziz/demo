package com.example.customer.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.customer.security.entity.AppRole;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

	AppRole findByRoleName(String roleName);

}
