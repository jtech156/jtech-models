package com.jtech.helper.dto.locationservice;

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
public class AreaLevel3Dto {

	private Integer areaLevel3Id;

	@NotEmpty(message = "areaLevel3Name cannot be empty")
	private String areaLevel3Name;

	private byte[] geometryPoint;

	@NotNull(message = "latitude cannot be null")
	private Double latitude;

	@NotNull(message = "longitude cannot be null")
	private Double longitude;

	private Integer areaLevel2Id;
}