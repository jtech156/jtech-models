package com.jtech.helper.controller;

import java.time.Instant;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.jtech.helper.logger.JtLogManager;
import com.jtech.helper.logger.JtLogger;
import com.jtech.helper.model.RestErrorModel;
import com.jtech.helper.model.RestResponseModel;

@ControllerAdvice
public abstract class AbstractBaseController {

	private static final JtLogger logger = JtLogManager.getLogger(AbstractBaseController.class);

	public <T> ResponseEntity<RestResponseModel<T>> jtResponse(T obj, HttpStatus httpStatus) {
		RestResponseModel<T> restResponseModel = new RestResponseModel<T>(obj, httpStatus.value(), null);
		return new ResponseEntity<RestResponseModel<T>>(restResponseModel, httpStatus);
	}

	public <T> ResponseEntity<RestResponseModel<T>> jtResponse(T obj) {
		return jtResponse(obj, HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public RestResponseModel<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			WebRequest request, HttpServletResponse response) {
		logger.error("Exception: ", ex);
		ArrayList<String> errorList = new ArrayList<String>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			errorList.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		RestErrorModel restErrorModel = new RestErrorModel(null, ex.getMessage(), Instant.now().toEpochMilli(),
				errorList);
		return new RestResponseModel<>(null, HttpStatus.BAD_REQUEST.value(), restErrorModel);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseBody
	public RestResponseModel<Object> handleNotFound(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		logger.error("Exception: ", ex);
		ArrayList<String> errorList = new ArrayList<String>();
		errorList.add("No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL());
		RestErrorModel restErrorModel = new RestErrorModel(null, ex.getMessage(), Instant.now().toEpochMilli(),
				errorList);
		return new RestResponseModel<>(null, HttpStatus.NOT_FOUND.value(), restErrorModel);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseBody
	public RestResponseModel<Object> handleMissingServletRequestParameterException(
			MissingServletRequestParameterException ex, WebRequest request, HttpServletResponse response) {
		logger.error("Exception: ", ex);
		ArrayList<String> errorList = new ArrayList<String>();
		errorList.add(ex.getParameterName() + " parameter is missing");
		RestErrorModel restErrorModel = new RestErrorModel(null, ex.getMessage(), Instant.now().toEpochMilli(),
				errorList);
		return new RestResponseModel<>(null, HttpStatus.BAD_REQUEST.value(), restErrorModel);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public RestResponseModel<Object> handleConstraintViolationExceptionCustom(ConstraintViolationException ex,
			WebRequest request, HttpServletResponse response) {
		logger.error("Exception: ", ex);
		ArrayList<String> errorList = new ArrayList<>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errorList.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}
		RestErrorModel restErrorModel = new RestErrorModel(null, ex.getMessage(), Instant.now().toEpochMilli(),
				errorList);
		return new RestResponseModel<>(null, HttpStatus.BAD_REQUEST.value(), restErrorModel);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseBody
	public RestResponseModel<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			WebRequest request, HttpServletResponse response) {
		logger.error("Exception: ", ex);
		ArrayList<String> errorList = new ArrayList<>();
		errorList.add(ex.getName() + " should be of type " + ex.getRequiredType().getName());
		RestErrorModel restErrorModel = new RestErrorModel(null, ex.getMessage(), Instant.now().toEpochMilli(),
				errorList);
		return new RestResponseModel<>(null, HttpStatus.BAD_REQUEST.value(), restErrorModel);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public RestResponseModel<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
			WebRequest request, HttpServletResponse response) {
		logger.error("Exception: ", ex);
		ArrayList<String> errorList = new ArrayList<>();
		errorList.add(ex.getMessage());
		RestErrorModel restErrorModel = new RestErrorModel(null, ex.getMessage(), Instant.now().toEpochMilli(),
				errorList);
		return new RestResponseModel<>(null, HttpStatus.BAD_REQUEST.value(), restErrorModel);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	public RestResponseModel<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("Exception: ", ex);
		ArrayList<String> errorList = new ArrayList<>();
		errorList.add(ex.getMethod() + " method is not supported for this request");
		RestErrorModel restErrorModel = new RestErrorModel(null, ex.getMessage(), Instant.now().toEpochMilli(),
				errorList);
		return new RestResponseModel<>(null, HttpStatus.BAD_REQUEST.value(), restErrorModel);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseBody
	public RestResponseModel<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("Exception: ", ex);
		ArrayList<String> errorList = new ArrayList<>();
		errorList.add(ex.getContentType() + " media type is not supported");
		RestErrorModel restErrorModel = new RestErrorModel(null, ex.getMessage(), Instant.now().toEpochMilli(),
				errorList);
		return new RestResponseModel<>(null, HttpStatus.BAD_REQUEST.value(), restErrorModel);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public RestResponseModel<Object> handleException(Exception ex, WebRequest request, HttpServletResponse response) {
		logger.error("Exception: ", ex);
		ArrayList<String> errorList = new ArrayList<>();
		errorList.add(ex.getMessage());
		RestErrorModel restErrorModel = new RestErrorModel(null, ex.getMessage(), Instant.now().toEpochMilli(),
				errorList);
		return new RestResponseModel<>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), restErrorModel);
	}
}