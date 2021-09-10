package com.mssql.crud.user.dto;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mssql.crud.user.model.User;

@ExtendWith(MockitoExtension.class)
class UsuarioDtoTest {

	@Test
	void testFrom() {
		var instance = UserDto.from(new User());
		assertNotNull(instance);
	}

	@Test
	void testFromNull() {
		var instance = UserDto.from(null);
		assertNull(instance);
	}

}
