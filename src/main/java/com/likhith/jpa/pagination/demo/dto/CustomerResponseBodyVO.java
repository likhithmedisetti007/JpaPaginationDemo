package com.likhith.jpa.pagination.demo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CustomerResponseBodyVO {

	private String page;
	private String pages;
	private String size;
	private String total;

	private List<CustomerInfo> info;

}