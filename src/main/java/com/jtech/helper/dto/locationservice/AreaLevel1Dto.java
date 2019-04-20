package com.jtech.helper.dto.locationservice;

import java.util.Set;

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
public class AreaLevel1Dto {

	private Integer areaLevel1Id;

	@NotEmpty(message = "areaLevel1Name cannot be empty")
	private String areaLevel1Name;

	@NotEmpty(message = "timezone cannot be empty")
	private String timezone;

	@Valid
	private Set<AreaLevel2Dto> areaLevel2List;
}