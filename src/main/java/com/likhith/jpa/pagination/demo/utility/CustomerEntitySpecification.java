package com.likhith.jpa.pagination.demo.utility;

import org.springframework.data.jpa.domain.Specification;

import com.likhith.jpa.pagination.demo.entity.CustomerEntity;

public class CustomerEntitySpecification {

	public static Specification<CustomerEntity> equal(String field, String value) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), value);
	}

}