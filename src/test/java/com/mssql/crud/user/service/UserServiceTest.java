package com.mssql.crud.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.mssql.crud.user.dto.UserDto;
import com.mssql.crud.user.exception.NotFoundException;
import com.mssql.crud.user.model.User;
import com.mssql.crud.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	UserService userService;

	@Mock
	UserRepository userRepository;

	User user;
	UserDto userDto;
	Page<User> userPage;

	protected static final String NOT_FOUND_MESSAGE = "No records found for id: ";

	@BeforeEach
	void setUp() {
		user = new User();
		user.setId(100);
		userDto = new UserDto();
		userDto.setId(user.getId());
		userPage = new PageImpl<User>(List.of(user), PageRequest.of(6, 50), 0);
		userPage.and(user);
	}

	@Test
	void testList() {
		when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);
		var instance = userService.list(PageRequest.of(0, 1)).join();
		assertEquals(ArrayList.class, instance.getClass());
		assertEquals(1, instance.size());
	}

	@Test
	void testGet() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		var instance = userService.get(userDto.getId()).join();
		assertEquals(UserDto.class, instance.getClass());
		assertEquals(userDto, instance);
	}

	@Test
	void testGetNotFoundException() {
		when(userRepository.findById(anyLong())).thenThrow(new NotFoundException("Any error message"));
		assertThrows(NotFoundException.class, () -> userService.get(userDto.getId()));
	}

	@Test
	void testStore() {
		when(userRepository.save(any(User.class))).thenReturn(user);
		var instance = userService.store(userDto).join();
		assertEquals(UserDto.class, instance.getClass());
		assertEquals(userDto, instance);
	}

	@Test
	void testUpdate() {

		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);
		var instance = userService.update(userDto.getId(), userDto).join();
		assertEquals(UserDto.class, instance.getClass());
		assertEquals(userDto, instance);
	}

	@Test
	void testUpdateNotFoundException() {
		when(userRepository.findById(anyLong())).thenThrow(new NotFoundException("Any error message"));
		assertThrows(NotFoundException.class, () -> {
			userService.update(userDto.getId(), userDto);
		});
	}

	@Test
	void testDelete() {
		doNothing().when(userRepository).deleteById(user.getId());
		var instance = userService.delete(user.getId()).join();
		assertNull(instance);
	}

}
