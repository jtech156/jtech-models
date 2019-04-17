package com.jtech.helper.dto.userservice;

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
public class UserAddressDto {

	private Integer id;

	private Integer userId;

	@NotEmpty(message = "Address cannot be blank")
	private String address;

	@NotNull(message = "Location cannot be blank")
	private Integer locationId;

	@NotNull(message = "AreaLevel2 cannot be blank")
	private Integer areaLevel2Id;

	private boolean deleted;

	private Long createdAt;

	private Long updatedAt;
}