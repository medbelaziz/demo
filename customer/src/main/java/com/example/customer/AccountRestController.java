package com.example.customer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.customer.security.AccountService;
import com.example.customer.security.entity.AppRole;
import com.example.customer.security.entity.AppUser;
import com.example.customer.security.model.RoleUserForm;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1")
public class AccountRestController {

	private AccountService accountService;

	@PostMapping("/users")
	void addUser(@RequestBody AppUser user) {
		accountService.addUser(user);
	}

	@PostMapping("/roles")
	void addRole(@RequestBody AppRole role) {
		accountService.addRole(role);
	}

	@PostMapping("/addRoleToUser")
	void addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
		accountService.addRoleToUser(roleUserForm);
	}
}
