package com.jtech.helper.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestResponseModel<T extends Object> {

	private T result;

	private int status;

	private RestErrorModel error;
}