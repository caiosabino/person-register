package com.rm.person_register.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @Column(nullable = false)
    private String name;

    @Column
    private String documentType;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String tradeName;

    @Column
    private String stateRegistration;

    @Column
    private String businessCategory;

    @Column
    private Date birthDate;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;
}
