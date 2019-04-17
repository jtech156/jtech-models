package com.jtech.helper.dto.communicationservice;

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
public class SmsListDto {

	@Valid
	@NotEmpty(message = "smsDtoList cannot be empty")
	List<SmsDto> smsDtoList;
}