package com.likhith.jpa.pagination.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.likhith.jpa.pagination.demo.entity.CustomerEntity;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

	Page<CustomerEntity> findByName(String name, Pageable pageable);

	List<CustomerEntity> findByName(String name);

	Page<CustomerEntity> findAll(Specification<CustomerEntity> customerEntitySpecification, Pageable pageable);

	List<CustomerEntity> findAll(Specification<CustomerEntity> customerEntitySpecification);

}