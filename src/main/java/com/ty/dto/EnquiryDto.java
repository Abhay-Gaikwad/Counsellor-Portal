package com.ty.dto;

import com.ty.enums.ClassMode;
import com.ty.enums.Course;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnquiryDto {

	private String name;
	
	private String email;
	
	private  Course course;
	
	private ClassMode classMode;
}
