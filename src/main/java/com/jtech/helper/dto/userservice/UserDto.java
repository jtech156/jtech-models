package com.jtech.helper.dto.userservice;

import java.util.Date;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Integer id;

	@NotEmpty(message = "Name cannot be blank")
	private String name;

	@NotEmpty(message = "Mobile cannot be blank")
	private String mobile;

	@NotEmpty(message = "Email cannot be blank")
	private String email;

	@NotNull(message = "Date of birth cannot be blank")
	private Date dob;
	
	private String otp;

	private boolean deleted;

	private Long createdAt;

	private Long updatedAt;

	@Valid
	@NotEmpty(message = "Address List cannot be empty")
	private Set<UserAddressDto> userAddressList;
}