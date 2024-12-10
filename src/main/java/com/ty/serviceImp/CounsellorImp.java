package com.ty.serviceImp;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ty.dto.CounsellorDto;
import com.ty.dto.LoginRequest;
import com.ty.entity.Counsellor;
import com.ty.enums.Status;
import com.ty.exception.CounsellorNotFound;
import com.ty.repository.CounsellorRepository;
import com.ty.responsestructure.ResponseStructure;
import com.ty.service.CounsellorService;

@Service
public class CounsellorImp implements CounsellorService{

	@Autowired
	private CounsellorRepository counsellorRepository;

	@Override
	public ResponseEntity<?> registerCounsellor(Counsellor counsellor) {
		Optional<Counsellor> op= counsellorRepository.findByEmail(counsellor.getEmail());
		if (op.isPresent()) {
			ResponseStructure<String> rs = new ResponseStructure<String>();
			rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
			rs.setMessage("Already Registered");
			rs.setData(counsellor.getEmail());
			return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
		} else {
			Counsellor save = counsellorRepository.save(counsellor);
			CounsellorDto cd = new CounsellorDto();
			BeanUtils.copyProperties(save, cd);
			
			ResponseStructure<CounsellorDto> rs = new ResponseStructure<CounsellorDto>();
			rs.setStatusCode(HttpStatus.CREATED.value());
			rs.setMessage("Registration Succesful");
			rs.setData(cd);
			return new ResponseEntity<ResponseStructure<CounsellorDto>>(rs,HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> loginCounsellor(LoginRequest loginRequest) {
		Counsellor counsellor=counsellorRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new CounsellorNotFound("Email not found or Counsellor not register"));
		ResponseStructure<String> rs = new ResponseStructure<String>();
		if (loginRequest.getPassword().equals(counsellor.getPassword())) {
			rs.setStatusCode(HttpStatus.ACCEPTED.value());
			rs.setMessage("Login Succesful");
			rs.setData(loginRequest.getEmail());
			return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
		}
		rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
		rs.setMessage("Invaild Password");
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateStatus(Integer cid, Status status) {
		Counsellor counsellor = counsellorRepository.findById(cid).orElseThrow(()-> new CounsellorNotFound("Counsellor not register"));
		counsellor.setStatus(status);
		Counsellor updatedCounsellor = counsellorRepository.save(counsellor);
		ResponseStructure<Status> rs = new ResponseStructure<Status>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Status is updated");
		rs.setData(updatedCounsellor.getStatus());
		return new ResponseEntity<ResponseStructure<Status>>(rs,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updatePhone(Integer cid, Long phone) {
		Counsellor counsellor=counsellorRepository.findById(cid).orElseThrow(()-> new CounsellorNotFound("Counsellor not resgister"));
		counsellor.setPhone(phone);
		Counsellor updatePhone = counsellorRepository.save(counsellor);
		ResponseStructure<Long> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Phone is updated");
		rs.setData(updatePhone.getPhone());
		return new ResponseEntity<ResponseStructure<Long>>(rs,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getCounsellor(Integer cid) {
		Counsellor counsellor =counsellorRepository.findById(cid).orElseThrow(()-> new CounsellorNotFound("Counsellor not register"));
		CounsellorDto dto = new CounsellorDto();
		BeanUtils.copyProperties(counsellor, dto);
		ResponseStructure<CounsellorDto> rs = new ResponseStructure<CounsellorDto>();
		rs.setStatusCode(HttpStatus.FOUND.value());
		rs.setMessage("Fetch counsellor succesfully");
		rs.setData(dto);
		return new ResponseEntity<ResponseStructure<CounsellorDto>>(rs,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteCounsellor(Integer cid) {
		Counsellor counsellor =counsellorRepository.findById(cid).orElseThrow(()-> new CounsellorNotFound("Counsellor not register"));
		counsellorRepository.delete(counsellor);
		ResponseStructure<String> rs = new ResponseStructure<String>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Counsellor details are deleted");
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
	}

	
}
