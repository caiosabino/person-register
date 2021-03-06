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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@Table(name = "person")
public class Person {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true, nullable = false)
	private Long document;

	@Column
	private String name;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;

	@Column
	private Date createdAt;

	@Column
	private Date updatedAt;
}