package com.mssql.crud.user.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.mssql.crud.user.dto.UserDto;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;

	public static User from(final UserDto source) {

		if (source == null) {
			return null;
		}

		final var instance = new User();
		BeanUtils.copyProperties(source, instance);
		return instance;
	}

}
