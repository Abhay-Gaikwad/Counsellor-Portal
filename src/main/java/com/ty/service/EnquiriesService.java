package com.ty.service;


import org.springframework.http.ResponseEntity;

import com.ty.dto.FilterDto;
import com.ty.entity.Enquiries;
import com.ty.enums.ClassMode;
import com.ty.enums.Course;
import com.ty.enums.Status;

public interface EnquiriesService {

    ResponseEntity<?> addEnquiry(Integer cid,Enquiries enquiry);
	
	ResponseEntity<?> updateCourse(Integer eid,Course course);
	
	ResponseEntity<?> updatePhone(Integer eid,Long phone);
	
	ResponseEntity<?> updateClassMode(Integer eid,ClassMode classMode);
	
	ResponseEntity<?> deleteEnquiry(Integer cid,Integer eid);
	
	ResponseEntity<?> filter(FilterDto dto);
	
	ResponseEntity<?> getEnquiriesByCounsellor(Integer cid);
}
