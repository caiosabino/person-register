package com.rm.person_register.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@Table(name = "address")
public class Address {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String street;

	@Column
	private String neighborhood;

	@Column
	private String city;

	@Column
	private String number;

	@Column
	private String complement;

	@Column
	private Date createdAt;

	@Column
	private Date updatedAt;
}