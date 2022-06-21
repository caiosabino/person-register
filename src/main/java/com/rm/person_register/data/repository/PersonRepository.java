package com.rm.person_register.data.repository;

import com.rm.person_register.data.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByDocument(Long document);
}