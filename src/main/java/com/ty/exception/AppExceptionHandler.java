package com.ty.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ty.responsestructure.ResponseStructure;

@RestControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(CounsellorNotFound.class)
	public ResponseEntity<?> catchCounsellorNotFound(CounsellorNotFound message) {
		ResponseStructure<String> rs = new ResponseStructure<String>();
		rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
		rs.setMessage(message.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
	}
	
	@ExceptionHandler(EnquiryNotFound.class)
	public ResponseEntity<?> catchEnquiryNotFound(EnquiryNotFound message) {
		ResponseStructure<String> rs = new ResponseStructure<String>();
		rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
		rs.setMessage(message.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
	}
	
}
