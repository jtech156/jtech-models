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
public class CountryDto {

	private Integer countryId;

	@NotEmpty(message = "countryName cannot be empty")
	private String countryName;

	@NotEmpty(message = "countryCode cannot be empty")
	private String countryCode;

	@NotEmpty(message = "countryIsoCode cannot be empty")
	private String countryIsoCode;

	@NotEmpty(message = "currencyCode cannot be empty")
	private String currencyCode;

	@NotEmpty(message = "currencySymbolCode cannot be empty")
	private String currencySymbolCode;

	@NotEmpty(message = "timezone cannot be empty")
	private String timezone;

	@NotEmpty(message = "phoneRegex cannot be empty")
	private String phoneRegex;

	@NotEmpty(message = "pincodeRegex cannot be empty")
	private String pincodeRegex;

	@Valid
	private Set<AreaLevel1Dto> areaLevel1List;
}