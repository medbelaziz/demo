package com.example.customer.security;

import org.springframework.stereotype.Service;

import com.example.customer.security.entity.AppRole;
import com.example.customer.security.entity.AppUser;
import com.example.customer.security.model.RoleUserForm;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class AccountService {

	private AppRoleRepository appRoleRepository;
	private AppUserRepository appUserRepository;

	public AccountService(AppRoleRepository appRoleRepository, AppUserRepository appUserRepository) {
		this.appRoleRepository = appRoleRepository;
		this.appUserRepository = appUserRepository;
	}

	public void addUser(AppUser user) {
		appUserRepository.save(user);
	}

	public void addRole(AppRole role) {
		appRoleRepository.save(role);
	}

	public void addRoleToUser(RoleUserForm form) {
		AppUser user = appUserRepository.findByUserName(form.getUserName());
		AppRole role = appRoleRepository.findByRoleName(form.getRoleName());
		user.getRoleList().add(role);
		appUserRepository.save(user);

	}
}
