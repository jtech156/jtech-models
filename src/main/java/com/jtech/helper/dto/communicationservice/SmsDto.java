package com.jtech.helper.dto.communicationservice;

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
public class SmsDto {

	@NotNull(message = "smsData cannot be null")
	private Object smsData;

	@NotEmpty(message = "smsType cannot be empty")
	private String smsType;

	@NotEmpty(message = "mobile cannot be empty")
	private String mobile;

	@NotNull(message = "templateId cannot be null")
	private Integer templateId;
}