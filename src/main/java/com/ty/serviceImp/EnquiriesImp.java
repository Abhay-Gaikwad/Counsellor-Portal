package com.ty.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ty.dto.EnquiryDto;
import com.ty.dto.FilterDto;
import com.ty.entity.Counsellor;
import com.ty.entity.Enquiries;
import com.ty.enums.ClassMode;
import com.ty.enums.Course;
import com.ty.enums.Status;
import com.ty.exception.CounsellorNotFound;
import com.ty.exception.EnquiryNotFound;
import com.ty.repository.CounsellorRepository;
import com.ty.repository.EnquiriesRepository;
import com.ty.responsestructure.ResponseStructure;
import com.ty.service.EnquiriesService;

@Service
public class EnquiriesImp implements EnquiriesService{
	
	@Autowired
	private EnquiriesRepository enquiriesRepository;

	@Autowired
	private CounsellorRepository counsellorRepository;
	
	@Override
	public ResponseEntity<?> addEnquiry(Integer cid, Enquiries enquiry) {
		Counsellor counsellor = counsellorRepository.findById(cid).orElseThrow(()-> new CounsellorNotFound("Counsellor not found Or register"));
		Optional<Enquiries> option = enquiriesRepository.findByEmail(enquiry.getEmail());	
		if (option.isPresent()) {
			ResponseStructure<String> rs = new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
			rs.setMessage("Enquiry already taken with this email");
			return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
		} else {
			enquiry.setCounsellor(counsellor);
			Enquiries enquirys = enquiriesRepository.save(enquiry);
			EnquiryDto dto = new EnquiryDto();
			BeanUtils.copyProperties(enquirys, dto);
			ResponseStructure<EnquiryDto> rs = new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.CREATED.value());
			rs.setMessage("Enquiry is added");
			rs.setData(dto);
			return new ResponseEntity<ResponseStructure<EnquiryDto>>(rs,HttpStatus.OK);	
		}
	}

	@Override
	public ResponseEntity<?> updateCourse(Integer eid, Course course) {
		Enquiries enquiries = enquiriesRepository.findById(eid).orElseThrow(()-> new EnquiryNotFound("Enquiry is not added"));
		enquiries.setCourse(course);
		enquiriesRepository.save(enquiries);
		ResponseStructure<Course> rs = new ResponseStructure<Course>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Course is updated");
		rs.setData(course);
		return new ResponseEntity<ResponseStructure<Course>>(rs,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updatePhone(Integer eid, Long phone) {
		Enquiries enquiries = enquiriesRepository.findById(eid).orElseThrow(()-> new EnquiryNotFound("Enquiry is not added"));
		enquiries.setPhone(phone);
		enquiriesRepository.save(enquiries);
		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Phone is updated");
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateClassMode(Integer eid, ClassMode classMode) {
		Enquiries enquiries = enquiriesRepository.findById(eid).orElseThrow(()-> new EnquiryNotFound("Enquiry is not added"));
		enquiries.setClassMode(classMode);
		enquiriesRepository.save(enquiries);
		ResponseStructure<ClassMode> rs = new ResponseStructure<ClassMode>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("ClassMode is updated");
		rs.setData(classMode);
		return new ResponseEntity<ResponseStructure<ClassMode>>(rs,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteEnquiry(Integer cid, Integer eid) {
		Enquiries enquiries = enquiriesRepository.findById(eid).orElseThrow(()-> new EnquiryNotFound("Enquiry is not added"));
		if (enquiries.getCounsellor().getId() == cid) {
			enquiriesRepository.delete(enquiries);
			ResponseStructure<String> rs = new ResponseStructure<String>();
			rs.setStatusCode(HttpStatus.OK.value());
			rs.setMessage("Enquiry is Deleted");
			return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
		}
		ResponseStructure<String> rs = new ResponseStructure<String>();
		rs.setStatusCode(HttpStatus.NOT_FOUND.value());
		rs.setMessage("Cannot delete");
		return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> filter(FilterDto dto) {
		Enquiries enquiry = new Enquiries();
		BeanUtils.copyProperties(dto, enquiry);
		Example<Enquiries> of = Example.of(enquiry);
		List<Enquiries> all = enquiriesRepository.findAll(of);
		ResponseStructure<List<Enquiries>> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Filtered Successfully");
		rs.setData(all);
		return new ResponseEntity<ResponseStructure<List<Enquiries>>>(rs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getEnquiriesByCounsellor(Integer cid) {
		Counsellor counsellor = counsellorRepository.findById(cid).orElseThrow(()-> new CounsellorNotFound("Counsellor Not Exist"));
		List<Enquiries> enquiries = counsellor.getEnquiries();
		ResponseStructure<List<Enquiries>> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Filtered Successfully");
		rs.setData(enquiries);
		return new ResponseEntity<ResponseStructure<List<Enquiries>>>(rs, HttpStatus.OK);
	}

}
