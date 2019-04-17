package com.jtech.helper.dto.userservice;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserListDto {

	@Valid
	@NotEmpty(message = "userDtoList cannot be empty")
	List<UserDto> userDtoList;
}