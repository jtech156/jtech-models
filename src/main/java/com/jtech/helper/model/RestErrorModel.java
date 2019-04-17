package com.jtech.helper.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestErrorModel {

	private String developerCode;

	private String message;

	private Long time;

	private List<String> errors;
}