package com.likhith.jpa.pagination.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.likhith.jpa.pagination.demo.dto.CustomerInfo;
import com.likhith.jpa.pagination.demo.dto.CustomerRequestBodyVO;
import com.likhith.jpa.pagination.demo.dto.CustomerResponseBodyVO;
import com.likhith.jpa.pagination.demo.entity.CustomerEntity;
import com.likhith.jpa.pagination.demo.mapper.JpaPaginationMapper;
import com.likhith.jpa.pagination.demo.repository.CustomerRepository;
import com.likhith.jpa.pagination.demo.utility.CustomerEntitySpecification;

@Component
public class JpaPaginationServiceImpl implements JpaPaginationService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	JpaPaginationMapper mapper;

	@SuppressWarnings("unchecked")
	@Override
	public CustomerResponseBodyVO getCustomers(String name, String filters, String page, String size,
			boolean noPagination) {

		CustomerResponseBodyVO response = new CustomerResponseBodyVO();
		List<CustomerInfo> customerInfoList = new ArrayList<>();
		List<CustomerEntity> customerEntityList = new ArrayList<>();
		Map<String, String> filtersMap = new HashMap<>();

		Specification<CustomerEntity> customerEntitySpecification = null;

		if (null != filters) {

			if (null != name) {
				customerEntitySpecification = CustomerEntitySpecification.equal("name", name);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				filtersMap = objectMapper.readValue(filters, Map.class);
			} catch (JsonProcessingException e) {
				System.out.println(e.getMessage());
			}

			if (!CollectionUtils.isEmpty(filtersMap)) {
				for (String key : filtersMap.keySet()) {
					Specification<CustomerEntity> spec = CustomerEntitySpecification.equal(key, filtersMap.get(key));
					if (null != customerEntitySpecification) {
						customerEntitySpecification.and(spec);
					} else {
						customerEntitySpecification = spec;
					}
				}
			}

		}

		if (!noPagination) {
			Page<CustomerEntity> customerEntityPage = null;
			Pageable pageable = PageRequest.of(Integer.valueOf(page) - 1, Integer.valueOf(size));

			if (null != name) {
				if (null != filters && null != customerEntitySpecification) {
					customerEntityPage = customerRepository.findAll(customerEntitySpecification, pageable);
				} else {
					customerEntityPage = customerRepository.findByName(name, pageable);
				}
			} else {
				if (null != filters && null != customerEntitySpecification) {
					customerEntityPage = customerRepository.findAll(customerEntitySpecification, pageable);
				} else {
					customerEntityPage = customerRepository.findAll(pageable);
				}
			}

			if (customerEntityPage.hasContent()) {
				customerEntityList = customerEntityPage.getContent().stream().map(s -> (CustomerEntity) s)
						.collect(Collectors.toCollection(ArrayList::new));
				response.setPage(page);
				response.setPages(String.valueOf(customerEntityPage.getTotalPages()));
				response.setSize(size);
				response.setTotal(String.valueOf(customerEntityPage.getTotalElements()));
			}
		} else {

			if (null != name) {
				if (null != filters && null != customerEntitySpecification) {
					customerEntityList = customerRepository.findAll(customerEntitySpecification);
				} else {
					customerEntityList = customerRepository.findByName(name);
				}
			} else {
				if (null != filters && null != customerEntitySpecification) {
					customerEntityList = customerRepository.findAll(customerEntitySpecification);
				} else {
					customerEntityList = customerRepository.findAll();
				}
			}
		}

		if (!CollectionUtils.isEmpty(customerEntityList)) {
			customerInfoList = mapper.mapToCustomerInfo(customerEntityList);

		}

		response.setInfo(customerInfoList);
		return response;
	}

	@Override
	public boolean postCustomers(CustomerRequestBodyVO requestBody) {

		try {
			List<CustomerEntity> customerEntityList = mapper.mapToCustomerEntity(requestBody.getInfo());
			customerRepository.saveAll(customerEntityList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

}