package com.ty.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.entity.Enquiries;

public interface EnquiriesRepository extends JpaRepository<Enquiries, Integer>{

	Optional<Enquiries> findByEmail(String email);
}
