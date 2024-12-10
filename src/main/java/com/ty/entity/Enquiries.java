package com.ty.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ty.enums.ClassMode;
import com.ty.enums.Course;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Enquiries {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer eid;
	
	private String name;
	
	private Long phone;
	
	@Column(unique = true)
	private String email;
	
	@Enumerated(EnumType.STRING)
	private ClassMode classMode=ClassMode.OFFLINE;
	
	@Enumerated(EnumType.STRING)
	private Course course;
	
	private Double fees;
	
	@ManyToOne
	@JoinColumn(name = "cid")
	@JsonIgnore
	private Counsellor counsellor;
}
