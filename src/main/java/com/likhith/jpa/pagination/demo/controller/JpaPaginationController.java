package com.likhith.jpa.pagination.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.likhith.jpa.pagination.demo.dto.CustomerRequestBodyVO;
import com.likhith.jpa.pagination.demo.dto.CustomerResponseBodyVO;
import com.likhith.jpa.pagination.demo.service.JpaPaginationService;

@RestController
@RequestMapping("/jpaPagination/api/v1")
public class JpaPaginationController {

	@Autowired
	JpaPaginationService service;

	@GetMapping("/getCustomers")
	private CustomerResponseBodyVO getCustomers(@Nullable @RequestParam String name,
			@Nullable @RequestParam String filters, @Nullable @RequestParam(defaultValue = "1") String page,
			@Nullable @RequestParam(defaultValue = "2") String size,
			@Nullable @RequestParam(defaultValue = "false") boolean noPagination) {

		CustomerResponseBodyVO response = service.getCustomers(name, filters, page, size, noPagination);
		return response;

	}

	@PostMapping("/postCustomers")
	private String postCustomers(@NonNull @RequestBody CustomerRequestBodyVO requestBody) {

		String responseMessage = null;
		boolean result = service.postCustomers(requestBody);

		if (result) {
			responseMessage = "Customers Inserted into DB";
		} else {
			responseMessage = "Unexpected Issue while inserting into DB";
		}
		return responseMessage;

	}

}