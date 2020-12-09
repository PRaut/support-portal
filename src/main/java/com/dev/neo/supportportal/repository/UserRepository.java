package com.dev.neo.supportportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.neo.supportportal.domain.User;

public interface UserRepository extends JpaRepository<User, Long>
{
	User findUserByUserName(String username);
	
	User findUserByEmail(String email);
}
