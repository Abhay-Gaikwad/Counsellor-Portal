package com.ty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ty.dto.FilterDto;
import com.ty.entity.Enquiries;
import com.ty.enums.ClassMode;
import com.ty.enums.Course;
import com.ty.service.EnquiriesService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/enquiry")
public class EnquiriesController {

	@Autowired
	private EnquiriesService enquiriesService;
	
	@PostMapping("/add")
	public ResponseEntity<?> addEnquiry(@RequestParam Integer cid,@RequestBody Enquiries enquiries) {
		return enquiriesService.addEnquiry(cid, enquiries);
	}
	
	@PatchMapping("/updatephone")
	public ResponseEntity<?> updatePhone(@RequestParam Integer eid,@RequestParam Long phone) {
		return enquiriesService.updatePhone(eid, phone);
	}
	
	@PatchMapping("/updatecourse")
	public ResponseEntity<?> updateCourse(@RequestParam Integer eid,@RequestParam Course course) {
		return enquiriesService.updateCourse(eid, course);
	}
	
	@PatchMapping("/updatemode")
	public ResponseEntity<?> updateClassMode(@RequestParam Integer eid,@RequestParam ClassMode classMode) {
		return enquiriesService.updateClassMode(eid, classMode);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteEnquiry(Integer cid, Integer eid){
		return enquiriesService.deleteEnquiry(cid, eid);
	}
	
	@GetMapping("/filter")
	public ResponseEntity<?> filter(@RequestBody FilterDto dto){
		return enquiriesService.filter(dto);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getEnquiriesByCounsellor(@RequestParam Integer cid) {
		return enquiriesService.getEnquiriesByCounsellor(cid);
	}
	
}
