package com.mssql.crud.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mssql.crud.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	// List<User> fiandAll(final Pageable pagination);

}
