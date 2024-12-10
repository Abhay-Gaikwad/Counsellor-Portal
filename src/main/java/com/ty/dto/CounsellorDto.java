package com.ty.dto;

import java.time.LocalDateTime;

import com.ty.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CounsellorDto {

	private Integer id;
	
	private String name;
	
	private String email;
	
	private Long phone;
	
	private LocalDateTime createTime;
	
	private Status status;
}
