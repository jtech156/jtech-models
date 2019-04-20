package com.jtech.helper.dto.locationservice;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaLevel2Dto {

	private Integer areaLevel2Id;

	@NotEmpty(message = "areaLevel2Name cannot be empty")
	private String areaLevel2Name;

	@NotEmpty(message = "timezone cannot be empty")
	private String timezone;
}