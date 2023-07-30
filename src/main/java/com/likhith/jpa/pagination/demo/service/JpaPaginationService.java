package com.likhith.jpa.pagination.demo.service;

import org.springframework.stereotype.Service;

import com.likhith.jpa.pagination.demo.dto.CustomerRequestBodyVO;
import com.likhith.jpa.pagination.demo.dto.CustomerResponseBodyVO;

@Service
public interface JpaPaginationService {

	public CustomerResponseBodyVO getCustomers(String name, String filters, String page, String size,
			boolean noPagination);

	public boolean postCustomers(CustomerRequestBodyVO requestBody);

}